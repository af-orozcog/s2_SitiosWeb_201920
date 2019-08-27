/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.persistence.ProjectPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Daniel Galindo Ruiz
 */
@RunWith(Arquillian.class)
public class ProjectPersistenceTest {
    
   @Deployment
   public static JavaArchive createDeployment(){
       return ShrinkWrap.create(JavaArchive.class)
               .addClass(ProjectEntity.class)
               .addClass(ProjectPersistence.class)
               .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
               .addAsManifestResource("META-INF/beans.xml", "beans.xml");
   }
   
   @Inject
   ProjectPersistence pp;
   
   @PersistenceContext
   EntityManager em;
   
   @Inject
   UserTransaction utx;
   
   private List<ProjectEntity> data = new ArrayList<ProjectEntity>();
   
   /**
    * Configuracion de la prueba
    */
   @Before
   public void configTest(){
       try{
           utx.begin();
           em.joinTransaction();
           clearData();
           insertData();
           utx.commit();
       } 
       catch(Exception e){
           e.printStackTrace();
           try{
               utx.rollback();
           }
           catch(Exception e1){
               e1.printStackTrace();
           }
       }
   }
   
   /**
    * Limpia tablas de la prueba
    */
   private void clearData(){
       em.createQuery("delete from ProjectEntity").executeUpdate();
   }
   
   private void insertData(){
       PodamFactory factory = new PodamFactoryImpl();
       for(int i = 0; i < 3; i++){
           
           ProjectEntity project = factory.manufacturePojo(ProjectEntity.class);
           em.persist(project);
           data.add(project);
       }
   }
   
   @Test
   public void createTest(){
       PodamFactory factory = new PodamFactoryImpl();
       ProjectEntity project = factory.manufacturePojo(ProjectEntity.class);
       ProjectEntity result = pp.create(project);
       Assert.assertNotNull(result);
       
       ProjectEntity entity = em.find(ProjectEntity.class, result.getId());

        Assert.assertEquals(project.getCompany(),entity.getCompany());
        Assert.assertEquals(project.getInternalProject(), entity.getInternalProject());

   }
   
   @Test
   public void getProjectsTest(){
       List<ProjectEntity> list = pp.findAll();
       Assert.assertEquals(data.size(), list.size());
       
       for(ProjectEntity ent:list){
           boolean found = false;
           
           for(ProjectEntity entity:data){
               
               if(ent.getId().equals(entity.getId())){
                   found = true;
               }
           }
           Assert.assertTrue(found);
       }
   }
   
   @Test
   public void getProjectEntiyTest(){
       ProjectEntity entity = data.get(0);
       ProjectEntity newEntity = pp.find(entity.getId());
       Assert.assertNotNull(newEntity);
       Assert.assertEquals(entity.getCompany(), newEntity.getCompany());
   }
   
   @Test
   public void deleteProjectTest(){
       ProjectEntity entity = data.get(0);
       pp.delete(entity.getId());
       ProjectEntity deleted = em.find(ProjectEntity.class, entity.getId());
       Assert.assertNull(deleted);
   }
   
   @Test
   public void updateProjectTest(){
       ProjectEntity entity = data.get(0);
       PodamFactory factory = new PodamFactoryImpl();
       ProjectEntity newEntity = factory.manufacturePojo(ProjectEntity.class);
       
       newEntity.setId(entity.getId());
       pp.update(newEntity);
       
       ProjectEntity resp = em.find(ProjectEntity.class, entity.getId());
       Assert.assertEquals(newEntity.getCompany(), resp.getCompany());
   }
}
