/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.HardwareEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author s.santosb
 */
@Stateless
public class HardwarePersistence {
    
    private static final Logger LOGGER = Logger.getLogger(HardwarePersistence.class.getName());
    @PersistenceContext(unitName = "sitioswebPU")
    
    
    protected EntityManager em;
    
    /**
     * Método que persiste la iteración pasada por parametro en la base de datos
     * @param hardware la iteración que se quiere hacer persistir
     * @return la iteración que se persistio
     */
    public HardwareEntity create (HardwareEntity hardware){
        LOGGER.log(Level.INFO, "Creando una iteración nueva");
        
        em.persist(hardware);
        LOGGER.log(Level.INFO, "iteración creada");
        return hardware;
    }
    
    /**
     * Devuelve todas las iteraciones de la base de datos.
     *
     * @return una lista con todas las iteraciones que encuentre en la base de
     * datos, "select u from HardwareEntity u" es como un "select * from
     * HardwareEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<HardwareEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las iteraciones");
        // Se crea un query para buscar todas las iteraciones en la base de datos.
        TypedQuery query = em.createQuery("select u from HardwareEntity u",HardwareEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de las iteraciones.
        return query.getResultList();
    }
    /**
     * Busca si hay alguna iteracion con el id que se envía de argumento
     *
     * @param projectId
     * @param hardwareId: id correspondiente a la iteracion buscada.
     * @return una iteracion.
     */
    public HardwareEntity find(Long projectId, Long hardwareId) {
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from HardwareEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Consultando la iteracion con id = {0} del proyecto con id = " + projectId, hardwareId);
        TypedQuery<HardwareEntity> q = em.createQuery("select p from HardwareEntity p where (p.project.id = :projectId) and (p.id = :hardwareId)", HardwareEntity.class);
        q.setParameter("projectId", projectId);
        q.setParameter("hardwareId", hardwareId);
        List<HardwareEntity> results = q.getResultList();
        HardwareEntity review = null;
        if (results == null) {
            review = null;
        } else if (results.isEmpty()) {
            review = null;
        } else if (results.size() >= 1) {
            review = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar el review con id = {0} del libro con id =" + projectId, hardwareId);
        return review;
    }
    
    /**
     * Actualiza una iteracion.
     *
     * @param hardwareEntity: la iteracion que viene con los nuevos cambios. Por
     * ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una iteracion con los cambios aplicados.
     */
    public HardwareEntity update(HardwareEntity hardwareEntity) {
        LOGGER.log(Level.INFO, "Actualizando la iteracion con id={0}", hardwareEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la iteracion con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        return em.merge(hardwareEntity);
    }
    
    /**
     * Borra una iteracion de la base de datos recibiendo como argumento el id de la
     * iteracion
     *
     * @param hardwareId: id correspondiente a la iteracion a borrar.
     */
    public void delete(Long hardwareId) {

        LOGGER.log(Level.INFO, "Borrando la iteracion con id={0}", hardwareId);
        // Se hace uso de mismo método que esta explicado en public HardwareEntity find(Long id) para obtener la iteracion a borrar.
        HardwareEntity hardwareEntity = em.find(HardwareEntity.class, hardwareId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
        EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
        Es similar a "delete from HardwareEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(hardwareEntity);
    }



}
