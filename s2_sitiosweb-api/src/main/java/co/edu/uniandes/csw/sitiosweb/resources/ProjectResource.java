/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.resources;

import co.edu.uniandes.csw.sitiosweb.dtos.ProjectDTO;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
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
    
    private static final Logger LOGGER = Logger.getLogger(ProjectResource.class.getName());
    
    @POST
    public ProjectDTO createProject(ProjectDTO project){
        return project;
    }
}
