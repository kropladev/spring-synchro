package pl.nordea.synchro.repo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pl.nordea.synchro.user.User;

public class SynchroActionsRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		SynchroActionsResultSetExtractor extractor= new SynchroActionsResultSetExtractor();
		return extractor.extractData(rs);
	}

}
