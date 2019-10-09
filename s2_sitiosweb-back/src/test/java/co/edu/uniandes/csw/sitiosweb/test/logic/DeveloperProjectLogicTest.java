/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
import co.edu.uniandes.csw.sitiosweb.ejb.DeveloperProjectLogic;
import co.edu.uniandes.csw.sitiosweb.ejb.DeveloperLogic;
import co.edu.uniandes.csw.sitiosweb.ejb.ProjectLogic;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.DeveloperPersistence;

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
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@RunWith(Arquillian.class)
public class DeveloperProjectLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private DeveloperProjectLogic developerProjectLogic;

    @Inject
    private ProjectLogic projectLogic;

    @Inject
    private DeveloperLogic developerLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private DeveloperEntity developer = new DeveloperEntity();
    private DeveloperEntity leader = new DeveloperEntity();

    private List<ProjectEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DeveloperProjectLogic.class.getPackage())
                .addPackage(DeveloperEntity.class.getPackage())
                .addPackage(ProjectEntity.class.getPackage())
                .addPackage(DeveloperLogic.class.getPackage())
                .addPackage(ProjectLogic.class.getPackage())
                .addPackage(DeveloperPersistence.class.getPackage())
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
        em.createQuery("delete from DeveloperEntity").executeUpdate();
        em.createQuery("delete from ProjectEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        developer = factory.manufacturePojo(DeveloperEntity.class);
        developer.setId(1L);
        developer.setProjects(new ArrayList<>());
        em.persist(developer);

        leader = factory.manufacturePojo(DeveloperEntity.class);
        leader.setId(2L);
        leader.setLeadingProjects(new ArrayList<>());
        leader.setType(DeveloperEntity.DeveloperType.Leader);
        em.persist(leader);

        for (int i = 0; i < 3; i++) {
            ProjectEntity entity = factory.manufacturePojo(ProjectEntity.class);
            entity.setDevelopers(new ArrayList<>());
            entity.getDevelopers().add(developer);
            entity.setLeader(leader);
            em.persist(entity);
            data.add(entity);
            developer.getProjects().add(entity);
            leader.getLeadingProjects().add(entity);
        }
    }

    /**
     * Prueba para asociar un desarrollador a un proyecto.
     *
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void addDeveloperTest() throws BusinessLogicException {
        ProjectEntity project = factory.manufacturePojo(ProjectEntity.class);
        projectLogic.createProject(project);
        developerLogic.createDeveloper(developer);

        DeveloperEntity developerEntity = developerProjectLogic.addDeveloper(developer.getId(), project.getId());
        Assert.assertNotNull(developerEntity);

        Assert.assertEquals(developerEntity.getId(), developer.getId());
        Assert.assertEquals(developerEntity.getLogin(), developer.getLogin());
        Assert.assertEquals(developerEntity.getEmail(), developer.getEmail());
        Assert.assertEquals(developerEntity.getPhone(), developer.getPhone());
        Assert.assertEquals(developerEntity.getType(), developer.getType());

        ProjectEntity lastProject = developerProjectLogic.getProject(developer.getId(), project.getId());

        Assert.assertEquals(lastProject.getId(), lastProject.getId());
        Assert.assertEquals(lastProject.getInternalProject(), lastProject.getInternalProject());
        Assert.assertEquals(lastProject.getCompany(), lastProject.getCompany());
        Assert.assertEquals(lastProject.getLeader(), lastProject.getLeader());
        Assert.assertEquals(lastProject.getHardware(), lastProject.getHardware());
        Assert.assertEquals(lastProject.getProvider(), lastProject.getProvider());

    }

}
