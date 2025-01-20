package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.repositories;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(@NotEmpty(message = "El nombre no puede estar vacío.") @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.") String name);

    boolean existsByNameAndIdNot(@NotEmpty(message = "El nombre no puede estar vacío.") @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.") String name, Long id);
}
