/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.DeveloperPersistence;
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
public class DeveloperLogic {

    private static final Logger LOGGER = Logger.getLogger(DeveloperLogic.class.getName());

    @Inject
    private DeveloperPersistence persistence;

    /**
     * Se encarga de crear un DeveloperEntity en la base de datos.
     *
     * @param developer Objeto de DeveloperEntity con los datos nuevos
     * @return Objeto de DeveloperEntity con los datos nuevos y su ID.
     * @throws BusinessLogicException si el developer tiene datos inválidos.
     */
    public DeveloperEntity createDeveloper(DeveloperEntity developer) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del desarrollador");
        if (developer.getLogin() == null) {
            throw new BusinessLogicException("El login del desarrollador está vacío");
        }
        if (developer.getEmail() == null) {
            throw new BusinessLogicException("El email del desarrollador está vacío");
        }
        if (developer.getPhone() == null) {
            throw new BusinessLogicException("El teléfono del desarrollador está vacío");
        }
        if (developer.getType() == null) {
            throw new BusinessLogicException("El tipo del desarrollador está vacío");
        }

        if (persistence.findByLogin(developer.getLogin()) != null) {
            throw new BusinessLogicException("El login ya existe");
        }
        //if(validatePhone(user.getPhone()))
        //  throw new BusinessLogicException("El teléfono es inválido");

        developer = persistence.create(developer);
        LOGGER.log(Level.INFO, "Termina proceso de creación del desarrollador");
        return developer;
    }

    /**
     * Obtiene la lista de los registros de Developer.
     *
     * @return Colección de objetos de DeveloperEntity.
     */
    public List<DeveloperEntity> getDevelopers() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los desarrolladores");
        List<DeveloperEntity> developers = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los desarrolladores");
        return developers;
    }

    /**
     * Obtiene los datos de una instancia de Developer a partir de su ID.
     *
     * @param developerId Identificador de la instancia a consultar
     * @return Instancia de DeveloperEntity con los datos del Developer
     * consultado.
     */
    public DeveloperEntity getDeveloper(Long developerId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el desarrollador con id = {0}", developerId);
        DeveloperEntity DeveloperEntity = persistence.find(developerId);
        if (DeveloperEntity == null) {
            LOGGER.log(Level.SEVERE, "El desarrollador con el id = {0} no existe", developerId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el desarrollador con id = {0}", developerId);
        return DeveloperEntity;
    }

    /**
     * Actualiza la información de una instancia de Developer.
     *
     * @param developerId Identificador de la instancia a actualizar
     * @param developerEntity Instancia de DeveloperEntity con los nuevos datos.
     * @return Instancia de DeveloperEntity con los datos actualizados.
     * @throws BusinessLogicException si el la instancia con los nuevos datos
     * tiene datos inválidos.
     */
    public DeveloperEntity updateDeveloper(Long developerId, DeveloperEntity developerEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el desarrollador con id = {0}", developerId);
        if (developerEntity.getLogin() == null) {
            throw new BusinessLogicException("El login del desarrollador está vacío");
        }
        if (developerEntity.getEmail() == null) {
            throw new BusinessLogicException("El email del desarrollador está vacío");
        }
        if (developerEntity.getPhone() == null) {
            throw new BusinessLogicException("El teléfono del desarrollador está vacío");
        }

        if (!persistence.find(developerId).getLogin().equalsIgnoreCase(developerEntity.getLogin())
                && persistence.findByLogin(developerEntity.getLogin()) != null) {
            throw new BusinessLogicException("El login ya existe");
        }

        List<ProjectEntity> leadingProjects = getDeveloper(developerId).getLeadingProjects();
        if (leadingProjects != null && !leadingProjects.isEmpty() && developerEntity.getType().equals(DeveloperEntity.DeveloperType.Developer)) {
            throw new BusinessLogicException("El desarrollador no puede dejar de ser lider si está liderando proyectos");
        }
        //if(validatePhone(user.getPhone()))
        //  throw new BusinessLogicException("El teléfono es inválido");

        DeveloperEntity newEntity = persistence.update(developerEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el desarrollador con id = {0}", developerEntity.getId());
        return newEntity;
    }

    /**
     * Elimina una instancia de Developer de la base de datos.
     *
     * @param developerId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el desarrollador tiene proyectos
     * asosciados.
     */
    public void deleteDeveloper(Long developerId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el desarrollador con id = {0}", developerId);
        List<ProjectEntity> projects = getDeveloper(developerId).getProjects();
        List<ProjectEntity> leadingProjects = getDeveloper(developerId).getLeadingProjects();
        
        if (projects != null && !projects.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el desarrollador con id = " + developerId + " porque tiene proyectos asociados");
        }
        if (leadingProjects != null && !leadingProjects.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el desarrollador con id = " + developerId + " porque esta liderando proyectos asociados");
        }
        persistence.delete(developerId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el desarrollador con id = {0}", developerId);
    }

    //private boolean validatePhone(Integer phone) {
    //  return !(phone == null || Long.SIZE != 9);
    //}
}
