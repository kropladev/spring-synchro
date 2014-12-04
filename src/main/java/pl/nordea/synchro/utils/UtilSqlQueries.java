package pl.nordea.synchro.utils;

public class UtilSqlQueries {

	public static final String SELECT_USERS_FOR_ACTION=
			" SELECT SIGN_ON_NAME, CUNIT_ID, ACTION_TO_DO, USER_NAME " +
			"  FROM BPSS_ADMIN.v_synchro_actions " +
			"  WHERE CUNIT_ID= ? " +
			"  AND ACTION_TO_DO= ? ";
	
	/**
	 * will be not used !??
	 */
	public static final String SELECT_USERS_FOR_CREATE=
			" SELECT sa.SIGN_ON_NAME, sa.CUNIT_ID, sa.ACTION_TO_DO, u.U_USER_NAME, u.U_MAIL " +
			"  FROM BPSS_ADMIN.v_synchro_actions sa LEFT OUTER JOIN  BPSS_ADMIN.users u ON u.u_user=sa.sign_on_name " +
			"  WHERE CUNIT_ID= ? " +
			"  AND ACTION_TO_DO= 'CREATE' ";
	
	
	public static final String SELECT_USER_ROLES=
			" SELECT PR_ROLE_ID, UP_CUNIT_ID, UP_DAO, PR_PERMISSIONS" +
			" FROM BPSS_ADMIN.USER_POSITIONS UP  " +
			" INNER JOIN BPSS_ADMIN.POSITION_ROLES PR " +
			" ON UP.UP_POSITION = PR.PR_POSITION " +
			" AND UP.UP_CUNIT_ID = PR.PR_CUNIT_ID " +
			" WHERE UP.UP_USER = ? " +
			" ORDER BY PR_ROLE_ID";
	
	public static final String SELECT_ROLES_FOR_COUNTRY=
			" SELECT ra_Role_Id " +
			" FROM BPSS_ADMIN.ROLE_ATTRIBUTES " +
			" WHERE ra_Attribute = \'PARAMETRIC_BY\' AND ra_Value = \'COUNTRY\' ";
	
	//mex roles
	public static final String SELECT_MANUAL_PARAMETRIC_ROLES=    
			"SELECT ra_Role_Id, ra_Value " +
			" FROM BPSS_ADMIN.ROLE_ATTRIBUTES  " +
			" WHERE ra_Attribute = \'MANUAL_ROLE_PARAM\' ";

	//mex roles
	public static final String SELECT_MANUAL_PARAMETER_FOR_ROLE=
			" SELECT upr_Param " +
			"   FROM BPSS_ADMIN.USER_ROLE_PARAM " +
			"  WHERE upr_User_Id = user_Id AND upr_Role_Id = ?";
	
	
	public static final String SELECT_UNIT_ROLE_PARAMS=
    " SELECT DAO.CUNIT_ID||\'*\'||DAO ROLE_PARAM " +
    " FROM (SELECT DAO, ALT_DEPT_PARENT, ALT_DEPT_LEVEL, CUNIT_ID  " +
    "  FROM BPSS_ADMIN.V_CDW_DAO DAO1 " +
    "  WHERE DAO1.CUNIT_ID = ?) DAO " +
    " WHERE DAO.CUNIT_ID = DAO.CUNIT_ID  " +
    "   AND DAO.ALT_DEPT_LEVEL IN ('01', '04', '08', '12', '16')" +
    " CONNECT BY PRIOR TO_CHAR(DAO.DAO) = DAO.ALT_DEPT_PARENT" +
    " START WITH DAO.DAO = ? ";
    //"  ORDER BY ROLE_PARAM ";
	
	public static final String SELECT_USERS_WITH_MAILS=
			" SELECT U_USER,  U_MAIL, U_CUNIT_ID " +
			"  FROM BPSS_ADMIN.USERS ";
	
	public static final String SELECT_USERS_DATA=
			" SELECT U_USER,  U_MAIL, U_CUNIT_ID " +
			"  FROM BPSS_ADMIN.USERS ";

	public static final String UPDATE_USER = 
			" UPDATE BPSS_ADMIN.USERS SET U_MAIL=? " +
			" WHERE U_USER=? ";

	public static final String UPDATE_USER_SYNCHRO_DATE = 
			" UPDATE BPSS_ADMIN.USERS SET U_LAST_SYNCHRO_DATE=? " +
					" WHERE U_USER=? ";
	
}
