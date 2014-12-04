package pl.nordea.synchro.directory;

import fuego.directory.DirectorySession;
/**
 * Service for getting BPM directory session
 * @author m010678
 *
 */
public interface IDirSessionService {
	/**
	 * Returns BPM Directory session for connection
	 * @return fuego DirectorySession
	 */
	DirectorySession<?> handleSession();
}
