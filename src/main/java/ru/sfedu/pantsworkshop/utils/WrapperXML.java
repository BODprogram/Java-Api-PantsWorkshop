package ru.sfedu.pantsworkshop.utils;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;


@Root(name = "Examples")
public class WrapperXML<T> {
    @ElementList(name = "cont", inline = true, required = false)
    private List<T> container;

    public WrapperXML() {
    }


    public List<T> getContainer() {
        return container;
    }

    public void setContainer(List<T> container) {
        this.container = container;
    }

    @Override
    public String toString() {
        return "{" +
                ", container=" + container +
                '}';
    }

}
