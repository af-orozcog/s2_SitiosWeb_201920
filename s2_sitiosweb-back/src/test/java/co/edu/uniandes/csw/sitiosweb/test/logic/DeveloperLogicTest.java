/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.DeveloperLogic;
import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.DeveloperPersistence;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@RunWith(Arquillian.class)
public class DeveloperLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private DeveloperLogic developerLogic;

    @PersistenceContext
    protected EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<DeveloperEntity> data = new ArrayList<DeveloperEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DeveloperEntity.class.getPackage())
                .addPackage(ProjectEntity.class.getPackage())
                .addPackage(DeveloperLogic.class.getPackage())
                .addPackage(DeveloperPersistence.class.getPackage())
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
        em.createQuery("delete from ProjectEntity").executeUpdate();
        em.createQuery("delete from DeveloperEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            DeveloperEntity entity = factory.manufacturePojo(DeveloperEntity.class);
            em.persist(entity);
            entity.setLeadingProjects(new ArrayList<>());
            data.add(entity);
        }

        DeveloperEntity leader = data.get(2);
        ProjectEntity entity = factory.manufacturePojo(ProjectEntity.class);
        entity.setLeader(leader);
        em.persist(entity);
        leader.getLeadingProjects().add(entity);
    }

    /**
     * Prueba para crear un Developer.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void createDeveloper() throws BusinessLogicException {

        DeveloperEntity newEntity = factory.manufacturePojo(DeveloperEntity.class);
        newEntity.setPhone("3206745567");
        DeveloperEntity result = developerLogic.createDeveloper(newEntity);
        Assert.assertNotNull(result);

        DeveloperEntity entity = em.find(DeveloperEntity.class, result.getId());
        Assert.assertEquals(entity.getId(), result.getId());
        Assert.assertEquals(entity.getLogin(), result.getLogin());
        Assert.assertEquals(entity.getEmail(), result.getEmail());
        Assert.assertEquals(entity.getPhone(), result.getPhone());

    }

    /**
     * Prueba para crear un Developer con email null.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createDeveloperEmailNull() throws BusinessLogicException {
        DeveloperEntity newEntity = factory.manufacturePojo(DeveloperEntity.class);
        newEntity.setEmail(null);
        DeveloperEntity result = developerLogic.createDeveloper(newEntity);
    }

    /**
     * Prueba para crear un Developer con login null.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createDeveloperLoginNull() throws BusinessLogicException {
        DeveloperEntity newEntity = factory.manufacturePojo(DeveloperEntity.class);
        newEntity.setLogin(null);
        DeveloperEntity result = developerLogic.createDeveloper(newEntity);
    }

    /**
     * Prueba para crear un Developer con phone null.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createDeveloperPhoneNull() throws BusinessLogicException {
        DeveloperEntity newEntity = factory.manufacturePojo(DeveloperEntity.class);
        newEntity.setPhone(null);
        DeveloperEntity result = developerLogic.createDeveloper(newEntity);
    }

    /**
     * Prueba para crear un Developer con type null.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createDeveloperTypeNull() throws BusinessLogicException {
        DeveloperEntity newEntity = factory.manufacturePojo(DeveloperEntity.class);
        newEntity.setType(null);
        DeveloperEntity result = developerLogic.createDeveloper(newEntity);
    }

    /**
     * Prueba para crear un Developer con un login ya existente.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createDeveloperLoginExistente() throws BusinessLogicException {
        DeveloperEntity newEntity = factory.manufacturePojo(DeveloperEntity.class);
        newEntity.setLogin(data.get(0).getLogin());
        developerLogic.createDeveloper(newEntity);
    }

    /**
     * Prueba para consultar la lista de Developers.
     */
    @Test
    public void getDevelopersTest() {
        List<DeveloperEntity> list = developerLogic.getDevelopers();
        Assert.assertEquals(data.size(), list.size());
        for (DeveloperEntity entity : list) {
            boolean found = false;
            for (DeveloperEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Developer.
     */
    @Test
    public void getDeveloperTest() {
        DeveloperEntity entity = data.get(0);
        DeveloperEntity resultEntity = developerLogic.getDeveloper(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getLogin(), resultEntity.getLogin());
        Assert.assertEquals(entity.getPhone(), resultEntity.getPhone());
        Assert.assertEquals(entity.getEmail(), resultEntity.getEmail());
    }

    /**
     * Prueba para actualizar un Developer.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void updateDeveloperTest() throws BusinessLogicException {
        DeveloperEntity entity = data.get(0);
        DeveloperEntity pojoEntity = factory.manufacturePojo(DeveloperEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setPhone("3206745567");
        developerLogic.updateDeveloper(pojoEntity.getId(), pojoEntity);
        DeveloperEntity resp = em.find(DeveloperEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getLogin(), resp.getLogin());
        Assert.assertEquals(pojoEntity.getPhone(), resp.getPhone());
        Assert.assertEquals(pojoEntity.getEmail(), resp.getEmail());
    }

    /**
     * Prueba para actualizar un Developer con login null.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateDeveloperConLoginNullTest() throws BusinessLogicException {
        DeveloperEntity entity = data.get(0);
        DeveloperEntity pojoEntity = factory.manufacturePojo(DeveloperEntity.class);
        pojoEntity.setLogin(null);
        pojoEntity.setId(entity.getId());
        developerLogic.updateDeveloper(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para actualizar un Developer con un login existente.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateDeveloperConLoginExistenteTest() throws BusinessLogicException {
        DeveloperEntity entity = data.get(0);
        DeveloperEntity pojoEntity = factory.manufacturePojo(DeveloperEntity.class);
        pojoEntity.setLogin(data.get(1).getLogin());
        pojoEntity.setId(entity.getId());
        developerLogic.updateDeveloper(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para actualizar un Developer con email null.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateDeveloperConEmailNullest() throws BusinessLogicException {
        DeveloperEntity entity = data.get(0);
        DeveloperEntity pojoEntity = factory.manufacturePojo(DeveloperEntity.class);
        pojoEntity.setEmail(null);
        pojoEntity.setId(entity.getId());
        developerLogic.updateDeveloper(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para actualizar un Developer con phone null.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateDeveloperConPhoneNullTest() throws BusinessLogicException {
        DeveloperEntity entity = data.get(0);
        DeveloperEntity pojoEntity = factory.manufacturePojo(DeveloperEntity.class);
        pojoEntity.setPhone(null);
        pojoEntity.setId(entity.getId());
        developerLogic.updateDeveloper(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para actualizar un Developer con type developer y es lider de
     * proyectos.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateDeveloperConTypeDeveloperTest() throws BusinessLogicException {
        DeveloperEntity entity = data.get(2);
        DeveloperEntity pojoEntity = factory.manufacturePojo(DeveloperEntity.class);
        pojoEntity.setType(DeveloperEntity.DeveloperType.Developer);
        pojoEntity.setId(entity.getId());
        developerLogic.updateDeveloper(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para eliminar un Developer
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void deleteDeveloperTest() throws BusinessLogicException {
        DeveloperEntity entity = data.get(0);
        developerLogic.deleteDeveloper(entity.getId());
        DeveloperEntity deleted = em.find(DeveloperEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminar un Developer asociado como lider a un proyecto
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteDeveloperLeadingTest() throws BusinessLogicException {
        developerLogic.deleteDeveloper(data.get(2).getId());
    }
}
