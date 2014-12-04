package pl.nordea.synchro.ldap.model.enties;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO
 * Connection data needed to link and log into LDAP server;
 * data will be set in SynchroLdap class
 * @author m010678
 *
 */
public class PojoLdapConnection {
	private String domainName ;
	private String serverName ;
	private String userAdmin;
	private String userAdminPassw;
	private List<String> countriesForSynchro;
	
	public PojoLdapConnection(){
		countriesForSynchro=new ArrayList<String>();
	}
	
	public String getUserAdmin() {
		return userAdmin;
	}
	public void setUserAdmin(String userAdmin) {
		this.userAdmin = userAdmin;
	}
	public String getUserAdminPassw() {
		return userAdminPassw;
	}
	public void setUserAdminPassw(String userAdminPassw) {
		this.userAdminPassw = userAdminPassw;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getServerName() {
		return serverName;
	}
	public List<String> getCountriesForSynchro() {
		return countriesForSynchro;
	}
	
	public void resetCountryForSynchro() {
		this.countriesForSynchro.clear();
	}
	
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public void addCountryForSynchro(String nodeValue) {
		this.countriesForSynchro.add(nodeValue);
	}
	
	@Override
	public String toString() {
		return "LdapConnectionObj [domainName=" + domainName + ", serverName="
				+ serverName +  ", userAdmin="
				+ userAdmin + ", userAdminPassw=" + userAdminPassw + "]";
	}
}
