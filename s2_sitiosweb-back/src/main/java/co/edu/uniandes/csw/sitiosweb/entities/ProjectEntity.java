/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
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

    @PodamExclude
    @ManyToMany
    private List<DeveloperEntity> developers;
    
    @PodamExclude
    @OneToOne
    private DeveloperEntity leader;
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

}
