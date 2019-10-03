/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.dtos;

import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
import co.edu.uniandes.csw.sitiosweb.entities.InternalSystemsEntity;
import co.edu.uniandes.csw.sitiosweb.entities.IterationEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Galindo Ruiz
 */
public class ProjectDetailDTO extends ProjectDTO implements Serializable{
    
     /*
    * Esta lista de tipo IterationDTO contiene los iterations que estan asociados a un proyecto.
     */
    private List<IterationDTO> iterations;
    
     /*
    * Esta lista de tipo DeveloperDTO contiene los developers que estan asociados a un proyecto.
     */
    private List<DeveloperDTO> developers;
    
         /*
    * Esta lista de tipo DeveloperDTO contiene los developers que estan asociados a un proyecto.
     */
    private List<InternalSystemsDTO> internalSystems;

    
    
    public ProjectDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param projectEntity La entidad de la cual se construye el DTO
     */
    public ProjectDetailDTO(ProjectEntity projectEntity) {
        super(projectEntity);
        if (projectEntity.getDevelopers() != null) {
            developers = new ArrayList<>();
            for (DeveloperEntity entityDeveloper : projectEntity.getDevelopers()) {
                developers.add(new DeveloperDTO(entityDeveloper));
            }
        }
        if (projectEntity.getIterations() != null) {
            iterations = new ArrayList<>();
            for (IterationEntity entityAuthor : projectEntity.getIterations()) {
                iterations.add(new IterationDTO(entityAuthor));
            }
        }
        
        if (projectEntity.getInternalSystems() != null) {
            internalSystems = new ArrayList<>();
            for (InternalSystemsEntity entityInternal : projectEntity.getInternalSystems()) {
                internalSystems.add(new InternalSystemsDTO(entityInternal));
            }
        }
    }

    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el libro.
     */
    @Override
    public ProjectEntity toEntity() {
        ProjectEntity projectEntity = super.toEntity();
        if (getDevelopers() != null) {
            List<DeveloperEntity> developersEntity = new ArrayList<>();
            for (DeveloperDTO DTODeveloper : getDevelopers()) {
                developersEntity.add(DTODeveloper.toEntity());
            }
            projectEntity.setDevelopers(developersEntity);
        }
        
        if (getIterations() != null) {
            List<IterationEntity> iterationsEntity = new ArrayList<>();
            for (IterationDTO DTOIteration : getIterations()) {
                iterationsEntity.add(DTOIteration.toEntity());
            }
            projectEntity.setIterations(iterationsEntity);
        }
        
        if (getInternalSystems() != null) {
            List<InternalSystemsEntity> internalSystemsEntity = new ArrayList<>();
            for (InternalSystemsDTO DTOInternal : getInternalSystems()) {
                internalSystemsEntity.add(DTOInternal.toEntity());
            }
            projectEntity.setInternalSystems(internalSystemsEntity);
        }
       
        return projectEntity;
    }


    /**
     * @return the iterations
     */
    public List<IterationDTO> getIterations() {
        return iterations;
    }

    /**
     * @param iterations the iterations to set
     */
    public void setIterations(List<IterationDTO> iterations) {
        this.iterations = iterations;
    }

    /**
     * @return the developers
     */
    public List<DeveloperDTO> getDevelopers() {
        return developers;
    }

    /**
     * @param developers the developers to set
     */
    public void setDevelopers(List<DeveloperDTO> developers) {
        this.developers = developers;
    }

    /**
     * @return the internalSystems
     */
    public List<InternalSystemsDTO> getInternalSystems() {
        return internalSystems;
    }

    /**
     * @param internalSystems the internalSystems to set
     */
    public void setInternalSystems(List<InternalSystemsDTO> internalSystems) {
        this.internalSystems = internalSystems;
    }
}
