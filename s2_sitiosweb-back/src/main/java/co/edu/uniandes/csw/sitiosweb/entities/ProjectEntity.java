/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Daniel Galindo Ruiz
 */
@Entity
public class ProjectEntity  extends BaseEntity implements Serializable {
 
    private Boolean internalProject;
    
    private String company;
    
    /**
     * Relationship where a project has one (the first) or more requests.
     */
    @PodamExclude
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<RequestEntity> requests = new ArrayList<>();

    @PodamExclude
    @ManyToMany
    private List<DeveloperEntity> developers = new ArrayList<>();
    
    @PodamExclude
    @OneToOne
    private DeveloperEntity leader;
    
    @PodamExclude
    @OneToOne(mappedBy = "project", fetch=FetchType.LAZY)
    private HardwareEntity hardware;
    
    @PodamExclude
    @OneToMany (mappedBy = "project",fetch=FetchType.LAZY)
    private List<IterationEntity> iterations = new ArrayList<>();
    
    @PodamExclude
    @OneToMany (mappedBy = "project",fetch=FetchType.LAZY)
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
    

}
