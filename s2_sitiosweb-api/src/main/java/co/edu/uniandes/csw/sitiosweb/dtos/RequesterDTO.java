package co.edu.uniandes.csw.sitiosweb.dtos;

import co.edu.uniandes.csw.sitiosweb.adapters.DateAdapter;
import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 
 * @author Nicolás Abondano nf.abondano 201812467
 */
public class RequesterDTO extends UserDTO implements Serializable {
    
    /**
     * Constructor por defecto
     */
    public RequesterDTO() {
        
    }

    /**
     * Constructor a partir de la entidad
     *
     * @param requesterEntity La entidad del libro
     */
    public RequesterDTO(RequesterEntity requesterEntity) {
        super(requesterEntity);
    }

    /**
     * Método para transformar el DTO a una entidad.
     *
     * @return La entidad del libro asociado.
     */
    public RequesterEntity toEntity() {
        RequesterEntity requesterEntity = new RequesterEntity();
        requesterEntity.setId(this.getId());
        requesterEntity.setLogin(this.getLogin());
        requesterEntity.setEmail(this.getEmail());
        requesterEntity.setPhone(this.getPhone());
        return requesterEntity;
        
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}