package pl.nordea.synchro.user.model;

import java.util.List;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pl.nordea.synchro.core.SynchroActionEnum;
import pl.nordea.synchro.user.model.enties.PojoUser;
import pl.nordea.synchro.user.model.mapper.SynchroActionMailRowmapper;
import pl.nordea.synchro.user.model.mapper.SynchroActionsRowMapper;
import pl.nordea.synchro.utils.UtilSqlQueries;

/**
 * Implementation of the DAO. Used to receive from database user accounts list with action symbol for change.
 * Using jdbcTemplate with mappers.
 * @author m010678
 *
 */
@Repository
public class ImplUserList implements IUserListDao {
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/**
	 * Get list of all users for specific country and action symbol - what to change.
	 *  Gets only users accounts record where some change was made in BPSS_ADMIN tables structures.
	 */
	@Override
	public List<PojoUser> collectUsersForSpecificAction(int countryCode,SynchroActionEnum action) {
		RowMapper<PojoUser> rowMapper;
		if (action.equals(SynchroActionEnum.CH_EMAIL)) {
			rowMapper=new SynchroActionMailRowmapper();
		}else {
			rowMapper=new SynchroActionsRowMapper();
		}
		
		return  jdbcTemplate.query(UtilSqlQueries.SELECT_USERS_FOR_ACTION, new Object[]{countryCode,action.toString()}, rowMapper);
	}

}
