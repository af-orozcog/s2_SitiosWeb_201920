/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.ProviderEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.ProviderPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
  * @author Andres Martinez Silva 
 */

@Stateless
public class ProviderLogic {
    @Inject
    private ProviderPersistence persitence;
    
    public ProviderEntity createPovider(ProviderEntity provider) throws BusinessLogicException{
        if(provider.getName()==null){
            throw new BusinessLogicException("El nombre del proveedor esta vacio");
        }
        
        provider = persitence.create(provider);
        return provider;
    }
}
