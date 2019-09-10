/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.HardwareLogic;
import co.edu.uniandes.csw.sitiosweb.entities.HardwareEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
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
public class HardwareLogicTest {
    
    @Deployment
    public static JavaArchive createDployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(HardwareEntity.class.getPackage())
                .addPackage(HardwareLogic.class.getPackage())
                .addPackage(HardwarePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml" );
    }

    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private HardwareLogic hardwareLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<HardwareEntity> data = new ArrayList<>();

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
    }

    @Test
    public void createHardwareTest() throws BusinessLogicException{
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        HardwareEntity result = hardwareLogic.createHardware(newEntity);
        Assert.assertNotNull(result);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createHardwareIdNullTest() throws BusinessLogicException{
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        newEntity.setIp(null);
        HardwareEntity result = hardwareLogic.createHardware(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createHardwareCoresZeroTest() throws BusinessLogicException{
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        newEntity.setCores(0);
        HardwareEntity result = hardwareLogic.createHardware(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createHardwareRamZeroTest() throws BusinessLogicException{
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        newEntity.setRam(0);
        HardwareEntity result = hardwareLogic.createHardware(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createHardwareCpuNullTest() throws BusinessLogicException{
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        newEntity.setCpu(null);
        HardwareEntity result = hardwareLogic.createHardware(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createHardwarePlataformaNullTest() throws BusinessLogicException{
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);
        newEntity.setPlataforma(null);
        HardwareEntity result = hardwareLogic.createHardware(newEntity);
    }
    
 
    @Test
    public void getHardwaresTest() {
        List<HardwareEntity> list = hardwareLogic.getHardwares();
        Assert.assertEquals(data.size(), list.size());
        for (HardwareEntity entity : list) {
            boolean found = false;
            for (HardwareEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }


    @Test
    public void getHardwareTest() {
        HardwareEntity entity = data.get(0);
        HardwareEntity resultEntity = hardwareLogic.getHardware(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getCpu(), resultEntity.getCpu());
        Assert.assertEquals(entity.getRam(), resultEntity.getRam());
        Assert.assertEquals(entity.getPlataforma(), resultEntity.getPlataforma());
        Assert.assertEquals(entity.getCores(), resultEntity.getCores());

                
    }

  
//    @Test
//    public void updateHardwareTest() {
//        HardwareEntity entity = data.get(0);
//        HardwareEntity pojoEntity = factory.manufacturePojo(HardwareEntity.class);
//
//        pojoEntity.setId(entity.getId());
//
//        hardwareLogic.updateHardware(pojoEntity.getId(), pojoEntity);
//
//        HardwareEntity resp = em.find(HardwareEntity.class, entity.getId());
//
//        Assert.assertEquals(pojoEntity.getId(), resp.getId());
//        Assert.assertEquals(entity.getId(), resp.getId());
//        Assert.assertEquals(entity.getCpu(), resp.getCpu());
//        Assert.assertEquals(entity.getRam(), resp.getRam());
//        Assert.assertEquals(entity.getPlataforma(), resp.getPlataforma());
//        Assert.assertEquals(entity.getCores(), resp.getCores());
//    }

}
