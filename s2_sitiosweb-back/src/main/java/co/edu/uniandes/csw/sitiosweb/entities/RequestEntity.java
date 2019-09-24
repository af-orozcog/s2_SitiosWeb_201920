/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.entities;

import co.edu.uniandes.csw.sitiosweb.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamIntValue;
import uk.co.jemos.podam.common.PodamStrategyValue;
import uk.co.jemos.podam.common.PodamStringValue;

/**
 *
 * @author Daniel del Castillo
 */
@Entity
public class RequestEntity extends BaseEntity implements Serializable
{
    // Attributes
    
    @PodamExclude
    @ManyToOne
    private RequesterEntity requester;
    
    /**
     * Name of the request.
     */
    @PodamStringValue(length = 1)
    private String name;
    
    /**
     * Purpose of the request.
     */
    @PodamStringValue(length = 1)
    private String purpose;
    
    /**
     * Description of the request.
     */
    @PodamStringValue(length = 1)
    private String description;
    
    /**
     * Unit of the request.
     */
    @PodamStringValue(length = 1)
    private String unit;
    
    /**
     * Budget of the request.
     */
    @PodamIntValue(minValue = 0)
    private Integer budget;
    
    /**
     * Beginning date of the request.
     */
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date beginDate;
    
    /**
     * Due date of the request.
     */
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date dueDate;
    
    /**
     * End date of the request.
     */
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date endDate;
    
    // TODO Status, Category, Type & Requester.

    // Methods
    
    /**
     * @return the name
     */
    public String getName() 
    { return name; }

    /**
     * @param name the name to set
     */
    public void setName(String name) 
    { this.name = name; }

    /**
     * @return the purpose
     */
    public String getPurpose() 
    { return purpose; }

    /**
     * @param purpose the purpose to set
     */
    public void setPurpose(String purpose) 
    { this.purpose = purpose; }

    /**
     * @return the description
     */
    public String getDescription() 
    { return description; }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) 
    { this.description = description; }

    /**
     * @return the unit
     */
    public String getUnit() 
    { return unit; }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) 
    { this.unit = unit; }

    /**
     * @return the budget
     */
    public Integer getBudget() 
    { return budget; }

    /**
     * @param budget the budget to set
     */
    public void setBudget(Integer budget) 
    { this.budget = budget; }

    /**
     * @return the beginDate
     */
    public Date getBeginDate() 
    { return beginDate; }

    /**
     * @param beginDate the beginDate to set
     */
    public void setBeginDate(Date beginDate) 
    { this.beginDate = beginDate; }

    /**
     * @return the dueDate
     */
    public Date getDueDate() 
    { return dueDate; }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Date dueDate) 
    { this.dueDate = dueDate; }

    /**
     * @return the endDate
     */
    public Date getEndDate() 
    { return endDate; }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) 
    { this.endDate = endDate; }
}
