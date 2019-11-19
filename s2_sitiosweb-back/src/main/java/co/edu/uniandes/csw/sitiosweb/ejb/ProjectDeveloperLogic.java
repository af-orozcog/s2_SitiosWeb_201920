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
 */
@Stateless
public class ProjectDeveloperLogic {

    
    private static final Logger LOGGER = Logger.getLogger(ProjectDeveloperLogic.class.getName());

    @Inject
    private ProjectPersistence projectPersistence;

    @Inject
    private DeveloperPersistence developerPersistence;

    /**
     * Asocia un Developer existente a un Project
     *
     * @param projectsId Identificador de la instancia de Project
     * @param developersId Identificador de la instancia de Developer
     * @return Instancia de DeveloperEntity que fue asociada a Project
     */
    public DeveloperEntity addDeveloper(Long projectsId, Long developersId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un desarrollador al proyecto con id = {0}", projectsId);
        DeveloperEntity developerEntity = developerPersistence.find(developersId);
        ProjectEntity projectEntity = projectPersistence.find(projectsId);
        projectEntity.getDevelopers().add(developerEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un desarrollador al proyecto con id = {0}", projectsId);
        return developerPersistence.find(developersId);
    }

    /**
     * Obtiene una colección de instancias de DeveloperEntity asociadas a una
     * instancia de Project
     *
     * @param projectsId Identificador de la instancia de Project
     * @return Colección de instancias de DeveloperEntity asociadas a la instancia
     * de Project
     */
    public List<DeveloperEntity> getDevelopers(Long projectsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los desarrolladores del proyecto con id = {0}", projectsId);
        return projectPersistence.find(projectsId).getDevelopers();
    }

    /**
     * Obtiene una instancia de DeveloperEntity asociada a una instancia de Project
     *
     * @param projectsId Identificador de la instancia de Project
     * @param developersId Identificador de la instancia de Developer
     * @return La entidad del Desarrollador asociada al proyecto
     */
    public DeveloperEntity getDeveloper(Long projectsId, Long developersId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un desarrollador del proyecto con id = {0}", projectsId);
        List<DeveloperEntity> developers = projectPersistence.find(projectsId).getDevelopers();
        DeveloperEntity developerEntity = developerPersistence.find(developersId);
        int index = developers.indexOf(developerEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar un desarrollador del proyecto con id = {0}", projectsId);
        if (index >= 0) {
            return developers.get(index);
        }
        return null;
    }
    
    /**
     * Remplaza las instancias de Developer asociadas a una instancia de Project
     *
     * @param projectsId Identificador de la instancia de Project
     * @param list Colección de instancias de DeveloperEntity a asociar a instancia
     * de Project
     * @return Nueva colección de DeveloperEntity asociada a la instancia de Project
     */
    public List<DeveloperEntity> replaceDevelopers(Long projectsId, List<DeveloperEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los desarrolladores del proyecto con id = {0}", projectsId);
        ProjectEntity projectEntity = projectPersistence.find(projectsId);
        projectEntity.setDevelopers(list);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los desarrolladores del proyecto con id = {0}", projectsId);
        return projectPersistence.find(projectsId).getDevelopers();
    }

    /**
     * Desasocia un Developer existente de un Project existente
     *
     * @param projectsId Identificador de la instancia de Project
     * @param developersId Identificador de la instancia de Developer
     */
    public void removeDeveloper(Long projectsId, Long developersId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un desarrollador del proyecto con id = {0}", projectsId);
        DeveloperEntity developerEntity = developerPersistence.find(developersId);
        ProjectEntity projectEntity = projectPersistence.find(projectsId);
        projectEntity.getDevelopers().remove(developerEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un desarrollador del proyecto con id = {0}", projectsId);
    }
}
