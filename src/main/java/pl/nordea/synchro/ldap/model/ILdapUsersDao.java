package pl.nordea.synchro.ldap.model;

import java.util.List;

import pl.nordea.synchro.ldap.model.enties.LdapUserRecPojo;

/**
 * Gets data (user accounts data) from LDAP
 * @author m010678
 *
 */
public interface ILdapUsersDao {
	/**
	 * Retrieve list of all users from LDAP. With no country specific
	 * @return List of @LdapUserRecPojo
	 */
	List<LdapUserRecPojo> getAllUsers();
	
	/**
	 * Retrieve list of users for specific Ldap context (country or ldap specific resource)
	 * @param contextStringForCountry String representative of the ldap context (could be country)
	 * @return List of @LdapUserRecPojo
	 */
	List<LdapUserRecPojo> getAllUsers(String contextStringForCountry);
	/**
	 * Get properties to connect to LDAP
	 * Sets user, password, server address, domain name and countries to connect to LDAP.
	 */
	void getConnectProperties();
	
	/**
	 * Retrieves all users data from LDAP context (first fetched). Used for testing.
	 */
	void getUserData();
}
