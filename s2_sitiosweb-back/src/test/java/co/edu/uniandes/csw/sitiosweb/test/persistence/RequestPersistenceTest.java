/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity.RequestType;
import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity.Status;
import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity.WebCategory;
import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import co.edu.uniandes.csw.sitiosweb.persistence.RequestPersistence;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class RequestPersistenceTest 
{
    /**
     * @return The Java archive for Payara to execute.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RequestEntity.class.getPackage())
                .addPackage(ProjectEntity.class.getPackage())
                .addPackage(RequesterEntity.class.getPackage())
                .addPackage(RequestPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    // Attributes
     
    /**
     * The request's persistence class.
     */
    @Inject
    private RequestPersistence rp;
    
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
     * The test's data.
     */
    private List<RequestEntity> data = new ArrayList<RequestEntity>();
    
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
    { em.createQuery("delete from RequestEntity").executeUpdate(); }
    
    /**
     * Creates randomly generated requests.
     */
    private void insertData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i < 3; ++i)
        {
            RequestEntity entity = factory.manufacturePojo(RequestEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Tests the request's creation.
     */
    @Test
    public void createTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        RequestEntity result = rp.create(request);
        Assert.assertNotNull(result);
        
        RequestEntity entity = em.find(RequestEntity.class, result.getId());
        Assert.assertEquals(request.getName(), entity.getName());
        Assert.assertEquals(request.getPurpose(), entity.getPurpose());
        Assert.assertEquals(request.getDescription(), entity.getDescription());
        Assert.assertEquals(request.getUnit(), entity.getUnit());
        Assert.assertEquals(request.getBudget(), entity.getBudget());
        Assert.assertEquals(request.getBeginDate(), entity.getBeginDate());
        Assert.assertEquals(request.getDueDate(), entity.getDueDate());
        Assert.assertEquals(request.getEndDate(), entity.getEndDate());
        Assert.assertEquals(request.getStatus(), entity.getStatus());
        Assert.assertEquals(request.getRequestType(), entity.getRequestType());
        Assert.assertEquals(request.getWebCategory(), entity.getWebCategory());
    }
    
    /**
     * Tests that the persisted request doesn't change name after persisted.
     */
    @Test
    public void createRequestInvalidNameTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        RequestEntity result = rp.create(request);
        Assert.assertNotNull(result);
        String name = result.getName();
        // Attribute Name is modified:
        request.setName("Different name");
        RequestEntity entity = em.find(RequestEntity.class, result.getId());
        Assert.assertEquals(name, entity.getName());
    }
    
    /**
     * Tests that the persisted request doesn't change purpose after persisted.
     */
    @Test
    public void createRequestInvalidPurposeTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        RequestEntity result = rp.create(request);
        Assert.assertNotNull(result);
        String purpose = result.getPurpose();
        // Attribute Purpose is modified:
        request.setPurpose("Different purpose");
        RequestEntity entity = em.find(RequestEntity.class, result.getId());
        Assert.assertEquals(purpose, entity.getPurpose());
    }
    
    /**
     * Tests that the persisted request doesn't change the description after persisted.
     */
    @Test
    public void createRequestInvalidDescriptionTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        RequestEntity result = rp.create(request);
        Assert.assertNotNull(result);
        String description = result.getDescription();
        // Attribute Description is modified:
        request.setDescription("Different description");
        RequestEntity entity = em.find(RequestEntity.class, result.getId());
        Assert.assertEquals(description, entity.getDescription());
    }
    
    /**
     * Tests that the persisted request doesn't change unit after persisted.
     */
    @Test
    public void createRequestInvalidUnitTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        RequestEntity result = rp.create(request);
        Assert.assertNotNull(result);
        String unit = result.getUnit();
        // Attribute Unit is modified:
        request.setUnit("Different unit");
        RequestEntity entity = em.find(RequestEntity.class, result.getId());
        Assert.assertEquals(unit, entity.getUnit());
    }
    
    /**
     * Tests that the persisted request doesn't change budget after persisted.
     */
    @Test
    public void createRequestInvalidBudgetTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        RequestEntity result = rp.create(request);
        Assert.assertNotNull(result);
        Integer budget = result.getBudget();
        // Attribute Budget is modified:
        request.setBudget(request.getBudget()+1);
        RequestEntity entity = em.find(RequestEntity.class, result.getId());
        Assert.assertEquals(budget, entity.getBudget());
    }
    
    /**
     * Tests that the persisted request doesn't change the starting date after persisted.
     */
    @Test
    public void createRequestInvalidBeginDateTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        RequestEntity result = rp.create(request);
        Assert.assertNotNull(result);
        Date beginDate = result.getBeginDate();
        // Attribute BeginDate is modified to be 1 hour in the future:
        Calendar cal = Calendar.getInstance();
        cal.setTime(request.getBeginDate());
        cal.add(Calendar.HOUR_OF_DAY, 1);
        request.setBeginDate(cal.getTime());
        RequestEntity entity = em.find(RequestEntity.class, result.getId());
        Assert.assertEquals(beginDate, entity.getBeginDate());
    }

    /**
     * Tests that the persisted request doesn't change the due date after persisted.
     */
    @Test
    public void createRequestInvalidDueDateTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        RequestEntity result = rp.create(request);
        Assert.assertNotNull(result);
        Date dueDate = result.getDueDate();
        // Attribute DueDate is modified to be 1 hour in the future:
        Calendar cal = Calendar.getInstance();
        cal.setTime(request.getDueDate());
        cal.add(Calendar.HOUR_OF_DAY, 1);
        request.setDueDate(cal.getTime());
        RequestEntity entity = em.find(RequestEntity.class, result.getId());
        Assert.assertEquals(dueDate, entity.getDueDate());
    }
    
    /**
     * Tests that the persisted request doesn't change the end date after persisted.
     */
    @Test
    public void createRequestInvalidEndDateTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        RequestEntity result = rp.create(request);
        Assert.assertNotNull(result);
        Date endDate = result.getEndDate();
        // Attribute EndDate is modified to be 1 hour in the future:
        Calendar cal = Calendar.getInstance();
        cal.setTime(request.getEndDate());
        cal.add(Calendar.HOUR_OF_DAY, 1);
        request.setEndDate(cal.getTime());
        RequestEntity entity = em.find(RequestEntity.class, result.getId());
        Assert.assertEquals(endDate, entity.getEndDate());
    }
    
    /**
     * Tests that the persisted request doesn't change status after persisted.
     */
    @Test
    public void createRequestInvalidStatusTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        RequestEntity result = rp.create(request);
        Assert.assertNotNull(result);
        Status status = result.getStatus();
        // Attribute Status is modified:
        request.setStatus(getDifferentStatus(request.getStatus()));
        RequestEntity entity = em.find(RequestEntity.class, result.getId());
        Assert.assertEquals(status, entity.getStatus());
    }
    
    /**
     * Tests that the persisted request doesn't change type after persisted.
     */
    @Test
    public void createRequestInvalidRequestTypeTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        RequestEntity result = rp.create(request);
        Assert.assertNotNull(result);
        RequestType requestType = result.getRequestType();
        // Attribute RequestType is modified:
        request.setRequestType(getDifferentRequestType(request.getRequestType()));
        RequestEntity entity = em.find(RequestEntity.class, result.getId());
        Assert.assertEquals(requestType, entity.getRequestType());
    }
    
    /**
     * Tests that the persisted request doesn't change web category after persisted.
     */
    @Test
    public void createRequestInvalidWebCategoryTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        RequestEntity result = rp.create(request);
        Assert.assertNotNull(result);
        WebCategory webCategory = result.getWebCategory();
        // Attribute WebCategory is modified:
        request.setWebCategory(getDifferentWebCategory(request.getWebCategory()));
        RequestEntity entity = em.find(RequestEntity.class, result.getId());
        Assert.assertEquals(webCategory, entity.getWebCategory());
    }
    
    /**
     * Tests the retrieval of multiple requests from the database.
     */
    @Test
    public void getRequestsTest()
    {
        List<RequestEntity> list = rp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (RequestEntity ent : list) 
        {
            boolean found = false;
            for (RequestEntity entity : data) 
            {
                if (ent.getId().equals(entity.getId()))
                    found = true;
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Tests the retrieval of a specific request from the database.
     */
    @Test
    public void getRequestTest()
    {
        RequestEntity entity = data.get(0);
        RequestEntity newEntity = rp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
    }
    
    /**
     * Tests the update of requests in the database.
     */
    @Test
    public void updateRequestTest()
    {
        RequestEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        newEntity.setId(entity.getId());
        rp.update(newEntity);
        RequestEntity response = em.find(RequestEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getName(), response.getName());
    }
    
    /**
     * Tests the deletion of a request from the database.
     */
    @Test
    public void deleteRequestTest()
    {
        RequestEntity entity = data.get(0);
        rp.delete(entity.getId());
        RequestEntity deleted = em.find(RequestEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    // Auxiliar methods:
    
    /**
     * @param status A specific status. Status = {Accepted, Denied, Development, Pending, Production}.
     * @return A different Status than the one given.
     */
    public Status getDifferentStatus(Status status)
    {
        Status result = null;
        switch (status) 
        {
            case Accepted:
                result = Status.Denied;
                break;
            case Denied:
                result = Status.Development;
                break;
            case Development:
                result = Status.Pending;
                break;
            case Pending:
                result = Status.Production;
                break;
            case Production:
                result = Status.Accepted;
                break;
            default:
                break;
        }
        return result;
    }
    
    /**
     * @param requestType A specific request type. RequestType = {Change, Creation, Development, Elimination, Production}. 
     * @return A different request type than the one given.
     */
    public RequestType getDifferentRequestType(RequestType requestType)
    {
        RequestType result = null;
        switch(requestType)
        {
            case Change:
                result = RequestType.Creation;
                break;
            case Creation:
                result = RequestType.Development;
                break;
            case Development:
                result = RequestType.Elimination;
                break;
            case Elimination:
                result = RequestType.Production;
                break;
            case Production:
                result = RequestType.Change;
                break;
            default:
                break;
        }
        return result;
    }
    
    /**
     * @param webCategory A specific web category. WebCategory = {Descriptive, Event, Application}.
     * @return A different web category than the one given.
     */
    public WebCategory getDifferentWebCategory(WebCategory webCategory)
    {
        WebCategory result = null;
        switch(webCategory)
        {
            case Application:
                result = WebCategory.Descriptive;
                break;
            case Descriptive:
                result = WebCategory.Event;
                break;
            case Event:
                result = WebCategory.Application;
                break;
            default:
                break;
        }
        return result;
    }
}
