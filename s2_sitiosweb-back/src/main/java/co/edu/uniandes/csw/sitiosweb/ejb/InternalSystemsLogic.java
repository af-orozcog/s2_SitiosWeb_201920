/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.InternalSystemsEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.InternalSystemsPersistence;
import co.edu.uniandes.csw.sitiosweb.persistence.ProjectPersistence;
import java.util.logging.Logger;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
  * @author Andres Martinez Silva 
 */

@Stateless
public class InternalSystemsLogic {
    
    private static final Logger LOGGER = Logger.getLogger(InternalSystemsLogic.class.getName());
    
    @Inject
    private InternalSystemsPersistence persistence;
    
    @Inject
    private ProjectPersistence projectPersistence;
    
    public InternalSystemsEntity createInternalSystems(Long projectId,InternalSystemsEntity iteracion) throws BusinessLogicException {
        if(iteracion.getType() == null)
           throw new BusinessLogicException("la fecha de inicio esta vacia"); 
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
     * Obtiene la lista de los registros de iterations.
     *
     * @return Colección de objetos de InternalSystemsEntity.
     */
    public List<InternalSystemsEntity> getInternalSystems(Long projectId) {
        ProjectEntity projectEntity = projectPersistence.find(projectId);
        return projectEntity.getInternalSystems();
    }
    
    /**
     * Obtiene los datos de una instancia de Author a partir de su ID.
     *
     * @param iterationId identificador de la iteración
     * @return Instancia de AuthorEntity con los datos del Author consultado.
     */
    public InternalSystemsEntity getInternalSystems(Long projectId, Long iterationId) {
        return persistence.find(projectId, iterationId);
    }
    
    /**
     * Actualiza la información de una instancia de Author.
     *
     * @param projectId id del proyecto al que pertenece
     * @param iterationEntity Instancia de InternalSystemsEntity con los nuevos datos.
     * @return Instancia de AuthorEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    public InternalSystemsEntity updateInternalSystems(Long projectId, InternalSystemsEntity iterationEntity) throws BusinessLogicException {
         if(iterationEntity.getType() == null)
           throw new BusinessLogicException("la fecha de inicio esta vacia"); 
        if(noExisteProject(projectId))
            throw new BusinessLogicException("el proyecto no existe");
        ProjectEntity projectEntity = projectPersistence.find(projectId);
        iterationEntity.setProject(projectEntity);
        InternalSystemsEntity newInternalSystemsEntity = persistence.update(iterationEntity);
        return newInternalSystemsEntity;
    }
   
    /**
     * Elimina una instancia de iteración de la base de datos.
     *
     * @param iterationsId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el autor tiene libros asociados.
     */
    public void deleteInternalSystems(Long projectId, Long iterationId) throws BusinessLogicException {
        InternalSystemsEntity old = getInternalSystems(projectId, iterationId);
        if (old == null) {
            throw new BusinessLogicException("El review con id = " + iterationId + " no esta asociado a el proyecto con id = " + projectId);
        }
        persistence.delete(old.getId());
    }
    
    
}