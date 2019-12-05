/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.RequesterPersistence;
import co.edu.uniandes.csw.sitiosweb.persistence.UnitPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@Stateless
public class RequesterLogic {

    private static final Logger LOGGER = Logger.getLogger(RequesterLogic.class.getName());

    @Inject
    private RequesterPersistence persistence;

    @Inject
    private UnitPersistence unitPersistence;

    /**
     * Se encarga de crear un RequesterEntity en la base de datos.
     *
     * @param requester Objeto de RequesterEntity con los datos nuevos
     * @return Objeto de RequesterEntity con los datos nuevos y su ID.
     * @throws BusinessLogicException si el requester tiene datos inválidos.
     */
    public RequesterEntity createRequester(RequesterEntity requester) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del solicitante");

        if (requester.getName() == null) {
            throw new BusinessLogicException("El nombre del solicitante está vacío");
        }
        if (requester.getLogin() == null) {
            throw new BusinessLogicException("El login del solicitante está vacío");
        }
        if (requester.getEmail() == null) {
            throw new BusinessLogicException("El email del solicitante está vacío");
        }
        if (!validatePhone(requester.getPhone())) {
            throw new BusinessLogicException("El teléfono es inválido");
        }
        if (requester.getUnit() == null || unitPersistence.find(requester.getUnit().getId()) == null) {
            throw new BusinessLogicException("La unidad es inválida");
        }
        if (persistence.findByLogin(requester.getLogin()) != null) {
            throw new BusinessLogicException("El login ya existe");
        }

        requester = persistence.create(requester);
        LOGGER.log(Level.INFO, "Termina proceso de creación del solicitante");
        return requester;
    }

    /**
     * Obtiene la lista de los registros de Requester.
     *
     * @return Colección de objetos de RequesterEntity.
     */
    public List<RequesterEntity> getRequesters() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los solicitantes");
        List<RequesterEntity> requesters = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los solicitantes");
        return requesters;
    }

    /**
     * Obtiene los datos de una instancia de Requester a partir de su ID.
     *
     * @param requesterId Identificador de la instancia a consultar
     * @return Instancia de RequesterEntity con los datos del Requester
     * consultado.
     */
    public RequesterEntity getRequester(Long requesterId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el solicitante con id = {0}", requesterId);
        RequesterEntity RequesterEntity = persistence.find(requesterId);
        if (RequesterEntity == null) {
            LOGGER.log(Level.SEVERE, "El solicitante con el id = {0} no existe", requesterId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el solicitante con id = {0}", requesterId);
        return RequesterEntity;
    }

    /**
     * Obtiene los datos de una instancia de Requester a partir de su login.
     *
     * @param requestersLogin Identificador de la instancia a consultar
     * @return Instancia de RequesterEntity con los datos del Requester
     * consultado.
     */
    public RequesterEntity getRequesterByLogin(String requestersLogin) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el solicitante con login = {0}", requestersLogin);
        RequesterEntity RequesterEntity = persistence.findByLogin(requestersLogin);
        if (RequesterEntity == null) {
            LOGGER.log(Level.SEVERE, "El solicitante con el login = {0} no existe", requestersLogin);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el solicitante con login = {0}", requestersLogin);
        return RequesterEntity;
    }

    /**
     * Actualiza la información de una instancia de Requester.
     *
     * @param requesterId Identificador de la instancia a actualizar
     * @param requesterEntity Instancia de RequesterEntity con los nuevos datos.
     * @return Instancia de RequesterEntity con los datos actualizados.
     * @throws BusinessLogicException si el la instancia con los nuevos datos
     * tiene datos inválidos.
     */
    public RequesterEntity updateRequester(Long requesterId, RequesterEntity requesterEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el solicitante con id = {0}", requesterId);
        if (requesterEntity.getName() == null) {
            throw new BusinessLogicException("El nombre del solicitante está vacío");
        }
        if (requesterEntity.getLogin() == null) {
            throw new BusinessLogicException("El login del solicitante está vacío");
        }
        if (requesterEntity.getEmail() == null) {
            throw new BusinessLogicException("El email del solicitante está vacío");
        }
        if (requesterEntity.getPhone() == null) {
            throw new BusinessLogicException("El teléfono del solicitante está vacío");
        }

        if (!validatePhone(requesterEntity.getPhone())) {
            throw new BusinessLogicException("El teléfono es inválido");
        }
        if (getRequester(requesterId).getLogin().equalsIgnoreCase(requesterEntity.getLogin()) && persistence.findByLogin(requesterEntity.getLogin()) != null) {
            throw new BusinessLogicException("El login ya existe");
        }

        RequesterEntity newEntity = persistence.update(requesterEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el solicitante con id = {0}", requesterEntity.getId());
        return newEntity;
    }

    /**
     * Elimina una instancia de Requester de la base de datos.
     *
     * @param requesterId Identificador de la instancia a eliminar.
     */
    public void deleteRequester(Long requesterId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el solicitante con id = {0}", requesterId);
        persistence.delete(requesterId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el solicitante con id = {0}", requesterId);
    }

    /**
     * Método que verifica si un teléfono es válido
     *
     * @param phone El teléfono a verificar
     * @return Si el teléfono es válido
     */
    private boolean validatePhone(String phone) {
        boolean f = true;
        for (int i = 0; i < phone.length(); i++) {
            if (!(phone.charAt(i) >= '0' && phone.charAt(i) <= '9')) {
                f = false;
            }
        }
        return f;
    }
}
