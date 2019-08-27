/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.UnitEntity;
import co.edu.uniandes.csw.sitiosweb.persistence.UnitPersistence;
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
public class UnitPersistenceTest 
{
    /**
     * @return The Java archive for Payara to execute.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(UnitEntity.class)
                .addClass(UnitPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    // Attributes
     
    /**
     * The unit's persistence class.
     */
    @Inject
    private UnitPersistence up;
    
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
    private List<UnitEntity> data = new ArrayList<UnitEntity>();
    
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
    { em.createQuery("delete from UnitEntity").executeUpdate(); }
    
    /**
     * Creates randomly generated units.
     */
    private void insertData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i < 3; ++i)
        {
            UnitEntity entity = factory.manufacturePojo(UnitEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Tests the unit's creation.
     */
    @Test
    public void createTest()
    {
        // Falta crear request
        PodamFactory factory = new PodamFactoryImpl();
        UnitEntity request = factory.manufacturePojo(UnitEntity.class);
        UnitEntity result = up.create(request);
        Assert.assertNotNull(result);
        
        UnitEntity entity = em.find(UnitEntity.class, result.getId());
        Assert.assertEquals(request.getName(), entity.getName());
    }
    
    /**
     * Tests the retrieval of multiple units from the database.
     */
    @Test
    public void getRequestsTest()
    {
        List<UnitEntity> list = up.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (UnitEntity ent : list) 
        {
            boolean found = false;
            for (UnitEntity entity : data) 
            {
                if (ent.getId().equals(entity.getId()))
                    found = true;
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Tests the retrieval of a specific unit from the database.
     */
    @Test
    public void getRequestTest()
    {
        UnitEntity entity = data.get(0);
        UnitEntity newEntity = up.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
    }
    
    /**
     * Tests the update of units in the database.
     */
    @Test
    public void updateRequestTest()
    {
        UnitEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        UnitEntity newEntity = factory.manufacturePojo(UnitEntity.class);
        newEntity.setId(entity.getId());
        up.update(newEntity);
        UnitEntity response = em.find(UnitEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getName(), response.getName());
    }
    
    /**
     * Tests the deletion of a unit from the database.
     */
    @Test
    public void deleteRequestTest()
    {
        UnitEntity entity = data.get(0);
        up.delete(entity.getId());
        UnitEntity deleted = em.find(UnitEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
