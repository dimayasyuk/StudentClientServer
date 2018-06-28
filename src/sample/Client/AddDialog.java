package sample.Client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Constants;
import sample.Student;
import sample.Work;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitriy on 6.5.18.
 */
public class AddDialog{
    private Stage newWindow;
    private Stage stage;
    private Table table;
    public AddDialog(Stage stage, Table table){
        this.stage = stage;
        this.table = table;
        createDialog();
    }
    public void createDialog(){
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(5);
        gridpane.setVgap(5);
        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
        column2.setHgrow(Priority.ALWAYS);
        gridpane.getColumnConstraints().addAll(column1, column2);

        Label fioLabel = new Label("Фамилия");
        TextField fioText = new TextField();

        Label nameLabel = new Label("Имя");
        TextField nameText = new TextField();

        Label patronymicLabel = new Label("Отчество");
        TextField patronymicText = new TextField();

        Label groupLabel = new Label("Группа");
        TextField groupText = new TextField();

        Label work1l = new Label("1 семестр");
        TextField work1t = new TextField();
        Label work2l = new Label("2 семестр");
        TextField work2t = new TextField();
        Label work3l = new Label("3 семестр");
        TextField work3t = new TextField();
        Label work4l = new Label("4 семестр");
        TextField work4t = new TextField();
        Label work5l = new Label("5 семестр");
        TextField work5t = new TextField();
        Label work6l = new Label("6 семестр");
        TextField work6t = new TextField();
        Label work7l = new Label("7 семестр");
        TextField work7t = new TextField();
        Label work8l = new Label("8 семестр");
        TextField work8t = new TextField();
        Label work9l = new Label("9 семестр");
        TextField work9t = new TextField();
        Label work10l = new Label("10 семестр");
        TextField work10t = new TextField();


        GridPane.setHalignment(fioLabel, HPos.RIGHT);
        gridpane.add(fioLabel,0,0);
        GridPane.setHalignment(nameLabel, HPos.RIGHT);
        gridpane.add(nameLabel,0,1);
        GridPane.setHalignment(patronymicLabel, HPos.RIGHT);
        gridpane.add(patronymicLabel,0,2);
        GridPane.setHalignment(groupLabel, HPos.RIGHT);
        gridpane.add(groupLabel,0,3);
        GridPane.setHalignment(work1l, HPos.RIGHT);
        gridpane.add(work1l,0,4);
        GridPane.setHalignment(work2l, HPos.RIGHT);
        gridpane.add(work2l,0,5);
        GridPane.setHalignment(work3l, HPos.RIGHT);
        gridpane.add(work3l,0,6);
        GridPane.setHalignment(work4l, HPos.RIGHT);
        gridpane.add(work4l,0,7);
        GridPane.setHalignment(work5l, HPos.RIGHT);
        gridpane.add(work5l,0,8);
        GridPane.setHalignment(work6l, HPos.RIGHT);
        gridpane.add(work6l,0,9);
        GridPane.setHalignment(work7l, HPos.RIGHT);
        gridpane.add(work7l,0,10);
        GridPane.setHalignment(work8l, HPos.RIGHT);
        gridpane.add(work8l,0,11);
        GridPane.setHalignment(work9l, HPos.RIGHT);
        gridpane.add(work9l,0,12);
        GridPane.setHalignment(work10l, HPos.RIGHT);
        gridpane.add(work10l,0,13);



        GridPane.setHalignment(fioText, HPos.LEFT);
        gridpane.add(fioText,1,0);
        GridPane.setHalignment(nameText, HPos.LEFT);
        gridpane.add(nameText,1,1);
        GridPane.setHalignment(patronymicText, HPos.LEFT);
        gridpane.add(patronymicText,1,2);
        GridPane.setHalignment(groupText, HPos.LEFT);
        gridpane.add(groupText,1,3);
        GridPane.setHalignment(work1t, HPos.LEFT);
        gridpane.add(work1t,1,4);
        GridPane.setHalignment(work2t, HPos.LEFT);
        gridpane.add(work2t,1,5);
        GridPane.setHalignment(work3t, HPos.LEFT);
        gridpane.add(work3t,1,6);
        GridPane.setHalignment(work4t, HPos.LEFT);
        gridpane.add(work4t,1,7);
        GridPane.setHalignment(work5t, HPos.LEFT);
        gridpane.add(work5t,1,8);
        GridPane.setHalignment(work6t, HPos.LEFT);
        gridpane.add(work6t,1,9);
        GridPane.setHalignment(work7t, HPos.LEFT);
        gridpane.add(work7t,1,10);
        GridPane.setHalignment(work8t, HPos.LEFT);
        gridpane.add(work8t,1,11);
        GridPane.setHalignment(work9t, HPos.LEFT);
        gridpane.add(work9t,1,12);
        GridPane.setHalignment(work10t, HPos.LEFT);
        gridpane.add(work10t,1,13);

        Button findButton = new Button("Добавить");
        findButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<Work> list = new ArrayList<>();
                list.add(new Work(work1t.getText()));
                list.add(new Work(work2t.getText()));
                list.add(new Work(work3t.getText()));
                list.add(new Work(work4t.getText()));
                list.add(new Work(work5t.getText()));
                list.add(new Work(work6t.getText()));
                list.add(new Work(work7t.getText()));
                list.add(new Work(work8t.getText()));
                list.add(new Work(work9t.getText()));
                list.add(new Work(work10t.getText()));
                table.getClient().sendData(Constants.ADD_STUDENT);
                table.getClient().sendData(new Student(fioText.getText(),nameText.getText(),patronymicText.getText(),groupText.getText(),list));
                try {
                    table.updateTable();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                newWindow.close();
            }
        });
        GridPane.setHalignment(findButton, HPos.RIGHT);
        gridpane.add(findButton,1,14);

        Scene scene = new Scene(gridpane,250,500);

        newWindow = new Stage();
        newWindow.setTitle("Добавить");
        newWindow.setScene(scene);
        newWindow.setResizable(false);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(stage);
        newWindow.show();
    }
}
