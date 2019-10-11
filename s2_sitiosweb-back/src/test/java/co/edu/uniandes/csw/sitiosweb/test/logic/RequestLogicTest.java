/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.RequestLogic;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.RequestPersistence;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
public class RequestLogicTest 
{
    /**
     * @return The Java archive for Payara to execute.
     */
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
    
    // Test attributes
    
    /**
     * A date set for 2099 for testing purposes.
     */
    private static Date date;
    
    /**
     * A date set for 1099 for testing purposes.
     */
    private static Date beforeDate;
    
    // Attributes
    
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
     * The test's entity manager.
     */
    @PersistenceContext
    EntityManager em;
    
    /**
     * The test's user transaction.
     */
    @Inject
    private UserTransaction utx;
    
    /**
     * The test's data.
     */
    private List<RequestEntity> data = new ArrayList<>();
    
    /**
     * The test's project data.
     */
    private List<ProjectEntity> projectData = new ArrayList<>();

    /**
     * The test's requester data.
     */
    private List<RequesterEntity> requesterData = new ArrayList<>();    
    
    // Methods
    
    /**
     * The test's configuration before execution.
     */
    @Before
    public void configTest()
    {
        beforeDate = new GregorianCalendar(1099, Calendar.DECEMBER, 15).getTime();
        date = new GregorianCalendar(9999, Calendar.DECEMBER, 15).getTime();
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
     * Sets the entity's date values to valid ones.
     * @param newEntity The entity whose date values will be valid.
     */
    private void setValidData(RequestEntity newEntity)
    {
        newEntity.setBeginDate(date);
        newEntity.setDueDate(date);
        newEntity.setEndDate(date);
    }
    
    /**
     * Clears the test's data.
     */
    private void clearData()
    { 
        em.createQuery("delete from RequestEntity").executeUpdate();
        em.createQuery("delete from ProjectEntity").executeUpdate();
        em.createQuery("delete from RequesterEntity").executeUpdate();
    }
    
    /**
     * Creates randomly generated requests.
     */
    private void insertData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i < 4; ++i)
        {
            RequestEntity entity = factory.manufacturePojo(RequestEntity.class);
            ProjectEntity projectEntity = factory.manufacturePojo(ProjectEntity.class);
            RequesterEntity requesterEntity = factory.manufacturePojo(RequesterEntity.class);
            // Adds the project and requester data to the request:
            entity.setProject(projectEntity);
            entity.setRequester(requesterEntity);
            setValidData(entity);
            em.persist(entity);
            em.persist(projectEntity);
            em.persist(requesterEntity);
            data.add(entity);
        }
        // The first request will not have requester and project.
        data.get(0).setRequester(null);
        data.get(0).setProject(null);
        // The second request will not have a requester.
        data.get(1).setRequester(null);
        // The third request will not have a project.
        data.get(2).setProject(null);
        // The fourth request will have both requester and project.
    }
    
    /**
     * Tests the request's creation.
     * @throws BusinessLogicException Logic exception associated with business rules. 
     */
    @Test
    public void createRequestTest() throws BusinessLogicException
    {
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        setValidData(newEntity);
        RequestEntity result = requestLogic.createRequest(newEntity);
        Assert.assertNotNull(result);
        RequestEntity entity = em.find(RequestEntity.class, result.getId());
        Assert.assertEquals(entity.getDescription(), result.getDescription());
        Assert.assertEquals(entity.getBeginDate(), result.getBeginDate());
        Assert.assertEquals(entity.getDueDate(), result.getDueDate());
        Assert.assertEquals(entity.getEndDate(), result.getEndDate());
        Assert.assertEquals(entity.getPurpose(), result.getPurpose());
        Assert.assertEquals(entity.getBudget(), result.getBudget());
        Assert.assertEquals(entity.getName(), result.getName());
        Assert.assertEquals(entity.getUnit(), result.getUnit());
    }
    
    /**
     * Tests the request's name null creation.
     * @throws BusinessLogicException Logic exception associated with business rules.
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequestNullNameTest() throws BusinessLogicException
    {
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        setValidData(newEntity);
        newEntity.setName(null);
        RequestEntity result = requestLogic.createRequest(newEntity);
    }
    
    /**
     * Tests the request's purpose null creation.
     * @throws BusinessLogicException Logic exception associated with business rules.
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequestNullPurposeTest() throws BusinessLogicException
    {
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        setValidData(newEntity);
        newEntity.setPurpose(null);
        RequestEntity result = requestLogic.createRequest(newEntity);
    }
    
    /**
     * Tests the request's description null creation.
     * @throws BusinessLogicException Logic exception associated with business rules.
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequestNullDescriptionTest() throws BusinessLogicException
    {
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        setValidData(newEntity);
        newEntity.setDescription(null);
        RequestEntity result = requestLogic.createRequest(newEntity);
    }
    
    /**
     * Tests the request's unit null creation.
     * @throws BusinessLogicException Logic exception associated with business rules.
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequestNullUnitTest() throws BusinessLogicException
    {
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        setValidData(newEntity);
        newEntity.setUnit(null);
        RequestEntity result = requestLogic.createRequest(newEntity);
    }
    
    /**
     * Tests the request's budget null creation.
     * @throws BusinessLogicException Logic exception associated with business rules.
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequestNullBudgetTest() throws BusinessLogicException
    {
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        setValidData(newEntity);
        newEntity.setBudget(null);
        RequestEntity result = requestLogic.createRequest(newEntity);
    }
    
    /**
     * Tests the request's begin date null creation.
     * @throws BusinessLogicException Logic exception associated with business rules.
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequestNullBeginDateTest() throws BusinessLogicException
    {
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        setValidData(newEntity);
        newEntity.setBeginDate(null);
        RequestEntity result = requestLogic.createRequest(newEntity);
    }
    
    /**
     * Tests the request's due date null creation.
     * @throws BusinessLogicException Logic exception associated with business rules.
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequestNullDueDateTest() throws BusinessLogicException
    {
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        setValidData(newEntity);
        newEntity.setDueDate(null);
        RequestEntity result = requestLogic.createRequest(newEntity);
    }
    
    /**
     * Tests the request's end date null creation.
     * @throws BusinessLogicException Logic exception associated with business rules.
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequestNullEndDateTest() throws BusinessLogicException
    {
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        setValidData(newEntity);
        newEntity.setEndDate(null);
        RequestEntity result = requestLogic.createRequest(newEntity);
    }
    
    /**
     * Tests the request's status null creation.
     * @throws BusinessLogicException Logic exception associated with business rules.
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequestNullStatusTest() throws BusinessLogicException
    {
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        setValidData(newEntity);
        newEntity.setStatus(null);
        RequestEntity result = requestLogic.createRequest(newEntity);
    }
    
    /**
     * Tests the request's web category null creation.
     * @throws BusinessLogicException Logic exception associated with business rules.
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequestNullWebCategoryTest() throws BusinessLogicException
    {
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        setValidData(newEntity);
        newEntity.setWebCategory(null);
        RequestEntity result = requestLogic.createRequest(newEntity);
    }
    
    /**
     * Tests the request's web request type null creation.
     * @throws BusinessLogicException Logic exception associated with business rules.
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequestNullRequestTypeTest() throws BusinessLogicException
    {
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        setValidData(newEntity);
        newEntity.setRequestType(null);
        RequestEntity result = requestLogic.createRequest(newEntity);
    }
    
    /**
     * Tests the request's invalid budget creation.
     * @throws BusinessLogicException Logic exception associated with business rules.
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequestInvalidBudgetTest() throws BusinessLogicException
    {
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        setValidData(newEntity);
        newEntity.setBudget(-10);
        RequestEntity result = requestLogic.createRequest(newEntity);
    }
    
    /**
     * Tests the request's invalid begin date creation.
     * @throws BusinessLogicException Logic exception associated with business rules.
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequestInvalidBeginDateTest() throws BusinessLogicException
    {
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        setValidData(newEntity);
        newEntity.setBeginDate(beforeDate);
        RequestEntity result = requestLogic.createRequest(newEntity);
    }
    
    /**
     * Tests the request's invalid due date creation.
     * @throws BusinessLogicException Logic exception associated with business rules.
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequestInvalidDueDateTest() throws BusinessLogicException
    {
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        setValidData(newEntity);
        newEntity.setDueDate(beforeDate);
        RequestEntity result = requestLogic.createRequest(newEntity);
    }
    
    /**
     * Tests the request's invalid end date creation.
     * @throws BusinessLogicException Logic exception associated with business rules.
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequestInvalidEndDateTest() throws BusinessLogicException
    {
        RequestEntity newEntity = factory.manufacturePojo(RequestEntity.class);
        setValidData(newEntity);
        newEntity.setEndDate(beforeDate);
        RequestEntity result = requestLogic.createRequest(newEntity);
    }
    
    // CRUD methods
    
    /**
     * Test for the consult of all requests.
     */
    @Test
    public void getRequestsTest()
    {
        List<RequestEntity> list = requestLogic.getRequests();
        Assert.assertEquals(data.size(), list.size());
        for(RequestEntity entity : list)
        {
            boolean found = false;
            for(RequestEntity storedEntity : data)
            {
                if(entity.getId().equals(storedEntity.getId()))
                    found = true;
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Test for the consult of a request.
     */
    @Test
    public void getRequestTest()
    {
        RequestEntity entity = data.get(0);
        RequestEntity result = requestLogic.getRequest(entity.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(entity.getDescription(), result.getDescription());
        Assert.assertEquals(entity.getBeginDate(), result.getBeginDate());
        Assert.assertEquals(entity.getDueDate(), result.getDueDate());
        Assert.assertEquals(entity.getEndDate(), result.getEndDate());
        Assert.assertEquals(entity.getPurpose(), result.getPurpose());
        Assert.assertEquals(entity.getBudget(), result.getBudget());
        Assert.assertEquals(entity.getName(), result.getName());
        Assert.assertEquals(entity.getUnit(), result.getUnit());
    }
    
    /**
     * Test for the update of a request.
     */
    @Test
    public void updateRequestTest()
    {
        RequestEntity entity = data.get(0);
        RequestEntity pojoEntity = factory.manufacturePojo(RequestEntity.class);
        setValidData(pojoEntity);
        pojoEntity.setId(entity.getId());
        requestLogic.updateRequest(pojoEntity.getId(), pojoEntity);
        RequestEntity result = em.find(RequestEntity.class, pojoEntity.getId());
        Assert.assertEquals(pojoEntity.getDescription(), result.getDescription());
        Assert.assertEquals(pojoEntity.getBeginDate(), result.getBeginDate());
        Assert.assertEquals(pojoEntity.getDueDate(), result.getDueDate());
        Assert.assertEquals(pojoEntity.getEndDate(), result.getEndDate());
        Assert.assertEquals(pojoEntity.getPurpose(), result.getPurpose());
        Assert.assertEquals(pojoEntity.getBudget(), result.getBudget());
        Assert.assertEquals(pojoEntity.getName(), result.getName());
        Assert.assertEquals(pojoEntity.getUnit(), result.getUnit());
    }
    
    /**
     * Test for the deletion of a request (data(0). Specifics in the insertData() method).
     * @throws BusinessLogicException Logic exception associated with business rules.
     */
    @Test
    public void deteleRequestTest1() throws BusinessLogicException
    {
        RequestEntity entity = data.get(0);
        requestLogic.deleteRequest(entity.getId());
        RequestEntity deleted = em.find(RequestEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Test for the deletion of a request (data(1). Specifics in the insertData() method).
     * @throws BusinessLogicException Logic exception associated with business rules.
     * */
    @Test
    public void deleteRequestTest2() throws BusinessLogicException
    {
        RequestEntity entity = data.get(1);
        requestLogic.deleteRequest(entity.getId());
        RequestEntity deleted = em.find(RequestEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Test for the deletion of a request (data(2). Specifics in the insertData() method).
     * @throws BusinessLogicException Logic exception associated with business rules.
     * */
    @Test(expected = BusinessLogicException.class)
    public void deleteRequestTest3() throws BusinessLogicException
    {
        RequestEntity entity = data.get(2);
        requestLogic.deleteRequest(entity.getId());
        RequestEntity deleted = em.find(RequestEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
