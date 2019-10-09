/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProviderEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.ProjectPersistence;
import co.edu.uniandes.csw.sitiosweb.persistence.ProviderPersistence;
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
public class ProviderLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ProviderLogic.class.getName());
    
    @Inject
    private ProviderPersistence persistence;
    
    @Inject
    private ProjectPersistence projectPersistence;
    
    public ProviderEntity createProvider(ProviderEntity provider) throws BusinessLogicException{
        if(provider.getName()==null || provider.getName().isEmpty()){
            throw new BusinessLogicException("El nombre del proveedor esta vacio");
        }  
        provider = persistence.create(provider);
        return provider;
    }
    
    
        public List<ProviderEntity> getProviders() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los proveedores");
        List<ProviderEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los proveedores");
        return lista;
    }
        
        
        public ProviderEntity getProvider(Long providersId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el proveedor con id = {0}", providersId);
        ProviderEntity providerEntity = persistence.find(providersId);
        if (providerEntity == null) {
            LOGGER.log(Level.SEVERE, "El proveedor con el id = {0} no existe", providersId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el proveedor con id = {0}", providersId);
        return providerEntity;
    }
        
        
        public ProviderEntity updateProvider(Long providersId, ProviderEntity providerEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el proveedor con id = {0}", providersId);
        if(providerEntity.getName()==null){
            throw new BusinessLogicException("El nombre del proveedor esta vacio");
        }  
        ProviderEntity newProviderEntity = persistence.update(providerEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el proveedor con id = {0}", providersId);
        return newProviderEntity;
    }
        
        
        public void deleteProvider(Long providersId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el autor con id = {0}", providersId);
        List<ProjectEntity> projects = getProvider(providersId).getProjects();
        if (projects != null && !projects.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el proveedor con id = " + providersId + " porque tiene proyectos asociados");
        }
        persistence.delete(providersId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el proveedor con id = {0}", providersId);
    }
}
