package pl.nordea.synchro.ldap.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import pl.nordea.synchro.ldap.model.enties.LdapBpssAdminUser;

public class BpssAdminUsersResultSetExtractor implements ResultSetExtractor<LdapBpssAdminUser>{

	@Override
	public LdapBpssAdminUser extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		LdapBpssAdminUser user = new LdapBpssAdminUser();
		user.setUserCode(rs.getString(1));
		user.setUserMail(rs.getString(2));
		user.setCountryId(rs.getInt(3));
		return user;
	}

}
