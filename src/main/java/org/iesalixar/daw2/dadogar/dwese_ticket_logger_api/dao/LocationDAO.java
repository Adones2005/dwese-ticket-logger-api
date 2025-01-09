package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dao;

import org.iesalixar.daw2.dadogar.dwese_ticket_logger_webapp.entity.Location;

import java.util.List;

public interface LocationDAO {
    List<Location> listAllLocations();

    void insertLocation(Location location);

    void updateLocation(Location location);

    void deleteLocation(int id);

    Location getLocationById(int id);
}
