package pl.nordea.synchro.utils;

import java.util.Properties;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class AppProps implements IAppPropsService {
	private static final Logger logger=LoggerFactory.getLogger(AppProps.class);
	public static final String SYNCHRO_CONFIG = "SYNCHRO";
	private static final String BASE_PROPS=
			"ldap.controller,ldap.loginDomain,ldap.contextsSynchro,ldap.user,module.ldapImport,module.email,module.name,module.country," +
			"module.roles,module.disable,module.activate,module.unitChange,module.create,dir.directoryId,dir.directoryPreset,ldap.passw";
	
	@Autowired
	private SynchroPropsService synchroPropsService;
	
	@Autowired
	private CronTriggerFactoryBean synchroCronTrigger;
	
	@Autowired
	private SchedulerFactoryBean quartSchedulerBean;
	
	private Properties props;
	
	public void setProps(Properties props) {
		this.props=props;
	}
	
	public void loadProperties() throws Exception {
		logger.info("AppPorps::::::::::::loadProps");
		props=null;
		props= synchroPropsService.loadProperties(SYNCHRO_CONFIG);
		//reasignQuartzCronPeriod();
		checkBaseProperties();
	}
	
	/**
	 * Checks if all needed (base) config values was loaded from Synchro_config table
	 * @throws Exception if single property is missing
	 */
	private void checkBaseProperties() throws Exception {
		for(String propName:BASE_PROPS.split(",")) {
			if (getProperty(propName)==null) {
				throw new Exception("One of the base property definition is missing. Prop::"+propName);
			}
			
		}
	}

	public String getPropValue(String propertyName) {
		return props.getProperty(propertyName);
	}
	
	//TODO check if method should be redefined to private
	/**
	 * Get single property from Properties loaded in method @loadProperties
	 */
	public String getProperty(String propertyName) {
		return props.getProperty(propertyName);
	}
	
	@SuppressWarnings("unused")
	private void reasignQuartzCronPeriod() {
		logger.info("QUARTZ:::: "+getProperty("quartz.cronExpression"));
		synchroCronTrigger.setCronExpression(getProperty("quartz.cronExpression"));
		try {
			quartSchedulerBean.stop();
			quartSchedulerBean.getScheduler().rescheduleJob(synchroCronTrigger.getObject().getKey(), synchroCronTrigger.getObject());
			quartSchedulerBean.start();
			logger.info("Reschedulling Jobs");
		} catch (SchedulerException e) {
			logger.error("Reschedulling Jobs error");
			e.printStackTrace();
		}
	}
	
	public boolean isDisableModuleActive() {
		return Boolean.valueOf(getPropValue("module.disable"));
	}

	public boolean isActivateModuleActive() {
		return Boolean.valueOf(getPropValue("module.activate"));	
	}

	public boolean isCreateModuleActive() {
		return Boolean.valueOf(getPropValue("module.create"));
	}

	public boolean isChangeNamesModuleActive() {
		return Boolean.valueOf(getPropValue("module.name"));
	}

	public boolean isChangeOrganizationUnitModuleActive() {
		return Boolean.valueOf(getPropValue("module.country"));
	}

	public boolean isChangeUnitModuleActive() {
		return Boolean.valueOf(getPropValue("module.unitChange"));
	}

	public boolean isChangeEmailModuleActive() {
		String prop=getPropValue("module.email");
		logger.info("mail module value:"+prop);
		return Boolean.valueOf(prop);
	}
	
	public boolean isChangeEmailModuleActive(Properties prop) {
		return Boolean.valueOf(prop.getProperty("mail"));
	}

	public boolean isRebuildRolesModuleActive() {
		return Boolean.valueOf(getPropValue("module.roles"));
	}

	public boolean isImportLdapDataModuleActive() {
		return Boolean.valueOf(getPropValue("module.ldapImport"));
	}
	
	@Override
	public String toString(){
		return "Synchronisation properties: " +
				props.toString();
	}
}
