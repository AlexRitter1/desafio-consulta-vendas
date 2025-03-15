package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.ReportResponseDTO;
import com.devsuperior.dsmeta.dto.SumaryResponseDTO;
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

	public List<SumaryResponseDTO> getSummary(String minDate, String maxDate) {
		LocalDate dateMin = parseDate(minDate, LocalDate.now().minusYears(1));
		LocalDate dateMax = parseDate(maxDate, LocalDate.now());

		validateDates(dateMin, dateMax);

		List<Object[]> results = repository.getSummary(dateMin, dateMax);

		return results.stream()
				.map(row -> new SumaryResponseDTO(
						(String) row[0],         // sellerName
						((Number) row[1]).doubleValue() // total
				))
				.collect(Collectors.toList());
	}

	private LocalDate parseDate(String dateStr, LocalDate defaultDate) {
		return dateStr.isEmpty() ? defaultDate : LocalDate.parse(dateStr);
	}

	private void validateDates(LocalDate start, LocalDate end) {
		if (start.isAfter(end)) {
			throw new IllegalArgumentException("Data inicial maior que final");
		}
	}

}