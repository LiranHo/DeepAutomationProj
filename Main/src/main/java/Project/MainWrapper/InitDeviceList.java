package Project.MainWrapper;

import Project.Main;
import Project.TestWrapper.Device;
import com.experitest.client.GridClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static Project.Main.*;

public class InitDeviceList {


    public static String initDevicesList() throws Exception {
        String devicesInitInfo = "";
        /* create client and get the connected devices list using "getdevicesinformation
         * afterwards, parse the xml and create object-device to each device
         */
        if (!Grid) {
            throw new Exception("Can't run not Grid");
//            client = new Client(Main.local_host, Main.local_port, true);
//            devices = getDevices(client.getDevicesInformation()); //create devices from getDevicesInformation
//            client.releaseClient();

        } else if (Grid) {
            GridClient gridClient = new GridClient(cloudUser.userName, cloudUser.Password, cloudUser.projectName, cloudUser.grid_domain, cloudUser.grid_port, cloudUser.isSecured);
            devices = getDevices(gridClient.getDevicesInformation());


//           gridClient = new GridClient(userName, Password, projectName, grid_domain, grid_port, isSecured);
//             devices = getDevices(gridClient.getDevicesInformation());

        }

        //print the devices list
        System.out.println("Devices List info: " + delimiter);
        System.out.println("Number of Devices in this run: " + devices.size());
        PrintDevicesInfo = "";
        PrintDeviceSN = "";
        for (int i = 0; i < devices.size(); i++) {
            PrintDevicesInfo += "#" + (i + 1) + " " + devices.get(i).toString() + delimiter + delimiter;
            PrintDeviceSN += devices.get(i).getSerialnumber() + delimiter;
        }

        System.out.println(PrintDeviceSN);
        System.out.println(PrintDevicesInfo);

        devicesInitInfo += "Devices List info: " + delimiter + "Number of Devices in this run: " + devices.size() + delimiter + PrintDeviceSN + delimiter + PrintDevicesInfo;
        return devicesInitInfo;
    }



    //get devices and enter them to hash map
    public static List<Device> getDevices(String xml) throws Exception {
        //The user can chose if he want to run on specific devices or on all connected devices:
        //1. If the user want to run on all connected devices:
        //2. if the user wants to run on specific devices:
        List<Device> devicesMap = new ArrayList<>();
        NodeList nodeList = parseDevices(xml); //parse the xml and get back nodeList with all the devices

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);
            Element eElement = (Element) nNode;
            Device device = new Device(
                    eElement.getAttribute("serialnumber"),
                    eElement.getAttribute("name"),
                    eElement.getAttribute("os"),
                    eElement.getAttribute("version"),
                    eElement.getAttribute("model"),
                    eElement.getAttribute("category"),
                    eElement.getAttribute("manufacture"),
                    eElement.getAttribute("remote"),
                    eElement.getAttribute("reservedtoyou"));
//            System.out.println(eElement.getAttribute("serialnumber"));

            //if we don't want to use all devices, check if the device is in the list of device
            if (chooseSpesificDevices) {

                for (String DeviceSN : Choosedevices) {
                    if (eElement.getAttribute("serialnumber").equalsIgnoreCase(DeviceSN)
                            || eElement.getAttribute("name").toLowerCase().contains(DeviceSN.toLowerCase())) {
                        //Create new folder for this device
                       // device.setDeviceFolderPath(Main.createNewDir(Main.innerDirectoryPath, eElement.getAttribute("serialnumber")));
                      //  System.out.println("device.setDeviceFolderPath is off");
                        devicesMap.add(device);


                    }
                }
            } else { //if use all devices is true
                //Create new folder for this device
//                device.setDeviceFolderPath(Main.createNewDir(Main.innerDirectoryPath, eElement.getAttribute("serialnumber")));
              //  System.out.println("device.setDeviceFolderPath is off");
                devicesMap.add(device);

            }
        }


        return devicesMap;

    }

    //Parse the devices XML from getDevicesInformation and returns A nodeList Element
    private static NodeList parseDevices(String xml) throws Exception {
        //Using JDOM to parse the devices xml
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        StringBuilder xmlStringBuilder = new StringBuilder();
        xmlStringBuilder.append(xml);
        ByteArrayInputStream input = new ByteArrayInputStream(
                xmlStringBuilder.toString().getBytes("UTF-8"));
        Document doc = builder.parse(input);
        doc.getDocumentElement().normalize();
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/devices/device";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
        System.out.println("Number of Devices connected: " + nodeList.getLength());

        return nodeList;

    }


}