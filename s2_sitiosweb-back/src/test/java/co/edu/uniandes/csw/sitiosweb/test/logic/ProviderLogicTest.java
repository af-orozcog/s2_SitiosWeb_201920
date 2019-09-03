/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.ProviderLogic;
import co.edu.uniandes.csw.sitiosweb.entities.ProviderEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.ProviderPersistence;
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
public class ProviderLogicTest {
    
    @Deployment
    public static JavaArchive createDployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProviderEntity.class.getPackage())
                .addPackage(ProviderLogic.class.getPackage())
                .addPackage(ProviderPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml" );
    }
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private ProviderLogic providerLogic;
    
    @Test
    public void createProvider() throws BusinessLogicException{
        ProviderEntity newEntity = factory.manufacturePojo(ProviderEntity.class);
        ProviderEntity result = providerLogic.createPovider(newEntity);
        Assert.assertNotNull(result);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createProviderNombreNull() throws BusinessLogicException{
        ProviderEntity newEntity = factory.manufacturePojo(ProviderEntity.class);
        newEntity.setName(null);
        ProviderEntity result = providerLogic.createPovider(newEntity);
    }
    
    
}

