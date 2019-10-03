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
    
    public IterationEntity createIteration(IterationEntity iteracion) throws BusinessLogicException {
        if(iteracion.getBeginDate() == null)
           throw new BusinessLogicException("la fecha de inicio esta vacia"); 
        if(iteracion.getEndDate() == null)
            throw new BusinessLogicException("la fecha final esta vacia");
        if(iteracion.getObjetive() == null)
            throw new BusinessLogicException("el objetivo esta vacio");
        if(iteracion.getValidationDate() == null)
            throw new BusinessLogicException("la fecha de validación esta vacia");
        iteracion = persistence.create(iteracion);
        return iteracion;
    }
    
    /**
     * Obtiene la lista de los registros de iterations.
     *
     * @return Colección de objetos de IterationEntity.
     */
    public List<IterationEntity> getIterations() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los autores");
        List<IterationEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los autores");
        return lista;
    }
    
    /**
     * Obtiene los datos de una instancia de Author a partir de su ID.
     *
     * @param iterationId identificador de la iteración
     * @return Instancia de AuthorEntity con los datos del Author consultado.
     */
    public IterationEntity getIteration(Long iterationId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el autor con id = {0}", iterationId);
        IterationEntity iterationEntity = persistence.find(iterationId);
        if (iterationEntity == null) {
            LOGGER.log(Level.SEVERE, "La editorial con el id = {0} no existe", iterationId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el autor con id = {0}", iterationId);
        return iterationEntity;
    }
    
    /**
     * Actualiza la información de una instancia de Author.
     *
     * @param projectId id del proyecto al que pertenece
     * @param iterationEntity Instancia de IterationEntity con los nuevos datos.
     * @return Instancia de AuthorEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    public IterationEntity updateIteration(Long projectId, IterationEntity iterationEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el autor con id = {0}", iterationEntity.getId());
         if(iterationEntity.getBeginDate() == null)
           throw new BusinessLogicException("la fecha de inicio esta vacia"); 
        if(iterationEntity.getEndDate() == null)
            throw new BusinessLogicException("la fecha final esta vacia");
        if(iterationEntity.getObjetive() == null)
            throw new BusinessLogicException("el objetivo esta vacio");
        if(iterationEntity.getValidationDate() == null)
            throw new BusinessLogicException("la fecha de validación esta vacia");
        ProjectEntity projectEntity = projectPersistence.find(projectId);
        iterationEntity.setProject(projectEntity);
        IterationEntity newIterationEntity = persistence.update(iterationEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actual nizar el autor con id = {0}", iterationEntity.getId());
        return newIterationEntity;
    }
   
    /**
     * Elimina una instancia de Author de la base de datos.
     *
     * @param iterationsId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el autor tiene libros asociados.
     */
    public void deleteIteration(Long iterationsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el autor con id = {0}", iterationsId);
        IterationEntity iteration = getIteration(iterationsId);
        if (iteration == null) {
            throw new BusinessLogicException("No se puede borrar la iteracion con id = " + iterationsId + " porque no existe");
        }
        persistence.delete(iterationsId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el autor con id = {0}", iterationsId);
    }
}
