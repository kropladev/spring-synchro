package pl.nordea.synchro.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.nordea.synchro.utils.AppProps;
import pl.nordea.synchro.utils.IDirectoryXmlService;
import fuego.directory.AuthenticationException;
import fuego.directory.Directory;
import fuego.directory.DirectoryConfigurationManager;
import fuego.directory.DirectoryPassport;
import fuego.directory.DirectorySession;
import fuego.directory.exception.ProtocolNotSupportedException;

@Service
public class ImplDirSession implements IDirSessionService {
	private static final Logger logger = LoggerFactory.getLogger(ImplDirSession.class);
	private static DirectorySession<?> session;
	@Autowired
	private AppProps appProps;
	@Autowired
	private IDirectoryXmlService dirService;
	
	@Override
	public  DirectorySession<?> handleSession(){
		checkIfSessionIsNull();
		return this.getSession();
	}
	
	public DirectorySession<?> getSession() {
		return session;
	}

	public void setSession(DirectorySession<?> session) {
		ImplDirSession.session=session;
	}
	
	private void checkIfSessionIsNull(){
		if(ImplDirSession.session==null){
			initializeDirectory();
		}
	}

	@SuppressWarnings("deprecation")
	private void initializeDirectory() {
		logger.info("Directory Session will be initialized");

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		
			String dirPathString=dirService.getDirectoryConfig();
			logger.info("DirFile PATH::"+dirPathString);

			DirectoryConfigurationManager.getRuntime("default").setPropertiesFileName(dirPathString);
			DirectoryPassport directoryPassport = DirectoryPassport.createWithIDAndPreset(appProps.getPropValue("dir.directoryId"), appProps.getPropValue("dir.directoryPreset"));
			directoryPassport.fillPassport();

			ImplDirSession.session=Directory.startAdminSession(directoryPassport);
			
		} catch (ClassNotFoundException e) {
			logger.error("OracleDriver class for Directory loading wasn't fund.");
		}catch(FileNotFoundException e) {
			logger.error("Can not open directory configuration file from database; exc:"+e.toString());
		}catch(IOException e) {
			logger.error("Can not open directory configuration file from database - IOException; exc:"+e.toString());
		}catch(URISyntaxException e) {
			logger.error("Can not open directory configuration file URI exception; exc:"+e.toString());
		}catch (ProtocolNotSupportedException e) {
			logger.error("Can not create new directory session: Protocol not Suppoted exception; exc:"+e.toString());
		}catch (AuthenticationException e) {
			logger.error("Can not create new directory session: Authentication Exception; exc:"+e.toString());
		}	
		logger.info("Directory Session has been initialized");
	}	
}
