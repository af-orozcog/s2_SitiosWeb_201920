/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.RequestLogic;
import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.RequestPersistence;
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
 * @author Daniel del Castillo A.
 */
@RunWith(Arquillian.class)
public class RequestLogicTest 
{
    // Configuration
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private RequestLogic requestLogic;
    
    @PersistenceContext
    EntityManager em;
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RequestEntity.class.getPackage())
                .addPackage(RequestLogic.class.getPackage())
                .addPackage(RequestPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Test
    public void createRequest() throws BusinessLogicException
    {
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        RequestEntity result = requestLogic.createRequest(newEntity);
        Assert.assertNotNull(result);
        
        RequestEntity entity = em.find(RequestEntity.class, result.getId());
        Assert.assertEquals(entity.getName(), result.getName());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createRequestNullNameTest() throws BusinessLogicException
    {
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        newEntity.setName(null);
        RequestEntity result = requestLogic.createRequest(newEntity);
    }
}
