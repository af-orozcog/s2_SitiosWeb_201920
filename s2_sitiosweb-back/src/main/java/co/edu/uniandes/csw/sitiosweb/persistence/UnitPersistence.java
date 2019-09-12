/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.UnitEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * @author Daniel del Castillo
 */
@Stateless
public class UnitPersistence 
{
    // Attributes
    
    /**
     * The request's logger.
     */
    private static final Logger LOGGER = Logger.getLogger(UnitPersistence.class.getName());
    
    /**
     * The request's entity manager.
     */
    @PersistenceContext(unitName = "sitioswebPU")
    protected EntityManager em;
    
    // Methods
    
    /**
     * Persists a unit in the database.
     * @param unit Unit to persist.
     * @return The persisted unit.
     */
    public UnitEntity create(UnitEntity unit)
    {
        LOGGER.log(Level.INFO, "Creating a new unit.");
        em.persist(unit);
        LOGGER.log(Level.INFO, "Exiting the creation of the unit.");
        return unit;
    }
    
    /**
     * Finds all the units in the database.
     * @return A list with all the units.
     */
    public List<UnitEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consulting all units.");
        TypedQuery query = em.createQuery("select u from UnitEntity u", UnitEntity.class);
        return query.getResultList();
    }
    
    /**
     * Finds a specific unit in the database.
     * @param unitId Id of the unit to find.
     * @return The specific unit. Null if it doesn't exist.
     */
    public UnitEntity find(Long unitId)
    {
        LOGGER.log(Level.INFO, "Consulting unit with id ={0}." , unitId);
        return em.find(UnitEntity.class, unitId);
    }
    
    /**
     * Updates a unit in the database.
     * @param unitEntity The unit to update.
     * @return The updated unitt.
     */
    public UnitEntity update(UnitEntity unitEntity)
    {
        LOGGER.log(Level.INFO, "Updating unit with id = {0}.", unitEntity.getId());
        LOGGER.log(Level.INFO, "Exiting the update of the unit with id = {0}.", unitEntity.getId());
        return em.merge(unitEntity);
    } 
    
    /**
     * Deletes the unit with the given id.
     * @param unitId The unit's id.
     */
    public void delete(Long unitId)
    {
        LOGGER.log(Level.INFO, "Deleting unit with id = {0}.", unitId);
        UnitEntity entity = em.find(UnitEntity.class, unitId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Exiting the deletion of the unit with id = {0}.", unitId);
    }
}
