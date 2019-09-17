/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.resources;

import co.edu.uniandes.csw.sitiosweb.dtos.UserDTO;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@Path("users")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class UserResource {
    private final static Logger LOGGER = Logger.getLogger(UserResource.class.getName());
    
    @POST
    public UserDTO createUser(UserDTO user){
        return user;
    }
}
