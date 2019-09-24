package co.edu.uniandes.csw.sitiosweb.dtos;

import co.edu.uniandes.csw.sitiosweb.adapters.DateAdapter;
import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity.DeveloperType;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 
 * @author Nicolás Abondano nf.abondano 201812467
 */
public class DeveloperDTO extends UserDTO implements Serializable {

    private DeveloperType type;
    
    /**
     * Constructor por defecto
     */
    public DeveloperDTO() {
        
    }

    /**
     * Constructor a partir de la entidad
     *
     * @param developerEntity La entidad del libro
     */
    public DeveloperDTO(DeveloperEntity developerEntity) {
        super(developerEntity);
        this.type = developerEntity.getType();
    }

    /**
     * Método para transformar el DTO a una entidad.
     *
     * @return La entidad del libro asociado.
     */
    public DeveloperEntity toEntity() {
        DeveloperEntity developerEntity = new DeveloperEntity();
        developerEntity.setId(this.getId());
        developerEntity.setLogin(this.getLogin());
        developerEntity.setEmail(this.getEmail());
        developerEntity.setPhone(this.getPhone());
        developerEntity.setType(this.getType());
        return developerEntity;
        
    }
    
        /**
     * @return the type
     */
    public DeveloperType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(DeveloperType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
