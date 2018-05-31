/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Domain.Person;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Steven
 */
public class XMLPersonManager {

    //variables
    private Document document;
    private Element root;
    private String path;
    private boolean successAddSon;

    public XMLPersonManager(String path) throws JDOMException, IOException {
        this.path = path;

        File fileStudent = new File(this.path);
        if (fileStudent.exists()) {
            //1. El archivo ya existe, entonces lo cargo en memoria
            //toma la estructura de datos y la carga en memoria
            SAXBuilder saxBuilder = new SAXBuilder();
            saxBuilder.setIgnoringElementContentWhitespace(true); //ignora espacios en blanco

            //cargar en memoria
            this.document = saxBuilder.build(this.path);
            this.root = this.document.getRootElement();
        } else {
            //2. No existe el documento, entonces lo creo y luego lo cargo en memoria
            //creamos el elemento raiz
            this.root = new Element("persons");
            this.root.setAttribute("identification", "num");

            //creamos el documento
            this.document = new Document(this.root);

            //guardamos el documento
            storeXML();
        }
    }//end method

    //almacena en disco duro nuestro documento XML en la ruta especificada
    private void storeXML() throws FileNotFoundException, IOException {
        XMLOutputter xmlOutputter = new XMLOutputter();
        xmlOutputter.output(this.document, new PrintWriter(this.path));
    }//end method

    //metodo para insertar una persona nueva
    public void insertPerson(Person person) throws IOException {
        //insertamos en el documento en memoria
        //crear el student
        Element ePerson = new Element("person");
        //agregamos un atributo
        ePerson.setAttribute("identification", String.valueOf(person.getId()));

        //crear el nombre
        Element eName = new Element("name");
        eName.addContent(person.getName());

        //crear los apellidos
        Element eLastName1 = new Element("firstLastname");
        eLastName1.addContent(person.getLastname1());

        Element eLastName2 = new Element("secondLastname");
        eLastName2.addContent(person.getLastname2());

        //crear el elemento fecha de nacimiento
        Element eBornDate = new Element("bornDate");
        eBornDate.addContent(person.getBornDate());

        //crear el elemento pais
        Element eCountry = new Element("country");
        eCountry.addContent(person.getCountry());

        //crear el elemento cedula del padre
        Element eFatherID = new Element("fatherId");
        eFatherID.addContent(String.valueOf(person.getFatherId()));

        //agregar al elemento student
        ePerson.addContent(eName);
        ePerson.addContent(eLastName1);
        ePerson.addContent(eLastName2);
        ePerson.addContent(eBornDate);
        ePerson.addContent(eCountry);
        ePerson.addContent(eFatherID);

        //agregar a root
        this.root.addContent(ePerson);

        //guardo todo
        storeXML();
    }
    
    
    //agrega hijos
    public boolean insertSon(int identification, Person person) throws IOException {
        this.successAddSon = false;
        search(this.root, String.valueOf(identification), person);
        return this.successAddSon;
    }

    //busca el id del padre
    public void search(Element parent, String id, Person person) throws IOException {
        //se pregunta si es el id que busco
        if (parent.getAttributeValue("identification").equals(id)) {
            //agrega el contenido al elemento
            
            Element eSon = new Element("person");
            eSon.setAttribute("identification", String.valueOf(person.getId()));

            //crear el nombre
            Element eSonName = new Element("name");
            eSonName.addContent(person.getName());

            //crear los apellidos
            Element eSonLastName1 = new Element("firstLastname");
            eSonLastName1.addContent(person.getLastname1());

            Element eSonLastName2 = new Element("secondLastname");
            eSonLastName2.addContent(person.getLastname2());

            //crear el elemento fecha de nacimiento
            Element eSonBornDate = new Element("bornDate");
            eSonBornDate.addContent(person.getBornDate());

            //crear el elemento pais
            Element eSonCountry = new Element("country");
            eSonCountry.addContent(person.getCountry());

            //crear el elemento cedula del padre
            Element eSonFatherID = new Element("fatherId");
            eSonFatherID.addContent(String.valueOf(person.getFatherId()));

            //agregar al elemento student
            eSon.addContent(eSonName);
            eSon.addContent(eSonLastName1);
            eSon.addContent(eSonLastName2);
            eSon.addContent(eSonBornDate);
            eSon.addContent(eSonCountry);
            eSon.addContent(eSonFatherID);

            parent.addContent(eSon);
            storeXML();
            this.successAddSon = true;
        } else {
            //si no lo encuentra, retorne cada uno de los hijos
            List childrenList = parent.getChildren("person");
            if (childrenList.size() > 0) {
                for(Object object : childrenList){
                    Element child = (Element) object;
                    search(child, id, person);
                }
            }
        }
    }

    //metodo para obtener todos los objetos del xml
    public Person[] getAllStudents() {
        //obtenemos la cantidad de estudiantes
        int personQuantity = this.root.getContentSize();
        Person[] studentsArray = new Person[personQuantity];

        //obtenemos una lista con todos los elementos del root
        List elementList = this.root.getChildren();
        //recorrer la lista para ir creando el arreglo
        int count = 0;
        for (Object currentObject : elementList) {//recorre todos los elementos de la lista y cada vez guarda el objeto en currentObject
            //transformo de object a element
            Element currentElement = (Element) currentObject;
            //crear el estudiante
            Person currentStudent = new Person();
            //establezco el id
            currentStudent.setId(Integer.parseInt(currentElement.getAttributeValue("identification")));
            //establezco el nombre
            currentStudent.setName(currentElement.getChild("name").getValue());
            //establezco los apellidos
            currentStudent.setLastname1(currentElement.getChild("firstLastname").getValue());
            currentStudent.setLastname2(currentElement.getChild("secondLastname").getValue());
            //establezco la fecha de nacimiento
            currentStudent.setBornDate(currentElement.getChild("bornDate").getValue());
            //establezco el pais
            currentStudent.setCountry(currentElement.getChild("country").getValue());
            //establezco la cedula del padre
            currentStudent.setFatherId(Integer.parseInt(currentElement.getChild("fatherId").getValue()));
            
            studentsArray[count++] = currentStudent;
        }//end for
        return studentsArray;
    }

    public boolean delete(String identification) throws IOException {
        List listElementos = this.root.getChildren();
        int cont = 0;
        for (Object objectActual : listElementos) {
            Element elementoActual = (Element) objectActual;
            if (elementoActual.getAttributeValue("identification").equals(identification)) {
                this.root.removeContent(cont);
                storeXML();
                return true;
            }
            cont++;
        }
        return false;
    }
}
