/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.UnitEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.UnitPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Daniel del Castillo A.
 */
@Stateless
public class UnitLogic 
{
    // Attributes
    
    @Inject
    private UnitPersistence persistence;
    
    // Methods
    
    /**
     * Method that creates an unit entity through the persistence.
     * @param unit The unit to create.
     * @return  The created unit.
     */
    public UnitEntity createUnit(UnitEntity unit) throws BusinessLogicException
    {
        if(unit.getName() == null)
            throw new BusinessLogicException("El nombre de la unidad está vacío.");
        unit = persistence.create(unit);
        return unit;
    }
}
