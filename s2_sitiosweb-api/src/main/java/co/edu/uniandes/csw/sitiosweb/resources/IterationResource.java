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
 * @author Andres Felipe Orozco Gonzalez 201730058 af.orozcog
 */
@Produces("application/json")
@Consumes("application/json")
public class IterationResource implements Serializable {
    
    private static final Logger LOGGER = Logger.getLogger(IterationResource.class.getName());
    
    /**
     * Atributo para relacionarse con la logica
     */
    @Inject
    private IterationLogic iterationLogic;
    
    /**
     * Método para permitir crear iteracion
     * @param projectsId id del projecto al que se esta relacionado
     * @param iteration la iteración a ser creada
     * @return el DTO creado
     * @throws BusinessLogicException si la creación de la iteración no sigue las reglas de negocio
     */
    @POST
    public IterationDTO createIteration(@PathParam("projectsId") Long projectsId,IterationDTO iteration) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "IterationResource createIteration: input: {0}", iteration);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        IterationEntity iterationEntity = iteration.toEntity();
        // Invoca la lógica para crear la editorial nueva
        IterationEntity nuevoIterationEntity = iterationLogic.createIteration(projectsId, iterationEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        IterationDTO nuevoIterationDTO = new IterationDTO(nuevoIterationEntity);
        LOGGER.log(Level.INFO, "IterationResource createIteration: output: {0}", nuevoIterationDTO);
        return nuevoIterationDTO;
    }
    
    /**
     * Método para obetener todas las iteraciones
     * @return una lista con las iteraciones en formato DTO
     */
    @GET
    public List<IterationDTO> getIterations(@PathParam("projectsId") Long projectId) {
        LOGGER.info("IterationResource getIterations: input: void");
        List<IterationDTO> listaUsuarios = listEntity2DTO(iterationLogic.getIterations(projectId));
        LOGGER.log(Level.INFO, "IterationResource getIterations: output: {0}", listaUsuarios);
        return listaUsuarios;
    }
   
    /**
     * Método que devuelve la iteración con el identificador pasado por parametro
     * @param iterationId el id de la iteración
     * @return la iteración que se solicito en formato DTO
     * @throws WebApplicationException 
     */
    @GET
    @Path("{iterationsId: \\d+}")
    public IterationDTO getIteration(@PathParam("projectsId") Long projectId,@PathParam("iterationsId") Long iterationId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "IterationResource getIteration: input: {0}", iterationId);
        System.out.println("LILILILI");
        IterationEntity iterationEntity = iterationLogic.getIteration(projectId,iterationId);
        if (iterationEntity == null) {
            throw new WebApplicationException("El recurso /iterations/" + iterationId + " no existe.", 404);
        }
        IterationDTO detailDTO = new IterationDTO(iterationEntity);
        LOGGER.log(Level.INFO, "IterationResource getIteration: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Método que permite actualizar la iteración con identificador pasado por parametro
     * @param projectsId
     * @param iterationsId el identificador de la iteracion
     * @param iteration el nuevo DTO de dicha iteracion
     * @return el nuevo DTO de dicha iteracion
     * @throws WebApplicationException
     * @throws BusinessLogicException si no se han cumplido las reglas de negocio para la modificación de la iteracion
     */
    @PUT
    @Path("{iterationsId: \\d+}")
    public IterationDTO updateIteration(@PathParam("projectsId") Long projectsId, @PathParam("iterationsId") Long iterationsId, IterationDTO iteration) throws WebApplicationException, BusinessLogicException {
        LOGGER.log(Level.INFO, "IterationResource updateIteration: input: id:{0} , iteration: {1}", new Object[]{iterationsId, iteration});
        //iteration.setId(iterationsId);
        if (iterationsId.equals(iteration.getId())) {
            throw new BusinessLogicException("Los ids del iterations no coinciden.");
        }
        if (iterationLogic.getIteration(projectsId,iterationsId) == null) {
            throw new WebApplicationException("El recurso /iterations/" + iterationsId + " no existe.", 404);
        }
        IterationEntity entity = iterationLogic.getIteration(projectsId, iterationsId);
        
        IterationDTO detailDTO = new IterationDTO(iterationLogic.updateIteration(projectsId, iteration.toEntity()));
        LOGGER.log(Level.INFO, "IterationResource updateIteration: output: {0}", detailDTO);
        return detailDTO;
    }
    
    /**
     * Método que convierte iterationEntities a IterationDTO
     * @param entityList lista de obejtos IterationEntity
     * @return una lista con objetos IterationDTO
     */
    private List<IterationDTO> listEntity2DTO(List<IterationEntity> entityList) {
        List<IterationDTO> list = new ArrayList<>();
        for (IterationEntity entity : entityList) {
            list.add(new IterationDTO(entity));
        }
        return list;
    }
    
    /**
     * Método para borrar la iteración con id pasado por parametro
     * @param iterationsId el id de la iteración que se quiere borrar
     * @throws BusinessLogicException si se incumplen reglas de negocio para borrar la iteracion
     */
    @DELETE
    @Path("{iterationsId: \\d+}")
    public void deleteIteration(@PathParam("projectsId") Long projectsId,@PathParam("iterationsId") Long iterationsId) throws BusinessLogicException {
        IterationEntity entity = iterationLogic.getIteration(projectsId, iterationsId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + projectsId + "/reviews/" + iterationsId + " no existe.", 404);
        }
        iterationLogic.deleteIteration(projectsId, iterationsId);
    }
    
    
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
