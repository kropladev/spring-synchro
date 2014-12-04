package pl.nordea.synchro.directory;

import fuego.directory.DirHumanParticipant;

/**
 * Service for rebuilding participant roles
 * @author m010678
 *
 */
public interface IRolesRebuildService {
	/**
	 * rebuild all roles definition for specific particular participant
	 * @param participant
	 */
	public void rebuildUserRoles(DirHumanParticipant participant);
}
