/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.resources;

import co.edu.uniandes.csw.sitiosweb.dtos.InternalSystemsDTO;
import co.edu.uniandes.csw.sitiosweb.ejb.InternalSystemsLogic;
import co.edu.uniandes.csw.sitiosweb.entities.InternalSystemsEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author s.santosb
 */
@Produces("application/json")
@Consumes("application/json")
public class InternalSystemsResource {
    
    private static final Logger LOGGER = Logger.getLogger(InternalSystemsResource.class.getName());
    
    /**
     * Atributo para relacionarse con la logica
     */
    @Inject
    private InternalSystemsLogic internalSystemsLogic;
    
    @Dependent
    private static final String NOEXISTE = " no existe.";
    
    /**
     * Método para permitir crear iteracion
     * @param projectsId id del projecto al que se esta relacionado
     * @param internalSystems la iteración a ser creada
     * @return el DTO creado
     * @throws BusinessLogicException si la creación de la iteración no sigue las reglas de negocio
     */
    @POST
    public InternalSystemsDTO createInternalSystems(@PathParam("projectsId") Long projectsId,InternalSystemsDTO internalSystems) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "InternalSystemsResource createInternalSystems: input: {0}", internalSystems);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        InternalSystemsEntity internalSystemsEntity = internalSystems.toEntity();
        // Invoca la lógica para crear la editorial nueva
        InternalSystemsEntity nuevoInternalSystemsEntity = internalSystemsLogic.createInternalSystems(projectsId, internalSystemsEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        InternalSystemsDTO nuevoInternalSystemsDTO = new InternalSystemsDTO(nuevoInternalSystemsEntity);
        LOGGER.log(Level.INFO, "InternalSystemsResource createInternalSystems: output: {0}", nuevoInternalSystemsDTO);
        return nuevoInternalSystemsDTO;
    }
    
    /**
     * Método para obetener todas las iteraciones
     * @return una lista con las iteraciones en formato DTO
     */
    @GET
    public List<InternalSystemsDTO> getInternalSystemss(@PathParam("projectsId") Long projectId) {
        LOGGER.info("InternalSystemsResource getInternalSystemss: input: void");
        List<InternalSystemsDTO> listaUsuarios = listEntity2DTO(internalSystemsLogic.getInternalSystems(projectId));
        LOGGER.log(Level.INFO, "InternalSystemsResource getInternalSystemss: output: {0}", listaUsuarios);
        return listaUsuarios;
    }
   
    /**
     * Método que devuelve la iteración con el identificador pasado por parametro
     * @param internalSystemsId el id de la iteración
     * @return la iteración que se solicito en formato DTO
     * @throws WebApplicationException 
     */
    @GET
    @Path("{internalSystemssId: \\d+}")
    public InternalSystemsDTO getInternalSystems(@PathParam("projectsId") Long projectId,@PathParam("internalSystemssId") Long internalSystemssId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "InternalSystemsResource getInternalSystems: input: {0}", internalSystemssId);
        System.out.println("LILILILI");
        InternalSystemsEntity internalSystemsEntity = internalSystemsLogic.getInternalSystems(projectId,internalSystemssId);
        if (internalSystemsEntity == null) {
            throw new WebApplicationException("El recurso /internalSystemss/" + internalSystemssId + NOEXISTE, 404);
        }
        InternalSystemsDTO detailDTO = new InternalSystemsDTO(internalSystemsEntity);
        LOGGER.log(Level.INFO, "InternalSystemsResource getInternalSystems: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Método que permite actualizar la iteración con identificador pasado por parametro
     * @param projectsId
     * @param internalSystemssId el identificador de la iteracion
     * @param internalSystems el nuevo DTO de dicha iteracion
     * @return el nuevo DTO de dicha iteracion
     * @throws WebApplicationException
     * @throws BusinessLogicException si no se han cumplido las reglas de negocio para la modificación de la iteracion
     */
    @PUT
    @Path("{internalSystemssId: \\d+}")
    public InternalSystemsDTO updateInternalSystems(@PathParam("projectsId") Long projectsId, @PathParam("internalSystemssId") Long internalSystemssId, InternalSystemsDTO internalSystems) throws WebApplicationException, BusinessLogicException {
        LOGGER.log(Level.INFO, "InternalSystemsResource updateInternalSystems: input: id:{0} , internalSystems: {1}", new Object[]{internalSystemssId, internalSystems});
        //internalSystems.setId(internalSystemssId);
        if (internalSystemssId.equals(internalSystems.getId())) {
            throw new BusinessLogicException("Los ids del internalSystemss no coinciden.");
        }
        InternalSystemsEntity entity = internalSystemsLogic.getInternalSystems(projectsId, internalSystemssId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /internalSystemss/" + internalSystemssId + NOEXISTE, 404);
        }
        
        InternalSystemsDTO internalSystemsDTO = new InternalSystemsDTO(internalSystemsLogic.updateInternalSystems(projectsId, internalSystems.toEntity()));
        LOGGER.log(Level.INFO, "InternalSystemsResource updateInternalSystems: output: {0}", internalSystemsDTO);
        return internalSystemsDTO;
    }
    
    /**
     * Método que convierte internalSystemsEntities a InternalSystemsDTO
     * @param entityList lista de obejtos InternalSystemsEntity
     * @return una lista con objetos InternalSystemsDTO
     */
    private List<InternalSystemsDTO> listEntity2DTO(List<InternalSystemsEntity> entityList) {
        List<InternalSystemsDTO> list = new ArrayList<>();
        for (InternalSystemsEntity entity : entityList) {
            list.add(new InternalSystemsDTO(entity));
        }
        return list;
    }
    
    /**
     * Método para borrar la iteración con id pasado por parametro
     * @param internalSystemssId el id de la iteración que se quiere borrar
     * @throws BusinessLogicException si se incumplen reglas de negocio para borrar la iteracion
     */
    @DELETE
    @Path("{internalSystemssId: \\d+}")
    public void deleteInternalSystems(@PathParam("projectsId") Long projectsId,@PathParam("internalSystemssId") Long internalSystemssId) throws BusinessLogicException {
        InternalSystemsEntity entity = internalSystemsLogic.getInternalSystems(projectsId, internalSystemssId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /projects/" + projectsId + "/internalSystemss/" + internalSystemssId + NOEXISTE, 404);
        }
        internalSystemsLogic.deleteInternalSystems(projectsId, internalSystemssId);
    }
    
    
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
