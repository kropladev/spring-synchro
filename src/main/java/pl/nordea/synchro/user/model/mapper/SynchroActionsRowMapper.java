package pl.nordea.synchro.user.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pl.nordea.synchro.user.model.enties.PojoUser;

public class SynchroActionsRowMapper implements RowMapper<PojoUser> {

	@Override
	public PojoUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		SynchroActionsResultSetExtractor extractor= new SynchroActionsResultSetExtractor();
		return extractor.extractData(rs);
	}

}
