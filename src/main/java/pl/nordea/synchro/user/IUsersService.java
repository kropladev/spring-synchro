package pl.nordea.synchro.user;
/**
 * Collection of users data with symbol of change
 * 
 * @author m010678
 *
 */
public interface IUsersService {
	/**
	 * Gets all user records (userId, userCode, userCountryId, synchroAction) from database.
	 * Single record points on change inside the core system that needs to be synchronized inside BPM.
	 * The result of this method will be to fill up collection with users data with pointe action.
	 * This collection will be used in second step of synchronization- @performChangesOnDirectory() method
	 */
	public void collectUsersForSynchroFromCoreSystem();
	
	/**
	 * print simple information about user record - should be used only for test purpose
	 */
	public void printUsersCollection();
	
	/**
	 * clear users collection to prevent the synchronization of old (listed in previous synchro) users
	 */
	public void flushUsersCollection();

	/**
	 * change countryID for synchro and run collectUsersForSynchroFromCoreSystem();
	 * @param countryCode
	 */
	void collectUsersForSynchroFromCoreSystem(int countryCode);
}
