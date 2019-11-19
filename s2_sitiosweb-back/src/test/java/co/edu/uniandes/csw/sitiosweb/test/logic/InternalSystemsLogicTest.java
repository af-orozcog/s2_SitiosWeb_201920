/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.InternalSystemsLogic;
import co.edu.uniandes.csw.sitiosweb.ejb.InternalSystemsLogic;
import co.edu.uniandes.csw.sitiosweb.entities.InternalSystemsEntity;
import co.edu.uniandes.csw.sitiosweb.entities.InternalSystemsEntity;
import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.InternalSystemsPersistence;
import co.edu.uniandes.csw.sitiosweb.persistence.InternalSystemsPersistence;
import co.edu.uniandes.csw.sitiosweb.persistence.RequestPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Andres Martinez Silva 
 */
@RunWith(Arquillian.class)
public class InternalSystemsLogicTest {
	
	/**
     * atributo necesario para crear los datos de las pruebas
     */
    private PodamFactory factory = new PodamFactoryImpl();
    
    /**
     * atributo para conectarse con la capa de logica
     */
    @Inject
    private InternalSystemsLogic iterationLogic;
    
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
    private List<InternalSystemsEntity> data = new ArrayList<InternalSystemsEntity>();
    
    /**
     * donde se van a preestablecer algunos datos para probar los 
     * métodos de la logica
     */
    private List<ProjectEntity> dataProject = new ArrayList<ProjectEntity>();
    
    
    @Deployment
     public static JavaArchive createDeploymet(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(InternalSystemsEntity.class.getPackage())
                .addPackage(InternalSystemsPersistence.class.getPackage())
                .addPackage(InternalSystemsLogic.class.getPackage())
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
        em.createQuery("delete from InternalSystemsEntity").executeUpdate();
        em.createQuery("delete from ProjectEntity").executeUpdate();
    }
    
    
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            InternalSystemsEntity entity = factory.manufacturePojo(InternalSystemsEntity.class);
            em.persist(entity);
            data.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            ProjectEntity entity = factory.manufacturePojo(ProjectEntity.class);
            InternalSystemsEntity en = data.get(i);
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
    public void createInternalSystems() throws BusinessLogicException{
        InternalSystemsEntity newEntity = factory.manufacturePojo(InternalSystemsEntity.class);
        newEntity.setProject(dataProject.get(0));
        InternalSystemsEntity result = iterationLogic.createInternalSystems(dataProject.get(0).getId(),newEntity);
        Assert.assertNotNull(result);
        
        InternalSystemsEntity entity = em.find(InternalSystemsEntity.class,result.getId());
        Assert.assertEquals(entity.getType(),result.getType());
    }

    
    /**
     * Método para probar que no se creen iteraciones con fecha final nula
     * @throws BusinessLogicException dado que se crea una iteracion con fecha final nula
     */
    @Test (expected = BusinessLogicException.class)
    public void createInternalSystemsTypeNull() throws BusinessLogicException{
        InternalSystemsEntity newEntity = factory.manufacturePojo(InternalSystemsEntity.class);
        newEntity.setProject(dataProject.get(0));
        newEntity.setType(null);
        InternalSystemsEntity result = iterationLogic.createInternalSystems(dataProject.get(0).getId(),newEntity);
    }
    
    
    /**
     * Prueba para consultar la lista de InternalSystemss
     */
    @Test
    public void getInternalSystemssTest() {
        List<InternalSystemsEntity> list = iterationLogic.getInternalSystems(dataProject.get(0).getId());
        Assert.assertEquals(1, list.size());
        for (InternalSystemsEntity entity : list) {
            boolean found = false;
            for (InternalSystemsEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getInternalSystemsTest(){
        InternalSystemsEntity entity = data.get(0);
        InternalSystemsEntity resultEntity = iterationLogic.getInternalSystems(dataProject.get(0).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getType(), resultEntity.getType());
    }
    
   /**
     * Prueba para actualizar un Iteracion.
     */
    @Test
    public void updateInternalSystemsTest() throws BusinessLogicException {
        InternalSystemsEntity entity = data.get(0);
        InternalSystemsEntity pojoEntity = factory.manufacturePojo(InternalSystemsEntity.class);

        pojoEntity.setId(entity.getId());

        iterationLogic.updateInternalSystems(dataProject.get(0).getId(), pojoEntity);

        InternalSystemsEntity resp = em.find(InternalSystemsEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getType(), resp.getType());
    } 
    
    /**
     * Prueba para eliminar una iteración
     *
     * @throws BusinessLogicException
     */
    @Test
    public void deleteInternalSystemsTest() throws BusinessLogicException {
        InternalSystemsEntity entity = data.get(0);
        iterationLogic.deleteInternalSystems(dataProject.get(0).getId(), entity.getId());
        InternalSystemsEntity deleted = em.find(InternalSystemsEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para eliminarle un review a un book del cual no pertenece.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteInternalSystemsConProjectNoAsociadoTest() throws BusinessLogicException {
        InternalSystemsEntity entity = data.get(0);
        iterationLogic.deleteInternalSystems(dataProject.get(1).getId(), entity.getId());
    }
}
