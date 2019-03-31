package Project.TestWrapper;

public class Device extends Thread{
    private String serialnumber;
    private String name;
    private String os;
    private String version;
    private String model;
    private String category;
    private String manufacture;
    private String remote;
    private String reservedtoyou;
    private String deviceFolderPath="";



    public Device(String serialnumber, String name, String os, String version, String model, String category, String manufacture, String remote, String reservedtoyou){
        this.serialnumber =serialnumber;
        this.name =name;
        this.os =os;
        this.version =version;
        this.model =model;
        this.category =category;
        this.manufacture =manufacture;
        this.remote =remote;
        this.reservedtoyou = reservedtoyou;
    }

    public Device(String serialnumber){
        this(serialnumber,"","","","","","","","");
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
                        "remote: "+this.remote;
        if(this.remote.equals("true")){deviceParameter+=delimiter+"reservedtoyou: "+this.reservedtoyou;}

        return deviceParameter;

    }

    public void setDeviceFolderPath(String path){
        this.deviceFolderPath=path;
    }

    public String getDeviceFolderPath(){
        return this.deviceFolderPath;
    }


    public String getSerialnumber(){
        return this.serialnumber;
    }
    public String getVersion(){ return this.version;}
    public String getCategory(){ return this.category;}
    public String getManufactor(){ return this.manufacture;}
    public String getOS(){ return this.os;}
    public Boolean IsDeviceRemote(){ return Boolean.valueOf(this.remote);}


    public boolean isAndroid(){
        if (os.equals("android"))
            return true;
        else
            return false;
    }

    public boolean isIOS(){
        if (os.equals("ios"))
            return true;
        else
            return false;
    }

}
