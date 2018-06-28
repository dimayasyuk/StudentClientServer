package sample.Server;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by dmitriy on 5.6.18.
 */
public class Server extends Application{
    private int port = 8080;
    private TextArea textArea;
    private ServerSocket socketListener;
    private boolean run = false;
    SessionsManager sessionsManager = new SessionsManager();

    public boolean isRun() {
        return run;
    }

    public void log(String text){
        textArea.appendText(text + '\n');
    }

    private void runServer(){
        try {
            log("Запуск сервера");
            socketListener = new ServerSocket(port);
            if (socketListener!=null){
                run = true;
            }
            while (run){
                if(socketListener.isClosed()){
                    run = false;
                    return;
                }
                Socket client = socketListener.accept();
                Session session = new Session(client,this);
                sessionsManager.addSession(session);
                new Thread(session).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private ToolBar createToolBar(){
        ToolBar toolBar = new ToolBar();

        Button buttonRun = createButton("Run");
        Button buttonStop = createButton("Stop");

        buttonRun.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (run)
                    return;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runServer();
                    }
                }).start();
            }
        });

        buttonStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(run) {
                        socketListener.close();
                        run = false;
                        for(Session session:sessionsManager.getSessions()){
                            session.stop();
                        }
                        textArea.appendText("Остановка сервера");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        toolBar.getItems().addAll(buttonRun,buttonStop);

        return toolBar;
    }
    private Button createButton(String text){
        Button button = new Button(text);
        button.setTooltip(new Tooltip(text));
        return button;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Server");

        textArea = new TextArea();
        textArea.setMinSize(400,400);
        ScrollPane scrollPane = new ScrollPane(textArea);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(scrollPane);
        borderPane.setTop(createToolBar());

        primaryStage.setScene(new Scene(borderPane,400,400));
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
