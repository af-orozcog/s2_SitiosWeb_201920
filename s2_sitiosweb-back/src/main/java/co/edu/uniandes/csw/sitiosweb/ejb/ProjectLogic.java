/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
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
       LOGGER.log(Level.INFO, "Creating a new logic project.");
       
        if(pe.getCompany() == null || pe.getCompany().isEmpty()){
            throw new BusinessLogicException("El proyecto no tiene compañia asociada");
        }
        if(pe.getInternalProject() == null){
            throw new BusinessLogicException("El proyecto no dice si es interno o no");
        }
        if(pe.getName() == null){
            throw new BusinessLogicException("El nombre del proyecto es nulo");
        }
        if(persistence.findByName(pe.getName()) != null){
            throw new BusinessLogicException("Un proyecto con el mismo nombre ya existe");
        }
        
        persistence.create(pe);
        LOGGER.log(Level.INFO, "Exiting the creation of the project.");
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
     * Obtiene los datos de una instancia de Requester a partir de su login.
     *
     * @param requestersLogin Identificador de la instancia a consultar
     * @return Instancia de RequesterEntity con los datos del Requester
     * consultado.
     */
    public ProjectEntity getProjectByName(String projectName) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el solicitante con login = {0}", projectName);
        ProjectEntity RequesterEntity = persistence.findByName(projectName);
        if (RequesterEntity == null) {
            LOGGER.log(Level.SEVERE, "El solicitante con el login = {0} no existe", projectName);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el solicitante con login = {0}", projectName);
        return RequesterEntity;
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
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    public ProjectEntity updateProject(Long projectId, ProjectEntity projectEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Updating project with id = {0}.", projectId);
        if(projectEntity.getCompany() == null){
            throw new BusinessLogicException("El proyecto no tiene compañia asociada");
        }
        if(projectEntity.getInternalProject() == null){
            throw new BusinessLogicException("El proyecto no dice si es interno o no");
        }
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
        List<DeveloperEntity> developers = getProject(projectId).getDevelopers();
        if (developers == null || developers.isEmpty()) {
            persistence.delete(projectId);
        }else {
            throw new BusinessLogicException("No se puede borrar el proyecto con id = " + projectId + " porque tiene developers asociados");
        }
                    
        LOGGER.log(Level.INFO, "Exiting the deletion of the project with id = {0}.", projectId);
    }
    
    
}
