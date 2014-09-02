package pl.nordea.synchro.repo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import pl.nordea.synchro.user.User;

public class SynchroActionsResultSetExtractor  implements ResultSetExtractor<User>  {

		@Override
		public User extractData(ResultSet rs) throws SQLException, DataAccessException {
			User user= new User();
			user.setUserCode(rs.getString(1));
			user.setUserCountryId(rs.getInt(2));
			user.setActionToDo(rs.getString(3));
			user.setUserName(rs.getString(4));
			return user;
		}
}
