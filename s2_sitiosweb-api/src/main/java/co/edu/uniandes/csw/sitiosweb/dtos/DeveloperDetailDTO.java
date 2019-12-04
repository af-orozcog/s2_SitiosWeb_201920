package co.edu.uniandes.csw.sitiosweb.dtos;

import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Nicolás Abondano nf.abondano 201812467
 */
public class DeveloperDetailDTO extends DeveloperDTO implements Serializable {

    private List<ProjectDTO> projects;
    
    private List<ProjectDTO> leadingProjects;
    
    public DeveloperDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param developerEntity La entidad de la cual se construye el DTO
     */
    public DeveloperDetailDTO(DeveloperEntity developerEntity) {
        super(developerEntity);
        if (developerEntity.getProjects() != null) {
            projects = new ArrayList<>();
            for (ProjectEntity entityProject : developerEntity.getProjects()) {
                projects.add(new ProjectDTO(entityProject));
            }
        }
    }

    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el libro.
     */
    @Override
    public DeveloperEntity toEntity() {
        DeveloperEntity developerEntity = super.toEntity();
        if (getProjects() != null) {
            List<ProjectEntity> projectsEntity = new ArrayList<>();
            for (ProjectDTO dtoProject : getProjects()) {
                projectsEntity.add(dtoProject.toEntity());
            }
            developerEntity.setProjects(projectsEntity);
        }
        if (getLeadingProjects()!= null ){
            List<ProjectEntity> leadingsEntity = new ArrayList<>();
            for (ProjectDTO dtoProject: getLeadingProjects()){
                leadingsEntity.add(dtoProject.toEntity());
            }
            developerEntity.setLeadingProjects(leadingsEntity);
        }
        return developerEntity;
    }
    
    /**
     * @return the projects
     */
    public List<ProjectDTO> getProjects() {
        return projects;
    }

    /**
     * @param projects the projects to set
     */
    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }
    
    /**
     * @return the leadingProjects
     */
    public List<ProjectDTO> getLeadingProjects() {
        return leadingProjects;
    }

    /**
     * @param leadingProjects the leadingProjects to set
     */
    public void setLeadingProjects(List<ProjectDTO> leadingProjects) {
        this.leadingProjects = leadingProjects;
    }
}
