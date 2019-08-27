/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.CatalogueEntity;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@Stateless
public class CataloguePersistence {
    
    @PersistenceContext(unitName = "sitioswebPU")
    protected EntityManager em;

    
    public CatalogueEntity create (CatalogueEntity catalogue){
        LOGGER.log(Level.INFO, "Creando un proeevedor nuevo");
        em.persist(catalogue);
        
        LOGGER.log(Level.INFO, "Proveedor creado");
        return catalogue;
    }
    
    
    public List<CatalogueEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los proveedores");
        
        TypedQuery query = em.createQuery("select u from CatalogueEntity u", CatalogueEntity.class);
        return query.getResultList();
    }
    
        public CatalogueEntity find(Long authorsId) {
        LOGGER.log(Level.INFO, "Consultando el proveedor con id={0}", authorsId);

        return em.find(CatalogueEntity.class, authorsId);
    }
        
        public CatalogueEntity update(CatalogueEntity authorEntity) {
        LOGGER.log(Level.INFO, "Actualizando el proveedor con id={0}", authorEntity.getId());
        
        return em.merge(authorEntity);
    }
        
        public void delete(Long authorsId) {
        LOGGER.log(Level.INFO, "Borrando el proveedor con id={0}", authorsId);
        
        CatalogueEntity authorEntity = em.find(CatalogueEntity.class, authorsId);
        em.remove(authorEntity);
    }
}
