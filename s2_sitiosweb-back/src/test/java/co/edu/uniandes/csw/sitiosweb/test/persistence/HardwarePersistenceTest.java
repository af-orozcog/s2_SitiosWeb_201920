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
    
    @Deployment
    public static JavaArchive createDeploymet(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(HardwareEntity.class.getPackage())
                .addPackage(ProjectEntity.class.getPackage())
                .addClass(HardwarePersistence.class)
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    @Inject
    HardwarePersistence hw;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    UserTransaction utx;

    private List<HardwareEntity> data = new ArrayList<HardwareEntity>();


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
    }
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            HardwareEntity entity = factory.manufacturePojo(HardwareEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    @Test
    public void createTest(){
        PodamFactory factory = new PodamFactoryImpl();
        HardwareEntity hardware = factory.manufacturePojo(HardwareEntity.class);
        HardwareEntity result = hw.create(hardware);
        Assert.assertNotNull(result);
        
        HardwareEntity entity = em.find(HardwareEntity.class,result.getId());
        Assert.assertEquals(hardware.getIp(),entity.getIp());
        Assert.assertEquals(hardware.getCores(),entity.getCores());
        Assert.assertEquals(hardware.getRam(),entity.getRam());
        Assert.assertEquals(hardware.getCpu(),entity.getCpu());
        Assert.assertEquals(hardware.getPlataforma(),entity.getPlataforma());
        
    }
    

    @Test
    public void getHardwaresTest() {
        List<HardwareEntity> list = hw.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (HardwareEntity ent : list) {
            boolean found = false;
            for (HardwareEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }


    @Test
    public void getHardwareTest() {
        HardwareEntity entity = data.get(0);
        HardwareEntity newEntity = hw.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getCores(), newEntity.getCores());
    }


    @Test
    public void deleteEditorialTest() {
        HardwareEntity entity = data.get(0);
        hw.delete(entity.getId());
        HardwareEntity deleted = em.find(HardwareEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updateEditorialTest() {
        HardwareEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        HardwareEntity newEntity = factory.manufacturePojo(HardwareEntity.class);

        newEntity.setId(entity.getId());

        hw.update(newEntity);

        HardwareEntity resp = em.find(HardwareEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getCores(), resp.getCores());
    }
    
}
