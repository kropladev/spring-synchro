package pl.nordea.synchro.directory.utils;

public enum UtilOrganisationUnit {
	POLAND ("Nordea/Nordea Bank Poland",1),
	LITHUANIA("Nordea/Nordea Bank Lithuania",2),
	LATVIA("Nordea/Nordea Bank Latvia",3), 
	ESTONIA("Nordea/Nordea Bank Estonia",4);
	
	private String ouName;
	@SuppressWarnings("unused")
	private int country;
	private UtilOrganisationUnit(String s,int country){
		this.country=country;
		ouName=s;
	}
	private UtilOrganisationUnit(int country){
		this.country=country;
	}
	
    public boolean equalsName(String otherOuName){
        return (otherOuName == null)? false:ouName.equals(otherOuName);
    }

    public String toString(){
       return ouName;
    }
	
}
