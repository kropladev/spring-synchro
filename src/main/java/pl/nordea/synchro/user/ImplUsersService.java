package pl.nordea.synchro.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.nordea.synchro.core.SynchroActionEnum;
import pl.nordea.synchro.user.model.IUserCollectionDao;
import pl.nordea.synchro.user.model.IUserListDao;
import pl.nordea.synchro.user.model.enties.PojoUser;
import pl.nordea.synchro.utils.AppProps;
import pl.nordea.synchro.utils.SynchroStatistics;

@Service
public class ImplUsersService implements IUsersService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AppProps props;
	
	@Autowired
	private  IUserCollectionDao usersForAction;

	@Autowired
	private  IUserListDao userAccountsDao;
	
	private int countryCode;
	
	@Override
	public void collectUsersForSynchroFromCoreSystem(int countryCode) {
		this.countryCode=countryCode;
		collectUsersForSynchroFromCoreSystem();
	}
	
	@Override
	public void collectUsersForSynchroFromCoreSystem(){
		//this.countryCode = 1;
		
		/** collect list of users that should be resynchronized. There will be attached synchroAction symbol to each user record 
		 * To switch off some of the actions you can comment some line
		 */
		logger.info("collectUsersForSynchroFromCoreSystem for country::"+this.countryCode);
		
		try {
			if(props.isChangeOrganizationUnitModuleActive()) {
				logger.info("Synchro module: Change Country users acounts is ENABLED");
				SynchroStatistics.setCounterChangeOuDb(prepareUserMapForSynchronisation(SynchroActionEnum.CH_COUNTRY,countryCode));
			}else {
				logger.info("Synchro module: Change Country users acounts is DISABLED");
			}
			
			if(props.isDisableModuleActive()) {
				logger.info("Synchro module: Disabling users acounts is ENABLED");
				SynchroStatistics.setCounterDisableDb(prepareUserMapForSynchronisation(SynchroActionEnum.DISABLE, countryCode));
			}else {
				logger.info("Synchro module: Disabling users acounts is DISABLED");
				}
			
			if(props.isActivateModuleActive()) {
				logger.info("Synchro module: Activation of users acounts is ENABLED");
				SynchroStatistics.setCounterActivateDb(prepareUserMapForSynchronisation(SynchroActionEnum.ACTIVATE,countryCode));
			}else {
				logger.info("Synchro module: Activation of users acounts is DISABLED");
			}
			
			if(props.isChangeNamesModuleActive()) {
				logger.info("Synchro module: Change Names of users is ENABLED");
				SynchroStatistics.setCounterChangeNameDb(prepareUserMapForSynchronisation(SynchroActionEnum.CH_NAME, countryCode));
			}else {
				logger.info("Synchro module: Change Names of users is DISABLED");
			}
			
			if(props.isChangeEmailModuleActive()) {
				logger.info("Synchro module: Change users emails is ENABLED");
				SynchroStatistics.setCounterChangeEmailDb(prepareUserMapForSynchronisation(SynchroActionEnum.CH_EMAIL,countryCode));
			}else {
				logger.info("Synchro module: Change users emails is DISABLED");
			}
			
			if(props.isRebuildRolesModuleActive()) {
				logger.info("Synchro module: Rebuild Roles is ENABLED");
				SynchroStatistics.setCounterRebuildRolesDb(prepareUserMapForSynchronisation(SynchroActionEnum.CH_ROLE, countryCode));
			}else {
				logger.info("Synchro module: Rebuild Roles is DISABLED");
			}
			
			if(props.isCreateModuleActive()) {
				logger.info("Synchro module: Create new users acounts is ENABLED");
				SynchroStatistics.setCounterCreateDb(prepareUserMapForSynchronisation(SynchroActionEnum.CREATE, countryCode));
			}else {
				logger.info("Synchro module: Create new users acounts is DISABLED");
			}
			
			if(props.isChangeUnitModuleActive()) {
				//TODO implement
				logger.info("Synchro module: Change Unit is ENABLED");
			}else {
				logger.info("Synchro module: Change Unit is DISABLED");
			}
		}catch(Exception e) {
			logger.error("Exception during fetching users accounts records to synchronized from Database. "+e.getMessage());
		}
	}
	

	/**
	 * Gets data from inside database and creates user record or (if record exists) adds new flags which points on single synchroAction change (which should be performed on participant record)
	 * It checks which users are in result list from specific DB view. The query handle only single synchroAction which is given into method as a parameter. Second parameter is a country ID;
	 * @param action
	 * @param countryCode
	 * @return Users collection size.
	 */
	public int prepareUserMapForSynchronisation(SynchroActionEnum action,int countryCode) {
		logger.info("collecting Data for user accounts on ACTION:"+action+"| country:"+countryCode);
		//gets list of users from DB
		List<PojoUser> list = userAccountsDao.collectUsersForSpecificAction(countryCode,action);
		//translate users list on collection data 
		for (PojoUser user : list) {
			usersForAction.markUserForActionChange(user, action);
		}
		return list.size();
	}
	

	/* getters and setters section */
	public int getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(int countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * for test connection only
	 */
	@Override
	public void printUsersCollection() {
		for(PojoUser user:usersForAction.getAllCollection()) {
			logger.info("USer:"+user.getUserCode()+" last action:"+user.getActionToDo());
		}
		
	}


	@Override
	public void flushUsersCollection() {
		usersForAction.flushUserList();
		
	}



}
