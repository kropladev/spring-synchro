package pl.nordea.synchro.directory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import pl.nordea.synchro.config.OrganisationUnit;
import pl.nordea.synchro.utils.ExceptionFormatter;
import pl.nordea.synchro.utils.SynchroStatistics;

import fuego.directory.DirHumanParticipant;
import fuego.directory.DirectoryException;


//@Service
//@Scope("prototype")
public class ParticipantBpm  implements IParticipant{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	//@Autowired
	//private IDirectorySession session;
	
	
	DirHumanParticipant participant;
	
	public ParticipantBpm(){
		
	}
	/**
	 * constructor creating Participant object
	 * @param userCode
	 */
	public ParticipantBpm(String userCode, IDirectorySession session) {
		//super();
		try {
			logger.debug("Participant constructor; UserCode:"+userCode);
			this.participant = DirHumanParticipant.fetch(session.handleSession(), userCode);
		} catch (DirectoryException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void disable() {
		if(participant.canBeDisabled() && participant.isEnabled()){
			this.participant.setEnabled(false);
			SynchroStatistics.counterDisableBPM++;
			logger.info("Participant:["+this.participant.getId()+"], Action:DISABLE");
		}
	}

	@Override
	public void activate() {
		if(!participant.isEnabled()){
			this.participant.setEnabled(true);
			SynchroStatistics.counterActivateBPM++;
			logger.info("Participant:["+this.participant.getId()+"], Action:ACTIVATE");
		}
	}

	@Override
	public void rebuildRoles() {
		// TODO Auto-generated method stub
		SynchroStatistics.counterRebuildRolesBPM++;
		logger.info("Participant:["+this.participant.getId()+"], Action:REBUILD_ROLES");
		
	}

	@Override
	public void changeName(String userName) {
		if(this.participant.canBeModified()){
			logger.info("Participant:["+this.participant.getId()+"], Action:CHANGE_NAME, from value:["+this.participant.getDisplayName()+"] to value:["+userName+"]");
			this.participant.setDisplayName(userName);
			this.participant.setLastName(userName);
			SynchroStatistics.counterChangeNameBPM++;
			//logger.info("Participant:["+this.participant.getEmployeeId()+"], Action:CHANGE_NAME");
		}
	}

	@Override
	public void changeCountry(int countryId) {
		if(this.participant.canBeModified()){
			//this.participant.setOU(ou.toString());
			SynchroStatistics.counterChangeOuBPM++;
			logger.info("Participant:["+this.participant.getId()+"], Action:CHANGE_COUNTRY");
		}
	}

	@Override
	public void changeEmail(String userEmail) {
		if(this.participant.canBeModified()){
			this.participant.setMail(userEmail);
			SynchroStatistics.counterChangeEmailBPM++;
			logger.info("Participant:["+this.participant.getId()+"], Action:CHANGE_EMAIL");
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
			SynchroStatistics.counterOtherErrors++;
		}
	}

	@Override
	public void create() {
		logger.info("Participant:["+this.participant.getId()+"], Action:CREATE");
	}
}
