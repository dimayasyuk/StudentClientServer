package sample;

import java.io.Serializable;

/**
 * Created by dmitriy on 5.5.18.
 */
public class Work implements Serializable{

    private String nameWork;

    public Work(String nameWork){
        this.nameWork = nameWork;
    }
    public String getNameWork() {
        return nameWork;
    }

    public void setNameWork(String nameWork) {
        this.nameWork = nameWork;
    }
}
