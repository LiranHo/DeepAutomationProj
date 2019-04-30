package Project.TestWrapper;

public class Browser extends Thread{
    private String serialnumber;
    private String browserName;
    private String os;
    private String browserVersion;
    private String platform;
    private String agent;
    private String deviceFolderPath="";

    public Browser(String serialnumber){
        this.serialnumber=serialnumber;
        this.os="Browser";

        this.browserName ="";
        this.browserVersion ="";
        this.platform ="";
        this.agent = "";
    }

    public String toString(){
        String delimiter = "\r\n";
        String deviceParameter=
                "Device info: "+delimiter+
                        "serialnumber: "+this.serialnumber+delimiter+
                        "browserName: "+this.browserName +delimiter+
                        "os: "+this.os+delimiter+
                        "browserVersion: "+this.browserVersion +delimiter+
                        "platform: "+this.platform +delimiter+
                        "agent: "+this.agent;

        return deviceParameter;

    }


    public String getDeviceFolderPath(){
        return this.deviceFolderPath;
    }


    public String getSerialnumber(){
        return this.serialnumber;
    }
    public String getAgent(){ return this.agent;}
    public String getplatform(){ return this.platform;}
    public String getBrowserVersion(){ return this.browserVersion;}
    public String getBrowserName(){ return this.browserName;}

    public String getOS(){ return this.os;}


    public void setBrowserInfo(String browserVersion, String platform, String agent, String browserName){
        this.browserVersion=browserVersion;
        this.platform=platform;
        this.agent=agent;
        this.browserName=browserName;

    }



}
