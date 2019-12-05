package co.edu.uniandes.csw.sitiosweb.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import uk.co.jemos.podam.common.PodamExclude;
/**
 * @author Andres Martinez Silva 
 */

@Entity
public class ProviderEntity extends BaseEntity implements Serializable{

    private String name;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @PodamExclude
    @OneToMany(mappedBy = "provider", cascade = CascadeType.PERSIST, orphanRemoval = false)
    private List<ProjectEntity> projects = new ArrayList<>();
    
    public List<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectEntity> projects) {
        this.projects = projects;
    }

    
}
