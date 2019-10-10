/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.ProviderEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * @author Andres Martinez Silva .
 */
@Stateless
public class ProviderPersistence {
    private static final Logger LOGGER = Logger.getLogger(IterationPersistence.class.getName());

    @PersistenceContext(unitName = "sitioswebPU")
    protected EntityManager em;

    
    public ProviderEntity create (ProviderEntity provider){
        LOGGER.log(Level.INFO, "Creando un proeevedor nuevo");
        em.persist(provider);
        LOGGER.log(Level.INFO, "Proveedor creado");
        return provider;
    }
    
    
    public List<ProviderEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los proveedores");

        TypedQuery query = em.createQuery("select u from ProviderEntity u", ProviderEntity.class);
       
        return query.getResultList();
    }
    
        public ProviderEntity find(Long providersId) {
        LOGGER.log(Level.INFO, "Consultando el proveedor con id={0}", providersId);
        ProviderEntity provider = em.find(ProviderEntity.class, providersId);
        return provider;
    }
        

        public ProviderEntity findById( Long providersId) {
       LOGGER.log(Level.INFO, "Consultando proveedor por Id ", providersId);
        TypedQuery query = em.createQuery("Select e From ProviderEntity e where e.isbn = :id", ProviderEntity.class);
        query = query.setParameter("id", providersId);
        List<ProviderEntity> sameId = query.getResultList();
        ProviderEntity result;
        if (sameId == null) {
            result = null;
        } else if (sameId.isEmpty()) {
            result = null;
        } else {
            result = sameId.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar proveedores por id ", providersId);
        return result;
    }   
        
        public ProviderEntity findByName( String providersName) {
       LOGGER.log(Level.INFO, "Consultando proveedor por nombre ", providersName);
        TypedQuery query = em.createQuery("Select e From ProviderEntity e where e.name = :nombre", ProviderEntity.class);
        query = query.setParameter("nombre", providersName);
        List<ProviderEntity> sameNombre = query.getResultList();
        ProviderEntity result;
        if (sameNombre == null) {
            result = null;
        } else if (sameNombre.isEmpty()) {
            result = null;
        } else {
            result = sameNombre.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar proveedores por nombre ", providersName);
        return result;
    } 
 

        
        public ProviderEntity update(ProviderEntity providerEntity) {
        LOGGER.log(Level.INFO, "Actualizando el proveedor con id={0}", providerEntity.getId());
        return em.merge(providerEntity);
    }
        
        public void delete(Long providersId) {
        LOGGER.log(Level.INFO, "Borrando el proveedor con id={0}", providersId);
        ProviderEntity providerEntity = em.find(ProviderEntity.class, providersId);
        em.remove(providerEntity);
        LOGGER.log(Level.INFO, "Saliendo de borrar El provider con id = {0}", providersId);
    }
}
