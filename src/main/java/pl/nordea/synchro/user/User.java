package pl.nordea.synchro.user;

import pl.nordea.synchro.config.OrganisationUnit;
import pl.nordea.synchro.config.SynchroActionEnum;
import pl.nordea.synchro.directory.IParticipant;

/**
 * @author m010678
 *
 */
public class User {

	IParticipant participantBpm;

	private String userCode;
	private int userCountryId;
	private OrganisationUnit oU;
	private String actionToDo;
	private String userName;
	private String userEmail;

	private boolean flagDisable = false;
	private boolean flagEnable = false;
	private boolean flagChangeName = false;
	private boolean flagChangeEmail = false;
	private boolean flagChangeCountry = false;
	private boolean flagChangeRoles = false;

	public void flaggSpecificAction( SynchroActionEnum action) {
		switch (action) {
		case DISABLE: 		this.setFlagDisable(true); break;
		case ACTIVATE: 		this.setFlagEnable(true); break;
		case CH_COUNTRY: 	this.setFlagChangeCountry(true);break;
		case CH_EMAIL: 		this.setFlagChangeEmail(true);break;
		case CH_NAME: 		this.setFlagChangeName(true);break;
		case CH_ROLE: 		this.setFlagChangeRoles(true); break;
		default: 			break;
		}
	}

	public IParticipant getParticipantBpm() {
		return participantBpm;
	}

	public void setParticipantBpm(IParticipant participantBpm) {
		this.participantBpm = participantBpm;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public int getUserCountryId() {
		return userCountryId;
	}

	public void setUserCountryId(int userCountryId) {
		this.userCountryId = userCountryId;
	}

	public String getActionToDo() {
		return actionToDo;
	}

	public void setActionToDo(String actionToDo) {
		this.actionToDo = actionToDo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public boolean isFlagDisable() {
		return flagDisable;
	}

	public void setFlagDisable(boolean flagDisable) {
		this.flagDisable = flagDisable;
	}

	public boolean isFlagEnable() {
		return flagEnable;
	}

	public void setFlagEnable(boolean flagEnable) {
		this.flagEnable = flagEnable;
	}

	public boolean isFlagChangeName() {
		return flagChangeName;
	}

	public void setFlagChangeName(boolean flagChangeName) {
		this.flagChangeName = flagChangeName;
	}

	public boolean isFlagChangeEmail() {
		return flagChangeEmail;
	}

	public void setFlagChangeEmail(boolean flagChangeEmail) {
		this.flagChangeEmail = flagChangeEmail;
	}

	public boolean isFlagChangeCountry() {
		return flagChangeCountry;
	}

	public void setFlagChangeCountry(boolean flagChangeCountry) {
		this.flagChangeCountry = flagChangeCountry;
	}

	public boolean isFlagChangeRoles() {
		return flagChangeRoles;
	}

	public void setFlagChangeRoles(boolean flagChangeRoles) {
		this.flagChangeRoles = flagChangeRoles;
	}

	public OrganisationUnit getoU() {
		return oU;
	}

	public void setoU(OrganisationUnit oU) {
		this.oU = oU;
	}

	@Override
	public String toString() {
		return "User [userCode=" + userCode + ", userCountryId="
				+ userCountryId + ", actionToDo=" + actionToDo + ", userName="
				+ userName + ", userEmail=" + userEmail + ", flagDisable="
				+ flagDisable + ", flagEnable=" + flagEnable
				+ ", flagChangeName=" + flagChangeName + ", flagChangeEmail="
				+ flagChangeEmail + ", flagChangeCountry=" + flagChangeCountry
				+ ", flagChangeRoles=" + flagChangeRoles + "]";
	}

}
