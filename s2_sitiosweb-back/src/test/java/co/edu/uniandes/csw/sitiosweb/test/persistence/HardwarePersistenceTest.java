/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.HardwareEntity;
import co.edu.uniandes.csw.sitiosweb.entities.IterationEntity;
import co.edu.uniandes.csw.sitiosweb.persistence.HardwarePersistence;
import co.edu.uniandes.csw.sitiosweb.persistence.IterationPersistence;
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
 * @author s.santosb
 */
@RunWith(Arquillian.class)
public class HardwarePersistenceTest {
    
    @Deployment
    public static JavaArchive createDeploymet(){
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(HardwareEntity.class)
                .addClass(HardwarePersistence.class)
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    @Inject
    HardwarePersistence hw;
    
    @PersistenceContext
    EntityManager em;
    
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
    
}
