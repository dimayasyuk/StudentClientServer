package sample.Client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import sample.Constants;
import sample.Student;

import java.io.IOException;

/**
 * Created by dmitriy on 7.5.18.
 */
public class DeleteFioAndNumberWork extends FioAndNumberWork{

    public DeleteFioAndNumberWork(Stage stage, Table table){
        this.stage = stage;
        this.table = table;
        showWindow();
    }

    public void showWindow() {
        newWindow = new Stage();
        newWindow.initOwner(stage);
        createDialog("Удаление");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int count = 0;
                try {
                    count = startAlgorithm();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                newWindow.close();
                if(count == 0){
                    showAlert("Записей не найдено!");
                }
                else{
                    showAlert("Удалено " + count + " записей");
                }
                try {
                    table.updateTable();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected int startAlgorithm() throws IOException, ClassNotFoundException {
        Student student = new Student(fioText.getText(), nameText.getText(), patronymicText.getText());
        table.getClient().sendData(Constants.DELETE_BY_NUMBER_WORK);
        table.getClient().sendData(student);
        table.getClient().sendData(Integer.parseInt(workText.getText()));
        table.getClient().sendData(Integer.parseInt(workText1.getText()));
        return (int)table.getClient().getInput().readObject();
    }
}
