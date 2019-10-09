/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.IterationEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import co.edu.uniandes.csw.sitiosweb.persistence.IterationPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Before;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Andres Felipe Orozco Gonzalez af.orozcog 201730058
 */
@RunWith(Arquillian.class)
public class IterationPersistenceTest {
   
    /**
     * atributo necesario para tener transaccionalidad durante las pruebas
     */
    @Inject
    UserTransaction utx;

    /**
     * arreglo donde se guardan algunas iteraciones preestablecidas para
     * probar los métodos
     */
    private List<IterationEntity> data = new ArrayList<>();
    
    
    /**
     * donde se van a preestablecer algunos datos para probar los 
     * métodos de la logica
     */
    private List<ProjectEntity> dataProject = new ArrayList<ProjectEntity>();
    
    /**
     * Método necesario para generar un contexto en el cual se 
     * va a llevar a cabo el despliegue
     * @return el contexto
     */
    @Deployment
    public static JavaArchive createDeploymet(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(IterationEntity.class.getPackage())
                .addPackage(IterationPersistence.class.getPackage())
                .addPackage(ProjectEntity.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * atributo con el cual se llama a la persistencia
     */
    @Inject
    IterationPersistence iterationPersistence;
    
    /**
     * manejador del contexto de persistencia
     */
    @PersistenceContext
    protected EntityManager em;
    
    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
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
        em.createQuery("delete from IterationEntity").executeUpdate();
        em.createQuery("delete from ProjectEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            IterationEntity entity = factory.manufacturePojo(IterationEntity.class);
            em.persist(entity);
            data.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            ProjectEntity entity = factory.manufacturePojo(ProjectEntity.class);
            IterationEntity en = data.get(i);
            en.setProject(entity);
            em.persist(en);
            em.persist(entity);
            dataProject.add(entity);
        }
    }
    
    
    /**
     * Método para la creación de la iteracion
     */
    @Test
    public void createTest(){
        PodamFactory factory = new PodamFactoryImpl();
        IterationEntity iteration = factory.manufacturePojo(IterationEntity.class);
        IterationEntity result = iterationPersistence.create(iteration);
        Assert.assertNotNull(result);
        
        IterationEntity entity = em.find(IterationEntity.class,result.getId());
        Assert.assertEquals(iteration.getObjetive(),entity.getObjetive());
        
    }
    
    /**
     * Prueba para crear un .
     */
    @Test
    public void createIterationTest() {
        PodamFactory factory = new PodamFactoryImpl();
        IterationEntity newEntity = factory.manufacturePojo(IterationEntity.class);
        IterationEntity result = iterationPersistence.create(newEntity);

        Assert.assertNotNull(result);

        IterationEntity entity = em.find(IterationEntity.class, result.getId());

        Assert.assertEquals(newEntity.getObjetive(), entity.getObjetive());
    }
    
    
    /**
     * Prueba para consultar una Iteracion.
     * prueba que los valores devueltos sean correctos
     */
    @Test
    public void getIterationTest() {
        IterationEntity entity = data.get(0);
        IterationEntity newEntity = iterationPersistence.find(dataProject.get(0).getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getObjetive(), entity.getObjetive());
        Assert.assertEquals(newEntity.getValidationDate(), entity.getValidationDate());
    }
    
    /**
     * Prueba para actualizar una Iteracion.
     */
    @Test
    public void updateIterationTest() {
        IterationEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        IterationEntity newEntity = factory.manufacturePojo(IterationEntity.class);

        newEntity.setId(entity.getId());

        iterationPersistence.update(newEntity);

        IterationEntity resp = em.find(IterationEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getObjetive(), resp.getObjetive());
        Assert.assertEquals(newEntity.getValidationDate(), resp.getValidationDate());
    }
    
    /**
     * Prueba para eliminar una Iteracion.
     */
    @Test
    public void deleteIterationTest() {
        IterationEntity entity = data.get(0);
        iterationPersistence.delete(entity.getId());
        IterationEntity deleted = em.find(IterationEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
