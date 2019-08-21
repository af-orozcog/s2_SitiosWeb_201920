/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.IterationEntity;
import co.edu.uniandes.csw.sitiosweb.persistence.IterationPersistence;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import co.edu.uniandes.csw.sitiosweb.persistence.IterationPersistence;
import javax.inject.Inject;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Estudiante
 */
@RunWith(Arquillian.class)
public class IterationPersistenceTest {
   
    @Deployment
    public static JavaArchive createDeploymet(){
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(IterationEntity.class)
                .addClass(IterationPersistence.class)
                .addAsManifestResource("META-INF/persistance.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    @Inject
    IterationPersistence ep;
    
    
    @Test
    public void createTest(){
        PodamFactory factory = new PodamFactoryImpl();
        IterationEntity iteration = factory.manufacturePojo(IterationEntity.class);
        IterationEntity result = ep.create(iteration);
        Assert.assertNotNull(result);
    }
}
