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
 * @developer Nicolás Abondano nf.abondano 201812467
 */
@Stateless
public class DeveloperProjectLogic {

    private static final Logger LOGGER = Logger.getLogger(DeveloperProjectLogic.class.getName());

    @Inject
    private ProjectPersistence projectPersistence;

    @Inject
    private DeveloperPersistence developerPersistence;

    /**
     * Asocia un Project existente a un Developer
     *
     * @param developersId Identificador de la instancia de Developer
     * @param projectsId Identificador de la instancia de Project
     * @return Instancia de ProjectEntity que fue asociada a Developer
     */
    public ProjectEntity addProject(Long developersId, Long projectsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un proyecto al desarrollador con id = {0}", developersId);
        DeveloperEntity developerEntity = developerPersistence.find(developersId);
        ProjectEntity projectEntity = projectPersistence.find(projectsId);
        projectEntity.getDevelopers().add(developerEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un proyecto al desarrollador con id = {0}", developersId);
        return projectPersistence.find(projectsId);
    }

    /**
     * Obtiene una colección de instancias de ProjectEntity asociadas a una
     * instancia de Developer
     *
     * @param developersId Identificador de la instancia de Developer
     * @return Colección de instancias de ProjectEntity asociadas a la instancia de
     * Developer
     */
    public List<ProjectEntity> getProjects(Long developersId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los proyectos del desarrollador con id = {0}", developersId);
        return developerPersistence.find(developersId).getProjects();
    }

    /**
     * Obtiene una instancia de ProjectEntity asociada a una instancia de Developer
     *
     * @param developersId Identificador de la instancia de Developer
     * @param projectsId Identificador de la instancia de Project
     * @return La entidadd de Proyecto del desarrollador
     * @throws BusinessLogicException Si el proyecto no está asociado al desarrollador
     */
    public ProjectEntity getProject(Long developersId, Long projectsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el proyecto con id = {0} del desarrollador con id = " + developersId, projectsId);
        List<ProjectEntity> projects = developerPersistence.find(developersId).getProjects();
        ProjectEntity projectEntity = projectPersistence.find(projectsId);
        int index = projects.indexOf(projectEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el proyecto con id = {0} del desarrollador con id = " + developersId, projectsId);
        if (index >= 0) {
            return projects.get(index);
        }
        throw new BusinessLogicException("El proyecto no está asociado al desarrollador");
    }

    /**
     * Remplaza las instancias de Project asociadas a una instancia de Developer
     *
     * @param developerId Identificador de la instancia de Developer
     * @param projects Colección de instancias de ProjectEntity a asociar a instancia
     * de Developer
     * @return Nueva colección de ProjectEntity asociada a la instancia de Developer
     */
    public List<ProjectEntity> replaceProjects(Long developerId, List<ProjectEntity> projects) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los proyectos asocidos al developer con id = {0}", developerId);
        DeveloperEntity developerEntity = developerPersistence.find(developerId);
        List<ProjectEntity> projectList = projectPersistence.findAll();
        for (ProjectEntity project : projectList) {
            if (projects.contains(project)) {
                if (!project.getDevelopers().contains(developerEntity)) {
                    project.getDevelopers().add(developerEntity);
                }
            } else {
                project.getDevelopers().remove(developerEntity);
            }
        }
        developerEntity.setProjects(projects);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los proyectos asocidos al developer con id = {0}", developerId);
        return developerEntity.getProjects();
    }

    /**
     * Desasocia un Project existente de un Developer existente
     *
     * @param developersId Identificador de la instancia de Developer
     * @param projectsId Identificador de la instancia de Project
     */
    public void removeProject(Long developersId, Long projectsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un proyecto del developer con id = {0}", developersId);
        DeveloperEntity developerEntity = developerPersistence.find(developersId);
        ProjectEntity projectEntity = projectPersistence.find(projectsId);
        projectEntity.getDevelopers().remove(developerEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un proyecto del developer con id = {0}", developersId);
    }
}
