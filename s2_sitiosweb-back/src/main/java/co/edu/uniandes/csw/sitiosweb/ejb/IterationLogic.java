/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.IterationEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.IterationPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Estudiante Andres Felipe Orozco Gonzalez
 */
@Stateless
public class IterationLogic {
    @Inject
    private IterationPersistence persistence;
    
    public IterationEntity createIteration(IterationEntity iteracion) throws BusinessLogicException {
        if(iteracion.getBeginDate() == null){
           throw new BusinessLogicException("la fecha de inicio esta vacia"); 
        }
        iteracion = persistence.create(iteracion);
        return iteracion;
    }
}
