package pl.nordea.synchro.utils;

import java.util.List;
import java.util.Properties;

import pl.nordea.synchro.utils.model.enties.SynchroProperties;

public interface SynchroPropsService {
	public void add(SynchroProperties props);
	public void edit(SynchroProperties props);
	public void delete(int propsId);
	public SynchroProperties getSynchroProperties(int propsId);
	public List<SynchroProperties> getAllSynchroProperties(String appName);
	public Properties loadProperties(String appName);
}
