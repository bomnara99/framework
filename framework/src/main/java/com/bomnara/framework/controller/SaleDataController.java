package com.bomnara.framework.controller;

import com.bomnara.framework.domain.SaleData;
import com.bomnara.framework.service.SaleDataService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Sale data
 */
@Log4j2
@RestController
@RequestMapping("/api/saleData")
public class SaleDataController {
	
	@Autowired
	private SaleDataService saleDataService;
		
	/**
	 * TOKEN 생성
	 * 
	 * @param model
	 * @return
	 */	
	@ResponseBody
	@RequestMapping(value="/getSaleData",method=RequestMethod.POST)
	@Operation(summary = "판매 정보 조회",description = "판매정보 조회")
	public ResponseEntity<Page<SaleData>> getSaleData(@RequestParam(name = "pageNo") int pageNo,
													  @RequestParam(name = "pageSize") int pageSize,
													  @RequestParam(name = "saleYear") String saleYear
													  ) {
		
		log.info("판매 정보 조회 시작");
		Pageable pageable = PageRequest.of(pageNo-1, pageSize, Sort.by("createDate").ascending());
		Page<SaleData> saleDataList  = saleDataService.getSaleDataList(saleYear,pageable);
		
		return ResponseEntity.ok(saleDataList);

	}
	
		
}
