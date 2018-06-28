package sample;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitriy on 4.6.18.
 */
public class StudentDb {
    private File file;
    private List<Student> students;
    public StudentDb(){
        students = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public void setStudents(List<Student> students) {
        this.students = new ArrayList<>(students);
    }
    public void addStudent(Student student){
        students.add(student);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getStudentsSize(){
        return students.size();

    }
    public void removeStudent(Student student){
        students.remove(student);
    }
    public void removeAll(List<Student> students){

    }
}
