/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@Entity
public class DeveloperEntity extends UserEntity implements Serializable {

    public enum DeveloperType {
        Leader, Developer
    }

    @PodamExclude
    @ManyToMany(mappedBy = "developers")
    private List<ProjectEntity> projects;

    private DeveloperType type;

    /**
     * @return the projects
     */
    public List<ProjectEntity> getProjects() {
        return projects;
    }

    /**
     * @param projects the projects to set
     */
    public void setProjects(List<ProjectEntity> projects) {
        this.projects = projects;
    }

    /**
     * @return the type
     */
    public DeveloperType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(DeveloperType type) {
        this.type = type;
    }

}
