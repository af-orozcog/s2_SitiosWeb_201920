/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.entities;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@Entity
public class CatalogueEntity extends BaseEntity implements Serializable{
    private int requestNum;
    
    private int projectNum;

    /**
     * @return the requestNum
     */
    public int getRequestNum() {
        return requestNum;
    }

    /**
     * @param requestNum the requestNum to set
     */
    public void setRequestNum(int requestNum) {
        this.requestNum = requestNum;
    }

    /**
     * @return the projectNum
     */
    public int getProjectNum() {
        return projectNum;
    }

    /**
     * @param projectNum the projectNum to set
     */
    public void setProjectNum(int projectNum) {
        this.projectNum = projectNum;
    }
    
}
