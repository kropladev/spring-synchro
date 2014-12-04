package pl.nordea.synchro.utils;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class SynchroStatistics {
	@Autowired
	AppProps props;
	
	private static final Logger logger = LoggerFactory.getLogger(SynchroStatistics.class);
	private static DateTime startTime, finishTime, totalTime, lastTime, event1Time;
	private static int counterDisableDb=0;
	private static int counterDisableBPM=0;
	private static int counterDisableErrors=0;


	private static int counterActivateDb=0;
	private static int counterActivateBPM=0;
	private static int counterActivateErrors=0;
	
	private static int counterRebuildRolesDb=0;
	private static int counterRebuildRolesBPM=0;
	private static int counterRebuildRolesErrors=0;
	
	private static int counterChangeEmailDb=0;
	private static int counterChangeEmailBPM=0;
	private static int counterChangeEmailErrors=0;
	
	private static int counterChangeNameDb=0;
	private static int counterChangeNameBPM=0;
	private static int counterChangeNameErrors=0;
	
	private static int counterChangeOuDb=0;
	private static int counterChangeOuBPM=0;
	private static int counterChangeOuErrors=0;
	
	private static int counterCreateDb=0;
	private static int counterCreateBPM=0;
	private static int counterCreateErrors=0;
	private static int counterChangeUnitDb=0;
	private static int counterChangeUnitBPM=0;
	private static int counterChangeUnitErrors=0;
	private static int counterOtherErrors=0;
	

	
	
	
	public  void printStatistics(){
		setFinishTime(new DateTime());
		if(props.isDisableModuleActive()){
			logger.info("DISABLE process - users quantum taken from DB:"+counterDisableDb);
			logger.info("DISABLE process - users quantum processed in BPM directory :"+counterDisableBPM);
			logger.info("DISABLE process - errors:"+counterDisableErrors);
		}
		if(props.isActivateModuleActive()){
			logger.info("ACTIVATE process - users quantum taken from DB:"+counterActivateDb);
			logger.info("ACTIVATE process - users quantum processed in BPM directory :"+counterActivateBPM);
			logger.info("ACTIVATE process - errors:"+counterActivateErrors);
		}
		if(props.isRebuildRolesModuleActive()){
			logger.info("REBUILD_ROLES process - users quantum taken from DB:"+counterRebuildRolesDb);
			logger.info("REBUILD_ROLES process - users quantum processed in BPM directory :"+counterRebuildRolesBPM);
			logger.info("REBUILD_ROLES process - errors:"+counterRebuildRolesErrors);
		}
		if(props.isChangeEmailModuleActive()){
			logger.info("CHANGE_EMAIL process - users quantum taken from DB:"+counterChangeEmailDb);
			logger.info("CHANGE_EMAIL process - users quantum processed in BPM directory :"+counterChangeEmailBPM);
			logger.info("CHANGE_EMAIL process - errors:"+counterChangeEmailErrors);
		}
		if(props.isChangeNamesModuleActive()){
			logger.info("CHANGE_NAME process - users quantum taken from DB:"+counterChangeNameDb);
			logger.info("CHANGE_NAME process - users quantum processed in BPM directory :"+counterChangeNameBPM);
			logger.info("CHANGE_NAME process - errors:"+counterChangeNameErrors);
		}
		if(props.isChangeOrganizationUnitModuleActive()){
			logger.info("CHANGE_OU process - users quantum taken from DB:"+counterChangeOuDb);
			logger.info("CHANGE_OU process - users quantum processed in BPM directory :"+counterChangeOuBPM);
			logger.info("CHANGE_OU process - errors:"+counterChangeOuErrors);
		}
		if(props.isCreateModuleActive()){
			logger.info("CREATE process - users quantum taken from DB:"+counterCreateDb);
			logger.info("CREATE process - users quantum processed in BPM directory :"+counterCreateBPM);
			logger.info("CREATE process - errors:"+counterCreateErrors);
		}
		if(props.isChangeUnitModuleActive()){
			logger.info("CHANGE_UNIT process - users quantum taken from DB:"+counterChangeUnitDb);
			logger.info("CHANGE_UNIT process - users quantum processed in BPM directory :"+counterChangeUnitBPM);
			logger.info("CHANGE_UNIT process - errors:"+counterChangeUnitErrors);
		}
		logger.info("OTHER ERRORS    : "+counterOtherErrors);
		logger.info("Start time      : "+startTime.toString("yyy-MM-dd HH:mm:ss"));
		logger.info("Finish time     : "+finishTime.toString("yyy-MM-dd HH:mm:ss"));
		
	}

	public static void logTotalDuration(String prefix, String postfix) {
		setTotalTime(new DateTime());
		logger.info(prefix+" duration : "+totalTime.minus( startTime.getMillis()).minusHours(1).toString("HH:mm:ss")+postfix);
	}
	
	public static void logEventDuration(String prefix, String postfix) {
		logger.info(prefix+" duration : "+new DateTime().minus(lastTime.getMillis()).minusHours(1).toString("HH:mm:ss")+postfix);
		setLastTime(new DateTime());
	}

	public static void logEvent1Duration(String prefix, String postfix) {
		
		logger.info(prefix+" duration : "+new DateTime().minus( event1Time.getMillis()).minusHours(1).toString("HH:mm:ss")+postfix);
		setEvent1Time(new DateTime());
	}
	
	public static DateTime getEvent1Time() {
		return event1Time;
	}

	public static void setEvent1Time(DateTime event1Time) {
		SynchroStatistics.event1Time = event1Time;
	}


	public static DateTime getLastTime() {
		return lastTime;
	}

	public static void setLastTime(DateTime lastTime) {
		SynchroStatistics.lastTime = lastTime;
	}
	
	public static DateTime getStartTime() {
		return startTime;
	}


	public static void setStartTime(DateTime startTime) {
		SynchroStatistics.startTime = startTime;
	}


	public static DateTime getFinishTime() {
		return finishTime;
	}


	public static void setFinishTime(DateTime finishTime) {
		SynchroStatistics.finishTime = finishTime;
	}


	public static DateTime getTotalTime() {
		return totalTime;
	}


	public static void setTotalTime(DateTime totalTime) {
		SynchroStatistics.totalTime = totalTime;
	}

	public static void initDates() {
		setStartTime(new DateTime());
		setEvent1Time(new DateTime());
		setLastTime(new DateTime());
	}

	public static void incrementCounterDisableBPM() {
		SynchroStatistics.counterDisableBPM++;
	}

	public static void incrementCounterOtherErrors() {
		SynchroStatistics.counterOtherErrors++;
	}

	public static void incrementCounterChangeEmailBPM() {
		SynchroStatistics.counterChangeEmailBPM++;
	}

	public static void incrementCounterChangeOuBPM() {
		SynchroStatistics.counterChangeOuBPM++;
	}

	public static void incrementCounterChangeNameBPM() {
		SynchroStatistics.counterChangeNameBPM++;
	}

	public static void incrementCounterActivateBPM() {
		SynchroStatistics.counterActivateBPM++;
	}

	public static void incrementCounterRebuildRolesErrors() {
		SynchroStatistics.counterRebuildRolesErrors++;
	}

	public static void incrementCounterRebuildRolesBPM() {
		SynchroStatistics.counterRebuildRolesBPM++;
	}

	public static void setCounterDisableDb(int counter) {
		SynchroStatistics.counterDisableDb=counter;
		
	}

	public static void setCounterActivateDb(int counter) {
		SynchroStatistics.counterActivateDb=counter;
		
	}

	public static void setCounterChangeNameDb(int counter) {
		SynchroStatistics.counterChangeNameDb=counter;
	}

	public static void setCounterChangeOuDb(int counter) {
		SynchroStatistics.counterChangeOuDb=counter;
	}

	public static void setCounterChangeEmailDb(int counter) {
		SynchroStatistics.counterChangeEmailDb=counter;
	}

	public static void setCounterRebuildRolesDb(int counter) {
		SynchroStatistics.counterRebuildRolesDb=counter;
	}

	public static void setCounterCreateDb(int counter) {
		SynchroStatistics.counterCreateDb=counter;
	}
}
