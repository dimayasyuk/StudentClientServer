package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitriy on 23.4.18.
 */
public class Student implements Serializable{
    private String firstName;
    private String lastName;
    private String patronymicName;
    private String group;
    private List<Work> work;

    public Student(){
        work = new ArrayList<>();
    }

    public Student(String firstName,String lastName,String patronymicName,String group,List<Work> work){
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymicName = patronymicName;
        this.group = group;
        this.work = work;
    }
    public Student(String firstName,String lastName,String patronymicName,List<Work> works){
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymicName = patronymicName;
        this.work = works;
    }
    public Student(String firstName,String lastName,String patronymicName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymicName = patronymicName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymicName() {
        return patronymicName;
    }

    public String getGroup() {
        return group;
    }

    public List<Work> getWork() {
        return work;
    }

    public void addWork(String name){
        work.add(new Work(name));
    }
}
