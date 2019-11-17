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

/**
 *
 * @author Daniel Galindo Ruiz
 */
@Path("developers")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class DeveloperResource {
    
    /**
     * Atributo para tener conexion con logica.
     */
    @Inject 
    private DeveloperLogic logica;
    
    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = Logger.getLogger(DeveloperResource.class.getName());
    
    /**
     * Metodo para crear un objeto DeveloperEntity
     * @param developer Objeto de DeveloperDTO a crear
     * @return DeveloperDTO nuevo en la app
     * @throws BusinessLogicException si se incumple algua regla de negocio
     */
    @POST
    public DeveloperDTO createDeveloper(DeveloperDTO developer) throws BusinessLogicException{
        DeveloperEntity developerEntity = developer.toEntity();
        developerEntity = logica.createDeveloper(developerEntity);
        return new DeveloperDTO (developerEntity);
    }
   /**
     * Busca y devuelve todos los proyectos que existen en la aplicacion.
     *
     * @return JSONArray {@link DeveloperDetailDTO} - Los proyectos
     * encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<DeveloperDetailDTO> getDevelopers() {
        LOGGER.info("DeveloperResource getDevelopers: input: void");
        List<DeveloperDetailDTO> listaProyectos = listEntity2DetailDTO(logica.getDevelopers());
        LOGGER.log(Level.INFO, "DeveloperResource getDevelopers: output: {0}", listaProyectos);
        return listaProyectos;
    }

    /**
     * Busca el proyecto con el id asociado recibido en la URL y la devuelve.
     *
     * @param developerId Identificador del proyecto que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link DeveloperDetailDTO} - El proyecto buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el proyecto.
     */
    @GET
    @Path("{developersId: \\d+}")
    public DeveloperDetailDTO getDeveloper(@PathParam("developersId") Long developerId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "DeveloperResource getDeveloper: input: {0}", developerId);
        DeveloperEntity developerEntity = logica.getDeveloper(developerId);
        if (developerEntity == null) {
            throw new WebApplicationException("El recurso /developers/" + developerId + " no existe.", 404);
        }
        DeveloperDetailDTO detailDTO = new DeveloperDetailDTO(developerEntity);
        LOGGER.log(Level.INFO, "DeveloperResource getDeveloper: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza el proyecto con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param developerId Identificador del proyecto que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param developer {@link DeveloperDetailDTO} El proyecto que se desea
     * guardar.
     * @return JSON {@link DeveloperDetailDTO} - El proyecto guardada.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el proyecto a
     * actualizar.
     */
    @PUT
    @Path("{developersId: \\d+}")
    public DeveloperDetailDTO updateDeveloper(@PathParam("developersId") Long developerId, DeveloperDetailDTO developer) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "DeveloperResource updateDeveloper: input: id:{0} , developer: {1}", new Object[]{developerId, developer});
        developer.setId(developerId);
        if (logica.getDeveloper(developerId) == null) {
            throw new WebApplicationException("El recurso /developers/" + developerId + " no existe.", 404);
        }
        DeveloperDetailDTO detailDTO = new DeveloperDetailDTO(logica.updateDeveloper(developerId, developer.toEntity()));
        LOGGER.log(Level.INFO, "DeveloperResource updateDeveloper: output: {0}", detailDTO);
        return detailDTO;

    }

    /**
     * Borra el proyecto con el id asociado recibido en la URL.
     *
     * @param developerId Identificador del proyecto que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar el proyecto.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el proyecto.
     */
    @DELETE
    @Path("{developersId: \\d+}")
    public void deleteDeveloper(@PathParam("developersId") Long developerId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "DeveloperResource deleteDeveloper: input: {0}", developerId);
        if (logica.getDeveloper(developerId) == null) {
            throw new WebApplicationException("El recurso /developers/" + developerId + " no existe.", 404);
        }
        logica.deleteDeveloper(developerId);
        LOGGER.info("DeveloperResource deleteDeveloper: output: void");
    }

    /**
     * Method to return a developer's projects by the developer's id.
     * @param developersId - the developer's id
     * @return a DeveloperProjectResource class.
     */
    @Path("{developersId: \\d+}/projects")
    public Class<DeveloperProjectResource> getDeveloperProjectResource(@PathParam("developersId") Long developersId){
        if(logica.getDeveloper(developersId) == null){
            throw new WebApplicationException("El recurso /developer/" + developersId + " no existe", 404);
        }
        return DeveloperProjectResource.class;
    }
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos EditorialEntity a una lista de
     * objetos EditorialDetailDTO (json)
     *
     * @param entityList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<DeveloperDetailDTO> listEntity2DetailDTO(List<DeveloperEntity> entityList) {
        List<DeveloperDetailDTO> list = new ArrayList<>();
        for (DeveloperEntity entity : entityList) {
            list.add(new DeveloperDetailDTO(entity));
        }
        return list;
    }
    
}
