package ru.sfedu.pantsworkshop.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.pantsworkshop.TestingClient;
import ru.sfedu.pantsworkshop.utils.TestData;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class DataProviderTestsAbstract {
    IDataProvider DataBasePantsworkshop;

    public static class StringForTests { //строка требуемая для изменений в лямба функциях.
        public static String actual = "";
    }

    private static final Logger log = LogManager.getLogger(TestingClient.class.getName());

    public void CreateTestTables() throws IOException {
    }

    public void DeleteTestTables() throws IOException {
    }


    @Test
    public void GetDetailedCheckTest() throws IOException {
        CreateTestTables();
        assertEquals(TestData.components, DataBasePantsworkshop.getDetailedCheck().getObject());
    }

    @Test
    public void SpecialOrderTest() throws IOException {
        CreateTestTables();
        assertEquals(TestData.commonOfferJeans1.getPrice(), DataBasePantsworkshop.specialOrder(TestData.commonOfferJeans1).getObject());
    }

    @Test
    public void GetProductionTimeTest() throws IOException {
        CreateTestTables();
        assertEquals(TestData.customOfferJeans1.getSewingTime(), DataBasePantsworkshop.getProductionTime(TestData.customOfferJeans1).getObject());
    }

    @Test
    public void GetComponentPriceTest() throws IOException {
        CreateTestTables();
        assertEquals(262, DataBasePantsworkshop.getComponentPrice(TestData.User1, false).getObject());
    }

    @Test
    public void GetProfitTest() throws IOException {
        CreateTestTables();
        assertEquals(3525, DataBasePantsworkshop.getProfit(TestData.User1, false).getObject());
    }

    @Test
    public void CalculateJeansCostTest() throws IOException {
        CreateTestTables();
        assertEquals(4888, DataBasePantsworkshop.calculateJeansCost(TestData.customOfferJeans1).getObject());
    }

    @Test
    public void GetComponentCostTest() throws IOException {
        CreateTestTables();
        assertEquals(44, DataBasePantsworkshop.getComponentCost(TestData.customOfferJeans1).getObject());
    }

    @Test
    public void SummariseAllProfitTest() throws IOException {
        CreateTestTables();
        assertEquals(3787, DataBasePantsworkshop.summariseAllProfit(TestData.User1).getObject());
    }
}