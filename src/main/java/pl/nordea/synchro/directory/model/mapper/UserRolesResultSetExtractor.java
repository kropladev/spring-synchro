package pl.nordea.synchro.directory.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import pl.nordea.synchro.directory.model.enties.PojoRole;

public class UserRolesResultSetExtractor implements ResultSetExtractor<PojoRole>{

	@Override
	public PojoRole extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		PojoRole role= new PojoRole();
		role.setRoleId(rs.getString(1));
		role.setCunitId(rs.getInt(2));
		role.setUnitId(rs.getString(3));
		role.setPermissions(rs.getInt(4));
		return role;
	}

}
