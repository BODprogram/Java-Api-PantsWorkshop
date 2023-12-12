package ru.sfedu.pantsworkshop.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.pantsworkshop.TestingClient;
import ru.sfedu.pantsworkshop.utils.TestData;

import java.io.IOException;
import java.util.ArrayList;

class DataProviderXMLTestNegative extends DataProviderTestsAbstractNegative {

    private static final Logger log = LogManager.getLogger(TestingClient.class.getName());

    DataProviderXMLTestNegative() {
        //this.DataBasePantsworkshop = new DataProviderCsv();
        DataBasePantsworkshop = new DataProviderXML();
    }

    DataProviderXML DataBasePantsworkshop2 = new DataProviderXML();

    public void CreateTestTables() throws IOException {
        DataBasePantsworkshop2.insertComponents(TestData.components);
        DataBasePantsworkshop2.insertCustomOfferJeans(TestData.customOfferJeans);
        // DataBasePantsworkshop2.insertCommonOfferJeans(TestData.Users ,);
        DataBasePantsworkshop2.insertCommonOfferJeans(TestData.commonOfferJeans);
    }

    public void DeleteTestTables() throws IOException {
        DataBasePantsworkshop2.insertComponents(new ArrayList());
        DataBasePantsworkshop2.insertCustomOfferJeans(new ArrayList());
        // DataBasePantsworkshop2.insertCommonOfferJeans(new ArrayList());
        DataBasePantsworkshop2.insertCommonOfferJeans(new ArrayList());
    }


}