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
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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
public class UserLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private UserLogic userLogic;
    
    @PersistenceContext
    protected EntityManager em;
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UserEntity.class.getPackage())
                .addPackage(UserLogic.class.getPackage())
                .addPackage(UserPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Test
    public void createUser() throws BusinessLogicException{
        
        UserEntity newEntity = factory.manufacturePojo(UserEntity.class);
        UserEntity result = userLogic.createUser(newEntity);
        Assert.assertNotNull(result);
        
        UserEntity entity = em.find(UserEntity.class, result.getId());
        Assert.assertEquals(entity.getEmail(), result.getEmail());
        
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

}
