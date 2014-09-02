package pl.nordea.synchro.repo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import pl.nordea.synchro.user.UserCode;

public class UserCodesResultSetExtractor implements ResultSetExtractor<UserCode> {

	@Override
	public UserCode extractData(ResultSet rs) throws SQLException, DataAccessException {
		UserCode userCode= new UserCode();
		userCode.setUserCode(rs.getString(1));
		userCode.setCountryCode(rs.getInt(2));
		return userCode;
	}

}
