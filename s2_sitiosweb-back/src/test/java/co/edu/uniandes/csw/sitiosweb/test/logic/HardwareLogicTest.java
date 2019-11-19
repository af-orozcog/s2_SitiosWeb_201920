/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.HardwareLogic;
import co.edu.uniandes.csw.sitiosweb.ejb.ProjectLogic;
import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
import co.edu.uniandes.csw.sitiosweb.entities.HardwareEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.HardwarePersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
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
public class HardwareLogicTest {
    
     /**
     * atributo necesario para crear los datos de las pruebas
     */
    private PodamFactory factory = new PodamFactoryImpl();
    
    /**
     * atributo para conectarse con la capa de logica
     */
    @Inject
    private HardwareLogic hardwareLogic;
    
    @PersistenceContext
    protected EntityManager em;
    
    /**
     * manejador de transaccionalidad
     */
    @Inject
    private UserTransaction utx;
    
    /**
     * donde se van a preestablecer algunos datos para probar los
     * métodos de la logica
     */
    private List<HardwareEntity> data = new ArrayList<HardwareEntity>();
    
    /**
     * donde se van a preestablecer algunos datos para probar los 
     * métodos de la logica
     */
    private List<ProjectEntity> dataProject = new ArrayList<ProjectEntity>();
    
    
    @Deployment
     public static JavaArchive createDeploymet(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(HardwareEntity.class.getPackage())
                .addPackage(HardwarePersistence.class.getPackage())
                .addPackage(HardwareLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
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
        em.createQuery("delete from HardwareEntity").executeUpdate();
        em.createQuery("delete from ProjectEntity").executeUpdate();
    }
    
    
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
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
     * Método para probar que la creación de una iteración se haga correctamente
     * @throws BusinessLogicException si se violo alguna regla de negocio para crear la iteración
     */
    @Test
    public void createHardware() throws BusinessLogicException{
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        newEntity.setProject(dataProject.get(0));
        HardwareEntity result = hardwareLogic.createHardware(dataProject.get(0).getId(),newEntity);
        Assert.assertNotNull(result);
        
        HardwareEntity entity = em.find(HardwareEntity.class,result.getId());
        Assert.assertEquals(result.getCores(),entity.getCores());
        Assert.assertEquals(result.getCpu(),entity.getCpu());
        Assert.assertEquals(result.getIp(),entity.getIp());
        Assert.assertEquals(result.getPlataforma(),entity.getPlataforma());
    }
    
    
    
    @Test
    public void getHardwareTest(){
        HardwareEntity entity = data.get(0);
        HardwareEntity resultEntity = hardwareLogic.getHardware(dataProject.get(0).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getCores(),entity.getCores());
        Assert.assertEquals(resultEntity.getCpu(),entity.getCpu());
        Assert.assertEquals(resultEntity.getIp(),entity.getIp());
        Assert.assertEquals(resultEntity.getPlataforma(),entity.getPlataforma());
    }
    
   /**
     * Prueba para actualizar un Iteracion.
     */
    @Test
    public void updateHardwareTest() throws BusinessLogicException {
        HardwareEntity entity = data.get(0);
        HardwareEntity pojoEntity = factory.manufacturePojo(HardwareEntity.class);

        pojoEntity.setId(entity.getId());

        hardwareLogic.updateHardware(dataProject.get(0).getId(), pojoEntity);

        HardwareEntity resp = em.find(HardwareEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getCores(),resp.getCores());
        Assert.assertEquals(pojoEntity.getCpu(),resp.getCpu());
        Assert.assertEquals(pojoEntity.getIp(),resp.getIp());
        Assert.assertEquals(pojoEntity.getPlataforma(),resp.getPlataforma());
    } 
    
    /**
     * Prueba para eliminar una iteración
     *
     * @throws BusinessLogicException
     */
    @Test
    public void deleteHardwareTest() throws BusinessLogicException {
        HardwareEntity entity = data.get(0);
        hardwareLogic.deleteHardware(dataProject.get(0).getId(), entity.getId());
        HardwareEntity deleted = em.find(HardwareEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para eliminarle un review a un book del cual no pertenece.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteHardwareConProjectNoAsociadoTest() throws BusinessLogicException {
        HardwareEntity entity = data.get(0);
        hardwareLogic.deleteHardware(dataProject.get(1).getId(), entity.getId());
    }

    
    @Test(expected = BusinessLogicException.class)
    public void createHardwareIdNullTest() throws BusinessLogicException{
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        newEntity.setIp(null);
        HardwareEntity result = hardwareLogic.createHardware(dataProject.get(1).getId(),newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createHardwareCoresZeroTest() throws BusinessLogicException{
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        newEntity.setCores(0);
        HardwareEntity result = hardwareLogic.createHardware(dataProject.get(1).getId(),newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createHardwareRamZeroTest() throws BusinessLogicException{
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        newEntity.setRam(0);
        HardwareEntity result = hardwareLogic.createHardware(dataProject.get(1).getId(),newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createHardwareCpuNullTest() throws BusinessLogicException{
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        newEntity.setCpu(null);
        HardwareEntity result = hardwareLogic.createHardware(dataProject.get(1).getId(),newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createHardwarePlataformaNullTest() throws BusinessLogicException{
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        newEntity.setPlataforma(null);
        HardwareEntity result = hardwareLogic.createHardware(dataProject.get(1).getId(),newEntity);
    }
    
 


}
