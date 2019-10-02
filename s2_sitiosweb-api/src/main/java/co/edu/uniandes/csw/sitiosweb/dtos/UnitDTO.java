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
 * The JSON serialization implements the following model: <br>
 * <pre>
 * {
 *      "id" : number,
 *      "name" : string
 * }
 * </pre> As an example: <br>
 * <pre>
 * {
 *      "id" : 1,
 *      "name" : "Departamento de Ingeniería de Sistemas"
 * }
 * </pre>
 */
public class UnitDTO implements Serializable
{
    // Attributes
    
    /**
     * Id of the unit.
     */
    private Long id;
    
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
    { 
        if(entity != null)
        {
            this.name = entity.getName(); 
            this.id = entity.getId();
        }   
    }
    
    // Methods
    
    /**
     * Converts this UnitDTO object to a UnitEntity object.
     * @return The UnitEntity object with the information in this object.
     */
    public UnitEntity toEntity()
    {
        UnitEntity unitEntity = new UnitEntity();
        unitEntity.setName(this.getName());
        unitEntity.setId(this.getId());
        return unitEntity;
    }

    /**
     * @return the name.
     */
    public String getName() 
    { return name; }

    /**
     * @param name the name to set.
     */
    public void setName(String name) 
    { this.name = name; }
    
    /**
     * @return the id.
     */
    public Long getId()
    { return id; }
    
    /**
     * @param id the id to set.
     */
    public void setId(Long id)
    { this.id = id; }
}
