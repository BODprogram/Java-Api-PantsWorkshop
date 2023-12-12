package ru.sfedu.pantsworkshop.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.pantsworkshop.TestingClient;
import ru.sfedu.pantsworkshop.beans.CommonOfferJeans;
import ru.sfedu.pantsworkshop.beans.CustomOfferJeans;
import ru.sfedu.pantsworkshop.utils.TestData;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import org.junit.Test;


abstract class DataProviderTestsAbstractNegative {
    protected IDataProvider DataBasePantsworkshop;

    public static class StringForTests { //строка требуемая для изменений в лямба функциях.
        public static String actual = "";
    }

    private static final Logger log = LogManager.getLogger(TestingClient.class.getName());

    public void CreateTestTables() throws IOException {
    }

    public void DeleteTestTables() throws IOException {
    }

    @Test
    public void GetDetailedCheckTestNegative() throws IOException {
        DeleteTestTables();
        assertEquals("select empty Error", DataBasePantsworkshop.getDetailedCheck().getMessage());
        CreateTestTables();
    }

    @Test
    public void SpecialOrderTestNegative() throws IOException {
        CreateTestTables();
        assertEquals("jeans model incorrect", DataBasePantsworkshop.specialOrder(new CommonOfferJeans()).getMessage());
    }

    @Test
    public void GetProductionTimeTestNegative() throws IOException {
        CreateTestTables();
        assertEquals("jeans model incorrect", DataBasePantsworkshop.getProductionTime(new CustomOfferJeans()).getMessage());
        //assertEquals(TestData.customOfferJeans1.getSewingTime(),DataBasePantsworkshop.getProductionTime(TestData.customOfferJeans1).getObject());
    }

    @Test
    public void GetComponentPriceTestNegative() throws IOException {
        CreateTestTables();
        assertEquals("worker access required", DataBasePantsworkshop.getComponentPrice(TestData.User2, false).getMessage());
    }

    @Test
    public void GetProfitTestNegative() throws IOException {
        CreateTestTables();
        assertEquals("worker access required", DataBasePantsworkshop.getProfit(TestData.User2, false).getMessage());
    }

    @Test
    public void CalculateJeansCostTestNegative() throws IOException {
        CreateTestTables();
        assertEquals("jeans model incorrect", DataBasePantsworkshop.calculateJeansCost(new CustomOfferJeans()).getMessage());
    }

    @Test
    public void GetComponentCostTestNegative() throws IOException {
        CreateTestTables();
        assertEquals("jeans model incorrect", DataBasePantsworkshop.getComponentCost(new CustomOfferJeans()).getMessage());
    }

    @Test
    public void SummariseAllProfitTestNegative() throws IOException {
        CreateTestTables();
        assertEquals("worker access required", DataBasePantsworkshop.summariseAllProfit(TestData.User2).getMessage());
    }
}
