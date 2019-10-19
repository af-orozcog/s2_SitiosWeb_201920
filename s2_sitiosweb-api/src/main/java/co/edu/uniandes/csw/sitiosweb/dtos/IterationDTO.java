package co.edu.uniandes.csw.sitiosweb.dtos;

import co.edu.uniandes.csw.sitiosweb.adapters.DateAdapter;
import co.edu.uniandes.csw.sitiosweb.entities.IterationEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/**
 * IterationDTO Objeto de transferencia de datos de iteraciones. Los DTO contienen las
 * representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "objetive": string,
 *      "validationDate": Date,
 *      "changes": string,
 *      "beginDate":Date,
 *      "endDate":Date,
 *      "projecto": {@link ProjectDTO}
 *   }
 * </pre> Por ejemplo una reseña se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 123,
 *      "objetive": "actualizacion de funcionalidades",
 *      "validationDate": "2019-08-29T00:00:00Z[UTC]",
 *      "changes": "cambios en las funcionalidades",
 *      "beginDate": "2019-08-22T00:00:00Z[UTC]",
 *      "endDate":"2019-08-28T00:00:00Z[UTC]"
 *      "projecto":
 *      {
 *          "id": 123
 *      }
 *   }
 *
 * </pre>
 *
 */
/**
 *
 * @author Andres orozco 201730058
 */
public class IterationDTO implements Serializable {
    
    /**
     * Atributo que representa el objetivo de la iteración
     */
    private String objetive;
    
    /**
     * Atributo que representa la fecha de validación
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date validationDate;
    
    /**
     * atributo que representa los cambios que se hicieron durante la iteración
     */
    
    private String changes;
    
    /**
     * atributo que representa la fecha de inicio de la iteración
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date beginDate;
    
    /**
     * atributo que representa la fecha final de la iteración
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date endDate;
    
    /**
     * Identificación del objeto
     */
    private Long id;
       
    /**
    * Atributo que representa la asociación con el projecto
    */
    private ProjectDTO projecto;
    
    
    /**
     * Método constructor vacio
     */
    public IterationDTO(){
    
    }
    
    /**
     * constructor del método iterationDTO, este método se crea apartir del
     * IterationEntity pasado por parametro
     * @param iterationEntity 
     */
    public IterationDTO(IterationEntity iterationEntity) {
        if (iterationEntity != null) {
            this.id = iterationEntity.getId();
            this.objetive = iterationEntity.getObjetive();
            this.validationDate = iterationEntity.getValidationDate();
            this.changes = iterationEntity.getChanges();
            this.beginDate = iterationEntity.getBeginDate();
            this.endDate = iterationEntity.getEndDate();
            ProjectDTO toAdd = new ProjectDTO(iterationEntity.getProject());
            this.projecto = toAdd;
        }
    }
    
    /**
     * Método necesario para convertir el IterationDTO a una entidad
     * @return un IterationEntity del IterationDTO
     */
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
