/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.dtos;

import co.edu.uniandes.csw.sitiosweb.entities.ProviderEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Andres Martinez Silva
 */
public class ProviderDTO implements Serializable{
        private Long id;
    private String name;

    /**
     * Constructor por defecto
     */
    public ProviderDTO() {
    }

    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param providerEntity: Es la entidad que se va a convertir a DTO
     */
    public ProviderDTO(ProviderEntity providerEntity) {
        if (providerEntity != null) {
            this.id = providerEntity.getId();
            this.name = providerEntity.getName();
        }
    }

    /**
     * Devuelve el ID de la provider.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el ID de la provider.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre de la provider.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Modifica el nombre de la provider.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public ProviderEntity toEntity() {
        ProviderEntity providerEntity = new ProviderEntity();
        providerEntity.setId(this.id);
        providerEntity.setName(this.name);
        return providerEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
