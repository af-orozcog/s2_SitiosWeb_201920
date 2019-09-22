/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.resources;

import co.edu.uniandes.csw.sitiosweb.dtos.DeveloperDTO;
import co.edu.uniandes.csw.sitiosweb.dtos.DeveloperDTO;
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
 * @author Nicolás Abondano nf.abondano 201812467
 */
@Path("developers")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class DeveloperResource {
    private final static Logger LOGGER = Logger.getLogger(DeveloperResource.class.getName());
    
    @Inject
    private DeveloperLogic developerLogic;
    
    @POST
    public DeveloperDTO createDeveloper(DeveloperDTO developer) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "DeveloperResource createDeveloper: input: {0}", developer);
        DeveloperEntity developerEntity = developer.toEntity();
        DeveloperEntity nuevoDeveloperEntity = developerLogic.createDeveloper(developerEntity);
        DeveloperDTO nuevoUsuerDTO = new DeveloperDTO(nuevoDeveloperEntity);
        LOGGER.log(Level.INFO, "DeveloperResource createDeveloper: output: {0}", nuevoUsuerDTO);
        return nuevoUsuerDTO;    
    }

    @GET
    public List<DeveloperDTO> getDevelopers() {
        LOGGER.info("DeveloperResource getDevelopers: input: void");
        List<DeveloperDTO> listaUsuarios = listEntity2DetailDTO(developerLogic.getDevelopers());
        LOGGER.log(Level.INFO, "DeveloperResource getDevelopers: output: {0}", listaUsuarios);
        return listaUsuarios;
    }

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

    @PUT
    @Path("{developersId: \\d+}")
    public DeveloperDTO updateDeveloper(@PathParam("developersId") Long developersId, DeveloperDTO developer) throws WebApplicationException, BusinessLogicException {
        LOGGER.log(Level.INFO, "DeveloperResource updateDeveloper: input: id:{0} , developer: {1}", new Object[]{developersId, developer});
        developer.setId(developersId);
        if (developerLogic.getDeveloper(developersId) == null) {
            throw new WebApplicationException("El recurso /developers/" + developersId + " no existe.", 404);
        }
        DeveloperDTO developerDTO = new DeveloperDTO(developerLogic.updateDeveloper(developersId, developer.toEntity()));
        LOGGER.log(Level.INFO, "DeveloperResource updateDeveloper: output: {0}", developerDTO);
        return developerDTO;
    }
    
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

    private List<DeveloperDTO> listEntity2DetailDTO(List<DeveloperEntity> entityList) {
        List<DeveloperDTO> list = new ArrayList<>();
        for (DeveloperEntity entity : entityList) {
            list.add(new DeveloperDTO(entity));
        }
        return list;
    }
}
