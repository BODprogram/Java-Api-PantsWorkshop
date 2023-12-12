package ru.sfedu.pantsworkshop.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.pantsworkshop.Constants;
import ru.sfedu.pantsworkshop.TestingClient;
import ru.sfedu.pantsworkshop.beans.*;
import ru.sfedu.pantsworkshop.utils.TestData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class DataProviderJDBCTest extends DataProviderTestsAbstract {
    DataProviderJDBC DataBasePantsworkshop2 = new DataProviderJDBC();

    DataProviderJDBCTest() {
        //this.DataBasePantsworkshop = new DataProviderCsv();
        DataBasePantsworkshop = new DataProviderJDBC();
    }

    private static final Logger log = LogManager.getLogger(TestingClient.class.getName());

    public void CreateTestTables() {
        DeleteTestTables();
        try {
            DataBasePantsworkshop2.executeSQL(Constants.CREATE_DATABASE);
        } catch (Exception ignored) {
        }
        try {
            DataBasePantsworkshop2.CreateTableForBean(Components.class);
        } catch (Exception ignored) {
        }
        try {
            DataBasePantsworkshop2.CreateTableForBean(CommonOfferJeans.class);
        } catch (Exception ignored) {
        }
        try {
            DataBasePantsworkshop2.CreateTableForBean(CustomOfferJeans.class);
        } catch (Exception ignored) {
        }

        TestData.components.stream().forEach(x -> {
            DataBasePantsworkshop2.insertComponent(x);
            log.info(x + "x");
        });
        TestData.customOfferJeans.stream().forEach(x -> {
            DataBasePantsworkshop2.insertBean(x, CustomOfferJeans.class);
            log.info(x + "x");
        });
        TestData.commonOfferJeans.stream().forEach(x -> {
            DataBasePantsworkshop2.insertBean(x, CommonOfferJeans.class);
            log.info(x + "x");
        });
        log.info("Test DATABASE loaded");
        //return  new DataOfResult<String>("",Status.SUCCESS, "Test DATABASE loaded");

    }

    public void DeleteTestTables() {
        try {
            DataBasePantsworkshop2.deleteBean(Components.class);
        } catch (Exception ignored) {

        }
        try {
            DataBasePantsworkshop2.deleteBean(CustomOfferJeans.class);
        } catch (Exception ignored) {

        }
        try {
            DataBasePantsworkshop2.deleteBean(CommonOfferJeans.class);
        } catch (Exception ignored) {

        }
        //return  new DataOfResult<String>("",Status.SUCCESS, "Test DATABASE loaded");
    }

   /* @Before
    public void initTables(){
        log.info("CreateTestTables");
        DataBasePantsworkshop.CreateTestTables();
        TestData.components
        log.info("CreateTestTables");
    }*/

    @Test
    public void getConnectionTest() throws SQLException {
        try (Connection connection = DataBasePantsworkshop2.getConnection()) {
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

} 