/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.ProjectLogic;
import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
import co.edu.uniandes.csw.sitiosweb.entities.HardwareEntity;
import co.edu.uniandes.csw.sitiosweb.entities.InternalSystemsEntity;
import co.edu.uniandes.csw.sitiosweb.entities.IterationEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProviderEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.ProjectPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
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
public class ProjectLogicTest {
   
    /**
     * Factory to make ProjectEntities classes
     */
   private PodamFactory factory = new PodamFactoryImpl();
   
   /**
    * ProjectLogic to verify rules
    */
   @Inject
   private ProjectLogic projectLogic;
   
   /**
    * EntityManager to handle persistence entities
    */
   @PersistenceContext
   EntityManager em;
   
       /**
     * manejador de transaccionalidad
     */
    @Inject
    private UserTransaction utx;
    
    /**
     * donde se van a preestablecer algunos datos para probar los
     * métodos de la logica
     */
    private List<ProjectEntity> data = new ArrayList<ProjectEntity>();
    
    /**
     * donde se van a preestablecer algunos datos de developers para probar los 
     * métodos de la logica
     */
    private List<DeveloperEntity> dataDeveloper = new ArrayList<DeveloperEntity>();
    
     /**
     * donde se van a preestablecer algunos datos de developers para probar los 
     * métodos de la logica
     */
    private List<IterationEntity> dataIteration = new ArrayList<IterationEntity>();
   
     /**
     * donde se van a preestablecer algunos datos de developers para probar los 
     * métodos de la logica
     */
    private List<HardwareEntity> dataHardware = new ArrayList<HardwareEntity>();
   
      /**
     * donde se van a preestablecer algunos datos de developers para probar los 
     * métodos de la logica
     */
    private List<ProviderEntity> dataProvider = new ArrayList<ProviderEntity>();
   
      /**
     * donde se van a preestablecer algunos datos de developers para probar los 
     * métodos de la logica
     */
    private List<InternalSystemsEntity> dataInternalSystems = new ArrayList<InternalSystemsEntity>();
   
    @Deployment
   public static JavaArchive createDeployment(){
       return ShrinkWrap.create(JavaArchive.class)
               .addPackage(ProjectEntity.class.getPackage())
               .addPackage(ProjectLogic.class.getPackage())
               .addPackage(ProjectPersistence.class.getPackage())
               .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
               .addAsManifestResource("META-INF/beans.xml", "beans.xml");
   }
   
    
    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    } 
     
     
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
    
        em.createQuery("delete from ProjectEntity").executeUpdate();
        em.createQuery("delete from IterationEntity").executeUpdate();
        em.createQuery("delete from ProviderEntity").executeUpdate();
        em.createQuery("delete from DeveloperEntity").executeUpdate();
        em.createQuery("delete from HardwareEntity").executeUpdate();
        em.createQuery("delete from InternalSystemsEntity").executeUpdate();
    }
    
    
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
         for (int i = 0; i < 3; i++) {
            DeveloperEntity entity = factory.manufacturePojo(DeveloperEntity.class);
            em.persist(entity);
            dataDeveloper.add(entity);
        }
         
        for(int i = 0; i < 3; i++){
            IterationEntity entity = factory.manufacturePojo(IterationEntity.class);
            em.persist(entity);
            dataIteration.add(entity);
        }
           
        for(int i = 0; i < 3; i++){
            HardwareEntity entity = factory.manufacturePojo(HardwareEntity.class);
            em.persist(entity);
            dataHardware.add(entity);
        }
            
        for(int i = 0; i < 3; i++){
            ProviderEntity entity = factory.manufacturePojo(ProviderEntity.class);
            em.persist(entity);
            dataProvider.add(entity);
        }
           
        for(int i = 0; i < 3; i++){
            InternalSystemsEntity entity = factory.manufacturePojo(InternalSystemsEntity.class);
            em.persist(entity);
            dataInternalSystems.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            ProjectEntity entity = factory.manufacturePojo(ProjectEntity.class);
            entity.setDevelopers(dataDeveloper);
            DeveloperEntity lider = factory.manufacturePojo(DeveloperEntity.class);
            entity.setLeader(lider);
            entity.setIterations(dataIteration);
            entity.setHardware(dataHardware.get(i));
            entity.setProvider(dataProvider.get(i));
            entity.setInternalSystems(dataInternalSystems);
            em.persist(entity);
            
            data.add(entity);
        }
        for(int i = 0; i< dataDeveloper.size(); i++){
            DeveloperEntity entity = dataDeveloper.get(i);
            entity.setProjects(data);
        }
       
    }
   
   /**
    * test for the creation of a project
    * @throws BusinessLogicException if a rule is broken during the creation of the project.
    */
   @Test
   public void createProject()throws BusinessLogicException{
       ProjectEntity newEntity = factory.manufacturePojo(ProjectEntity.class);
       ProjectEntity result = projectLogic.createProject(newEntity);
       Assert.assertNotNull(result);
       
       ProjectEntity entity = em.find(ProjectEntity.class, result.getId());
       Assert.assertEquals(entity.getCompany(), result.getCompany());
   }
   
   /**
    * Test to create a project with company atribute null
    * @throws BusinessLogicException expected to be thrown as a rule is broken.
    */
   @Test(expected = BusinessLogicException.class)
   public void createProjectCompanyNull() throws BusinessLogicException{
       
       ProjectEntity newEntity = factory.manufacturePojo(ProjectEntity.class);
       newEntity.setCompany(null);
       ProjectEntity result = projectLogic.createProject(newEntity);
   }
   
   /**
    * Test to create project with internalProject atribute null
    * @throws BusinessLogicException expected to be thrown as a rule is broken.
    */
   @Test(expected = BusinessLogicException.class)
   public void createInternalProjectNull() throws BusinessLogicException{
       
       ProjectEntity newEntity = factory.manufacturePojo(ProjectEntity.class);
       newEntity.setInternalProject(null);
       ProjectEntity result = projectLogic.createProject(newEntity);
   }
   
    /**
     * Prueba para consultar la lista de Projects
     */
    @Test
    public void getProjectsTest() {
        List<ProjectEntity> list = projectLogic.getProjects();
        Assert.assertEquals(data.size(), list.size());
        for (ProjectEntity entity : list) {
            boolean found = false;
            for (ProjectEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
   /**
     * Prueba para actualizar un Project.
     */
    @Test
    public void updateProjectTest() throws BusinessLogicException {
        ProjectEntity entity = data.get(0);
        ProjectEntity pojoEntity = factory.manufacturePojo(ProjectEntity.class);
        pojoEntity.setId(entity.getId());
        projectLogic.updateProject(data.get(0).getId(), pojoEntity);

        ProjectEntity resp = em.find(ProjectEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getCompany(), resp.getCompany());
        Assert.assertEquals(pojoEntity.getInternalProject(), resp.getInternalProject());
    } 
    
    /**
     * Prueba para eliminar un proyecto con developers asociados
     *
     * @throws BusinessLogicException
     */
    @Test
    public void deleteProjectTest() throws BusinessLogicException {
        ProjectEntity entity = data.get(0);
        projectLogic.deleteProject(entity.getId());
        IterationEntity deleted = em.find(IterationEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
   
}
