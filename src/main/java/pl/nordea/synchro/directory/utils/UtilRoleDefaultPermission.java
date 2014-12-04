package pl.nordea.synchro.directory.utils;

public class UtilRoleDefaultPermission {
	private static final int X_EXECUTE		=  1;
	private static final int R_ROUTE		=  2;
	private static final int S_SUSPEND		=  4;
	private static final int A_ABORT		=  8;
	private static final int D_DELEGATE		= 16;
	@SuppressWarnings("unused")
	private static final int G_GRAB			= 32;
	private static final int E_ESCALATE		= 64;
	@SuppressWarnings("unused")
	private static final int P_PEER_ASSIGN	=128;
	
	public static int returnDefaultPermissionValue(){
		return X_EXECUTE+R_ROUTE+S_SUSPEND+A_ABORT+D_DELEGATE+E_ESCALATE;
	}
}
