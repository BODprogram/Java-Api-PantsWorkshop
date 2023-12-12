package ru.sfedu.pantsworkshop;

public class Constants {
    public static final String PACKAGE_PATH = "ru.sfedu";
    public static final String NAME_TABLE_PROFILE = "Profile";

    public static final int WorkHourPrice = 300;
    public static final int WorkFinalmultiplier = 2;
    public static final int MODEL_PRICE = 105;
    public static final int WORK_MODEL_TIME = 1;
    public static final int INDIGO_EAGLE_PRICE = 900;
    public static final int BLACK_CAT_PRICE = 950;
    public static final int HEAVEN_EXPANSE_PRICE = 970;

    public static final String CSV_PATH = "CSV_PATH";
    public static final String CSV_PATTERN = "%s.csv";
    public static final String empty_csv = "src/main/resources/csv/empty.csv";
    public static final String COMPONENTS_CSV = "src/main/resources/csv/components.csv";


    public static final String COMPONENTS_XML = "data.xml.components";
    public static final String USER_XML = "data.xml.user";
    public static final String CUSTOMOFFERJEANS_XML = "data.xml.customofferjeans";
    public static final String COMMONOFFERJEANS_XML = "data.xml.commonofferjeans";
    public static final String JEANS_XML = "data.xml.jeans";


    public static final String JDBC_DRIVER = "ru.sfedu.jdbcDriver";
    public static final String DB_CONNECT = "ru.sfedu.dbConn";
    public static final String DB_USER = "ru.sfedu.dbUsr";
    public static final String DB_PASS = "ru.sfedu.dbPass";

    public static final String DEFAULT_ACTOR = "system";

    public static final String MONGO_COLLECTION = "mongoCollection";
    public static final String MONGO_URI = "mongoURI";
    public static final String ENV_PROP_KEY = "environment";//?
    public static final String ENV_PROP_VALUE = "src/main/resources/environment.properties";//?
    public static final String MONGO_DATABASE = "mongoDatabase";
    //SQL
    public static final String CREATE_DATABASE = "CREATE DATABASE pantsworkshoptest";
    public static final String CREATE_COMPONENTS = "CREATE TABLE components(id integer,  numberofcomponents integer,сomponentname text,price integer)";
    public static final String CREATE_USERS = "CREATE TABLE users(id integer,  adress text,name text,workeracess boolean)";
    public static final String CREATE_CUSTOMOFFERJEANS = "CREATE TABLE customofferjeans(size integer,id integer,  price integer,color text,format text,stripe text,fabric text,sewingtime integer)";
    public static final String CREATE_COMMONOFFERJEANS = "CREATE TABLE commonofferjeans(size integer,id integer,  price integer,modelname text)";
    public static final String CREATE_JEANS = "CREATE TABLE jeans(size integer,id integer,  price integer)";

    public static final String DELETE_TABLE_COMPONENTS = "DELETE FROM components";
    public static final String DELETE_TABLE_BEAN = "DELETE FROM ";


    public static final String INSERT_COMPONENTS = "INSERT INTO components values( %d, %d, '%s', %d)";
    public static final String INSERT_USERS = "INSERT INTO users values( %d, '%s' '%s', '%s')";//bool?
    public static final String INSERT_CUSTOMOFFERJEANS = "INSERT INTO customofferjeans values( %d, %d, %d, '%s', '%s', '%s', '%s',%d)";
    public static final String INSERT_COMMONOFFERJEANS = "INSERT INTO commonofferjeans values( %d, %d, %d, '%s')";
    public static final String INSERT_JEANS = "INSERT INTO jeans values( %d, %d, %d)";
    public static final String SELECT_COMPONENTS = "SELECT* FROM components";
    public static final String SELECT_BEAN = "SELECT* FROM ";
    ;
    public static final String SELECT_COMPONENTS_ID = "SELECT * FROM components WHERE id = ";
    public static final String SELECT_COMPONENTS_NAME = "SELECT * FROM components WHERE сomponentname = ";
    public static final String DELETE_COMPONENTS_ID = "DELETE FROM components WHERE id = ";
    public static final String UPDATE_COMPONENTS_WHERE_ID = "UPDATE components SET numberofcomponents = %d,сomponentname='%s',price=%d WHERE id = %d";

}