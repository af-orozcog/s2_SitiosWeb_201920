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
public class HardwareResource {    
    
    private static final Logger LOGGER = Logger.getLogger(HardwareResource.class.getName());
    
    @Dependent
    private static final String NOEXISTE = " no existe.";
    
    /**
     * Atributo para relacionarse con la logica
     */
    @Inject
    private HardwareLogic hardwareLogic;
    
    /**
     * Método para permitir crear iteracion
     * @param projectsId id del projecto al que se esta relacionado
     * @param hardware la iteración a ser creada
     * @return el DTO creado
     * @throws BusinessLogicException si la creación de la iteración no sigue las reglas de negocio
     */
    @POST
    public HardwareDTO createHardware(@PathParam("projectsId") Long projectsId,HardwareDTO hardware) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "HardwareResource createHardware: input: {0}", hardware);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        HardwareEntity hardwareEntity = hardware.toEntity();
        // Invoca la lógica para crear la editorial nueva
        HardwareEntity nuevoHardwareEntity = hardwareLogic.createHardware(projectsId, hardwareEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        HardwareDTO nuevoHardwareDTO = new HardwareDTO(nuevoHardwareEntity);
        LOGGER.log(Level.INFO, "HardwareResource createHardware: output: {0}", nuevoHardwareDTO);
        return nuevoHardwareDTO;
    }
    
    /**
     * Método para obetener todas las iteraciones
     * @param projectId
     * @return una lista con las iteraciones en formato DTO
     */
    @GET
    public HardwareDTO getHardware(@PathParam("projectsId") Long projectId) {
        LOGGER.info("HardwareResource getHardwares: input: void");
        HardwareDTO listaUsuarios = new HardwareDTO(hardwareLogic.getHardware(projectId));
        LOGGER.log(Level.INFO, "HardwareResource getHardwares: output: {0}", listaUsuarios);
        return listaUsuarios;
    }
   
    /**
     * Método que devuelve la iteración con el identificador pasado por parametro
     * @param projectId
     * @param hardwaresId
     * @return la iteración que se solicito en formato DTO
     * @throws WebApplicationException 
     */
    @GET
    @Path("{hardwaresId: \\d+}")
    public HardwareDTO getHardware(@PathParam("projectsId") Long projectId,@PathParam("hardwaresId") Long hardwaresId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "HardwareResource getHardware: input: {0}", hardwaresId);
        System.out.println("LILILILI");
        HardwareEntity hardwareEntity = hardwareLogic.getHardware(projectId,hardwaresId);
        if (hardwareEntity == null) {
            throw new WebApplicationException("El recurso /hardwares/" + hardwaresId + NOEXISTE, 404);
        }
        HardwareDTO detailDTO = new HardwareDTO(hardwareEntity);
        LOGGER.log(Level.INFO, "HardwareResource getHardware: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Método que permite actualizar la iteración con identificador pasado por parametro
     * @param projectsId
     * @param hardwaresId el identificador de la iteracion
     * @param hardware el nuevo DTO de dicha iteracion
     * @return el nuevo DTO de dicha iteracion
     * @throws WebApplicationException
     * @throws BusinessLogicException si no se han cumplido las reglas de negocio para la modificación de la iteracion
     */
    @PUT
    @Path("{hardwaresId: \\d+}")
    public HardwareDTO updateHardware(@PathParam("projectsId") Long projectsId, @PathParam("hardwaresId") Long hardwaresId, HardwareDTO hardware) throws WebApplicationException, BusinessLogicException {
        LOGGER.log(Level.INFO, "HardwareResource updateHardware: input: id:{0} , hardware: {1}", new Object[]{hardwaresId, hardware});
        //hardware.setId(hardwaresId);
        if (hardwaresId.equals(hardware.getId())) {
            throw new BusinessLogicException("Los ids del hardwares no coinciden.");
        }
        HardwareEntity entity = hardwareLogic.getHardware(projectsId, hardwaresId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /hardwares/" + hardwaresId + NOEXISTE, 404);
        }
        
        HardwareDTO hardwareDTO = new HardwareDTO(hardwareLogic.updateHardware(projectsId, hardware.toEntity()));
        LOGGER.log(Level.INFO, "HardwareResource updateHardware: output: {0}", hardwareDTO);
        return hardwareDTO;
    }
        
    /**
     * Método para borrar la iteración con id pasado por parametro
     * @param hardwaresId el id de la iteración que se quiere borrar
     * @throws BusinessLogicException si se incumplen reglas de negocio para borrar la iteracion
     */
    @DELETE
    @Path("{hardwaresId: \\d+}")
    public void deleteHardware(@PathParam("projectsId") Long projectsId,@PathParam("hardwaresId") Long hardwaresId) throws BusinessLogicException {
        HardwareEntity entity = hardwareLogic.getHardware(projectsId, hardwaresId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /projects/" + projectsId + "/hardwares/" + hardwaresId + NOEXISTE, 404);
        }
        hardwareLogic.deleteHardware(projectsId, hardwaresId);
    }
    
    
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }


}
