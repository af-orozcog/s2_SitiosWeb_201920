/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.HardwareEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.HardwarePersistence;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author s.santosb
 */
@Stateless
public class HardwareLogic {
    @Inject
    private HardwarePersistence persistence;
    
    public HardwareEntity createHardware(HardwareEntity hardware) throws BusinessLogicException{
        if(hardware.getIp()==null){
            throw new BusinessLogicException("El ip del hardware esta vacio");
        }
        
        if(hardware.getCores()==0){
            throw new BusinessLogicException("Los nucleos del harware no son correctos");
        }
        
        if(hardware.getRam()==0){
            throw new BusinessLogicException("La RAM del hardware no es valida");
        }
        
        if(hardware.getCpu()==null){
            throw new BusinessLogicException("El cpu del hardware esta vacio");
        }
        
        if(hardware.getPlataforma()==null){
            throw new BusinessLogicException("La plataforma del hardware esta vacia");
        }
        
        hardware = persistence.create(hardware);
        return hardware;
    }
    
    /**
     * Devuelve todos los hardwares que hay en la base de datos.
     *
     * @return Lista de entidades de tipo hardware.
     */
    public List<HardwareEntity> getHardwares() {
        List<HardwareEntity> hardwares = persistence.findAll();
        return hardwares;
    }

    /**
     * Busca un hardware por ID
     *
     * @param hardwareId El id del hardware a buscar
     * @return El hardware encontrado, null si no lo encuentra.
     */
    public HardwareEntity getHardware(Long hardwareId) {
        HardwareEntity hardwareEntity = persistence.find(hardwareId);
        return hardwareEntity;
    }

    /**
     * Actualizar un hardware por ID
     *
     * @param hardwareId El ID del libro a actualizar
     * @param hardwareEntity La entidad del libro con los cambios deseados
     * @return La entidad del hardware luego de actualizarla
     */
    public HardwareEntity updateHardware(Long hardwareId, HardwareEntity hardwareEntity) {
        HardwareEntity newEntity = persistence.update(hardwareEntity);
        return newEntity;
    }
 
}
