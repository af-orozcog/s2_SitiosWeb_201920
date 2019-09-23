/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.resources;

import co.edu.uniandes.csw.sitiosweb.dtos.ProjectDTO;
import co.edu.uniandes.csw.sitiosweb.ejb.ProjectLogic;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Daniel Galindo Ruiz
 */
@Path("projects")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ProjectResource {
    
    @Inject 
    private ProjectLogic logica;
    private static final Logger LOGGER = Logger.getLogger(ProjectResource.class.getName());
    
    @POST
    public ProjectDTO createProject(ProjectDTO project) throws BusinessLogicException{
        ProjectEntity projectEntity = project.toEntity();
        projectEntity = logica.createProject(projectEntity);
        return new ProjectDTO (projectEntity);
    }
}
