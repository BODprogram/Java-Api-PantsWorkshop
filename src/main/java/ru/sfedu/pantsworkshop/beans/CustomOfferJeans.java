package ru.sfedu.pantsworkshop.beans;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.pantsworkshop.beans.enums.Color;
import ru.sfedu.pantsworkshop.beans.enums.Fabric;
import ru.sfedu.pantsworkshop.beans.enums.OfferJeans;

import java.util.Objects;

@Root
public class CustomOfferJeans extends Jeans {
    @Element
    @CsvBindByPosition(position = 3)
    Color color;
    @Element
    @CsvBindByPosition(position = 4)
    String format;
    @Element
    @CsvBindByPosition(position = 5)
    String stripe;
    @Element
    @CsvBindByPosition(position = 6)
    Fabric Fabric;
    @Element
    @CsvBindByPosition(position = 7)
    Integer sewingTime;

    /**
     * @param size
     * @param id
     * @param price
     * @param color
     * @param format
     * @param stripe
     * @param fabric
     * @param sewingTime
     */
    public CustomOfferJeans(Integer size, long id, Integer price, Color color, String format, String stripe, ru.sfedu.pantsworkshop.beans.enums.Fabric fabric, Integer sewingTime) {
        super(size, id, price);
        this.color = color;
        this.format = format;
        this.stripe = stripe;
        this.Fabric = fabric;
        this.sewingTime = sewingTime;
    }

    /**
     * @param color
     * @param format
     * @param stripe
     * @param fabric
     * @param sewingTime
     */
    public CustomOfferJeans(Color color, String format, String stripe, ru.sfedu.pantsworkshop.beans.enums.Fabric fabric, Integer sewingTime) {
        this.color = color;
        this.format = format;
        this.stripe = stripe;
        this.Fabric = fabric;
        this.sewingTime = sewingTime;
    }


    public CustomOfferJeans() {
        this.sewingTime = 0;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @param color
     */
    public void setColor(String color) {//teal, indigo, blue, black
        Color result = switch (color) {
            case "teal" -> Color.teal;
            case "indigo" -> Color.indigo;
            case "blue" -> Color.blue;
            default -> Color.black;
        };
        this.color = result;
    }

    /**
     * @param fabric
     */
    public void setFabric(String fabric) {//denim, jean, stretch, serge
        Fabric result = switch (fabric) {
            case "denim" -> Fabric.denim;
            case "jean" -> Fabric.jean;
            case "stretch" -> Fabric.stretch;
            default -> Fabric.serge;
        };
        this.Fabric = result;
    }


    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getStripe() {
        return stripe;
    }

    public void setStripe(String stripe) {
        this.stripe = stripe;
    }

    public ru.sfedu.pantsworkshop.beans.enums.Fabric getFabric() {
        return Fabric;
    }

    public void setFabric(ru.sfedu.pantsworkshop.beans.enums.Fabric fabric) {
        Fabric = fabric;
    }

    public Integer getSewingTime() {
        return sewingTime;
    }

    public void setSewingTime(Integer sewingTime) {
        this.sewingTime = sewingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomOfferJeans)) return false;
        CustomOfferJeans that = (CustomOfferJeans) o;
        return getColor() == that.getColor() && getFormat().equals(that.getFormat()) && getStripe().equals(that.getStripe()) && getFabric() == that.getFabric() && getSewingTime().equals(that.getSewingTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getColor(), getFormat(), getStripe(), getFabric(), getSewingTime());
    }

    @Override
    public String toString() {
        return "CustomOfferJeans{" +
                "color=" + color +
                ", format='" + format + '\'' +
                ", stripe='" + stripe + '\'' +
                ", Fabric=" + Fabric +
                ", sewingTime=" + sewingTime +
                ", size=" + size +
                ", id=" + id +
                ", price=" + price +
                '}';
    }
}
