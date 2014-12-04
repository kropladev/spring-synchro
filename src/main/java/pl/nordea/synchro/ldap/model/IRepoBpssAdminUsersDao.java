package pl.nordea.synchro.ldap.model;

import java.util.List;
import java.util.Map;

import pl.nordea.synchro.ldap.model.enties.LdapBpssAdminUser;

/**
 * [LDAP mail synchro module]
 * 
 * @author m010678
 *
 */
public interface IRepoBpssAdminUsersDao {
	/**
	 * get all users from database table BPSS_ADMIN.USERS with userCode, Mail and countryId fields 
	 * @return List of BpssAdminUser objects containing mail, countryId and userCode
	 */
	public List<LdapBpssAdminUser> collectAllBpssAdmnUsers();	
	public Map<String, LdapBpssAdminUser> collectAllBpssAdmnUsersMap();
	
	/**
	 * update new mail value into BPSS_ADMIN.USERS table 
	 * @return 
	 */
	public int update(LdapBpssAdminUser user);
	
	public void updateModificationTime(String userCode);
	
	/**
	 * update new mail values in batch operation into BPSS_ADMIN.USERS table 
	 */
	public void updateBatch(List<LdapBpssAdminUser> userList);

}
