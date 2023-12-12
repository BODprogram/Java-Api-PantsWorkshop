package ru.sfedu.pantsworkshop.beans;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Objects;

@Root
public class Jeans implements Serializable {
    @Element
    @CsvBindByPosition(position = 0)
    Integer size;
    @Element
    @CsvBindByPosition(position = 1)
    long id;
    @Element
    @CsvBindByPosition(position = 2)
    Integer price;

    /**
     * @param size
     * @param id
     * @param price
     */
    public Jeans(Integer size, long id, Integer price) {
        this.size = size;
        this.id = id;
        this.price = price;
    }

    public Jeans() {
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jeans)) return false;
        Jeans jeans = (Jeans) o;
        return getId() == jeans.getId() && getSize().equals(jeans.getSize()) && getPrice().equals(jeans.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSize(), getId(), getPrice());
    }
}
