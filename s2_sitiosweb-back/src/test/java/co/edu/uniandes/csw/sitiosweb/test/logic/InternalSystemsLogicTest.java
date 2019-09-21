/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.InternalSystemsLogic;
import co.edu.uniandes.csw.sitiosweb.entities.InternalSystemsEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.InternalSystemsPersistence;
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
 *
 * @author Andres Martinez Silva 
 */
@RunWith(Arquillian.class)
public class InternalSystemsLogicTest {
       private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private InternalSystemsLogic internalSystemsLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<InternalSystemsEntity> data = new ArrayList<>();


    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(InternalSystemsEntity.class.getPackage())
                .addPackage(InternalSystemsLogic.class.getPackage())
                .addPackage(InternalSystemsPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

 
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void clearData() {
        em.createQuery("delete from InternalSystemsEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            InternalSystemsEntity entity = factory.manufacturePojo(InternalSystemsEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un InternalSystems.
     */
    @Test
    public void createInternalSystemsTest() throws BusinessLogicException  {
        InternalSystemsEntity newEntity = factory.manufacturePojo(InternalSystemsEntity.class);
        InternalSystemsEntity result = internalSystemsLogic.createInternalSystems(newEntity);
        Assert.assertNotNull(result);
        InternalSystemsEntity entity = em.find(InternalSystemsEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getType(), entity.getType());
    }

    /**
     * Prueba para consultar la lista de InternalSystemss.
     */
    @Test
    public void getInternalSystemssTest() {
        List<InternalSystemsEntity> list = internalSystemsLogic.getInternalSystems();
        Assert.assertEquals(data.size(), list.size());
        for (InternalSystemsEntity entity : list) {
            boolean found = false;
            for (InternalSystemsEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un InternalSystems.
     */
    @Test
    public void getInternalSystemsTest() {
        InternalSystemsEntity entity = data.get(0);
        InternalSystemsEntity resultEntity = internalSystemsLogic.getInternalSystems(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getType(), resultEntity.getType());
    }

    /**
     * Prueba para actualizar un InternalSystems.
     */
    @Test
    public void updateInternalSystemsTest() {
        InternalSystemsEntity entity = data.get(0);
        InternalSystemsEntity pojoEntity = factory.manufacturePojo(InternalSystemsEntity.class);

        pojoEntity.setId(entity.getId());

        internalSystemsLogic.updateInternalSystems(pojoEntity.getId(), pojoEntity);

        InternalSystemsEntity resp = em.find(InternalSystemsEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getType(), resp.getType());
    }

    /**
     * Prueba para eliminar un InternalSystems
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteInternalSystemsTest() throws BusinessLogicException {
        InternalSystemsEntity entity = data.get(0);
        internalSystemsLogic.deleteInternalSystems(entity.getId());
        InternalSystemsEntity deleted = em.find(InternalSystemsEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createInternalSystemsNullTypeTest() throws BusinessLogicException
    {
        InternalSystemsEntity newEntity = factory.manufacturePojo(InternalSystemsEntity.class);
        newEntity.setType(null);
        InternalSystemsEntity result = internalSystemsLogic.createInternalSystems(newEntity);
    }
}
