package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.repositories;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProvinceRepository extends JpaRepository<Province, Long> {

    boolean existsProvinceByCode(String code);


    @Query("SELECT COUNT(p) > 0 FROM Province p WHERE p.code = :code AND p.id != :id")
    boolean existsProvinceByCodeAndNotId(@Param("code") String code, @Param("id") Long id);

    boolean existsByCode(@NotEmpty(message = "{msg.province.code.notEmpty}") @Size(max = 2, message ="{msg.province.code.size}" ) String code);
}
