package com.project.bootcamp.service;

import com.project.bootcamp.exceptions.BusinessException;
import com.project.bootcamp.exceptions.NotFoundException;
import com.project.bootcamp.mapper.StockMapper;
import com.project.bootcamp.model.Stock;
import com.project.bootcamp.model.dto.StockDTO;
import com.project.bootcamp.repository.StockRepository;
import com.project.bootcamp.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.List;
import java.time.LocalDate;

@Service
public class StockService {

    @Autowired
    private StockRepository repository;

    @Autowired
    private StockMapper mapper;

    @Transactional
    public StockDTO save(StockDTO dto) {
        Optional<Stock> optionalStock = repository.findByNameAndDate(dto.getName(), dto.getDate());
        if (optionalStock.isPresent()) {
          throw new BusinessException(MessageUtils.STOCK_ALREADY_EXISTS);
        }

        Stock stock = mapper.toEntity(dto);
        repository.save(stock);
        return mapper.toDto(stock);
    }

    @Transactional
    public StockDTO update(StockDTO dto) {
        Optional<Stock> optionalStock = repository.findByStockUpdate(dto.getName(), dto.getDate(), dto.getId());
        if (optionalStock.isPresent()) {
            throw new BusinessException(MessageUtils.STOCK_ALREADY_EXISTS);
        }

        Stock stock = mapper.toEntity(dto);
        repository.save(stock);
        return mapper.toDto(stock);

    }

    @Transactional
    public List<StockDTO> findAll() {
        List<Stock> list = repository.findAll();
        return mapper.toDto(list);
    }

    @Transactional
    public StockDTO findById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(NotFoundException::new);


    }

    @Transactional
    public StockDTO delete(Long id) {
        StockDTO stockdto = this.findById(id);
        repository.deleteById(stockdto.getId());
        return stockdto;
    }

    @Transactional
    public List<StockDTO> findByToday() {
        List<Stock> listaretorno =  repository.findByToDay(LocalDate.now());
        if (listaretorno.size()==0) {
            throw new BusinessException(MessageUtils.NO_RECORDS_FOUND);
        }
        return mapper.toDto(listaretorno);


    }
}
