package ru.sfedu.pantsworkshop.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.pantsworkshop.Constants;
import ru.sfedu.pantsworkshop.TestingClient;
import ru.sfedu.pantsworkshop.beans.*;
import ru.sfedu.pantsworkshop.beans.Components;
import ru.sfedu.pantsworkshop.beans.CustomOfferJeans;
import ru.sfedu.pantsworkshop.beans.User;
import ru.sfedu.pantsworkshop.utils.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ru.sfedu.pantsworkshop.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderXML implements IDataProvider {
    private static final Logger log = LogManager.getLogger(TestingClient.class.getName());

    private static <T> HistoryContent createHistoryContent(String className, String methodName, T object, Status status) {
        return new HistoryContent(new Date().getTime(), className, new Date().toString(), Constants.DEFAULT_ACTOR, methodName, object, status);
    }

    /**
     * @param tClass
     * @param <T>
     * @return
     */
    private static <T> String getPath(Class<T> tClass) {
        String path = switch (tClass.getSimpleName()) {
            case "Jeans" -> Constants.JEANS_XML;
            case "User" -> Constants.USER_XML;
            case "CommonOfferJeans" -> Constants.COMMONOFFERJEANS_XML;
            case "CustomOfferJeans" -> Constants.CUSTOMOFFERJEANS_XML;
            case "Components" -> Constants.COMPONENTS_XML;
            default -> "";
        };
        return path;
    }


    /**
     * @return
     */
    public DataOfResult<List<Components>> selectComponents() {
        List<Components> loaded;
        try {
            Serializer serializer = new Persister();
            FileReader file = new FileReader(getConfigurationEntry(Constants.COMPONENTS_XML));
            WrapperXML xml = serializer.read(WrapperXML.class, file);
            log.info("xml = " + xml);
            file.close();
            loaded = xml.getContainer();
        } catch (Exception e) {
            e.printStackTrace();
            return new DataOfResult<>(new ArrayList<>(), Status.FAULT, e.getMessage());
        }
        log.info("loaded = " + loaded);
        return new DataOfResult<>(loaded, Status.SUCCESS, "All components loaded");

    }

    /**
     * @param id
     * @return
     */
    public Components getById(long id) {
        if (selectComponents() != null) {
            Components list = selectComponents().getObject().stream()
                    .filter(x -> (x).getId() == (id))
                    .findFirst().orElse(null);
            log.info(list);
            return list;
        }
        return null;
    }

    /**
     * @param сomponents
     * @return
     * @throws IOException
     */
    public DataOfResult<String> insertComponents(List<Components> сomponents) throws IOException {
        Serializer serializer = new Persister();
        WrapperXML wrap = new WrapperXML<>();
        wrap.setContainer(сomponents);
        FileWriter result = new FileWriter(getConfigurationEntry(Constants.COMPONENTS_XML));
        String message = "All components saved";
        //log.info(result.toString());
        WrapperXML<Components> xml = new WrapperXML<>();
        try {
            serializer.write(wrap, result);
            try {//log.info("no error");
                result.close();
            } catch (IOException e) {
                //log.info("error");
                e.printStackTrace();
                message = e.getMessage();
                return new DataOfResult<>(message, Status.FAULT, message);
            }
            log.info("writer is working");
        } catch (Exception e) {
            e.printStackTrace();
            return new DataOfResult<>(e.getMessage(), Status.FAULT, e.getMessage());
        }
        return new DataOfResult<>(message, Status.SUCCESS, message);

    }

    /**
     * @return
     */
    public DataOfResult<String> deleteComponents() {
        List<Components> empty = new ArrayList();
        try {
            insertComponents(empty);
        } catch (IOException e) {
            e.printStackTrace();
            return new DataOfResult<>("delete not execute", Status.FAULT, e.getMessage());
        }
        return new DataOfResult<>("All components delete", Status.SUCCESS, "All components delete");
    }

    /**
     * @param сomponents
     * @return
     */
    public DataOfResult<List<Components>> updateComponents(List<Components> сomponents) {
        List<Components> list = selectComponents().getObject();
        list.addAll(сomponents);
        try {
            insertComponents(list);
        } catch (IOException e) {
            e.printStackTrace();
            return new DataOfResult<>(list, Status.FAULT, e.getMessage());
        }
        return new DataOfResult<>(list, Status.SUCCESS, "All components update");
    }

    /**
     * @return
     */
    public DataOfResult<List<CommonOfferJeans>> selectCommonOfferJeans() {
        List<CommonOfferJeans> loaded;
        try {
            Serializer serializer = new Persister();
            FileReader file = new FileReader(getConfigurationEntry(Constants.COMMONOFFERJEANS_XML));
            WrapperXML xml = serializer.read(WrapperXML.class, file);
            log.info("xml = " + xml);
            file.close();
            loaded = xml.getContainer();
        } catch (Exception e) {
            e.printStackTrace();
            return new DataOfResult<>(new ArrayList<>(), Status.FAULT, e.getMessage());
        }
        log.info("loaded = " + loaded);
        return new DataOfResult<>(loaded, Status.SUCCESS, "All CommonOfferJeans loaded");
    }

    /**
     * @return
     */
    public DataOfResult<List<CustomOfferJeans>> selectCustomOfferJeans() {
        List<CustomOfferJeans> loaded;
        try {
            Serializer serializer = new Persister();
            FileReader file = new FileReader(getConfigurationEntry(Constants.CUSTOMOFFERJEANS_XML));
            WrapperXML xml = serializer.read(WrapperXML.class, file);
            log.info("xml = " + xml);
            file.close();
            loaded = xml.getContainer();
        } catch (Exception e) {
            e.printStackTrace();
            return new DataOfResult<>(new ArrayList<>(), Status.FAULT, e.getMessage());
        }
        log.info("loaded = " + loaded);
        return new DataOfResult<>(loaded, Status.SUCCESS, "All CustomOfferJeans loaded");
    }

    /**
     * @return
     */
    public DataOfResult<List<Jeans>> selectJeans() {
        List<Jeans> loaded;
        try {
            Serializer serializer = new Persister();
            FileReader file = new FileReader(getConfigurationEntry(Constants.JEANS_XML));
            WrapperXML xml = serializer.read(WrapperXML.class, file);
            log.info("xml = " + xml);
            file.close();
            loaded = xml.getContainer();
        } catch (Exception e) {
            e.printStackTrace();
            return new DataOfResult<>(new ArrayList<>(), Status.FAULT, e.getMessage());
        }
        log.info("loaded = " + loaded);
        return new DataOfResult<>(loaded, Status.SUCCESS, "All Jeans loaded");
    }

    /**
     * @return
     */
    public DataOfResult<List<User>> selectUser() {
        List<User> loaded;
        try {
            Serializer serializer = new Persister();
            FileReader file = new FileReader(getConfigurationEntry(Constants.USER_XML));
            WrapperXML xml = serializer.read(WrapperXML.class, file);
            log.info("xml = " + xml);
            file.close();
            loaded = xml.getContainer();
        } catch (Exception e) {
            e.printStackTrace();
            return new DataOfResult<>(new ArrayList<>(), Status.FAULT, e.getMessage());
        }
        log.info("loaded = " + loaded);
        return new DataOfResult<>(loaded, Status.SUCCESS, "All User loaded");
    }

    /**
     * @param commonOfferJeans
     * @return
     * @throws IOException
     */
    public DataOfResult<String> insertCommonOfferJeans(List<CommonOfferJeans> commonOfferJeans) throws IOException {
        Serializer serializer = new Persister();
        WrapperXML wrap = new WrapperXML<>();
        wrap.setContainer(commonOfferJeans);
        FileWriter result = new FileWriter(getConfigurationEntry(Constants.COMMONOFFERJEANS_XML));
        String message = "All CommonOfferJeans saved";
        // log.info(result);
        WrapperXML<CommonOfferJeans> xml = new WrapperXML<>();
        try {
            serializer.write(wrap, result);
            try {
                result.close();
            } catch (IOException e) {
                e.printStackTrace();
                message = e.getMessage();
                return new DataOfResult<>(message, Status.FAULT, message);
            }
            log.info("writer is working");
        } catch (Exception e) {
            e.printStackTrace();
            return new DataOfResult<>(e.getMessage(), Status.FAULT, e.getMessage());
        }
        return new DataOfResult<>(message, Status.SUCCESS, message);
    }

    /**
     * @param customOfferJeans
     * @return
     * @throws IOException
     */
    public DataOfResult<String> insertCustomOfferJeans(List<CustomOfferJeans> customOfferJeans) throws IOException {
        Serializer serializer = new Persister();
        WrapperXML wrap = new WrapperXML<>();
        wrap.setContainer(customOfferJeans);
        FileWriter result = new FileWriter(getConfigurationEntry(Constants.CUSTOMOFFERJEANS_XML));
        String message = "All CustomOfferJeans saved";
        //log.info(result);
        WrapperXML<CommonOfferJeans> xml = new WrapperXML<>();
        try {
            serializer.write(wrap, result);
            try {
                result.close();
            } catch (IOException e) {
                e.printStackTrace();
                message = e.getMessage();
                return new DataOfResult<>(message, Status.FAULT, message);
            }
            log.info("writer is working");
        } catch (Exception e) {
            e.printStackTrace();
            return new DataOfResult<>(e.getMessage(), Status.FAULT, e.getMessage());
        }
        return new DataOfResult<>(message, Status.SUCCESS, message);
    }


    /**
     * @return
     */
    @Override
    public DataOfResult<List<Components>> getAllComponents() {
        DataOfResult<List<Components>> result = selectComponents();
        if (result.getObject() == null) {
            result.setStatus(Status.FAULT);
            result.setMessage("select empty Error");
        }
        return result;
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
            selectCustomOfferJeans().getObject().stream().forEach(x -> {
                log.debug("x " + x.toString());
                ref.price = ref.price + calculateJeansCost(x).getObject();
                ref.work = ref.work + getProductionTime(x).getObject();
                ref.cost = ref.cost + (getComponentCost(x).getObject());
            });
            selectCommonOfferJeans().getObject().stream().forEach(x -> {
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
     * @param Jeans
     * @return
     * @throws IOException
     */
    public DataOfResult<String> insertJeans(List<Jeans> Jeans) throws IOException {
        Serializer serializer = new Persister();
        WrapperXML wrap = new WrapperXML<>();
        wrap.setContainer(Jeans);
        FileWriter result = new FileWriter(getConfigurationEntry(Constants.JEANS_XML));
        String message = "All Jeans saved";
        // log.info(result);
        WrapperXML<Jeans> xml = new WrapperXML<>();
        try {
            serializer.write(wrap, result);
            try {
                result.close();
            } catch (IOException e) {
                e.printStackTrace();
                message = e.getMessage();
                return new DataOfResult<>(message, Status.FAULT, message);
            }
            log.info("writer is working");
        } catch (Exception e) {
            e.printStackTrace();
            return new DataOfResult<>(e.getMessage(), Status.FAULT, e.getMessage());
        }
        return new DataOfResult<>(message, Status.SUCCESS, message);
    }

    /**
     * @param user
     * @return
     * @throws IOException
     */
    public DataOfResult<String> insertUser(List<User> user) throws IOException {
        Serializer serializer = new Persister();
        WrapperXML wrap = new WrapperXML<>();
        wrap.setContainer(user);
        FileWriter result = new FileWriter(getConfigurationEntry(Constants.USER_XML));
        String message = "All Users saved";
        // log.info(result);
        WrapperXML<User> xml = new WrapperXML<>();
        try {
            serializer.write(wrap, result);
            try {
                result.close();
            } catch (IOException e) {
                e.printStackTrace();
                message = e.getMessage();
                return new DataOfResult<>(message, Status.FAULT, message);
            }
            log.info("writer is working");
        } catch (Exception e) {
            e.printStackTrace();
            return new DataOfResult<>(e.getMessage(), Status.FAULT, e.getMessage());
        }
        return new DataOfResult<>(message, Status.SUCCESS, message);
    }

    /**
     * @param list
     * @param className
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> DataOfResult<String> insertBeanToXML(List<T> list, Class<T> className) throws IOException {
        Serializer serializer = new Persister();
        WrapperXML wrap = new WrapperXML<>();
        wrap.setContainer(list);
        FileWriter result = new FileWriter(getConfigurationEntry(getPath(className)));
        String message = className.toString() + " saved";
        // log.info(result);
        WrapperXML<T> xml = new WrapperXML<>();
        try {
            serializer.write(wrap, result);
            try {
                result.close();
            } catch (IOException e) {
                e.printStackTrace();
                message = e.getMessage();
                return new DataOfResult<>(message, Status.FAULT, message);
            }
            log.info("writer is working");
        } catch (Exception e) {
            e.printStackTrace();
            return new DataOfResult<>(e.getMessage(), Status.FAULT, e.getMessage());
        }

        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();//mongodb
        HistoryContent historyContent = createHistoryContent(className.getSimpleName(), methodName,(list.toString()), Status.SUCCESS);
        HistoryUtil.saveHistory(historyContent);
        return new DataOfResult<>(message, Status.SUCCESS, message);
    }

    /**
     * @param className
     * @param <T>
     * @return
     */
    public <T> DataOfResult<String> deleteBeanXML(Class<T> className) {
        List<T> empty = new ArrayList();
        try {
            insertBeanToXML(empty, className);
        } catch (IOException e) {
            e.printStackTrace();
            return new DataOfResult<>("delete not execute", Status.FAULT, e.getMessage());
        }
        return new DataOfResult<>(className.toString() + " deleted", Status.SUCCESS, className.toString() + " deleted");
    }

    /**
     * @param list
     * @param className
     * @param <T>
     * @return
     */
    public <T> DataOfResult<List<T>> updateBeanXML(List<T> list, Class<T> className) {
        List<T> list2 = selectBeanXML(className).getObject();
        list.addAll(list2);
        try {
            insertBeanToXML(list, className);
        } catch (IOException e) {
            e.printStackTrace();
            return new DataOfResult<>(list, Status.FAULT, e.getMessage());
        }
        return new DataOfResult<>(list, Status.SUCCESS, className.toString() + "updates");
    }

    /**
     * @param className
     * @param <T>
     * @return
     */
    public <T> DataOfResult<List<T>> selectBeanXML(Class<T> className) {
        List<T> loaded;
        try {
            Serializer serializer = new Persister();
            FileReader file = new FileReader(getConfigurationEntry(getPath(className)));
            WrapperXML xml = serializer.read(WrapperXML.class, file);
            log.info("xml = " + xml);
            file.close();
            loaded = xml.getContainer();
        } catch (Exception e) {
            e.printStackTrace();
            return new DataOfResult<>(new ArrayList<>(), Status.FAULT, e.getMessage());
        }
        log.info("loaded = " + loaded);
        return new DataOfResult<>(loaded, Status.SUCCESS, "All " + className.toString() + "loaded");
    }
}

