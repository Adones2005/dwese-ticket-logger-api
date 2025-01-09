package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_webapp.entity.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional // Indica que las operaciones en este DAO se ejecutan en una transacción
public class CategoryDAOImpl implements CategoryDAO{

    // Logger para registrar eventos importantes en el DAO
    private static final Logger logger = LoggerFactory.getLogger(CategoryDAOImpl.class);

    @PersistenceContext // Inyección del EntityManager para realizar operaciones con la base de datos
    private EntityManager entityManager;

    /**
     * Lista todas las categorías almacenadas en la base de datos.
     *
     * @return Lista de todas las categorías.
     */
    @Override
    public List<Category> listAllCategories() {
        logger.info("Listing all categories from the database.");


        String query = "SELECT c FROM Category c";

        List<Category> categories = entityManager.createQuery(query, Category.class).getResultList();

        logger.info("Retrieved {} categories from the database.", categories.size());
        return categories;
    }


    /**
     * Inserta una nueva categoría en la base de datos.
     *
     * @param category Categoría a insertar.
     */
    @Override
    public void insertCategory(Category category) {
        logger.info("Inserting category with name: {} and parentId: {}", category.getName(), category.getParent());
        entityManager.persist(category); // Persistir la categoría en la base de datos
        logger.info("Inserted category with ID: {}", category.getId());
    }

    /**
     * Actualiza una categoría existente en la base de datos.
     *
     * @param category Categoría con los nuevos datos.
     */
    @Override
    public void updateCategory(Category category) {
        logger.info("Updating category with id: {}", category.getId());
        entityManager.merge(category); // Actualizar la categoría en la base de datos
        logger.info("Updated category with id: {}", category.getId());
    }

    /**
     * Elimina una categoría de la base de datos por su ID.
     *
     * @param id ID de la categoría a eliminar.
     */
    @Override
    public void deleteCategory(int id) {
        logger.info("Deleting category with id: {}", id);
        Category category = entityManager.find(Category.class, id); // Buscar la categoría por ID
        if (category != null) {
            entityManager.remove(category); // Eliminar la categoría si existe
            logger.info("Deleted category with id: {}", id);
        } else {
            logger.warn("Category with id: {} not found.", id); // Advertir si la categoría no existe
        }
    }

    /**
     * Recupera una categoría de la base de datos por su ID.
     *
     * @param id ID de la categoría a recuperar.
     * @return Categoría correspondiente al ID, o null si no existe.
     */
    @Override
    public Category getCategoryById(int id) {
        logger.info("Retrieving category by id: {}", id);
        Category category = entityManager.find(Category.class, id); // Buscar la categoría por ID
        if (category != null) {
            logger.info("Category retrieved: {} - {}", category.getName(), category.getParent());
        } else {
            logger.warn("No category found with id: {}", id); // Advertir si la categoría no se encuentra
        }
        return category; // Retornar la categoría encontrada o null
    }
}
