/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.ProjectPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Galindo Ruiz
 */
@Stateless
public class ProjectLogic {
    
    @Inject
    private ProjectPersistence persistence;
    
    public ProjectEntity createProject(ProjectEntity pe) throws BusinessLogicException{
       
        if(pe.getCompany() == null){
            throw new BusinessLogicException("El proyecto ya existe");
        }
        pe = persistence.create(pe);
        return pe;
    }
}
