package sample.Client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.Constants;

import java.io.File;
import java.io.IOException;

/**
 * Created by dmitriy on 4.6.18.
 */
public class MyView {
    private Table table;
    private Table findTable;
    private MenuBar menuBar;
    private ToolBar toolBar;
    private Stage primaryStage;
    private BorderPane borderPane;
    private TextField host;
    private TextField port;
    private Client client;
    public MyView(Stage stage){
        this.primaryStage = stage;
        primaryStage.setTitle("StudentTable");
        borderPane = new BorderPane();
        table = new Table(Constants.MAIN_TABLE);
        findTable = new Table(Constants.FIND_TABLE);
        menuBar = createMenuBar();
        toolBar = createToolBar();
        borderPane.setTop(menuBar);
        borderPane.setBottom(toolBar);
        borderPane.setCenter(table.createPage());
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setScene(new Scene(borderPane,primaryScreenBounds.getWidth(),primaryScreenBounds.getHeight()));
        primaryStage.show();
    }
    public Table getTable(){
        return table;
    }

    public Table getFindTable() {
        return findTable;
    }

    private void connect(){
        client = new Client(this,host.getText(),Integer.parseInt(port.getText()));
        table.setClient(client);
        findTable.setClient(client);
    }
    private Button createToolButton(String text, String url){
        Button button = new Button(text,new ImageView(url));
        button.setTooltip(new Tooltip(text));
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        return button;
    }
    private void showAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");

        alert.setHeaderText(null);
        alert.setContentText("Вы не подключены к серверу");

        alert.showAndWait();
    }
    private void openFile() throws IOException, ClassNotFoundException {
        if(client.isConnect()){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt", "*.xml"));
            File file = fileChooser.showOpenDialog(primaryStage);
            if(file!=null){
                table.getClient().sendData(Constants.OPEN_FILE);
                table.getClient().sendData(file);
                table.updateTable();
            }
        }else {
            showAlert();
        }
    }
    private void saveFile() throws IOException, ClassNotFoundException {
        if(client.isConnect()){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt", "*.xml"));
            File file = fileChooser.showOpenDialog(primaryStage);
            if(file!=null){
                table.getClient().sendData(Constants.SAVE_FILE);
                table.getClient().sendData(file);
            }
        }else {
            showAlert();
        }
    }
    private void exit(){
        table.getClient().stop();
        System.exit(0);
    }
    private void add(){
        if(client.isConnect()){
            new AddDialog(primaryStage,table);
        }
        else {
            showAlert();
        }
    }
    private void deleteByGroup(){
        if(client.isConnect()){
            new DeleteFioAndGroup(primaryStage,table);
        }
        else {
            showAlert();
        }
    }
    private void deleteByWork(){
        if(client.isConnect()){
            new DeleteFioAndWork(primaryStage,table);
        }
        else {
            showAlert();
        }
    }
    private void deleteByNumberWork(){
        if(client.isConnect()){
            new DeleteFioAndNumberWork(primaryStage,table);
        }
        else {
            showAlert();
        }
    }
    private void findByGroup(){
        if(client.isConnect()){
            new FindFioAndGroup(primaryStage,table,findTable);
        }
        else {
            showAlert();
        }
    }
    private void findByWork(){
        if(client.isConnect()){
            new FindFioAndWork(primaryStage,table,findTable);
        }
        else {
            showAlert();
        }
    }
    private void findByNumberWork(){
        if(client.isConnect()){
            new FindFioAndNumberWork(primaryStage,table,findTable);
        }
        else {
            showAlert();
        }
    }
    private ToolBar createToolBar(){
        ToolBar newToolBar = new ToolBar();

        Button addButton = createToolButton("Добавить","image/add.jpeg");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                add();
            }
        });


        Button findFioAndGroup = createToolButton("Поиск по Фио и группе","image/find1.jpeg");
        findFioAndGroup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                findByGroup();
            }
        });

        Button findFioAndWork = createToolButton("Поиск по Фио и виду общественной работы","image/find2.png");
        findFioAndWork.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                findByWork();
            }
        });

        Button findFioAndNumberWork = createToolButton("Поиск по Фио и количеству работ","image/find3.png");
        findFioAndNumberWork.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                findByNumberWork();
            }
        });

        Button deleteFioAndGroup = createToolButton("Удаление по Фио и группе","image/delete1.jpeg");
        deleteFioAndGroup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteByGroup();
            }
        });

        Button deleteFioAndWork = createToolButton("Удаление по Фио и виду общественной работы","image/delete2.jpeg");
        deleteFioAndWork.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteByWork();
            }
        });

        Button deleteFioAndNumberWork = createToolButton("Удаление по Фио и количеству работ","image/delete3.jpeg");
        deleteFioAndNumberWork.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteByNumberWork();
            }
        });

        newToolBar.getItems().addAll(addButton,findFioAndGroup,findFioAndWork,findFioAndNumberWork,deleteFioAndGroup,deleteFioAndWork,deleteFioAndNumberWork);

        return newToolBar;
    }

    private MenuBar createMenuBar(){
        MenuBar newMenuBar = new MenuBar();
        Menu file = new Menu("Файл");

        MenuItem open = new MenuItem("Открыть",new ImageView("image/open.png"));
        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    openFile();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        MenuItem save = new MenuItem("Сохранить",new ImageView("image/save.png"));
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    saveFile();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        MenuItem exit = new MenuItem("Выйти",new ImageView("image/exit.jpg"));
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exit();
            }
        });

        Menu edit = new Menu("Изменить");
        Menu addMenu = new Menu("Добавить");
        MenuItem addOne = new MenuItem("Добавить студента");
        addOne.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                add();
            }
        });

        addMenu.getItems().addAll(addOne);

        Menu findMenu = new Menu("Поиск");
        MenuItem findFioAndGroup = new MenuItem("Поиск по Фио и группе");
        findFioAndGroup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                findByGroup();
            }
        });
        MenuItem findFioAndWork = new MenuItem("Поиск по Фио и виду общественной работы");
        findFioAndWork.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                findByWork();
            }
        });
        MenuItem findFioAndNumberWork = new MenuItem("Поиск по Фио и количеству работ");
        findFioAndNumberWork.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                findByNumberWork();
            }
        });

        findMenu.getItems().addAll(findFioAndGroup,findFioAndWork,findFioAndNumberWork);


        Menu deleteMenu = new Menu("Удалить");
        MenuItem deleteFioAndGroup = new MenuItem("Удаление по Фио и группе");
        deleteFioAndGroup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteByGroup();
            }
        });
        MenuItem deleteFioAndWork = new MenuItem("Удаление по Фио и виду общественной работы");
        deleteFioAndWork.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteByWork();
            }
        });
        MenuItem deleteFioAndNumberWork = new MenuItem("Удаление по Фио и количеству работ");
        deleteFioAndNumberWork.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteByNumberWork();
            }
        });

        deleteMenu.getItems().addAll(deleteFioAndGroup,deleteFioAndWork,deleteFioAndNumberWork);

        Menu connect = new Menu("Подключиться");
        MenuItem connectServer = new MenuItem("Подключиться к серверу");
        connectServer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GridPane gridpane = new GridPane();
                Stage newWindow = new Stage();
                gridpane.setPadding(new Insets(5));
                gridpane.setHgap(5);
                gridpane.setVgap(5);
                ColumnConstraints column1 = new ColumnConstraints(50);
                ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
                gridpane.getColumnConstraints().addAll(column1, column2);

                Label hostLabel = new Label("Host:");
                host = new TextField("127.0.0.1");
                Label portLabel = new Label("Port:");
                port = new TextField("8080");

                GridPane.setHalignment(hostLabel, HPos.RIGHT);
                gridpane.add(hostLabel,0,0);
                GridPane.setHalignment(portLabel, HPos.RIGHT);
                gridpane.add(portLabel,0,1);


                GridPane.setHalignment(host, HPos.LEFT);
                gridpane.add(host,1,0);
                GridPane.setHalignment(port, HPos.LEFT);
                gridpane.add(port,1,1);
                Button button = new Button("Connect");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                                connect();
                            newWindow.close();
                    }
                });

                GridPane.setHalignment(button, HPos.RIGHT);
                gridpane.add(button,1,2);

                newWindow.setTitle("Подключение");
                newWindow.setScene(new Scene(gridpane,200,100));
                newWindow.setResizable(false);
                newWindow.initModality(Modality.WINDOW_MODAL);
                newWindow.initOwner(primaryStage);
                newWindow.show();
            }
        });
        connect.getItems().add(connectServer);

        file.getItems().addAll(open,save,new SeparatorMenuItem(),exit);
        edit.getItems().addAll(addMenu,findMenu,deleteMenu);
        newMenuBar.getMenus().addAll(file,edit,connect);

        return newMenuBar;
    }
}
