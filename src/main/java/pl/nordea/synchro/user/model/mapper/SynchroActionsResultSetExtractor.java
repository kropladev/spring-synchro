package pl.nordea.synchro.user.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import pl.nordea.synchro.user.model.enties.PojoUser;

public class SynchroActionsResultSetExtractor  implements ResultSetExtractor<PojoUser>  {

		@Override
		public PojoUser extractData(ResultSet rs) throws SQLException, DataAccessException {
			PojoUser user= new PojoUser();
			user.setUserCode(rs.getString(1));
			user.setUserCountryId(rs.getInt(2));
			user.setActionToDo(rs.getString(3));
			user.setUserName(rs.getString(4));
			return user;
		}
}
