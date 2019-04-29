package Project.TestWrapper;

public class Browser extends Thread{
    private String serialnumber;
    private String name;
    private String os;
    private String version;
    private String model;
    private String category;
    private String manufacture;
    private String remote;
    private String reservedtoyou;
    private String agent;
    private String deviceFolderPath="";


    public Browser(String serialnumber){
        this.serialnumber=serialnumber;
        this.os="Browser";

        this.name ="";
        this.version ="";
        this.model ="";
        this.category ="";
        this.manufacture ="";
        this.remote ="";
        this.reservedtoyou = "";
        this.agent = "";
    }

    public String toString(){
        String delimiter = "\r\n";
        String deviceParameter=
                "Device info: "+delimiter+
                        "serialnumber: "+this.serialnumber+delimiter+
                        "name: "+this.name+delimiter+
                        "os: "+this.os+delimiter+
                        "version: "+this.version+delimiter+
                        "model: "+this.model+delimiter+
                        "category: "+this.category+delimiter+
                        "manufacture: "+this.manufacture+delimiter+
                        "remote: "+this.remote+
                        "agent: "+this.agent;
        if(this.remote.equals("true")){deviceParameter+=delimiter+"reservedtoyou: "+this.reservedtoyou;}

        return deviceParameter;

    }


    public String getDeviceFolderPath(){
        return this.deviceFolderPath;
    }


    public String getSerialnumber(){
        return this.serialnumber;
    }
    public String getAgent(){ return this.agent;}

    public String getOS(){ return this.os;}



}
