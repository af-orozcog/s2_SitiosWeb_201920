/*
 * To change this license header, choose License Headers in Developer Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.ProjectPersistence;
import co.edu.uniandes.csw.sitiosweb.persistence.DeveloperPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 */
@Stateless
public class DeveloperProjectLogic {

    
    private static final Logger LOGGER = Logger.getLogger(DeveloperProjectLogic.class.getName());

    @Inject
    private DeveloperPersistence developerPersistence;

    @Inject
    private ProjectPersistence projectPersistence;

    /**
     * Asocia un Project existente a un Developer
     *
     * @param developersId Identificador de la instancia de Developer
     * @param projectsId Identificador de la instancia de Project
     * @return Instancia de ProjectEntity que fue asociada a Developer
     */
    public ProjectEntity addProject(Long developersId, Long projectsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un proyecto al desarrollador con id = {0}", developersId);
        ProjectEntity projectEntity = projectPersistence.find(projectsId);
        DeveloperEntity developerEntity = developerPersistence.find(developersId);
        developerEntity.getProjects().add(projectEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un proyecto al desarrollador con id = {0}", developersId);
        return projectPersistence.find(projectsId);
    }

    /**
     * Obtiene una colección de instancias de ProjectEntity asociadas a una
     * instancia de Developer
     *
     * @param developersId Identificador de la instancia de Developer
     * @return Colección de instancias de ProjectEntity asociadas a la instancia
     * de Developer
     */
    public List<ProjectEntity> getProjects(Long developersId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los proyectoes del desarrollador con id = {0}", developersId);
        return developerPersistence.find(developersId).getProjects();
    }

    /**
     * Obtiene una instancia de ProjectEntity asociada a una instancia de Developer
     *
     * @param developersId Identificador de la instancia de Developer
     * @param projectsId Identificador de la instancia de Project
     * @return La entidad del Proyecto asociada al desarrollador
     */
    public ProjectEntity getProject(Long developersId, Long projectsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un proyecto del desarrollador con id = {0}", developersId);
        List<ProjectEntity> projects = developerPersistence.find(developersId).getProjects();
        ProjectEntity projectEntity = projectPersistence.find(projectsId);
        int index = projects.indexOf(projectEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar un proyecto del desarrollador con id = {0}", developersId);
        if (index >= 0) {
            return projects.get(index);
        }
        return null;
    }
    
    /**
     * Remplaza las instancias de Project asociadas a una instancia de Developer
     *
     * @param developersId Identificador de la instancia de Developer
     * @param list Colección de instancias de ProjectEntity a asociar a instancia
     * de Developer
     * @return Nueva colección de ProjectEntity asociada a la instancia de Developer
     */
    public List<ProjectEntity> replaceProjects(Long developersId, List<ProjectEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los proyectoes del desarrollador con id = {0}", developersId);
        DeveloperEntity developerEntity = developerPersistence.find(developersId);
        developerEntity.setProjects(list);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los proyectoes del desarrollador con id = {0}", developersId);
        return developerPersistence.find(developersId).getProjects();
    }

    /**
     * Desasocia un Project existente de un Developer existente
     *
     * @param developersId Identificador de la instancia de Developer
     * @param projectsId Identificador de la instancia de Project
     */
    public void removeProject(Long developersId, Long projectsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un proyecto del desarrollador con id = {0}", developersId);
        ProjectEntity projectEntity = projectPersistence.find(projectsId);
        DeveloperEntity developerEntity = developerPersistence.find(developersId);
        developerEntity.getProjects().remove(projectEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un proyecto del desarrollador con id = {0}", developersId);
    }
}
