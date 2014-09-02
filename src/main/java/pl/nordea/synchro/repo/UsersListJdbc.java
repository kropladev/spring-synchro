package pl.nordea.synchro.repo;

import java.util.List;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pl.nordea.synchro.config.SynchroActionEnum;
import pl.nordea.synchro.repo.mapper.SynchroActionsRowMapper;
import pl.nordea.synchro.repo.mapper.UserCodeRowMapper;
import pl.nordea.synchro.user.User;
import pl.nordea.synchro.user.UserCode;

@Repository
public class UsersListJdbc implements IUsersListFromDatabase {
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<User> collectUsersForSpecificAction(int countryCode,SynchroActionEnum action) {
		return  jdbcTemplate.query(SqlQueries.SELECT_USERS_FOR_ACTION, new Object[]{countryCode,action.toString()}, new SynchroActionsRowMapper());
	}

}
