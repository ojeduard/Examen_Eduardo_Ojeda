package Model;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Department extends AbstractDepartment {
    private String codigo;
    private String nombre;
    private boolean auditado;
    double presupuesto;

    ArrayList<AbstractDepartment> departamentos;

    public Department() {
        this.departamentos = new ArrayList<>();
    }

    public Department(String codigo, String nombre, boolean auditado, double presupuesto) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.auditado = auditado;
        this.presupuesto = presupuesto;
        this.departamentos = new ArrayList<>();
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isAuditado() {
        return auditado;
    }

    public void setAuditado(boolean auditado) {
        this.auditado = auditado;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public ArrayList<AbstractDepartment> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(ArrayList<AbstractDepartment> departamentos) {
        this.departamentos = departamentos;
    }

    @Override
    public void agregar(AbstractDepartment department) {
        this.departamentos.add(department);
    }

    @Override
    public void remover(AbstractDepartment department) {
        this.departamentos.remove(department);
    }

    @Override
    public void obtenerHijo(int x) {
        departamentos.get(x);
    }

    @Override
    public String toString() {
        return "Department{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", auditado=" + auditado +
                ", presupuesto=" + presupuesto +
                ", departamentos=" + departamentos +
                '}';
    }

    @Override
    public boolean crearXML(String filename) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Files.deleteIfExists(Paths.get(filename));

            // creation of root element
            Element root = document.createElement("DEPARTMENTO");
            document.appendChild(root);

            for (AbstractDepartment department : departamentos) {

                Element departmentXML = document.createElement("department");
                root.appendChild(departmentXML);

                Attr attr = document.createAttribute("codigo");
                attr.setValue(department.getCodigo());
                departmentXML.setAttributeNode(attr);


                Element nombre = document.createElement("nombre");
                nombre.appendChild(document.createTextNode(department.getNombre()));
                departmentXML.appendChild(nombre);


                Element auditado = document.createElement("auditado");
                auditado.appendChild(document.createTextNode(String.valueOf(department.isAuditado())));
                departmentXML.appendChild(auditado);


                Element presupuesto = document.createElement("presupuesto");
                presupuesto.appendChild(document.createTextNode(Double.toString(department.getPresupuesto())));
                departmentXML.appendChild(presupuesto);


                // create the xml file
                // transform the DOM Object to an XML File
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource domSource = new DOMSource(document);
                StreamResult streamResult = new StreamResult(new File(filename));

                transformer.transform(domSource, streamResult);

            }
        } catch (ParserConfigurationException e) {
            return false;
        } catch (TransformerException tfe) {
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public boolean cargarXML(String filename) {

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filename);

            NodeList nodeList = document.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;

                    // Get the value of the ID attribute.
                    String codigo = node.getAttributes().getNamedItem("codigo").getNodeValue();

                    // Get the value of all sub-elements.
                    String nombre = elem.getElementsByTagName("nombre")
                            .item(0).getChildNodes().item(0).getNodeValue();
                    boolean auditado = Boolean.parseBoolean(elem.getElementsByTagName("auditado").item(0)
                            .getChildNodes().item(0).getNodeValue());
                    double presupuesto = Double.parseDouble(elem.getElementsByTagName("presupuesto")
                            .item(0).getChildNodes().item(0).getNodeValue());


                    departamentos.add(new Department(codigo, nombre, auditado, presupuesto));

                }
            }
        } catch (ParserConfigurationException e) {
            return false;
        } catch (SAXException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

        return true;

    }

    public static void main(String[] args) {
        AbstractDepartment dep = new Department ("SJO", "Mantenimiento", false, 2000);
        AbstractDepartment dep2 =  new Department ("AFZ", "Limpieza", true, 500);

        dep.agregar(dep2);
        dep.crearXML("Prueba.xml");






    }
}


