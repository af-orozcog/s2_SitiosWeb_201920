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
 * @developer Nicolás Abondano nf.abondano 201812467
 */
@RunWith(Arquillian.class)
public class DeveloperProjectLogicTest {
private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private DeveloperProjectLogic developerProjectLogic;

    @Inject
    private ProjectLogic projectLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private DeveloperEntity developer = new DeveloperEntity();

    private List<ProjectEntity> data = new ArrayList<ProjectEntity>();

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
                .addPackage(DeveloperProjectLogic.class.getPackage())
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
        developer.setPhone("3206745567");
        developer.setProjects(new ArrayList<>());
        em.persist(developer);
        
        for (int i = 0; i < 3; i++) {
            ProjectEntity entity = factory.manufacturePojo(ProjectEntity.class);
            entity.setDevelopers(new ArrayList<>());
            entity.getDevelopers().add(developer);
            entity.setName("project" + i);
            em.persist(entity);
            data.add(entity);
            developer.getProjects().add(entity);
        }
    }

    /**
     * Prueba para asociar un desarrollador a un proyecto.
     *
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void addProjectTest() throws BusinessLogicException {
        ProjectEntity newProject = factory.manufacturePojo(ProjectEntity.class);
        newProject.setName("new project");
        projectLogic.createProject(newProject);
        ProjectEntity projectEntity = developerProjectLogic.addProject(developer.getId(), newProject.getId());
        Assert.assertNotNull(projectEntity);

        Assert.assertEquals(projectEntity.getId(), newProject.getId());
        Assert.assertEquals(projectEntity.getCompany(), newProject.getCompany());
        Assert.assertEquals(projectEntity.getInternalProject(), newProject.getInternalProject());

        ProjectEntity lastProject = developerProjectLogic.getProject(developer.getId(), newProject.getId());

        Assert.assertEquals(lastProject.getId(), newProject.getId());
        Assert.assertEquals(lastProject.getCompany(), newProject.getCompany());
        Assert.assertEquals(lastProject.getInternalProject(), newProject.getInternalProject());

    }
    
    /**
     * Prueba para consultar la lista de Projects de un desarrollador.
     */
    @Test
    public void getProjectsTest() {
        List<ProjectEntity> projectEntities = developerProjectLogic.getProjects(developer.getId());

        Assert.assertEquals(data.size(), projectEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(projectEntities.contains(data.get(0)));
        }
    }

    /**
     * Prueba para cpnsultar un proyecto de un desarrollador.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void getProjectTest() throws BusinessLogicException {
        ProjectEntity projectEntity = data.get(0);
        ProjectEntity project = developerProjectLogic.getProject(developer.getId(), projectEntity.getId());
        Assert.assertNotNull(project);

        Assert.assertEquals(projectEntity.getId(), project.getId());
        Assert.assertEquals(projectEntity.getCompany(), project.getCompany());
        Assert.assertEquals(projectEntity.getInternalProject(), project.getInternalProject());
    }

    /**
     * Prueba para actualizar los proyectos de un desarrollador.
     *
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    @Test
    public void replaceProjectsTest() throws BusinessLogicException {
        List<ProjectEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ProjectEntity entity = factory.manufacturePojo(ProjectEntity.class);
            entity.setName("replace" + i);
            entity.setDevelopers(new ArrayList<>());
            entity.getDevelopers().add(developer);
            projectLogic.createProject(entity);
            nuevaLista.add(entity);
        }
        developerProjectLogic.replaceProjects(developer.getId(), nuevaLista);
        List<ProjectEntity> projectEntities = developerProjectLogic.getProjects(developer.getId());
        for (ProjectEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(projectEntities.contains(aNuevaLista));
        }
    }
    
    /**
     * Prueba desasociar un proyecto con un desarrollador.
     *
     */
    @Test
    public void removeProjectTest() {
        for (ProjectEntity project : data) {
            developerProjectLogic.removeProject(developer.getId(), project.getId());
        }
        Assert.assertTrue(developerProjectLogic.getProjects(developer.getId()).isEmpty());
    }
}
