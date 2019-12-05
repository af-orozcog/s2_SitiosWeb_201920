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
import javax.persistence.Query;
import javax.persistence.TypedQuery;


/**
 * @author Daniel Galindo Ruiz
 */
@Stateless
public class ProjectPersistence {
    
    /**
     * EntityManager for operations on the DB of the project.
     */
    @PersistenceContext(unitName = "sitioswebPU")
    protected EntityManager em;

    /**
     * Constructor of a ProjectEntity
     * @param project ProjectEntity recieved to be built and stored in the DB.
     * @return the ProjectEntity created.
     */
    public ProjectEntity create(ProjectEntity project){
        em.persist(project);
        return project;
    }
    
    /**
     * Finds all projects in the DB
     * @return all ProjectEntity objects found in the DB
     */
    public List<ProjectEntity> findAll(){
        Query query = em.createQuery("select u from ProjectEntity u", ProjectEntity.class);
        return query.getResultList();
    }
    
    /**
     * Finds a ProjectEntity object by its ID
     * @param projectID the id of the projectEntiy that is being looked for.
     * @return A ProjectEntity which id matches the id recieved by parameter.
     */
    public ProjectEntity find(Long projectID){
        return em.find(ProjectEntity.class, projectID);
    }
    
    
    public ProjectEntity findByName(String name) {
        TypedQuery query = em.createQuery("Select e From ProjectEntity e where e.name = :name", ProjectEntity.class);
        query = query.setParameter("name", name);
        List<ProjectEntity> sameName = query.getResultList();
        ProjectEntity result = null;
        if (!sameName.isEmpty()) {
            result = sameName.get(0);
        }
        return result;
    } 
    /**
     * Updates the ProjectEntity specified by parameter.
     * @param project the ProjectEntity to be updated.
     * @return The ProjectEntity updated with the new information added.
     */
    public ProjectEntity update(ProjectEntity project){
        return em.merge(project);
    }
    
    /**
     * Deletes a ProjectEntity by its ID.
     * @param projectID the project ID which will find and delete the ProjectEntity.
     */
    public void delete(Long projectID){
        ProjectEntity project = em.find(ProjectEntity.class, projectID);
        em.remove(project);
    }
}