package co.edu.uniandes.csw.sitiosweb.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@Entity
public class DeveloperEntity extends UserEntity implements Serializable {

    
    @PodamExclude
    @ManyToMany(mappedBy = "developers")
    private List<ProjectEntity> projects;

    @PodamExclude
    @OneToMany(mappedBy = "leader")
    private List<ProjectEntity> leadingProjects;
    
    private Boolean leader;

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
     * @return if is leader
     */
    public Boolean getLeader() {
        return leader;
    }

    /**
     * @param leader if is leader
     */
    public void setLeader(boolean leader) {
        this.leader = leader;
    }
    /**
     * @return the leadingProjects
     */
    public List<ProjectEntity> getLeadingProjects() {
        return leadingProjects;
    }

    /**
     * @param leadingProjects the leadingProjects to set
     */
    public void setLeadingProjects(List<ProjectEntity> leadingProjects) {
        this.leadingProjects = leadingProjects;
    }

}
