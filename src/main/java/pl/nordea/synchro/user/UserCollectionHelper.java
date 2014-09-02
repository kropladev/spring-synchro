package pl.nordea.synchro.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.nordea.synchro.config.SynchroActionEnum;
import pl.nordea.synchro.directory.IDirectorySession;
import pl.nordea.synchro.directory.IParticipant;
import pl.nordea.synchro.directory.ParticipantBpm;
import pl.nordea.synchro.repo.IUsersListFromDatabase;
import pl.nordea.synchro.utils.SynchroStatistics;

@Repository
public class UserCollectionHelper implements IUsersCollectionWithModSym {
	
	@Autowired
	public IUserCollectionActions userCollection;

	@Autowired
	public IUsersListFromDatabase userRepoFromDB;
	
	@Autowired
	private IDirectorySession session;
	
	@Override
	public void collectUsersForSynchroFromCoreSystem(){
		int countryCode = 1;
		//this.collectUsersForDisable(countryCode);
		//this.collectUsersForActivation(countryCode);
		//this.collectUsersForModifyNames(countryCode);
		this.collectUsersForModifyCountry(countryCode);
		//this.collectUsersForRebuildingRoles(countryCode);
	}
	
	@Override
	public void performChangesOnDirectory() {
		for (User user:userCollection.getAllCollection()){
			//TODO check if participant was fetched in database engine
			IParticipant participant= new ParticipantBpm(user.getUserCode(),this.session);
			if(user.isFlagDisable()) {participant.disable();}
			if(user.isFlagChangeName()){ participant.changeName(user.getUserName());}
			if(user.isFlagChangeEmail()){participant.changeEmail(user.getUserEmail());}
				//TODO implementation of the change country value - sets the enum to string and int
			//if(user.isFlagChangeCountry()){participant.changeCountry((user.);}
				//TODO implementation of part.rebuildRoles method
			if(user.isFlagChangeRoles()){participant.rebuildRoles();}
			if(user.isFlagEnable()){participant.activate();}
			participant.store();
		}
		
	}
	
	
	public void collectUsersForDisable(int countryCode) {
		SynchroStatistics.counterDisableDb=prepareUserMapForSynchronisation(SynchroActionEnum.DISABLE, countryCode);
	}

	public void collectUsersForActivation(int countryCode) {
		SynchroStatistics.counterActivateDb=prepareUserMapForSynchronisation(SynchroActionEnum.ACTIVATE,
				countryCode);
	}

	public void collectUsersForModifyNames(int countryCode) {
		SynchroStatistics.counterChangeNameDb=prepareUserMapForSynchronisation(SynchroActionEnum.CH_NAME, countryCode);
	}

	public void collectUsersForModifyCountry(int countryCode) {
		SynchroStatistics.counterChangeOuDb=prepareUserMapForSynchronisation(SynchroActionEnum.CH_COUNTRY,
				countryCode);
	}

	public void collectUsersForModifyEmail(int countryCode) {
		SynchroStatistics.counterChangeEmailDb=prepareUserMapForSynchronisation(SynchroActionEnum.CH_EMAIL,
				countryCode);
	}

	public void collectUsersForRebuildingRoles(int countryCode) {
		SynchroStatistics.counterRebuildRolesDb=prepareUserMapForSynchronisation(SynchroActionEnum.CH_ROLE, countryCode);
	}

	public void collectUsersForCreation(int countryCode) {
		SynchroStatistics.counterCreateDb=prepareUserMapForSynchronisation(SynchroActionEnum.CREATE, countryCode);
	}


	public int prepareUserMapForSynchronisation(SynchroActionEnum action,int countryCode) {
		List<User> list = userRepoFromDB.collectUsersForSpecificAction(countryCode,action);
		for (User user : list) {
			userCollection.processUserAction(user, action);
		}
		return list.size();
	}


	
}
