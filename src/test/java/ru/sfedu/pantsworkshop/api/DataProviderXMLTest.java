package ru.sfedu.pantsworkshop.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.pantsworkshop.TestingClient;
import ru.sfedu.pantsworkshop.utils.TestData;

import java.io.IOException;
import java.util.ArrayList;

class DataProviderXMLTest extends DataProviderTestsAbstract {

    private static final Logger log = LogManager.getLogger(TestingClient.class.getName());

    DataProviderXMLTest() {
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
  /*  @Test
    public void testComponentsSelect() {
        DataProviderXML dpc = new DataProviderXML();
        Components сomponents1 = new Components(1,105,"Denim",10);
        Components сomponents2=dpc.selectComponents().getObject().get(0);
        log.info(сomponents1);
        assertEquals(сomponents1, сomponents2);
    }
   @Test
    public void testInsertComponents() throws IOException {
        List<Components> list = new ArrayList<>();
        DataProviderXML dpc = new DataProviderXML();
        Components сomponents1 = new Components(1,105,"Denim",10);
        log.info("num1");
//        log.info(idtime);
        Components сomponents2 = new Components(2,106,"Denim",11);
        list.add(сomponents1);
        list.add(сomponents2);
        log.info("list =" + list);
        DataProviderXML.insertComponents(list);
        log.info("num1");
        Components listTest = dpc.getById(1);
        assertEquals(сomponents1,listTest);
    }*/


}