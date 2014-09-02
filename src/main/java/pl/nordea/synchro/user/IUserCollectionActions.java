package pl.nordea.synchro.user;

import java.util.Collection;

import pl.nordea.synchro.config.SynchroActionEnum;

public interface IUserCollectionActions {
	void processUserAction(User user, SynchroActionEnum action);
	Collection <User> getAllCollection();
}
