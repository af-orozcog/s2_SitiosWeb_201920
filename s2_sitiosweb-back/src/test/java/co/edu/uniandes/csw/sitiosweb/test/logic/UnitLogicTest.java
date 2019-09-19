/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.UnitLogic;
import co.edu.uniandes.csw.sitiosweb.entities.UnitEntity;
import co.edu.uniandes.csw.sitiosweb.persistence.UnitPersistence;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
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
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * @author Daniel del Castillo A.
 */
@RunWith(Arquillian.class)
public class UnitLogicTest 
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
    
    // Attributes
     
    /*
    The test's random data generator.
    */
    private PodamFactory factory = new PodamFactoryImpl();
    
    /**
     * The unit's logic class.
     */
    @Inject
    private UnitLogic unitLogic;
    
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
    private List<UnitEntity> data = new ArrayList<>();
    
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
     * @throws BusinessLogicException Logic exception associated with business rules. 
     */
    @Test
    public void createUnitTest() throws BusinessLogicException
    {
        UnitEntity newEntity = factory.manufacturePojo(UnitEntity.class);
        UnitEntity result = unitLogic.createUnit(newEntity);
        Assert.assertNotNull(result);
        UnitEntity entity = em.find(UnitEntity.class, result.getId());
        Assert.assertEquals(entity.getName(), result.getName());
    }
    
    /**
     * Tests the unit's name null creation.
     * @throws BusinessLogicException Logic exception associated with business rules.
     */
    @Test(expected = BusinessLogicException.class)
    public void createUnitNullNameTest() throws BusinessLogicException
    {
        UnitEntity newEntity = factory.manufacturePojo(UnitEntity.class);
        newEntity.setName(null);
        UnitEntity result = unitLogic.createUnit(newEntity);
    }
    
    /**
     * Test for the consult of all units.
     */
    @Test
    public void getUnitsTest()
    {
        List<UnitEntity> list = unitLogic.getUnits();
        Assert.assertEquals(data.size(), list.size());
        for(UnitEntity entity : list)
        {
            boolean found = false;
            for(UnitEntity storedEntity : data)
            {
                if(entity.getId().equals(storedEntity.getId()))
                    found = true;
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Test for the consult of an unit.
     */
    @Test
    public void getUnitTest()
    {
        UnitEntity entity = data.get(0);
        UnitEntity resultEntity = unitLogic.getUnit(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }
    
    /**
     * Test for the update of an unit.
     */
    @Test
    public void updateUnitTest()
    {
        UnitEntity entity = data.get(0);
        UnitEntity pojoEntity = factory.manufacturePojo(UnitEntity.class);
        pojoEntity.setId(entity.getId());
        unitLogic.updateUnit(pojoEntity.getId(), pojoEntity);
        UnitEntity result = em.find(UnitEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getName(), result.getName());
        Assert.assertEquals(pojoEntity.getId(), result.getId());
    }
     
    /**
     * Test for the deletion of an unit.
     * @throws BusinessLogicException Logic exception associated with business rules.
     */
    @Test
    public void deleteUnitTest() throws BusinessLogicException
    {
        UnitEntity entity = data.get(0);
        unitLogic.deleteUnit(entity.getId());
        UnitEntity deleted = em.find(UnitEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
