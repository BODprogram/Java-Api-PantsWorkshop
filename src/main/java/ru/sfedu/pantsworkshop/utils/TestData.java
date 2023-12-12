package ru.sfedu.pantsworkshop.utils;

import ru.sfedu.pantsworkshop.beans.*;
import ru.sfedu.pantsworkshop.beans.enums.Color;
import ru.sfedu.pantsworkshop.beans.enums.Fabric;
import ru.sfedu.pantsworkshop.beans.enums.OfferJeans;

import java.util.ArrayList;
import java.util.Arrays;

public class TestData {


    public final Components сomponents1 = new Components(1, 105, "Denim", 10);
    public final Components сomponents2 = new Components(2, 106, "Denim", 11);
    public static ArrayList<Components> components = new ArrayList<Components>((
            Arrays.asList(new Components(1, 105, "denim", 12),
                    new Components(2, 106, "jean", 11),
                    new Components(3, 123, "stretch", 37),
                    new Components(4, 136, "serge", 78),
                    new Components(5, 86, "teal", 42),
                    new Components(6, 96, "indigo", 32),
                    new Components(7, 89, "blue", 14),
                    new Components(8, 79, "black", 36)
            )));

    public final Jeans jeans1 = new Jeans();
    public final Jeans jeans2 = new Jeans();


    public final static User User1 = new User(1, "igor", "moskva", true);
    public final static User User2 = new User(2, "andrey", "piter", false);
    public static ArrayList<User> Users = new ArrayList<User>(
            Arrays.asList(new User(1, "igor", "moskva", true),
                    new User(2, "andrey", "piter", false)
            ));
    //id integer,  adress text,name text,workeracess boolean

    public static final CustomOfferJeans customOfferJeans1 = new CustomOfferJeans(40, 1, 303, Color.indigo, "normal", "none", Fabric.denim, 8);
    public final CustomOfferJeans customOfferJeans2 = new CustomOfferJeans(38, 2, 363, Color.teal, "normal", "none", Fabric.jean, 7);
    public static ArrayList<CustomOfferJeans> customOfferJeans = new ArrayList<CustomOfferJeans>(
            Arrays.asList(new CustomOfferJeans(40, 1, 303, Color.indigo, "normal", "none", Fabric.denim, 8),
                    new CustomOfferJeans(38, 2, 363, Color.teal, "normal", "none", Fabric.jean, 7)
            ));
    public static final CommonOfferJeans commonOfferJeans1 = new CommonOfferJeans(37, 1, 950, OfferJeans.blackCat);
    public final CommonOfferJeans commonOfferJeans2 = new CommonOfferJeans(33, 2, 900, OfferJeans.indigoEagle);

    public static ArrayList<CommonOfferJeans> commonOfferJeans = new ArrayList<CommonOfferJeans>(
            Arrays.asList(new CommonOfferJeans(37, 1, 950, OfferJeans.blackCat),
                    new CommonOfferJeans(33, 2, 900, OfferJeans.indigoEagle)
            ));

}
