package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dao;

import org.iesalixar.daw2.dadogar.dwese_ticket_logger_webapp.entity.Location;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_webapp.entity.Province;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_webapp.entity.Supermarket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementación del DAO para gestionar las operaciones de `Location` en la base de datos.
 */
@Repository
public class LocationDAOImpl implements LocationDAO {

    // Logger para registrar información, advertencias y errores
    private static final Logger logger = LoggerFactory.getLogger(LocationDAOImpl.class);
    private final JdbcTemplate jdbcTemplate; // JdbcTemplate para realizar operaciones de base de datos

    /**
     * Constructor que inicializa el JdbcTemplate.
     *
     * @param jdbcTemplate JdbcTemplate para interactuar con la base de datos.
     */
    public LocationDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Lista todas las ubicaciones desde la base de datos.
     *
     * @return Lista de todas las ubicaciones.
     */
    @Override
    public List<Location> listAllLocations() {
        logger.info("Listing all locations from the database.");
        String sql = "SELECT l.*, p.id AS province_id, p.code AS province_code, p.name AS province_name, " +
                "s.id AS supermarket_id, s.name AS supermarket_name " +
                "FROM locations l " +
                "JOIN provinces p ON l.province_id = p.id " +
                "JOIN supermarkets s ON l.supermarket_id = s.id"; // Obtener ubicaciones junto con provincias y supermercados
        List<Location> locations = jdbcTemplate.query(sql, new LocationRowMapper()); // Ejecutar la consulta y mapear resultados
        logger.info("Retrieved {} locations from the database.", locations.size());
        return locations; // Devolver la lista de ubicaciones
    }

    /**
     * Inserta una nueva ubicación en la base de datos.
     *
     * @param location Objeto que contiene los datos de la ubicación a insertar.
     */
    @Override
    public void insertLocation(Location location) {
        logger.info("Inserting location with address: {} and city: {}", location.getAddress(), location.getCity());
        String sql = "INSERT INTO locations (address, city, province_id, supermarket_id) VALUES (?, ?, ?, ?)"; // Consulta para insertar
        int rowsAffected = jdbcTemplate.update(sql, location.getAddress(), location.getCity(),
                location.getProvince().getId(), location.getSupermarket().getId()); // Ejecutar la inserción
        logger.info("Inserted location. Rows affected: {}", rowsAffected);
    }

    /**
     * Actualiza una ubicación existente en la base de datos.
     *
     * @param location Objeto que contiene los datos de la ubicación a actualizar.
     */
    @Override
    public void updateLocation(Location location) {
        logger.info("Updating location with id: {}", location.getId());
        String sql = "UPDATE locations SET address = ?, city = ?, province_id = ?, supermarket_id = ? WHERE id = ?"; // Consulta para actualizar
        int rowsAffected = jdbcTemplate.update(sql, location.getAddress(), location.getCity(),
                location.getProvince().getId(), location.getSupermarket().getId(), location.getId()); // Ejecutar la actualización
        logger.info("Updated location. Rows affected: {}", rowsAffected);
    }

    /**
     * Elimina una ubicación de la base de datos.
     *
     * @param id ID de la ubicación a eliminar.
     */
    @Override
    public void deleteLocation(int id) {
        logger.info("Deleting location with id: {}", id);
        String sql = "DELETE FROM locations WHERE id = ?"; // Consulta para eliminar
        int rowsAffected = jdbcTemplate.update(sql, id); // Ejecutar la eliminación
        logger.info("Deleted location. Rows affected: {}", rowsAffected);
    }

    /**
     * Recupera una ubicación por su ID.
     *
     * @param id ID de la ubicación a recuperar.
     * @return Objeto `Location` recuperado o null si no se encuentra.
     */
    @Override
    public Location getLocationById(int id) {
        logger.info("Retrieving location by id: {}", id);
        String sql = "SELECT l.*, p.id AS province_id, p.code AS province_code, p.name AS province_name, " +
                "s.id AS supermarket_id, s.name AS supermarket_name " +
                "FROM locations l " +
                "JOIN provinces p ON l.province_id = p.id " +
                "JOIN supermarkets s ON l.supermarket_id = s.id " +
                "WHERE l.id = ?"; // Consulta para recuperar una ubicación por ID
        try {
            Location location = jdbcTemplate.queryForObject(sql, new LocationRowMapper(), id); // Ejecutar la consulta y mapear resultados
            logger.info("Location retrieved: {} - {}", location.getAddress(), location.getCity());
            return location; // Devolver la ubicación recuperada
        } catch (Exception e) {
            logger.warn("No location found with id: {}", id);
            return null; // Retornar null si no se encuentra la ubicación
        }
    }


    /**
     * Mapeo de resultados de la base de datos a la entidad `Location`.
     */
    private static class LocationRowMapper implements RowMapper<Location> {
        @Override
        public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
            Location location = new Location(); // Crear nuevo objeto Location
            location.setId(rs.getInt("id")); // Establecer ID
            location.setAddress(rs.getString("address")); // Establecer dirección
            location.setCity(rs.getString("city")); // Establecer ciudad

            // Mapeo de Province
            Province province = new Province();
            province.setId(rs.getInt("province_id")); // Establecer ID de provincia
            province.setCode(rs.getString("province_code")); // Establecer código de provincia
            province.setName(rs.getString("province_name")); // Establecer nombre de provincia
            location.setProvince(province); // Establecer la provincia en la ubicación

            // Mapeo de Supermercado
            Supermarket supermarket = new Supermarket();
            supermarket.setId(rs.getInt("supermarket_id")); // Establecer ID de supermercado
            supermarket.setName(rs.getString("supermarket_name")); // Establecer nombre de supermercado
            location.setSupermarket(supermarket); // Establecer el supermercado en la ubicación

            return location; // Retornar la ubicación mapeada
        }
    }
}
