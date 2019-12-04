package co.edu.uniandes.csw.sitiosweb.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStringValue;

/**
 *
 * @author Daniel Galindo Ruiz
 */
@Entity
public class ProjectEntity  extends BaseEntity implements Serializable {
 
    /**
     * Atributo que dice si el proyecto es interno o no.
     */
    private Boolean internalProject;
    
    /**
     * Atributo donde se almacena el nombre del proyecto
     */
    @PodamStringValue(length = 1)
    private String name;
    
    
    /**
     * Da el nombre de la compañia asociada al proyecto.
     */
    @PodamStringValue(length = 1)
    private String company;
    
    /**
     * Relationship where a project has one (the first) or more requests.
     */
    @PodamExclude
    @OneToMany(mappedBy = "project",cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RequestEntity> requests = new ArrayList<>();

    /**
     * Relationship where a project has one or more requests.
     */
    @PodamExclude
    @ManyToMany
    private List<DeveloperEntity> developers = new ArrayList<>();
    
    /**
     * Relationship where a project has a leader.
     */
    @PodamExclude
    @ManyToOne
    private DeveloperEntity leader;
    
    /**
     * Relationship where a project has one or more providers.
     */
    @PodamExclude
    @ManyToOne
    private ProviderEntity provider;
    
    /**
     * Relationship where a project has hardware.
     */
    @PodamExclude
    @OneToOne(mappedBy = "project", fetch=FetchType.LAZY)
    private HardwareEntity hardware;
    
    /**
     * Relationship where a project has one or more iterations.
     */
    @PodamExclude
    @OneToMany (mappedBy = "project", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<IterationEntity> iterations = new ArrayList<>();
    
    /**
     * Relationship where a project has one or more internalSystems.
     */
    @PodamExclude
    @OneToMany (mappedBy = "project", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<InternalSystemsEntity> internalSystems = new ArrayList<>();


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
     * @return the leader
     */
    public DeveloperEntity getLeader() {
        return leader;
    }

    /**
     * @param leader the leader to set
     */
    public void setLeader(DeveloperEntity leader) {
        this.leader = leader;
    }
    
        /**
     * @return the developers
     */
    public List<DeveloperEntity> getDevelopers() {
        return developers;
    }

    /**
     * @param developers the developers to set
     */
    public void setDevelopers(List<DeveloperEntity> developers) {
        this.developers = developers;
    }

    /**
     * @return the hardware
     */
    public HardwareEntity getHardware() {
        return hardware;
    }

    /**
     * @param hardware the hardware to set
     */
    public void setHardware(HardwareEntity hardware) {
        this.hardware = hardware;
    }

    /**
     * @return the iterations
     */
    public List<IterationEntity> getIterations() {
        return iterations;
    }

    /**
     * @param iterations the iterations to set
     */
    public void setIterations(List<IterationEntity> iterations) {
        this.iterations = iterations;
    }

    /**
     * @return the internalSystems
     */
    public List<InternalSystemsEntity> getInternalSystems() {
        return internalSystems;
    }

    /**
     * @param internalSystems the internalSystems to set
     */
    public void setInternalSystems(List<InternalSystemsEntity> internalSystems) {
        this.internalSystems = internalSystems;
    }

    /**
     * @return the requests
     */
    public List<RequestEntity> getRequests() {
        return requests;
    }

    /**
     * @param requests the requests to set
     */
    public void setRequests(List<RequestEntity> requests) {
        this.requests = requests;
    }

    /**
     * @return the provider
     */
    public ProviderEntity getProvider() {
        return provider;
    }

    /**
     * @param provider the provider to set
     */
    public void setProvider(ProviderEntity provider) {
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
