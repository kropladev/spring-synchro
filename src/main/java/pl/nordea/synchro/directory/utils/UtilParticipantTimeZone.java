package pl.nordea.synchro.directory.utils;

public enum UtilParticipantTimeZone {
	POLAND("Europe/Warsaw",1),
	LITHUANIA("Europe/Vilnius",2),
	LATVIA("Europe/Riga",3),
	ESTONIA("Europe/Tallinn",4);
	
	private String timeZone;
	@SuppressWarnings("unused")
	private int country;
	
	private UtilParticipantTimeZone(String zone, int country) {
		this.timeZone=zone;
		this.country=country;
	}
	
	private UtilParticipantTimeZone(int country) {
		this.country=country;
	}
	
	public boolean equalsName(String otherTimeZone) {
		return (otherTimeZone==null)?false:timeZone.equals(otherTimeZone);
	}
	
	@Override
	public String toString() {
		return this.timeZone;
	}
	
}
