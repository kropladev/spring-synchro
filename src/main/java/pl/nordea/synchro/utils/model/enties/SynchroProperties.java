package pl.nordea.synchro.utils.model.enties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SYNCHRO_CONFIG")
public class SynchroProperties {
	private static final String PARAM="SYNCHRO";
	private static final String TARGET="APP";
	private static final int COUNTRY=0;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqSynchroConfig")
	@SequenceGenerator(name="seqSynchroConfig", sequenceName="SEQ_SYNCHRO_CONFIG",allocationSize=1)
	@Column(name="SC_ID")
	private int id;
	
	@Column(name="SC_NAME")
	private String name;
	
	@Column(name="SC_CUNIT_ID")
	private int countryId=COUNTRY;
	
	@Column(name="SC_PARAM")
	private String paramName=PARAM;
	
	@Column(name="SC_TARGET")
	private String target=TARGET;
	
	@Column(name="SC_VALUE")
	private String value;
	
	@Column(name="SC_DESC")
	private String descriptionString;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescriptionString() {
		return descriptionString;
	}
	public void setDescriptionString(String descriptionString) {
		this.descriptionString = descriptionString;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
}
