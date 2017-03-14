package com.jubaka.sors.managed;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by root on 15.02.17.
 */

@Named
@ApplicationScoped
public class ServerArgumentsBean {

    private String uploadPath;
    private Integer portListenTo;


    @PostConstruct
    public void readArguments() {
        try {

            InputStream isArgs =  this.getClass().getResourceAsStream("/server-args/arguments.xml");
            if (isArgs == null) {

                return;
            }
            DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(isArgs);
            Element rootElem =  doc.getDocumentElement();
            NodeList nodeList =  rootElem.getChildNodes();

            String portStr = findInNodeList("port",nodeList);
            String pathStr = findInNodeList("uploadPath",nodeList);

            portListenTo = Integer.parseInt(portStr);
            uploadPath = pathStr;

        }  catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        } catch (SAXException sax) {
            sax.printStackTrace();
        }

    }

    private String findInNodeList(String tagName, NodeList list) {
        for (int i = 0 ; i<list.getLength() ; i++) {
            Node n = list.item(i);
            if (n instanceof Element) {
                 Element elem = (Element) n;
                if (elem.getTagName().equals(tagName)) {
                    return elem.getTextContent();
                }
            }
        }
        return null;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public Integer getPortListenTo() {
        return portListenTo;
    }

    public void setPortListenTo(Integer portListenTo) {
        this.portListenTo = portListenTo;
    }

}
