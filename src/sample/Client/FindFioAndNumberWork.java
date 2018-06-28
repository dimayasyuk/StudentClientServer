package sample.Client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.Constants;
import sample.Student;

import java.io.IOException;
import java.util.List;

/**
 * Created by dmitriy on 22.5.18.
 */
public class FindFioAndNumberWork extends FioAndNumberWork{
    private Table findTable;
    public FindFioAndNumberWork(Stage stage,Table table,Table findTable){
        this.stage = stage;
        this.table = table;
        this.findTable = findTable;
        showWindow();
    }
    public void showWindow() {
        newWindow = new Stage();
        newWindow.initOwner(stage);
        createDialog("Поиск");
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
                if (count == 0) {
                    showAlert("Записей не найдено!");
                    try {
                        findTable.updateTable();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                    List<Student> students = (List<Student>)table.getClient().getInput().readObject();
                    BorderPane borderPane = new BorderPane();
                    borderPane.setCenter(findTable.createPage());
                        findTable.updateTable();
                    Button delete = new Button("Удалить найденные");
                    delete.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                table.getClient().sendData(Constants.DELETE_STUDENT);
                                table.getClient().sendData(students);
                                table.updateTable();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            newWindow.close();
                        }
                    });
                    HBox deleteBox = new HBox();
                    deleteBox.setAlignment(Pos.BASELINE_CENTER);
                    deleteBox.getChildren().add(delete);
                    borderPane.setBottom(deleteBox);
                    newWindow.setScene(new Scene(borderPane, 600, 600));
                    newWindow.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    protected int startAlgorithm() throws IOException, ClassNotFoundException {
        Student student = new Student(fioText.getText(), nameText.getText(), patronymicText.getText());
        table.getClient().sendData(Constants.FIND_BY_NUMBER_WORK);
        table.getClient().sendData(student);
        table.getClient().sendData(Integer.parseInt(workText.getText()));
        table.getClient().sendData(Integer.parseInt(workText1.getText()));
        return (int)table.getClient().getInput().readObject();
    }
}
