package com.bomnara.framework.repository;

import com.bomnara.framework.domain.SaleData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleDataRepository extends JpaRepository<SaleData, Long> {

    Page<SaleData> findBySaleYear(String saleYear, Pageable pageable);
}