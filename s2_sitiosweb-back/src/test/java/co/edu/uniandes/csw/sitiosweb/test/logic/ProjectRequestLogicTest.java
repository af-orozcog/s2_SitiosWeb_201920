/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.ProjectLogic;
import co.edu.uniandes.csw.sitiosweb.ejb.ProjectRequestLogic;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.ProjectPersistence;
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
public class ProjectRequestLogicTest 
{
    /**
     * @return The Java archive for Payara to execute.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProjectEntity.class.getPackage())
                .addPackage(ProjectLogic.class.getPackage())
                .addPackage(ProjectPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    // Test attributes
    
    /**
     * The test's random data generator.
     */
    private PodamFactory factory = new PodamFactoryImpl();
    
    /**
     * The project's logic class.
     */
    @Inject
    private ProjectLogic projectLogic;
    
    /**
     * The project-requests logic class.
     */
    @Inject
    private ProjectRequestLogic projectRequestLogic;
    
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
     * The test's project data.
     */
    private List<ProjectEntity> projectData = new ArrayList<>();
    
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
        em.createQuery("delete from ProjectEntity");
    }
    
    /**
     * Creates randomly generated requests and projects.
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
            ProjectEntity entity = factory.manufacturePojo(ProjectEntity.class);
            em.persist(entity);
            projectData.add(entity);
            if(i == 0)
                requestData.get(i).setProject(entity);
        }
    }
    
    /**
     * Tests the addition of a request to a given project.
     */
    @Test
    public void addRequestTest()
    {
        RequestEntity request = requestData.get(0);
        ProjectEntity project = projectData.get(1);
        RequestEntity result = projectRequestLogic.addRequest(project.getId(), request.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(request.getId(), result.getId());
    }
    
    /**
     * Tests the retrieval of a project's requests.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void getRequestsTest() throws BusinessLogicException
    {
        ProjectEntity project = projectData.get(0);
        List<RequestEntity> requests = projectRequestLogic.getRequests(project.getId());
        Assert.assertEquals(requests.size(), 1);
    }
    
    /**
     * Tests the retrieval of a project's specific request.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void getRequestTest() throws BusinessLogicException
    {
        ProjectEntity project = projectData.get(0);
        RequestEntity request = requestData.get(0);
        RequestEntity result = projectRequestLogic.getRequest(project.getId(), request.getId());
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
     * project is invalid (not persisted).
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getRequestsInvalidProjectTest() throws BusinessLogicException
    {
        ProjectEntity project = factory.manufacturePojo(ProjectEntity.class);
        // The project hasn't been added, therefore, it should launch the exception.
        List<RequestEntity> requests = projectRequestLogic.getRequests(project.getId());
    }
    
    /**
     * Tests that the method fails when retrieving a request that isn't
     * asociated to a specific valid (persisted) project.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getRequestInvalidProjectTest() throws BusinessLogicException
    {
        ProjectEntity project = projectData.get(1);
        RequestEntity request = requestData.get(1);
        RequestEntity result = projectRequestLogic.getRequest(project.getId(), request.getId());
    }
    
    /**
     * Tests that the method fails when retrieving a request that is invalid (not persisted).
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getRequestInvalidRequestTest() throws BusinessLogicException
    {
        ProjectEntity project = projectData.get(0);
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        RequestEntity result = projectRequestLogic.getRequest(project.getId(), request.getId());
    }
    
    /**
     * Tests that a project's list of requests is updated.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void replaceRequestsTest() throws BusinessLogicException
    {
        ProjectEntity project = projectData.get(0);
        List<RequestEntity> list = requestData.subList(1, 3);
        projectRequestLogic.replaceRequests(project.getId(), list);
        ProjectEntity result = projectLogic.getProject(project.getId());
        Assert.assertFalse(result.getRequests().contains(requestData.get(0)));
        Assert.assertTrue(result.getRequests().contains(requestData.get(1)));
        Assert.assertTrue(result.getRequests().contains(requestData.get(2)));
    }
    
    /**
     * Tests that the method fails when replacing the request's list of
     * a project that is invalid (not persisted).
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void replaceRequestsInvalidProjectTest() throws BusinessLogicException
    {
        ProjectEntity project = factory.manufacturePojo(ProjectEntity.class);
        List<RequestEntity> list = requestData.subList(1, 3);
        projectRequestLogic.replaceRequests(project.getId(), list);
    }
}
