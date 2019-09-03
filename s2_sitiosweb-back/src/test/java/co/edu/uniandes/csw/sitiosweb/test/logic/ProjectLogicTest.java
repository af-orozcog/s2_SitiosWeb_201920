/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.ProjectLogic;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.ProjectPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Daniel Galindo Ruiz
 */
@RunWith(Arquillian.class)
public class ProjectLogicTest {
    
   private PodamFactory factory = new PodamFactoryImpl();
   
   @Inject
   private ProjectLogic projectLogic;
   
   @PersistenceContext
   EntityManager em;
   
    @Deployment
   public static JavaArchive createDeployment(){
       return ShrinkWrap.create(JavaArchive.class)
               .addPackage(ProjectEntity.class.getPackage())
               .addPackage(ProjectLogic.class.getPackage())
               .addPackage(ProjectPersistence.class.getPackage())
               .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
               .addAsManifestResource("META-INF/beans.xml", "beans.xml");
   }
   
   @Test
   public void createProject()throws BusinessLogicException{
       ProjectEntity newEntity = factory.manufacturePojo(ProjectEntity.class);
       ProjectEntity result = projectLogic.createProject(newEntity);
       Assert.assertNotNull(result);
       
       ProjectEntity entity = em.find(ProjectEntity.class, result.getId());
       Assert.assertEquals(entity.getCompany(), result.getCompany());
   }
   
   @Test(expected = BusinessLogicException.class)
   public void createProjectCompanyNull() throws BusinessLogicException{
       
       ProjectEntity newEntity = factory.manufacturePojo(ProjectEntity.class);
       newEntity.setCompany(null);
       ProjectEntity result = projectLogic.createProject(newEntity);
   }
}
