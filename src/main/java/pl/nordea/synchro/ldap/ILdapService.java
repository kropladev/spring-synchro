package pl.nordea.synchro.ldap;
/**
 * Running synchronization process of ldap accounts with BPSS_ADMIN.USERS table
 * @author m010678
 *
 */
public interface ILdapService {
	/**
	 * Start synchronized LDAP with Users table 
	 */
	void synchronizeLdapDataWithBpssAdminSchema();
	
	/**
	 * Method to move to different Service (this should be used only for LDAP modification)
	 * @param userCode
	 */
	void updateUserLastSynchroDate(String userCode);
	//TODO move method @updateUserLastSynchroDate to different service directly connected with BPSS_ADMIN schema
	
	void checkConnection();

}
