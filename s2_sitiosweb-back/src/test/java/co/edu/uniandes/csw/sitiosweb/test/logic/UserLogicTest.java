/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.UserLogic;
import co.edu.uniandes.csw.sitiosweb.entities.UserEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.UserPersistence;
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
 * @author Nicolás Abondano nf.abondano 201812467
 */
@RunWith(Arquillian.class)
public class UserLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private UserLogic userLogic;
    
    @PersistenceContext
    protected EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<UserEntity> data = new ArrayList<UserEntity>();

    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UserEntity.class.getPackage())
                .addPackage(UserLogic.class.getPackage())
                .addPackage(UserPersistence.class.getPackage())
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
          em.createQuery("delete from UserEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            UserEntity entity = factory.manufacturePojo(UserEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    @Test
    public void createUser() throws BusinessLogicException{
        
        UserEntity newEntity = factory.manufacturePojo(UserEntity.class);
        UserEntity result = userLogic.createUser(newEntity);
        Assert.assertNotNull(result);
        
        UserEntity entity = em.find(UserEntity.class, result.getId());
        Assert.assertEquals(entity.getId(), result.getId());
        Assert.assertEquals(entity.getLogin(), result.getLogin());
        Assert.assertEquals(entity.getEmail(), result.getEmail());
        Assert.assertEquals(entity.getPhone(), result.getPhone());
        
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createUserEmailNull() throws BusinessLogicException{
        UserEntity newEntity = factory.manufacturePojo(UserEntity.class);
        newEntity.setEmail(null);
        UserEntity result = userLogic.createUser(newEntity);
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createUserLoginNull() throws BusinessLogicException{
        UserEntity newEntity = factory.manufacturePojo(UserEntity.class);
        newEntity.setLogin(null);
        UserEntity result = userLogic.createUser(newEntity);
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createUserPhoneNull() throws BusinessLogicException{
        UserEntity newEntity = factory.manufacturePojo(UserEntity.class);
        newEntity.setPhone(null);
        UserEntity result = userLogic.createUser(newEntity);
    }
    
     @Test
    public void getUsersTest() {
        List<UserEntity> list = userLogic.getUsers();
        Assert.assertEquals(data.size(), list.size());
        for (UserEntity entity : list) {
            boolean found = false;
            for (UserEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Book.
     */
    @Test
    public void getUserTest() {
        UserEntity entity = data.get(0);
        UserEntity resultEntity = userLogic.getUser(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getLogin(), resultEntity.getLogin());
        Assert.assertEquals(entity.getPhone(), resultEntity.getPhone());
        Assert.assertEquals(entity.getEmail(), resultEntity.getEmail());
    }

    @Test
    public void updateUserTest() throws BusinessLogicException {
        UserEntity entity = data.get(0);
        UserEntity pojoEntity = factory.manufacturePojo(UserEntity.class);
        pojoEntity.setId(entity.getId());
        userLogic.updateUser(pojoEntity.getId(), pojoEntity);
        UserEntity resp = em.find(UserEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getLogin(), resp.getLogin());
        Assert.assertEquals(pojoEntity.getPhone(), resp.getPhone());
        Assert.assertEquals(pojoEntity.getEmail(), resp.getEmail());
    }

    @Test(expected = BusinessLogicException.class)
    public void updateUserConLoginInvalidoTest() throws BusinessLogicException {
        UserEntity entity = data.get(0);
        UserEntity pojoEntity = factory.manufacturePojo(UserEntity.class);
        pojoEntity.setLogin(null);
        pojoEntity.setId(entity.getId());
        userLogic.updateUser(pojoEntity.getId(), pojoEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void updateUserConEmailInvalidoTest() throws BusinessLogicException {
        UserEntity entity = data.get(0);
        UserEntity pojoEntity = factory.manufacturePojo(UserEntity.class);
        pojoEntity.setEmail(null);
        pojoEntity.setId(entity.getId());
        userLogic.updateUser(pojoEntity.getId(), pojoEntity);
    }
    
        @Test(expected = BusinessLogicException.class)
    public void updateUserConPhoneInvalidoTest() throws BusinessLogicException {
        UserEntity entity = data.get(0);
        UserEntity pojoEntity = factory.manufacturePojo(UserEntity.class);
        pojoEntity.setPhone(null);
        pojoEntity.setId(entity.getId());
        userLogic.updateUser(pojoEntity.getId(), pojoEntity);
    }
    
    @Test
    public void deleteUserTest() throws BusinessLogicException {
        UserEntity entity = data.get(0);
        userLogic.deleteUser(entity.getId());
        UserEntity deleted = em.find(UserEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

}
