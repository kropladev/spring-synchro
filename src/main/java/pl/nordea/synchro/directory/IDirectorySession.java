package pl.nordea.synchro.directory;

import fuego.directory.DirectorySession;

public interface IDirectorySession {
	DirectorySession<?> handleSession();
}
