package ru.sfedu.pantsworkshop.beans;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Objects;

@Root

public class Components implements Serializable {
    @Element
    @CsvBindByPosition(position = 0)
    private long id;
    @Element
    @CsvBindByPosition(position = 1)
    private Integer numberOfComponents;
    @Element
    @CsvBindByPosition(position = 2)
    private String сomponentName;
    @Element
    @CsvBindByPosition(position = 3)
    private Integer price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getNumberOfComponents() {
        return numberOfComponents;
    }

    public void setNumberOfComponents(Integer numberOfComponents) {
        this.numberOfComponents = numberOfComponents;
    }

    public String getСomponentName() {
        return сomponentName;
    }

    public void setСomponentName(String сomponentName) {
        this.сomponentName = сomponentName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * @param id
     * @param numberOfComponents
     * @param сomponentName
     * @param price
     */
    public Components(long id, Integer numberOfComponents, String сomponentName, Integer price) {

        this.id = id;
        this.numberOfComponents = numberOfComponents;
        this.сomponentName = сomponentName;
        this.price = price;
    }

    public Components() {
        ;
    }

    @Override
    public String toString() {
        return "Components{" +
                "id=" + id +
                ", numberOfComponents=" + numberOfComponents +
                ", сomponentName='" + сomponentName + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Components)) return false;
        Components that = (Components) o;
        return getId() == that.getId() && getNumberOfComponents().equals(that.getNumberOfComponents()) && getСomponentName().equals(that.getСomponentName()) && getPrice().equals(that.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumberOfComponents(), getСomponentName(), getPrice());
    }
}
