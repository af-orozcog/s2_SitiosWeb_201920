/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.InternalSystemsEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.InternalSystemsPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
  * @author Andres Martinez Silva 
 */

@Stateless
public class InternalSystemsLogic {
    @Inject
    private InternalSystemsPersistence persitence;
    
    public InternalSystemsEntity createInternalSystems(InternalSystemsEntity internalSystems) throws BusinessLogicException{
        if(internalSystems.getType()==null){
            throw new BusinessLogicException("El tipo del sistema esta vacio");
        }
        
        internalSystems = persitence.create(internalSystems);
        return internalSystems;
    }
}