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

import co.edu.uniandes.csw.sitiosweb.dtos.DeveloperDetailDTO;
import co.edu.uniandes.csw.sitiosweb.ejb.DeveloperLogic;
import co.edu.uniandes.csw.sitiosweb.ejb.ProjectDeveloperLogic;
import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
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
 * Clase que implementa el recurso "projects/{id}/developers".
 *
 * @developer ISIS2603
 * @version 1.0
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProjectDeveloperResource {

    private static final Logger LOGGER = Logger.getLogger(ProjectDeveloperResource.class.getName());

    @Inject
    private ProjectDeveloperLogic projectDeveloperLogic;

    @Inject
    private DeveloperLogic developerLogic;

    /**
     * Asocia un desarrollador existente con un proyecto existente
     *
     * @param developersId El ID del desarrollador que se va a asociar
     * @param projectsId El ID del proyecto al cual se le va a asociar el desarrollador
     * @return JSON {@link DeveloperDetailDTO} - El desarrollador asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el desarrollador.
     */
    @POST
    @Path("{developersId: \\d+}")
    public DeveloperDetailDTO addDeveloper(@PathParam("projectsId") Long projectsId, @PathParam("developersId") Long developersId) {
        LOGGER.log(Level.INFO, "ProjectDevelopersResource addDeveloper: input: projectsId {0} , developersId {1}", new Object[]{projectsId, developersId});
        if (developerLogic.getDeveloper(developersId) == null) {
            throw new WebApplicationException("El recurso /developers/" + developersId + " no existe.", 404);
        }
        DeveloperDetailDTO detailDTO = new DeveloperDetailDTO(projectDeveloperLogic.addDeveloper(projectsId, developersId));
        LOGGER.log(Level.INFO, "ProjectDevelopersResource addDeveloper: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los desarrolladores que existen en un proyecto.
     *
     * @param projectsId El ID del proyecto del cual se buscan los desarrolladores
     * @return JSONArray {@link DeveloperDetailDTO} - Los desarrolladores encontrados en el
     * proyecto. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<DeveloperDetailDTO> getDevelopers(@PathParam("projectsId") Long projectsId) {
        LOGGER.log(Level.INFO, "ProjectDevelopersResource getDevelopers: input: {0}", projectsId);
        List<DeveloperDetailDTO> lista = developersListEntity2DTO(projectDeveloperLogic.getDevelopers(projectsId));
        LOGGER.log(Level.INFO, "ProjectDevelopersResource getDevelopers: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve el desarrollador con el ID recibido en la URL, relativo a un
     * proyecto.
     *
     * @param developersId El ID del desarrollador que se busca
     * @param projectsId El ID del proyecto del cual se busca el desarrollador
     * @return {@link DeveloperDetailDTO} - El desarrollador encontrado en el proyecto.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el desarrollador.
     */
    @GET
    @Path("{developersId: \\d+}")
    public DeveloperDetailDTO getDeveloper(@PathParam("projectsId") Long projectsId, @PathParam("developersId") Long developersId) {
        LOGGER.log(Level.INFO, "ProjectDevelopersResource getDeveloper: input: projectsId {0} , developersId {1}", new Object[]{projectsId, developersId});
        if (developerLogic.getDeveloper(developersId) == null) {
            throw new WebApplicationException("El recurso /developers/" + developersId + " no existe.", 404);
        }
        DeveloperDetailDTO detailDTO = new DeveloperDetailDTO(projectDeveloperLogic.getDeveloper(projectsId, developersId));
        LOGGER.log(Level.INFO, "ProjectDevelopersResource getDeveloper: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de desarrolladores de un proyecto con la lista que se recibe en
     * el cuerpo.
     *
     * @param projectsId El ID del proyecto al cual se le va a asociar la lista de
     * desarrolladores
     * @param developers JSONArray {@link DeveloperDetailDTO} - La lista de desarrolladores
     * que se desea guardar.
     * @return JSONArray {@link DeveloperDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el desarrollador.
     */
    @PUT
    public List<DeveloperDetailDTO> replaceDevelopers(@PathParam("projectsId") Long projectsId, List<DeveloperDetailDTO> developers) {
        LOGGER.log(Level.INFO, "ProjectDevelopersResource replaceDevelopers: input: projectsId {0} , developers {1}", new Object[]{projectsId, developers});
        for (DeveloperDetailDTO developer : developers) {
            if (developerLogic.getDeveloper(developer.getId()) == null) {
                throw new WebApplicationException("El recurso /developers/" + developer.getId() + " no existe.", 404);
            }
        }
        List<DeveloperDetailDTO> lista = developersListEntity2DTO(projectDeveloperLogic.replaceDevelopers(projectsId, developersListDTO2Entity(developers)));
        LOGGER.log(Level.INFO, "ProjectDevelopersResource replaceDevelopers: output:{0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el desarrollador y el proyecto recibidos en la URL.
     *
     * @param projectsId El ID del proyecto al cual se le va a desasociar el desarrollador
     * @param developersId El ID del desarrollador que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el desarrollador.
     */
    @DELETE
    @Path("{developersId: \\d+}")
    public void removeDeveloper(@PathParam("projectsId") Long projectsId, @PathParam("developersId") Long developersId) {
        LOGGER.log(Level.INFO, "ProjectDevelopersResource removeDeveloper: input: projectsId {0} , developersId {1}", new Object[]{projectsId, developersId});
        if (developerLogic.getDeveloper(developersId) == null) {
            throw new WebApplicationException("El recurso /developers/" + developersId + " no existe.", 404);
        }
        projectDeveloperLogic.removeDeveloper(projectsId, developersId);
        LOGGER.info("ProjectDevelopersResource removeDeveloper: output: void");
    }

    /**
     * Convierte una lista de DeveloperEntity a una lista de DeveloperDetailDTO.
     *
     * @param entityList Lista de DeveloperEntity a convertir.
     * @return Lista de DeveloperDetailDTO convertida.
     */
    private List<DeveloperDetailDTO> developersListEntity2DTO(List<DeveloperEntity> entityList) {
        List<DeveloperDetailDTO> list = new ArrayList<>();
        for (DeveloperEntity entity : entityList) {
            list.add(new DeveloperDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de DeveloperDetailDTO a una lista de DeveloperEntity.
     *
     * @param dtos Lista de DeveloperDetailDTO a convertir.
     * @return Lista de DeveloperEntity convertida.
     */
    private List<DeveloperEntity> developersListDTO2Entity(List<DeveloperDetailDTO> dtos) {
        List<DeveloperEntity> list = new ArrayList<>();
        for (DeveloperDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
