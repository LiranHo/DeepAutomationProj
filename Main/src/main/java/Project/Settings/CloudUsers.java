package Project.Settings;

import Project.Main;

    public enum CloudUsers {

        Example1("user", "pass", "default", false, "domain", 80),
        Example2("AccessKey", "default", false, "domain", 80),


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

}
