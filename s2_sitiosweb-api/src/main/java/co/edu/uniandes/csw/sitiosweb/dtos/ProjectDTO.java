/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.dtos;

import java.io.Serializable;

/**
 *
 * @author Daniel Galindo Ruiz
 */
public class ProjectDTO implements Serializable{
    
    private Boolean internalProject;
    private String company;

    
    public ProjectDTO (){
        
    }
    /**
     * @return the internalProject
     */
    public Boolean getInternalProject() {
        return internalProject;
    }

    /**
     * @param internalProject the internalProject to set
     */
    public void setInternalProject(Boolean internalProject) {
        this.internalProject = internalProject;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }
    
}
