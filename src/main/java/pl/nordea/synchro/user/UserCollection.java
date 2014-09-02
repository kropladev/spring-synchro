package pl.nordea.synchro.user;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;
import pl.nordea.synchro.config.SynchroActionEnum;

@Repository
public class UserCollection implements IUserCollectionActions{
	private Map<String, User> userMap;
	
	public UserCollection(){
		checkUserMapIfNull();
	}
	
	public void addNewValue(User user, SynchroActionEnum action) {
		user.flaggSpecificAction(action);
  	  	this.userMap.put(user.getUserCode(), user);	
	}

	
	public void modifyExistingValue(String userKey, SynchroActionEnum action) {
		 User userToModify =userMap.get(userKey);
    	 userToModify.flaggSpecificAction(action);
    	 userMap.put(userKey,userToModify);		
	}

	
	public void checkUserMapIfNull() {
		if (this.userMap==null){
			this.userMap=new TreeMap<String, User>();
		}
	}

	
	public boolean userRecordAlreadyExists(String userCode) {
		return this.userMap.containsKey(userCode);
	}

	@Override
	public Collection<User> getAllCollection() {
		return userMap.values();
	}


	@Override
	public void processUserAction(User user, SynchroActionEnum action) {
		String userKey = user.getUserCode();
		if(userKey!=null){
			if (this.userRecordAlreadyExists(userKey)) {
				this.modifyExistingValue(userKey, action);
			} else {
				this.addNewValue(user, action);
			}
		}
		
	}

	

}
