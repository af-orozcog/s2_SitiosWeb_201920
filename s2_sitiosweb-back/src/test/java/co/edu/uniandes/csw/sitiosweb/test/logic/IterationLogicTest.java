/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.IterationLogic;
import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
import co.edu.uniandes.csw.sitiosweb.entities.IterationEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.IterationPersistence;
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
 * @author Estudiante Andres Felipe Orozco Gonzalez
 */
@RunWith(Arquillian.class)
public class IterationLogicTest {
    
    /**
     * atributo necesario para crear los datos de las pruebas
     */
    private PodamFactory factory = new PodamFactoryImpl();
    
    /**
     * atributo para conectarse con la capa de logica
     */
    @Inject
    private IterationLogic iterationLogic;
    
    @PersistenceContext
    protected EntityManager em;
    
    /**
     * manejador de transaccionalidad
     */
    @Inject
    private UserTransaction utx;
    
    /**
     * donde se van a preestablecer algunos datos para probar los
     * métodos de la logica
     */
    private List<IterationEntity> data = new ArrayList<IterationEntity>();
    
    /**
     * donde se van a preestablecer algunos datos para probar los 
     * métodos de la logica
     */
    private List<ProjectEntity> dataProject = new ArrayList<ProjectEntity>();
    
    
    @Deployment
     public static JavaArchive createDeploymet(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(IterationEntity.class.getPackage())
                .addPackage(IterationPersistence.class.getPackage())
                .addPackage(IterationLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
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
        em.createQuery("delete from IterationEntity").executeUpdate();
        em.createQuery("delete from ProjectEntity").executeUpdate();
    }
    
    
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            IterationEntity entity = factory.manufacturePojo(IterationEntity.class);
            em.persist(entity);
            data.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            ProjectEntity entity = factory.manufacturePojo(ProjectEntity.class);
            IterationEntity en = data.get(i);
            en.setProject(entity);
            em.persist(en);
            em.persist(entity);
            dataProject.add(entity);
        }
    }
    
    /**
     * Método para probar que la creación de una iteración se haga correctamente
     * @throws BusinessLogicException si se violo alguna regla de negocio para crear la iteración
     */
    @Test
    public void createIteration() throws BusinessLogicException{
        IterationEntity newEntity = factory.manufacturePojo(IterationEntity.class);
        IterationEntity result = iterationLogic.createIteration(newEntity);
        //Assert.assertNotNull(result);
        
        IterationEntity entity = em.find(IterationEntity.class,result.getId());
        Assert.assertEquals(entity.getObjetive(),result.getObjetive());
    }
    
    /**
     * Método para probar que no se creen iteraciones con objetivo nulo
     * @throws BusinessLogicException dado que se crea una iteracion con objetivo nulo
     */
    @Test (expected = BusinessLogicException.class)
    public void createIterationObjetiveNull() throws BusinessLogicException{
        IterationEntity newEntity = factory.manufacturePojo(IterationEntity.class);
        newEntity.setObjetive(null);
        IterationEntity result = iterationLogic.createIteration(newEntity);
    }
    
    /**
     * Método para probar que no se creen iteraciones con fecha de validación nula
     * @throws BusinessLogicException dado que se crea una iteración con fecha de validación nula
     */
    @Test (expected = BusinessLogicException.class)
    public void createIterationValidationDateNull() throws BusinessLogicException{
        IterationEntity newEntity = factory.manufacturePojo(IterationEntity.class);
        newEntity.setValidationDate(null);
        IterationEntity result = iterationLogic.createIteration(newEntity);
    }
    
    /**
     * Método para probar que no se creen iteraciones con fecha de inicio nula
     * @throws BusinessLogicException dado que se crea una iteracion con fecha de validadción nula
     */
    @Test (expected = BusinessLogicException.class)
    public void createIterationBeginDateNull() throws BusinessLogicException{
        IterationEntity newEntity = factory.manufacturePojo(IterationEntity.class);
        newEntity.setBeginDate(null);
        IterationEntity result = iterationLogic.createIteration(newEntity);
    }
    
    /**
     * Método para probar que no se creen iteraciones con fecha final nula
     * @throws BusinessLogicException dado que se crea una iteracion con fecha final nula
     */
    @Test (expected = BusinessLogicException.class)
    public void createIterationEndDateNull() throws BusinessLogicException{
        IterationEntity newEntity = factory.manufacturePojo(IterationEntity.class);
        newEntity.setEndDate(null);
        IterationEntity result = iterationLogic.createIteration(newEntity);
    }
    
    
    /**
     * Prueba para consultar la lista de Iterations
     */
    @Test
    public void getIterationsTest() {
        List<IterationEntity> list = iterationLogic.getIterations();
        Assert.assertEquals(data.size(), list.size());
        for (IterationEntity entity : list) {
            boolean found = false;
            for (IterationEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
   /**
     * Prueba para actualizar un Iteracion.
     */
    @Test
    public void updateIterationTest() throws BusinessLogicException {
        IterationEntity entity = data.get(0);
        IterationEntity pojoEntity = factory.manufacturePojo(IterationEntity.class);

        pojoEntity.setId(entity.getId());

        iterationLogic.updateIteration(dataProject.get(0).getId(), pojoEntity);

        IterationEntity resp = em.find(IterationEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getObjetive(), resp.getObjetive());
        Assert.assertEquals(pojoEntity.getChanges(), resp.getChanges());
    } 
    
    /**
     * Prueba para eliminar una iteración
     *
     * @throws BusinessLogicException
     */
    @Test
    public void deleteIterationTest() throws BusinessLogicException {
        IterationEntity entity = data.get(0);
        iterationLogic.deleteIteration(entity.getId());
        IterationEntity deleted = em.find(IterationEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
