package co.edu.uniandes.csw.sitiosweb.dtos;

import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProviderEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andres Martinez Silva
 */
public class ProviderDetailDTO extends ProviderDTO implements Serializable{
    
    private List<ProjectDTO> projects;
    
    public ProviderDetailDTO(){
        super();
    }
    
    public ProviderDetailDTO(ProviderEntity providerEntity){
        super(providerEntity);
        if(providerEntity.getProjects() != null){
            projects = new ArrayList<>();
            for(ProjectEntity entityProject: providerEntity.getProjects()){
                projects.add(new ProjectDTO(entityProject));
            }
        }
    }
    
    @Override
    public ProviderEntity toEntity(){
        ProviderEntity providerEntity= super.toEntity();
         if(getProjects() != null){
             ArrayList<ProjectEntity> projectsEntity = new ArrayList<>();
             for(ProjectDTO dtoProject: getProjects()){
                projectsEntity.add(dtoProject.toEntity());
            }
             providerEntity.setProjects(projectsEntity);
         }
         return providerEntity;
    }

    public List<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }
    
    
    
}
