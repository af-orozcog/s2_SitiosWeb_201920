/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.dtos;

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
    * Esta lista de tipo InternalSystemsDTO contiene los InternalSystems que estan asociados a un proyecto.
     */
    //private List<InternalSystemsDTO> internalSystems;
    
     /**
     * Constructor por defecto
     */       
    public ProjectDetailDTO (){
        
    }
    
     /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param projectEntity La entidad del project para transformar a DTO.
     */
    //public ProjectDetailDTO(ProjectEntity projectEntity) {
    //    super(projectEntity);
    //    if (projectEntity != null) {
    //        if (ProjectEntity.getBooks() != null) {
    //            books = new ArrayList<>();
    //            for (BookEntity entityBook : editorialEntity.getBooks()) {
    //                books.add(new BookDTO(entityBook));
    //            }
    //        }
    //    }
    //}
}
