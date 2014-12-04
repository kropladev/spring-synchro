package pl.nordea.synchro.utils.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.web.multipart.MultipartFile;
/**
 * Insert new directory.xml into database table BPSS_ADMIN.SYNCHRO_CONFIG_DIR with operation of removing old config file;
 * Also serving pulling out the directory configuration file
 * @author m010678
 *
 */
public interface IDirectoryXmlDao {
	/**
	 * store configuration file into database
	 * @param file - The file to be uploaded into database (serves from controller)
	 * @throws IOException
	 */
	void insertDirectoryConfig(MultipartFile file) throws IOException;
	
	/**
	 * get directory configuration file from database
	 * @return
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	String getDirectoryConfig() throws FileNotFoundException, IOException, URISyntaxException;
}
