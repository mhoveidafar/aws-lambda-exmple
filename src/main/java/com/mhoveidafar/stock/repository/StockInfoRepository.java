package com.mhoveidafar.stock.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.mhoveidafar.stock.domain.Stock;

import lombok.AllArgsConstructor;

import static java.util.UUID.randomUUID;

@AllArgsConstructor
public class StockInfoRepository {

    // add its dependency in build.gradle
    private Table table;

    public StockInfoRepository () {
        this.table = new DynamoDB(AmazonDynamoDBAsyncClientBuilder.defaultClient()).getTable(System.getenv("STOCK_TABLE_NAME"));
    }

    public String saveToDB (Stock stock) {

        // using Item class to create columns of table
        String id = randomUUID().toString();
        Item item = new Item()
                .withPrimaryKey("id", id)
                .withString("name", stock.getCompanyName())
                .withLong("marketCapitalization", stock.getMarketCapitalization())
                .withInt("employee", stock.getEmployeesNumber())
                .withDouble("priceEarningRatio", stock.getPriceEarningRatio())
                .withDouble("dividendYieldPercentage", stock.getDividendYieldPercentage())
                .withLong("date", System.currentTimeMillis());

        table.putItem(item);

        return item.toJSON();
    }
}
