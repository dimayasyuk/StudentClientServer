package sample.Server;

import sample.Client.FileWorker;
import sample.Constants;
import sample.Student;
import sample.StudentDb;
import sample.Work;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dmitriy on 5.6.18.
 */
public class Session implements Runnable{
    private Server server;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private StudentDb studentDb;
    private StudentDb findStudentDb;
    public Session(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        studentDb = new StudentDb();
        findStudentDb = new StudentDb();
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            server.log("Новая сессия");
            runSession();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void stop() {
        try {
            if(input!=null) {
                input.close();
            }
            if(output!=null){
                output.close();
            }
        } catch (IOException e) {
            server.log("Ошибка закрытия соединения");
        }finally {
            Thread thread = Thread.currentThread();
            thread.interrupt();
        }
    }
    private void runSession() throws IOException, ClassNotFoundException {
        while (true){
            String action = "";
            if(!server.isRun() || action.equals(Constants.Exit)){
                break;
            }
            action = (String) input.readObject();
            switch (action){
                case Constants.ADD_STUDENT:
                    server.log("Добавление студента");
                    addStudent();
                    break;
                case Constants.DELETE_STUDENT:
                    server.log("Удаление найденных");
                    deleteStudent();
                    break;
                case Constants.FIRST_PAGE:
                    server.log("На первую страницу");
                    firstPage();
                    break;
                case Constants.LAST_PAGE:
                    server.log("На последнюю страницу");
                    lastPage();
                    break;
                case Constants.PREV_PAGE:
                    server.log("На предыдущую страницу");
                    prevPage();
                    break;
                case Constants.NEXT_PAGE:
                    server.log("На следующую страницу");
                    nextPage();
                    break;
                case Constants.DELETE_BY_GROUP:
                    server.log("Удаление по ФИО и группе");
                    deleteByGroup();
                    break;
                case Constants.DELETE_BY_WORK:
                    server.log("Удаление по ФИО и виду общественной работы");
                    deleteByWork();
                    break;
                case Constants.DELETE_BY_NUMBER_WORK:
                    server.log("Удаление по ФИО и количеству общественных работ");
                    deleteByNumberWork();
                    break;
                case Constants.FIND_BY_WORK:
                    server.log("Поиск по ФИО и виду общественной работы");
                    findByWork();
                    break;
                case Constants.FIND_BY_GROUP:
                    server.log("Поиск по ФИО и группе");
                    findByGroup();
                    break;
                case Constants.FIND_BY_NUMBER_WORK:
                    server.log("Поиск по ФИО и количеству общественных работ");
                    findByNumberWork();
                    break;
                case Constants.OPEN_FILE:
                    server.log("Попытка чтения файла");
                    openFile();
                    break;
                case Constants.SAVE_FILE:
                    server.log("Попытка сохранения файла");
                    saveFile();
                    break;
                case Constants.CHANGE_ITEMS:
                    server.log("Изменение количества студентов на странице");
                    changeItems();
                    break;
            }
        }
        server.log("Отсоединение клиента");
    }
    private void changeItems() throws IOException, ClassNotFoundException {
        String name = (String)(input.readObject());
        int studentOnPage = (int)(input.readObject());
        if(name.equals(Constants.MAIN_TABLE)){
            int currentPage = (int)Math.ceil((double) studentDb.getStudents().size() / studentOnPage);
            sendResponse(studentDb,currentPage,studentOnPage);
        }else {
            int currentPage = (int)Math.ceil((double) findStudentDb.getStudents().size() / studentOnPage);
            sendResponse(findStudentDb,currentPage,studentOnPage);
        }

    }
    private void openFile() throws IOException, ClassNotFoundException {
        File file = (File)(input.readObject());
        studentDb.setStudents(new ArrayList<>());
        FileWorker fileWorker = new FileWorker(server,studentDb);
        studentDb = fileWorker.openFile(file);
        int studentOnPage = (int)(input.readObject());
        int currentPage = (int)Math.ceil((double) studentDb.getStudents().size() / studentOnPage);
        sendResponse(studentDb,currentPage,studentOnPage);
    }
    private void saveFile() throws IOException, ClassNotFoundException {
        File file = (File)(input.readObject());
        FileWorker fileWorker = new FileWorker(server,studentDb);
        fileWorker.saveFile(file);
    }
    private void addStudent(){
        try {
            Student student = (Student) input.readObject();
            studentDb.addStudent(student);
            int studentOnPage = (int)(input.readObject());
            int currentPage = (int)Math.ceil((double) studentDb.getStudents().size() / studentOnPage);
            sendResponse(studentDb,currentPage,studentOnPage);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void deleteStudent() throws IOException, ClassNotFoundException {
        List<Student> students = (ArrayList<Student>)input.readObject();
        for(Student student:students){
            if(studentDb.getStudents().contains(student))
                System.out.println("Contains");
        }
        for (Student studentDelete:students) {
            Iterator<Student> it  = studentDb.getStudents().iterator();
            while (it.hasNext()) {
                Student student = it.next();
                if (student.getFirstName().equals(studentDelete.getFirstName()) && student.getLastName().equals(studentDelete.getLastName())
                        && student.getPatronymicName().equals(studentDelete.getPatronymicName()) && student.getGroup().equals(studentDelete.getGroup())) {
                    it.remove();
                }
            }
        }
        int studentOnPage = (int)(input.readObject());
        int currentPage = (int)Math.ceil((double) studentDb.getStudents().size() / studentOnPage);
        sendResponse(studentDb,currentPage,studentOnPage);
    }
    private void findByGroup() throws IOException, ClassNotFoundException {
        int count = 0;
        int studentOnPage = 0;
        int currentPage = 0;
        Student studentDelete = (Student)input.readObject();
        Iterator<Student> it  = studentDb.getStudents().iterator();
        findStudentDb = new StudentDb();
        while (it.hasNext()){
            Student student = it.next();
            if (student.getFirstName().equals(studentDelete.getFirstName()) && student.getLastName().equals(studentDelete.getLastName())
                    && student.getPatronymicName().equals(studentDelete.getPatronymicName()) && student.getGroup().equals(studentDelete.getGroup())) {
                findStudentDb.addStudent(student);
                count++;
            }
        }
        output.writeObject(count);
        output.writeObject(findStudentDb.getStudents());
        studentOnPage = (int)(input.readObject());
        currentPage = (int)Math.ceil((double) findStudentDb.getStudents().size() / studentOnPage);
        sendResponse(findStudentDb,currentPage,studentOnPage);
    }
    private void findByWork() throws IOException, ClassNotFoundException {
        int count = 0;
        int studentOnPage = 0;
        int currentPage = 0;
        Student studentDelete = (Student)input.readObject();
        String workDelete = (String)input.readObject();
        Iterator<Student> it  = studentDb.getStudents().iterator();
        findStudentDb = new StudentDb();
        while (it.hasNext()) {
            Student student = it.next();
            if (student.getFirstName().equals(studentDelete.getFirstName()) && student.getLastName().equals(studentDelete.getLastName())
                    && student.getPatronymicName().equals(studentDelete.getPatronymicName())) {
                for (Work work : student.getWork()) {
                    if (work.getNameWork().equals(workDelete)) {
                        findStudentDb.addStudent(student);
                        count++;
                        break;
                    }
                }
            }
        }
        output.writeObject(count);
        output.writeObject(findStudentDb.getStudents());
        studentOnPage = (int)(input.readObject());
        currentPage = (int)Math.ceil((double) findStudentDb.getStudents().size() / studentOnPage);
        sendResponse(findStudentDb,currentPage,studentOnPage);
    }
    private void findByNumberWork() throws IOException, ClassNotFoundException {
        int count = 0;
        int students = 0;
        int studentOnPage = 0;
        int currentPage = 0;
        Student studentDelete = (Student)input.readObject();
        int one = (int)input.readObject();
        int two = (int)input.readObject();
        Iterator<Student> it  = studentDb.getStudents().iterator();
        findStudentDb = new StudentDb();
        while (it.hasNext()) {
            Student student = it.next();
            if (student.getFirstName().equals(studentDelete.getFirstName()) && student.getLastName().equals(studentDelete.getLastName())
                    && student.getPatronymicName().equals(studentDelete.getPatronymicName())) {
                for (Work work:student.getWork()) {
                    if (!work.getNameWork().equals("")) {
                        count++;
                    }
                }
                if((one<=count) && (count<=two)){
                    findStudentDb.addStudent(student);
                    students++;
                }
            }
            count = 0;
        }
        output.writeObject(students);
        output.writeObject(findStudentDb.getStudents());
        studentOnPage = (int)(input.readObject());
        currentPage = (int)Math.ceil((double) findStudentDb.getStudents().size() / studentOnPage);
        sendResponse(findStudentDb,currentPage,studentOnPage);
    }
    private void deleteByWork() throws IOException, ClassNotFoundException {
        int count = 0;
        int studentOnPage = 0;
        int currentPage = 0;
        Student studentDelete = (Student)input.readObject();
        String workDelete = (String)input.readObject();
        Iterator<Student> it  = studentDb.getStudents().iterator();
        while (it.hasNext()) {
            Student student = it.next();
            if (student.getFirstName().equals(studentDelete.getFirstName()) && student.getLastName().equals(studentDelete.getLastName())
                    && student.getPatronymicName().equals(studentDelete.getPatronymicName())) {
                for (Work work:student.getWork()) {
                    if (work.getNameWork().equals(workDelete)) {
                        it.remove();
                        count++;
                        break;
                    }
                }
            }
        }
        output.writeObject(count);
        studentOnPage = (int)(input.readObject());
        currentPage = (int)Math.ceil((double) studentDb.getStudents().size() / studentOnPage);
        sendResponse(studentDb,currentPage,studentOnPage);
    }
    private void deleteByNumberWork() throws IOException, ClassNotFoundException {
        int count = 0;
        int students = 0;
        int studentOnPage = 0;
        int currentPage = 0;
        Student studentDelete = (Student)input.readObject();
        int one = (int)input.readObject();
        int two = (int)input.readObject();
        Iterator<Student> it  = studentDb.getStudents().iterator();
        while (it.hasNext()) {
            Student student = it.next();
            if (student.getFirstName().equals(studentDelete.getFirstName()) && student.getLastName().equals(studentDelete.getLastName())
                    && student.getPatronymicName().equals(studentDelete.getPatronymicName())) {
                for (Work work:student.getWork()) {
                    if (!work.getNameWork().equals("")) {
                        count++;
                    }
                }
                if((one<=count) && (count<=two)){
                    it.remove();
                    students++;
                }
            }
            count = 0;
        }
        output.writeObject(students);
        studentOnPage = (int)(input.readObject());
        currentPage = (int)Math.ceil((double) studentDb.getStudents().size() / studentOnPage);
        sendResponse(studentDb,currentPage,studentOnPage);
    }
    private void deleteByGroup() throws IOException, ClassNotFoundException {
        int count = 0;
        int studentOnPage = 0;
        int currentPage = 0;
        Student studentDelete = (Student)input.readObject();
        Iterator<Student> it  = studentDb.getStudents().iterator();
        while (it.hasNext()){
            Student student = it.next();
            if (student.getFirstName().equals(studentDelete.getFirstName()) && student.getLastName().equals(studentDelete.getLastName())
                    && student.getPatronymicName().equals(studentDelete.getPatronymicName()) && student.getGroup().equals(studentDelete.getGroup())) {
                it.remove();
                count++;
            }
        }
        output.writeObject(count);
        studentOnPage = (int)(input.readObject());
        currentPage = (int)Math.ceil((double) studentDb.getStudents().size() / studentOnPage);
        sendResponse(studentDb,currentPage,studentOnPage);
    }
    private void firstPage() throws IOException, ClassNotFoundException {
        int currentPage = 1;
        String name = (String)(input.readObject());
        int studentOnPage = (int)(input.readObject());
        if(name.equals(Constants.MAIN_TABLE)){
            sendResponse(studentDb,currentPage,studentOnPage);
        }else{
            sendResponse(findStudentDb,currentPage,studentOnPage);
        }
    }
    private void lastPage() throws IOException, ClassNotFoundException {
        String name = (String)(input.readObject());
        int studentOnPage = 0;
        int currentPage = 0;
        if(name.equals(Constants.MAIN_TABLE)){
            studentOnPage = (int)(input.readObject());
            currentPage = (int)Math.ceil((double) studentDb.getStudents().size() / studentOnPage);
            sendResponse(studentDb,currentPage,studentOnPage);
        }else{
            studentOnPage = (int)(input.readObject());
            currentPage = (int)Math.ceil((double) findStudentDb.getStudents().size() / studentOnPage);
            sendResponse(findStudentDb,currentPage,studentOnPage);
        }
    }
    private void nextPage() throws IOException, ClassNotFoundException {
        String name = (String)(input.readObject());
        int currentPage = (int)(input).readObject();
        int studentOnPage = (int)(input.readObject());
        if(name.equals(Constants.MAIN_TABLE)){
            if(currentPage < (int)Math.ceil((double) studentDb.getStudents().size() / studentOnPage)){
                currentPage++;
            }
            sendResponse(studentDb,currentPage,studentOnPage);
        }else{
            if(currentPage < (int)Math.ceil((double) findStudentDb.getStudents().size() / studentOnPage)){
                currentPage++;
            }
            sendResponse(findStudentDb,currentPage,studentOnPage);
        }

    }
    private void prevPage() throws IOException, ClassNotFoundException {
        String name = (String)(input.readObject());
        int currentPage = (int)(input).readObject();
        int studentOnPage = (int)(input.readObject());
        if(currentPage > 1){
            currentPage--;
        }
        if(name.equals(Constants.MAIN_TABLE)){
            sendResponse(studentDb,currentPage,studentOnPage);
        }else{
            sendResponse(findStudentDb,currentPage,studentOnPage);
        }
    }
    private void sendResponse(StudentDb studentDb,int currentPage,int studentOnPage) throws IOException, ClassNotFoundException {
        if(currentPage == 0){
            currentPage = 1;
        }
        List<Student> students = studentDb.getStudents();
        List<Student> sendStudents = new ArrayList<>();
        int firstStudent = studentOnPage*(currentPage-1);
        for(int number = firstStudent;number<firstStudent + studentOnPage && number<students.size();number++){
            sendStudents.add(students.get(number));
        }
        output.writeObject(sendStudents);
        output.writeObject(students.size());
        output.writeObject(currentPage);
        output.writeObject(studentOnPage);
        output.flush();
    }
}
