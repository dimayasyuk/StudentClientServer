package sample.Client;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sample.Server.Server;
import sample.Student;
import sample.StudentDb;
import sample.Work;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Created by dmitriy on 8.5.18.
 */
public class FileWorker {
    private StudentDb studentDb;
    private Server server;

    public FileWorker(Server server, StudentDb studentDb) {
        this.studentDb = studentDb;
        this.server = server;
    }

    public StudentDb openFile(File file) {
            try {
                SAXParserFactory spfac = SAXParserFactory.newInstance();
                SAXParser sp = spfac.newSAXParser();

                DefaultHandler handler = new DefaultHandler() {
                    private Student student;
                    private String temp;

                    @Override
                    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                        temp = "";
                        if (qName.equalsIgnoreCase("STUDENT")) {
                            student = new Student();
                        }

                    }

                    @Override
                    public void endElement(String uri, String localName, String qName) throws SAXException {
                        if (qName.equalsIgnoreCase("students")) {
                        } else if (qName.equalsIgnoreCase("STUDENT")) {
                            studentDb.addStudent(student);
                        } else if (qName.equalsIgnoreCase("FIRSTNAME")) {
                            student.setFirstName(temp);
                        } else if (qName.equalsIgnoreCase("LASTNAME")) {
                            student.setLastName(temp);
                        } else if (qName.equalsIgnoreCase("patronymicName")) {
                            student.setPatronymicName(temp);
                        } else if (qName.equalsIgnoreCase("GROUP")) {
                            student.setGroup(temp);
                        } else if (qName.equalsIgnoreCase("sem1")) {
                            student.addWork(temp);
                        } else if (qName.equalsIgnoreCase("sem2")) {
                            student.addWork(temp);
                        } else if (qName.equalsIgnoreCase("sem3")) {
                            student.addWork(temp);
                        } else if (qName.equalsIgnoreCase("sem4")) {
                            student.addWork(temp);
                        } else if (qName.equalsIgnoreCase("sem5")) {
                            student.addWork(temp);
                        } else if (qName.equalsIgnoreCase("sem6")) {
                            student.addWork(temp);
                        } else if (qName.equalsIgnoreCase("sem7")) {
                            student.addWork(temp);
                        } else if (qName.equalsIgnoreCase("sem8")) {
                            student.addWork(temp);
                        } else if (qName.equalsIgnoreCase("sem9")) {
                            student.addWork(temp);
                        } else if (qName.equalsIgnoreCase("sem10")) {
                            student.addWork(temp);
                        }
                    }

                    @Override
                    public void characters(char[] ch, int start, int length) throws SAXException {
                        temp = new String(ch, start, length);
                    }
                };
                sp.parse(file, handler);
            } catch (Exception e) {
                server.log("Ошибка чтения файла");
                e.printStackTrace();
            }
            return studentDb;
        }

    public void saveFile(File file) {
                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder;

                try {
                    documentBuilder = builderFactory.newDocumentBuilder();
                    Document doc = documentBuilder.newDocument();
                    Element rootElement = doc.createElement("students");
                    doc.appendChild(rootElement);
                    for (Student s : studentDb.getStudents()) {
                        rootElement.appendChild(createStudent(doc, s));

                    }
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transf = transformerFactory.newTransformer();

                    transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                    transf.setOutputProperty(OutputKeys.INDENT, "yes");

                    DOMSource source = new DOMSource(doc);
                    StreamResult files = new StreamResult(file);

                    transf.transform(source, files);

                } catch (Exception ex) {
                    server.log("Ошибка записи файла");
                    ex.printStackTrace();
                }
            }
    private Node createStudent(Document doc, Student s){
        Element student = doc.createElement("student");
        student.appendChild(createStudentElement(doc,"firstname",s.getFirstName()));
        student.appendChild(createStudentElement(doc,"lastname",s.getLastName()));
        student.appendChild(createStudentElement(doc,"patronymicname",s.getPatronymicName()));
        student.appendChild(createStudentElement(doc,"group",s.getGroup()));
        Element work = doc.createElement("work");
        int index = 1;
        for(Work w:s.getWork()){
            String name =  "sem"+index;
            work.appendChild(createStudentElement(doc,name,w.getNameWork()));
            index++;
        }
        student.appendChild(work);
        return student;
    }
    private static Node createStudentElement(Document doc, String name,
                                          String value) {

        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));

        return node;
    }
}
