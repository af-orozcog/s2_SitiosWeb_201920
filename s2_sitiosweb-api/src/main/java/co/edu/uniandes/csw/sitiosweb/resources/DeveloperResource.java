/*
 * To change this license header, choose License Headers in Developer Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.resources;

import co.edu.uniandes.csw.sitiosweb.dtos.DeveloperDTO;
import co.edu.uniandes.csw.sitiosweb.dtos.DeveloperDetailDTO;
import co.edu.uniandes.csw.sitiosweb.ejb.DeveloperLogic;
import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
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
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el recurso "developers".
 *
 * @developer ISIS2603
 * @version 1.0
 */
@Path("/developers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class DeveloperResource {

    private static final Logger LOGGER = Logger.getLogger(DeveloperResource.class.getName());

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
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @POST
    public DeveloperDTO createDeveloper(DeveloperDTO developer) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "DeveloperResource createDeveloper: input: {0}", developer);
        DeveloperDTO developerDTO = new DeveloperDTO(developerLogic.createDeveloper(developer.toEntity()));
        LOGGER.log(Level.INFO, "DeveloperResource createDeveloper: output: {0}", developerDTO);
        return developerDTO;
    }

    /**
     * Busca y devuelve todos los desarrolladores que existen en la aplicacion.
     *
     * @return JSONArray {@link DeveloperDetailDTO} - Los desarrolladores encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<DeveloperDetailDTO> getDevelopers() {
        LOGGER.info("DeveloperResource getDevelopers: input: void");
        List<DeveloperDetailDTO> listaDevelopers = listEntity2DTO(developerLogic.getDevelopers());
        LOGGER.log(Level.INFO, "DeveloperResource getDevelopers: output: {0}", listaDevelopers);
        return listaDevelopers;
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
    public DeveloperDetailDTO getDeveloper(@PathParam("developersId") Long developersId) {
        LOGGER.log(Level.INFO, "DeveloperResource getDeveloper: input: {0}", developersId);
        DeveloperEntity developerEntity = developerLogic.getDeveloper(developersId);
        if (developerEntity == null) {
            throw new WebApplicationException("El recurso /developers/" + developersId + " no existe.", 404);
        }
        DeveloperDetailDTO detailDTO = new DeveloperDetailDTO(developerEntity);
        LOGGER.log(Level.INFO, "DeveloperResource getDeveloper: output: {0}", detailDTO);
        return detailDTO;
    }
    
        /**
     * Busca el desarrollador con el id asociado recibido en la URL y lo devuelve.
     *
     * @param developersLogin Identificador del desarrollador que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link DeveloperDetailDTO} - El desarrollador buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el desarrollador.
     */
    @GET
    @Path("/{developersLogin}")
    public DeveloperDetailDTO getDeveloperByLogin(@PathParam("developersLogin") String developersLogin) {
        LOGGER.log(Level.INFO, "DeveloperResource getDeveloper: input: {0}", developersLogin);
        DeveloperEntity developerEntity = developerLogic.getDeveloperByLogin(developersLogin);
        if (developerEntity == null) {
            throw new WebApplicationException("El recurso /developers/login" + developersLogin + " no existe.", 404);
        }
        DeveloperDetailDTO detailDTO = new DeveloperDetailDTO(developerEntity);
        LOGGER.log(Level.INFO, "DeveloperResource getDeveloper: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza el desarrollador con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param developersId Identificador del desarrollador que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param developer {@link DeveloperDetailDTO} El desarrollador que se desea guardar.
     * @return JSON {@link DeveloperDetailDTO} - El desarrollador guardado.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el desarrollador a
     * actualizar.
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
     * @param developersId Identificador del desarrollador que se desea borrar. Este debe
     * ser una cadena de dígitos.
     * @throws co.edu.uniandes.csw.projectstore.exceptions.BusinessLogicException
     * si el desarrollador tiene proyectos asociados
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el desarrollador a borrar.
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
     * Conexión con el servicio de proyectos para un desarrollador.
     * {@link DeveloperProjectsResource}
     *
     * Este método conecta la ruta de /developers con las rutas de /projects que
     * dependen del desarrollador, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de los proyectos.
     *
     * @param developersId El ID del desarrollador con respecto al cual se accede al
     * servicio.
     * @return El servicio de Libros para ese desarrollador en paricular.
     */
    @Path("{developersId: \\d+}/projects")
    public Class<DeveloperProjectResource> getDeveloperProjectsResource(@PathParam("developersId") Long developersId) {
        if (developerLogic.getDeveloper(developersId) == null) {
            throw new WebApplicationException("El recurso /developers/" + developersId + " no existe.", 404);
        }
        return DeveloperProjectResource.class;
    }

    /**
     * Convierte una lista de DeveloperEntity a una lista de DeveloperDetailDTO.
     *
     * @param entityList Lista de DeveloperEntity a convertir.
     * @return Lista de DeveloperDetailDTO convertida.
     */
    private List<DeveloperDetailDTO> listEntity2DTO(List<DeveloperEntity> entityList) {
        List<DeveloperDetailDTO> list = new ArrayList<>();
        for (DeveloperEntity entity : entityList) {
            list.add(new DeveloperDetailDTO(entity));
        }
        return list;
    }
}