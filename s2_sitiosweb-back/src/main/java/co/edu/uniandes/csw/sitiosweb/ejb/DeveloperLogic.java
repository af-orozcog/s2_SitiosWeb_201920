/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.DeveloperPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@Stateless
public class DeveloperLogic {

    private static final Logger LOGGER = Logger.getLogger(DeveloperLogic.class.getName());

    @Inject
    private DeveloperPersistence persistence;

    /**
     * Se encarga de crear un DeveloperEntity en la base de datos.
     *
     * @param developer Objeto de DeveloperEntity con los datos nuevos
     * @return Objeto de DeveloperEntity con los datos nuevos y su ID.
     * @throws BusinessLogicException si el developer tiene datos inválidos.
     */
    public DeveloperEntity createDeveloper(DeveloperEntity developer) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del desarrollador");
        if(developer.getName() == null )
            throw new BusinessLogicException( "El nombre del desarrollador está vacío" );
        if(developer.getLogin() == null )
            throw new BusinessLogicException( "El login del desarrollador está vacío" );
        if(developer.getEmail() == null )
            throw new BusinessLogicException( "El email del desarrollador está vacío" );
        if(!validatePhone(developer.getPhone()))
            throw new BusinessLogicException("El teléfono es inválido");
        if(developer.getImage() == null )
            developer.setImage("https://genslerzudansdentistry.com/wp-content/uploads/2015/11/anonymous-user-300x296.png");

        developer = persistence.create(developer);
        LOGGER.log(Level.INFO, "Termina proceso de creación del desarrollador");
        return developer;
    }

    /**
     * Obtiene la lista de los registros de Developer.
     *
     * @return Colección de objetos de DeveloperEntity.
     */
    public List<DeveloperEntity> getDevelopers() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los desarrolladores");
        List<DeveloperEntity> developers = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los desarrolladores");
        return developers;
    }

    /**
     * Obtiene los datos de una instancia de Developer a partir de su ID.
     *
     * @param developerId Identificador de la instancia a consultar
     * @return Instancia de DeveloperEntity con los datos del Developer
     * consultado.
     */
    public DeveloperEntity getDeveloper(Long developerId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el desarrollador con id = {0}", developerId);
        DeveloperEntity developerEntity = persistence.find(developerId);
        if (developerEntity == null) {
            LOGGER.log(Level.SEVERE, "El desarrollador con el id = {0} no existe", developerId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el desarrollador con id = {0}", developerId);
        return developerEntity;
    }
    
     /**
     * Actualizar un desarrollador por ID
     *
     * @param developerId El ID del desarrollador a actualizar
     * @param developerEntity La entidad del desarrollador con los cambios deseados
     * @return La entidad del desarrollador luego de actualizarla
     * @throws BusinessLogicException Si tiene datos nuevos inválidos
     */
    public DeveloperEntity updateDeveloper(Long developerId, DeveloperEntity developerEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el desarrollador con id = {0}", developerId);
        if(developerEntity.getName() == null )
            throw new BusinessLogicException( "El nombre del desarrollador está vacío" );
        if(developerEntity.getLogin() == null )
            throw new BusinessLogicException( "El login del desarrollador está vacío" );
        if(developerEntity.getEmail() == null )
            throw new BusinessLogicException( "El email del desarrollador está vacío" );
        if(!validatePhone(developerEntity.getPhone()))
            throw new BusinessLogicException("El teléfono es inválido");
        
        if(!developerEntity.getLeader() && !developerEntity.getLeadingProjects().isEmpty())
            throw new BusinessLogicException("El desarrollador está liderando proyectos, no puede dejar de ser lider");
        
        DeveloperEntity newEntity = persistence.update(developerEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el desarrollador con id = {0}", developerEntity.getId());
        return newEntity;
    }

    /**
     * Elimina una instancia de Developer de la base de datos.
     *
     * @param developerId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el desarrollador tiene proyectos
     * asosciados.
     */
    public void deleteDeveloper(Long developerId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el desarrollador con id = {0}", developerId);
        List<ProjectEntity> projects = getDeveloper(developerId).getProjects();
        List<ProjectEntity> leadingProjects = getDeveloper(developerId).getLeadingProjects();

        if (projects != null && !projects.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el desarrollador con id = " + developerId + " porque tiene proyectos asociados");
        }
        if (leadingProjects != null && !leadingProjects.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el desarrollador con id = " + developerId + " porque esta liderando proyectos asociados");
        }
        persistence.delete(developerId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el desarrollador con id = {0}", developerId);
    }
    
    private boolean validatePhone(String phone) {
        if(phone == null || phone.length() != 10) return false;
        boolean f = true;
        for(int i=0; i<10; i++){
            if(!(phone.charAt(i) >= '0' && phone.charAt(i) <= '9'))
                f=false;
        }
        return f;
    }
}
