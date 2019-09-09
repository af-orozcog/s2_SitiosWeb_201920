/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.IterationLogic;
import co.edu.uniandes.csw.sitiosweb.entities.IterationEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.IterationPersistence;
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
 * @author Estudiante Andres Felipe Orozco Gonzalez
 */
@RunWith(Arquillian.class)
public class IterationLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private IterationLogic iterationLogic;
    
    @PersistenceContext
    protected EntityManager em;
    
    @Deployment
     public static JavaArchive createDeploymet(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(IterationEntity.class.getPackage())
                .addPackage(IterationPersistence.class.getPackage())
                .addPackage(IterationLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
    @Test
    public void createIteration() throws BusinessLogicException{
        IterationEntity newEntity = factory.manufacturePojo(IterationEntity.class);
        IterationEntity result = iterationLogic.createIteration(newEntity);
        //Assert.assertNotNull(result);
        
        IterationEntity entity = em.find(IterationEntity.class,result.getId());
        Assert.assertEquals(entity.getObjetive(),result.getObjetive());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createIterationObjetiveNull() throws BusinessLogicException{
        IterationEntity newEntity = factory.manufacturePojo(IterationEntity.class);
        newEntity.setObjetive(null);
        IterationEntity result = iterationLogic.createIteration(newEntity);
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createIterationValidationDateNull() throws BusinessLogicException{
        IterationEntity newEntity = factory.manufacturePojo(IterationEntity.class);
        newEntity.setValidationDate(null);
        IterationEntity result = iterationLogic.createIteration(newEntity);
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createIterationBeginDateNull() throws BusinessLogicException{
        IterationEntity newEntity = factory.manufacturePojo(IterationEntity.class);
        newEntity.setBeginDate(null);
        IterationEntity result = iterationLogic.createIteration(newEntity);
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createIterationEndDateNull() throws BusinessLogicException{
        IterationEntity newEntity = factory.manufacturePojo(IterationEntity.class);
        newEntity.setEndDate(null);
        IterationEntity result = iterationLogic.createIteration(newEntity);
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createIterationChangesNull() throws BusinessLogicException{
        IterationEntity newEntity = factory.manufacturePojo(IterationEntity.class);
        newEntity.setChanges(null);
        IterationEntity result = iterationLogic.createIteration(newEntity);
    }
}
