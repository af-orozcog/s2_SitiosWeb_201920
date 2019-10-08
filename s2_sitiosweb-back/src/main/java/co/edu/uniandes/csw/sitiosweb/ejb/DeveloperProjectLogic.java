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
import co.edu.uniandes.csw.sitiosweb.persistence.ProjectPersistence;
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
public class DeveloperProjectLogic {

    private static final Logger LOGGER = Logger.getLogger(DeveloperProjectLogic.class.getName());

    @Inject
    private ProjectPersistence projectPersistence;

    @Inject
    private DeveloperPersistence developerPersistence;

    /**
     * Asocia un Developer existente a un Project
     *
     * @param projectId Identificador de la instancia de Project
     * @param developerId Identificador de la instancia de Developer
     * @return Instancia de DeveloperEntity que fue asociada a Project
     */
    public DeveloperEntity addDeveloper(Long projectId, Long developerId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un desarrollador al proyecto con id = {0}", projectId);
        DeveloperEntity developerEntity = developerPersistence.find(developerId);
        ProjectEntity projectEntity = projectPersistence.find(projectId);
        projectEntity.getDevelopers().add(developerEntity);
        developerEntity.getProjects().add(projectEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un desarrollador al proyecto con id = {0}", projectId);
        return developerPersistence.find(developerId);
    }

    /**
     * Asocia un Developer lider existente a un Project recién creado
     *
     * @param projectId Identificador de la instancia de Project
     * @param developerId Identificador de la instancia de Developer
     * @return Instancia de DeveloperEntity que fue asociada a Project
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    public DeveloperEntity addLeader(Long projectId, Long developerId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un desarrollador lider al proyecto con id = {0}", projectId);
        DeveloperEntity developerEntity = developerPersistence.find(developerId);
        ProjectEntity projectEntity = projectPersistence.find(projectId);
        if (!developerEntity.getType().equals(DeveloperEntity.DeveloperType.Leader)) {
            throw new BusinessLogicException("No se puede asociar el desarrollador con id = " + developerId + " porque no es un lider");
        }

        projectEntity.setLeader(developerEntity);
        developerEntity.getLeadingProjects().add(projectEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un desarrollador lider al proyecto con id = {0}", projectId);
        return developerPersistence.find(developerId);
    }

    /**
     * Obtiene una colección de instancias de DeveloperEntity asociadas a una
     * instancia de Project
     *
     * @param projectId Identificador de la instancia de Project
     * @return Colección de instancias de DeveloperEntity asociadas a la instancia
     * de Project
     */
    public List<DeveloperEntity> getDevelopers(Long projectId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los desarrolladores del proyecto con id = {0}", projectId);
        return projectPersistence.find(projectId).getDevelopers();
    }

    /**
     * Obtiene una instancia de ProjectEntity asociada a una instancia de Developer
     *
     * @param developerId Identificador de la instancia de Developer
     * @param projectId Identificador de la instancia de Project
     * @return La entidadd de proyecto del desarrollador
     * @throws BusinessLogicException Si el proyecto no está asociado al desarrollador
     */
    public ProjectEntity getProject(Long developerId, Long projectId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el proyecto con id = {0} del desarrollador con id = " + developerId, projectId);
        List<ProjectEntity> projects = developerPersistence.find(developerId).getProjects();
        ProjectEntity projectEntity=  projectPersistence.find(projectId);
        int index = projects.indexOf(projectEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el proyecto con id = {0} del desarrollador con id = " + developerId, projectId);
        if (index >= 0) {
            return projects.get(index);
        }
        throw new BusinessLogicException("El proyecto no está asociado al desarrollador");
    }
    
    /**
     * Remplazar el lider de un project.
     *
     * @param projectId Identificador de la instancia de Project
     * @param developerId Identificador de la instancia de Developer
     * @return Instancia de DeveloperEntity que fue asociada a Project
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    public DeveloperEntity replaceLeader(Long projectId, Long developerId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar proyecto con id = {0}", projectId);
        ProjectEntity projectEntity = projectPersistence.find(projectId);
        DeveloperEntity developerEntity = developerPersistence.find(developerId);
        DeveloperEntity oldLeaderEntity = developerPersistence.find(projectEntity.getLeader().getId());

        if (!developerEntity.getType().equals(DeveloperEntity.DeveloperType.Leader)) {
            throw new BusinessLogicException("No se puede asociar el desarrollador con id = " + developerId + " porque no es un lider");
        }
        projectEntity.setLeader(developerEntity);
        developerEntity.getLeadingProjects().add(projectEntity);
        oldLeaderEntity.getLeadingProjects().remove(projectEntity);

        LOGGER.log(Level.INFO, "Termina proceso de actualizar proyecto con id = {0}", developerEntity.getId());
        return developerEntity;
    }

    /**
     * Borrar un Developer de un proyecto.
     *
     * @param developerId El desarrollador que se desea borrar del proyecto.
     * @param projectId
     */
    public void removeDeveloper(Long developerId, Long projectId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el desarrollador del proyecto con id = {0}", projectId);
        DeveloperEntity developerEntity = developerPersistence.find(developerId);
        ProjectEntity projectEntity = projectPersistence.find(projectId);

        developerEntity.getProjects().remove(projectEntity);
        projectEntity.getDevelopers().remove(developerEntity);

        LOGGER.log(Level.INFO, "Termina proceso de borrar el desarrollador del proyecto con id = {0}", projectId);
    }
}
