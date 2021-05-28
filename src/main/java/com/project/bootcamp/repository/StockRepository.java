package com.project.bootcamp.repository;

import com.project.bootcamp.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {


    Optional<Stock> findByNameAndDate(String name, LocalDate date);

    @Query("Select stock from Stock stock where stock.name=:name and stock.date=:date and stock.id <> :id ")
    Optional<Stock> findByStockUpdate(String name, LocalDate date, Long id);

    @Query("Select stock from Stock stock where stock.date=:date ")
    List<Stock> findByToDay(LocalDate date);




}
