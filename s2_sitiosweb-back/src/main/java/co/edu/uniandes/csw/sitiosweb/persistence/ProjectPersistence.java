/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Daniel Galindo Ruiz
 */
@Stateless
public class ProjectPersistence {
    
    @PersistenceContext(unitName = "sitioswebPU")
    protected EntityManager em;

    
    public ProjectEntity create(ProjectEntity project){
        em.persist(project);
        return project;
        //throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }
}
