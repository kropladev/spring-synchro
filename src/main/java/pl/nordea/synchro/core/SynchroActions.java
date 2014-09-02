package pl.nordea.synchro.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.nordea.synchro.user.IUsersCollectionWithModSym;
import pl.nordea.synchro.utils.SynchroStatistics;

/**
 * actions triggered by BPSS synchronization process
 * 
 * @author m010678
 * 
 */

@Component
public class SynchroActions implements ISynchroActions {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IUsersCollectionWithModSym userCollectionForSynchro;

	@Override
	public void startSynchronisationProcess() {
		SynchroStatistics.toggleAllStatistics(true);
		userCollectionForSynchro.collectUsersForSynchroFromCoreSystem();
		this.performParticipantsChanges();

	}

	private void performParticipantsChanges() {
		userCollectionForSynchro.performChangesOnDirectory();
		logger.debug("Synchronization process is finished");
		SynchroStatistics.printStatistics();
		
	}
}
