package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dao;

import org.iesalixar.daw2.dadogar.dwese_ticket_logger_webapp.entity.Supermarket;

import java.util.List;

public interface SupermarketDAO {
    List<Supermarket> listAllSupermarkets();

    void insertSupermarket(Supermarket supermarket);

    void updateSupermarket(Supermarket supermarket);

    void deleteSupermarket(int id);

    Supermarket getSupermarketById(int id);
}
