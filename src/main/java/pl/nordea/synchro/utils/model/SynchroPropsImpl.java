package pl.nordea.synchro.utils.model;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.nordea.synchro.utils.model.enties.SynchroProperties;

@Repository
public class SynchroPropsImpl implements ISynchroPropertiesDao{

	@Autowired
	private SessionFactory session;
	
	@Override
	public void add(SynchroProperties props) {
		session.getCurrentSession().save(props);
	}

	@Override
	public void edit(SynchroProperties props) {
		session.getCurrentSession().update(props);
	}

	@Override
	public void delete(int propsId) {
		session.getCurrentSession().delete(getSynchroProperties(propsId));
		
	}

	@Override
	public SynchroProperties getSynchroProperties(int propsId) {
		return (SynchroProperties) session.getCurrentSession().get(SynchroProperties.class, propsId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SynchroProperties> getAllSynchroProperties(String appName) {
		return session.getCurrentSession().createQuery("from SynchroProperties where target='APP' and paramName=:paramAppName").setParameter("paramAppName", appName).list();
	}
}
