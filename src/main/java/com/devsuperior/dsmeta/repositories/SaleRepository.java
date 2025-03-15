package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SumaryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

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

    @Query(nativeQuery = true, value = """
        SELECT
            SELLER.NAME AS sellerName,
            CAST(SUM(SALES.AMOUNT) AS DOUBLE) AS total
        FROM TB_SALES SALES
        INNER JOIN TB_SELLER SELLER ON SALES.SELLER_ID = SELLER.ID
        WHERE
            (:beginDate IS NULL OR SALES.DATE >= :beginDate) AND
            (:finalDate IS NULL OR SALES.DATE <= :finalDate)
        GROUP BY SELLER.NAME
        """)
    List<Object[]> getSummary(
            @Param("beginDate") LocalDate beginDate,
            @Param("finalDate") LocalDate finalDate
    );
}