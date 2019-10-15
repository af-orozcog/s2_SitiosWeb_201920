/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.RequesterLogic;
import co.edu.uniandes.csw.sitiosweb.ejb.RequesterRequestLogic;
import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.RequesterPersistence;
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
public class RequesterRequestLogicTest 
{
    /**
     * @return The Java archive for Payara to execute.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RequesterEntity.class.getPackage())
                .addPackage(RequesterLogic.class.getPackage())
                .addPackage(RequesterPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    // Test attributes
    
    /**
     * The test's random data generator.
     */
    private PodamFactory factory = new PodamFactoryImpl();
    
    /**
     * The requester's logic class.
     */
    @Inject
    private RequesterLogic requesterLogic;
    
    @Inject
    private RequesterRequestLogic requesterRequestLogic;
    
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
     * Tests the addition of a request to a given requester.
     */
    @Test
    public void addRequestTest()
    {
        RequestEntity request = requestData.get(0);
        RequesterEntity requester = requesterData.get(1);
        RequestEntity result = requesterRequestLogic.addRequest(requester.getId(), request.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(request.getId(), result.getId());
    }
    
    /**
     * Tests the retrieval of a requester's requests.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void getRequests() throws BusinessLogicException
    {
        RequesterEntity requester = requesterData.get(0);
        List<RequestEntity> requests = requesterRequestLogic.getRequests(requester.getId());
        Assert.assertEquals(requests.size(), 1);
    }
    
    /**
     * Tests the retrieval of a requester's specific request.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void getRequestTest() throws BusinessLogicException
    {
        RequesterEntity requester = requesterData.get(0);
        RequestEntity request = requestData.get(0);
        RequestEntity result = requesterRequestLogic.getRequest(requester.getId(), request.getId());
        Assert.assertEquals(request.getDescription(), result.getDescription());
        Assert.assertEquals(request.getRequestType(), result.getRequestType());
        Assert.assertEquals(request.getWebCategory(), result.getWebCategory());
        Assert.assertEquals(request.getBeginDate(), result.getBeginDate());
        Assert.assertEquals(request.getRequester(), result.getRequester());
        Assert.assertEquals(request.getProject(), result.getProject());
        Assert.assertEquals(request.getDueDate(), result.getDueDate());
        Assert.assertEquals(request.getEndDate(), result.getEndDate());
        Assert.assertEquals(request.getPurpose(), result.getPurpose());
        Assert.assertEquals(request.getBudget(), result.getBudget());
        Assert.assertEquals(request.getStatus(), result.getStatus());
        Assert.assertEquals(request.getName(), result.getName());
        Assert.assertEquals(request.getUnit(), result.getUnit());
        Assert.assertEquals(request.getId(), result.getId());
    }
    
    /**
     * Tests that the method fails when retrieving a list of requests whose
     * requester is invalid (not persisted).
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getRequestsInvalidRequesterTest() throws BusinessLogicException
    {
        RequesterEntity requester = factory.manufacturePojo(RequesterEntity.class);
        // The requester hasn't been added, therefore, it should launch the exception.
        List<RequestEntity> requests = requesterRequestLogic.getRequests(requester.getId());
    }
    
    /**
     * Tests that the method fails when retrieving a request that isn't
     * asociated to a specific valid (persisted) requester.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getRequestInvalidRequesterTest() throws BusinessLogicException
    {
        RequesterEntity requester = requesterData.get(1);
        RequestEntity request = requestData.get(1);
        RequestEntity result = requesterRequestLogic.getRequest(requester.getId(), request.getId());
    }
    
    /**
     * Tests that the method fails when retrieving a request that is invalid (not persisted).
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getRequestInvalidRequestTest() throws BusinessLogicException
    {
        RequesterEntity requester = requesterData.get(0);
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        RequestEntity result = requesterRequestLogic.getRequest(requester.getId(), request.getId());
    }
    
    /**
     * Tests that a requester's list of requests is updated.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void replaceRequestsTest() throws BusinessLogicException
    {
        RequesterEntity requester = requesterData.get(0);
        List<RequestEntity> list = requestData.subList(1, 3);
        requesterRequestLogic.replaceRequests(requester.getId(), list);
        RequesterEntity result = requesterLogic.getRequester(requester.getId());
        Assert.assertFalse(result.getRequests().contains(requestData.get(0)));
        Assert.assertTrue(result.getRequests().contains(requestData.get(1)));
        Assert.assertTrue(result.getRequests().contains(requestData.get(2)));
    }
    
    /**
     * Tests that the method fails when replacing the request's list of
     * a requester that is invalid (not persisted).
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void replaceRequestsInvalidProjectTest() throws BusinessLogicException
    {
        RequesterEntity requester = factory.manufacturePojo(RequesterEntity.class);
        List<RequestEntity> list = requestData.subList(1, 3);
        requesterRequestLogic.replaceRequests(requester.getId(), list);
    }
}
