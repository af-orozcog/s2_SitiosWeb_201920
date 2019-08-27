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
    
     @Inject
    UserTransaction utx;

    private List<CatalogueEntity> data = new ArrayList<CatalogueEntity>();


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
        em.createQuery("delete from CatalogueEntity").executeUpdate();
    }
    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            CatalogueEntity entity = factory.manufacturePojo(CatalogueEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
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
    
    @Test
    public void getCataloguesTest() {
        List<CatalogueEntity> list = cp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (CatalogueEntity ent : list) {
            boolean found = false;
            for (CatalogueEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }


    @Test
    public void getCatalogueTest() {
        CatalogueEntity entity = data.get(0);
        CatalogueEntity newEntity = cp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getProjectNum(), newEntity.getProjectNum());
    }


    @Test
    public void deleteEditorialTest() {
        CatalogueEntity entity = data.get(0);
        cp.delete(entity.getId());
        CatalogueEntity deleted = em.find(CatalogueEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updateEditorialTest() {
        CatalogueEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CatalogueEntity newEntity = factory.manufacturePojo(CatalogueEntity.class);

        newEntity.setId(entity.getId());

        cp.update(newEntity);

        CatalogueEntity resp = em.find(CatalogueEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getProjectNum(), resp.getProjectNum());
    }
}
