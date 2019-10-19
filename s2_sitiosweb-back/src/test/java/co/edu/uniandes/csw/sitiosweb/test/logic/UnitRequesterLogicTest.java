/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.UnitLogic;
import co.edu.uniandes.csw.sitiosweb.ejb.UnitRequesterLogic;
import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import co.edu.uniandes.csw.sitiosweb.entities.UnitEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
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
 * @author Daniel del Castillo A.
 */
@RunWith(Arquillian.class)
public class UnitRequesterLogicTest 
{
    /**
     * @return The Java archive for Payara to execute.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UnitEntity.class.getPackage())
                .addPackage(UnitLogic.class.getPackage())
                .addPackage(UnitPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    // Test attributes
    
    /**
     * The test's random data generator.
     */
    private PodamFactory factory = new PodamFactoryImpl();
    
    /**
     * The unit's logic class.
     */
    @Inject
    private UnitLogic unitLogic;
    
    /**
     * The unit-requester's logic class.
     */
    @Inject
    private UnitRequesterLogic unitRequesterLogic;
    
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
     * The test's unit data.
     */
    private List<UnitEntity> unitData = new ArrayList<>();
    
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
        em.createQuery("delete from RequesterEntity");
        em.createQuery("delete from UnitEntity");
    }
    
    /**
     * Creates randomly generated requesters and units.
     */
    private void insertData()
    {
        for(int i = 0; i < 3; ++i)
        {
            RequesterEntity entity = factory.manufacturePojo(RequesterEntity.class);
            em.persist(entity);
            requesterData.add(entity);
        }
        for(int i = 0; i < 3; ++i)
        {
            UnitEntity entity = factory.manufacturePojo(UnitEntity.class);
            em.persist(entity);
            unitData.add(entity);
            if(i == 0)
                requesterData.get(i).setUnit(entity);
        }
    }
    
    /**
     * Tests the addition of a requester to a given unit.
     */
    @Test
    public void addRequesterTest()
    {
        RequesterEntity requester = requesterData.get(0);
        UnitEntity unit = unitData.get(1);
        RequesterEntity result = unitRequesterLogic.addRequester(unit.getId(), requester.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(requester.getId(), result.getId());
    }
    
    /**
     * Tests the retrieval of a unit's requesters.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void getRequestersTest() throws BusinessLogicException
    {
        UnitEntity unit = unitData.get(0);
        List<RequesterEntity> requesters = unitRequesterLogic.getRequesters(unit.getId());
        Assert.assertEquals(requesters.size(), 1);
    }
    
    /**
     * Tests the retrieval of a unit's specific requester.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void getRequesterTest() throws BusinessLogicException
    {
        UnitEntity unit = unitData.get(0);
        RequesterEntity requester = requesterData.get(0);
        RequesterEntity result = unitRequesterLogic.getRequester(unit.getId(), requester.getId());
        Assert.assertEquals(requester.getRequests(), result.getRequests());
        Assert.assertEquals(requester.getEmail(), result.getEmail());
        Assert.assertEquals(requester.getLogin(), result.getLogin());
        Assert.assertEquals(requester.getPhone(), result.getPhone());
        Assert.assertEquals(requester.getUnit(), result.getUnit());
        Assert.assertEquals(requester.getId(), result.getId());
    }
    
    /**
     * Tests that the method fails when retrieving a list of requesters whose
     * unit is invalid (not persisted).
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getRequestersInvalidUnitTest() throws BusinessLogicException
    {
        UnitEntity unit = factory.manufacturePojo(UnitEntity.class);
        // The unit hasn't been added, therefore, it should launch the exception.
        List<RequesterEntity> requesters = unitRequesterLogic.getRequesters(unit.getId());
    }
    
    /**
     * Tests that the method fails when retrieving a requester that isn't
     * asociated to a specific valid (persisted) unit.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getRequesterInvalidUnitTest() throws BusinessLogicException
    {
        UnitEntity unit = unitData.get(1);
        RequesterEntity requester = requesterData.get(1);
        RequesterEntity result = unitRequesterLogic.getRequester(unit.getId(), requester.getId());
    }
    
    /**
     * Tests that the method fails when retrieving a requester that is invalid (not persisted).
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getRequesterInvalidRequesterTest() throws BusinessLogicException
    {
        UnitEntity unit = unitData.get(0);
        RequesterEntity requester = factory.manufacturePojo(RequesterEntity.class);
        RequesterEntity result = unitRequesterLogic.getRequester(unit.getId(), requester.getId());
    }
    
    /**
     * Tests that a unit's list of requesters is updated.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void replaceRequestersTest() throws BusinessLogicException
    {
        UnitEntity unit = unitData.get(0);
        List<RequesterEntity> list = requesterData.subList(1, 3);
        unitRequesterLogic.replaceRequesters(unit.getId(), list);
        UnitEntity result = unitLogic.getUnit(unit.getId());
        Assert.assertFalse(result.getRequesters().contains(requesterData.get(0)));
        Assert.assertTrue(result.getRequesters().contains(requesterData.get(1)));
        Assert.assertTrue(result.getRequesters().contains(requesterData.get(2)));
    }
    
    /**
     * Tests that the method fails when replacing the requester's list of
     * a unit that is invalid (not persisted).
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void replaceRequestersInvalidUnitTest() throws BusinessLogicException
    {
        UnitEntity unit = factory.manufacturePojo(UnitEntity.class);
        List<RequesterEntity> list = requesterData.subList(1, 3);
        unitRequesterLogic.replaceRequesters(unit.getId(), list);
    }
}
