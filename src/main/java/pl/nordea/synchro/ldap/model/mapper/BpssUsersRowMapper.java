package pl.nordea.synchro.ldap.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pl.nordea.synchro.ldap.model.enties.LdapBpssAdminUser;

public class BpssUsersRowMapper implements RowMapper<LdapBpssAdminUser> {

	@Override
	public LdapBpssAdminUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		BpssAdminUsersResultSetExtractor extractor= new BpssAdminUsersResultSetExtractor();
		return extractor.extractData(rs);
	}
}
