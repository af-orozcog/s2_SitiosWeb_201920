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

    public RequesterEntity create(RequesterEntity requester) {
        LOGGER.log(Level.INFO, "Creando un proeevedor nuevo");
        em.persist(requester);

        LOGGER.log(Level.INFO, "Proveedor creado");
        return requester;
    }

    public List<RequesterEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los solicitantes");
        Query q = em.createQuery("select u from RequesterEntity u");
        return q.getResultList();
    }

    public RequesterEntity find(Long requestersId) {
        LOGGER.log(Level.INFO, "Consultando el solicitante con id={0}", requestersId);

        return em.find(RequesterEntity.class, requestersId);
    }

    public RequesterEntity update(RequesterEntity requesterEntity) {
        LOGGER.log(Level.INFO, "Actualizando el solicitante con id={0}", requesterEntity.getId());

        return em.merge(requesterEntity);
    }

    public void delete(Long requestersId) {
        LOGGER.log(Level.INFO, "Borrando el solicitante con id={0}", requestersId);

        RequesterEntity requesterEntity = em.find(RequesterEntity.class, requestersId);
        em.remove(requesterEntity);
    }

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
