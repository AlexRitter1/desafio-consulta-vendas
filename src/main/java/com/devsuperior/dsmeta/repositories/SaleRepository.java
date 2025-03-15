package com.devsuperior.dsmeta.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s FROM Sale s " +
            "WHERE " +
            "(:name IS NULL OR LOWER(s.seller.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "s.date BETWEEN :minDate AND :maxDate")
    Page<Sale> findSales(
            @Param("name") String name,
            @Param("minDate") LocalDate minDate,
            @Param("maxDate") LocalDate maxDate,
            Pageable pageable
    );
}