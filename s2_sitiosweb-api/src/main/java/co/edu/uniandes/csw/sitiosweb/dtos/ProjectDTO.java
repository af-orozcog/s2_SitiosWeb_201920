/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.dtos;

import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import java.io.Serializable;

/**
 *
 * @author Daniel Galindo Ruiz
 */
public class ProjectDTO implements Serializable{
    
    private Boolean internalProject;
    private String company;
    private Long id;
    
    public ProjectDTO (){
        
    }
    
    public ProjectDTO(ProjectEntity entity){
        setId(entity.getId());
        setInternalProject(entity.getInternalProject());
        setCompany(entity.getCompany());
    }
    
    public ProjectEntity toEntity(){
        ProjectEntity entidad = new ProjectEntity();
        entidad.setId(this.getId());
        entidad.setInternalProject(this.internalProject);
        entidad.setCompany(this.company);
        return entidad;
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
