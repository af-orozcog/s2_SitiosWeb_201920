/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.resources;

import co.edu.uniandes.csw.sitiosweb.dtos.HardwareDTO;
import co.edu.uniandes.csw.sitiosweb.dtos.InternalSystemsDTO;
import co.edu.uniandes.csw.sitiosweb.ejb.HardwareLogic;
import co.edu.uniandes.csw.sitiosweb.ejb.InternalSystemsLogic;
import co.edu.uniandes.csw.sitiosweb.entities.HardwareEntity;
import co.edu.uniandes.csw.sitiosweb.entities.InternalSystemsEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
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

/**
 *
 * @author s.santosb
 */
@Path("internalSystems")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class InternalSystemsResource {
    
    private static final Logger LOGGER = Logger.getLogger(InternalSystemsResource.class.getName());

    @Inject
    private InternalSystemsLogic internalSystemsLogic;

    /**
     * Crea un nuevo internalSystem con la informacion que recibe de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param internalSystem {@link InternalSystemsDTO} - El internalSystem que se desea guardar.
     * @return JSON {@link HardwareDTO} - El hardware guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el hardware.
     */
    @POST
    public InternalSystemsDTO createInternalSystems(InternalSystemsDTO internalSystem) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "InternalSystemsResource createInternalSystems: input: {0}", internalSystem);
        InternalSystemsDTO internalSystemDTO = new InternalSystemsDTO(internalSystemsLogic.createInternalSystems( internalSystem.toEntity()));
        LOGGER.log(Level.INFO, "InternalSystemsResource createInternalSystems: output: {0}", internalSystemDTO);
        return internalSystemDTO;
    }

    /**
     * Busca y devuelve todos los internalSystems que existen en un project.
     * @param projectsId El ID del project del cual se buscan los internalSystems
     * @return JSONArray {@link InternalSystemsDTO} - Los internalSystems encontrados en el
     * project. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<InternalSystemsDTO> getInternalSystems(@PathParam("projectsId") Long projectsId) {
        LOGGER.log(Level.INFO, "InternalSystemsResource getInternalSystems: input: {0}", projectsId);
        List<InternalSystemsDTO> dto = this.listEntity2DetailDTO(internalSystemsLogic.getInternalSystemsByProject(projectsId));
        LOGGER.log(Level.INFO, "InternalSystemsResource getInternalSystems: output: {0}", dto);
        return dto;
    }

    /**
     * Busca y devuelve el internalSystem con el ID recibido en la URL, relativa a un
     * proyecto.
     *
     * @param projectsId El ID del proyecto del cual se buscan el hardware
     * @param internalSystemsId El ID del internalSystem que se busca
     * @return {@link InternalSystemDTO} - El internalSystem encontrado en el proyecto.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el proyecto.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el internalSystem.
     */
    @GET
    @Path("{internalSystemsId: \\d+}")
    public InternalSystemsDTO getInternalSystems(@PathParam("projectsId") Long projectsId, @PathParam("internalSystemsId") Long internalSystemsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "InternalSystemsResource getInternalSystems: input: {0}", internalSystemsId);
        InternalSystemsEntity entity = internalSystemsLogic.getInternalSystems(internalSystemsId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /projects/" + projectsId + "/internalSystems/" + internalSystemsId + " no existe.", 404);
        }
        InternalSystemsDTO internalDTO = new InternalSystemsDTO(entity);
        LOGGER.log(Level.INFO, "InternalSystemsResource getInternalSystems: output: {0}", internalSystemsId);
        return internalDTO;
    }

    /**
     * Actualiza una reseña con la informacion que se recibe en el cuerpo de la
     * petición y se regresa el objeto actualizado.
     *
     * @param projectsId El ID del libro del cual se guarda la reseña
     * @param internalSystemsId El ID de la reseña que se va a actualizar
     * @param internalSystem {@link InternalSystemsDTO} - La reseña que se desea guardar.
     * @return JSON {@link ReviewDTO} - La reseña actualizada.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la reseña.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reseña.
     */
    @PUT
    @Path("{internalSystemsId: \\d+}")
    public InternalSystemsDTO updateInternalSystems(@PathParam("projectsId") Long projectsId, @PathParam("internalSystemsId") Long internalSystemsId, InternalSystemsDTO internalSystem) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "InternalSystemsResource updateInternalSystems: input: projectsId: {0} , internalSystemsId: {1} , internalSystem:{2}", new Object[]{projectsId, internalSystemsId, internalSystem});
        if (internalSystemsId.equals(internalSystem.getId())) {
            throw new BusinessLogicException("Los ids del InternalSystem no coinciden.");
        }
        InternalSystemsEntity entity = internalSystemsLogic.getInternalSystems(internalSystemsId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /projects/" + projectsId + "/internalSystems/" + internalSystemsId + " no existe.", 404);

        }
        InternalSystemsDTO internalDTO = new InternalSystemsDTO(internalSystemsLogic.updateInternalSystems(projectsId, internalSystem.toEntity()));
        LOGGER.log(Level.INFO, "InternalSystemsResource updateInternalSystems: output:{0}", internalDTO);
        return internalDTO;

    }

    /**
     * Borra la reseña con el id asociado recibido en la URL.
     *
     * @param projectsId
     * @param internalSystemsId
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar el project.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el internalSystem.
     */
    @DELETE
    @Path("{internalSystemsId: \\d+}")
    public void deleteInternalSystems(@PathParam("projectsId") Long projectsId, @PathParam("internalSystemsId") Long internalSystemsId) throws BusinessLogicException {
        InternalSystemsEntity entity = internalSystemsLogic.getInternalSystems(internalSystemsId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /projects/" + projectsId + "/internalSystems/" + internalSystemsId + " no existe.", 404);
        }
        internalSystemsLogic.deleteInternalSystems(internalSystemsId);
    }
    
    private List<InternalSystemsDTO> listEntity2DetailDTO(List<InternalSystemsEntity> entityList) {
        List<InternalSystemsDTO> list = new ArrayList<>();
        for (InternalSystemsEntity entity : entityList) {
            list.add(new InternalSystemsDTO(entity));
        }
        return list;
    }

}
