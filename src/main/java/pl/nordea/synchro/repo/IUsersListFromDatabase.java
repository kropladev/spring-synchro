package pl.nordea.synchro.repo;

import java.util.List;

import pl.nordea.synchro.config.SynchroActionEnum;
import pl.nordea.synchro.user.User;

/**
 * interface for access to database for collecting list of users that should be taken under consider of synchro process
 * (some modification on core system was done)
 * @author m010678
 *
 */
public interface IUsersListFromDatabase {
	public abstract List<User> collectUsersForSpecificAction(int countryCode,SynchroActionEnum activate);
}
