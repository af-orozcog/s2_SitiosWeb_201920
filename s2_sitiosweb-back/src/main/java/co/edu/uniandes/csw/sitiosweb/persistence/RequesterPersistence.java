/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
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
public class RequesterPersistence {

    private static final Logger LOGGER = Logger.getLogger(IterationPersistence.class.getName());

    @PersistenceContext(unitName = "sitioswebPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param requesterEntity objeto solicitante que se creará en la base de
     * datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public RequesterEntity create(RequesterEntity requesterEntity) {
        LOGGER.log(Level.INFO, "Creando un proeevedor nuevo");
        em.persist(requesterEntity);

        LOGGER.log(Level.INFO, "Proveedor creado");
        return requesterEntity;
    }

    /**
     * Devuelve todos los solicitantees de la base de datos.
     *
     * @return una lista con todos los solicitantees que encuentre en la base de
     * datos, "select u from DeveloperEntity u" es como un "select * from
     * DeveloperEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<RequesterEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los solicitantes");
        Query q = em.createQuery("select u from RequesterEntity u");
        return q.getResultList();
    }

    /**
     * Busca si hay algun solicitante con el id que se envía de argumento
     *
     * @param requestersId: id correspondiente al solicitante buscado.
     * @return un solicitante.
     */
    public RequesterEntity find(Long requestersId) {
        LOGGER.log(Level.INFO, "Consultando el solicitante con id={0}", requestersId);

        return em.find(RequesterEntity.class, requestersId);
    }

    /**
     * Actualiza un solicitante.
     *
     * @param requesterEntity: el solicitante que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return un solicitante con los cambios aplicados.
     */
    public RequesterEntity update(RequesterEntity requesterEntity) {
        LOGGER.log(Level.INFO, "Actualizando el solicitante con id={0}", requesterEntity.getId());

        return em.merge(requesterEntity);
    }

    /**
     *
     * Borra un solicitante de la base de datos recibiendo como argumento el id
     * del solicitante
     *
     * @param requestersId: id correspondiente al solicitante a borrar.
     */
    public void delete(Long requestersId) {
        LOGGER.log(Level.INFO, "Borrando el solicitante con id={0}", requestersId);

        RequesterEntity requesterEntity = em.find(RequesterEntity.class, requestersId);
        em.remove(requesterEntity);
    }

    /**
     * Busca si hay algun solicitante con el Login que se envía de argumento
     *
     * @param login: Login del solicitante que se está buscando
     * @return null si no existe ningun solicitante con el login del argumento.
     * Si existe alguno devuelve el primero.
     */
    public RequesterEntity findByLogin(String login) {
        LOGGER.log(Level.INFO, "Consultando solicitante por login ", login);
        TypedQuery query = em.createQuery("Select e From RequesterEntity e where e.login = :login", RequesterEntity.class);
        query = query.setParameter("login", login);
        List<RequesterEntity> sameLogin = query.getResultList();
        RequesterEntity result;
        if (sameLogin == null) {
            result = null;
        } else if (sameLogin.isEmpty()) {
            result = null;
        } else {
            result = sameLogin.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar solicitante por login ", login);
        return result;
    }
}
