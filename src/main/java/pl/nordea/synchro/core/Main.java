package pl.nordea.synchro.core;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import pl.nordea.synchro.core.job.ISynchroWork;

@Service("taskBean")
public class Main implements ISynchroWork, Serializable{
	private static final Logger logger = LoggerFactory.getLogger( Main.class );
	
	@Autowired
	ISynchroActions synchroActions;
	
	 /**
	 * automatic generated serial id 
	 */
	private static final long serialVersionUID = 5197115054535637077L;
	
	@Scheduled(fixedDelay=12000)
	public void callSynchro() {
		System.out.println("SYNCHRO");
	}
	
	
	 @Override
	 public void runSynchro() {
		 logger.info("SYNCHRO START");
		
		//ApplicationContext context = //new ClassPathXmlApplicationContext("/spring/application-context.xml");

		synchroActions.startSynchronisationProcess();
		logger.info("SYNCHRO END");
	 };
}