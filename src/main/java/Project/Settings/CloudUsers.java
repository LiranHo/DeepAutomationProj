package Project.Settings;

import Project.Main;

public enum CloudUsers {

    Example("name","pass", "default", false,"111.111.1.111", 80),

    ;

    public String userName;
    public String Password;
    public String projectName;
    public boolean isSecured;
    public String grid_domain; //without https
    public int grid_port;
    public String Authorization;
    public String AccessKey;


    CloudUsers(String userName, String Password , String projectName , Boolean isSecured ,
               String grid_domain , int grid_port){
        this.userName=userName;
        this.Password=Password;
        this.projectName=projectName;
        this.isSecured=isSecured;
        this.grid_domain=grid_domain;
        this.grid_port=grid_port;
        this.Authorization="";
        this.AccessKey="0";
    }

    CloudUsers(String userName, String Password , String projectName , Boolean isSecured ,
               String grid_domain , int grid_port, String Authorization){
        this.userName=userName;
        this.Password=Password;
        this.projectName=projectName;
        this.isSecured=isSecured;
        this.grid_domain=grid_domain;
        this.grid_port=grid_port;
        this.Authorization=Authorization;
        this.AccessKey="0";

    }

    CloudUsers(String AccessKey , String projectName , Boolean isSecured ,
               String grid_domain , int grid_port){
        this.AccessKey=AccessKey;
        this.projectName=projectName;
        this.isSecured=isSecured;
        this.grid_domain=grid_domain;
        this.grid_port=grid_port;
    }

    public String toString(boolean PrintInOneLine){

        if(PrintInOneLine){
            String starts="http";
            if(isSecured) starts+="s";
            return starts+"://"+grid_domain+":"+grid_port+"   |    "+"UserName: "+userName;
        }else
            return "## Cloud User: ##"+ Main.delimiter+
                    "userName: "+userName+ Main.delimiter+
                    "projectName: "+projectName+ Main.delimiter+
                    "isSecured: "+isSecured+ Main.delimiter +
                    "grid_domain: "+grid_domain+ Main.delimiter +
                    "grid_port: "+grid_port + Main.delimiter
                    ;
    }


    public String toString(){
        return "## Cloud User: ##"+ Main.delimiter+
                "userName: "+userName+ Main.delimiter+
                "projectName: "+projectName+ Main.delimiter+
                "isSecured: "+isSecured+ Main.delimiter +
                "grid_domain: "+grid_domain+ Main.delimiter +
                "grid_port: "+grid_port + Main.delimiter
                ;
    }

    public String getUserName(){return userName;}
    public String getPassword(){return Password;}
    public String getAccessKey(){return AccessKey;}
    public String getprojectName(){return projectName;}
    public String getCloudFullAdress(){
        String starts="http";
        if(isSecured) starts+="s";
        return starts+"://"+grid_domain+":"+grid_port;
    }

}
