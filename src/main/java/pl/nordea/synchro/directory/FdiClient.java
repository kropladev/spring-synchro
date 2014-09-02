package pl.nordea.synchro.directory;

import fuego.directory.*;
import fuego.directory.exception.ProtocolNotSupportedException;
import java.util.Properties;

public class FdiClient {
	public FdiClient(){}
	private static DirectorySession<?> directorySession;
	static{
		Properties configuration = new Properties();
		//Configuration for Standalone
		configuration.setProperty( "fuego.custom.replication.config.directoryId", "default");
		configuration.setProperty("fuego.custom.replication.config.directoryFile", "resource/directory252.xml");
		configuration.setProperty("fuego.custom.replication.config.preset", "engine");
		try {
			DirectoryConfigurationManager.getRuntime("default").setPropertiesFileName(configuration.getProperty("fuego.custom.replication.config.directoryFile").trim());
			DirectoryPassport directoryPassport = DirectoryPassport.createWithIDAndPreset(configuration.getProperty("fuego.custom.replication.config.directoryId").trim(), configuration.getProperty("fuego.custom.replication.config.preset").trim());
			directoryPassport.fillPassport();
			directorySession = Directory.startAdminSession(directoryPassport);

		}catch (ProtocolNotSupportedException e) {
			e.printStackTrace();
		}catch (AuthenticationException e) {
			e.printStackTrace();
		}catch(DirectoryException e){
			e.printStackTrace();
		}
	}
	private void disconnect () throws DirectoryException{
		if(directorySession.isConnected()){
			directorySession.disconnect();
		}
	}
	private void createParticipant (final String participantName, final String participantDisplayName, final String password) throws Exception{
		if(!directorySession.isConnected()){
			throw new Exception ("session already closed..");
		}
		// fetch Role
		DirOrganizationalRole nsiRoles = DirOrganizationalRole.fetch(directorySession, "Controller");

		fuego.directory.DirHumanParticipant.RoleAssignment role = new DirHumanParticipant.RoleAssignment(nsiRoles.getId(), 255, nsiRoles.getIn());

		DirHumanParticipant participant = DirHumanParticipant.create(directorySession,
		participantName,
		participantDisplayName,
		participantName,
		participantDisplayName,
		"fts@mydomain.com", "", "",
		password, DirOrganizationalUnit.fetchRoot(directorySession) , new DirHumanParticipant.RoleAssignment[]{ role } );

		System.out.println("Participant create:"+ participant.getStatus());
	}
	
/*	private void changedPassword(final String oldPassWord, final String newPassword) throws Exception{
		if(!directorySession.isConnected()){
			throw new Exception ("session already closed..");
		}
		DirHumanParticipant part = DirHumanParticipant.fetch(directorySession, "test23");
		part.changePassword(oldPassWord, newPassword);
		System.out.println("Status:"+part.getStatus());
	}*/
	
	private DirHumanParticipant getParticipant(final String name) throws Exception {
		if(!directorySession.isConnected()){
			throw new Exception ("session already closed..");
		}
		return DirHumanParticipant.fetch(directorySession, name);
	}
	
	public static void main(String[] args) {
		System.out.println("FDI - connect to Oracle BPM directory Service...");
		FdiClient client = new FdiClient();
		DirHumanParticipant part;
		try {
			
			
			part = client.getParticipant("M010466");
	
		System.out.println("Status:"+ part.getDisplayName());
		part.setDisplayName("Johny Bravo289");
		part.update();
		client.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
