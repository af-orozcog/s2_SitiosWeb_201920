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
import javax.inject.Inject;
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

}
