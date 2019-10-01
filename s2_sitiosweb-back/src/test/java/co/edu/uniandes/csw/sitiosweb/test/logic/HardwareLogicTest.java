/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.HardwareLogic;
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
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private HardwareLogic hardwareLogic;

    @PersistenceContext(unitName = "sitioswebPU")
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<HardwareEntity> data = new ArrayList<HardwareEntity>();

    private List<ProjectEntity> dataBook = new ArrayList<ProjectEntity>();

    private List<DeveloperEntity> dataBookD = new ArrayList<DeveloperEntity>();


    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(HardwareEntity.class.getPackage())
                .addPackage(HardwareLogic.class.getPackage())
                .addPackage(HardwarePersistence.class.getPackage())
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
        em.createQuery("delete from HardwareEntity").executeUpdate();
        em.createQuery("delete from ProjectEntity").executeUpdate();
        em.createQuery("delete from DeveloperEntity").executeUpdate();

    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        for (int i = 0; i < 4; i++) {
            DeveloperEntity entity = factory.manufacturePojo(DeveloperEntity.class);
            em.persist(entity);
            dataBookD.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            HardwareEntity entity = factory.manufacturePojo(HardwareEntity.class);
            data.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            ProjectEntity entity = factory.manufacturePojo(ProjectEntity.class);
            HardwareEntity en = data.get(i);
            entity.setHardware(en);
            entity.setDevelopers(dataBookD);
            entity.setLeader(dataBookD.get(3));
            en.setProject(entity);
            em.persist(en);
            em.persist(entity);
            dataBook.add(entity);
        }
     
        
    }
    
    @Test
    public void createHardwareTest() throws BusinessLogicException{
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        HardwareEntity result = hardwareLogic.createHardware(dataBook.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createHardwareIdNullTest() throws BusinessLogicException{
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        newEntity.setIp(null);
        HardwareEntity result = hardwareLogic.createHardware(dataBook.get(1).getId(),newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createHardwareCoresZeroTest() throws BusinessLogicException{
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        newEntity.setCores(0);
        HardwareEntity result = hardwareLogic.createHardware(dataBook.get(1).getId(),newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createHardwareRamZeroTest() throws BusinessLogicException{
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        newEntity.setRam(0);
        HardwareEntity result = hardwareLogic.createHardware(dataBook.get(1).getId(),newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createHardwareCpuNullTest() throws BusinessLogicException{
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        newEntity.setCpu(null);
        HardwareEntity result = hardwareLogic.createHardware(dataBook.get(1).getId(), newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createHardwarePlataformaNullTest() throws BusinessLogicException{
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        newEntity.setPlataforma(null);
        HardwareEntity result = hardwareLogic.createHardware(dataBook.get(1).getId(), newEntity);
    }
    
 
    /**
     * Prueba para consultar la lista de Reviews.
     *
     */
    @Test
    public void getHardwaresTest() {

        HardwareEntity en = data.get(1);
        HardwareEntity result = hardwareLogic.getHardwares(dataBook.get(1).getId());
     
        Assert.assertNotNull(result);
        Assert.assertEquals(en.getIp(),result.getIp());
        Assert.assertEquals(en.getCores(),result.getCores());
        Assert.assertEquals(en.getRam(),result.getRam());
        Assert.assertEquals(en.getCpu(),result.getCpu());
        Assert.assertEquals(en.getPlataforma(),result.getPlataforma());

    }

    /**
     * Prueba para consultar un Review.
     */
    @Test
    public void getHardwareTest() {
        HardwareEntity entity = data.get(0);
        HardwareEntity resultEntity = hardwareLogic.getHardware(dataBook.get(0).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getIp(),entity.getIp());
        Assert.assertEquals(resultEntity.getCores(),entity.getCores());
        Assert.assertEquals(resultEntity.getRam(),entity.getRam());
        Assert.assertEquals(resultEntity.getCpu(),entity.getCpu());
        Assert.assertEquals(resultEntity.getPlataforma(),entity.getPlataforma());
    }

    /**
     * Prueba para actualizar un Review.
     */
    @Test
    public void updateHardwareTest() throws BusinessLogicException {
        HardwareEntity entity = data.get(0);
        HardwareEntity pojoEntity = factory.manufacturePojo(HardwareEntity.class);

        pojoEntity.setId(entity.getId());

        hardwareLogic.updateHardware(dataBook.get(0).getId(), pojoEntity);

        HardwareEntity resp = em.find(HardwareEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getIp(),resp.getIp());
        Assert.assertEquals(pojoEntity.getCores(),resp.getCores());
        Assert.assertEquals(pojoEntity.getRam(),resp.getRam());
        Assert.assertEquals(pojoEntity.getCpu(),resp.getCpu());
        Assert.assertEquals(pojoEntity.getPlataforma(),resp.getPlataforma());
    }

    /**
     * Prueba para eliminar un Review.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void deleteHardwareTest() throws BusinessLogicException {
        HardwareEntity entity = data.get(0);
        hardwareLogic.deleteHardware(dataBook.get(0).getId(), entity.getId());
        HardwareEntity deleted = em.find(HardwareEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }


}
