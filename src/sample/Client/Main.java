package sample.Client;


import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.PropertyConfigurator;
import sample.StudentDb;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        PropertyConfigurator.configure("log4j.properties");
        StudentDb studentDb = new StudentDb();
        MyView myView = new MyView(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
