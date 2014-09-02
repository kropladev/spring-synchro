package pl.nordea.synchro.repo;

public class SqlQueries {

	public static final String SELECT_USERS_FOR_ACTION=
			" SELECT SIGN_ON_NAME, CUNIT_ID, ACTION_TO_DO, USER_NAME " +
			"  FROM BPSS_ADMIN.v_synchro_actions " +
			"  WHERE CUNIT_ID= ? " +
			"  AND ACTION_TO_DO= ? ";
}
