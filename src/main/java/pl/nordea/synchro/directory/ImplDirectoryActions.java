package pl.nordea.synchro.directory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.nordea.synchro.directory.model.IDirParticipantDao;
import pl.nordea.synchro.ldap.ILdapService;
import pl.nordea.synchro.user.model.IUserCollectionDao;
import pl.nordea.synchro.user.model.enties.PojoUser;

@Component
public class ImplDirectoryActions implements IDirectoryActions {
	private static final Logger logger=LoggerFactory.getLogger(ImplDirectoryActions.class);
	@Autowired
	private IDirParticipantDao participant;
	
	@Autowired
	private IUserCollectionDao userCollection;
	
	@Autowired
	private ILdapService bpssUser;
	
	@Override
	public void performChangesOnDirectory() {
		try {
			for (PojoUser user:userCollection.getAllCollection()){
				
				if(user.isFlagCreate()) {
					//TODO 
					//user object shouldn't be used directly in participant - participant should fetch user by id
					participant.create(user);
				}else {
					participant.fetchParticipantBpm(user.getUserCode());
					participant.setCountryId(user.getUserCountryId());
					
					if (user.isFlagDisable()) {
						participant.disable();
					}
					
					if (user.isFlagChangeName()) {
						participant.changeName(user.getUserName());
					}
					
					if (user.isFlagChangeEmail()) {
						participant.changeEmail(user.getUserEmail());
					}
	
					if (user.isFlagChangeCountry()) {
						participant.changeCountry(user.getUserCountryId());
					}
					
					if (user.isFlagChangeRoles()) {
						participant.rebuildRoles();
					}
					
					if (user.isFlagEnable()) {
						participant.activate();
					}
				}	
				participant.store();
				bpssUser.updateUserLastSynchroDate(user.getUserCode());
			}

		}catch(Exception e) {
			logger.error("Exception during performing changes on participants accounts. "+e.getMessage());
			e.printStackTrace();
		}
	}
}
