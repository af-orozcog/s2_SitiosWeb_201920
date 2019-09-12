/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.UnitEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.UnitPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Daniel del Castillo A.
 */
@Stateless
public class UnitLogic 
{
    // Constants
    
    /**
     * The logicRequest's logger.
     */
    private static final Logger LOGGER = Logger.getLogger(UnitLogic.class.getName());
    
    // Attributes
    
    /**
     * This dependance allows allocation for database unit conection.
     */
    @Inject
    private UnitPersistence persistence;
    
    // Methods
    
    /**
     * Method that creates an unit entity through the persistence.
     * @param unit The unit to create.
     * @return  The created unit.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    public UnitEntity createUnit(UnitEntity unit) throws BusinessLogicException
    {
        if(unit.getName() == null || unit.getName().isEmpty())
            throw new BusinessLogicException("El nombre de la unidad está vacío.");
        LOGGER.log(Level.INFO, "Creating a new logic unit.");
        unit = persistence.create(unit);
        LOGGER.log(Level.INFO, "Exiting the creaton of the logic unit.");
        return unit;
    }
    
    /**
     * Finds all the units in the database.
     * @return A list with all the units.
     */
    public List<UnitEntity> getUnits()
    {
        LOGGER.log(Level.INFO, "Consulting al units.");
        List<UnitEntity> list = persistence.findAll();
        LOGGER.log(Level.INFO, "Exiting the consult of all units.");
        return list;
    }
    
    /**
     * Finds a specific unit in the database.
     * @param unitId Id of the unit to find.
     * @return The specific unit. Null if it doesn't exist.
     */
    public UnitEntity getUnit(Long unitId)
    {
        LOGGER.log(Level.INFO, "Consulting unit with id = {0}.", unitId);
        UnitEntity unitEntity = persistence.find(unitId);
        if(unitEntity == null)
            LOGGER.log(Level.SEVERE, "The unit with id = {0} does not exist.", unitId);
        LOGGER.log(Level.INFO, "Exiting the consult of the unit with id = {0}.", unitId);
        return unitEntity;
    }
    
    /**
     * Updates a unit in the database.
     * @param unitId  The unit's id.
     * @param UnitEntity The unit to update.
     * @return The updated unit.
     */
    public UnitEntity updateUnit(Long unitId, UnitEntity UnitEntity)
    {
        LOGGER.log(Level.INFO, "Updating unit with id = {0}.", unitId);
        UnitEntity newUnitEntity = persistence.update(UnitEntity);
        LOGGER.log(Level.INFO, "Exiting the update of the unit with id = {0}.", unitId);
        return newUnitEntity;
    }
    
    /**
     * Deletes the unit with the given id.
     * @param unitId The unit's id.
     * BUSINESS LOGIC RULE:
     *  - The name can't be null or empty.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    public void deleteUnit(Long unitId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Deleting unit with id = {0}.", unitId);
        // TODO relationships with Requester.
        persistence.delete(unitId);
        LOGGER.log(Level.INFO, "Exiting the deletion of the unit with id = {0}.", unitId);
    }
}
