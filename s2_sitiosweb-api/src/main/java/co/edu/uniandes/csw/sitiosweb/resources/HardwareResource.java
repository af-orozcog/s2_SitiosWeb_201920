/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.resources;

import co.edu.uniandes.csw.sitiosweb.dtos.HardwareDTO;
import co.edu.uniandes.csw.sitiosweb.ejb.HardwareLogic;
import co.edu.uniandes.csw.sitiosweb.entities.HardwareEntity;
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
@Path("hardwares")
@Produces("application/json")
@Consumes("application/json")
public class HardwareResource {
    
private static final Logger LOGGER = Logger.getLogger(HardwareResource.class.getName());

    @Inject
    private HardwareLogic hardwareLogic;

    /**
     * Crea un nuevo hardware con la informacion que recibe de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param projectsId El ID del pryecto al cual se le agrega el hardware
     * @param hardware {@link HardwareDTO} - El hardware que se desea guardar.
     * @return JSON {@link HardwareDTO} - El hardware guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el hardware.
     */
    @POST
    public HardwareDTO createHardware(@PathParam("projectsId") Long projectsId, HardwareDTO hardware) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "HardwareResource createHardware: input: {0}", hardware);
        HardwareDTO nuevoHardwareDTO = new HardwareDTO(hardwareLogic.createHardware(projectsId, hardware.toEntity()));
        LOGGER.log(Level.INFO, "HardwareResource createHardware: output: {0}", nuevoHardwareDTO);
        return nuevoHardwareDTO;
    }

    /**
     * Busca y devuelve todas las reseñas que existen en un libro.
     * @param projectsId El ID del libro del cual se buscan las reseñas
     * @return {@link HardwareDTO} - El hardware encontrado en el
     * proyecto. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public HardwareDTO getHardwares(@PathParam("projectsId") Long projectsId) {
        LOGGER.log(Level.INFO, "HardwareResource getHardwares: input: {0}", projectsId);
        HardwareDTO dto = new HardwareDTO(hardwareLogic.getHardwares(projectsId));
        LOGGER.log(Level.INFO, "HardwareResource getHardwares: output: {0}", dto);
        return dto;
    }

    /**
     * Busca y devuelve el hardware con el ID recibido en la URL, relativa a un
     * proyecto.
     *
     * @param projectsId El ID del proyecto del cual se buscan el hardware
     * @param hardwaresId El ID del hardware que se busca
     * @return {@link HardwareDTO} - El hardware encontrado en el proyecto.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el proyecto.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el hardware.
     */
    @GET
    @Path("{hardwaresId: \\d+}")
    public HardwareDTO getHardware(@PathParam("projectsId") Long projectsId, @PathParam("hardwaresId") Long hardwaresId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "HardwareResoursce getHardware: input: {0}", hardwaresId);
        HardwareEntity entity = hardwareLogic.getHardware(projectsId, hardwaresId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /projects/" + projectsId + "/hardwares/" + hardwaresId + " no existe.", 404);
        }
        HardwareDTO hardwareDTO = new HardwareDTO(entity);
        LOGGER.log(Level.INFO, "HardwareResoursce getHardware: output: {0}", hardwaresId);
        return hardwareDTO;
    }

    /**
     * Actualiza una reseña con la informacion que se recibe en el cuerpo de la
     * petición y se regresa el objeto actualizado.
     *
     * @param projectsId
     * @param hardwaresId
     * @param hardware
     * @return JSON {@link HardwareDTO} - La reseña actualizada.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la reseña.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reseña.
     */
    @PUT
    @Path("{hardwaresId: \\d+}")
    public HardwareDTO updateReview(@PathParam("projectsId") Long projectsId, @PathParam("hardwaresId") Long hardwaresId, HardwareDTO hardware) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "HardwareResource updateHardware: input: projectsId: {0} , hardwaresId: {1} , hardware:{2}", new Object[]{projectsId, hardwaresId, hardware});
        hardware.setId(hardwaresId);
        if (hardwaresId.equals(hardwaresId)) {
            throw new BusinessLogicException("Los ids del Hardware no coinciden.");
        }
        HardwareEntity entity = hardwareLogic.getHardware(projectsId, hardwaresId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + projectsId + "/reviews/" + hardwaresId + " no existe.", 404);

        }
        HardwareDTO hardwareDTO = new HardwareDTO(hardwareLogic.updateHardware(projectsId, hardware.toEntity()));
        LOGGER.log(Level.INFO, "HardwareResource updateHardware: output:{0}", hardwareDTO);
        return hardwareDTO;

    }

    /**
     * Borra la reseña con el id asociado recibido en la URL.
     *
     * @param projectsId
     * @param hardwareId
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la reseña.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reseña.
     */
    @DELETE
    @Path("{hardwaresId: \\d+}")
    public void deleteHardware(@PathParam("projectsId") Long projectsId, @PathParam("hardwaresId") Long hardwareId) throws BusinessLogicException {
        HardwareEntity entity = hardwareLogic.getHardware(projectsId, hardwareId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /projects/" + projectsId + "/hardwares/" + hardwareId + " no existe.", 404);
        }
        hardwareLogic.deleteHardware(projectsId, hardwareId);
    }

}
