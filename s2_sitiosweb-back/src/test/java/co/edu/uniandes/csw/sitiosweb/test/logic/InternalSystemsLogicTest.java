/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.InternalSystemsLogic;
import co.edu.uniandes.csw.sitiosweb.entities.InternalSystemsEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.InternalSystemsPersistence;
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
 * @author Andres Martinez Silva 
 */
@RunWith(Arquillian.class)
public class InternalSystemsLogicTest {
    
    @Deployment
    public static JavaArchive createDployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(InternalSystemsEntity.class.getPackage())
                .addPackage(InternalSystemsLogic.class.getPackage())
                .addPackage(InternalSystemsPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml" );
    }
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private InternalSystemsLogic internalSystemsLogic;
    
    @Test
    public void createProvider() throws BusinessLogicException{
        InternalSystemsEntity newEntity = factory.manufacturePojo(InternalSystemsEntity.class);
        InternalSystemsEntity result = internalSystemsLogic.createInternalSystems(newEntity);
        Assert.assertNotNull(result);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createProviderNombreNull() throws BusinessLogicException{
        InternalSystemsEntity newEntity = factory.manufacturePojo(InternalSystemsEntity.class);
        newEntity.setType(null);
        InternalSystemsEntity result = internalSystemsLogic.createInternalSystems(newEntity);
    }
    
    
}
