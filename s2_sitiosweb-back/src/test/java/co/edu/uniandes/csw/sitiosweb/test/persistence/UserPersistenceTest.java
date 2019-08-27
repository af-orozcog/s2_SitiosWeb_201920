/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.UserEntity;
import co.edu.uniandes.csw.sitiosweb.persistence.UserPersistence;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import co.edu.uniandes.csw.sitiosweb.persistence.UserPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Before;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@RunWith(Arquillian.class)
public class UserPersistenceTest {
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(UserEntity.class)
                .addClass(UserPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Inject
    UserPersistence up;

    @PersistenceContext
    protected EntityManager em;
    
     @Inject
    UserTransaction utx;

    private List<UserEntity> data = new ArrayList<UserEntity>();


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
        em.createQuery("delete from UserEntity").executeUpdate();
    }
    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            UserEntity entity = factory.manufacturePojo(UserEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
    @Test
    public void createTest(){
        PodamFactory factory = new PodamFactoryImpl();
        UserEntity user = factory.manufacturePojo(UserEntity.class);
        UserEntity result = up.create(user);
        Assert.assertNotNull(result);
        
        UserEntity entity = em.find(UserEntity.class, result.getId());
        Assert.assertEquals(user.getLogin(), entity.getLogin());
        Assert.assertEquals(user.getEmail(), entity.getEmail());
        
        
    }
    
    @Test
    public void getUsersTest() {
        List<UserEntity> list = up.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (UserEntity ent : list) {
            boolean found = false;
            for (UserEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }


    @Test
    public void getUserTest() {
        UserEntity entity = data.get(0);
        UserEntity newEntity = up.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getLogin(), newEntity.getLogin());
    }


    @Test
    public void deleteEditorialTest() {
        UserEntity entity = data.get(0);
        up.delete(entity.getId());
        UserEntity deleted = em.find(UserEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updateEditorialTest() {
        UserEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        UserEntity newEntity = factory.manufacturePojo(UserEntity.class);

        newEntity.setId(entity.getId());

        up.update(newEntity);

        UserEntity resp = em.find(UserEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getLogin(), resp.getLogin());
    }
}
