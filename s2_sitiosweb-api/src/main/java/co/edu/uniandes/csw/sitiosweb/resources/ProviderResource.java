package co.edu.uniandes.csw.sitiosweb.resources;



import co.edu.uniandes.csw.sitiosweb.dtos.ProviderDTO;
import co.edu.uniandes.csw.sitiosweb.dtos.ProviderDetailDTO;
import co.edu.uniandes.csw.sitiosweb.ejb.ProviderLogic;
import co.edu.uniandes.csw.sitiosweb.entities.ProviderEntity;
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
 * @author Andres MArtinez Silva
 */
@Path("providers")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ProviderResource 
{
    // Constants
    
    private static final String ERR_MSG_1 = "El recurso /providers/";
    
    private static final String ERR_MSG_2 = " no existe.";
    
    private static final Logger LOGGER = Logger.getLogger(ProviderResource.class.getName());

    @Inject
    private ProviderLogic providerLogic;


    @POST
    public ProviderDTO createProvider(@PathParam("booksId") Long booksId, ProviderDTO provider) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProviderResource createProvider: input: {0}", provider);
        ProviderDTO nuevoProviderDTO = new ProviderDTO(providerLogic.createProvider(provider.toEntity()));
        LOGGER.log(Level.INFO, "ProviderResource createProvider: output: {0}", nuevoProviderDTO);
        return nuevoProviderDTO;
    }

    @GET
    public List<ProviderDetailDTO> getProviders() {
        LOGGER.log(Level.INFO, "ProviderResource getProviders: input: {0}");
        List<ProviderDetailDTO> listaDTOs = listEntity2DTO(providerLogic.getProviders());
        LOGGER.log(Level.INFO, "EditorialBooksResource getBooks: output: {0}", listaDTOs);
        return listaDTOs;
    }


    @GET
    @Path("{providersId: \\d+}")
    public ProviderDetailDTO getProvider(@PathParam("providersId") Long providersId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProviderResource getProvider: input: {0}", providersId);
        ProviderEntity entity = providerLogic.getProvider(providersId);
        if (entity == null) {
            throw new WebApplicationException(ERR_MSG_1 + providersId + ERR_MSG_2, 404);
        }
        ProviderDetailDTO providerDTO = new ProviderDetailDTO(entity);
        LOGGER.log(Level.INFO, "ProviderResource getProvider: output: {0}", providerDTO);
        return providerDTO;
    }

    
    @PUT
    @Path("{providersId: \\d+}")
    public ProviderDTO updateProvider(@PathParam("providersId") Long providersId, ProviderDTO provider) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProviderResource updateProvider: input: providersId: {0} , provider:{1}", new Object[]{ providersId, provider});
        if (providersId.equals(provider.getId())) {
            throw new BusinessLogicException("Los ids del Provider no coinciden.");
        }
        ProviderEntity entity = providerLogic.getProvider(providersId);
        if (entity == null) {
            throw new WebApplicationException(ERR_MSG_1 + providersId + ERR_MSG_2, 404);

        }
        ProviderDTO providerDTO = new ProviderDTO(providerLogic.updateProvider(providersId, provider.toEntity()));
        LOGGER.log(Level.INFO, "ProviderResource updateProvider: output:{0}", providerDTO);
        return providerDTO;

    }


    @DELETE
    @Path("{providersId: \\d+}")
    public void deleteProvider( @PathParam("providersId") Long providersId) throws BusinessLogicException {
        ProviderEntity entity = providerLogic.getProvider(providersId);
        if (entity == null) {
            throw new WebApplicationException(ERR_MSG_1 + providersId + ERR_MSG_2, 404);
        }
        providerLogic.deleteProvider( providersId);
    }

    private List<ProviderDetailDTO> listEntity2DTO(List<ProviderEntity> entityList) {
        List<ProviderDetailDTO> list = new ArrayList<>();
        for (ProviderEntity entity : entityList) {
            list.add(new ProviderDetailDTO(entity));
        }
        return list;
    }
}