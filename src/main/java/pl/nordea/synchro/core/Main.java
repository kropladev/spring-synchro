package pl.nordea.synchro.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/application-context.xml");
		SynchroActions synchro= context.getBean(SynchroActions.class);
		synchro.startSynchronisationProcess();
	}	
}