/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.RequestLogic;
import co.edu.uniandes.csw.sitiosweb.ejb.RequestProjectLogic;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
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
public class RequestProjectLogicTest 
{
    /**
     * @return The Java archive for Payara to execute.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProjectEntity.class.getPackage())
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
     * The request-project's logic class.
     */
    @Inject
    private RequestProjectLogic requestProjectLogic;
    
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
     * Tests the replacement of a request's project.
     */
    @Test
    public void replaceProjectTest()
    {
        RequestEntity request = requestData.get(0);
        requestProjectLogic.replaceProject(request.getId(), projectData.get(1).getId());
        request = requestLogic.getRequest(request.getId());
        Assert.assertEquals(request.getProject(), projectData.get(1));
    }
    
    /**
     * Tests the removal of a project from a request.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void removeRequestTest() throws BusinessLogicException
    {
        RequestEntity result = requestLogic.getRequest(requestData.get(0).getId());
        requestProjectLogic.removeRequest(requestData.get(0).getId());
        result = requestLogic.getRequest(requestData.get(0).getId());
        // The request shouldn't be in the database:
        Assert.assertNull(result);
    }
    
    /**
     * Tests that the method fails when the request is invalid (not persisted).
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void removeRequestInvalidRequestTest() throws BusinessLogicException
    {
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        Assert.assertTrue(request.getProject() == null);
        requestProjectLogic.removeRequest(request.getId());
    }
    
    /**
     * Tests that the method fails when the request has a project that is 
     * invalid (not persisted).
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void removeRequestInvalidProjectTest() throws BusinessLogicException
    {
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        ProjectEntity project = factory.manufacturePojo(ProjectEntity.class);
        request.setProject(project);
        Assert.assertNotNull(request.getProject());
        requestLogic.updateRequest(requestData.get(2).getId(), request);
        requestProjectLogic.removeRequest(request.getId());
    }
}
