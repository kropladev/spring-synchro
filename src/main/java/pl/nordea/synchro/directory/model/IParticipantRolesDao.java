package pl.nordea.synchro.directory.model;

import java.util.List;

import pl.nordea.synchro.directory.model.enties.PojoRole;
/**
 * Interface to getting repository data connected with Roles
 * @author m010678
 *
 */
public interface IParticipantRolesDao {
	/**
	 * Gets all collections of roles definition for particular participant
	 * @param userCode (m-code, z-code)
	 * @return list of PojoRole objects which contains all data needed for role assignment. Example: roleId:'crm blokada reporting'; cuniId:1; dao:9003; permissions:null
	 */
	public List<PojoRole> collectParticipantRoles(String userCode);
	
	/**
	 * Gets all parameters values based on specific country id and unit id 
	 * @param daoCode (participant unit id)
	 * @param countryId identity of country
	 * @return list of string params where strings are concatenation of country id and unit Id . Example: roleParam:'1*9003'
	 */
	public List<String> collectParticipantParamsForRole(String daoCode, int countryId);
	
	/**
	 * Gets all special roles defined on country level.
	 * @return list of string where strings are the names of the role. Example: roleId:'archivation administrator'. <right now there is only one such role>
	 */
	public List<String> collectSpecialRolesForCountry();
	
}
