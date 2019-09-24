/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.dtos;

import co.edu.uniandes.csw.sitiosweb.entities.IterationEntity;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Andres orozco 201730058
 */
public class IterationDTO implements Serializable {
    
    
    private String objetive;
    private Date validationDate;
    private String changes;
    private Date beginDate;
    private Date endDate;
    private Long id;
       
    public IterationDTO(){
    
    }
    
    public IterationDTO(IterationEntity iterationEntity) {
        if (iterationEntity != null) {
            this.id = iterationEntity.getId();
            this.objetive = iterationEntity.getObjetive();
            this.validationDate = iterationEntity.getValidationDate();
            this.changes = iterationEntity.getChanges();
            this.beginDate = iterationEntity.getBeginDate();
            this.endDate = iterationEntity.getEndDate();
        }
    }

    public IterationEntity toEntity() {
        IterationEntity iterationEntity = new IterationEntity();
        iterationEntity.setId(this.getId());
        iterationEntity.setObjetive(this.getObjetive());
        iterationEntity.setValidationDate(this.getValidationDate());
        iterationEntity.setChanges(this.getChanges());
        iterationEntity.setBeginDate(this.getBeginDate());
        iterationEntity.setEndDate(this.getEndDate());
        return iterationEntity;
    }
    
    /**
     * @return the objetive
     */
    public String getObjetive() {
        return objetive;
    }

    /**
     * @param objetive the objetive to set
     */
    public void setObjetive(String objetive) {
        this.objetive = objetive;
    }

    /**
     * @return the validationDate
     */
    public Date getValidationDate() {
        return validationDate;
    }

    /**
     * @param validationDate the validationDate to set
     */
    public void setValidationDate(Date validationDate) {
        this.validationDate = validationDate;
    }

    /**
     * @return the changes
     */
    public String getChanges() {
        return changes;
    }

    /**
     * @param changes the changes to set
     */
    public void setChanges(String changes) {
        this.changes = changes;
    }

    /**
     * @return the beginDate
     */
    public Date getBeginDate() {
        return beginDate;
    }

    /**
     * @param beginDate the beginDate to set
     */
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
       
}
