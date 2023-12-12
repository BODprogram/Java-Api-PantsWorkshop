package ru.sfedu.pantsworkshop.beans;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.pantsworkshop.Constants;
import ru.sfedu.pantsworkshop.beans.enums.OfferJeans;

import java.util.Objects;

@Root
public class CommonOfferJeans extends Jeans {
    @Element
    @CsvBindByPosition(position = 3)
    OfferJeans modelName;


    /**
     * @param size
     * @param id
     * @param price
     * @param modelName
     */
    public CommonOfferJeans(Integer size, long id, Integer price, OfferJeans modelName) {
        super(size, id, price);
        this.modelName = modelName;
    }

    public CommonOfferJeans(OfferJeans modelName) {
        this.modelName = modelName;
    }

    public CommonOfferJeans() {
    }

    public OfferJeans getModelName() {
        return modelName;
    }

    public void setModelName(OfferJeans modelName) {
        this.modelName = modelName;
    }

    /**
     * @param modelName
     */
    public void setModelName(String modelName) {
        OfferJeans result = switch (modelName) { //indigoEagle, blackCat, heavenlyExpanse
            case "blackCat" -> OfferJeans.blackCat;
            case "heavenlyExpanse" -> OfferJeans.heavenlyExpanse;
            default -> OfferJeans.indigoEagle;
        };
        this.modelName = result;
    }

    @Override
    public String toString() {
        return "CommonOfferJeans{" +
                "modelName=" + modelName +
                ", size=" + size +
                ", id=" + id +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommonOfferJeans)) return false;
        if (!super.equals(o)) return false;
        CommonOfferJeans that = (CommonOfferJeans) o;
        return getModelName() == that.getModelName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getModelName());
    }
}
