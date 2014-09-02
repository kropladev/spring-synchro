package pl.nordea.synchro.directory;

import java.util.Properties;
import org.springframework.stereotype.Component;
import fuego.directory.AuthenticationException;
import fuego.directory.Directory;
import fuego.directory.DirectoryConfigurationManager;
import fuego.directory.DirectoryException;
import fuego.directory.DirectoryPassport;
import fuego.directory.DirectorySession;
import fuego.directory.exception.ProtocolNotSupportedException;

@Component
public class DirSession implements IDirectorySession {
	private DirectorySession<?> session;
	
	public DirSession(){
		checkIfSessionIsNull();
	}

	@Override
	public  DirectorySession<?> handleSession(){
		return this.getSession();
	}
	
	public DirectorySession<?> getSession() {
		return session;
	}

	public void setSession(DirectorySession<?> session) {
		this.session=session;
	}
	
	private void checkIfSessionIsNull(){
		if(this.session==null){
			initializeDirectory();
		}
	}

	private void initializeDirectory() {
		Properties configuration = new Properties();
		//Configuration for Standalone
		configuration.setProperty( "fuego.custom.replication.config.directoryId", "default");
		configuration.setProperty("fuego.custom.replication.config.directoryFile", "resource/directory252.xml");
		configuration.setProperty("fuego.custom.replication.config.preset", "engine");
		try {
			DirectoryConfigurationManager.getRuntime("default").setPropertiesFileName(configuration.getProperty("fuego.custom.replication.config.directoryFile").trim());
			DirectoryPassport directoryPassport = DirectoryPassport.createWithIDAndPreset(configuration.getProperty("fuego.custom.replication.config.directoryId").trim(), configuration.getProperty("fuego.custom.replication.config.preset").trim());
			directoryPassport.fillPassport();
			//this.setSession(Directory.startAdminSession(directoryPassport));
			this.setSession(Directory.startAdminSession(directoryPassport));
			
		}catch (ProtocolNotSupportedException e) {
			e.printStackTrace();
		}catch (AuthenticationException e) {
			e.printStackTrace();
		}catch(DirectoryException e){
			e.printStackTrace();
		}
		
	}
	
}
