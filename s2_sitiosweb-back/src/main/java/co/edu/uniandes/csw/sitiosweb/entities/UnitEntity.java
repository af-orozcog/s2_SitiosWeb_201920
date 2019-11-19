package co.edu.uniandes.csw.sitiosweb.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStringValue;

/**
 * @author Daniel del Castillo A.
 */
@Entity
public class UnitEntity extends BaseEntity implements Serializable
{
    // Attributes
 
    /**
     * Relationship where one unit has many requesters.
     */
    @PodamExclude
    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY)
    private List<RequesterEntity> requesters;
    
    /**
     * Name of the unit.
     */
    @PodamStringValue(length = 1)
    private String name;

    // Methods
    
    /**
     * @return the name
     */
    public String getName() 
    { return name; }

    /**
     * @param name the name to set
     */
    public void setName(String name) 
    { this.name = name; }

    /**
     * @return the requesters
     */
    public List<RequesterEntity> getRequesters() 
    { return requesters; }

    /**
     * @param requesters the requesters to set
     */
    public void setRequesters(List<RequesterEntity> requesters) 
    { this.requesters = requesters; }
}
