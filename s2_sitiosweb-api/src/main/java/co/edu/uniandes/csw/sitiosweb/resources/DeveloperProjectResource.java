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
import co.edu.uniandes.csw.sitiosweb.ejb.DeveloperLogic;
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
     * Atribute to handle developer`s logic
     */
    @Inject
    private DeveloperLogic developerLogic;
    
    /**
     *
     * @param developersId
     * @return
     */
    @Path("{developersId: \\d+}/projects")
    public Class<DeveloperProjectResource> getDeveloperProjectResource(@PathParam("developersId") Long developersId) {
        if (developerLogic.getDeveloper(developersId) == null) {
            throw new WebApplicationException("El recurso /developer/" + developersId , 404);
        }
        return DeveloperProjectResource.class;
    }

    /**
     * Asocia un proyecto existente con un desarrollador existente
     *
     * @param projectsId El ID del proyecto que se va a asociar
     * @param developersId El ID del desarrollador al cual se le va a asociar el proyecto
     * @return JSON {@link ProjectDetailDTO} - El proyecto asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el proyecto.
     */
    @POST
    @Path("{projectsId: \\d+}")
    public ProjectDetailDTO addProject(@PathParam("developersId") Long developersId, @PathParam("projectsId") Long projectsId) {
        LOGGER.log(Level.INFO, "DeveloperProjectsResource addProject: input: developersId {0} , projectsId {1}", new Object[]{developersId, projectsId});
        if (projectLogic.getProject(projectsId) == null) {
            throw new WebApplicationException("El recurso /projects/" + projectsId + " no existe.", 404);
        }
        ProjectDetailDTO detailDTO = new ProjectDetailDTO(developerProjectLogic.addProject(developersId, projectsId));
        LOGGER.log(Level.INFO, "DeveloperProjectsResource addProject: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los proyectoes que existen en un desarrollador.
     *
     * @param developersId El ID del desarrollador del cual se buscan los proyectoes
     * @return JSONArray {@link ProjectDetailDTO} - Los proyectoes encontrados en el
     * desarrollador. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ProjectDetailDTO> getProjects(@PathParam("developersId") Long developersId) {
        LOGGER.log(Level.INFO, "DeveloperProjectsResource getProjects: input: {0}", developersId);
        List<ProjectDetailDTO> lista = projectsListEntity2DTO(developerProjectLogic.getProjects(developersId));
        LOGGER.log(Level.INFO, "DeveloperProjectsResource getProjects: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve el proyecto con el ID recibido en la URL, relativo a un
     * desarrollador.
     *
     * @param projectsId El ID del proyecto que se busca
     * @param developersId El ID del desarrollador del cual se busca el proyecto
     * @return {@link ProjectDetailDTO} - El proyecto encontrado en el desarrollador.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el proyecto.
     */
    @GET
    @Path("{projectsId: \\d+}")
    public ProjectDetailDTO getProject(@PathParam("developersId") Long developersId, @PathParam("projectsId") Long projectsId) {
        LOGGER.log(Level.INFO, "DeveloperProjectsResource getProject: input: developersId {0} , projectsId {1}", new Object[]{developersId, projectsId});
        if (projectLogic.getProject(projectsId) == null) {
            throw new WebApplicationException("El recurso /projects/" + projectsId + " no existe.", 404);
        }
        ProjectDetailDTO detailDTO = new ProjectDetailDTO(developerProjectLogic.getProject(developersId, projectsId));
        LOGGER.log(Level.INFO, "DeveloperProjectsResource getProject: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de proyectoes de un desarrollador con la lista que se recibe en
     * el cuerpo.
     *
     * @param developersId El ID del desarrollador al cual se le va a asociar la lista de
     * proyectoes
     * @param projects JSONArray {@link ProjectDetailDTO} - La lista de proyectoes
     * que se desea guardar.
     * @return JSONArray {@link ProjectDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el proyecto.
     */
    @PUT
    public List<ProjectDetailDTO> replaceProjects(@PathParam("developersId") Long developersId, List<ProjectDetailDTO> projects) {
        LOGGER.log(Level.INFO, "DeveloperProjectsResource replaceProjects: input: developersId {0} , projects {1}", new Object[]{developersId, projects});
        for (ProjectDetailDTO project : projects) {
            if (projectLogic.getProject(project.getId()) == null) {
                throw new WebApplicationException("El recurso /projects/" + project.getId() + " no existe.", 404);
            }
        }
        List<ProjectDetailDTO> lista = projectsListEntity2DTO(developerProjectLogic.replaceProjects(developersId, projectsListDTO2Entity(projects)));
        LOGGER.log(Level.INFO, "DeveloperProjectsResource replaceProjects: output:{0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el proyecto y el desarrollador recibidos en la URL.
     *
     * @param developersId El ID del desarrollador al cual se le va a desasociar el proyecto
     * @param projectsId El ID del proyecto que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el proyecto.
     */
    @DELETE
    @Path("{projectsId: \\d+}")
    public void removeProject(@PathParam("developersId") Long developersId, @PathParam("projectsId") Long projectsId) {
        LOGGER.log(Level.INFO, "DeveloperProjectsResource removeProject: input: developersId {0} , projectsId {1}", new Object[]{developersId, projectsId});
        if (projectLogic.getProject(projectsId) == null) {
            throw new WebApplicationException("El recurso /projects/" + projectsId + " no existe.", 404);
        }
        developerProjectLogic.removeProject(developersId, projectsId);
        LOGGER.info("DeveloperProjectsResource removeProject: output: void");
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
