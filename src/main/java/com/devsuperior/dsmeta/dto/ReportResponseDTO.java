package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.entities.Seller;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public class ReportResponseDTO {

    private Long id;
    private Double amount;
    private LocalDate date;
    private String sellerName;

    public ReportResponseDTO() {
    }

    public ReportResponseDTO(Long id, Double amount, LocalDate date, String sellerName) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.sellerName = sellerName;
    }

    public ReportResponseDTO(Sale sale) {
        this.id = sale.getId();
        this.amount = sale.getAmount();
        this.date = sale.getDate();
        this.sellerName = sale.getSeller().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
}