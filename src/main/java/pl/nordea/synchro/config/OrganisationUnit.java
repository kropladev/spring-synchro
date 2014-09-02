package pl.nordea.synchro.config;

public enum OrganisationUnit {
	POLAND ("Nordea/Nordea Bank Poland"),LITHUANIA("Nordea/Nordea Bank Lithuania"),LATVIA("Nordea/Nordea Bank Latvia"), ESTONIA("Nordea/Nordea Bank Estonia");
	
	String ouName;
	private OrganisationUnit(String s){
		ouName=s;
	}
    public boolean equalsName(String otherOuName){
        return (otherOuName == null)? false:ouName.equals(otherOuName);
    }

    public String toString(){
       return ouName;
    }
	
}
