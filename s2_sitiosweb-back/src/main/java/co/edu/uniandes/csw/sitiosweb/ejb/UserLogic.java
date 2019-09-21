/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.UserEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.UserPersistence;
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
public class UserLogic {
    private static final Logger LOGGER = Logger.getLogger(UserLogic.class.getName());
    
    @Inject
    private UserPersistence persistence;
    
    public UserEntity createUser( UserEntity user) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de creación del usuario");
        if(user.getLogin() == null )
            throw new BusinessLogicException( "El login del usuario está vacío" );
        if(user.getEmail() == null )
            throw new BusinessLogicException( "El email del usuario está vacío" );
        if(user.getPhone() == null )
            throw new BusinessLogicException( "El teléfono del usuario está vacío" );

        user = persistence.create(user);
        LOGGER.log(Level.INFO, "Termina proceso de creación del usuario");
        return user;
    }
    
    public List<UserEntity> getUsers() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los libros");
        List<UserEntity> users = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los libros");
        return users;
    }

    public UserEntity getUser(Long userId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el usuario con id = {0}", userId);
        UserEntity UserEntity = persistence.find(userId);
        if (UserEntity == null) {
            LOGGER.log(Level.SEVERE, "El usuario con el id = {0} no existe", userId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el usuario con id = {0}", userId);
        return UserEntity;
    }
    
    public UserEntity updateUser(Long userId, UserEntity UserEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el usuario con id = {0}", userId);
        if(UserEntity.getLogin() == null )
            throw new BusinessLogicException( "El login del usuario está vacío" );
        if(UserEntity.getEmail() == null )
            throw new BusinessLogicException( "El email del usuario está vacío" );
        if(UserEntity.getPhone() == null )
            throw new BusinessLogicException( "El teléfono del usuario está vacío" );
        
        UserEntity newEntity = persistence.update(UserEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el usuario con id = {0}", UserEntity.getId());
        return newEntity;
    }

    public void deleteUser(Long userId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el usuario con id = {0}", userId);
        persistence.delete(userId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el usuario con id = {0}", userId);
    }
}
