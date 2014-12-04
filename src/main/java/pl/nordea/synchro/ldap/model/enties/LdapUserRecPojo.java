package pl.nordea.synchro.ldap.model.enties;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;

/**
 * POJO
 * @author m010678
 * POJO class for single user account in LDAP (will be used in a List collection of users)
 */
public class LdapUserRecPojo {

	private String account;

	private String name;
	private List<String> groups;
	private String email;
	private boolean enabled;
	private String telephone;
	private String bitsAttribute;
	
	public String getBitsAttribute() {
		return bitsAttribute;
	}

	public void setBitsAttribute(String bitsAttribute) {
		this.bitsAttribute = bitsAttribute;
	}

	public String getBitsAttributeHex() {
		return bitsAttributeHex;
	}

	public void setBitsAttributeHex(String bitsAttributeHex) {
		this.bitsAttributeHex = bitsAttributeHex;
	}

	private String bitsAttributeHex;
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public LdapUserRecPojo() {
		groups = new ArrayList<String>();
	}
	
	@Override
	public String toString() {
		return "UserRecObj [account=" + account + ", name=" + name
				+ ", groups=" + groups + ", email=" + email + ", enabled="
				+ enabled + ", telephone=" + telephone + ", bitsAttribute="
				+ bitsAttribute + ", bitsAttributeHex=" + bitsAttributeHex
				+ "]";
	}

	/**
	 * parse attribute value to Hex
	 * 
	 * @param bitsAttribute2
	 * @return
	 * @throws NumberFormatException
	 * @throws NamingException
	 */
	public static String parseBitsAttrToHex(Attribute bitsAttribute2) throws NumberFormatException, NamingException {
		 long lng=Long.parseLong(bitsAttribute2.get(0).toString());
		 return Long.toHexString(lng);
	}

	/**
	 * Check account data - if account is active
	 * - based on bitsAttrib variable taken from LDAP account 
	 * - if value of 3. bit (from the end) of parsed to hex string is equal 2 then account is enabled 
	 * @param bitsAttrib
	 * @throws NumberFormatException
	 * @throws NamingException
	 */
	public void checkIfAccountIsActive(Attribute bitsAttrib) throws NumberFormatException, NamingException {
		this.setBitsAttribute(bitsAttrib.get(0).toString());
		//parse value to Hex
		this.setBitsAttributeHex(parseBitsAttrToHex(bitsAttrib));
/*		long lng = Long.parseLong(this.getBitsAttribute());
		this.setBitsAttributeHex(Long.toHexString(lng));*/
		//get 3.bit from the end
		char res=(this.getBitsAttributeHex()).substring((this.getBitsAttributeHex()).length()-3).charAt(0);
		if (res == '2') {
			this.setEnabled(true);
		}
		
	}
	
}