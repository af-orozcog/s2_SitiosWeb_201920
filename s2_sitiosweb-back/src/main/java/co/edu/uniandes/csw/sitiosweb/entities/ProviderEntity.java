

package co.edu.uniandes.csw.sitiosweb.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;
/**
 * @author Andres Martinez Silva 
 */

@Entity
public class ProviderEntity extends BaseEntity implements Serializable{

    private String name;

    @PodamExclude
    @OneToOne
    private ProjectEntity project;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
