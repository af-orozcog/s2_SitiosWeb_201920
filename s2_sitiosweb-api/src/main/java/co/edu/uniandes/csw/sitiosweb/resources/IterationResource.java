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
import javax.ws.rs.GET;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import co.edu.uniandes.csw.sitiosweb.dtos.IterationDTO;
import co.edu.uniandes.csw.sitiosweb.ejb.IterationLogic;
import co.edu.uniandes.csw.sitiosweb.entities.IterationEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import java.util.logging.Level;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;


/**
 * 
 */
@Path("iterations")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class IterationResource implements Serializable {
    
    private static final Logger LOGGER = Logger.getLogger(IterationResource.class.getName());
    
    @Inject
    private IterationLogic iterationLogic;
    
    @POST
    public IterationDTO createIteration(IterationDTO iteration) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "IterationResource createIteration: input: {0}", iteration);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        IterationEntity iterationEntity = iteration.toEntity();
        // Invoca la lógica para crear la editorial nueva
        IterationEntity nuevoIterationEntity = iterationLogic.createIteration(iterationEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        IterationDTO nuevoIterationDTO = new IterationDTO(nuevoIterationEntity);
        LOGGER.log(Level.INFO, "IterationResource createIteration: output: {0}", nuevoIterationDTO);
        return nuevoIterationDTO;
    }
    
    @GET
    public List<IterationDTO> getIterations() {
        LOGGER.info("IterationResource getIterations: input: void");
        List<IterationDTO> listaUsuarios = listEntity2DTO(iterationLogic.getIterations());
        LOGGER.log(Level.INFO, "IterationResource getIterations: output: {0}", listaUsuarios);
        return listaUsuarios;
    }
   
    @GET
    @Path("{iterationsId: \\d+}")
    public IterationDTO getIteration(@PathParam("iterationsId") Long iterationId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "IterationResource getIteration: input: {0}", iterationId);
        IterationEntity iterationEntity = iterationLogic.getIteration(iterationId);
        if (iterationEntity == null) {
            throw new WebApplicationException("El recurso /iteration/" + iterationId + " no existe.", 404);
        }
        IterationDTO detailDTO = new IterationDTO(iterationEntity);
        LOGGER.log(Level.INFO, "IterationResource getIteration: output: {0}", detailDTO);
        return detailDTO;
    }

    @PUT
    @Path("{iterationsId: \\d+}")
    public IterationDTO updateIteration(@PathParam("iterationsId") Long iterationsId, IterationDTO iteration) throws WebApplicationException, BusinessLogicException {
        LOGGER.log(Level.INFO, "IterationResource updateIteration: input: id:{0} , iteration: {1}", new Object[]{iterationsId, iteration});
        iteration.setId(iterationsId);
        if (iterationLogic.getIteration(iterationsId) == null) {
            throw new WebApplicationException("El recurso /iterations/" + iterationsId + " no existe.", 404);
        }
        IterationDTO detailDTO = new IterationDTO(iterationLogic.updateIteration(iterationsId, iteration.toEntity()));
        LOGGER.log(Level.INFO, "IterationResource updateIteration: output: {0}", detailDTO);
        return detailDTO;
    }
    
    
    private List<IterationDTO> listEntity2DTO(List<IterationEntity> entityList) {
        List<IterationDTO> list = new ArrayList<>();
        for (IterationEntity entity : entityList) {
            list.add(new IterationDTO(entity));
        }
        return list;
    }
    
    @DELETE
    @Path("{iterationsId: \\d+}")
    public void deleteIteration(@PathParam("iterationsId") Long iterationsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "IterationResource deleteIteration: input: {0}", iterationsId);
        if (iterationLogic.getIteration(iterationsId) == null) {
            throw new WebApplicationException("El recurso /iterations/" + iterationsId + " no existe.", 404);
        }
        iterationLogic.deleteIteration(iterationsId);
        LOGGER.info("IterationResource deleteIteration: output: void");
    }
    
    
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
