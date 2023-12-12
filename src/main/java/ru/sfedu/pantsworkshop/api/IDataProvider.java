package ru.sfedu.pantsworkshop.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.pantsworkshop.Constants;
import ru.sfedu.pantsworkshop.TestingClient;
import ru.sfedu.pantsworkshop.beans.*;
import ru.sfedu.pantsworkshop.beans.enums.OfferJeans;
import ru.sfedu.pantsworkshop.utils.DataOfResult;
import ru.sfedu.pantsworkshop.utils.Status;

import java.util.List;

public interface IDataProvider {
    public static final Logger log = LogManager.getLogger(TestingClient.class.getName());

    public DataOfResult<List<Components>> getAllComponents();

    DataOfResult<Integer> summariseAllProfit(User user);
    // public default <T> boolean writeCSV(List<T> list, Class<T> type){return false;}
    // public default DataOfResult<String> insertComponents(List<Components> сomponents) throws IOException {return null;}
    // public default  DataOfResult<String> insertCustomOfferJeans(List<CustomOfferJeans> customOfferJeans) throws IOException {return null;}
    // public default DataOfResult<String> insertCommonOfferJeans(List<CommonOfferJeans> commonOfferJeans) throws IOException {return null;}

    public default Integer GetComponentPriceСalculation(List<Components> ListComponents) {
        //Включенный метод. Входные данные передаются в сигнатуре родительского метода getProfit().
        //Цель: получить цену всех текущих компонентов на складе
        //Входные данные:
        //•	String typeOfPrice
        //Выходные данные:
        //•	Объект Answer <Integer>
        //
        //Расширяем методом getDetailedCheck.
        //Условие: Метод вызывается, если поле родительского метода typeOfPrice равно «DETAIL».
        //Параметры: Параметры передаются в сигнатуре метода getСomponentPrice.

        // DataOfResult<List <Components>> getAllComponents();
        var ref = new Object() {
            int Answer = 0;
        };
        ListComponents.stream().forEach(x -> {
            ref.Answer = ref.Answer + x.getPrice();

        });
        return ref.Answer;
    }


    /**
     * @param jeans
     * @return
     */
    public default Integer SpecialOrderСalculation(CommonOfferJeans jeans) {
        int price = 850;
        if (jeans.getModelName() == OfferJeans.blackCat) {
            price = Constants.BLACK_CAT_PRICE;
        }
        if (jeans.getModelName() == OfferJeans.indigoEagle) {
            price = Constants.INDIGO_EAGLE_PRICE;
        }
        if (jeans.getModelName() == OfferJeans.heavenlyExpanse) {
            price = Constants.HEAVEN_EXPANSE_PRICE;
        }

        return price;
    }

    /**
     * @param jeans
     * @return
     */
    public default Integer GetProductionTimeСalculation(CustomOfferJeans jeans) {
        return jeans.getSewingTime();
    }

    /**
     * @param Jeans
     * @param components
     * @return
     */
    default int GetСomponentСostСalculation(CustomOfferJeans Jeans, List<Components> components) {
        var ref = new Object() {
            int colorPrice = 12;
            int fabricPrice = 23;
        };
        if ((Jeans.getColor() == null) | (Jeans.getFabric() == null)) {
            return (ref.colorPrice + ref.fabricPrice);
        }
        components.stream().forEach(x -> {
            if (Jeans.getColor().toString().equals(x.getСomponentName())) {
                ref.colorPrice = x.getPrice();
            }
            if (Jeans.getFabric().toString().equals(x.getСomponentName())) {
                ref.fabricPrice = x.getPrice();
            }
        });

        // Цель: Метод возвращает стоимость всех компонентов для джинс
        return (ref.colorPrice + ref.fabricPrice);
    }

    /**
     * @return
     */
    public default DataOfResult<List<Components>> getDetailedCheck() {
//Цель: получить детальную информацию о всех текущих товарах на складе
        String message = "information about all current goods in the warehouse is read";
        DataOfResult<List<Components>> AllComponents = getAllComponents();
        if (AllComponents.getStatus() == Status.FAULT) {
            message = AllComponents.getMessage();
        }

        return new DataOfResult<>(AllComponents.getObject(), AllComponents.getStatus(), message);
    }

    /**
     * @param jeans
     * @return
     */
    default DataOfResult<Integer> getProductionTime(CustomOfferJeans jeans) //Calculatejeanscost().
    // Цель: метод возвращает время на создание джинс.
    {
        int data = GetProductionTimeСalculation(jeans);
        if (data == 0) {
            return new DataOfResult<>(data, Status.FAULT, "jeans model incorrect");
        }
        return new DataOfResult<>(data, Status.SUCCESS, "time to make jeans");
    }

    /**
     * @param jeans
     * @return
     */
    default DataOfResult<Integer> getComponentCost(CustomOfferJeans jeans) //Calculatejeanscost().
    //Метод возвращает стоимость всех компонентов для джинс
    {
        DataOfResult<List<Components>> AllComponents = getAllComponents();
        int data = GetСomponentСostСalculation(jeans, AllComponents.getObject());
        if (data == 35) {
            return new DataOfResult<>(data, Status.FAULT, "jeans model incorrect");
        }
        return new DataOfResult<>(data, AllComponents.getStatus(), "the cost of all components for jeans");
    }

    /**
     * @param jeans
     * @return
     */
    default DataOfResult<Integer> specialOrder(CommonOfferJeans jeans)  //Calculatejeanscost().
    //Special order  \\\\Calculate jeans cost
    //метод возвращает уже рассчитанную стоимость джинс, без потребности рассчитывать заказ с самого начала
    {
        int data = SpecialOrderСalculation(jeans);

        if (data == 850) {
            return new DataOfResult<>(data, Status.FAULT, "jeans model incorrect");
        }
        return new DataOfResult<>(data, Status.SUCCESS, "jeans price");
    }

    /**
     * @param jeans
     * @return
     */
    default DataOfResult<Integer> calculateJeansCost(Jeans jeans)  //summariseAllProfit()..
    //Цель: Метод позволяет рассчитать стоимость заказанных пользователем джинс
    {
        try {
            switch (jeans.getClass().getSimpleName()) {
                case "CustomOfferJeans":
                    int price = 0;
                    price = price + getComponentCost((CustomOfferJeans) jeans).getObject();
                    if (price == 35) {
                        return new DataOfResult<>(0, Status.FAULT, "jeans model incorrect");
                    }
                    price = price + Constants.WorkHourPrice * getProductionTime((CustomOfferJeans) jeans).getObject();
                    price = price * Constants.WorkFinalmultiplier;
                    return new DataOfResult<>(price, Status.SUCCESS, "jeans price");
                case "CommonOfferJeans":
                    specialOrder((CommonOfferJeans) jeans);
                default:
                    return new DataOfResult<>(0, Status.FAULT, "unknown data");
            }
        } catch (
                Exception e) {
            log.error(e);
            return new DataOfResult<>(0, Status.FAULT, e.getMessage());
        }
    }

    /**
     * @param user
     * @param detailTypeOfPrice
     * @return
     */
    public default DataOfResult<Integer> getProfit(User user, boolean detailTypeOfPrice) {
        if (user.getWorkerAcess() == true) {
            int data = summariseAllProfit(user).getObject() - getComponentPrice(user, detailTypeOfPrice).getObject();
            return new DataOfResult<>(data, Status.SUCCESS, "profit calculated");
        }
        //Цель: получить разницу между затраченными на товары деньгами и полученной прибылью
        return new DataOfResult<>(0, Status.FAULT, "worker access required");
    }

    /**
     * @param user
     * @param detailTypeOfPrice
     * @return
     */
    public default DataOfResult<Integer> getComponentPrice(User user, boolean detailTypeOfPrice) {//getProfit().
        if (user.getWorkerAcess() == true) {
            int data = GetComponentPriceСalculation(getAllComponents().getObject());
            if (detailTypeOfPrice == true) {
                getDetailedCheck();
            }
            return new DataOfResult<>(data, Status.SUCCESS, "the price of components in stock is calculated");
        }
        //Цель: получить цену всех текущих компонентов на складе
        return new DataOfResult<>(0, Status.FAULT, "worker access required");
    }


}
