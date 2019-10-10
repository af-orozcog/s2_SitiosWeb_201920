/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.HardwareEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.HardwarePersistence;
import co.edu.uniandes.csw.sitiosweb.persistence.ProjectPersistence;
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
    @Inject
    private ProjectPersistence projectPersistence;

    public HardwareEntity createHardware(Long projectsId, HardwareEntity hardware) throws BusinessLogicException{
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
        
        if(noExisteProject(projectsId)){
            throw new BusinessLogicException("El proyecto al que esta asociado no existe");
        }
        
        hardware = persistence.create(hardware);
        return hardware;
    }
    
    private boolean noExisteProject(Long id){
        ProjectEntity entity = projectPersistence.find(id);
        return entity==null;
    }
    
    /**
     * Devuelve todos los hardwares que hay en la base de datos.
     *
     * @param projectsId
     * @return Lista de entidades de tipo hardware.
     */
    public HardwareEntity getHardwares(Long projectsId) {
        ProjectEntity entity = projectPersistence.find(projectsId);
        return entity.getHardware();
    }

    /**
     * Busca un hardware por ID
     *
     * @param hardwareId El id del hardware a buscar
     * @return El hardware encontrado, null si no lo encuentra.
     */
    public HardwareEntity getHardware(Long projectId, Long hardwareId) {
        return persistence.find(hardwareId);
    }

    /**
     * Actualizar un hardware por ID
     *
     * @param projectId
     * @param hardware La entidad del libro con los cambios deseados
     * @return La entidad del hardware luego de actualizarla
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    public HardwareEntity updateHardware(Long projectId, HardwareEntity hardware) throws BusinessLogicException {
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
        
        ProjectEntity projectEntity = projectPersistence.find(projectId);
        hardware.setProject(projectEntity);

        HardwareEntity newEntity = persistence.update(hardware);
        return newEntity;
    }
    
    /**
     *
     * @param projectsId
     * @param hardwareId
     * @throws BusinessLogicException
     */
    public void deleteHardware(Long projectsId, Long hardwareId) throws BusinessLogicException {
        HardwareEntity old = getHardware(projectsId, hardwareId);
        if (old == null) {
            throw new BusinessLogicException("El hardware con id = " + hardwareId + " no esta asociado a el project con id = " + projectsId);
        }
        persistence.delete(old.getId());
    }

 
}
