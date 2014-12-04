package pl.nordea.synchro.core;


/**
 * contract class of synchronization process - methods used inside synchronization
 * @author m010678
 *
 */
public interface ISynchroActions {
	/**
	 * Starts synchronization subprocesses for all countries.
	 */
	void startSynchronisationProcess();

	void checkConnection();

	void checkLdap();

	/**
	 * Starts synchronization subprocesses for specific country.
	 * @param countryId int country id - 1- pl, 2- lt, 3- lv, 4 - ee
	 */
	void startSynchronisationProcess(int countryId);
	
}
