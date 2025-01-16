package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.repositories;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RegionRepository extends JpaRepository<Region, Long> {


    boolean existsRegionByCode(String code);
    @Query("SELECT COUNT(r) > 0 FROM Region r WHERE r.code = :code AND r.id != :id")
    boolean existsRegionByCodeAndNotId(@Param("code") String code, @Param("id") Long id);


    boolean existsByCode(@NotEmpty(message = "{msg.region.code.notEmpty}") @Size(max = 2, message ="{msg.region.code.size}" ) String code);
}
