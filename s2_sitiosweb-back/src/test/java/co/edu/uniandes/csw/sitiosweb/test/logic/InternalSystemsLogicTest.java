/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.InternalSystemsLogic;
import co.edu.uniandes.csw.sitiosweb.entities.InternalSystemsEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.InternalSystemsPersistence;
import co.edu.uniandes.csw.sitiosweb.persistence.RequestPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private static final Logger LOGGER = Logger.getLogger(InternalSystemsLogicTest.class.getName());

    @Inject
    private InternalSystemsLogic internalSystemsLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<InternalSystemsEntity> data = new ArrayList<>();
    
    private List<ProjectEntity> dataP = new ArrayList<>();



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
        
        ProjectEntity project1 = factory.manufacturePojo(ProjectEntity.class);
        ProjectEntity project2 = factory.manufacturePojo(ProjectEntity.class);

        InternalSystemsEntity internal1 = factory.manufacturePojo(InternalSystemsEntity.class);
        InternalSystemsEntity internal2 = factory.manufacturePojo(InternalSystemsEntity.class);
        InternalSystemsEntity internal3 = factory.manufacturePojo(InternalSystemsEntity.class);

        List<InternalSystemsEntity> lista1 = new ArrayList<>();
        List<InternalSystemsEntity> lista2 = new ArrayList<>();
        
        internal1.setProject(project1);
        internal2.setProject(project1);
        internal3.setProject(project2);
        
        em.persist(internal1);
        em.persist(internal2);
        em.persist(internal3);

        data.add(internal1);
        data.add(internal2);
        data.add(internal3);

        lista1.add(internal1);
        lista1.add(internal2);
        lista2.add(internal3);

        project1.setInternalSystems(lista1);
        project2.setInternalSystems(lista2);

        em.persist(project1);
        em.persist(project2);
        
        dataP.add(project1);
        dataP.add(project2);
        
    }

    @Test
    public void createInternalSystemsTest() throws BusinessLogicException  {
        InternalSystemsEntity newEntity = factory.manufacturePojo(InternalSystemsEntity.class);
        InternalSystemsEntity result = internalSystemsLogic.createInternalSystems(newEntity);
        Assert.assertNotNull(result);
        InternalSystemsEntity entity = em.find(InternalSystemsEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getType(), entity.getType());
    }


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


    @Test
    public void getInternalSystemsTest() {
        InternalSystemsEntity entity = data.get(0);
        InternalSystemsEntity resultEntity = internalSystemsLogic.getInternalSystems(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getType(), resultEntity.getType());
    }
    
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
