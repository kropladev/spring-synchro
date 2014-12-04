package pl.nordea.synchro.user.model;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;
import pl.nordea.synchro.core.SynchroActionEnum;
import pl.nordea.synchro.user.model.enties.PojoUser;

@Repository
public class ImplUserCollection implements IUserCollectionDao{
	private Map<String, PojoUser> userMap;
	
	public ImplUserCollection(){
		checkUserMapIfNull();
	}
	
	public void addNewValue(PojoUser user, SynchroActionEnum action) {
		user.flaggSpecificAction(action);
  	  	this.userMap.put(user.getUserCode(), user);	
	}

	public void modifyExistingValue(PojoUser user, SynchroActionEnum action) {
		 PojoUser userToModify =userMap.get(user.getUserCode());
    	 userToModify.flaggSpecificAction(action);
    	 if(action.equals(SynchroActionEnum.CH_EMAIL)) {
    		 userToModify.setUserEmail(user.getUserEmail());
    	 }else if(action.equals(SynchroActionEnum.CH_NAME)) {
    		 userToModify.setUserName(user.getUserName());
    	 }else if(action.equals(SynchroActionEnum.CH_COUNTRY)) {
    		 userToModify.setUserCountryId(user.getUserCountryId());
    	 }
    	// userMap.put(userKey,userToModify);		
	}

	public void checkUserMapIfNull() {
		if (this.userMap==null){
			this.userMap=new TreeMap<String, PojoUser>();
		}
	}

	public boolean userRecordAlreadyExists(String userCode) {
		return this.userMap.containsKey(userCode);
	}

	@Override
	public Collection<PojoUser> getAllCollection() {
		return userMap.values();
	}

	@Override
	public void markUserForActionChange(PojoUser user, SynchroActionEnum action) {
		String userKey = user.getUserCode();
		if(userKey!=null){
			if (this.userRecordAlreadyExists(userKey)) {
				this.modifyExistingValue(user, action);
			} else {
				this.addNewValue(user, action);
			}
		}
	}
	
	@Override
	public void flushUserList() {
		this.userMap.clear();
	}
}
