/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.resources;

/**
 *
 * @author Estudiante af.orozcog
 */


import java.io.Serializable;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import co.edu.uniandes.csw.sitiosweb.dtos.IterationDTO;

@Path("iterations")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class IterationResource implements Serializable {
    
    private static final Logger LOGGER = Logger.getLogger(IterationResource.class.getName());
    
    
    @POST
    public IterationDTO createIteration(IterationDTO iteration){
        
    }
    
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
