/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.dtos;

import co.edu.uniandes.csw.sitiosweb.entities.UnitEntity;
import java.io.Serializable;

/**
 * @author Daniel del Castillo A.
 */
public class UnitDTO implements Serializable
{
    // Attributes
    
    /**
     * Name of the unit.
     */
    private String name;
    
    // Constructors
    
    /**
     * Empty constructor.
     */
    public UnitDTO(){}
    
    /**
     * Creates an UnitDTO object given a UnitEntity object.
     * @param entity The UnitEntity object.
     */
    public UnitDTO(UnitEntity entity)
    { this.name = entity.getName(); }
    
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
