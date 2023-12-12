package ru.sfedu.pantsworkshop.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.pantsworkshop.Constants;
import ru.sfedu.pantsworkshop.TestingClient;
import ru.sfedu.pantsworkshop.beans.CommonOfferJeans;
import ru.sfedu.pantsworkshop.beans.Components;
import ru.sfedu.pantsworkshop.beans.CustomOfferJeans;
import ru.sfedu.pantsworkshop.beans.User;
import ru.sfedu.pantsworkshop.utils.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataProviderCsv implements IDataProvider {
    private static final Logger log = LogManager.getLogger(TestingClient.class.getName());

    private static <T> HistoryContent createHistoryContent(String className, String methodName, T object, Status status) {
        return new HistoryContent(new Date().getTime(), className, new Date().toString(), Constants.DEFAULT_ACTOR, methodName, object, status);
    }


    /**
     * @return
     */
 public DataOfResult<List<Components>> getAllComponents() {

     return new DataOfResult(getBeanCSV(Components.class).getObject(), Status.SUCCESS, "All components loaded");
    }

    /**
     * @return
     */
    public DataOfResult<List<CustomOfferJeans>> getAllCustomOfferJeans() {
        return new DataOfResult(getBeanCSV(CustomOfferJeans.class).getObject(), Status.SUCCESS, "All CustomOfferJeans loaded");
    }

    /**
     * @return
     */
    public DataOfResult<List<CommonOfferJeans>> getAllCommonOfferJeans() {
        return new DataOfResult(getBeanCSV(CommonOfferJeans.class).getObject(), Status.SUCCESS, "All CommonOfferJeans loaded");
    }


    /**
     *
     */
    protected String fileNamePattern;

    {
        try {
            fileNamePattern = ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH) + Constants.CSV_PATTERN;
            log.debug(ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH));
            //log.info(Constants.CSV_PATTERN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param type
     * @param <T>
     * @return
     */
    protected <T> String getFileName(Class<T> type) {
        //log.debug(String.format(fileNamePattern, type.getSimpleName()));
        return String.format(fileNamePattern, type.getSimpleName());

    }

    /**
     * @param FileName
     * @return
     * @throws IOException
     */
    protected File initFile(String FileName) throws IOException {
        File file = new File(FileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        return file;
    }

    /**
     * @param className
     * @param <T>
     * @return
     */
    public <T> DataOfResult<List<T>> getBeanCSV(Class<T> className) {
        List<T> list = new ArrayList<>();
        try {
            File file = initFile(getFileName(className));
            if (file.length() > 0) {
                CSVReader csvReader = new CSVReader(new FileReader(file));
                CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader).withType(className).build();
                list.addAll(csvToBean.parse());
                csvReader.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new DataOfResult<>(new ArrayList<>(), Status.FAULT, e.getMessage());
        }
        return new DataOfResult<>(list, Status.SUCCESS, "Bean loaded");
    }

    /**
     * @param list
     * @param className
     * @param <T>
     * @return
     */
    public <T> DataOfResult<String> writeBeanToCSV(List<T> list, Class<T> className) {
        String message = "Bean saved";
        try {
            File file = initFile(getFileName(className));
            CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).build();
            beanToCsv.write(list);
            csvWriter.close();

        } catch (Exception e) {
            log.error(e.getMessage());
            return new DataOfResult<>(e.getMessage(), Status.SUCCESS, e.getMessage());
        }
        //Место для монги
        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();//mongodb
        HistoryContent historyContent = createHistoryContent(className.getSimpleName(), methodName,(list.toString()), Status.SUCCESS);
        HistoryUtil.saveHistory(historyContent);
        return new DataOfResult<>(message, Status.SUCCESS, message);
    }


    /**
     * @param user
     * @return
     */
    public DataOfResult<Integer> summariseAllProfit(User user)  //getProfit()..
    //Summarise all profit  //getProfit().
    //Цель: высчитать прибыль на основе стоимости джинс и затрат времени на их производство.
    {//CalculateJeansCost-GetComponentCost
        if (user.getWorkerAcess() == true) {
            var ref = new Object() {
                int price = 0;
                int work = 0;
                int cost = 0;
            };
            getAllCustomOfferJeans().getObject().stream().forEach(x -> {
                log.debug("x " + x.toString());
                ref.price = ref.price + calculateJeansCost(x).getObject();
                ref.work = ref.work + getProductionTime(x).getObject();
                ref.cost = ref.cost + (getComponentCost(x).getObject());
            });
            getAllCommonOfferJeans().getObject().stream().forEach(x -> {
                log.debug("x " + x.toString());
                ref.price = ref.price + calculateJeansCost(x).getObject();
                ref.work = ref.work + Constants.WORK_MODEL_TIME;
                ref.cost = ref.cost + Constants.MODEL_PRICE;
            });
            ref.work = ref.work * Constants.WorkHourPrice;
            log.info(" price " + ref.price + " work " + ref.work + " cost " + ref.cost);
            int data = ref.price - ref.work - ref.cost;
            return new DataOfResult<>(data, Status.SUCCESS, "Profit");
        }
        return new DataOfResult<>(0, Status.FAULT, "worker access required");
    }

    /**
     * @param list
     * @param className
     * @param element
     * @param <T>
     * @return
     */
    public <T> DataOfResult<String> updateBeanToCSV(List<T> list, Class<T> className, T element) {
        list.add(element);
        return writeBeanToCSV(list, className);
    }

    /**
     * @param className
     * @param <T>
     * @return
     */
    public <T> DataOfResult<String> DeleteBeanCSV(Class<T> className) {
        List<T> list = new ArrayList<>();
        return writeBeanToCSV(list, className);
    }

}
