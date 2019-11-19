package co.edu.uniandes.csw.sitiosweb.dtos;

import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
public class RequesterDTO extends UserDTO implements Serializable {

    private UnitDTO unit;

    /**
     * Constructor por defecto
     */
    public RequesterDTO() {

    }

    /**
     * Constructor a partir de la entidad
     *
     * @param requesterEntity La entidad del solicitante
     */
    public RequesterDTO(RequesterEntity requesterEntity) {
        super(requesterEntity);
        unit = new UnitDTO(requesterEntity.getUnit());

    }

    /**
     * Método para transformar el DTO a una entidad.
     *
     * @return La entidad del solicitante asociado.
     */
    @Override
    public RequesterEntity toEntity() {
        RequesterEntity requesterEntity = new RequesterEntity();
        requesterEntity.setId(this.getId());
        requesterEntity.setName(this.getName());
        requesterEntity.setLogin(this.getLogin());
        requesterEntity.setEmail(this.getEmail());
        requesterEntity.setPhone(this.getPhone());
        requesterEntity.setImage(this.getImage());
        requesterEntity.setUnit(this.getUnit().toEntity());
        return requesterEntity;

    }

    /**
     * @return the unit
     */
    public UnitDTO getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(UnitDTO unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
