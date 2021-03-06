/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.resources;

import co.edu.uniandes.csw.sitiosweb.dtos.ProjectDTO;
import co.edu.uniandes.csw.sitiosweb.dtos.ProjectDetailDTO;
import co.edu.uniandes.csw.sitiosweb.ejb.ProjectLogic;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
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
@Path("projects")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ProjectResource {
    
    /**
     * Atributo para tener conexion con logica.
     */
    @Inject 
    private ProjectLogic logica;
    
    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = Logger.getLogger(ProjectResource.class.getName());
    
    @Dependent
    private static final String NOEXISTE = " no existe.";
    
    @Dependent
    private static final String RECURSONO = "El recurso /projects/";
     
    /**
     * Metodo para crear un objeto ProjectEntity
     * @param project Objeto de ProjectDTO a crear
     * @return ProjectDTO nuevo en la app
     * @throws BusinessLogicException si se incumple algua regla de negocio
     */
    @POST
    public ProjectDTO createProject(ProjectDTO project) throws BusinessLogicException{
        ProjectEntity projectEntity = project.toEntity();
        projectEntity = logica.createProject(projectEntity);
        return new ProjectDTO (projectEntity);
    }
    
   /**
     * Busca y devuelve todos los proyectos que existen en la aplicacion.
     *
     * @return JSONArray {@link ProjectDetailDTO} - Los proyectos
     * encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ProjectDetailDTO> getProjects() {
        LOGGER.info("ProjectResource getProjects: input: void");
        List<ProjectDetailDTO> listaProyectos = listEntity2DetailDTO(logica.getProjects());
        LOGGER.log(Level.INFO, "ProjectResource getProjects: output: {0}", listaProyectos);
        return listaProyectos;
    }

    /**
     * Busca el proyecto con el id asociado recibido en la URL y la devuelve.
     *
     * @param projectId Identificador del proyecto que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link ProjectDetailDTO} - El proyecto buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el proyecto.
     */
    @GET
    @Path("{projectsId: \\d+}")
    public ProjectDetailDTO getProject(@PathParam("projectsId") Long projectId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "ProjectResource getProject: input: {0}", projectId);
        ProjectEntity projectEntity = logica.getProject(projectId);
        if (projectEntity == null) {
            throw new WebApplicationException(RECURSONO + projectId + NOEXISTE, 404);
        }
        ProjectDetailDTO detailDTO = new ProjectDetailDTO(projectEntity);
        LOGGER.log(Level.INFO, "ProjectResource getProject: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza el proyecto con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param projectId Identificador del proyecto que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param project {@link ProjectDetailDTO} El proyecto que se desea
     * guardar.
     * @return JSON {@link ProjectDetailDTO} - El proyecto guardada.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el proyecto a
     * actualizar.
     */
    @PUT
    @Path("{projectsId: \\d+}")
    public ProjectDetailDTO updateProject(@PathParam("projectsId") Long projectId, ProjectDetailDTO project) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProjectResource updateProject: input: id:{0} , project: {1}", new Object[]{projectId, project});
        project.setId(projectId);
        if (logica.getProject(projectId) == null) {
            throw new WebApplicationException(RECURSONO + projectId + NOEXISTE, 404);
        }
        ProjectDetailDTO detailDTO = new ProjectDetailDTO(logica.updateProject(projectId, project.toEntity()));
        LOGGER.log(Level.INFO, "ProjectResource updateProject: output: {0}", detailDTO);
        return detailDTO;

    }

    /**
     * Borra el proyecto con el id asociado recibido en la URL.
     *
     * @param projectId Identificador del proyecto que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar el proyecto.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el proyecto.
     */
    @DELETE
    @Path("{projectsId: \\d+}")
    public void deleteProject(@PathParam("projectsId") Long projectId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProjectResource deleteProject: input: {0}", projectId);
        if (logica.getProject(projectId) == null) {
            throw new WebApplicationException(RECURSONO + projectId + NOEXISTE, 404);
        }
        logica.deleteProject(projectId);
        LOGGER.info("ProjectResource deleteProject: output: void");
    }

    /**
     * Method to return a project's developers by the project's id.
     * @param projectsId - the project's id
     * @return a ProjectDeveloperResource class.
     */
    @Path("{projectsId: \\d+}/developers")
    public Class<ProjectDeveloperResource> getProjectDeveloperResource(@PathParam("projectsId") Long projectsId){
        if(logica.getProject(projectsId) == null){
            throw new WebApplicationException(RECURSONO + projectsId + NOEXISTE, 404);
        }
        return ProjectDeveloperResource.class;
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
    private List<ProjectDetailDTO> listEntity2DetailDTO(List<ProjectEntity> entityList) {
        List<ProjectDetailDTO> list = new ArrayList<>();
        for (ProjectEntity entity : entityList) {
            list.add(new ProjectDetailDTO(entity));
        }
        return list;
    }
    
    
    /**
     * Conexión con el servicio de Iteraciones de un projecto. 
     *
     * Este método conecta la ruta de /project con las rutas de /iterations que
     * dependen del proyecto, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las reseñas.
     *
     * @param projectsId El ID del proyecto con respecto al cual se accede a la iteración
     * @return El servicio de iteracions para ese projecto en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     */
    @Path("{projectsId: \\d+}/iterations")
    public Class<IterationResource> getIterationResource(@PathParam("projectsId") Long projectsId) {
        if (logica.getProject(projectsId) == null) {
            throw new WebApplicationException(RECURSONO + projectsId + "/iterations no existe.", 404);
        }
        return IterationResource.class;
    }
    
    /**
     * Conexión con el servicio de hardware de un projecto. 
     *
     * Este método conecta la ruta de /project con las rutas de /hardwares que
     * dependen del proyecto, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las reseñas.
     *
     * @param projectsId El ID del proyecto con el se accede 
     * @return El servicio de iteracions para ese projecto en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     */
    @Path("{projectsId: \\d+}/hardwares")
    public Class<HardwareResource> getHardwareResource(@PathParam("projectsId") Long projectsId) {
        if (logica.getProject(projectsId) == null) {
            throw new WebApplicationException("El recurso /book/" + projectsId + "/reviews no existe.", 404);
        }
        return HardwareResource.class;
    }
    
    @Path("{projectsId: \\d+}/internalSystems")
    public Class<InternalSystemsResource> getInternalSystemsResource(@PathParam("projectsId") Long projectsId) {
        if (logica.getProject(projectsId) == null) {
            throw new WebApplicationException("El recurso /book/" + projectsId + "/reviews no existe.", 404);
        }
        return InternalSystemsResource.class;
    }
    
}
