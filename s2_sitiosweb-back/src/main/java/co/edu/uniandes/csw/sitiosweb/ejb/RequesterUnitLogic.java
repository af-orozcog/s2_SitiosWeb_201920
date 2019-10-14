/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import co.edu.uniandes.csw.sitiosweb.entities.UnitEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Class tha implements the conection with the persistence for the
 * entity relationship Requester->Unit.
 * @author Daniel del Castillo A.
 */
@Stateless
public class RequesterUnitLogic 
{
    // Constants
    
    /**
     * The RequesterUnitLogic's logger. 
     */
    private static final Logger LOGGER = Logger.getLogger(RequesterUnitLogic.class.getName());
    
    // Attributes
    
    /**
     * The requester's persistence.
     */
    @Inject
    private RequesterLogic requesterPersistence;
    
    /**
     * The unit's persistence.
     */
    @Inject
    private UnitLogic unitPersistence;
    
    // Methods
    
    /**
     * Replaces the unit of a requester.
     * @param requesterId The id of the requester whose unit will be replaced.
     * @param unitId The id of the unit that will become the new unit.
     * @return The requester entity with the updated requester.
     */
    public RequesterEntity replaceUnit(Long requesterId, Long unitId)
    {
        LOGGER.log(Level.INFO, "Replacing the unit of the requester with id = {0}.", requesterId);
        RequesterEntity requesterEntity = requesterPersistence.getRequester(requesterId);
        UnitEntity unitEntity = unitPersistence.getUnit(unitId);
        requesterEntity.setUnit(unitEntity);
        LOGGER.log(Level.INFO, "Exiting the replacement of the unit of the requester with id = {0}.", requesterId);
        return requesterEntity;
    }
    
    /**
     * Removes the unit of a given requester and deletes this requester
     * from the unit's requester list.
     * @param requesterId The requester whose unit will be deleted.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     * if the requester or requester's unit doesn't exist.
     */
    public void removeRequester(Long requesterId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Deleting the unit of the requester with id = {0}.", requesterId);
        RequesterEntity requesterEntity = requesterPersistence.getRequester(requesterId);
        if(requesterEntity == null)
        {
            LOGGER.log(Level.INFO, "No unit was deleted since the requester doesn't exist.");
            throw new BusinessLogicException("No unit was deleted since the requester doesn't exit.");
        }
        UnitEntity unitEntity = unitPersistence.getUnit(requesterEntity.getUnit().getId());
        if(unitEntity != null)
        {
            // This will not delete the requester from the DB:
            requesterEntity.setUnit(null);
            unitEntity.getRequesters().remove(requesterEntity);
        }
        else
        {
            LOGGER.log(Level.INFO, "No unit was deleted since there wasn't one.");
            throw new BusinessLogicException("No unit was deleted since there wasn't one.");
        }
        LOGGER.log(Level.INFO, "Exiting the deletion of the unit of the requester with id = {0}.", requesterId);
    }
}
