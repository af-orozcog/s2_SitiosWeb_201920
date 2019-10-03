/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import co.edu.uniandes.csw.sitiosweb.entities.UnitEntity;
import co.edu.uniandes.csw.sitiosweb.entities.UserEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import co.edu.uniandes.csw.sitiosweb.persistence.RequesterPersistence;
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
public class RequesterPersistenceTest {

    @Inject
    RequesterPersistence up;

    @PersistenceContext
    protected EntityManager em;

    @Inject
    UserTransaction utx;

    private List<RequesterEntity> data = new ArrayList<RequesterEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UserEntity.class.getPackage())
                .addPackage(RequesterEntity.class.getPackage())
                .addPackage(RequestEntity.class.getPackage())
                .addPackage(UnitEntity.class.getPackage())
                .addPackage(RequesterPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

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
        em.createQuery("delete from RequesterEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            RequesterEntity entity = factory.manufacturePojo(RequesterEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Requester.
     */
    @Test
    public void createTest() {
        PodamFactory factory = new PodamFactoryImpl();
        RequesterEntity requester = factory.manufacturePojo(RequesterEntity.class);
        RequesterEntity result = up.create(requester);
        Assert.assertNotNull(result);

        RequesterEntity entity = em.find(RequesterEntity.class, result.getId());
        Assert.assertEquals(requester.getLogin(), entity.getLogin());
        Assert.assertEquals(requester.getEmail(), entity.getEmail());
        Assert.assertEquals(requester.getPhone(), entity.getPhone());

    }

    /**
     * Prueba para consultar la lista de Requesters.
     */
    @Test
    public void getRequestersTest() {
        List<RequesterEntity> list = up.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (RequesterEntity ent : list) {
            boolean found = false;
            for (RequesterEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Requester.
     */
    @Test
    public void getRequesterTest() {
        RequesterEntity entity = data.get(0);
        RequesterEntity newEntity = up.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getLogin(), newEntity.getLogin());
        Assert.assertEquals(entity.getEmail(), newEntity.getEmail());
        Assert.assertEquals(entity.getPhone(), newEntity.getPhone());

    }

    /**
     * Prueba para actualizar un Requester.
     */
    @Test
    public void deleteRequesterTest() {
        RequesterEntity entity = data.get(0);
        up.delete(entity.getId());
        RequesterEntity deleted = em.find(RequesterEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminar un Requester.
     */
    @Test
    public void updateRequesterTest() {
        RequesterEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        RequesterEntity newEntity = factory.manufacturePojo(RequesterEntity.class);

        newEntity.setId(entity.getId());

        up.update(newEntity);

        RequesterEntity resp = em.find(RequesterEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getLogin(), resp.getLogin());
        Assert.assertEquals(newEntity.getEmail(), resp.getEmail());
        Assert.assertEquals(newEntity.getLogin(), resp.getLogin());

    }
}
