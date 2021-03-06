 /*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.sitiosweb.resources;

import co.edu.uniandes.csw.sitiosweb.dtos.ProjectDetailDTO;
import co.edu.uniandes.csw.sitiosweb.ejb.ProjectLogic;
import co.edu.uniandes.csw.sitiosweb.ejb.DeveloperProjectLogic;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.mappers.WebApplicationExceptionMapper;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "developers/{id}/projects".
 *
 * @project ISIS2603
 * @version 1.0
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeveloperProjectResource {

    /**
     * Logger for this class
     */
    private static final Logger LOGGER = Logger.getLogger(DeveloperProjectResource.class.getName());
    
    /**
     * Constante mensaje error project
     */
    private static final String RECURSOPROJECT = "El recurso /projects/";
    
    /**
     * Constante no existe mensaje
     */
    private static final String NOEXISTE = " no existe.";

    /**
     * Atribute to handle developerProject`s logic
     */
    @Inject
    private DeveloperProjectLogic developerProjectLogic;
    
    /**
     * Atribute to handle project`s logic
     */
    @Inject
    private ProjectLogic projectLogic;
    
     /**
     * Asocia un proyecto existente con un desarrollador existente
     *
     * @param developersId El ID del desarrollador al cual se le va a asociar el proyecto
     * @param projectsId El ID del proyecto que se asocia
     * @return JSON {@link ProjectDetailDTO} - El proyecto asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el proyecto.
     */
    @POST
    @Path("{projectsId: \\d+}")
    public ProjectDetailDTO addProject(@PathParam("developersId") Long developersId, @PathParam("projectsId") Long projectsId) {
        LOGGER.log(Level.INFO, "DeveloperProjectResource addProject: input: developersId {0} , projectsId {1}", new Object[]{developersId, projectsId});
        if (projectLogic.getProject(projectsId) == null) {
            throw new WebApplicationException( RECURSOPROJECT + projectsId + NOEXISTE , 404);
        }
        ProjectDetailDTO detailDTO = new ProjectDetailDTO(developerProjectLogic.addProject(developersId, projectsId));
        LOGGER.log(Level.INFO, "DeveloperProjectResource addProject: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los proyectos que existen en un desarrollador.
     *
     * @param developersId El ID del desarrollador del cual se buscan los proyectos
     * @return JSONArray {@link ProjectDetailDTO} - Los proyectos encontrados en el
     * desarrollador. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ProjectDetailDTO> getProjects(@PathParam("developersId") Long developersId) {
        LOGGER.log(Level.INFO, "DeveloperProjectResource getProjects: input: {0}", developersId);
        List<ProjectDetailDTO> lista = projectsListEntity2DTO(developerProjectLogic.getProjects(developersId));
        LOGGER.log(Level.INFO, "DeveloperProjectResource getProjects: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve el proyecto con el ID recibido en la URL, relativo a un
     * desarrollador.
     *
     * @param developersId El ID del desarrollador del cual se busca el proyecto
     * @param projectsId El ID del proyecto que se busca
     * @return {@link ProjectDetailDTO} - El proyecto encontrado en el desarrollador.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     * si el proyecto no está asociado al desarrollador
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el proyecto.
     */
    @GET
    @Path("{projectsId: \\d+}")
    public ProjectDetailDTO getProject(@PathParam("developersId") Long developersId, @PathParam("projectsId") Long projectsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "DeveloperProjectResource getProject: input: developersId {0} , projectsId {1}", new Object[]{developersId, projectsId});
        if (projectLogic.getProject(projectsId) == null) {
            throw new WebApplicationException( RECURSOPROJECT + projectsId + NOEXISTE , 404);
        }
        ProjectDetailDTO detailDTO = new ProjectDetailDTO(developerProjectLogic.getProject(developersId, projectsId));
        LOGGER.log(Level.INFO, "DeveloperProjectResource getProject: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de proyectos de un desarrollador con la lista que se recibe en el
     * cuerpo
     *
     * @param developersId El ID del desarrollador al cual se le va a asociar el proyecto
     * @param projects JSONArray {@link ProjectDetailDTO} - La lista de proyectos que se
     * desea guardar.
     * @return JSONArray {@link ProjectDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el proyecto.
     */
    @PUT
    public List<ProjectDetailDTO> replaceProjects(@PathParam("developersId") Long developersId, List<ProjectDetailDTO> projects) {
        LOGGER.log(Level.INFO, "DeveloperProjectResource replaceProjects: input: developersId {0} , projects {1}", new Object[]{developersId, projects});
        for (ProjectDetailDTO project : projects) {
            if (projectLogic.getProject(project.getId()) == null) {
                throw new WebApplicationException( RECURSOPROJECT + project.getId() + NOEXISTE , 404);
            }
        }
        List<ProjectDetailDTO> lista = projectsListEntity2DTO(developerProjectLogic.replaceProjects(developersId, projectsListDTO2Entity(projects)));
        LOGGER.log(Level.INFO, "DeveloperProjectResource replaceProjects: output: {0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el proyecto y e desarrollador recibidos en la URL.
     *
     * @param developersId El ID del desarrollador al cual se le va a desasociar el proyecto
     * @param projectsId El ID del proyecto que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el proyecto.
     */
    @DELETE
    @Path("{projectsId: \\d+}")
    public void removeProject(@PathParam("developersId") Long developersId, @PathParam("projectsId") Long projectsId) {
        LOGGER.log(Level.INFO, "DeveloperProjectResource deleteProject: input: developersId {0} , projectsId {1}", new Object[]{developersId, projectsId});
        if (projectLogic.getProject(projectsId) == null) {
            throw new WebApplicationException( RECURSOPROJECT + projectsId + NOEXISTE , 404);
        }
        developerProjectLogic.removeProject(developersId, projectsId);
        LOGGER.info("DeveloperProjectResource deleteProject: output: void");
    }

    /**
     * Convierte una lista de ProjectEntity a una lista de ProjectDetailDTO.
     *
     * @param entityList Lista de ProjectEntity a convertir.
     * @return Lista de ProjectDetailDTO convertida.
     */
    private List<ProjectDetailDTO> projectsListEntity2DTO(List<ProjectEntity> entityList) {
        List<ProjectDetailDTO> list = new ArrayList<>();
        for (ProjectEntity entity : entityList) {
            list.add(new ProjectDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ProjectDetailDTO a una lista de ProjectEntity.
     *
     * @param dtos Lista de ProjectDetailDTO a convertir.
     * @return Lista de ProjectEntity convertida.
     */
    private List<ProjectEntity> projectsListDTO2Entity(List<ProjectDetailDTO> dtos) {
        List<ProjectEntity> list = new ArrayList<>();
        for (ProjectDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
