package pl.nordea.synchro.directory;

public interface IParticipant {

	void disable();
	void activate();
	void rebuildRoles();
	void changeName(String userName);
	void changeCountry(int coutryId);
	void changeEmail(String userEmail);
	void create();
	void store();
}
