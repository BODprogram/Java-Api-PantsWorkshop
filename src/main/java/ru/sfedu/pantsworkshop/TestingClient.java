package ru.sfedu.pantsworkshop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.pantsworkshop.api.DataProviderCsv;
import ru.sfedu.pantsworkshop.api.DataProviderJDBC;
import ru.sfedu.pantsworkshop.api.DataProviderXML;
import ru.sfedu.pantsworkshop.api.IDataProvider;
import ru.sfedu.pantsworkshop.beans.*;
import ru.sfedu.pantsworkshop.utils.DataOfResult;
import ru.sfedu.pantsworkshop.utils.Status;
import ru.sfedu.pantsworkshop.utils.TestData;

import java.util.Arrays;
import java.util.List;

public class TestingClient {
    private static final Logger log = LogManager.getLogger(TestingClient.class.getName());


    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        logBasicSystemInfo();
        try {
            List<String> listArgs = Arrays.asList(args);
            if (listArgs.size() == 0) {
                log.error("Empty input.");
            } else {
                int id = -1;
                if (listArgs.contains("CSV")){
                    id = listArgs.indexOf("CSV");
                } else if (listArgs.contains("XML")){
                    id = listArgs.indexOf("XML");
                } if (listArgs.contains("JDBC")){
                    id = listArgs.indexOf("JDBC");
                }
                if (id != -1) {
                    listArgs = Arrays.asList(args).subList(id, Arrays.asList(args).size());
                }
                IDataProvider provider = getDataProvider(listArgs.get(0));
                if (provider != null){
                    Status status = ClientRun(provider, listArgs);
                    if (status.equals(Status.FAULT)) log.error("Run error");
                } else{
                    log.error("Wrong data provider.");
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
    }
    private static IDataProvider getDataProvider(String nameProvider) {
        IDataProvider dataProvider = switch (nameProvider) {
            case "CSV" -> new DataProviderCsv();
            case "XML" -> new DataProviderXML();
            case "JDBC" -> new DataProviderJDBC();
            default -> null;
        };
        return dataProvider;}

    public TestingClient() {
        log.debug("pantsworkshop.TestingClient: starting application.........");
    }

    private static void logBasicSystemInfo() throws Exception {
        log.info("Launching the application...");
        log.info(
                "Operating System: " + System.getProperty("os.name") + " "
                        + System.getProperty("os.version")
        );
        log.info("JRE: " + System.getProperty("java.version"));
        log.info("Java Launched From: " + System.getProperty("java.home"));
        log.info("Class Path: " + System.getProperty("java.class.path"));
        log.info("Library Path: " + System.getProperty("java.library.path"));
        log.info("User Home Directory: " + System.getProperty("user.home"));
        log.info("User Working Directory: " + System.getProperty("user.dir"));
        log.warn("Test INFO logging.");
    }

    public static Status ClientRun(IDataProvider provider, List<String> args) {
        switch (args.get(1)) {
            case "getDetailedCheck" : {
                try {
                    DataOfResult<List<Components>> result = provider.getDetailedCheck();
                    log.info(result.getStatus());
                    log.info(result.getMessage());
                    return result.getStatus();
                } catch (Exception e){
                    log.error(e);
                    return Status.FAULT;
                }
            }
            case "specialOrder" : {
                try {
                    CommonOfferJeans jeans=new CommonOfferJeans();
                    jeans.setSize(Math.toIntExact(Long.parseLong(args.get(2))));
                    jeans.setId(Long.parseLong(args.get(3)));
                    jeans.setPrice(Math.toIntExact(Long.parseLong(args.get(4))));
                    jeans.setModelName(args.get(5));
                    DataOfResult<Integer> result = provider.specialOrder(jeans);
                    log.info(result.getStatus());
                    log.info(result.getMessage());
                    log.info(result.getObject());
                    return result.getStatus();
                } catch (Exception e){
                    log.error(e);
                    return Status.FAULT;
                }
            }
            case "getProductionTime" : {
                try {
                    CustomOfferJeans jeans=new CustomOfferJeans();
                    jeans.setSize(Math.toIntExact(Long.parseLong(args.get(2))));
                    jeans.setId(Long.parseLong(args.get(3)));
                    jeans.setPrice(Math.toIntExact(Long.parseLong(args.get(4))));
                    jeans.setColor(args.get(5));
                    jeans.setFormat(args.get(6));
                    jeans.setStripe(args.get(7));
                    jeans.setFabric(args.get(8));
                    jeans.setSewingTime(Math.toIntExact(Long.parseLong(args.get(9))));
                    DataOfResult<Integer> result = provider.getProductionTime(jeans);
                    log.info(result.getStatus());
                    log.info(result.getMessage());
                    log.info(result.getObject());
                    return result.getStatus();
                } catch (Exception e){
                    log.error(e);
                    return Status.FAULT;
                }
            }
            case "getComponentPrice" : {
                try {
                    DataOfResult<Integer> result = provider.getComponentPrice(TestData.User1, false);
                    log.info(result.getStatus());
                    log.info(result.getMessage());
                    log.info(result.getObject());
                    return result.getStatus();
                } catch (Exception e){
                    log.error(e);
                    return Status.FAULT;
                }
            }
            case "getProfit" : {
                try {
                    DataOfResult<Integer> result = provider.getProfit(TestData.User1, false);
                    log.info(result.getStatus());
                    log.info(result.getMessage());
                    log.info(result.getObject());
                    return result.getStatus();
                } catch (Exception e){
                    log.error(e);
                    return Status.FAULT;
                }
            }
            case "calculateJeansCost" : {
                try {
                    CustomOfferJeans jeans=new CustomOfferJeans();
                    jeans.setSize(Math.toIntExact(Long.parseLong(args.get(2))));
                    jeans.setId(Long.parseLong(args.get(3)));
                    jeans.setPrice(Math.toIntExact(Long.parseLong(args.get(4))));
                    jeans.setColor(args.get(5));
                    jeans.setFormat(args.get(6));
                    jeans.setStripe(args.get(7));
                    jeans.setFabric(args.get(8));
                    jeans.setSewingTime(Math.toIntExact(Long.parseLong(args.get(9))));
                    DataOfResult<Integer> result = provider.calculateJeansCost(jeans);
                    log.info(result.getStatus());
                    log.info(result.getMessage());
                    log.info(result.getObject());
                    return result.getStatus();
                } catch (Exception e){
                    log.error(e);
                    return Status.FAULT;
                }
            }
            case "getComponentCost" : {
                try {
                    CustomOfferJeans jeans=new CustomOfferJeans();
                    jeans.setSize(Math.toIntExact(Long.parseLong(args.get(2))));
                    jeans.setId(Long.parseLong(args.get(3)));
                    jeans.setPrice(Math.toIntExact(Long.parseLong(args.get(4))));
                    jeans.setColor(args.get(5));
                    jeans.setFormat(args.get(6));
                    jeans.setStripe(args.get(7));
                    jeans.setFabric(args.get(8));
                    jeans.setSewingTime(Math.toIntExact(Long.parseLong(args.get(9))));
                    DataOfResult<Integer> result = provider.getComponentCost(jeans);
                    log.info(result.getStatus());
                    log.info(result.getMessage());
                    log.info(result.getObject());
                    return result.getStatus();
                } catch (Exception e){
                    log.error(e);
                    return Status.FAULT;
                }
            }
            case "summariseAllProfit" : {
                try {
                    DataOfResult<Integer> result = provider.summariseAllProfit(TestData.User1);
                    log.info(result.getStatus());
                    log.info(result.getMessage());
                    log.info(result.getObject());
                    return result.getStatus();
                } catch (Exception e){
                    log.error(e);
                    return Status.FAULT;
                }
            }

            default: return Status.FAULT;
        }
    }

}
