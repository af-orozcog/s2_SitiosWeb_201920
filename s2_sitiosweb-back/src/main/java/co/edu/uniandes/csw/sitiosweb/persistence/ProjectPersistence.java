/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * @author Daniel Galindo Ruiz
 */
@Stateless
public class ProjectPersistence {
    
    @PersistenceContext(unitName = "sitioswebPU")
    protected EntityManager em;

    
    public ProjectEntity create(ProjectEntity project){
        em.persist(project);
        return project;
    }
    
    public List<ProjectEntity> findAll(){
        TypedQuery query = em.createQuery("select u from ProjectEntity u", ProjectEntity.class);
        return query.getResultList();
    }
    
    public ProjectEntity find(Long projectID){
        return em.find(ProjectEntity.class, projectID);
    }
    
    public ProjectEntity update(ProjectEntity project){
        return em.merge(project);
    }
    public void delete(Long projectID){
        ProjectEntity project = em.find(ProjectEntity.class, projectID);
        em.remove(project);
    }
}