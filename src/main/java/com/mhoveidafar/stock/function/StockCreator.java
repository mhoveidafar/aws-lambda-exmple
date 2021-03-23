package com.mhoveidafar.stock.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhoveidafar.stock.domain.Stock;
import com.mhoveidafar.stock.exception.ValidationException;
import com.mhoveidafar.stock.repository.StockInfoRepository;
import com.mhoveidafar.stock.validation.StockValidation;
import lombok.AllArgsConstructor;

import java.io.IOException;

//implements RequestHandler from lambda runtime, then give type in <>
@AllArgsConstructor
public class StockCreator implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private StockInfoRepository stockInfo;

    // to convert object to String and vice versa, should use ObjectMapper
    // make it final to avoid the use of it in the test
    private final  ObjectMapper objectMapper = new ObjectMapper();

    public StockCreator() {
        this.stockInfo = new StockInfoRepository();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {

        // below cannot be used because type of input is String,
        // but input parameter of saveToDB is type of Stock class, so we use StockMapper
        // stockInfo.saveToDB(input.getBody());

        String savedStock = "";

        try {
            // converting String to a class
            Stock stock = objectMapper.readValue(input.getBody(), Stock.class);

            // check if user input the company name using StockValidation which is linked to ValidationException
            StockValidation stockValidation = new StockValidation();
            stockValidation.validate(stock);

            savedStock = stockInfo.saveToDB(stock);

        } catch (ValidationException e) {
            return createResponse(e.getReason(), e.getStatus());
        } catch (IOException e) {
            e.printStackTrace();
            return createResponse("Unexpected error", 500);
        }

        return createResponse(savedStock, 200);
    }

    // at first content of this method was in above method
    //but because we want to use it in 3 places in above method, we extract it as a method
    public APIGatewayProxyResponseEvent createResponse(String body, int statusCode) {

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setBody(body);
        response.setStatusCode(statusCode);

        return response;
    }
}
