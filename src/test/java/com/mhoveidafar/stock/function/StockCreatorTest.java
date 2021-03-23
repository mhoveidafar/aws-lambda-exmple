package com.mhoveidafar.stock.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhoveidafar.stock.domain.Stock;
import com.mhoveidafar.stock.repository.StockInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockCreatorTest {

    // to convert object to String and vice versa, should use ObjectMapper
    ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private Context context;

    // should mock it, otherwise the test will fail because they want to connect to AWS table
    @Mock
    private StockInfoRepository mockedStockInfoRepository;

    @Test
    public void handleRequestTest() throws IOException {

        StockCreator stockCreator = new StockCreator(mockedStockInfoRepository);

        APIGatewayProxyRequestEvent input = new APIGatewayProxyRequestEvent();

        Stock stock = Stock.builder()
                .companyName("Apple")
                .marketCapitalization(1130000000000l) // put l at the end to make it long
                .employeesNumber(137000)
                .priceEarningRatio(20.41)
                .dividendYieldPercentage(1.19)
                .build();

        // convert class to String because setBody accepts String
        String body = objectMapper.writeValueAsString(stock);

        input.setBody(body);

        // should mock all transactions with AWS DB in the main class here in the test,
        // otherwise it throws nullPointer Exception and test will fail
        when(mockedStockInfoRepository.saveToDB(stock)).thenReturn(body);

        APIGatewayProxyResponseEvent response = stockCreator.handleRequest(input, context);

        // for assertion needs to convert String to Stock class
        // converting String to a class
        Stock actualResult = objectMapper.readValue(response.getBody(), Stock.class);

        assertEquals("Apple", actualResult.getCompanyName());
        assertEquals(137000, actualResult.getEmployeesNumber());
        // like assertEqual but better - dependency added in build.gradle
        assertThat(response.getStatusCode()).isEqualTo(200);
    }
}