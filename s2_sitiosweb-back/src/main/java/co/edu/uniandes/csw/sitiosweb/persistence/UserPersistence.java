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
        LOGGER.log(Level.INFO, "Consultando todos los proveedores");
        
        TypedQuery query = em.createQuery("select u from UserEntity u", UserEntity.class);
        return query.getResultList();
    }
    
        public UserEntity find(Long authorsId) {
        LOGGER.log(Level.INFO, "Consultando el proveedor con id={0}", authorsId);

        return em.find(UserEntity.class, authorsId);
    }
        
        public UserEntity update(UserEntity authorEntity) {
        LOGGER.log(Level.INFO, "Actualizando el proveedor con id={0}", authorEntity.getId());
        
        return em.merge(authorEntity);
    }
        
        public void delete(Long authorsId) {
        LOGGER.log(Level.INFO, "Borrando el proveedor con id={0}", authorsId);
        
        UserEntity authorEntity = em.find(UserEntity.class, authorsId);
        em.remove(authorEntity);
    }
}
