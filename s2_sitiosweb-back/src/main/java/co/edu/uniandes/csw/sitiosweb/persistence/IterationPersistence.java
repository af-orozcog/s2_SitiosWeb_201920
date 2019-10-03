/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.IterationEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Andrés Felipe Orozco Gonzalez af.orozcog 201730058
 */
@Stateless
public class IterationPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(IterationPersistence.class.getName());
    @PersistenceContext(unitName = "sitioswebPU")
    
    
    protected EntityManager em;
    
    /**
     * Método que persiste la iteración pasada por parametro en la base de datos
     * @param iteration la iteración que se quiere hacer persistir
     * @return la iteración que se persistio
     */
    public IterationEntity create (IterationEntity iteration){
        LOGGER.log(Level.INFO, "Creando una iteración nueva");
        
        em.persist(iteration);
        LOGGER.log(Level.INFO, "iteración creada");
        return iteration;
    }
    
    /**
     * Devuelve todas las iteraciones de la base de datos.
     *
     * @return una lista con todas las iteraciones que encuentre en la base de
     * datos, "select u from IterationEntity u" es como un "select * from
     * IterationEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<IterationEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las iteraciones");
        // Se crea un query para buscar todas las iteraciones en la base de datos.
        TypedQuery query = em.createQuery("select u from IterationEntity u",IterationEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de las iteraciones.
        return query.getResultList();
    }
    /**
     * Busca si hay alguna iteracion con el id que se envía de argumento
     *
     * @param iterationId: id correspondiente a la iteracion buscada.
     * @return una iteracion.
     */
    public IterationEntity find(Long iterationId) {
        LOGGER.log(Level.INFO, "Consultando la iteracion con id={0}", iterationId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from IterationEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(IterationEntity.class, iterationId);
    }
    
    /**
     * Actualiza una iteracion.
     *
     * @param iterationEntity: la iteracion que viene con los nuevos cambios. Por
     * ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una iteracion con los cambios aplicados.
     */
    public IterationEntity update(IterationEntity iterationEntity) {
        LOGGER.log(Level.INFO, "Actualizando la iteracion con id={0}", iterationEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la iteracion con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        return em.merge(iterationEntity);
    }
    
    /**
     * Borra una iteracion de la base de datos recibiendo como argumento el id de la
     * iteracion
     *
     * @param iterationId: id correspondiente a la iteracion a borrar.
     */
    public void delete(Long iterationId) {

        LOGGER.log(Level.INFO, "Borrando la iteracion con id={0}", iterationId);
        // Se hace uso de mismo método que esta explicado en public IterationEntity find(Long id) para obtener la iteracion a borrar.
        IterationEntity iterationEntity = em.find(IterationEntity.class, iterationId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
        EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
        Es similar a "delete from IterationEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(iterationEntity);
    }
}
