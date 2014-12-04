package pl.nordea.synchro.directory.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import pl.nordea.synchro.directory.IDirSessionService;
import pl.nordea.synchro.directory.IRolesRebuildService;
import pl.nordea.synchro.directory.utils.UtilOrganisationUnit;
import pl.nordea.synchro.directory.utils.UtilParticipantTimeZone;
import pl.nordea.synchro.user.model.enties.PojoUser;
import pl.nordea.synchro.utils.ExceptionFormatter;
import pl.nordea.synchro.utils.SynchroStatistics;

import fuego.directory.DirHumanParticipant;
import fuego.directory.DirHumanParticipant.RoleAssignment;
import fuego.directory.DirOrganizationalUnit;
import fuego.directory.DirectoryException;
import java.util.Properties;

@Repository
@Scope("prototype")
public class ImplDirParticipant  implements IDirParticipantDao{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final static String PROPERTY_CATEGORY="WAM";
	private final static String PROPERTY_KEY="OPTIONS";
	private DirHumanParticipant participant;
	private int countryId;
	
	@Autowired
	private IDirSessionService session;
	@Autowired
	private IRolesRebuildService userRoles;

	@Override
	public void fetchParticipantBpm(String userCode){
		
		try {
			logger.debug("Participant constructor; UserCode:"+userCode);
			this.participant = DirHumanParticipant.fetch(session.handleSession(), userCode);
		} catch (DirectoryException e) {
			logger.error("Can not fetch particiant:["+userCode+"]; exc:"+e.getMessage());
		}
	}

	
	@Override
	public void disable() {
		if(participant.canBeDisabled() && participant.isEnabled()){
			this.participant.setEnabled(false);
			SynchroStatistics.incrementCounterDisableBPM();
			logger.info("Participant:["+this.participant.getId()+"], Action:DISABLE");
		}
	}

	@Override
	public void activate() {
		if(!participant.isEnabled()){
			this.participant.setEnabled(true);
			SynchroStatistics.incrementCounterActivateBPM();
			logger.info("Participant:["+this.participant.getId()+"], Action:ACTIVATE");
		}
	}

	@Override
	public void rebuildRoles() {
		logger.info("Participant:["+this.participant.getId()+"], Action:REBUILD_ROLES START");
		userRoles.rebuildUserRoles(this.participant);
		logger.info("Participant:["+this.participant.getId()+"], Action:REBUILD_ROLES END");	
	}

	@Override
	public void changeName(String userName) {
		if(this.participant.canBeModified()){
			logger.info("Participant:["+this.participant.getId()+"], Action:CHANGE_NAME, from value:["+this.participant.getDisplayName()+"] to value:["+userName+"]");
			this.participant.setDisplayName(userName);
			this.participant.setLastName(userName);
			SynchroStatistics.incrementCounterChangeNameBPM();
		}
	}

	@Override
	public void changeCountry(int countryId) {
		if(this.participant.canBeModified()){
			String oldOuString=this.participant.getOU();
			this.participant.setOU(transformProperOU(countryId));
			String newOuString=this.participant.getOU();
			SynchroStatistics.incrementCounterChangeOuBPM();
			logger.info("Participant:["+this.participant.getId()+"], Action:CHANGE_COUNTRY, from::"+oldOuString+" to::"+newOuString);
		}
	}


	@Override
	public void changeEmail(String userEmail) {
		if(this.participant.canBeModified()){
			logger.info("Changing email address user["+participant.getId()+"] from:"+this.participant.getMail()+"] to:["+userEmail+"]");
			this.participant.setMail(userEmail);
			SynchroStatistics.incrementCounterChangeEmailBPM();
			logger.debug("Participant:["+this.participant.getId()+"], Action:CHANGE_EMAIL");
		}
	}
	
	@Override
	public void store(){
		try {
			this.participant.update();
			logger.info("Storing participant:["+this.participant.getId()+"] data");
		} catch (DirectoryException e) {
			logger.error("Exception during storing data of participant:["+this.participant.getId()+"]");
			logger.error(ExceptionFormatter.printError(e));
			SynchroStatistics.incrementCounterOtherErrors();
		}
	}

	@Override
	public void create(PojoUser user) {
		logger.info("Creating new account user["+user.getUserCode()+"] for country:["+user.getUserCountryId()+"] mail:["+user.getUserEmail()+"] name:["+user.getUserName()+"]");
		
		//transform user name from concatenation of strings '<user name>|<user Mail>' taken from db view
		transformUserName(user);
		
		try {
			this.participant=DirHumanParticipant.create(
						session.handleSession(), 
						user.getUserCode(),//id 
						"",//firstName, 
						user.getUserName(),//lastName, 
						user.getUserName(),//displayName, 
						user.getUserEmail(),//mail, 
						"",//telephone, 
						"",//fax, 
						takeUserPassword(user.getUserCode()),//password 
						DirOrganizationalUnit.fetch(session.handleSession(), user.getoU().toString()),//ou, 
						new RoleAssignment[] {},//rolesAssignment, 
						true//enabled
						);
			
			this.participant.changeLocale(takeUserLocale(user.getUserCountryId()));
			this.participant.changeTimezone(takeUserTimeZone(user.getUserCountryId()));
			
			try {
				String optionProp =(String)this.participant.retrieveProperty(PROPERTY_CATEGORY, PROPERTY_KEY);
				ByteArrayOutputStream outString =new ByteArrayOutputStream();
				ByteArrayInputStream inString= null;
				Properties props= new Properties();
				
				if(optionProp!=null && optionProp.length()>0) {
					inString=new ByteArrayInputStream(optionProp.getBytes());
						props.load(inString);
				}
				
				props.setProperty("fuego.useroptions.userLocalLanguage", takeUserLocale(user.getUserCountryId()).toString());
				props.setProperty("fuego.useroptions.userTimeZoneID",  takeUserTimeZone(user.getUserCountryId()).getID()); 
				props.store( outString, "fuego.useroptions"); 
				
				this.participant.storeProperty(PROPERTY_CATEGORY,PROPERTY_KEY, outString.toString());
				userRoles.rebuildUserRoles(this.participant);
				
			} catch (IOException e) {
				logger.error("Can not create new particiant:["+user.getUserCode()+"]; exc:"+e.toString());
			}
			
		} catch (DirectoryException e) {
			logger.error("Can not create new particiant - directory exception:["+user.getUserCode()+"]; exc:"+e.toString());
		}
		
		logger.info("Participant:["+this.participant.getId()+"], Action:CREATE");
	}
	
	/**
	 * transform user name and email address; transform user name from concatenation of strings 'N:<user name>|E:<user Mail>' taken from db view;
	 * The record from the V_SYNCHRO_ACTIONS is an concatenation of the user name and user email address - it is only for CREATE action
	 * @param user
	 */
	public void transformUserName(PojoUser user) {
		String[] data=user.getUserName().split("\\|");
		if (data.length==2) {
			String[] nameDataStrings=data[0].split(":");
			String[] emailDataStrings=data[1].split(":");

			if(nameDataStrings.length==2) {
				user.setUserName(nameDataStrings[1]);
			}else {
				user.setUserName("");
			}
			
			if (emailDataStrings.length==2) {
				user.setUserEmail(emailDataStrings[1]);
			}else {
				user.setUserEmail("");
			}	
		}
	}
	
	/**
	 * Gets timezone for specific user based on Country id parameter
	 * @param userCountryId
	 * @return
	 */
	public TimeZone takeUserTimeZone(int userCountryId) {
		return TimeZone.getTimeZone(findProperTimeZone(userCountryId));
	}
	
	public String findProperTimeZone(int userCountryId) {
		return UtilParticipantTimeZone.values()[userCountryId].toString();
	}
	
	public String transformProperOU(int countryId) {
		return UtilOrganisationUnit.values()[countryId-1].toString();
	}
	
	/**
	 * Getter for the participant locale. 
	 * If user is from PL (countryId==1) and Polish locale is installed than return PL locale. 
	 * Other way return default EN. 
	 * @param userCountryId
	 * @return
	 */
	public Locale takeUserLocale(int userCountryId) {
		if(userCountryId==1 ) {
			for(Locale loc:Locale.getAvailableLocales()) {
				if(loc.toString().equalsIgnoreCase("pl")){
					return loc;
				}
			}
		}
		return Locale.ENGLISH;
	}
	
	/**
	 * Setter for the user password. Returns transformed password.
	 * @param userCode
	 * @return
	 */
	private String takeUserPassword(String userCode) {
		return userCode+"01";
	}
	public int getCountryId() {
		return countryId;
	}
	@Override
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	@Override
	public String testPrint() {
		if (participant!=null) {
			return this.participant.getDisplayName();
		}
		return null;
	}
	
	public String getParticipantName() {
		return this.participant.getDisplayName();
	}
}
