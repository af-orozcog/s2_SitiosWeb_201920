/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@Entity
public class RequesterEntity extends UserEntity implements Serializable {

    @PodamExclude
    @OneToOne
    private UnitEntity unit;

    @PodamExclude
    @OneToMany(mappedBy = "requester")
    private List<RequestEntity> requests;

    /**
     * @return the unit
     */
    public UnitEntity getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(UnitEntity unit) {
        this.unit = unit;
    }

    /**
     * @return the requests
     */
    public List<RequestEntity> getRequests() {
        return requests;
    }

    /**
     * @param requests the requests to set
     */
    public void setRequests(List<RequestEntity> requests) {
        this.requests = requests;
    }

}
