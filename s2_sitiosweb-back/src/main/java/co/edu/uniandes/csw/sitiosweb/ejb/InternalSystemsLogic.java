/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.InternalSystemsEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.InternalSystemsPersistence;
import java.util.logging.Logger;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
  * @author Andres Martinez Silva 
 */

@Stateless
public class InternalSystemsLogic {
    
    private static final Logger LOGGER = Logger.getLogger(InternalSystemsLogic.class.getName());
    
    @Inject
    private InternalSystemsPersistence persistence;
    
    public InternalSystemsEntity createInternalSystems(InternalSystemsEntity internalSystems) throws BusinessLogicException{
        if(internalSystems.getType()==null){
            throw new BusinessLogicException("El tipo del sistema esta vacio");
        }
        
        internalSystems = persistence.create(internalSystems);
        return internalSystems;
    }
    
    public List<InternalSystemsEntity> getInternalSystems() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los sistemas internos");
        List<InternalSystemsEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los sistemas internos");
        return lista;
    }
    
    public InternalSystemsEntity getInternalSystems(Long internalSystemsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los sistemas con id = {0}", internalSystemsId);
        InternalSystemsEntity internalSystemsEntity = persistence.find(internalSystemsId);
        if (internalSystemsEntity == null) {
            LOGGER.log(Level.SEVERE, "El sistema con el id = {0} no existe", internalSystemsId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el sistema con id = {0}", internalSystemsId);
        return internalSystemsEntity;
    }
    
    public InternalSystemsEntity updateInternalSystems(Long internalSystemsId, InternalSystemsEntity internalSystemsEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el sistema interno con id = {0}", internalSystemsId);
        InternalSystemsEntity newInternalSystemsEntity = persistence.update(internalSystemsEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el sistema interno con id = {0}", internalSystemsId);
        return newInternalSystemsEntity;
    }
    
        public void deleteInternalSystems(Long internalSystemsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el sistema interno con id = {0}", internalSystemsId);
        persistence.delete(internalSystemsId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el sistema interno con id = {0}", internalSystemsId);
    }
    
    
}