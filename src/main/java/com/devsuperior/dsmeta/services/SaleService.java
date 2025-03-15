package com.devsuperior.dsmeta.services;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import com.devsuperior.dsmeta.controllers.SaleController;
import com.devsuperior.dsmeta.dto.ReportResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<ReportResponseDTO> report(String name, String minDate, String maxDate, Pageable pageable) {

		LocalDate dateMax = !maxDate.isEmpty()
				? LocalDate.parse(maxDate)
				: LocalDate.now();

		LocalDate dateMin = !minDate.isEmpty()
				? LocalDate.parse(minDate)
				: dateMax.minusYears(1L);

		Page<Sale> salesPage = repository.findSales(name, dateMin, dateMax, pageable);

		return salesPage.map(ReportResponseDTO::new);
	}

}