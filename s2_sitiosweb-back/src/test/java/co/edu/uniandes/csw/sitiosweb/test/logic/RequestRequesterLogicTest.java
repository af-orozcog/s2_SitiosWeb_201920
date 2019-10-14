/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.RequestLogic;
import co.edu.uniandes.csw.sitiosweb.ejb.RequestRequesterLogic;
import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.RequestPersistence;
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
 * @author Daniel del Castillo A.
 */
@RunWith(Arquillian.class)
public class RequestRequesterLogicTest 
{
    /**
     * @return The Java archive for Payara to execute.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RequesterEntity.class.getPackage())
                .addPackage(RequestLogic.class.getPackage())
                .addPackage(RequestPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    // Test attributes
    
    /**
     * The test's random data generator.
     */
    private PodamFactory factory = new PodamFactoryImpl();
    
    /**
     * The request's logic class.
     */
    @Inject
    private RequestLogic requestLogic;
    
    /**
     * The request-requester's logic class.
     */
    @Inject
    private RequestRequesterLogic requestRequesterLogic;
    
    /**
     * The test's entity manager.
     */
    @PersistenceContext
    private EntityManager em;
    
    /**
     * The test's user transaction.
     */
    @Inject
    private UserTransaction utx;
    
    /**
     * The test's requester data.
     */
    private List<RequesterEntity> requesterData = new ArrayList<>();
    
    /**
     * The test's request data.
     */
    private List<RequestEntity> requestData = new ArrayList<>();
    
    // Methods
    
    /**
     * The test's configuration before execution.
     */
    @Before
    public void configTest()
    {
        try
        {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            { utx.rollback(); }
            catch(Exception e1)
            { e1.printStackTrace(); }
        }
    }
    
    /**
     * Clears the test's data.
     */
    private void clearData()
    {
        em.createQuery("delete from RequestEntity");
        em.createQuery("delete from RequesterEntity");
    }
    
    /**
     * Creates randomly generated requests and requesters.
     */
    private void insertData()
    {
        for(int i = 0; i < 3; ++i)
        {
            RequestEntity entity = factory.manufacturePojo(RequestEntity.class);
            em.persist(entity);
            requestData.add(entity);
        }
        for(int i = 0; i < 3; ++i)
        {
            RequesterEntity entity = factory.manufacturePojo(RequesterEntity.class);
            em.persist(entity);
            requesterData.add(entity);
            if(i == 0)
                requestData.get(i).setRequester(entity);
        }
    }
    
    /**
     * Tests the replacement of a request's requester.
     */
    @Test
    public void replaceRequesterTest()
    {
        RequestEntity request = requestData.get(0);
        requestRequesterLogic.replaceRequester(request.getId(), requesterData.get(1).getId());
        request = requestLogic.getRequest(request.getId());
        Assert.assertEquals(request.getRequester(), requesterData.get(1));
    }
    
    /**
     * Tests the removal of a project from a request.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void removeRequestTest() throws BusinessLogicException
    {
        RequestEntity result = requestLogic.getRequest(requestData.get(0).getId());
        requestRequesterLogic.removeRequest(requestData.get(0).getId());
        result = requestLogic.getRequest(requestData.get(0).getId());
        // The request should still be in the database:
        Assert.assertNotNull(result);
    }
    
    /**
     * Tests that the method fails when the request is invalid (not persisted).
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void removeRequestInvalidRequestTest() throws BusinessLogicException
    {
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        Assert.assertTrue(request.getRequester() == null);
        requestRequesterLogic.removeRequest(request.getId());
    }
    
    /**
     * Tests that the method fails when the request has a requester that is 
     * invalid (not persisted).
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void removeRequestInvalidRequesterTest() throws BusinessLogicException
    {
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        RequesterEntity requester = factory.manufacturePojo(RequesterEntity.class);
        request.setRequester(requester);
        Assert.assertNotNull(request.getRequester());
        requestLogic.updateRequest(requestData.get(2).getId(), request);
        requestRequesterLogic.removeRequest(request.getId());
    }
}
