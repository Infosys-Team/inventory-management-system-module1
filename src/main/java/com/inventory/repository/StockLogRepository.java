package com.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.model.StockLog;

public interface StockLogRepository extends JpaRepository<StockLog, Long> {
}
