/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import co.edu.uniandes.csw.sitiosweb.entities.UnitEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Class tha implements the conection with the persistence for the
 * entity relationship Unit->Requester.
 * @author Daniel del Castillo A.
 */
@Stateless
public class UnitRequesterLogic 
{
    // Constants
    
    /**
     * The UnitRequesterLogic's logger.
     */
    private static final Logger LOGGER = Logger.getLogger(UnitRequesterLogic.class.getName());
    
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
     * Adds a given requester to a given unit by setting the requester's unit to the one given.
     * @param requesterId The id of the requester that will be asociated to the given unit.
     * @param unitId The id of the unit that will be asociated to the given requester.
     * @return The requester entity with the added unit.
     */
    public RequesterEntity addRequester(Long unitId, Long requesterId)
    {
        LOGGER.log(Level.INFO, "Adding the requester with id = {0} to the unit with id = {1}.", new Object[] {requesterId, unitId});
        UnitEntity unitEntity = unitPersistence.getUnit(unitId);
        RequesterEntity requesterEntity = requesterPersistence.getRequester(requesterId);
        requesterEntity.setUnit(unitEntity);
        LOGGER.log(Level.INFO, "Exiting the addition the requester with id = {0} to the unit with id = {1}.", new Object[] {requesterId, unitId});
        return requesterEntity;
    }
    
    /**
     * @param unitId The id of the unit whose requesters will be retrieved.
     * @return The list of requester entities of the given unit.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException 
     * If there isn't a unit with the given id.
     */
    public List<RequesterEntity> getRequesters(Long unitId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Getting the requesters of the unit with id = {0}.", unitId);
        UnitEntity unit = unitPersistence.getUnit(unitId);
        if(unit == null)
            throw new BusinessLogicException("No hay unidad con ese id.");
        LOGGER.log(Level.INFO, "Exiting the acquirement the requesters of the unit with id = {0}.", unitId);
        return unit.getRequesters();
    }
    
    /**
     * @param requesterId The id of the requester asociated with the given unit.
     * @param unitId The id of the unit that has the given requester.
     * @return The requester asociated with the given unit.
     * @throws BusinessLogicException If the unit or requester doesn't exist
     * or if the given requester isn't asociated with the given unit.
     */
    public RequesterEntity getRequester(Long unitId, Long requesterId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Getting the requester with id = {0} of the unit with id = {1}.", new Object[] {requesterId, unitId});
        List<RequesterEntity> requesters = getRequesters(unitId); 
        RequesterEntity requester = requesterPersistence.getRequester(requesterId);
        if(requester == null)
            throw new BusinessLogicException("No hay solicitador con ese id.");
        int index = requesters.indexOf(requester);
        LOGGER.log(Level.INFO, "Exiting the acquirement the requester with id = {0} of the unit with id = {1}.", new Object[] {requesterId, unitId});
        if(index >= 0)
            return requesters.get(index);
        throw new BusinessLogicException("No hay solicitador asociada a esa unidad.");
    }
    
    /**
     * Replaces the given unit's requesters for the ones given.
     * @param unitId The id of the unit whose requesters will be replaced.
     * @param requesters The list of requesters that will become the unit's new requesters.
     * @return The unit's new requesters.
     * @throws BusinessLogicException If the unit doesn't exist.
     */
    public List<RequesterEntity> replaceRequesters(Long unitId, List<RequesterEntity> requesters) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Updating the requesters of the unit with id = {0}.", unitId);
        UnitEntity unit = unitPersistence.getUnit(unitId);
        if(unit == null)
            throw new BusinessLogicException("No hay unidad con ese id.");
        List<RequesterEntity> requesterList = requesterPersistence.getRequesters();
        for(RequesterEntity requester : requesterList)
        {
            if(requesters.contains(requester))
                requester.setUnit(unit);
            else if(requester.getUnit() != null && requester.getUnit().equals(unit))
                requester.setUnit(null);
        }
        LOGGER.log(Level.INFO, "Exiting the update of the requesters of the unit with id = {0}.", unitId);
        return requesters;
    }
}
