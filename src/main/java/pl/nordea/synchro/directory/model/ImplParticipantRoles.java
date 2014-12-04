package pl.nordea.synchro.directory.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import pl.nordea.synchro.directory.model.enties.PojoRole;
import pl.nordea.synchro.directory.model.mapper.UserRolesRowMapper;
import pl.nordea.synchro.utils.UtilSqlQueries;

@Repository
public class ImplParticipantRoles implements IParticipantRolesDao{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public ImplParticipantRoles(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<PojoRole> collectParticipantRoles(String userCode) {
		logger.debug("userCode:"+userCode);
		List<PojoRole> roleList=jdbcTemplate.query(UtilSqlQueries.SELECT_USER_ROLES, new Object[]{userCode}, new UserRolesRowMapper());
		logger.info("records on role list:"+roleList.size());
		return  roleList;
	}
	
	@Override
	public List<String> collectParticipantParamsForRole(String daoCode, int countryId) {
		logger.debug("userCode:"+daoCode);
		List<String> paramList=jdbcTemplate.query(UtilSqlQueries.SELECT_UNIT_ROLE_PARAMS, new Object[]{countryId, daoCode}, new ParameterizedRowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
		logger.info("records on param list:"+paramList.size());
		return  paramList;
	}

	@Override
	public List<String> collectSpecialRolesForCountry() {
		List<String> specialRolesList=jdbcTemplate.query(UtilSqlQueries.SELECT_ROLES_FOR_COUNTRY, new Object[]{}, new ParameterizedRowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
		logger.info("records on specialRolesList list:"+specialRolesList.size());
		return  specialRolesList;
	}
}
