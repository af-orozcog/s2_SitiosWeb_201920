/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
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
 * @author Daniel del Castillo
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
                .addClass(RequestEntity.class)
                .addClass(RequestPersistence.class)
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
}
