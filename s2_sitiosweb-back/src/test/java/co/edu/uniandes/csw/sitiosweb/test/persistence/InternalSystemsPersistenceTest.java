/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.persistence;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import co.edu.uniandes.csw.sitiosweb.entities.InternalSystemsEntity;
import co.edu.uniandes.csw.sitiosweb.persistence.InternalSystemsPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


/**
 *
 * @author s.santosb
 */
@RunWith(Arquillian.class)
public class InternalSystemsPersistenceTest {
    
    @Deployment
    public static JavaArchive createDeploymet(){
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(InternalSystemsEntity.class)
                .addClass(InternalSystemsPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    @Inject
    InternalSystemsPersistence hw;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    UserTransaction utx;

    private List<InternalSystemsEntity> data = new ArrayList<InternalSystemsEntity>();


    /**
     * Configuración inicial de la prueba.
     */
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

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from InternalSystemsEntity").executeUpdate();
    }
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            InternalSystemsEntity entity = factory.manufacturePojo(InternalSystemsEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    @Test
    public void createTest(){
        PodamFactory factory = new PodamFactoryImpl();
        InternalSystemsEntity internal = factory.manufacturePojo(InternalSystemsEntity.class);
        InternalSystemsEntity result = hw.create(internal);
        Assert.assertNotNull(result);
        
        InternalSystemsEntity entity = em.find(InternalSystemsEntity.class,result.getId());
        Assert.assertEquals(internal.getType(),entity.getType());
        
    }
    

    @Test
    public void getHardwaresTest() {
        List<InternalSystemsEntity> list = hw.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (InternalSystemsEntity ent : list) {
            boolean found = false;
            for (InternalSystemsEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }


    @Test
    public void getHardwareTest() {
        InternalSystemsEntity entity = data.get(0);
        InternalSystemsEntity newEntity = hw.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getType(), newEntity.getType());
    }


    @Test
    public void deleteEditorialTest() {
        InternalSystemsEntity entity = data.get(0);
        hw.delete(entity.getId());
        InternalSystemsEntity deleted = em.find(InternalSystemsEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updateEditorialTest() {
        InternalSystemsEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        InternalSystemsEntity newEntity = factory.manufacturePojo(InternalSystemsEntity.class);

        newEntity.setId(entity.getId());

        hw.update(newEntity);

        InternalSystemsEntity resp = em.find(InternalSystemsEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getType(), resp.getType());
    }

}
