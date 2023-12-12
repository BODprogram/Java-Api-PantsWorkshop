package ru.sfedu.pantsworkshop.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.pantsworkshop.TestingClient;
import ru.sfedu.pantsworkshop.beans.CommonOfferJeans;
import ru.sfedu.pantsworkshop.beans.Components;
import ru.sfedu.pantsworkshop.beans.CustomOfferJeans;
import ru.sfedu.pantsworkshop.beans.User;
import ru.sfedu.pantsworkshop.utils.TestData;

import java.util.ArrayList;

class DataProviderCsvTest extends DataProviderTestsAbstract {
    DataProviderCsvTest() {
        //this.DataBasePantsworkshop = new DataProviderCsv();
        DataBasePantsworkshop = new DataProviderCsv();
    }

    private static final Logger log = LogManager.getLogger(TestingClient.class.getName());
    DataProviderCsv DataBasePantsworkshop2 = new DataProviderCsv();

    public void CreateTestTables() {
        DataBasePantsworkshop2.writeBeanToCSV(TestData.components, Components.class);
        DataBasePantsworkshop2.writeBeanToCSV(TestData.customOfferJeans, CustomOfferJeans.class);
        DataBasePantsworkshop2.writeBeanToCSV(TestData.Users, User.class);
        DataBasePantsworkshop2.writeBeanToCSV(TestData.commonOfferJeans, CommonOfferJeans.class);
    }

    public void DeleteTestTables() {
        DataBasePantsworkshop2.writeBeanToCSV(new ArrayList(), Components.class);
        DataBasePantsworkshop2.writeBeanToCSV(new ArrayList(), CustomOfferJeans.class);
        DataBasePantsworkshop2.writeBeanToCSV(new ArrayList(), User.class);
        DataBasePantsworkshop2.writeBeanToCSV(new ArrayList(), CommonOfferJeans.class);
    }


}