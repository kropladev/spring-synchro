package pl.nordea.synchro.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchroStatistics {
	private static final Logger logger = LoggerFactory.getLogger(SynchroStatistics.class);
	public static int counterDisableDb=0;
	public static int counterDisableBPM=0;
	public static int counterDisableErrors=0;

	public static int counterActivateDb=0;
	public static int counterActivateBPM=0;
	public static int counterActivateErrors=0;
	
	public static int counterRebuildRolesDb=0;
	public static int counterRebuildRolesBPM=0;
	public static int counterRebuildRolesErrors=0;
	
	public static int counterChangeEmailDb=0;
	public static int counterChangeEmailBPM=0;
	public static int counterChangeEmailErrors=0;
	
	public static int counterChangeNameDb=0;
	public static int counterChangeNameBPM=0;
	public static int counterChangeNameErrors=0;
	
	public static int counterChangeOuDb=0;
	public static int counterChangeOuBPM=0;
	public static int counterChangeOuErrors=0;
	
	public static int counterCreateDb=0;
	public static int counterCreateBPM=0;
	public static int counterCreateErrors=0;
	
	public static int counterChangeUnitDb=0;
	public static int counterChangeUnitBPM=0;
	public static int counterChangeUnitErrors=0;

	public static int counterOtherErrors=0;
	
	private static boolean disableModuleActive=false;
	private static boolean activateModuleActive=false;
	private static boolean createModuleActive=false;
	private static boolean changeNamesModuleActive=false;
	private static boolean changeOrganizationUnitModuleActive=false;
	private static boolean changeUnitModuleActive=false;
	private static boolean changeEmailModuleActive=false;
	private static boolean rebuildRolesModuleActive=false;
	
	
	
	public static void printStatistics(){
		if(disableModuleActive){
			logger.info("DISABLE process - users quantum taken from DB:"+counterDisableDb);
			logger.info("DISABLE process - users quantum processed in BPM directory :"+counterDisableBPM);
			logger.info("DISABLE process - errors:"+counterDisableErrors);
		}
		if(activateModuleActive){
			logger.info("ACTIVATE process - users quantum taken from DB:"+counterDisableDb);
			logger.info("ACTIVATE process - users quantum processed in BPM directory :"+counterDisableBPM);
			logger.info("ACTIVATE process - errors:"+counterDisableErrors);
		}
		if(rebuildRolesModuleActive){
			logger.info("REBUILD_ROLES process - users quantum taken from DB:"+counterDisableDb);
			logger.info("REBUILD_ROLES process - users quantum processed in BPM directory :"+counterDisableBPM);
			logger.info("REBUILD_ROLES process - errors:"+counterDisableErrors);
		}
		if(changeEmailModuleActive){
			logger.info("CHANGE_EMAIL process - users quantum taken from DB:"+counterDisableDb);
			logger.info("CHANGE_EMAIL process - users quantum processed in BPM directory :"+counterDisableBPM);
			logger.info("CHANGE_EMAIL process - errors:"+counterDisableErrors);
		}
		if(changeNamesModuleActive){
			logger.info("CHANGE_NAME process - users quantum taken from DB:"+counterDisableDb);
			logger.info("CHANGE_NAME process - users quantum processed in BPM directory :"+counterDisableBPM);
			logger.info("CHANGE_NAME process - errors:"+counterDisableErrors);
		}
		if(changeOrganizationUnitModuleActive){
			logger.info("CHANGE_OU process - users quantum taken from DB:"+counterDisableDb);
			logger.info("CHANGE_OU process - users quantum processed in BPM directory :"+counterDisableBPM);
			logger.info("CHANGE_OU process - errors:"+counterDisableErrors);
		}
		if(createModuleActive){
			logger.info("CREATE process - users quantum taken from DB:"+counterDisableDb);
			logger.info("CREATE process - users quantum processed in BPM directory :"+counterDisableBPM);
			logger.info("CREATE process - errors:"+counterDisableErrors);
		}
		if(changeUnitModuleActive){
			logger.info("CHANGE_UNIT process - users quantum taken from DB:"+counterDisableDb);
			logger.info("CHANGE_UNIT process - users quantum processed in BPM directory :"+counterDisableBPM);
			logger.info("CHANGE_UNIT process - errors:"+counterDisableErrors);
		}
		logger.info("OTHER ERRORS:"+counterOtherErrors);
		
	}


	public boolean isDisableModuleActive() {
		return disableModuleActive;
	}


	public static void setDisableModuleActive(boolean disableModuleActive) {
		SynchroStatistics.disableModuleActive = disableModuleActive;
	}


	public boolean isActivateModuleActive() {
		return activateModuleActive;
	}


	public static void setActivateModuleActive(boolean activateModuleActive) {
		SynchroStatistics.activateModuleActive = activateModuleActive;
	}


	public boolean isCreateModuleActive() {
		return createModuleActive;
	}


	public static void setCreateModuleActive(boolean createModuleActive) {
		SynchroStatistics.createModuleActive = createModuleActive;
	}


	public boolean isChangeNamesModuleActive() {
		return changeNamesModuleActive;
	}


	public static void setChangeNamesModuleActive(boolean changeNamesModuleActive) {
		SynchroStatistics.changeNamesModuleActive = changeNamesModuleActive;
	}


	public boolean isChangeOrganizationUnitModuleActive() {
		return changeOrganizationUnitModuleActive;
	}


	public static void setChangeOrganizationUnitModuleActive(
			boolean changeOrganizationUnitModuleActive) {
		SynchroStatistics.changeOrganizationUnitModuleActive = changeOrganizationUnitModuleActive;
	}


	public boolean isChangeUnitModuleActive() {
		return changeUnitModuleActive;
	}


	public static void setChangeUnitModuleActive(boolean changeUnitModuleActive) {
		SynchroStatistics.changeUnitModuleActive = changeUnitModuleActive;
	}


	public boolean isChangeEmailModuleActive() {
		return changeEmailModuleActive;
	}


	public static void setChangeEmailModuleActive(boolean changeEmailModuleActive) {
		SynchroStatistics.changeEmailModuleActive = changeEmailModuleActive;
	}


	public boolean isRebuildRolesModuleActive() {
		return rebuildRolesModuleActive;
	}


	public static void setRebuildRolesModuleActive(boolean rebuildRolesModuleActive) {
		SynchroStatistics.rebuildRolesModuleActive = rebuildRolesModuleActive;
	}
	
	public static void toggleAllStatistics(boolean val){
		setChangeEmailModuleActive(val);
		setActivateModuleActive(val);
		setChangeNamesModuleActive(val);
		setChangeOrganizationUnitModuleActive(val);
		setChangeUnitModuleActive(val);
		setCreateModuleActive(val);
		setDisableModuleActive(val);
		setRebuildRolesModuleActive(val);
	}
}
