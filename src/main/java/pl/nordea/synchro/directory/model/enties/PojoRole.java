package pl.nordea.synchro.directory.model.enties;

public class PojoRole {
	private String roleId;
	private int cunitId;
	private String unitId;
	private int permissions;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public int getCunitId() {
		return cunitId;
	}
	public void setCunitId(int cunitId) {
		this.cunitId = cunitId;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public int getPermissions() {
		return permissions;
	}
	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}
	
	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", cunitId=" + cunitId + ", unitId="
				+ unitId + ", permissions=" + permissions + "]";
	}

}
