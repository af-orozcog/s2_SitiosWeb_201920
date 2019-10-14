/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.RequesterLogic;
import co.edu.uniandes.csw.sitiosweb.ejb.RequesterUnitLogic;
import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import co.edu.uniandes.csw.sitiosweb.entities.UnitEntity;
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
public class RequesterUnitLogicTest 
{
    /**
     * @return The Java archive for Payara to execute.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UnitEntity.class.getPackage())
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
    
    /**
     * The requester-unit's logic class.
     */
    @Inject
    private RequesterUnitLogic requesterUnitLogic;
    
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
     * Tests the replacement of a requester's unit.
     */
    @Test
    public void replaceUnitTest()
    {
        RequesterEntity requester = requesterData.get(0);
        requesterUnitLogic.replaceUnit(requester.getId(), unitData.get(1).getId());
        requester = requesterLogic.getRequester(requester.getId());
        Assert.assertEquals(requester.getUnit(), unitData.get(1));
    }
    
    /**
     * Tests the removal of a unit from a requester.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void removeRequesterTest() throws BusinessLogicException
    {
        RequesterEntity result = requesterLogic.getRequester(requesterData.get(0).getId());
        requesterUnitLogic.removeRequester(requesterData.get(0).getId());
        result = requesterLogic.getRequester(requesterData.get(0).getId());
        // The request should be in the database:
        Assert.assertNotNull(result);
    }
    
    /**
     * Tests that the method fails when the requester is invalid (not persisted).
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void removeRequesterInvalidRequesterTest() throws BusinessLogicException
    {
        RequesterEntity requester = factory.manufacturePojo(RequesterEntity.class);
        Assert.assertTrue(requester.getUnit() == null);
        requesterUnitLogic.removeRequester(requester.getId());
    }
    
    /**
     * Tests that the method fails when the requester has a unit that is 
     * invalid (not persisted).
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void removeRequesterInvalidUnitTest() throws BusinessLogicException
    {
        RequesterEntity requester = factory.manufacturePojo(RequesterEntity.class);
        UnitEntity unit = factory.manufacturePojo(UnitEntity.class);
        requester.setUnit(unit);
        Assert.assertNotNull(requester.getUnit());
        requesterLogic.updateRequester(requesterData.get(2).getId(), requester);
        requesterUnitLogic.removeRequester(requester.getId());
    }
}
