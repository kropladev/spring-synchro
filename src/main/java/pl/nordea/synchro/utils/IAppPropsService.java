package pl.nordea.synchro.utils;

public interface IAppPropsService {
	void loadProperties() throws Exception;
	String getPropValue(String propertyName);
	String getProperty(String propertyName) ;
	boolean isDisableModuleActive();
	boolean isActivateModuleActive();
	boolean isCreateModuleActive();
	boolean isChangeNamesModuleActive();
	boolean isChangeOrganizationUnitModuleActive() ;
	boolean isChangeUnitModuleActive();
	boolean isChangeEmailModuleActive() ;
	boolean isRebuildRolesModuleActive();
	boolean isImportLdapDataModuleActive();
}
