package pl.nordea.synchro.utils.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pl.nordea.synchro.utils.IDirectoryXmlService;
@Service
@Scope("prototype")
public class DirectoryXmlServiceImpl implements IDirectoryXmlService {
	@Autowired
	IDirectoryXmlDao directoryXmlDao;
	
	@Override
	public void insertDirectoryConfig(MultipartFile file) throws IOException {
		directoryXmlDao.insertDirectoryConfig(file);

	}

	@Override
	public String getDirectoryConfig() throws FileNotFoundException, IOException, URISyntaxException {
		return directoryXmlDao.getDirectoryConfig();
	}

}
