package pl.nordea.synchro.test;

import java.util.Locale;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;

import pl.nordea.synchro.directory.model.ImplDirParticipant;
import pl.nordea.synchro.directory.utils.UtilOrganisationUnit;
import pl.nordea.synchro.directory.utils.UtilParticipantTimeZone;
import pl.nordea.synchro.user.model.enties.PojoUser;

public class ParticipantBpmTest {
	private static final String USER_NAME="N:Agnieszka Majdziak-Kneblewska|E:agnieszka.majdziak-kneblewska@nordea.com";
	private static final String USER_NAME2="N:Agnieszka Majdziak-Kneblewska|E:";
	private static final String USER_NAME3="N:|E:agnieszka.majdziak-kneblewska@nordea.com";
	private static final String USER_NAME4="N:|E:";
	
	@Test
	public void findProperTimeZoneTest() {
		ImplDirParticipant pBpm = new ImplDirParticipant();
		Assert.assertEquals(UtilParticipantTimeZone.ESTONIA.toString(), pBpm.findProperTimeZone(3));
		Assert.assertEquals(UtilParticipantTimeZone.POLAND.toString(),  pBpm.findProperTimeZone(0));
	}
	
	@Test
	public void takeUserTimZoneTest() {
		ImplDirParticipant pBpm = new ImplDirParticipant();
		Assert.assertEquals(TimeZone.getTimeZone("Europe/Tallinn"), pBpm.takeUserTimeZone(3));
		Assert.assertEquals(TimeZone.getTimeZone("Europe/Warsaw"),  pBpm.takeUserTimeZone(0));
	}
	
	@Test
	public void takeUserLocaleTest() {
		ImplDirParticipant pBpm = new ImplDirParticipant();
		Assert.assertEquals(Locale.ENGLISH, pBpm.takeUserLocale(3));
		Assert.assertFalse((Locale.ENGLISH).equals(pBpm.takeUserLocale(1)));
		Assert.assertTrue((Locale.ENGLISH).equals(pBpm.takeUserLocale(2)));
	}	
	
	@Test
	public void transformUserName() {
		PojoUser user = new PojoUser();
		ImplDirParticipant pBpm = new ImplDirParticipant();
		user.setUserName(USER_NAME);
		pBpm.transformUserName(user);
		String[] expectedString= new String[] {"Agnieszka Majdziak-Kneblewska","agnieszka.majdziak-kneblewska@nordea.com",""};
		Assert.assertEquals(expectedString[0], user.getUserName());
		Assert.assertEquals(expectedString[1], user.getUserEmail());

		user = new PojoUser();
		user.setUserName(USER_NAME2);
		pBpm.transformUserName(user);
		Assert.assertEquals(expectedString[0], user.getUserName());
		Assert.assertEquals(expectedString[2], user.getUserEmail());
	
		user = new PojoUser();
		user.setUserName(USER_NAME3);
		pBpm.transformUserName(user);
		Assert.assertEquals(expectedString[2], user.getUserName());
		Assert.assertEquals(expectedString[1], user.getUserEmail());
		
		user = new PojoUser();
		user.setUserName(USER_NAME4);
		pBpm.transformUserName(user);
		Assert.assertEquals(expectedString[2], user.getUserName());
		Assert.assertEquals(expectedString[2], user.getUserEmail());
	}
	
	@Test
	public void testTransformProperOU() {
		ImplDirParticipant participant = new ImplDirParticipant();
		Assert.assertEquals(UtilOrganisationUnit.POLAND.toString(), participant.transformProperOU(1));
		Assert.assertEquals(UtilOrganisationUnit.ESTONIA.toString(), participant.transformProperOU(4));
	}
}
