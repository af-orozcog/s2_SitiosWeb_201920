package co.edu.uniandes.csw.sitiosweb.dtos;

import co.edu.uniandes.csw.sitiosweb.adapters.DateAdapter;
import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 
 * @author Nicolás Abondano nf.abondano 201812467
 */
public class RequesterDetailDTO extends RequesterDTO implements Serializable {

    private List<RequestDTO> requests;
    
    public RequesterDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param developerEntity La entidad de la cual se construye el DTO
     */
    public RequesterDetailDTO(RequesterEntity developerEntity) {
        super(developerEntity);
        if (developerEntity.getRequests() != null) {
            requests = new ArrayList<>();
            for (RequestEntity entityRequest : developerEntity.getRequests()) {
                requests.add(new RequestDTO(entityRequest));
            }
        }
    }

    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el libro.
     */
    @Override
    public RequesterEntity toEntity() {
        RequesterEntity developerEntity = super.toEntity();
        if (getRequests() != null) {
            List<RequestEntity> requestsEntity = new ArrayList<>();
            for (RequestDTO dtoRequest : getRequests()) {
                requestsEntity.add(dtoRequest.toEntity());
            }
            developerEntity.setRequests(requestsEntity);
        }
        return developerEntity;
    }
    
    /**
     * @return the requests
     */
    public List<RequestDTO> getRequests() {
        return requests;
    }

    /**
     * @param requests the requests to set
     */
    public void setRequests(List<RequestDTO> requests) {
        this.requests = requests;
    }
}
