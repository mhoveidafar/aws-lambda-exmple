package com.mhoveidafar.stock.repository;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhoveidafar.stock.domain.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class StockInfoRepositoryTest {

    // to convert object to String and vice versa, should use ObjectMapper
    ObjectMapper objectMapper = new ObjectMapper();

    // for mocking the table in AWS - dependency added in build.gradle
    @Mock
    private Table mockedTable;

    @Test
    public void saveToDBTest() throws IOException {

        StockInfoRepository stockInfo = new StockInfoRepository(mockedTable);
        Stock stock = Stock.builder()
                .companyName("Apple")
                .marketCapitalization(1130000000000l) // put l at the end to make it long
                .employeesNumber(137000)
                .priceEarningRatio(20.41)
                .dividendYieldPercentage(1.19)
                .build();

        String actualResult = stockInfo.saveToDB (stock);
        // for assertion needs to convert String to Stock class

        // converting String to a class
        Stock actualResultClass = objectMapper.readValue(actualResult, Stock.class);

        assertEquals("Apple", actualResultClass.getCompanyName());
        assertEquals(137000, actualResultClass.getEmployeesNumber());
    }
}