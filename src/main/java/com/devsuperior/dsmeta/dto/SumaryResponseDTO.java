package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;

public class SumaryResponseDTO {

    private String sellerName;
    private Double total;

    public SumaryResponseDTO() {
    }

    public SumaryResponseDTO(String sellerName, Double total) {
        this.sellerName = sellerName;
        this.total = total;
    }

    public SumaryResponseDTO(Sale sale) {
        this.sellerName = sale.getSeller().getName();
        this.total = sale.getAmount();
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
