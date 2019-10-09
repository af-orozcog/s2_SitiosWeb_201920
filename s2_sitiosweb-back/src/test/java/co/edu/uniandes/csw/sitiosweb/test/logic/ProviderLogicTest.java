/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.ProviderLogic;
import co.edu.uniandes.csw.sitiosweb.entities.ProviderEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.ProviderPersistence;
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
public class ProviderLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ProviderLogic providerLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ProviderEntity> data = new ArrayList<ProviderEntity>();


    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProviderEntity.class.getPackage())
                .addPackage(ProviderLogic.class.getPackage())
                .addPackage(ProviderPersistence.class.getPackage())
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
        em.createQuery("delete from ProviderEntity").executeUpdate();
        em.createQuery("delete from ProjectEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ProviderEntity entity = factory.manufacturePojo(ProviderEntity.class);
            em.persist(entity);
            data.add(entity);
        }
        ProjectEntity project = factory.manufacturePojo(ProjectEntity.class);
        project.setProvider(data.get(1));
        em.persist(project);
        data.get(0).setProjects(new ArrayList<>());
        data.get(1).setProjects(new ArrayList<>());
        data.get(2).setProjects(new ArrayList<>());
        data.get(1).getProjects().add(project);
    }

    @Test
    public void createProviderTest() throws BusinessLogicException  {
        ProviderEntity newEntity = factory.manufacturePojo(ProviderEntity.class);
        ProviderEntity result = providerLogic.createProvider(newEntity);
        Assert.assertNotNull(result);
        ProviderEntity entity = em.find(ProviderEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

     @Test(expected = BusinessLogicException.class)
    public void createProviderkTestConNombreInvalido() throws BusinessLogicException {
        ProviderEntity newEntity = factory.manufacturePojo(ProviderEntity.class);
        newEntity.setName("");
        providerLogic.createProvider(newEntity);
    }


    @Test(expected = BusinessLogicException.class)
    public void createProviderTestConNombreInvalido2() throws BusinessLogicException {
        ProviderEntity newEntity = factory.manufacturePojo(ProviderEntity.class);
        newEntity.setName(null);
        providerLogic.createProvider(newEntity);
    }
    
    
    @Test
    public void getProvidersTest() {
        List<ProviderEntity> list = providerLogic.getProviders();
        Assert.assertEquals(data.size(), list.size());
        for (ProviderEntity entity : list) {
            boolean found = false;
            for (ProviderEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getProviderTest() {
        ProviderEntity entity = data.get(0);
        ProviderEntity resultEntity = providerLogic.getProvider(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
    }

    @Test
    public void updateProviderTest() throws BusinessLogicException {
        ProviderEntity entity = data.get(0);
        ProviderEntity pojoEntity = factory.manufacturePojo(ProviderEntity.class);

        pojoEntity.setId(entity.getId());

        providerLogic.updateProvider(pojoEntity.getId(), pojoEntity);

        ProviderEntity resp = em.find(ProviderEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
    }
    
        @Test(expected = BusinessLogicException.class)
    public void updateProviderConNombreInvalidoTest() throws BusinessLogicException {
        ProviderEntity entity = data.get(0);
        ProviderEntity pojoEntity = factory.manufacturePojo(ProviderEntity.class);
        pojoEntity.setName("");
        pojoEntity.setId(entity.getId());
        providerLogic.updateProvider(pojoEntity.getId(), pojoEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void updateProviderConNombreInvalidoTest2() throws BusinessLogicException {
        ProviderEntity entity = data.get(0);
        ProviderEntity pojoEntity = factory.manufacturePojo(ProviderEntity.class);
        pojoEntity.setName(null);
        pojoEntity.setId(entity.getId());
        providerLogic.updateProvider(pojoEntity.getId(), pojoEntity);
    }


    
    
    @Test(expected = BusinessLogicException.class)
    public void deleteProviderWithProjectTest() throws BusinessLogicException {
        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD"+ data.size());
        ProviderEntity entity = data.get(1);
        providerLogic.deleteProvider(entity.getId());
    }
    
        @Test
    public void deleteProviderTest() throws BusinessLogicException {
        ProviderEntity entity = data.get(0);
        providerLogic.deleteProvider(entity.getId());
        ProviderEntity deleted = em.find(ProviderEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}

