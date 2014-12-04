package pl.nordea.synchro.ldap.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pl.nordea.synchro.ldap.model.enties.LdapBpssAdminUser;
import pl.nordea.synchro.ldap.model.mapper.BpssUsersRowMapper;
import pl.nordea.synchro.utils.UtilSqlQueries;

/**
 * [LDAP mail synchro module]
 * @author m010678
 *
 */
@Repository
public class ImplRepoBpssAdminUsers implements IRepoBpssAdminUsersDao{
	private final Logger logger= LoggerFactory.getLogger(this.getClass());
	private JdbcTemplate template;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<LdapBpssAdminUser> collectAllBpssAdmnUsers() {
		return  template.query(UtilSqlQueries.SELECT_USERS_WITH_MAILS, new BpssUsersRowMapper());
	}

	@Override
	public int update(LdapBpssAdminUser user) {
		return template.update(UtilSqlQueries.UPDATE_USER, user.getUserCode(),user.getUserMail());
	}

	@Override
	public Map<String, LdapBpssAdminUser> collectAllBpssAdmnUsersMap() {
		List<LdapBpssAdminUser> list= template.query(UtilSqlQueries.SELECT_USERS_WITH_MAILS, new BpssUsersRowMapper());
		return transfrormListOfusersToMap(list);
	}

	/**
	 * transform list of records from sql query to map object
	 * @param list
	 * @return
	 */
	private Map<String, LdapBpssAdminUser> transfrormListOfusersToMap(
			List<LdapBpssAdminUser> list) {
		Map<String, LdapBpssAdminUser> userMap = new HashMap<String, LdapBpssAdminUser>();
		
		for(LdapBpssAdminUser user: list) {
			userMap.put(user.getUserCode(), user);
		}
		return userMap;
	}

	@Override
	public void updateBatch(final List<LdapBpssAdminUser> userList) {
		logger.debug("updateBatch RUN");
		//int[] updateCounts=template.batchUpdate(UtilSqlQueries.UPDATE_USER, new BatchPreparedStatementSetter() {
		template.batchUpdate(UtilSqlQueries.UPDATE_USER, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				LdapBpssAdminUser user = userList.get(i);
				logger.info("	user:mail::"+user.getUserMail()+"|uCode:"+user.getUserCode());
				
				ps.setString(1, user.getUserMail());
				ps.setString(2, user.getUserCode());
			}
			
			@Override
			public int getBatchSize() {
				return userList.size();
			}
		});
		logger.debug("updateBatch END");
		//return updateCounts;	
	}

	@Override
	public void updateModificationTime(String userCode) {
		template.update(UtilSqlQueries.UPDATE_USER_SYNCHRO_DATE, new Timestamp(System.currentTimeMillis()),userCode);
		
	}
}
