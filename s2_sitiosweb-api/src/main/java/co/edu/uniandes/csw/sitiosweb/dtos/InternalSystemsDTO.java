package co.edu.uniandes.csw.sitiosweb.dtos;

import co.edu.uniandes.csw.sitiosweb.entities.InternalSystemsEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author s.santosb
 */
public class InternalSystemsDTO implements Serializable{
    
    private Long id;
    private String type;
    /**
    * Atributo que representa la asociación con el projecto
    */
    private ProjectDTO projecto;

    
     /**
     * Constructor vacio
     */
    public InternalSystemsDTO() {
    }

    /**
     * Crea un objeto InternalSystemsDTO a partir de un objeto InternalSystemsEntity.
     *
     * @param internalSystemsEntity Entidad HardwareEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public InternalSystemsDTO(InternalSystemsEntity internalSystemsEntity) {
        if (internalSystemsEntity != null) {
            this.id = internalSystemsEntity.getId();
            this.type = internalSystemsEntity.getType(); 
            if(internalSystemsEntity.getProject()!=null){
                this.projecto = new ProjectDTO(internalSystemsEntity.getProject());
            }
            else{
                this.projecto = null;
            }
        }
    }

    /**
     * Convierte un objeto InternalSystemsDTO a un objeto InternalSystemsEntity.
     *
     * @return Nueva objeto internalSystemsEntity.
     *
     */
    public InternalSystemsEntity toEntity() {
        InternalSystemsEntity internalSystemsEntity = new InternalSystemsEntity();
        internalSystemsEntity.setId(this.getId());
        internalSystemsEntity.setType(this.getType());
        return internalSystemsEntity;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the projecto
     */
    public ProjectDTO getProject() {
        return projecto;
    }

    /**
     * @param projecto the projecto to set
     */
    public void setProject(ProjectDTO projecto) {
        this.projecto = projecto;
    }
    
    
    
    
    
}
