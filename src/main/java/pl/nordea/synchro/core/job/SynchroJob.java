package pl.nordea.synchro.core.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class SynchroJob extends QuartzJobBean {
	@Autowired
	private ISynchroWork worker;

	/**
	 * triggered automatically by Quartz trigger defined in quartz-job.xml loaded by spring context
	 */
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		worker.runSynchro();
	}
}
