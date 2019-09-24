/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStringValue;

/**
 * @author Daniel del Castillo A.
 */
@Entity
public class UnitEntity extends BaseEntity implements Serializable
{
    // Attributes
 
    @PodamExclude
    @OneToMany(mappedBy = "unit")
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
}
