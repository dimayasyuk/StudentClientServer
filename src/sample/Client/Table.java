package sample.Client;


import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.Client.Client;
import sample.Constants;
import sample.Student;

import java.io.IOException;
import java.util.List;

/**
 * Created by dmitriy on 7.5.18.
 */
public class Table {
    private TableView<Student> table;
    private ObservableList<Student> studentsTable = FXCollections.observableArrayList();
    private int studentOnPage;
    private int currentPage;
    private int studentsSize;
    private Label numberItemsOfPage;
    private Label numberPages;
    private TextField numberItemsT;;
    private Client client;
    private String name;
    public Table(String name){
        studentsTable = FXCollections.observableArrayList();
        studentOnPage = 2;
        currentPage = 1;
        this.name = name;
    }
    public int getStudentOnPage() {
        return studentOnPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public TableView<Student> getTable() {
        return table;
    }

    public void setStudentsTable(List<Student> studentsTable) {
        this.studentsTable = FXCollections.observableArrayList(studentsTable);
    }

    public void setStudentOnPage(int studentOnPage) {
        this.studentOnPage = studentOnPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setStudentsSize(int studentsSize) {
        this.studentsSize = studentsSize;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public VBox createPage() {
            VBox vBox = new VBox();
            table = new TableView<>();
            table.setItems(studentsTable);
            table.setEditable(true);
            TableColumn<Student, String> Fio = new TableColumn<>("Фио");
            Fio.setMinWidth(300);
            Fio.setCellValueFactory((TableColumn.CellDataFeatures<Student, String> p) -> {
                String val = p.getValue().getFirstName() + " " + p.getValue().getLastName()
                        + " " + p.getValue().getPatronymicName();
                return new ReadOnlyStringWrapper(val);
            });

            TableColumn<Student, String> group = new TableColumn<>("Группа");
            group.setMinWidth(100);
            group.setCellValueFactory(new PropertyValueFactory<>("group"));

            TableColumn<Student, String> work = new TableColumn<>("Общественная работа");
            TableColumn<Student, String> semester1 = createColumn(1);
            TableColumn<Student, String> semester2 = createColumn(2);
            TableColumn<Student, String> semester3 = createColumn(3);
            TableColumn<Student, String> semester4 = createColumn(4);
            TableColumn<Student, String> semester5 = createColumn(5);
            TableColumn<Student, String> semester6 = createColumn(6);
            TableColumn<Student, String> semester7 = createColumn(7);
            TableColumn<Student, String> semester8 = createColumn(8);
            TableColumn<Student, String> semester9 = createColumn(9);
            TableColumn<Student, String> semester10 = createColumn(10);
            work.getColumns().addAll(semester1, semester2, semester3, semester4, semester5, semester6, semester7, semester8, semester9, semester10);
            table.getColumns().addAll(Fio, group, work);

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.BASELINE_CENTER);
            Button firstPage = createButton("Первая страница","image/first.jpeg");
            firstPage.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        firstPage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            Button lastPage = createButton("Последняя страница","image/last.jpeg");
            lastPage.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        lastPage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            Button nextPage = createButton("Следующая страница","image/next.jpeg");
            nextPage.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        nextPage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            Button prevPage = createButton("Предыдущая страница","image/prev.jpeg");
            prevPage.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        prevPage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });


            HBox hboxNumberItems = new HBox();
            hboxNumberItems.setAlignment(Pos.BASELINE_CENTER);
            numberItemsOfPage = new Label("Количество записей на странице:" + 0);
            hboxNumberItems.getChildren().add(numberItemsOfPage);


            HBox hboxNumberPages = new HBox();
            hboxNumberPages.setAlignment(Pos.BASELINE_CENTER);
            numberPages = new Label("Количество страниц:"+ 0 + '/' + 0);
            hboxNumberPages.getChildren().add(numberPages);

            HBox hboxText = new HBox();
            hboxText.setAlignment(Pos.BASELINE_CENTER);
            Label numberItemsL = new Label("Введите количество записей на странице:");
            numberItemsT = new TextField();
            numberItemsT.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String str = numberItemsT.getText();
                    if (str == null || str.isEmpty()){
                        return;
                    }else {
                        for (int i = 0; i < str.length(); i++) {
                            if (!Character.isDigit(str.charAt(i))) return;
                        }
                    }
                    try {
                        changeItems();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            numberItemsT.setMinSize(20,20);
            hboxText.getChildren().addAll(numberItemsL,numberItemsT);

            hBox.getChildren().addAll(firstPage,prevPage,nextPage,lastPage);

            vBox.getChildren().addAll(table,hBox,hboxNumberItems,hboxNumberPages,hboxText);

            return vBox;
    }
    private void firstPage() throws IOException, ClassNotFoundException {
        if(client!=null){
            if (client.isConnect()){
                client.sendData(Constants.FIRST_PAGE);
                client.sendData(name);
                updateTable();
            }
        }
    }
    private void lastPage() throws IOException, ClassNotFoundException {
        if(client!=null) {
            if (client.isConnect()) {
                client.sendData(Constants.LAST_PAGE);
                client.sendData(name);
                updateTable();
            }
        }
    }
    private void nextPage() throws IOException, ClassNotFoundException {
        if(client!=null) {
            if (client.isConnect()) {
                client.sendData(Constants.NEXT_PAGE);
                client.sendData(name);
                client.sendData(currentPage);
                updateTable();
            }
        }
    }
    private  void prevPage() throws IOException, ClassNotFoundException {
        if(client!=null) {
            if (client.isConnect()) {
                client.sendData(Constants.PREV_PAGE);
                client.sendData(name);
                client.sendData(currentPage);
                updateTable();
            }
        }
    }
    private void changeItems() throws IOException, ClassNotFoundException {
        client.sendData(Constants.CHANGE_ITEMS);
        client.sendData(name);
        studentOnPage = (Integer.parseInt(numberItemsT.getText()));
        updateTable();
    }
    public void updateTable() throws IOException, ClassNotFoundException {
        client.sendData(studentOnPage);
        client.updateTable(name);
        table.setItems(studentsTable);
        numberItemsOfPage.setText("Количество записей на странице:"+table.getItems().size());
        numberPages.setText("Количество страниц:"+ currentPage + '/' + (int)Math.ceil((double) studentsSize / studentOnPage));;

    }
    private Button createButton(String text, String url){
        Button button = new Button(text,new ImageView(url));
        button.setTooltip(new Tooltip(text));
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        return button;
    }
    private TableColumn<Student, String> createColumn(int index) {
        TableColumn<Student, String> sem = new TableColumn<>(index + " семестр");
        sem.setMinWidth(100);
        sem.setCellValueFactory((TableColumn.CellDataFeatures<Student, String> p) -> {
            String val = p.getValue().getWork().get(index - 1).getNameWork();
            return new ReadOnlyStringWrapper(val);
        });
        return sem;
    }

}
