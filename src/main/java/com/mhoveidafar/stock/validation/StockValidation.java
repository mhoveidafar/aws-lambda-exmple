package com.mhoveidafar.stock.validation;

import com.mhoveidafar.stock.domain.Stock;
import com.mhoveidafar.stock.exception.ValidationException;

public class StockValidation {

    public void validate (Stock stock) {

        // null checks if the whole JSON line is missing in request body, is empty checks empty String
        // can do validation for other columns too
        if (stock.getCompanyName() == null || stock.getCompanyName().trim().isEmpty()) {

            throw new ValidationException("company name cannot be empty");
        }
    }
}
