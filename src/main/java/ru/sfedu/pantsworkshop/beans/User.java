package ru.sfedu.pantsworkshop.beans;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Objects;

@Root
public class User implements Serializable {
    @Element
    @CsvBindByPosition(position = 0)
    long id;
    @Element
    @CsvBindByPosition(position = 1)
    String Name;
    @Element
    @CsvBindByPosition(position = 2)
    String adress;
    @Element
    @CsvBindByPosition(position = 3)
    Boolean WorkerAcess;

    /**
     * @param id
     * @param name
     * @param adress
     * @param workerAcess
     */
    public User(long id, String name, String adress, Boolean workerAcess) {
        this.id = id;
        Name = name;
        this.adress = adress;
        WorkerAcess = workerAcess;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Boolean getWorkerAcess() {
        return WorkerAcess;
    }

    public void setWorkerAcess(Boolean workerAcess) {
        WorkerAcess = workerAcess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() && getName().equals(user.getName()) && getAdress().equals(user.getAdress()) && getWorkerAcess().equals(user.getWorkerAcess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAdress(), getWorkerAcess());
    }
}
