/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.persistence;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import co.edu.uniandes.csw.sitiosweb.entities.InternalSystemsEntity;
import co.edu.uniandes.csw.sitiosweb.entities.InternalSystemsEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.persistence.InternalSystemsPersistence;
import co.edu.uniandes.csw.sitiosweb.persistence.InternalSystemsPersistence;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


/**
 *
 * @author s.santosb
 */
@RunWith(Arquillian.class)
public class InternalSystemsPersistenceTest {
    /**
     * atributo necesario para tener transaccionalidad durante las pruebas
     */
    @Inject
    UserTransaction utx;

    /**
     * arreglo donde se guardan algunas iteraciones preestablecidas para
     * probar los métodos
     */
    private List<InternalSystemsEntity> data = new ArrayList<>();
    
    
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
                .addPackage(InternalSystemsEntity.class.getPackage())
                .addPackage(InternalSystemsPersistence.class.getPackage())
                .addPackage(ProjectEntity.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * atributo con el cual se llama a la persistencia
     */
    @Inject
    InternalSystemsPersistence internalSystemsPersistence;
    
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
        em.createQuery("delete from InternalSystemsEntity").executeUpdate();
        em.createQuery("delete from ProjectEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            InternalSystemsEntity entity = factory.manufacturePojo(InternalSystemsEntity.class);
            em.persist(entity);
            data.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            ProjectEntity entity = factory.manufacturePojo(ProjectEntity.class);
            InternalSystemsEntity en = data.get(i);
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
        InternalSystemsEntity internalSystems = factory.manufacturePojo(InternalSystemsEntity.class);
        InternalSystemsEntity result = internalSystemsPersistence.create(internalSystems);
        Assert.assertNotNull(result);
        
        InternalSystemsEntity entity = em.find(InternalSystemsEntity.class,result.getId());
        Assert.assertEquals(internalSystems.getType(),entity.getType());
        
    }
    
    /**
     * Prueba para crear un .
     */
    @Test
    public void createInternalSystemsTest() {
        PodamFactory factory = new PodamFactoryImpl();
        InternalSystemsEntity newEntity = factory.manufacturePojo(InternalSystemsEntity.class);
        InternalSystemsEntity result = internalSystemsPersistence.create(newEntity);

        Assert.assertNotNull(result);

        InternalSystemsEntity entity = em.find(InternalSystemsEntity.class, result.getId());

        Assert.assertEquals(newEntity.getType(), entity.getType());
    }
    
    
    /**
     * Prueba para consultar una Iteracion.
     * prueba que los valores devueltos sean correctos
     */
    @Test
    public void getInternalSystemsTest() {
        InternalSystemsEntity entity = data.get(0);
        InternalSystemsEntity newEntity = internalSystemsPersistence.find(dataProject.get(0).getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getType(), entity.getType());
    }
    
    /**
     * Prueba para actualizar una Iteracion.
     */
    @Test
    public void updateInternalSystemsTest() {
        InternalSystemsEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        InternalSystemsEntity newEntity = factory.manufacturePojo(InternalSystemsEntity.class);

        newEntity.setId(entity.getId());

        internalSystemsPersistence.update(newEntity);

        InternalSystemsEntity resp = em.find(InternalSystemsEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getType(), resp.getType());
    }
    
    /**
     * Prueba para eliminar una Iteracion.
     */
    @Test
    public void deleteInternalSystemsTest() {
        InternalSystemsEntity entity = data.get(0);
        internalSystemsPersistence.delete(entity.getId());
        InternalSystemsEntity deleted = em.find(InternalSystemsEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }


}
