/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.DeveloperPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@Stateless
public class DeveloperProjectLogic {
    private static final Logger LOGGER = Logger.getLogger(DeveloperProjectLogic.class.getName());
    
    @Inject
    private BookPersistence bookPersistence;

    @Inject
    private EditorialPersistence editorialPersistence;

    /**
     * Remplazar la editorial de un book.
     *
     * @param booksId id del libro que se quiere actualizar.
     * @param editorialsId El id de la editorial que se será del libro.
     * @return el nuevo libro.
     */
    public BookEntity replaceEditorial(Long booksId, Long editorialsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar libro con id = {0}", booksId);
        EditorialEntity editorialEntity = editorialPersistence.find(editorialsId);
        BookEntity bookEntity = bookPersistence.find(booksId);
        bookEntity.setEditorial(editorialEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar libro con id = {0}", bookEntity.getId());
        return bookEntity;
    }

    /**
     * Borrar un book de una editorial. Este metodo se utiliza para borrar la
     * relacion de un libro.
     *
     * @param booksId El libro que se desea borrar de la editorial.
     */
    public void removeEditorial(Long booksId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la Editorial del libro con id = {0}", booksId);
        BookEntity bookEntity = bookPersistence.find(booksId);
        EditorialEntity editorialEntity = editorialPersistence.find(bookEntity.getEditorial().getId());
        bookEntity.setEditorial(null);
        editorialEntity.getBooks().remove(bookEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la Editorial del libro con id = {0}", bookEntity.getId());
    }
    
    //private boolean validatePhone(Integer phone) {
      //  return !(phone == null || Long.SIZE != 9);
    //}
}
