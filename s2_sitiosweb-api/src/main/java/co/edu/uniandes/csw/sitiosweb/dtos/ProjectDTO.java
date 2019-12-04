package co.edu.uniandes.csw.sitiosweb.dtos;

import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import java.io.Serializable;

/**
 *
 * @author Daniel Galindo Ruiz
 */
public class ProjectDTO implements Serializable{
    
    /**
     * Atributo boolean que dice si el proyecto es interno o no.
     */
    private Boolean internalProject;
    
    /**
     * Atributo que dice que compañia tiene a cargo el proyecto.
     */
    private String company;
    
    /**
     * id del proyecto.
     */
    private Long id;
    
    /**
     * Hardware del proyecto
     */
    private HardwareDTO hw;
    
    /**
     * Lider del proyecto.
     */
    private DeveloperDTO leader;
    
    /**
     * Proveedor del proyecto.
     */
    private ProviderDTO provider;
    
    /**
     * El nombre del proyecto
     */
    private String name;
    
    /**
     * Empty constructor
     */
    public ProjectDTO (){
        
    }
    
    /**
     * Creates a ProjectDTO object given projectEntity object.
     * @param entity The ProjectEntity object.
     */
    public ProjectDTO(ProjectEntity entity){
        if(entity != null){

            if(entity.getLeader() != null){
                this.leader = new DeveloperDTO(entity.getLeader());
            }
            else{
                this.leader = null;
            }
            if(entity.getProvider() != null){
                this.provider = new ProviderDTO(entity.getProvider());
            }
            else{
                this.provider = null;
            }
            this.id = entity.getId();
            this.internalProject = entity.getInternalProject();
            this.company = entity.getCompany();
            this.name = entity.getName();
        }
    }
    
    // Methods
    
    /**
     * Converts the ProjectDTO to a ProjetEntity object.
     * @return The ProjectEntity object with the information in this object.
     */
    public ProjectEntity toEntity(){
        ProjectEntity entidad = new ProjectEntity();
        if(this.getHw() != null){
            entidad.setHardware(this.getHw().toEntity());
        }
        if(this.getLeader() != null){
            entidad.setLeader(this.getLeader().toEntity());
        }
        if(this.getProvider() != null){
            entidad.setProvider(this.getProvider().toEntity());
        }
        entidad.setId(this.getId());
        entidad.setInternalProject(this.getInternalProject());
        entidad.setCompany(this.getCompany());
        entidad.setName(this.getName());
        return entidad;
    }
    /**
     * @return the internalProject
     */
    public Boolean getInternalProject() {
        return internalProject;
    }

    /**
     * @param internalProject the internalProject to set
     */
    public void setInternalProject(Boolean internalProject) {
        this.internalProject = internalProject;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the hw
     */
    public HardwareDTO getHw() {
        return hw;
    }

    /**
     * @param hw the hw to set
     */
    public void setHw(HardwareDTO hw) {
        this.hw = hw;
    }

    /**
     * @return the leader
     */
    public DeveloperDTO getLeader() {
        return leader;
    }

    /**
     * @param leader the leader to set
     */
    public void setLeader(DeveloperDTO leader) {
        this.leader = leader;
    }

    /**
     * @return the provider
     */
    public ProviderDTO getProvider() {
        return provider;
    }

    /**
     * @param provider the provider to set
     */
    public void setProvider(ProviderDTO provider) {
        this.provider = provider;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
}
