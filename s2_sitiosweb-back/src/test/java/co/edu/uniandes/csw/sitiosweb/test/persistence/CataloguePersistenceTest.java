/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.CatalogueEntity;
import co.edu.uniandes.csw.sitiosweb.persistence.CataloguePersistence;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import co.edu.uniandes.csw.sitiosweb.persistence.CataloguePersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@RunWith(Arquillian.class)
public class CataloguePersistenceTest {
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(CatalogueEntity.class)
                .addClass(CataloguePersistence.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Inject
    CataloguePersistence cp;

    @PersistenceContext
    protected EntityManager em;
    
    @Test
    public void createTest(){
        PodamFactory factory = new PodamFactoryImpl();
        CatalogueEntity catalogue = factory.manufacturePojo(CatalogueEntity.class);
        CatalogueEntity result = cp.create(catalogue);
        Assert.assertNotNull(result);
        
        CatalogueEntity entity = em.find(CatalogueEntity.class, result.getId());
        Assert.assertEquals(catalogue.getProjectNum(), entity.getProjectNum());
        Assert.assertEquals(catalogue.getRequestNum(), entity.getRequestNum());
        
        
    }
    
    
}
