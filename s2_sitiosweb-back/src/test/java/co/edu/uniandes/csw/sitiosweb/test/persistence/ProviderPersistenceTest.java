/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.ProviderEntity;
import co.edu.uniandes.csw.sitiosweb.persistence.ProviderPersistence;
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
 * @author Andres Martinez Silva 
 */
@RunWith(Arquillian.class)
public class ProviderPersistenceTest {
        @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
              .addClass(ProviderEntity.class)
              .addClass(ProviderPersistence.class)
              .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
              .addAsManifestResource("META-INF/beans.xml", "beans.xml" );
    }

    @Inject
    ProviderPersistence pp;
    
    @PersistenceContext
    EntityManager em;

    @Test
    public void createTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ProviderEntity provider = factory.manufacturePojo(ProviderEntity.class);
        ProviderEntity result = pp.create(provider);
        Assert.assertNotNull(result);
        ProviderEntity entity = em.find(ProviderEntity.class, result.getId());
        Assert.assertEquals(provider.getName(), entity.getName());

    }

}
