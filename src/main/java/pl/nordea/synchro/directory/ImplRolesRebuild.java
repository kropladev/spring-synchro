package pl.nordea.synchro.directory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.nordea.synchro.directory.utils.UtilRoleDefaultPermission;
import pl.nordea.synchro.directory.model.IParticipantRolesDao;
import pl.nordea.synchro.directory.model.enties.PojoRole;
import pl.nordea.synchro.utils.SynchroStatistics;

import fuego.directory.DirHumanParticipant;
import fuego.directory.DirOrganizationalRole;
import fuego.directory.DirectoryException;
import fuego.directory.DirHumanParticipant.RoleAssignment;

/**
 * 
 * @author m010678
 * rebuild roles service
 */
@Service
public class ImplRolesRebuild implements IRolesRebuildService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public IParticipantRolesDao userRoles;	
	
	@Autowired
	private IDirSessionService session;
	
	private static int defaultPermission=0;
	
	private List<String> specialParamRolesForCountry=null;
	private List<PojoRole> userListOfRoles;
	
	/**
	 * return default role permission 
	 */
	static{
		setPermissions();
	}
	
	
/*
 * (non-Javadoc)
 * @see pl.nordea.synchro.role.IRebuildRoles#rebuildUserRoles(fuego.directory.DirHumanParticipant)
 */
	@Override
	public void rebuildUserRoles(DirHumanParticipant participant) {
		this.prepareSpecialRoles();
		this.prepareListOfUserRoles(participant.getId());
		List<RoleAssignment> participantRoles =new ArrayList<RoleAssignment>();
		//reset assignments
		participant.setRolesAssignment(null);
		
		for(PojoRole role:this.userListOfRoles){
			try {
				DirOrganizationalRole orgRole=DirOrganizationalRole.fetch(session.handleSession(), role.getRoleId());
				if(orgRole!=null){
					//logger.debug(orgRole.toString());
					if(orgRole.isParametric()){
						participantRoles.addAll(this.addParametricRole(orgRole, role));
					}else{
						logger.debug("Role:"+ orgRole.toString()+" not in role array");
						RoleAssignment roleAss= new RoleAssignment(orgRole.getId(), getDefaultPermission(), orgRole.getIn());
						participantRoles.add(roleAss);
					}
				}else{
					logger.error(role.getRoleId()+" is not defined inside BPM engine [1]"); 
					SynchroStatistics.incrementCounterRebuildRolesErrors();
				}
			} catch (DirectoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException nexp){
				logger.error(role.getRoleId()+" is not defined inside BPM engine [2]");
			}
		}
		if (participantRoles!=null && !participantRoles.isEmpty()){
			participant.setRolesAssignment((RoleAssignment[]) participantRoles.toArray(new RoleAssignment[participantRoles.size()]) );
		}
		SynchroStatistics.incrementCounterRebuildRolesBPM();
		logger.info("Participant:["+participant.getId()+"], Action:REBUILD_ROLES END");
	}
	
/**
 * Returns single role with all parameter
 * @param orgRole - directory role which was fetched in engine - exists in engine
 * @param role - role definition taken from relation between BPSS_ADMIN.USER_POSITION and POSITION
 * @return collection of Directory Roles Assignment which contains data of role with parameters
 */
	private Collection<? extends RoleAssignment> addParametricRole(DirOrganizationalRole orgRole, PojoRole role) {
		logger.info("addParametricRoles Start role:"+orgRole.getDisplayName()+"|| role2:" +role.getRoleId());
		List<RoleAssignment> rolesList=new ArrayList<DirHumanParticipant.RoleAssignment>();
		if(specialParamRolesForCountry.contains(orgRole.getId().toLowerCase())){
			String param=Integer.toString(role.getCunitId());
			rolesList.add(createRoleAssignment(orgRole.getId(),param, orgRole.getIn()));
		}else{
			for(String param: userRoles.collectParticipantParamsForRole(role.getUnitId(),role.getCunitId())){
				rolesList.add(createRoleAssignment(orgRole.getId(),param, orgRole.getIn()));
			}
		}
		return rolesList;
	}

	private RoleAssignment createRoleAssignment(String id, String param, int in) {
		return new RoleAssignment(id,param,getDefaultPermission(),in);
	}

	/**
	 * retrieve all roles definitions from BPSS_ADMIN objects - this is only definitions!
	 * roles definitions are stored inside instance variable userListOfRoles
	 * @param participantId
	 */
	private void prepareListOfUserRoles(String participantId) {
		this.userListOfRoles=userRoles.collectParticipantRoles(participantId);
	}

	/**
	 * retrieve all special Roles definitions which can be defined for specific country
	 * roles definitions are stored inside variable specialParamRolesForCountry
	 */
	private void prepareSpecialRoles() {
		this.setSpecialParamRolesForCountry(userRoles.collectSpecialRolesForCountry());	
	}

	/**
	 * prepare default permission value for role
	 */
	private static void setPermissions() {
		setDefaultPermission(UtilRoleDefaultPermission.returnDefaultPermissionValue());
	}
	
	/**
	 * setters and getters section
	 * 
	 */
	
	public static int getDefaultPermission() {
		return defaultPermission;
	}
	public static void setDefaultPermission(int argDefaultPermission) {
		defaultPermission = argDefaultPermission;
	}
	public List<String> getSpecialParamRolesForCountry() {
		return specialParamRolesForCountry;
	}

	public void setSpecialParamRolesForCountry(List<String> specialParamRolesForCountry) {
		this.specialParamRolesForCountry = specialParamRolesForCountry;
	}
}
