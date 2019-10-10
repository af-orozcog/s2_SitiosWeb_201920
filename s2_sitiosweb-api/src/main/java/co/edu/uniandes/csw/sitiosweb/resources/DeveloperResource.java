/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.resources;

import co.edu.uniandes.csw.sitiosweb.dtos.DeveloperDTO;
import co.edu.uniandes.csw.sitiosweb.dtos.DeveloperDTO;
import co.edu.uniandes.csw.sitiosweb.dtos.DeveloperDetailDTO;
import co.edu.uniandes.csw.sitiosweb.ejb.DeveloperLogic;
import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;

import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.sitiosweb.mappers.WebApplicationExceptionMapper;

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
 * @developer Nicolás Abondano nf.abondano 201812467
 */
@Path("developers")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class DeveloperResource {

    private final static Logger LOGGER = Logger.getLogger(DeveloperResource.class.getName());

    @Inject
    private DeveloperLogic developerLogic;

    /**
     * Crea un nuevo desarrollador con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param developer {@link DeveloperDTO} - EL desarrollador que se desea guardar.
     * @return JSON {@link DeveloperDTO} - El desarrollador guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el login
     * o si el telefono ingresado es inválido.
     */
    @POST
    public DeveloperDTO createDeveloper(DeveloperDTO developer) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "DeveloperResource createDeveloper: input: {0}", developer);
        DeveloperEntity developerEntity = developer.toEntity();
        DeveloperEntity nuevoDeveloperEntity = developerLogic.createDeveloper(developerEntity);
        DeveloperDTO nuevoUsuerDTO = new DeveloperDTO(nuevoDeveloperEntity);
        LOGGER.log(Level.INFO, "DeveloperResource createDeveloper: output: {0}", nuevoUsuerDTO);
        return nuevoUsuerDTO;
    }

    /**
     * Busca y devuelve todos los desarrolladors que existen en la aplicacion.
     *
     * @return JSONArray {@link DeveloperDetailDTO} - Los desarrolladors encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<DeveloperDTO> getDevelopers() {
        LOGGER.info("DeveloperResource getDevelopers: input: void");
        List<DeveloperDTO> listaUsuarios = listEntity2DetailDTO(developerLogic.getDevelopers());
        LOGGER.log(Level.INFO, "DeveloperResource getDevelopers: output: {0}", listaUsuarios);
        return listaUsuarios;
    }

    /**
     * Busca el desarrollador con el id asociado recibido en la URL y lo devuelve.
     *
     * @param developersId Identificador del desarrollador que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link DeveloperDetailDTO} - El desarrollador buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el desarrollador.
     */
    @GET
    @Path("{developersId: \\d+}")
    public DeveloperDTO getDeveloper(@PathParam("developersId") Long developersId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "DeveloperResource getDeveloper: input: {0}", developersId);
        DeveloperEntity developerEntity = developerLogic.getDeveloper(developersId);
        if (developerEntity == null) {
            throw new WebApplicationException("El recurso /developers/" + developersId + " no existe.", 404);
        }
        DeveloperDTO developerDTO = new DeveloperDTO(developerEntity);
        LOGGER.log(Level.INFO, "DeveloperResource getDeveloper: output: {0}", developerDTO);
        return developerDTO;
    }

    /**
     * Actualiza el developer con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param developersId Identificador del developer que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param developer {@link DeveloperDetailDTO} El developer que se desea guardar.
     * @return JSON {@link DeveloperDetailDTO} - El developer guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el developer a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar el desarrollador.
     */
    
    @PUT
    @Path("{developersId: \\d+}")
    public DeveloperDetailDTO updateDeveloper(@PathParam("developersId") Long developersId, DeveloperDetailDTO developer) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "DeveloperResource updateDeveloper: input: developersId: {0} , developer: {1}", new Object[]{developersId, developer});
        developer.setId(developersId);
        if (developerLogic.getDeveloper(developersId) == null) {
            throw new WebApplicationException("El recurso /developers/" + developersId + " no existe.", 404);
        }
        DeveloperDetailDTO detailDTO = new DeveloperDetailDTO(developerLogic.updateDeveloper(developersId, developer.toEntity()));
        LOGGER.log(Level.INFO, "DeveloperResource updateDeveloper: output: {0}", detailDTO);
        return detailDTO;
    }
    
    /**
     * Borra el desarrollador con el id asociado recibido en la URL.
     *
     * @param developersId Identificador del desarrollador que se desea borrar. Este debe ser
     * una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando el desarrollador tiene proyectos liderados asociados.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el desarrollador.
     */
    @DELETE
    @Path("{developersId: \\d+}")
    public void deleteDeveloper(@PathParam("developersId") Long developersId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "DeveloperResource deleteDeveloper: input: {0}", developersId);
        if (developerLogic.getDeveloper(developersId) == null) {
            throw new WebApplicationException("El recurso /developers/" + developersId + " no existe.", 404);
        }
        developerLogic.deleteDeveloper(developersId);
        LOGGER.info("DeveloperResource deleteDeveloper: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos DeveloperEntity a una lista de
     * objetos DeveloperDetailDTO (json)
     *
     * @param entityList corresponde a la lista de desarrolladors de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de desarrolladors en forma DTO (json)
     */
    private List<DeveloperDTO> listEntity2DetailDTO(List<DeveloperEntity> entityList) {
        List<DeveloperDTO> list = new ArrayList<>();
        for (DeveloperEntity entity : entityList) {
            list.add(new DeveloperDTO(entity));
        }
        return list;
    }
}
