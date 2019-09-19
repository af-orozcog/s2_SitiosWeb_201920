/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.IterationEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.IterationPersistence;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.util.List;
import java.util.logging.Level;
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
        if(iteracion.getBeginDate() == null)
           throw new BusinessLogicException("la fecha de inicio esta vacia"); 
        if(iteracion.getEndDate() == null)
            throw new BusinessLogicException("la fecha final esta vacia");
        if(iteracion.getChanges() == null)
            throw new BusinessLogicException("los cambios estan vacios");
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
     * @param iterationsId Identificador de la instancia a actualizar
     * @param iterationEntity Instancia de IterationEntity con los nuevos datos.
     * @return Instancia de AuthorEntity con los datos actualizados.
     */
    public IterationEntity updateIteration(Long iterationsId, IterationEntity iterationEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el autor con id = {0}", iterationsId);
        IterationEntity newIterationEntity = persistence.update(iterationEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el autor con id = {0}", iterationsId);
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
