package pl.nordea.synchro.repo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pl.nordea.synchro.user.UserCode;

public class UserCodeRowMapper implements RowMapper<UserCode> {

	@Override
	public UserCode mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserCodesResultSetExtractor extractor= new UserCodesResultSetExtractor();
		return extractor.extractData(rs);
	}

}
