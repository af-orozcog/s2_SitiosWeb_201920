/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import co.edu.uniandes.csw.sitiosweb.persistence.RequestPersistence;
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
 * @author Daniel del Castillo
 */
@RunWith(Arquillian.class)
public class RequestPersistenceTest 
{
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(RequestEntity.class)
                .addClass(RequestPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
     
    @Inject
    RequestPersistence rp;
    
    @PersistenceContext
    EntityManager em;
    
    @Test
    public void createTest()
    {
        // Falta crear request
        PodamFactory factory = new PodamFactoryImpl();
        RequestEntity request = factory.manufacturePojo(RequestEntity.class);
        RequestEntity result = rp.create(request);
        Assert.assertNotNull(result);
        
        RequestEntity entity = em.find(RequestEntity.class, result.getId());
        Assert.assertEquals(request.getName(), entity.getName());
        Assert.assertEquals(request.getPurpose(), entity.getPurpose());
        Assert.assertEquals(request.getDescription(), entity.getDescription());
        Assert.assertEquals(request.getUnit(), entity.getUnit());
        Assert.assertEquals(request.getBudget(), entity.getBudget());
        Assert.assertEquals(request.getBeginDate(), entity.getBeginDate());
        Assert.assertEquals(request.getDueDate(), entity.getDueDate());
        Assert.assertEquals(request.getEndDate(), entity.getEndDate());
    }
}
