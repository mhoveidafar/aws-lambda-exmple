package com.mhoveidafar.stock.validation;

import com.mhoveidafar.stock.domain.Stock;
import com.mhoveidafar.stock.exception.ValidationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class StockValidationTest {

    // when want to test if the method throws exception
    @Test(expected = ValidationException.class)
    public void validateTest() {

        StockValidation stockValidation = new StockValidation();

        Stock stock = Stock.builder()
                .companyName("")
                .build();

        stockValidation.validate(stock);
    }
}