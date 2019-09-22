/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.dtos;

import co.edu.uniandes.csw.sitiosweb.adapters.DateAdapter;
import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Daniel del Castillo A.
 */
public class RequestDTO implements Serializable
{
    // Attributes
    
    /**
     * Name of the request.
     */
    private String name;
    
    /**
     * Purpose of the request.
     */
    private String purpose;
    
    /**
     * Description of the request.
     */
    private String description;
    
    /**
     * Unit of the request.
     */
    private String unit;
    
    /**
     * Budget of the request.
     */
    private Integer budget;
    
    /**
     * Beginning date of the request.
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date beginDate;
    
    /**
     * Due date of the request.
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date dueDate;
    
    /**
     * End date of the request.
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date endDate;
    
    // Constructor
    /**
     * Empty constructor.
     */
    public RequestDTO(){}
    
    /**
     * Creates a RequestDTO object given a RequestEntity object.
     * @param request The RequestEntity object.
     */
    public RequestDTO(RequestEntity request)
    {
        this.description = request.getDescription();
        this.beginDate = request.getBeginDate();
        this.dueDate = request.getDueDate();
        this.endDate = request.getEndDate();
        this.purpose = request.getPurpose();
        this.budget = request.getBudget();
        this.name = request.getName();
        this.unit = request.getUnit();
    }
    
    // Methods
    
    /**
     * Converts the RequestDTO to a RequestEntity object.
     * @return 
     */
    public RequestEntity toEntity()
    {
        RequestEntity entity = new RequestEntity();
        entity.setDescription(this.getDescription());
        entity.setBeginDate(this.getBeginDate());
        entity.setDueDate(this.getDueDate());
        entity.setEndDate(this.getEndDate());
        entity.setPurpose(this.getPurpose());
        entity.setBudget(this.getBudget());
        entity.setName(this.getName());
        entity.setUnit(this.getUnit());
        return entity;
    }

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
    {
        this.budget = budget;
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
     * @return the dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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
}
