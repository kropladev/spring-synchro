package pl.nordea.synchro.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.nordea.synchro.directory.model.IDirParticipantDao;
import pl.nordea.synchro.utils.IDirectoryXmlService;
import pl.nordea.synchro.utils.model.IDirectoryXmlDao;


@SuppressWarnings("unused")
/*@ContextConfiguration(locations={
	"classpath:META-INF/springtest/application-testcontext.xml",
	"classpath:META-INF/springtest/repository-test.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)*/
public class DirectoryXmlImplTest {
	/*@Autowired
	private IDirectoryXmlService dirService;
	@Autowired
	private IDirectoryXmlDao dirDao;
	@Autowired
	private IDirParticipantDao participant;
	@Test
	public void testGetDirectoryConfig() {
		String fileName;
		try {
			fileName=dirDao.getDirectoryConfig();
			BufferedReader bufferedReader=new BufferedReader(new FileReader(fileName));
			try {
			
				String txt=null;
				while ((txt=bufferedReader.readLine())!=null) {
					System.out.println(txt);
				}
			}finally {
				bufferedReader.close();
			}
		} catch (FileNotFoundException e) {
		    Assert.fail(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			 Assert.fail(e.getMessage());
		} catch (URISyntaxException e) {
			 Assert.fail(e.getMessage());
		}
		Assert.assertTrue(true);
	}
	
	@Test
	public void testFetchParticipant() {
		participant.fetchParticipantBpm("M010678");
		System.out.println("hello "+participant.getParticipantName());
		Assert.assertTrue(true);
	}*/
}
