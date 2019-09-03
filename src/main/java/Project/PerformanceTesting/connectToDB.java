package Project.PerformanceTesting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;


public class connectToDB {

    static String url = "jdbc:postgresql://192.168.2.225:5432/Performance";
    static String user = "postgres";
    static String password = "123456";

    public static void main(String[] args) {
//        connect();
        insert(12,"CN","SN",10.01, "12.8", 1111,"12.8.1111");
    }


    public static void insert(int id, String commandname, String devicesn, double during, String version_number, int build_number,  String full_version){
//        int id = 12;
//        String commandname = "CN";
//        String devicesn = "SN";
//        double during = 10.01;

        String query = "INSERT INTO gridcommands(id, commandname, devicesn, during, version_number, build_number, full_version) VALUES(?, ?, ?, ?, ?, ? , ?)";


        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id);
            pst.setString(2, commandname);
            pst.setString(3, devicesn);
            pst.setDouble(4, during);
            pst.setString(5, version_number);
            pst.setInt(6, build_number);
            pst.setString(7, full_version);

            pst.executeUpdate();

        } catch (SQLException ex) {

            System.out.println(ex.getNextException().getMessage());
        }
    }


    public static void insertDeviceToDB(String devicesn, String device_name, String os, String os_version){
        String checkIFexistsQuery = "SELECT devicesn from devices WHERE devices.devicesn = ?;";

        //check if thie device already exists, if not enter it to the data base

        try (Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmnt  = con.prepareStatement(checkIFexistsQuery)) {
            pStmnt.setString(1, devicesn);
            ResultSet rs = pStmnt.executeQuery();

            if (!rs.next()) {
                String query = "INSERT INTO devices(devicesn, device_name, os, os_version) VALUES(?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(query);

                pst.setString(1, devicesn);
                pst.setString(2, device_name);
                pst.setString(3, os);
                pst.setString(4, os_version);

                pst.executeUpdate();


            }} catch (SQLException ex) {

            System.out.println(ex.getNextException().getMessage());
        }
    }


    public static void connect(){
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM gridcommands")) {
            if (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getNextException().getMessage());
        }
    }


//    public static String insert(String ID, String CommandName, String DeviceSN, String During){
//      insert into "GridCommands" (
//                "ID",
//                "CommandName",
//                "DeviceSN",
//                "During")
//        values (
//    :ID,
//    :CommandName,
//    :DeviceSN,
//    :During)
//        ;
//
//
//    }

}
