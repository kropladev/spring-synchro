package pl.nordea.synchro.utils.model;

import java.util.List;
import pl.nordea.synchro.utils.model.enties.SynchroProperties;


public interface ISynchroPropertiesDao {
	public void add(SynchroProperties props);
	public void edit(SynchroProperties props);
	public void delete(int propsId);
	public SynchroProperties getSynchroProperties(int propsId);
	public List<SynchroProperties> getAllSynchroProperties(String appName);
}
