package pl.nordea.synchro.ldap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.nordea.synchro.ldap.model.ILdapUsersDao;
import pl.nordea.synchro.ldap.model.IRepoBpssAdminUsersDao;
import pl.nordea.synchro.ldap.model.enties.LdapBpssAdminUser;
import pl.nordea.synchro.ldap.model.enties.LdapUserRecPojo;

@Service
public class ImplLdap implements ILdapService{
	private final Logger logger= LoggerFactory.getLogger(ImplLdap.class);
	private Map<String,LdapBpssAdminUser> userListBpss;
	private List<LdapUserRecPojo>    userListLdap;
	private List<LdapBpssAdminUser> userListToSynchronize;
	
	@Autowired
	private IRepoBpssAdminUsersDao bpssUsers;
	@Autowired
	private ILdapUsersDao ldapUsers;
	
	/**
	 * list of actions to perform:
	 * - get data from bpss_admin schema
	 * - get data from Ldap
	 * - check which accounts should be synchronized
	 * - send changes to db
	 */
	@Override
	public void synchronizeLdapDataWithBpssAdminSchema() {
		try {
			logger.debug("synchronizeLdapDataWithBpssAdminSchema Start");
			ldapUsers.getConnectProperties();
			
			prepareListOfAllUsersFromBpssAdmin();
			prepareListOfAllUsersFromLdap();
			synchronizeListsOfUsers();
			sendChangesToDatabaseUsingBatch();
		}catch(Exception e) {
			logger.error("Exception during synchronizing accounts with LDAP. ");
			e.printStackTrace();
		}
	}

	/**
	 * prepere list of users which has different mail addresses
	 * in result new list is prepared @userListToSynchronize
	 */
	private void synchronizeListsOfUsers() {
		//TODO refactoring - too many if condition statements
		logger.debug("synchronizedListsOfUsers; START");
		if (this.userListLdap!=null &&! this.userListLdap.isEmpty() && this.userListBpss!=null && !this.userListBpss.isEmpty()) {
			
			this.userListToSynchronize = new ArrayList<LdapBpssAdminUser>();
			//iterate on all LDAP accounts
			for(LdapUserRecPojo ldapUser: userListLdap) {
				//if account exists on BPSS side...
				if (userListBpss.containsKey(ldapUser.getAccount().toUpperCase())) {
					logger.debug("user found in BPSS");
					LdapBpssAdminUser bpUser= userListBpss.get(ldapUser.getAccount().toUpperCase());
					//.. than check if it has different mail address
					logger.debug("Checking user:"+bpUser.getUserCode());
					if(ldapUser.getEmail()!=null) {
						try {
							if(bpUser.getUserMail()==null || (!bpUser.getUserMail().toString().trim().equals(ldapUser.getEmail().toString().trim()))) {
								if (bpUser.getUserMail()==null) {
									logger.info("adding user to synchronize list:"+bpUser.getUserCode()+"|change old mail:[null]|new:"+ldapUser.getEmail()+"["+ldapUser.getEmail().length()+"]|");
								} else {
									logger.info("adding user to synchronize list:"+bpUser.getUserCode()+"|change old mail:"+bpUser.getUserMail()+"["+bpUser.getUserMail().length()+"]|new:"+ldapUser.getEmail()+"["+ldapUser.getEmail().length()+"]|");
								}
								
								bpUser.setUserMail(ldapUser.getEmail());
								//send to list of accounts to change/update on BPSS_ADMIN schema
								this.userListToSynchronize.add(bpUser);
							}
						} catch (Exception ex) {
							logger.error("Exception while preparing list of ldap changes: "+ex.getMessage());
						}
					}
				}
			}
			
			logger.info("After preparing list for synchronize. Number of records to update:"+(this.userListToSynchronize!=null?this.userListToSynchronize.size():"-1"));
		}
	}

	/**
	 * get all accounts from Ldap
	 */
	private void prepareListOfAllUsersFromLdap() {
		logger.debug("prepareListOfAllUsersFromLdap START");
		this.userListLdap= ldapUsers.getAllUsers();	
		if (this.userListLdap !=null) {
			logger.info("  list was created with ldap users records number:"+this.userListLdap.size());
		}
	}

	/**
	 * get all users from db bpss_admin schema
	 */
	private void prepareListOfAllUsersFromBpssAdmin() {
		logger.debug("prepareListOfAllUsersFromBpssAdmin START");
		this.userListBpss= bpssUsers.collectAllBpssAdmnUsersMap();
		if (this.userListBpss !=null) {
			logger.info("  list was created with BPSS_ADMIN.USERS records number:"+this.userListBpss.size());
		}
	}
	
	/**
	 * send list of all accounts that should have changed mail address into db bpss_admin using batch
	 */
	private void sendChangesToDatabaseUsingBatch() {
		logger.debug("sendChangesToDatabaseUsingBatch START");
		if(this.userListToSynchronize!=null && !this.userListToSynchronize.isEmpty()) {
			//int[] updatesCounter= bpssUsers.updateBatch(this.userListToSynchronize);
			bpssUsers.updateBatch(this.userListToSynchronize);
		}
	}

	/**
	 * for test only
	 */
	@Override
	public void checkConnection() {
		ldapUsers.getConnectProperties();
		ldapUsers.getUserData();
	}

	@Override
	public void updateUserLastSynchroDate(String userCode) {
		bpssUsers.updateModificationTime(userCode);
	}
	
	
}
