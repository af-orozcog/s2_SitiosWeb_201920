/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.test.logic;

import co.edu.uniandes.csw.sitiosweb.ejb.IterationLogic;
import co.edu.uniandes.csw.sitiosweb.entities.IterationEntity;
import co.edu.uniandes.csw.sitiosweb.persistence.IterationPersistence;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Estudiante Andres Felipe Orozco Gonzalez
 */
@RunWith(Arquillian.class)
public class IterationLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private IterationLogic iterationLogic;
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(IterationEntity.class.getPackage())
                .addPackage(IterationLogic.class.getPackage())
                .addPackage(IterationPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
    @Test
    public createIteration(){
        
    }
    
}
