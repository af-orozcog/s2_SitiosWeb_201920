/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.dtos;
import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import co.edu.uniandes.csw.sitiosweb.entities.UnitEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel del Castillo A.
 * Class that extends from {@Link UnitDTO}.
 * The JSON serialization implements the following model: <br>
 * <pre>
 * {
 *      "id" : number,
 *      "name" : string,
 *      "requesters" : [{@Link RequestDTO}]
 * }
 * </pre> As an example: <br>
 * <pre>
 * {
 *      "id" : 1,
 *      "name" : "Departamento de Ingeniería de Sistemas",
 *      "requesters" : [...]
 * }
 * </pre>
 */
public class UnitDetailDTO extends UnitDTO implements Serializable
{
    // Attributes
    
    // Relationship where one unit has many requesters.
    private List<RequesterDTO> requesters;
    
    // Constructors
    
    /**
     * Empty constructor.
     */
    public UnitDetailDTO()
    { super(); }
    
    /**
     * Creates an UnitDTO object given a UnitEntity object. Includes the attributes
     * of UnitDTO and the relationship with RequesterDTO.
     * @param entity The UnitEntity object.
     */
    public UnitDetailDTO(UnitEntity entity)
    {
        super(entity);
        if(entity != null)
        {
            requesters = new ArrayList<>();
            for(RequesterEntity requesterEntity : entity.getRequesters())
                requesters.add(new RequesterDTO(requesterEntity));
        }
    }
    
    /**
     * Converts this UnitDTO object to a UnitEntity object. Includes the
     * relationship with RequesterDTO.
     * @return The UnitEntity object with the information in this object.
     */
    @Override
    public UnitEntity toEntity()
    {
        UnitEntity unitEntity = super.toEntity();
        if(getRequesters() != null)
        {
            List<RequesterEntity> requestersEntity = new ArrayList<>();
            for(RequesterDTO dtoRequest : getRequesters())
                requestersEntity.add(dtoRequest.toEntity());
            unitEntity.setRequesters(requestersEntity);
        }
        return unitEntity;
    }

    /**
     * @return the requesters.
     */
    public List<RequesterDTO> getRequesters() 
    { return requesters; }

    /**
     * @param requesters the requesters to set.
     */
    public void setRequesters(List<RequesterDTO> requesters) 
    { this.requesters = requesters; }
}
