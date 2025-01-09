package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.controllers;


import jakarta.validation.Valid;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_webapp.dao.CategoryDAO;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_webapp.entity.Category;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_webapp.services.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;
@Controller
@RequestMapping("/categories") // Mapeo de la URL para las operaciones de categorías
public class CategoryController {

    // Logger para registrar información, advertencias y errores
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    // DAO para gestionar las operaciones de las categorias en la base de datos
    @Autowired
    private CategoryDAO categoryDAO;

    // Servicio para manejar el almacenamiento de archivos
    @Autowired
    private FileStorageService fileStorageService;

    // Fuente de mensajes para la internacionalización
    @Autowired
    private MessageSource messageSource;

    /**
     * Maneja la solicitud para listar todas las categorías.
     *
     * @param model Modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para renderizar la lista de categorías.
     */
    @GetMapping
    public String listCategories(Model model) {
        logger.info("Solicitando la lista de todas las categorias...");
        List<Category> listCategories = categoryDAO.listAllCategories(); // Obtener la lista de categorias
        logger.info("Se han cargado {} categorias.", listCategories.size());
        model.addAttribute("listCategories", listCategories); // Pasar la lista de categorias al modelo
        return "category"; // Nombre de la plantilla Thymeleaf a renderizar
    }

    /**
     * Muestra el formulario para crear una nueva categoría.
     *
     * @param model Modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para el formulario.
     */
    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Mostrando formulario para nueva categoria.");
        model.addAttribute("category", new Category()); // Crear un nuevo objeto Categoria
        List<Category> listCategories = categoryDAO.listAllCategories(); // Obtener la lista de Categorias
        model.addAttribute("listCategories", listCategories); // Pasar la lista de Categorias
        return "category-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }

    /**
     * Muestra el formulario para editar una categoría existente.
     *
     * @param id    ID de la categoría a editar.
     * @param model Modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para el formulario.
     */
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") int id, Model model) {
        logger.info("Mostrando formulario de edición para la categoria con ID {}", id);
        Category category = categoryDAO.getCategoryById(id); // Obtener la categoria por ID
        List<Category> listCategories = categoryDAO.listAllCategories(); // Obtener la lista de categorias

        if (category == null) {
            logger.warn("No se encontró la categoria con ID {}", id);
            model.addAttribute("listCategories", listCategories); // Pasar la lista de Categorias
            return "redirect:/categories"; // Redirigir si no se encuentra la categoria
        }

        model.addAttribute("category", category); // Pasar la categoria al modelo
        model.addAttribute("listCategories", listCategories); // Pasar la lista de categorias al modelo
        return "category-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }

    /**
     * Inserta una nueva categoría en la base de datos.
     *
     * @param category             Objeto que contiene los datos del formulario.
     * @param result               Resultados de la validación del formulario.
     * @param imageFile           Archivo de imagen subido para la categoría.
     * @param redirectAttributes    Atributos para mensajes flash de redirección.
     * @param locale               Locale para la internacionalización.
     * @param model                Modelo para pasar datos a la vista.
     * @return Redirección a la lista de categorías.
     */
    @PostMapping("/insert")
    public String insertCategory(@Valid @ModelAttribute("category") Category category, BindingResult result,
                                 @RequestParam("imageFile") MultipartFile imageFile,
                                 RedirectAttributes redirectAttributes, Locale locale, Model model) {

        // Verificar si hay errores en la validación
        if (result.hasErrors()) {
            List<Category> listCategories = categoryDAO.listAllCategories();
            model.addAttribute("listCategories", listCategories); // Pasar la lista de Categorias
            return "category-form"; // Devuelve el formulario para mostrar los errores de validación
        }

        logger.info("Insertando nueva categoria con name {}", category.getName());

        // Verificar y asignar categoría padre si existe
        if (category.getParent() != null && category.getParent().getId() == null) {
            category.setParent(null); // No asignar categoría padre
        }

        // Guardar el archivo de imagen si se ha subido uno
        if (!imageFile.isEmpty()) {
            String fileName = fileStorageService.saveFile(imageFile);
            if (fileName != null) {
                category.setImage(fileName); // Guardar el nombre del archivo en la entidad
            }
        }

        categoryDAO.insertCategory(category); // Insertar la nueva categoria

        logger.info("Categoria {} insertada con éxito.", category.getName());
        return "redirect:/categories"; // Redirigir a la lista de categorias
    }

    /**
     * Actualiza una categoría existente en la base de datos.
     *
     * @param category             Objeto que contiene los datos del formulario.
     * @param result               Resultados de la validación del formulario.
     * @param imageFile           Archivo de imagen subido para la categoría.
     * @param redirectAttributes    Atributos para mensajes flash de redirección.
     * @param locale               Locale para la internacionalización.
     * @param model                Modelo para pasar datos a la vista.
     * @return Redirección a la lista de categorías.
     */
    @PostMapping("/update")
    public String updateCategory(@Valid @ModelAttribute("category") Category category, BindingResult result,
                                 @RequestParam("imageFile") MultipartFile imageFile,
                                 RedirectAttributes redirectAttributes, Locale locale, Model model) {

        // Verificar si hay errores en la validación
        if (result.hasErrors()) {
            List<Category> listCategories = categoryDAO.listAllCategories();
            model.addAttribute("listCategories", listCategories); // Pasar la lista de Categorias
            return "category-form"; // Devuelve el formulario para mostrar los errores de validación
        }

        logger.info("Actualizando categoria con ID {}", category.getId());

        // Verificar y asignar categoría padre si existe
        if (category.getParent() != null && category.getParent().getId() == null) {
            category.setParent(null); // No asignar categoría padre
        }

        // Guardar el archivo de imagen si se ha subido uno
        if (!imageFile.isEmpty()) {
            String fileName = fileStorageService.saveFile(imageFile);
            if (fileName != null) {
                category.setImage(fileName); // Guardar el nombre del archivo en la entidad
            }
        }

        categoryDAO.updateCategory(category); // Actualizar la categoria

        logger.info("Categoria con ID {} actualizada con éxito.", category.getId());
        return "redirect:/categories"; // Redirigir a la lista de categorias
    }

    /**
     * Elimina una categoría de la base de datos.
     *
     * @param id                   ID de la categoría a eliminar.
     * @param redirectAttributes    Atributos para mensajes flash de redirección.
     * @return Redirección a la lista de categorías.
     */
    @PostMapping("/delete")
    public String deleteCategory(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        logger.info("Eliminando categoria con ID {}", id);
        try{
            // Obtener la categoría antes de eliminarla para acceder a la imagen
            Category category = categoryDAO.getCategoryById(id);

            // Eliminar la categoría
            categoryDAO.deleteCategory(id);
            logger.info("Categoria con ID {} eliminada con éxito.", id);

            // Eliminar la imagen asociada, si existe
            if (category.getImage() != null && !category.getImage().isEmpty()) {
                fileStorageService.deleteFile(category.getImage());
                logger.info("Imagen {} eliminada del almacenamiento.", category.getImage());
            }
        }catch (Exception e){
            logger.error("Error al eliminar la categoria {}", e.getMessage() );

        }



        return "redirect:/categories"; // Redirigir a la lista de categorias
    }
}
