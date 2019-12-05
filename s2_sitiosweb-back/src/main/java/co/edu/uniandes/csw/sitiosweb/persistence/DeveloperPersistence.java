/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
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
public class DeveloperPersistence {

    private static final Logger LOGGER = Logger.getLogger(IterationPersistence.class.getName());

    @PersistenceContext(unitName = "sitioswebPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param developerEntity objeto desarrollador que se creará en la base de
     * datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public DeveloperEntity create(DeveloperEntity developerEntity) {
        LOGGER.log(Level.INFO, "Creando un desarrollador nuevo");
        em.persist(developerEntity);

        LOGGER.log(Level.INFO, "Desarrollador creado");
        return developerEntity;
    }

    /**
     * Devuelve todos los desarrolladores de la base de datos.
     *
     * @return una lista con todos los desarrolladores que encuentre en la base
     * de datos, "select u from DeveloperEntity u" es como un "select * from
     * DeveloperEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<DeveloperEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los desarrolladores");
        Query q = em.createQuery("select u from DeveloperEntity u");
        return q.getResultList();
    }

    /**
     * Busca si hay algun desarrollador con el id que se envía de argumento
     *
     * @param developersId: id correspondiente al desarrollador buscado.
     * @return un desarrollador.
     */
    public DeveloperEntity find(Long developersId) {
        LOGGER.log(Level.INFO, "Consultando el desarrollador con id={0}", developersId);

        return em.find(DeveloperEntity.class, developersId);
    }

    /**
     * Actualiza un desarrollador.
     *
     * @param developerEntity: el desarrollador que viene con los nuevos
     * cambios. Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso
     * del método update.
     * @return un desarrollador con los cambios aplicados.
     */
    public DeveloperEntity update(DeveloperEntity developerEntity) {
        LOGGER.log(Level.INFO, "Actualizando el desarrollador con id={0}", developerEntity.getId());

        return em.merge(developerEntity);
    }

    /**
     *
     * Borra un desarrollador de la base de datos recibiendo como argumento el
     * id del desarrollador
     *
     * @param developersId: id correspondiente al desarrollador a borrar.
     */
    public void delete(Long developersId) {
        LOGGER.log(Level.INFO, "Borrando el desarrollador con id={0}", developersId);

        DeveloperEntity developerEntity = em.find(DeveloperEntity.class, developersId);
        em.remove(developerEntity);
    }

    /**
     * Busca si hay algun desarrollador con el Login que se envía de argumento
     *
     * @param login: Login del desarrollador que se está buscando
     * @return null si no existe ningun desarrollador con el login del
     * argumento. Si existe alguno devuelve el primero.
     */
    public DeveloperEntity findByLogin(String login) {
        LOGGER.log(Level.INFO, "Consultando desarrollador por login {0}", login);
        TypedQuery query = em.createQuery("Select e From DeveloperEntity e where e.login = :login", DeveloperEntity.class);
        query = query.setParameter("login", login);
        List<DeveloperEntity> sameLogin = query.getResultList();
        DeveloperEntity result;
        if (sameLogin == null) {
            result = null;
        } else if (sameLogin.isEmpty()) {
            result = null;
        } else {
            result = sameLogin.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar desarrollador por login {0}", login);
        return result;
    }
    
     /**
     * Busca si hay algun desarrollador con el Name que se envía de argumento
     *
     * @param name: Name del desarrollador que se está buscando
     * @return null si no existe ningun desarrollador con el name del
     * argumento. Si existe alguno devuelve el primero.
     */
    public DeveloperEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando desarrollador por name {0}", name);
        TypedQuery query = em.createQuery("Select e From DeveloperEntity e where e.name = :name", DeveloperEntity.class);
        query = query.setParameter("name", name);
        List<DeveloperEntity> sameName = query.getResultList();
        DeveloperEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar desarrollador por name {0}", name);
        return result;
    }
}
