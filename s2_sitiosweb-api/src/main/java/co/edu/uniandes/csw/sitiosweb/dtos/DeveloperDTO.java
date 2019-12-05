package co.edu.uniandes.csw.sitiosweb.dtos;

import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
public class DeveloperDTO extends UserDTO implements Serializable {

    private boolean leader;

    /**
     * Constructor por defecto
     */
    public DeveloperDTO() {

    }

    /**
     * Constructor a partir de la entidad
     *
     * @param developerEntity La entidad del desarrollador
     */
    public DeveloperDTO(DeveloperEntity developerEntity) {
        super(developerEntity);
        this.leader = developerEntity.getLeader();
    }

    /**
     * Método para transformar el DTO a una entidad.
     *
     * @return La entidad del desarrollador asociado.
     */
    @Override
    public DeveloperEntity toEntity() {
        DeveloperEntity developerEntity = new DeveloperEntity();
        developerEntity.setId(this.getId());
        developerEntity.setName(this.getName());
        developerEntity.setLogin(this.getLogin());
        developerEntity.setEmail(this.getEmail());
        developerEntity.setPhone(this.getPhone());
        developerEntity.setLeader(this.getLeader());
        developerEntity.setImage(this.getImage());
        return developerEntity;

    }

    /**
     * @return if leader
     */
    public boolean getLeader() {
        return this.leader;
    }

    /**
     * @param leader if is leader
     */
    public void setLeader(boolean leader) {
        this.leader = leader;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
