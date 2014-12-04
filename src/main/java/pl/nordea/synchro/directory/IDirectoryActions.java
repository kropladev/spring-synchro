package pl.nordea.synchro.directory;

/**
 * trigger changes on participant accounts inside Directory
 * @author m010678
 *
 */
public interface IDirectoryActions {
	
	/**
	 * Gets collection of users data (which should be created and filled in first step of synchronization - @collectUsersForSynchroFromCoreSystem
	 * and make change in particpants accounts based the synchroAction symbol atached to each record
	 */
	public void performChangesOnDirectory();
}
