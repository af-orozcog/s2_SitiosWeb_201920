/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.DeveloperPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@Stateless
public class DeveloperLogic {
    private static final Logger LOGGER = Logger.getLogger(DeveloperLogic.class.getName());
    
    @Inject
    private DeveloperPersistence persistence;
    
    public DeveloperEntity createDeveloper( DeveloperEntity developer) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de creación del desarrollador");
        if(developer.getLogin() == null )
            throw new BusinessLogicException( "El login del desarrollador está vacío" );
        if(developer.getEmail() == null )
            throw new BusinessLogicException( "El email del desarrollador está vacío" );
        if(developer.getPhone() == null )
            throw new BusinessLogicException( "El teléfono del desarrollador está vacío" );
        if(developer.getType() == null )
            throw new BusinessLogicException( "El tipo del desarrollador está vacío" );
        developer = persistence.create(developer);
        LOGGER.log(Level.INFO, "Termina proceso de creación del desarrollador");
        return developer;
    }
    
    public List<DeveloperEntity> getDevelopers() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los desarrolladores");
        List<DeveloperEntity> developers = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los desarrolladores");
        return developers;
    }

    public DeveloperEntity getDeveloper(Long developerId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el desarrollador con id = {0}", developerId);
        DeveloperEntity DeveloperEntity = persistence.find(developerId);
        if (DeveloperEntity == null) {
            LOGGER.log(Level.SEVERE, "El desarrollador con el id = {0} no existe", developerId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el desarrollador con id = {0}", developerId);
        return DeveloperEntity;
    }
    
    public DeveloperEntity updateDeveloper(Long developerId, DeveloperEntity DeveloperEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el desarrollador con id = {0}", developerId);
        if(DeveloperEntity.getLogin() == null )
            throw new BusinessLogicException( "El login del desarrollador está vacío" );
        if(DeveloperEntity.getEmail() == null )
            throw new BusinessLogicException( "El email del desarrollador está vacío" );
        if(DeveloperEntity.getPhone() == null )
            throw new BusinessLogicException( "El teléfono del desarrollador está vacío" );
        
        DeveloperEntity newEntity = persistence.update(DeveloperEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el desarrollador con id = {0}", DeveloperEntity.getId());
        return newEntity;
    }

    public void deleteDeveloper(Long developerId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el desarrollador con id = {0}", developerId);
        persistence.delete(developerId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el desarrollador con id = {0}", developerId);
    }
}
