/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.RequesterPersistence;
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
public class RequesterLogic {
    private static final Logger LOGGER = Logger.getLogger(RequesterLogic.class.getName());
    
    @Inject
    private RequesterPersistence persistence;
    
    public RequesterEntity createRequester( RequesterEntity requester) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de creación del solicitante");
        if(requester.getLogin() == null )
            throw new BusinessLogicException( "El login del solicitante está vacío" );
        if(requester.getEmail() == null )
            throw new BusinessLogicException( "El email del solicitante está vacío" );
        if(requester.getPhone() == null )
            throw new BusinessLogicException( "El teléfono del solicitante está vacío" );

        if(persistence.findByLogin(requester.getLogin()) != null)
            throw new BusinessLogicException( "El login ya existe" );
        //if(validatePhone(requester.getPhone()))
          //  throw new BusinessLogicException("El teléfono es inválido");
        
        requester = persistence.create(requester);
        LOGGER.log(Level.INFO, "Termina proceso de creación del solicitante");
        return requester;
    }
    
    public List<RequesterEntity> getRequesters() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los solicitantes");
        List<RequesterEntity> requesters = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los solicitantes");
        return requesters;
    }

    public RequesterEntity getRequester(Long requesterId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el solicitante con id = {0}", requesterId);
        RequesterEntity RequesterEntity = persistence.find(requesterId);
        if (RequesterEntity == null) {
            LOGGER.log(Level.SEVERE, "El solicitante con el id = {0} no existe", requesterId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el solicitante con id = {0}", requesterId);
        return RequesterEntity;
    }
    
    public RequesterEntity updateRequester(Long requesterId, RequesterEntity RequesterEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el solicitante con id = {0}", requesterId);
        if(RequesterEntity.getLogin() == null )
            throw new BusinessLogicException( "El login del solicitante está vacío" );
        if(RequesterEntity.getEmail() == null )
            throw new BusinessLogicException( "El email del solicitante está vacío" );
        if(RequesterEntity.getPhone() == null )
            throw new BusinessLogicException( "El teléfono del solicitante está vacío" );
        
        //if(validatePhone(RequesterEntity.getPhone()))
          //  throw new BusinessLogicException("El teléfono es inválido");
        
        RequesterEntity newEntity = persistence.update(RequesterEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el solicitante con id = {0}", RequesterEntity.getId());
        return newEntity;
    }

    public void deleteRequester(Long requesterId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el solicitante con id = {0}", requesterId);
        persistence.delete(requesterId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el solicitante con id = {0}", requesterId);
    }
    
    //private boolean validatePhone(Integer phone) {
      //  return !(phone == null || Long.SIZE != 9);
    //}
}
