package pl.nordea.synchro.directory.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import pl.nordea.synchro.directory.model.enties.PojoRole;

public class UserRolesRowMapper implements RowMapper<PojoRole> {

	@Override
	public PojoRole mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserRolesResultSetExtractor extractor= new UserRolesResultSetExtractor();
		return extractor.extractData(rs);
	}

}
