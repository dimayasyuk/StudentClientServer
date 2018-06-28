package sample.Client;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by dmitriy on 27.5.18.
 */
// A is B
public abstract class FioAndFork {
    protected Stage stage;
    protected Stage newWindow;
    protected TextField nameText;
    protected TextField fioText;
    protected TextField patronymicText;
    protected TextField workText;
    protected Button button;
    protected Table table;

    public TextField getFioText() {
        return fioText;
    }
    public void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");

        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }
    public void createDialog(String buttonText){
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(5);
        gridpane.setVgap(5);
        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
        column2.setHgrow(Priority.ALWAYS);
        gridpane.getColumnConstraints().addAll(column1, column2);

        Label fioLabel = new Label("Фамилия");
        fioText = new TextField();
        Label nameLabel = new Label("Имя");
        nameText = new TextField();
        Label patronymicLabel = new Label("Отчество");
        patronymicText = new TextField();
        Label workLabel = new Label("Работа");
        workText = new TextField();

        GridPane.setHalignment(fioLabel, HPos.RIGHT);
        gridpane.add(fioLabel,0,0);
        GridPane.setHalignment(nameLabel, HPos.RIGHT);
        gridpane.add(nameLabel,0,1);
        GridPane.setHalignment(patronymicLabel, HPos.RIGHT);
        gridpane.add(patronymicLabel,0,2);
        GridPane.setHalignment(workLabel, HPos.RIGHT);
        gridpane.add(workLabel,0,3);


        GridPane.setHalignment(fioText, HPos.LEFT);
        gridpane.add(fioText,1,0);
        GridPane.setHalignment(nameText, HPos.LEFT);
        gridpane.add(nameText,1,1);
        GridPane.setHalignment(patronymicText, HPos.LEFT);
        gridpane.add(patronymicText,1,2);
        GridPane.setHalignment(workText, HPos.LEFT);
        gridpane.add(workText,1,3);
        button = new Button(buttonText);

        GridPane.setHalignment(button, HPos.RIGHT);
        gridpane.add(button,1,4);

        Scene scene = new Scene(gridpane,250,170);

        newWindow.setTitle(buttonText);
        newWindow.setScene(scene);
        newWindow.setResizable(false);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.show();
    }
    protected abstract int startAlgorithm() throws IOException, ClassNotFoundException;
}
