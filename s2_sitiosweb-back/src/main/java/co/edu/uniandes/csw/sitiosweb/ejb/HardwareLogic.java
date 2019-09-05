/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.HardwareEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.HardwarePersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author s.santosb
 */
@Stateless
public class HardwareLogic {
    @Inject
    private HardwarePersistence persitence;
    
    public HardwareEntity createHardware(HardwareEntity hardware) throws BusinessLogicException{
        if(hardware.getIp()==null){
            throw new BusinessLogicException("El ip del hardware esta vacio");
        }
        
        hardware = persitence.create(hardware);
        return hardware;
    }
 
}
