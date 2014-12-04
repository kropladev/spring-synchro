package pl.nordea.synchro.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pl.nordea.synchro.directory.IDirectoryActions;
import pl.nordea.synchro.directory.utils.UtilOrganisationUnit;
import pl.nordea.synchro.ldap.ILdapService;
import pl.nordea.synchro.user.IUsersService;
import pl.nordea.synchro.utils.AppProps;
import pl.nordea.synchro.utils.SynchroStatistics;

/**
 * actions triggered by BPSS synchronization process
 * 
 * @author m010678
 * 
 */

@Component
@Scope("prototype")
public class SynchroActionsImpl implements ISynchroActions {
	private static final Logger logger = LoggerFactory.getLogger(SynchroActionsImpl.class);
	@Autowired
	private IUsersService userCollectionForSynchro;
	@Autowired
	private ILdapService ldapActions;
	@Autowired
	private IDirectoryActions dirActions;
	@Autowired
	private AppProps props;
	//TODO check if it can be changed AppProps to interface class IAppPropsService
	
	
	@Override
	public void startSynchronisationProcess() {
		try {
			props.loadProperties();
			logger.info("AppProps::"+props.toString());
			SynchroStatistics.initDates();
			
			//SynchroStatistics.toggleAllStatistics(true);
			if(props.isImportLdapDataModuleActive()) {
				ldapActions.synchronizeLdapDataWithBpssAdminSchema();
				SynchroStatistics.logEventDuration("Synchronize LDAP data","");
			}
						
			for (int countryId: returnCuntriesIdForSynchro()) {
				userCollectionForSynchro.collectUsersForSynchroFromCoreSystem(countryId);
				SynchroStatistics.logEventDuration("Collect Users Data from Core system for country:"+UtilOrganisationUnit.values()[countryId-1].toString(),"");
			}
			
			/** Connect to BPM engine and perform collected changes on BPM directory **/
			dirActions.performChangesOnDirectory();
			
			/** flush users list after synchro **/
			userCollectionForSynchro.flushUsersCollection();
			SynchroStatistics.logEventDuration("Changes made","");
			//SynchroStatistics.printStatistics();
			SynchroStatistics.logTotalDuration("Total","");
		}catch (NullPointerException e) {
			logger.info(" NullPointerException:: "+e.getMessage());
		}catch (NumberFormatException e) {
			logger.info(" NumberFormatException:: "+e.getMessage());
		}catch(Exception e) {
			logger.info(" Exception:: "+e.getMessage());
		}
	}
	
	@Override
	public void startSynchronisationProcess(int countryId) {
		try {
			props.loadProperties();
			logger.info("AppProps::"+props.toString());
			SynchroStatistics.initDates();
			
			//SynchroStatistics.toggleAllStatistics(true);
			if(props.isImportLdapDataModuleActive()) {
				ldapActions.synchronizeLdapDataWithBpssAdminSchema();
				SynchroStatistics.logEventDuration("Synchronize LDAP data","");
			}
		
				userCollectionForSynchro.collectUsersForSynchroFromCoreSystem(countryId);
				SynchroStatistics.logEventDuration("Collect Users Data from Core system for country:"+UtilOrganisationUnit.values()[countryId-1].toString(),"");
			
			/** Connect to BPM engine and perform collected changes on BPM directory **/
			dirActions.performChangesOnDirectory();
			
			/** flush users list after synchro **/
			userCollectionForSynchro.flushUsersCollection();
			SynchroStatistics.logEventDuration("Changes made","");
			//SynchroStatistics.printStatistics();
			SynchroStatistics.logTotalDuration("Total","");
		}catch (NullPointerException e) {
			logger.info(" NullPointerException:: "+e.getMessage());
		}catch (NumberFormatException e) {
			logger.info(" NumberFormatException:: "+e.getMessage());
		}catch(Exception e) {
			logger.info(" Exception:: "+e.getMessage());
		}
	}

	@Override
	public void checkConnection() {
		userCollectionForSynchro.collectUsersForSynchroFromCoreSystem();
		
	}

	@Override
	public void checkLdap() {
		ldapActions.checkConnection();
	}
	
	private List<Integer> returnCuntriesIdForSynchro() throws NullPointerException, NumberFormatException{
			List<Integer> countryIds=new ArrayList<Integer>();
			String countryIdSymbol= props.getProperty("synchro.country.list");
			if (countryIdSymbol==null) {
				throw new NullPointerException(" synchro.country.list property wasn't found!!");
			}
			String[] tmpTab=countryIdSymbol.split(",");
			for(int i=0; i<tmpTab.length; i++) {
				try {
				countryIds.add(Integer.valueOf( tmpTab[i]));
				}catch (NumberFormatException e) {
					throw new NumberFormatException(" Parameter value "+ tmpTab[i]+"format is incorrect to parse it to int.");
				}
			}
		return countryIds; 
	}
}
