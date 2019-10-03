/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.ProviderEntity;
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
 * @author Andres Martinez Silva 
 */
@RunWith(Arquillian.class)
public class ProviderPersistenceTest {
        @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
              .addPackage(ProviderEntity.class.getPackage())
              .addPackage(ProviderPersistence.class.getPackage())
              .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
              .addAsManifestResource("META-INF/beans.xml", "beans.xml" );
    }

    @Inject
    ProviderPersistence pp;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    UserTransaction utx;

    private List<ProviderEntity> data = new ArrayList<>();
    
    
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
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
    }

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ProviderEntity entity = factory.manufacturePojo(ProviderEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    @Test
    public void createTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ProviderEntity provider = factory.manufacturePojo(ProviderEntity.class);
        ProviderEntity result = pp.create(provider);
        Assert.assertNotNull(result);
        ProviderEntity entity = em.find(ProviderEntity.class, result.getId());
        Assert.assertEquals(provider.getName(), entity.getName());

    }
    
    @Test
    public void getAuthorsTest() {
        List<ProviderEntity> list = pp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ProviderEntity ent : list) {
            boolean found = false;
            for (ProviderEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    
    @Test
    public void getAuthorTest() {
        ProviderEntity entity = data.get(0);
        ProviderEntity newEntity = pp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
    }

    @Test
    public void updateAuthorTest() {
        ProviderEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ProviderEntity newEntity = factory.manufacturePojo(ProviderEntity.class);

        newEntity.setId(entity.getId());

        pp.update(newEntity);

        ProviderEntity resp = em.find(ProviderEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getName(), resp.getName());
    }
    
    @Test
    public void deleteAuthorTest() {
        ProviderEntity entity = data.get(0);
        pp.delete(entity.getId());
        ProviderEntity deleted = em.find(ProviderEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
