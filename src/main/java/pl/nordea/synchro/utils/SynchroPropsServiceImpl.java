package pl.nordea.synchro.utils;

import static pl.nordea.synchro.utils.PasswordWallet.*;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.nordea.synchro.utils.model.ISynchroPropertiesDao;
import pl.nordea.synchro.utils.model.enties.SynchroProperties;

@Service
public class SynchroPropsServiceImpl implements SynchroPropsService {

	@Autowired
	private ISynchroPropertiesDao synchroPropsDao;
	
	@Transactional
	public void add(SynchroProperties props) {
		if(props.getValue()!=null && props.getName().contains("passw")) {
			System.out.println("ADD Value before encrypt:"+props.getValue());
			props.setValue(encrypt(props.getValue()));
			System.out.println("ADD Value after encrypt:"+props.getValue());
		}
		
		synchroPropsDao.add(props);
	}

	@Transactional
	public void edit(SynchroProperties props) {
		if(props.getValue()!=null && props.getName().contains("passw")) {
			System.out.println("EDIT Value before encrypt:"+props.getValue());
			props.setValue(encrypt(props.getValue()));
			System.out.println("EDIT Value after encrypt:"+props.getValue());
		}
		
		synchroPropsDao.edit(props);
		
	}

	@Transactional
	public void delete(int propsId) {
		synchroPropsDao.delete(propsId);
	}

	@Transactional
	public SynchroProperties getSynchroProperties(int propsId) {
		return synchroPropsDao.getSynchroProperties(propsId);
	}
	
/*	@Override
	public SynchroProperties getDecryptSynchroProperties(int propsId) {
		SynchroProperties props= synchroPropsDao.getSynchroProperties(propsId);
		if(props.getValue()!=null && props.getName().contains("passw")) {
			System.out.println("GET Value before decrypt:"+props.getValue());
			props.setValue(decrypt(props.getValue()));
			System.out.println("GET Value after decrypt:"+props.getValue());
		}
		return props;
	}*/

	@Transactional
	public List<SynchroProperties> getAllSynchroProperties(String appName) {
		return synchroPropsDao.getAllSynchroProperties(appName);
	}
	
/*	@Override
	public List<SynchroProperties> getAllDecryptSynchroProperties(String appName) {
		List<SynchroProperties> workList=synchroPropsDao.getAllSynchroProperties(appName);
		for(SynchroProperties sProp:workList) {
			if (sProp.getValue()!=null && sProp.getName().contains("passw")) {
				System.out.println("GET ALL value before decrypt:"+sProp.getValue());
				sProp.setValue(decrypt(sProp.getValue()));
				System.out.println("GET ALL value after decrypt:"+sProp.getValue());
			}
		}
		return workList;
	}*/


	@Transactional
	public Properties loadProperties(String appName) {
		Properties props= new Properties();
			for(SynchroProperties propEntity: getAllSynchroProperties(appName)) {
				props.put(propEntity.getName(), propEntity.getValue());
			}
		return props;
	}
}
