package ru.sfedu.pantsworkshop.api;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.pantsworkshop.Constants;
import ru.sfedu.pantsworkshop.TestingClient;
import ru.sfedu.pantsworkshop.beans.*;
import ru.sfedu.pantsworkshop.beans.enums.Color;
import ru.sfedu.pantsworkshop.beans.enums.Fabric;
import ru.sfedu.pantsworkshop.beans.enums.OfferJeans;
import ru.sfedu.pantsworkshop.utils.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static ru.sfedu.pantsworkshop.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderJDBC implements IDataProvider {
    private static <T> HistoryContent createHistoryContent(String className, String methodName, T object, Status status) {
        return new HistoryContent(new java.util.Date().getTime(), className, new Date().toString(), Constants.DEFAULT_ACTOR, methodName, object, status);
    }

    private static final Logger log = LogManager.getLogger(TestingClient.class.getName());

    /**
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
        Connection connection;
        Class.forName(getConfigurationEntry(Constants.JDBC_DRIVER));
        connection = DriverManager.getConnection(
                getConfigurationEntry(Constants.DB_CONNECT),
                getConfigurationEntry(Constants.DB_USER),
                getConfigurationEntry(Constants.DB_PASS));
        //executeSQL(Constants.CREATE_COMPONENTS);
        //try {statement.executeUpdate(CREATE_DATABASE_SQL_CONNECTION);} catch (Exception ignored){}

        return connection;
    }


    /**
     * @param id
     * @param numberOfComponents
     * @param сomponentName
     * @param price
     * @return
     */
    public DataOfResult<String> updateComponentsInId(long id, Integer numberOfComponents, String сomponentName, Integer price) {
        String request = (String.format(Constants.UPDATE_COMPONENTS_WHERE_ID, numberOfComponents, сomponentName, price, id));
        DataOfResult<String> answer = (executeSQL(request));
        //место монги
        //final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();//mongodb
        //HistoryContent historyContent = createHistoryContent("Components", methodName, answer.getObject().toString(), Status.SUCCESS);
        //HistoryUtil.saveHistory(historyContent);

        return answer;
    }


    /**
     * @param sql
     * @return
     */
    public DataOfResult<String> executeSQL(String sql) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            int resultSet = statement.executeUpdate();
            getConnection().close();
            String result = "";
            if (resultSet == 1) {
                result = "SQL request accepted";
            }
            if (resultSet == 0) {
                result = "SQL request not accepted";
            }
            log.info(result);
            return new DataOfResult<>(result, Status.SUCCESS, result);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
            return new DataOfResult<>(Status.FAULT, e.getMessage());
        }
    }

    /**
     * @param id
     * @return
     */
    public DataOfResult<String> deleteComponentsById(long id) {
        String request = (String.format(Constants.DELETE_COMPONENTS_ID + id));
        return (executeSQL(request));
    }

    /**
     * @param id
     * @return
     */
    public DataOfResult<Components> getComponentById(long id) {
        String request = String.format(Constants.SELECT_COMPONENTS_ID + id);
        log.info("sqlrequest = " + request);
        DataOfResult<ResultSet> select1 = selectSQL(request);
        ResultSet res = select1.getObject();
        Components receivedData = new Components();
        String Message = select1.getMessage();
        try {
            if (res != null && res.next()) {
                receivedData.setId(res.getLong("id"));
                receivedData.setNumberOfComponents(res.getInt("numberOfComponents"));
                receivedData.setСomponentName(res.getString("сomponentName"));
                receivedData.setPrice(res.getInt("price"));
                log.info("receivedData:" + receivedData);
            }
        } catch (SQLException e) {
            log.error(e);
            Message = e.getMessage();
            return new DataOfResult<>(receivedData, Status.FAULT, Message);
        }
        if (receivedData.getId() == 0) {
            Message = "select empty Error";
            log.info(Message);
            return new DataOfResult<>(receivedData, Status.FAULT, Message);
        }
        return new DataOfResult<>(receivedData, Status.SUCCESS, Message);
    }

    /**
     * @param sql
     * @return
     */
    public DataOfResult<ResultSet> selectSQL(String sql) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            getConnection().close();
            String Message = "select working";
            return new DataOfResult<>(resultSet, Status.SUCCESS, Message);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
            return new DataOfResult<>(null, Status.FAULT, e.getMessage());
        }
    }

    /**
     * @param Component
     * @return
     */
    public DataOfResult<String> insertComponent(Components Component) {
        long id = Component.getId();
        Integer numberOfComponents = Component.getNumberOfComponents();
        String сomponentName = Component.getСomponentName();
        Integer price = Component.getPrice();
        String request = (String.format(Constants.INSERT_COMPONENTS, id, numberOfComponents, сomponentName, price));
        DataOfResult<String> answer = (executeSQL(request));

        Components answerObject = new Components(id, numberOfComponents, сomponentName, price);
        //место монги
        // final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();//mongodb
        // HistoryContent historyContent = createHistoryContent("Components", methodName,(answerObject.toString()), Status.SUCCESS);
        // HistoryUtil.saveHistory(historyContent);
        return (answer);
    }


    /**
     * @return
     */
    public DataOfResult<List<Components>> getAllComponents() {
        String request = String.format(Constants.SELECT_COMPONENTS);
        log.debug("sqlrequest = " + request.toString());
        DataOfResult<ResultSet> select1 = selectSQL(request);
        ResultSet res = select1.getObject();
        ArrayList<Components> receivedList = new ArrayList<Components>();
        Components receivedData = new Components();
        String Message = select1.getMessage();
        try {
            while (res != null && res.next()) {//stream not working for Resultset
                receivedData = new Components();
                receivedData.setId(res.getLong("id"));
                receivedData.setNumberOfComponents(res.getInt("numberOfComponents"));
                receivedData.setСomponentName(res.getString("сomponentName"));
                receivedData.setPrice(res.getInt("price"));
                //log.info("receivedData:" + receivedData);
                receivedList.add(receivedData);
                //receivedList.add(receivedList.size(),receivedData);
                //log.info("receivedlist:" + receivedList.toString());

            }
        } catch (SQLException e) {
            log.error(e);
        }
        if (receivedData.getId() == 0) {
            Message = "select empty Error";
            log.info(Message);
            return new DataOfResult<>(receivedList, Status.FAULT, Message);
        }
        return new DataOfResult<>(receivedList, Status.SUCCESS, "All components loaded");
    }

    /**
     * @param name
     * @return
     */
    public DataOfResult<Components> getComponentByName(String name) {
        String request = String.format(Constants.SELECT_COMPONENTS_NAME + "'" + name + "'");
        log.debug("sqlrequest = " + request);
        DataOfResult<ResultSet> select1 = selectSQL(request);
        ResultSet res = select1.getObject();
        Components receivedData = new Components();
        String Message = select1.getMessage();
        try {
            if (res != null && res.next()) {
                receivedData.setId(res.getLong("id"));
                receivedData.setNumberOfComponents(res.getInt("numberOfComponents"));
                receivedData.setСomponentName(res.getString("сomponentName"));
                receivedData.setPrice(res.getInt("price"));
                log.debug("receivedData:" + receivedData);
            }
        } catch (SQLException e) {
            log.error(e);
            Message = e.getMessage();
            return new DataOfResult<>(receivedData, Status.FAULT, Message);
        }
        if (receivedData.getId() == 0) {
            Message = "select empty Error";
            log.info(Message);
            return new DataOfResult<>(receivedData, Status.FAULT, Message);
        }
        return new DataOfResult<>(receivedData, Status.SUCCESS, Message);
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

            selectBean(CustomOfferJeans.class).getObject().stream().forEach(x -> {
                log.debug("x " + x.toString());
                ref.price = ref.price + calculateJeansCost(x).getObject();
                ref.work = ref.work + getProductionTime(x).getObject();
                ref.cost = ref.cost + (getComponentCost(x).getObject());
            });
            selectBean(CommonOfferJeans.class).getObject().stream().forEach(x -> {
                log.debug("x " + x.toString());
                ref.price = ref.price + calculateJeansCost(x).getObject();
                ref.work = ref.work + Constants.WORK_MODEL_TIME;
                ref.cost = ref.cost + Constants.MODEL_PRICE;
            });
            log.info(" price " + ref.price + " work " + ref.work + " cost " + ref.cost);
            ref.work = ref.work * Constants.WorkHourPrice;
            int data = ref.price - ref.work - ref.cost;
            return new DataOfResult<>(data, Status.SUCCESS, "Profit");
        }
        return new DataOfResult<>(0, Status.FAULT, "worker access required");
    }

    /**
     * @param className
     * @param <T>
     * @return
     */
    private static <T> String getLowerName(Class<T> className) {
        String path = className.getSimpleName().toLowerCase();
        return path;
    }
    //try {
    //            DataBasePantsworkshop2.executeSQL(Constants.CREATE_COMPONENTS);
    //        } catch (Exception ignored) {

    /**
     * @param className
     * @param <T>
     * @return
     */
    public <T> DataOfResult<String> deleteBean(Class<T> className) {
        String request = (String.format(Constants.DELETE_TABLE_BEAN + getLowerName(className)));
        return executeSQL(request);
    }


    /**
     * @param className
     * @param <T>
     * @return
     */
    public <T> DataOfResult<List<T>> selectBean(Class<T> className) {
        String request = String.format(Constants.SELECT_BEAN + getLowerName(className));
        log.debug("sqlrequest = " + request.toString());
        DataOfResult<ResultSet> select1 = selectSQL(request);
        ResultSet res = select1.getObject();
        ArrayList<T> receivedList = new ArrayList<T>();
        T receivedData = null;
        String Message = select1.getMessage();
        try {
            while (res != null && res.next()) {
                if (className == Components.class) {
                    receivedData = (T) new Components();
                    ((Components) receivedData).setId(res.getLong("id"));
                    ((Components) receivedData).setNumberOfComponents(res.getInt("numberOfComponents"));
                    ((Components) receivedData).setСomponentName(res.getString("сomponentName"));
                    ((Components) receivedData).setPrice(res.getInt("price"));
                }
                if (className == User.class) {
                    receivedData = (T) new User();
                    ((User) receivedData).setId(res.getLong("id"));
                    ((User) receivedData).setName(res.getString("name"));
                    ((User) receivedData).setAdress(res.getString("adress"));
                    ((User) receivedData).setWorkerAcess(res.getBoolean("WorkerAcess"));
                }
                if (className == Jeans.class) {
                    receivedData = (T) new Jeans();
                    ((Jeans) receivedData).setSize(res.getInt("size"));
                    ((Jeans) receivedData).setId(res.getLong("id"));
                    ((Jeans) receivedData).setPrice(res.getInt("price"));
                }
                if (className == CommonOfferJeans.class) {
                    receivedData = (T) new CommonOfferJeans();
                    ((CommonOfferJeans) receivedData).setSize(res.getInt("size"));
                    ((CommonOfferJeans) receivedData).setId(res.getLong("id"));
                    ((CommonOfferJeans) receivedData).setPrice(res.getInt("price"));
                    ((CommonOfferJeans) receivedData).setModelName(res.getString("modelname"));
                }
                if (className == CustomOfferJeans.class) {
                    receivedData = (T) new CustomOfferJeans();
                    ((CustomOfferJeans) receivedData).setSize(res.getInt("size"));
                    ((CustomOfferJeans) receivedData).setId(res.getLong("id"));
                    ((CustomOfferJeans) receivedData).setPrice(res.getInt("price"));
                    ((CustomOfferJeans) receivedData).setColor(res.getString("color"));
                    ((CustomOfferJeans) receivedData).setFormat(res.getString("format"));
                    ((CustomOfferJeans) receivedData).setStripe(res.getString("stripe"));
                    ((CustomOfferJeans) receivedData).setFabric(res.getString("Fabric"));
                    ((CustomOfferJeans) receivedData).setSewingTime(res.getInt("sewingTime"));
                }
                receivedList.add(receivedData);
            }
        } catch (SQLException e) {
            log.error(e);
        }
        if (receivedData == null) {
            Message = "select empty Error";
            log.info(Message);
            return new DataOfResult<>(receivedList, Status.FAULT, Message);
        }
        return new DataOfResult<>(receivedList, Status.SUCCESS, className.getSimpleName() + " loaded");
    }

    /**
     * @param beanObject
     * @param className
     * @param <T>
     * @return
     */
    public <T> DataOfResult<String> insertBean(T beanObject, Class<T> className) {
        DataOfResult<String> answer = null;
        String request = null;
        if (className == Components.class) {
            long id = ((Components) beanObject).getId();
            Integer numberOfComponents = ((Components) beanObject).getNumberOfComponents();
            String сomponentName = ((Components) beanObject).getСomponentName();
            Integer price = ((Components) beanObject).getPrice();
            request = (String.format(Constants.INSERT_COMPONENTS, id, numberOfComponents, сomponentName, price));
        }
        if (className == Jeans.class) {
            long id = ((Jeans) beanObject).getId();
            Integer size = ((Jeans) beanObject).getSize();
            Integer price = ((Jeans) beanObject).getPrice();
            request = (String.format(Constants.INSERT_JEANS, size, id, price));
        }
        if (className == CommonOfferJeans.class) {
            long id = ((CommonOfferJeans) beanObject).getId();
            Integer size = ((CommonOfferJeans) beanObject).getSize();
            Integer price = ((CommonOfferJeans) beanObject).getPrice();
            OfferJeans modelName = ((CommonOfferJeans) beanObject).getModelName();
            request = (String.format(Constants.INSERT_COMMONOFFERJEANS, size, id, price, modelName));
        }
        if (className == CustomOfferJeans.class) {
            long id = ((CustomOfferJeans) beanObject).getId();
            Integer size = ((CustomOfferJeans) beanObject).getSize();
            Integer price = ((CustomOfferJeans) beanObject).getPrice();
            Color color = ((CustomOfferJeans) beanObject).getColor();
            String format = ((CustomOfferJeans) beanObject).getFormat();
            String stripe = ((CustomOfferJeans) beanObject).getStripe();
            Fabric Fabric = ((CustomOfferJeans) beanObject).getFabric();
            Integer sewingTime = ((CustomOfferJeans) beanObject).getSewingTime();
            request = (String.format(Constants.INSERT_CUSTOMOFFERJEANS, size, id, price, color, format, stripe, Fabric, sewingTime));
        }
        if (className == User.class) {
            long id = ((User) beanObject).getId();
            String Name = ((User) beanObject).getName();
            String adress = ((User) beanObject).getAdress();
            Boolean WorkerAcess = ((User) beanObject).getWorkerAcess();
            request = (String.format(Constants.INSERT_USERS, id, Name, adress, WorkerAcess));
        }

        answer = (executeSQL(request));
        //
        //место монги
        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();//mongodb
        HistoryContent historyContent = createHistoryContent(className.getSimpleName(), methodName,(beanObject.toString()), Status.SUCCESS);
        HistoryUtil.saveHistory(historyContent);
        return (answer);
    }


    /**
     * @param className
     * @param <T>
     * @return
     */
    public <T> DataOfResult<String> CreateTableForBean(Class<T> className) {
        String request = switch (className.getSimpleName()) {
            case "Jeans" -> Constants.CREATE_JEANS;
            case "User" -> Constants.CREATE_USERS;
            case "CommonOfferJeans" -> Constants.CREATE_COMMONOFFERJEANS;
            case "CustomOfferJeans" -> Constants.CREATE_CUSTOMOFFERJEANS;
            case "Components" -> Constants.CREATE_COMPONENTS;
            default -> "";
        };
        return executeSQL(request);
    }
}
