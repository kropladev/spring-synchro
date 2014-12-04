package pl.nordea.synchro.user.model;

import java.util.Collection;

import pl.nordea.synchro.core.SynchroActionEnum;
import pl.nordea.synchro.user.model.enties.PojoUser;
/**
 * Operations on collection of records, which each one contains data about user who should be synchronized (with marked action/reason of change)
 * 
 * @author m010678
 *
 */
public interface IUserCollectionDao {
	/**
	 * Mark that user record should be synchronized for specific action (EMAIL,NAME,REBUILD_ROLE....).
	 * If user record doesn't exist create one and add to map
	 * @param user
	 * @param action
	 */
	void markUserForActionChange(PojoUser user, SynchroActionEnum action);
	
	/**
	 * return all collection. Should be used when all informations about changes will be inserted into collection (after markUserForActionChange on each change)
	 * @return
	 */
	Collection <PojoUser> getAllCollection();
	
	/**
	 * clear user list to change
	 */
	void flushUserList();
}
