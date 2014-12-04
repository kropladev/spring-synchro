package pl.nordea.synchro.directory.model;

import pl.nordea.synchro.user.model.enties.PojoUser;
/**
 * Sets of actions which can be performed on single user/participant which must first created or fetched.
 * @author m010678
 */
public interface IDirParticipantDao {
	/**
	 * Fetch user (participant) record inside the BPM Directory (if participant record exists)
	 * @param userCode - user code (RID: M012345 or Z012345)
	 */
	void fetchParticipantBpm(String userCode);
	
	/**
	 * Disable user inside BPM directory (not delete!!).
	 * This method sets FUEGO_STATUS column symbol at value 'D' using specific fuego-java-client API.
	 */
	void disable();
	
	/**
	 * Activate user account in BPM Directory. 
	 * This method sets FUEGO_STATUS column symbol at value 'A' using specific fuego-java-client API.
	 */
	void activate();
	
	/**
	 * Rebuilds users roles definition based on BPSS_ADMIN.USER_POSITIONS table. 
	 */
	void rebuildRoles();
	
	/**
	 * Changes user names based on that attached in parameter
	 * @param userName
	 */
	void changeName(String userName);
	
	/**
	 * Changing participant Organisation Unit based on user Country id
	 * @param coutryId
	 */
	void changeCountry(int coutryId);
	
	/**
	 * perform changes on Directory - change user email address taken from BPSS_ADMIN.USERS table
	 * @param userEmail - new email address 
	 */
	void changeEmail(String userEmail);
	
	/**
	 * Creating new user account from scratch. Notice that there should not be used fetch operation first!
	 * @param user
	 */
	void create(PojoUser user);
	
	/**
	 * Storing user data into DB. Notice that you should be calling store only one on each participant, because its triggers rebuilding all participant data inside the engine!!
	 */
	void store();
	
	/**
	 * Sets user Country identifier (given as a parameter)
	 * @param countryId
	 */
	void setCountryId(int countryId);

	String testPrint();
	public String getParticipantName();
}
