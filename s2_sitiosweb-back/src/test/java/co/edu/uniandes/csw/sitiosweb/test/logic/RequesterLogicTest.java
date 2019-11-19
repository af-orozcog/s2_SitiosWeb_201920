/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.RequesterLogic;
import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import co.edu.uniandes.csw.sitiosweb.entities.UnitEntity;
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
    private RequesterLogic requesterLogic;

    @PersistenceContext
    protected EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<RequesterEntity> data = new ArrayList<RequesterEntity>();
    
    private List<UnitEntity> unitData = new ArrayList<UnitEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RequesterEntity.class.getPackage())
                .addPackage(RequesterLogic.class.getPackage())
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
        for (int i = 0; i < 3; i++) {
            UnitEntity unitEntity = factory.manufacturePojo(UnitEntity.class);
            em.persist(unitEntity);
            unitData.add(unitEntity);
        }
        for (int i = 0; i < 3; i++) {
            RequesterEntity entity = factory.manufacturePojo(RequesterEntity.class);
            entity.setUnit(unitData.get(i));
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Requester.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void createRequester() throws BusinessLogicException {

        RequesterEntity newEntity = factory.manufacturePojo(RequesterEntity.class);
        newEntity.setPhone("3206745567");
        newEntity.setUnit(unitData.get(0));
        RequesterEntity result = requesterLogic.createRequester(newEntity);
        Assert.assertNotNull(result);

        RequesterEntity entity = em.find(RequesterEntity.class, result.getId());
        Assert.assertEquals(entity.getId(), result.getId());
        Assert.assertEquals(entity.getName(), result.getName());
        Assert.assertEquals(entity.getLogin(), result.getLogin());
        Assert.assertEquals(entity.getEmail(), result.getEmail());
        Assert.assertEquals(entity.getPhone(), result.getPhone());
        Assert.assertEquals(entity.getImage(), result.getImage());
        
    }
    
    /**
     * Prueba para crear un Requester con login null.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequesterNameNull() throws BusinessLogicException {
        RequesterEntity newEntity = factory.manufacturePojo(RequesterEntity.class);
        newEntity.setName(null);
        RequesterEntity result = requesterLogic.createRequester(newEntity);
    }

    /**
     * Prueba para crear un Requester con email null.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequesterEmailNull() throws BusinessLogicException {
        RequesterEntity newEntity = factory.manufacturePojo(RequesterEntity.class);
        newEntity.setEmail(null);
        RequesterEntity result = requesterLogic.createRequester(newEntity);
    }

    /**
     * Prueba para crear un Requester con login null.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequesterLoginNull() throws BusinessLogicException {
        RequesterEntity newEntity = factory.manufacturePojo(RequesterEntity.class);
        newEntity.setLogin(null);
        RequesterEntity result = requesterLogic.createRequester(newEntity);
    }

    /**
     * Prueba para crear un Requester con phone null.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequesterPhoneNull() throws BusinessLogicException {
        RequesterEntity newEntity = factory.manufacturePojo(RequesterEntity.class);
        newEntity.setPhone(null);
        RequesterEntity result = requesterLogic.createRequester(newEntity);
    }

    /**
     * Prueba para crear un Requester con un login ya existente.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequesterLoginExistente() throws BusinessLogicException {
        RequesterEntity newEntity = factory.manufacturePojo(RequesterEntity.class);
        newEntity.setLogin(data.get(0).getLogin());
        requesterLogic.createRequester(newEntity);
    }

      /**
     * Prueba para crear un Requester con un unit que no existe.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequesterTestConUnitInexistente() throws BusinessLogicException {
        RequesterEntity newEntity = factory.manufacturePojo(RequesterEntity.class);
        UnitEntity unitEntity = new UnitEntity();
        unitEntity.setId(Long.MIN_VALUE);
        newEntity.setUnit(unitEntity);
        requesterLogic.createRequester(newEntity);
    }

    /**
     * Prueba para crear un Requester con unit en null.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequesterTestConNullUnit() throws BusinessLogicException {
        RequesterEntity newEntity = factory.manufacturePojo(RequesterEntity.class);
        newEntity.setUnit(null);
        requesterLogic.createRequester(newEntity);
    }
    
    /**
     * Prueba para consultar la lista de Requesters.
     */
    @Test
    public void getRequestersTest() {
        List<RequesterEntity> list = requesterLogic.getRequesters();
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
     * Prueba para consultar un Requester.
     */
    @Test
    public void getRequesterTest() {
        RequesterEntity entity = data.get(0);
        RequesterEntity resultEntity = requesterLogic.getRequester(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getLogin(), resultEntity.getLogin());
        Assert.assertEquals(entity.getPhone(), resultEntity.getPhone());
        Assert.assertEquals(entity.getEmail(), resultEntity.getEmail());
        Assert.assertEquals(entity.getImage(), resultEntity.getImage());
    }

    /**
     * Prueba para actualizar un Requester.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void updateRequesterTest() throws BusinessLogicException {
        RequesterEntity entity = data.get(0);
        RequesterEntity pojoEntity = factory.manufacturePojo(RequesterEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setPhone("3206745567");
        requesterLogic.updateRequester(pojoEntity.getId(), pojoEntity);
        RequesterEntity resp = em.find(RequesterEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getLogin(), resp.getLogin());
        Assert.assertEquals(pojoEntity.getPhone(), resp.getPhone());
        Assert.assertEquals(pojoEntity.getEmail(), resp.getEmail());
        Assert.assertEquals(pojoEntity.getImage(), resp.getImage());
    }

    /**
     * Prueba para actualizar un Requester con login null.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateRequesterConLoginNullTest() throws BusinessLogicException {
        RequesterEntity entity = data.get(0);
        RequesterEntity pojoEntity = factory.manufacturePojo(RequesterEntity.class);
        pojoEntity.setLogin(null);
        pojoEntity.setId(entity.getId());
        requesterLogic.updateRequester(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para actualizar un Requester con login null.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateRequesterConNameNullTest() throws BusinessLogicException {
        RequesterEntity entity = data.get(0);
        RequesterEntity pojoEntity = factory.manufacturePojo(RequesterEntity.class);
        pojoEntity.setName(null);
        pojoEntity.setId(entity.getId());
        requesterLogic.updateRequester(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para actualizar un Requester con un email null.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateRequesterConEmailNullTest() throws BusinessLogicException {
        RequesterEntity entity = data.get(0);
        RequesterEntity pojoEntity = factory.manufacturePojo(RequesterEntity.class);
        pojoEntity.setEmail(null);
        pojoEntity.setId(entity.getId());
        requesterLogic.updateRequester(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para actualizar un Requester con phone null.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateRequesterConPhoneNullTest() throws BusinessLogicException {
        RequesterEntity entity = data.get(0);
        RequesterEntity pojoEntity = factory.manufacturePojo(RequesterEntity.class);
        pojoEntity.setPhone(null);
        pojoEntity.setId(entity.getId());
        requesterLogic.updateRequester(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para eliminar un Requester
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void deleteRequesterTest() throws BusinessLogicException {
        RequesterEntity entity = data.get(0);
        requesterLogic.deleteRequester(entity.getId());
        RequesterEntity deleted = em.find(RequesterEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

}
