/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.UserEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.UserPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@Stateless
public class UserLogic {
    
    @Inject
    private UserPersistence persistence;
    
    public UserEntity createUser( UserEntity user) throws BusinessLogicException{
        
        if(user.getLogin() == null )
            throw new BusinessLogicException( "El login del usuario está vacío" );
        if(user.getEmail() == null )
            throw new BusinessLogicException( "El email del usuario está vacío" );
        if(user.getPhone() == null )
            throw new BusinessLogicException( "El teléfono del usuario está vacío" );

        user = persistence.create(user);
        return user;
    }
}
