package pl.nordea.synchro.user.model.enties;

/***
 * @deprecated - Need to check if it is in use
 * @author m010678
 *
 */
public class PojoUserCode {
	private String userCode;
	private int countryCode;
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public int getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(int countryCode) {
		this.countryCode = countryCode;
	}
	
	@Override
	public String toString() {
		return "UserCode [userCode=" + userCode + ", countryCode="
				+ countryCode + "]";
	}
}
