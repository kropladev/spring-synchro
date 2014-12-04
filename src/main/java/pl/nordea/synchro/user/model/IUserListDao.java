package pl.nordea.synchro.user.model;

import java.util.List;

import pl.nordea.synchro.core.SynchroActionEnum;
import pl.nordea.synchro.user.model.enties.PojoUser;

/**
 * interface for access to database for collecting list of users that should be taken under consider of synchro process
 * (some modification on core system was done)
 * @author m010678
 *
 */
public interface IUserListDao {
	/**
	 * Get list of records from database view v_synchro_actions (BPSS_ADMIN) schema.
	 * List consists of users ids with action symbol (what change has been made)
	 * @param countryCode
	 * @param activate
	 * @return
	 */
	List<PojoUser> collectUsersForSpecificAction(int countryCode,SynchroActionEnum activate);
}
