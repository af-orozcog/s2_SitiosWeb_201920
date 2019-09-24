/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.RequesterLogic;
import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
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
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@RunWith(Arquillian.class)
public class RequesterLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private RequesterLogic userLogic;
    
    @PersistenceContext
    protected EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<RequesterEntity> data = new ArrayList<RequesterEntity>();

    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RequesterEntity.class.getPackage())
                .addPackage(RequesterLogic.class.getPackage())
                .addPackage(RequesterPersistence.class.getPackage())
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
          em.createQuery("delete from RequesterEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            RequesterEntity entity = factory.manufacturePojo(RequesterEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    @Test
    public void createRequester() throws BusinessLogicException{
        
        RequesterEntity newEntity = factory.manufacturePojo(RequesterEntity.class);
        RequesterEntity result = userLogic.createRequester(newEntity);
        Assert.assertNotNull(result);
        
        RequesterEntity entity = em.find(RequesterEntity.class, result.getId());
        Assert.assertEquals(entity.getId(), result.getId());
        Assert.assertEquals(entity.getLogin(), result.getLogin());
        Assert.assertEquals(entity.getEmail(), result.getEmail());
        Assert.assertEquals(entity.getPhone(), result.getPhone());
        
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createRequesterEmailNull() throws BusinessLogicException{
        RequesterEntity newEntity = factory.manufacturePojo(RequesterEntity.class);
        newEntity.setEmail(null);
        RequesterEntity result = userLogic.createRequester(newEntity);
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createRequesterLoginNull() throws BusinessLogicException{
        RequesterEntity newEntity = factory.manufacturePojo(RequesterEntity.class);
        newEntity.setLogin(null);
        RequesterEntity result = userLogic.createRequester(newEntity);
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createRequesterPhoneNull() throws BusinessLogicException{
        RequesterEntity newEntity = factory.manufacturePojo(RequesterEntity.class);
        newEntity.setPhone(null);
        RequesterEntity result = userLogic.createRequester(newEntity);
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createRequesterLoginExistente() throws BusinessLogicException{
        RequesterEntity newEntity = factory.manufacturePojo(RequesterEntity.class);
        newEntity.setLogin(data.get(0).getLogin());
        userLogic.createRequester(newEntity);
    }
    
     @Test
    public void getRequestersTest() {
        List<RequesterEntity> list = userLogic.getRequesters();
        Assert.assertEquals(data.size(), list.size());
        for (RequesterEntity entity : list) {
            boolean found = false;
            for (RequesterEntity storedEntity : data) {
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
    public void getRequesterTest() {
        RequesterEntity entity = data.get(0);
        RequesterEntity resultEntity = userLogic.getRequester(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getLogin(), resultEntity.getLogin());
        Assert.assertEquals(entity.getPhone(), resultEntity.getPhone());
        Assert.assertEquals(entity.getEmail(), resultEntity.getEmail());
    }

    @Test
    public void updateRequesterTest() throws BusinessLogicException {
        RequesterEntity entity = data.get(0);
        RequesterEntity pojoEntity = factory.manufacturePojo(RequesterEntity.class);
        pojoEntity.setId(entity.getId());
        userLogic.updateRequester(pojoEntity.getId(), pojoEntity);
        RequesterEntity resp = em.find(RequesterEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getLogin(), resp.getLogin());
        Assert.assertEquals(pojoEntity.getPhone(), resp.getPhone());
        Assert.assertEquals(pojoEntity.getEmail(), resp.getEmail());
    }

    @Test(expected = BusinessLogicException.class)
    public void updateRequesterConLoginInvalidoTest() throws BusinessLogicException {
        RequesterEntity entity = data.get(0);
        RequesterEntity pojoEntity = factory.manufacturePojo(RequesterEntity.class);
        pojoEntity.setLogin(null);
        pojoEntity.setId(entity.getId());
        userLogic.updateRequester(pojoEntity.getId(), pojoEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void updateRequesterConEmailInvalidoTest() throws BusinessLogicException {
        RequesterEntity entity = data.get(0);
        RequesterEntity pojoEntity = factory.manufacturePojo(RequesterEntity.class);
        pojoEntity.setEmail(null);
        pojoEntity.setId(entity.getId());
        userLogic.updateRequester(pojoEntity.getId(), pojoEntity);
    }
    
        @Test(expected = BusinessLogicException.class)
    public void updateRequesterConPhoneInvalidoTest() throws BusinessLogicException {
        RequesterEntity entity = data.get(0);
        RequesterEntity pojoEntity = factory.manufacturePojo(RequesterEntity.class);
        pojoEntity.setPhone(null);
        pojoEntity.setId(entity.getId());
        userLogic.updateRequester(pojoEntity.getId(), pojoEntity);
    }
    
    @Test
    public void deleteRequesterTest() throws BusinessLogicException {
        RequesterEntity entity = data.get(0);
        userLogic.deleteRequester(entity.getId());
        RequesterEntity deleted = em.find(RequesterEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

}
