package com.bomnara.framework.service.impl;

import com.bomnara.framework.domain.SaleData;
import com.bomnara.framework.repository.SaleDataRepository;
import com.bomnara.framework.service.SaleDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service	
public class SaleDataServiceImpl implements SaleDataService {
	
	private final SaleDataRepository saleDataRepository;

	/**
	 * 토큰 리스트 조회
	 */
	public Page<SaleData> getSaleDataList(String saleYear, Pageable pageable){
		
		
		Page<SaleData> pageSaleData = saleDataRepository.findBySaleYear(saleYear,pageable);

		return pageSaleData;
		
	}

	
}
