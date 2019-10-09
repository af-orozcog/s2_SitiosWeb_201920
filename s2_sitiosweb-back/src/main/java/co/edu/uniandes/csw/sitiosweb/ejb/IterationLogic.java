/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.IterationEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.IterationPersistence;
import co.edu.uniandes.csw.sitiosweb.persistence.ProjectPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.inject.Inject;


/**
 *
 * @author Estudiante Andres Felipe Orozco Gonzalez af.orozcog 201730058
 */
@Stateless
public class IterationLogic {
    
    private static final Logger LOGGER = Logger.getLogger(IterationLogic.class.getName());
    
    @Inject
    private IterationPersistence persistence;
    
    @Inject
    private ProjectPersistence projectPersistence;
    
    public IterationEntity createIteration(Long projectId,IterationEntity iteracion) throws BusinessLogicException {
        System.out.println(projectId);
        if(iteracion.getBeginDate() == null)
           throw new BusinessLogicException("la fecha de inicio esta vacia"); 
        if(iteracion.getEndDate() == null)
            throw new BusinessLogicException("la fecha final esta vacia");
        if(iteracion.getObjetive() == null)
            throw new BusinessLogicException("el objetivo esta vacio");
        if(iteracion.getValidationDate() == null)
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
     * Obtiene la lista de los registros de iterations.
     *
     * @return Colección de objetos de IterationEntity.
     */
    public List<IterationEntity> getIterations(Long projectId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los reviews asociados al book con id = {0}", projectId);
        ProjectEntity projectEntity = projectPersistence.find(projectId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los reviews asociados al book con id = {0}", projectId);
        return projectEntity.getIterations();
    }
    
    /**
     * Obtiene los datos de una instancia de Author a partir de su ID.
     *
     * @param iterationId identificador de la iteración
     * @return Instancia de AuthorEntity con los datos del Author consultado.
     */
    public IterationEntity getIteration(Long projectId, Long iterationId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la iteracion con id = {0} del proyecto con id = " + projectId, iterationId);
        return persistence.find(projectId, iterationId);
    }
    
    /**
     * Actualiza la información de una instancia de Author.
     *
     * @param projectId id del proyecto al que pertenece
     * @param iterationId id de la iteración
     * @param iterationEntity Instancia de IterationEntity con los nuevos datos.
     * @return Instancia de AuthorEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    public IterationEntity updateIteration(Long projectId, IterationEntity iterationEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar una iteracion con id = {0} en el proyecto "+ projectId, iterationEntity.getId());
         if(iterationEntity.getBeginDate() == null)
           throw new BusinessLogicException("la fecha de inicio esta vacia"); 
        if(iterationEntity.getEndDate() == null)
            throw new BusinessLogicException("la fecha final esta vacia");
        if(iterationEntity.getObjetive() == null)
            throw new BusinessLogicException("el objetivo esta vacio");
        if(iterationEntity.getValidationDate() == null)
            throw new BusinessLogicException("la fecha de validación esta vacia");
        if(noExisteProject(projectId))
            throw new BusinessLogicException("el proyecto no existe");
        ProjectEntity projectEntity = projectPersistence.find(projectId);
        iterationEntity.setProject(projectEntity);
        IterationEntity newIterationEntity = persistence.update(iterationEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualzar una iteración con id = {0}", iterationEntity.getId());
        return newIterationEntity;
    }
   
    /**
     * Elimina una instancia de iteración de la base de datos.
     *
     * @param iterationsId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el autor tiene libros asociados.
     */
    public void deleteIteration(Long projectId, Long iterationId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el review con id = {0} del libro con id = " + projectId, iterationId);
        IterationEntity old = getIteration(projectId, iterationId);
        if (old == null) {
            throw new BusinessLogicException("El review con id = " + iterationId + " no esta asociado a el proyecto con id = " + projectId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el review con id = {0} del libro con id = " + projectId, iterationId);
    }
}
