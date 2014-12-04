package pl.nordea.synchro.ldap.model;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.nordea.synchro.ldap.model.enties.LdapUserRecPojo;
import pl.nordea.synchro.ldap.model.enties.PojoLdapConnection;
import pl.nordea.synchro.utils.IAppPropsService;
import pl.nordea.synchro.utils.SynchroStatistics;
import static pl.nordea.synchro.utils.PasswordWallet.*;

/**
 * 
 * @author m010678
 *
 */
@Repository
public class ImplLdapUsers implements ILdapUsersDao{

	@Autowired
	private IAppPropsService synchroLdapProps;
	private int accountCounter = 0;
	private static final Logger logger = LoggerFactory.getLogger(ImplLdapUsers.class);
	private PojoLdapConnection lconn ;
	private Hashtable<String, String> env;
	private DirContext ctx;
	private List<LdapUserRecPojo> ldapUsers;
	private String[] attributeNames = { /*"memberOf",*/ "userAccountControl", "mail",
			"name", "sAMAccountName","telephonenumber" };

	public ImplLdapUsers(){
		lconn = new PojoLdapConnection();
		ldapUsers= new ArrayList<LdapUserRecPojo>();
	}
	

	@Override
	public void getConnectProperties(){;
	try {
		String countriesSynchro=synchroLdapProps.getProperty("ldap.contextsSynchro");
		lconn.resetCountryForSynchro();
		for (String countrySymbol : countriesSynchro.split(",")) {
			lconn.addCountryForSynchro(synchroLdapProps.getProperty("ldap.rootContext"+countrySymbol));
		}
		lconn.setDomainName( synchroLdapProps.getProperty("ldap.loginDomain"));
		lconn.setServerName(synchroLdapProps.getProperty("ldap.controller"));
		lconn.setUserAdmin(synchroLdapProps.getProperty("ldap.user"));
	
		lconn.setUserAdminPassw(decrypt(synchroLdapProps.getProperty("ldap.passw")));
	} catch (Exception e) {
		logger.error("Exception during the properties loading. Check: ldap.contextsSynchro/ldap.rootContext/ldap.rootContext+[CountryId]/ldap.loginDomain/ldap.controller/ldap.user/ldap.passw. "+e);
	}
	}
	
	/**
	 * init connect to directory 
	 * @param user
	 * @param password
	 */
	public void initDirectoryContext(String user, String password){
		try {
			logger.debug("[SynchroLdap.initDirectoryContext]user: " + user);
			// create an initial directory context
			env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, "ldap://" + lconn.getServerName());
			env.put(Context.SECURITY_PRINCIPAL, user + "@" + lconn.getDomainName());
			env.put(Context.SECURITY_CREDENTIALS, password);
		}catch (NullPointerException ne) {
			logger.error("Exception during the initialization the Ldap connection. Check configuration values for user/password/ServerName/DomainName. "+ne);
		}
	}
		
	
	/**
	 * create new context based on Hashtable<String, String> env object 
	 * (data inside this object is set from lconn object of type LdapConnectionObj)
	 * @throws NamingException
	 */
	public void createDirectoryContext() throws NamingException{
		ctx = new InitialDirContext(env);
	}
	
	/**
	 * connects to Ldap using ldap.properties,
	 * creates directory context
	 * and returns List of UserRecObj records 	 	
	 */
	@Override
	public List<LdapUserRecPojo> getAllUsers() {
		try {		
			initDirectoryContext(this.lconn.getUserAdmin(), this.lconn.getUserAdminPassw());
			createDirectoryContext();
			SynchroStatistics.setEvent1Time(new DateTime());
			logger.info("this.lconn.getCountriesForSynchro():["+this.lconn.getCountriesForSynchro().size()+"]:"+this.lconn.getCountriesForSynchro());
			for (String contextString : this.lconn.getCountriesForSynchro()) {
				logger.info("[SynchroLdap.getAllUsers] Getting list of users for:"+contextString);
				listSubContext(ctx, contextString);
				SynchroStatistics.logEvent1Duration(" LDAP connect", "");
			}
			logger.debug("[SynchroLdap.getAllUsers] All users in LDAP="+accountCounter);
		} catch (NamingException e) {
			logger.error("[SynchroLdap.getAllUsers] getAllUsers error: "+e);
		}
		return ldapUsers;
	}
	/**
	 * connects to Ldap using ldap.properties,
	 * creates directory context;
	 * use recurency of listSubContext (which adds user records to field ldapUsers of type List)
	 * and returns List of UserRecObj records
	 */
	@Override
	public List<LdapUserRecPojo> getAllUsers(String countrySymbol) {
		ldapUsers.clear();
		try {
			logger.debug("[SynchroLdap.getAllUsers(countrySymbol)] getAllUsers for country:"+countrySymbol);	
			initDirectoryContext(this.lconn.getUserAdmin(), this.lconn.getUserAdminPassw());
			createDirectoryContext();
			logger.debug("[SynchroLdap.getAllUsers(countrySymbol)] Getting list of users for:"+countrySymbol);
			listSubContext(ctx, countrySymbol);
			
			logger.debug("[SynchroLdap.getAllUsers(countrySymbol)]All users in LDAP="+accountCounter);
		} catch (NamingException e) {
			logger.error("[SynchroLdap.getAllUsers(countrySymbol)] getAllUsers error: "+e);
		}
		return ldapUsers;
	}

	/**
	 * drill down data inside the nodes of LDAP server
	 * @param ctx - context hashtable based on env variable set in initDirectoryContext method
	 * @param countrySymbol 
	 * @throws NamingException
	 */
	private void listSubContext(DirContext ctx, String countrySymbol)
			throws NamingException {
		

		NamingEnumeration<?> contentsEnum = ctx.list(countrySymbol);
		while (contentsEnum.hasMore()) {
			NameClassPair ncp = (NameClassPair) contentsEnum.next();
			String userName = ncp.getName();
			logger.debug("LDAP userName:"+userName);
			Attributes attr1 = ctx.getAttributes(userName + "," + countrySymbol,
					new String[] { "objectcategory" });
			if(attr1.get("objectcategory")!=null) {
				if (attr1.get("objectcategory").toString().indexOf("CN=Person") == -1) {
					// Recurse sub-contexts
					listSubContext(ctx, userName + "," + countrySymbol);
				} else {
					logger.debug("LDAP fetching account["+accountCounter+"] user:"+userName+"||countrySYMBOL:"+countrySymbol);
					fetchUsersData(userName,countrySymbol,attributeNames);
					accountCounter++;
				}
			}else {
				logger.warn("Ldap:: cannot fetch userAccount:"+userName+". Possible error in LDAP nodes configuration");
			}
		}
	}
	/**
	 * Gets specific data that can be synchronized with BPSS system
	 * @param userName
	 * @param countrySymbol
	 * @param attributeNames
	 */
	private void fetchUsersData(String userName, String countrySymbol,
			String[] attributeNames) {
		//logger.info("Fetching ldap user:"+userName);
		Attribute bitsAttribute = null;
		String bitsAttributeHexa="";
		LdapUserRecPojo rec = new LdapUserRecPojo();
		try {
			Attributes attrs = ctx.getAttributes(userName + "," + countrySymbol,attributeNames);
			
			bitsAttribute = attrs.get("userAccountControl");
			Attribute mailAttribute = attrs.get("mail");
			Attribute nameAttribute = attrs.get("name");
			Attribute telephoneAttribute = attrs.get("telephonenumber");
			Attribute accountAttribute = attrs.get("sAMAccountName");
			
			if (accountAttribute != null) {
				for (int i = 0; i < accountAttribute.size(); i++) {
					rec.setAccount((String) accountAttribute.get(i));
				}
			}

			if (bitsAttribute != null) {
				
				rec.checkIfAccountIsActive(bitsAttribute);
				
				long lng = Long.parseLong(bitsAttribute.get(0).toString());
				bitsAttributeHexa=Long.toHexString(lng);
				char res=bitsAttributeHexa.substring(bitsAttributeHexa.length()-3).charAt(0);
				if (res == '2') {
					rec.setEnabled(true);
				}
			}
			if (mailAttribute != null) {
				for (int i = 0; i < mailAttribute.size(); i++) {
					rec.setEmail((String) mailAttribute.get(i));
				}
			}
			if (nameAttribute != null) {
				for (int i = 0; i < nameAttribute.size(); i++) {
					rec.setName((String) nameAttribute.get(i));
				}
			}
			if (telephoneAttribute!= null) {
				for (int i = 0; i < telephoneAttribute.size(); i++) {
					rec.setTelephone((String) telephoneAttribute.get(i));
				}
			}
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
		
		if (rec.isEnabled()){
				ldapUsers.add(rec);
		}	
	}

	@Override
	public void getUserData() {
		initDirectoryContext(this.lconn.getUserAdmin(), this.lconn.getUserAdminPassw());
		try {
			createDirectoryContext();
				listSubContextTest(ctx, (lconn.getCountriesForSynchro().get(0)));
				logger.info("size:"+ldapUsers.size());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void listSubContextTest(DirContext ctx, String countrySymbol)
			throws NamingException {

		NamingEnumeration<?> contentsEnum = ctx.list(countrySymbol);
		
			NameClassPair ncp = (NameClassPair) contentsEnum.next();
			String userName = ncp.getName();
			Attributes attr1 = ctx.getAttributes(userName + "," + countrySymbol,
					new String[] { "objectcategory" });
			if (attr1.get("objectcategory").toString().indexOf("CN=Person") == -1) {
				// Recurse sub-contexts
				listSubContextTest(ctx, userName + "," + countrySymbol);
			} else {
				fetchUsersData(userName,countrySymbol,attributeNames);
			}
	}
	
}