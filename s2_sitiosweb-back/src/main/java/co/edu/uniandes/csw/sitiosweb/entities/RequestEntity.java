package co.edu.uniandes.csw.sitiosweb.entities;

import co.edu.uniandes.csw.sitiosweb.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamIntValue;
import uk.co.jemos.podam.common.PodamStrategyValue;
import uk.co.jemos.podam.common.PodamStringValue;

/**
 *
 * @author Daniel del Castillo A.
 */
@Entity
public class RequestEntity extends BaseEntity implements Serializable
{
    // Enums
    
    /**
     * Enumeration of the request's RequestType.
     */
    public enum RequestType
    {
        ELIMINATION,
        CREATION,
        CHANGE,
        DEVELOPMENT,
        PRODUCTION
    }
    
    /**
     * Enumeration of the request's WebCategory.
     */
    public enum WebCategory
    {
        DESCRIPTIVE,
        APPLICATION,
        EVENT
    }
    
    /**
     * Enumeration of the request's Status.
     */
    public enum Status
    {
        DEVELOPMENT,
        PRODUCTION,
        ACCEPTED,
        PENDING,
        DENIED
    }
    
    // Attributes
    
    /**
     * The request's status.
     */
    @Enumerated(EnumType.STRING)
    private Status status;
    
    /**
     * The request's web category.
     */
    @Enumerated(EnumType.STRING)
    private WebCategory webCategory;
    
    /**
     * The request's type.
     */
    @Enumerated(EnumType.STRING)
    private RequestType requestType;
    
    /**
     * Relationship where a request has one requester.
     */
    @PodamExclude
    @ManyToOne
    private RequesterEntity requester;
    
    /**
     * Relationship where a request has one project.
     */
    @PodamExclude
    @ManyToOne
    private ProjectEntity project;
    
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

    // Methods
    
    /**
     * @return the name.
     */
    public String getName() 
    { return name; }

    /**
     * @param name the name to set.
     */
    public void setName(String name) 
    { this.name = name; }

    /**
     * @return the purpose.
     */
    public String getPurpose() 
    { return purpose; }

    /**
     * @param purpose the purpose to set.
     */
    public void setPurpose(String purpose) 
    { this.purpose = purpose; }

    /**
     * @return the description.
     */
    public String getDescription() 
    { return description; }

    /**
     * @param description the description to set.
     */
    public void setDescription(String description) 
    { this.description = description; }

    /**
     * @return the unit.
     */
    public String getUnit() 
    { return unit; }

    /**
     * @param unit the unit to set.
     */
    public void setUnit(String unit) 
    { this.unit = unit; }

    /**
     * @return the budget.
     */
    public Integer getBudget() 
    { return budget; }

    /**
     * @param budget the budget to set.
     */
    public void setBudget(Integer budget) 
    { this.budget = budget; }

    /**
     * @return the beginDate.
     */
    public Date getBeginDate() 
    { return beginDate; }

    /**
     * @param beginDate the beginDate to set.
     */
    public void setBeginDate(Date beginDate) 
    { this.beginDate = beginDate; }

    /**
     * @return the dueDate.
     */
    public Date getDueDate() 
    { return dueDate; }

    /**
     * @param dueDate the dueDate to set.
     */
    public void setDueDate(Date dueDate) 
    { this.dueDate = dueDate; }

    /**
     * @return the endDate.
     */
    public Date getEndDate() 
    { return endDate; }

    /**
     * @param endDate the endDate to set.
     */
    public void setEndDate(Date endDate) 
    { this.endDate = endDate; }
    
    
    /**
     * @return the requester.
     */
    public RequesterEntity getRequester() 
    { return requester; }

    /**
     * @param requester the requester to set.
     */
    public void setRequester(RequesterEntity requester) 
    { this.requester = requester; }

    /**
     * @return the project.
     */
    public ProjectEntity getProject() 
    { return project; }

    /**
     * @param project the project to set.
     */
    public void setProject(ProjectEntity project) 
    { this.project = project; }

    /**
     * @return the status.
     */
    public Status getStatus() 
    { return status; }

    /**
     * @param status the status to set.
     */
    public void setStatus(Status status) 
    { this.status = status; }

    /**
     * @return the webCategory.
     */
    public WebCategory getWebCategory() 
    { return webCategory; }

    /**
     * @param webCategory the webCategory to set.
     */
    public void setWebCategory(WebCategory webCategory) 
    { this.webCategory = webCategory; }

    /**
     * @return the requestType.
     */
    public RequestType getRequestType() 
    { return requestType; }

    /**
     * @param requestType the requestType to set.
     */
    public void setRequestType(RequestType requestType) 
    { this.requestType = requestType; }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Request");
        sb.append(getId());
        sb.append(" = requester : ");
        sb.append(requester);
        sb.append(", project : ");
        sb.append(project);
        return sb.toString();
    }
}
