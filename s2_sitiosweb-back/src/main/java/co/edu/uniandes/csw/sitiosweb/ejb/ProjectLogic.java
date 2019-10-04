/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.ProjectPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Galindo Ruiz
 */
@Stateless
public class ProjectLogic {
    
    /**
     * Peristence class for the logic to use
     */
    @Inject
    private ProjectPersistence persistence;
    
        /**
     * The project's logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ProjectLogic.class.getName());
    
    /**
     * Contructor of ProjectEntity that evaluates rules before a ProjectEntity's creation
     * @param pe ProjectEntity to be created
     * @return a new ProjectEntity 
     * @throws BusinessLogicException if any rules are broken
     */
    public ProjectEntity createProject(ProjectEntity pe) throws BusinessLogicException{
       
        if(pe.getCompany() == null){
            throw new BusinessLogicException("El proyecto no tiene compañia asociada");
        }
        if(pe.getInternalProject() == null){
            throw new BusinessLogicException("El proyecto no dice si es interno o no");
        }
        if(persistence.find(pe.getId()) != null){
            throw new BusinessLogicException("El proyecto ya existe");
        }
        LOGGER.log(Level.INFO, "Creating a new logic project.");
        pe = persistence.create(pe);
        LOGGER.log(Level.INFO, "Exiting the creaton of the project.");
        return pe;
    }
     /**
     * Finds all the requests in the database.
     * @return A list with all the requests.
     */
    public List<ProjectEntity> getProjects()
    {
        LOGGER.log(Level.INFO, "Consulting all projects.");
        List<ProjectEntity> list = persistence.findAll();
        LOGGER.log(Level.INFO, "Exiting the consult of all projects.");
        return list;
    }
        /**
     * Finds a specific project in the database.
     * @param projectId Id of the project to find.
     * @return The specific project. Null if it doesn't exist.
     */
    public ProjectEntity getProject(Long projectId)
    {
        LOGGER.log(Level.INFO, "Consulting project with id = {0}.", projectId);
        ProjectEntity projectEntity = persistence.find(projectId);
        if(projectEntity == null)
            LOGGER.log(Level.SEVERE, "The request with id = {0} does not exist.", projectId);
        LOGGER.log(Level.INFO, "Exiting the consult of the request with id = {0}.", projectId);
        return projectEntity;
    }
    
     /**
     * Updates a project in the database.
     * @param projectId  The project's id.
     * @param projectEntity The request to update.
     * @return The updated project.
     */
    public ProjectEntity updateProject(Long projectId, ProjectEntity projectEntity)
    {
        LOGGER.log(Level.INFO, "Updating project with id = {0}.", projectId);
        ProjectEntity newRequestEntity = persistence.update(projectEntity);
        LOGGER.log(Level.INFO, "Exiting the update of the project with id = {0}.", projectId);
        return newRequestEntity;
    }
     /**
     * Deletes the project with the given id.
     * @param projectId The project's id.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    public void deleteProject(Long projectId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Deleting project with id = {0}.", projectId);
        // TODO relationships with Requester and Project.
        persistence.delete(projectId);
        LOGGER.log(Level.INFO, "Exiting the deletion of the project with id = {0}.", projectId);
    }
}
