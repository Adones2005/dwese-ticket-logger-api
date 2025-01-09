package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_webapp.dao.LocationDAO;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_webapp.dao.ProvinceDAO;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_webapp.dao.SupermarketDAO;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_webapp.entity.Location;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_webapp.entity.Province;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_webapp.entity.Supermarket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;

/**
 * Controlador que maneja las operaciones CRUD para la entidad `Location`.
 * Utiliza `LocationDAO` para interactuar con la base de datos.
 */
@Controller
@RequestMapping("/locations")
public class LocationController {

    // Logger para registrar información, advertencias y errores
    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    // DAO para gestionar las operaciones de las ubicaciones en la base de datos
    @Autowired
    private LocationDAO locationDAO;

    // DAO para gestionar las provincias
    @Autowired
    private ProvinceDAO provinceDAO;

    // DAO para gestionar los supermercados
    @Autowired
    private SupermarketDAO supermarketDAO;

    // Fuente de mensajes para la internacionalización
    @Autowired
    private MessageSource messageSource;

    /**
     * Lista todas las ubicaciones y las pasa como atributo al modelo para que sean
     * accesibles en la vista `location.html`.
     *
     * @param model Objeto del modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para renderizar la lista de ubicaciones.
     */
    @GetMapping
    public String listLocations(Model model) {
        logger.info("Solicitando la lista de todas las ubicaciones...");
        List<Location> listLocations = locationDAO.listAllLocations(); // Obtener la lista de ubicaciones
        logger.info("Se han cargado {} ubicaciones.", listLocations.size());
        model.addAttribute("listLocations", listLocations); // Pasar la lista de ubicaciones al modelo
        return "location"; // Nombre de la plantilla Thymeleaf a renderizar
    }

    /**
     * Muestra el formulario para crear una nueva ubicación.
     *
     * @param model Modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para el formulario.
     */
    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Mostrando formulario para nueva ubicación.");
        List<Province> listProvinces = provinceDAO.listAllProvinces(); // Obtener la lista de provincias
        List<Supermarket> listSupermarkets = supermarketDAO.listAllSupermarkets(); // Obtener la lista de supermercados
        model.addAttribute("location", new Location()); // Crear un nuevo objeto Location
        model.addAttribute("listProvinces", listProvinces); // Pasar la lista de provincias al modelo
        model.addAttribute("listSupermarkets", listSupermarkets); // Pasar la lista de supermercados al modelo
        return "location-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }

    /**
     * Muestra el formulario para editar una ubicación existente.
     *
     * @param id    ID de la ubicación a editar.
     * @param model Modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para el formulario.
     */
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") int id, Model model) {
        logger.info("Mostrando formulario de edición para la ubicación con ID {}", id);
        Location location = locationDAO.getLocationById(id); // Obtener la ubicación por ID
        List<Province> listProvinces = provinceDAO.listAllProvinces(); // Obtener la lista de provincias
        List<Supermarket> listSupermarkets = supermarketDAO.listAllSupermarkets(); // Obtener la lista de supermercados

        if (location == null) {
            logger.warn("No se encontró la ubicación con ID {}", id);
            return "redirect:/locations"; // Redirigir si no se encuentra la ubicación
        }

        model.addAttribute("location", location); // Pasar la ubicación al modelo
        model.addAttribute("listProvinces", listProvinces); // Pasar la lista de provincias al modelo
        model.addAttribute("listSupermarkets", listSupermarkets); // Pasar la lista de supermercados al modelo
        return "location-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }

    /**
     * Inserta una nueva ubicación en la base de datos.
     *
     * @param location             Objeto que contiene los datos del formulario.
     * @param result               Resultados de la validación del formulario.
     * @param redirectAttributes    Atributos para mensajes flash de redirección.
     * @param locale               Locale para la internacionalización.
     * @return Redirección a la lista de ubicaciones.
     */
    @PostMapping("/insert")
    public String insertLocation(@Valid @ModelAttribute("location") Location location, BindingResult result, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Insertando nueva ubicación en la ciudad {}", location.getCity());

        if (result.hasErrors()) {
            return "location-form"; // Devuelve el formulario para mostrar los errores de validación
        }

        locationDAO.insertLocation(location); // Insertar la nueva ubicación
        logger.info("Ubicación insertada con éxito.");
        return "redirect:/locations"; // Redirigir a la lista de ubicaciones
    }

    /**
     * Actualiza una ubicación existente en la base de datos.
     *
     * @param location             Objeto que contiene los datos del formulario.
     * @param result               Resultados de la validación del formulario.
     * @param redirectAttributes    Atributos para mensajes flash de redirección.
     * @param locale               Locale para la internacionalización.
     * @return Redirección a la lista de ubicaciones.
     */
    @PostMapping("/update")
    public String updateLocation(@Valid @ModelAttribute("location") Location location, BindingResult result, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Actualizando ubicación con ID {}", location.getId());

        if (result.hasErrors()) {
            return "location-form"; // Devuelve el formulario para mostrar los errores de validación
        }

        locationDAO.updateLocation(location); // Actualizar la ubicación
        logger.info("Ubicación con ID {} actualizada con éxito.", location.getId());
        return "redirect:/locations"; // Redirigir a la lista de ubicaciones
    }

    /**
     * Elimina una ubicación de la base de datos.
     *
     * @param id                   ID de la ubicación a eliminar.
     * @param redirectAttributes    Atributos para mensajes flash de redirección.
     * @return Redirección a la lista de ubicaciones.
     */
    @PostMapping("/delete")
    public String deleteLocation(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        logger.info("Eliminando ubicación con ID {}", id);
        locationDAO.deleteLocation(id); // Eliminar la ubicación
        logger.info("Ubicación con ID {} eliminada con éxito.", id);
        return "redirect:/locations"; // Redirigir a la lista de ubicaciones
    }
}
