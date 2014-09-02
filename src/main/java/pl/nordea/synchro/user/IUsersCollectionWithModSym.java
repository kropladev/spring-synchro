package pl.nordea.synchro.user;
/**
 * Collection of users data with symbol of change
 * 
 * @author m010678
 *
 */
public interface IUsersCollectionWithModSym {

	public void collectUsersForSynchroFromCoreSystem();
	public void performChangesOnDirectory();
}
