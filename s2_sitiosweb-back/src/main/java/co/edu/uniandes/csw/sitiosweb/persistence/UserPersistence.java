/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.UserEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@Stateless
public class UserPersistence {
    private static final Logger LOGGER = Logger.getLogger(IterationPersistence.class.getName());

    @PersistenceContext(unitName = "sitioswebPU")
    protected EntityManager em;

    
    public UserEntity create (UserEntity user){
        LOGGER.log(Level.INFO, "Creando un proeevedor nuevo");
        em.persist(user);
        
        LOGGER.log(Level.INFO, "Proveedor creado");
        return user;
    }
    
    
    public List<UserEntity> findAll() {
       LOGGER.log(Level.INFO, "Consultando todos los usuarios");
        Query q = em.createQuery("select u from UserEntity u");
        return q.getResultList();
    }
    
        public UserEntity find(Long usersId) {
        LOGGER.log(Level.INFO, "Consultando el usuario con id={0}", usersId);

        return em.find(UserEntity.class, usersId);
    }
        
        public UserEntity update(UserEntity userEntity) {
        LOGGER.log(Level.INFO, "Actualizando el usuario con id={0}", userEntity.getId());
        
        return em.merge(userEntity);
    }
        
        public void delete(Long usersId) {
        LOGGER.log(Level.INFO, "Borrando el usuario con id={0}", usersId);
        
        UserEntity userEntity = em.find(UserEntity.class, usersId);
        em.remove(userEntity);
    }
        
        public UserEntity findByLogin(String login) {
        LOGGER.log(Level.INFO, "Consultando desarrollador por login ", login);
        TypedQuery query = em.createQuery("Select e From UserEntity e where e.login = :login", UserEntity.class);
        query = query.setParameter("login", login);
        List<UserEntity> sameLogin = query.getResultList();
        UserEntity result;
        if (sameLogin == null) {
            result = null;
        } else if (sameLogin.isEmpty()) {
            result = null;
        } else {
            result = sameLogin.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar desarrollador por login ", login);
        return result;
    }
}
