package co.edu.uniandes.csw.sitiosweb.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author s.santosb
 */
@Entity
public class InternalSystemsEntity extends BaseEntity implements Serializable {
    
    private String type;
    
    
    @PodamExclude
    @ManyToOne
    private ProjectEntity project;

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the project
     */
    public ProjectEntity getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(ProjectEntity project) {
        this.project = project;
    }
    
    public boolean equals(InternalSystemsEntity entity){
        return entity.getId().equals(this.getId()) && 
                this.getType().equals(entity.getType());
    }
}
