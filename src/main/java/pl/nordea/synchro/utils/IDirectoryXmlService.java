package pl.nordea.synchro.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.web.multipart.MultipartFile;

public interface IDirectoryXmlService {
	void insertDirectoryConfig(MultipartFile file) throws IOException;
	String getDirectoryConfig() throws FileNotFoundException, IOException, URISyntaxException;
}
