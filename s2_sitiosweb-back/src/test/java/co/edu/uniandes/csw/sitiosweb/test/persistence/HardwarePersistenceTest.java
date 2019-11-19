 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.HardwareEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.persistence.HardwarePersistence;
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
 * @author s.santosb
 */
@RunWith(Arquillian.class)
public class HardwarePersistenceTest {
    
    /**
     * atributo necesario para tener transaccionalidad durante las pruebas
     */
    @Inject
    UserTransaction utx;

    /**
     * arreglo donde se guardan algunas iteraciones preestablecidas para
     * probar los métodos
     */
    private List<HardwareEntity> data = new ArrayList<>();
    
    
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
                .addPackage(HardwareEntity.class.getPackage())
                .addPackage(HardwarePersistence.class.getPackage())
                .addPackage(ProjectEntity.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * atributo con el cual se llama a la persistencia
     */
    @Inject
    HardwarePersistence hardwarePersistence;
    
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
        em.createQuery("delete from HardwareEntity").executeUpdate();
        em.createQuery("delete from ProjectEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            HardwareEntity entity = factory.manufacturePojo(HardwareEntity.class);
            em.persist(entity);
            data.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            ProjectEntity entity = factory.manufacturePojo(ProjectEntity.class);
            HardwareEntity en = data.get(i);
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
        HardwareEntity hardware = factory.manufacturePojo(HardwareEntity.class);
        HardwareEntity result = hardwarePersistence.create(hardware);
        Assert.assertNotNull(result);
        
        HardwareEntity entity = em.find(HardwareEntity.class,result.getId());
        Assert.assertEquals(hardware.getCores(),entity.getCores());
        Assert.assertEquals(hardware.getCpu(),entity.getCpu());
        Assert.assertEquals(hardware.getIp(),entity.getIp());
        Assert.assertEquals(hardware.getPlataforma(),entity.getPlataforma());

        
    }
    
    /**
     * Prueba para crear un .
     */
    @Test
    public void createHardwareTest() {
        PodamFactory factory = new PodamFactoryImpl();
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        HardwareEntity result = hardwarePersistence.create(newEntity);

        Assert.assertNotNull(result);

        HardwareEntity entity = em.find(HardwareEntity.class, result.getId());

        Assert.assertEquals(newEntity.getIp(), entity.getIp());
    }
    
    
    /**
     * Prueba para consultar una Iteracion.
     * prueba que los valores devueltos sean correctos
     */
    @Test
    public void getHardwareTest() {
        HardwareEntity entity = data.get(0);
        HardwareEntity newEntity = hardwarePersistence.find(dataProject.get(0).getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getCores(),entity.getCores());
        Assert.assertEquals(newEntity.getCpu(),entity.getCpu());
        Assert.assertEquals(newEntity.getIp(),entity.getIp());
        Assert.assertEquals(newEntity.getPlataforma(),entity.getPlataforma());
    }
    
    
    
    /**
     * Prueba para actualizar una Iteracion.
     */
    @Test
    public void updateHardwareTest() {
        HardwareEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);

        newEntity.setId(entity.getId());

        hardwarePersistence.update(newEntity);

        HardwareEntity resp = em.find(HardwareEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getCores(),resp.getCores());
        Assert.assertEquals(newEntity.getCpu(),resp.getCpu());
        Assert.assertEquals(newEntity.getIp(),resp.getIp());
        Assert.assertEquals(newEntity.getPlataforma(),resp.getPlataforma());
    }
    
    /**
     * Prueba para eliminar una Iteracion.
     */
    @Test
    public void deleteHardwareTest() {
        HardwareEntity entity = data.get(0);
        hardwarePersistence.delete(entity.getId());
        HardwareEntity deleted = em.find(HardwareEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}