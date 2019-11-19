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
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author s.santosb
 */
@Stateless
public class HardwareLogic {
    
    private static final Logger LOGGER = Logger.getLogger(HardwareLogic.class.getName());
    
    @Inject
    private HardwarePersistence persistence;
    
    @Inject
    private ProjectPersistence projectPersistence;
    
    public HardwareEntity createHardware(Long projectId,HardwareEntity iteracion) throws BusinessLogicException {
        System.out.println(projectId);
        if(iteracion.getIp() == null)
           throw new BusinessLogicException("la fecha de inicio esta vacia"); 
        if(iteracion.getRam() == 0)
           throw new BusinessLogicException("la fecha de inicio esta vacia"); 
        if(iteracion.getCores() == 0)
            throw new BusinessLogicException("la fecha final esta vacia");
        if(iteracion.getCpu() == null)
            throw new BusinessLogicException("el objetivo esta vacio");
        if(iteracion.getPlataforma() == null)
            throw new BusinessLogicException("la fecha de validación esta vacia");
        if(noExisteProject(projectId))
            throw new BusinessLogicException("el proyecto al que esta asociado no existe");
        iteracion.setProject(projectPersistence.find(projectId));
        iteracion = persistence.create(iteracion);
        
        return iteracion;
    }
    
    /**
     * Método que notifica si hay un proyecto relacionado con el id pasado por parametro
     * @param id el id del proyecto que se quiere consultar
     * @return falso o verdadero si existe el proyecto
     */
    private boolean noExisteProject(Long id){
        ProjectEntity entity = projectPersistence.find(id);
        return entity==null;
    }
    
    /**
     * Obtiene la lista de los registros de hardwares.
     *
     * @param projectId
     * @return Colección de objetos de HardwareEntity.
     */
    public HardwareEntity getHardware(Long projectId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los reviews asociados al book con id = {0}", projectId);
        ProjectEntity projectEntity = projectPersistence.find(projectId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los reviews asociados al book con id = {0}", projectId);
        return projectEntity.getHardware();
    }
    
    /**
     * Obtiene los datos de una instancia de Author a partir de su ID.
     *
     * @param projectId
     * @param hardwareId identificador de la iteración
     * @return Instancia de AuthorEntity con los datos del Author consultado.
     */
    public HardwareEntity getHardware(Long projectId, Long hardwareId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la iteracion con id = {0} del proyecto con id = " + projectId, hardwareId);
        return persistence.find(projectId, hardwareId);
    }
    
    /**
     * Actualiza la información de una instancia de Author.
     *
     * @param projectId id del proyecto al que pertenece
     * @param hardwareId id de la iteración
     * @param hardwareEntity Instancia de HardwareEntity con los nuevos datos.
     * @return Instancia de AuthorEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    public HardwareEntity updateHardware(Long projectId, HardwareEntity hardwareEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar una iteracion con id = {0} en el proyecto "+ projectId, hardwareEntity.getId());
        if(hardwareEntity.getRam() == 0)
           throw new BusinessLogicException("la fecha de inicio esta vacia"); 
        if(hardwareEntity.getCores() == 0)
            throw new BusinessLogicException("la fecha final esta vacia");
        if(hardwareEntity.getCpu() == null)
            throw new BusinessLogicException("el objetivo esta vacio");
        if(hardwareEntity.getPlataforma() == null)
            throw new BusinessLogicException("la fecha de validación esta vacia");
        if(noExisteProject(projectId))
            throw new BusinessLogicException("el proyecto al que esta asociado no existe");
        ProjectEntity projectEntity = projectPersistence.find(projectId);
        hardwareEntity.setProject(projectEntity);
        HardwareEntity newHardwareEntity = persistence.update(hardwareEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualzar una iteración con id = {0}", hardwareEntity.getId());
        return newHardwareEntity;
    }
   
    /**
     * Elimina una instancia de iteración de la base de datos.
     *
     * @param hardwaresId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el autor tiene libros asociados.
     */
    public void deleteHardware(Long projectId, Long hardwareId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el review con id = {0} del libro con id = " + projectId, hardwareId);
        HardwareEntity old = getHardware(projectId, hardwareId);
        if (old == null) {
            throw new BusinessLogicException("El review con id = " + hardwareId + " no esta asociado a el proyecto con id = " + projectId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el review con id = {0} del libro con id = " + projectId, hardwareId);
    }

}
