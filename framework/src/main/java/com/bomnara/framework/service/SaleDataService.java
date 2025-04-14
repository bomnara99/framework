package com.bomnara.framework.service;

import com.bomnara.framework.domain.SaleData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SaleDataService {
	
	Page<SaleData> getSaleDataList(String saleYear,Pageable pageable);
}
