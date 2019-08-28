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

    static String url = "jdbc:postgresql://localhost:5432/Performance";
    static String user = "postgres";
    static String password = "123456";

    public static void main(String[] args) {
//        connect();
        insert(12,"CN","SN",10.01, "12.8", 1111);
    }


    public static void insert(int id, String commandname, String devicesn, double during, String version_number, int build_number){
//        int id = 12;
//        String commandname = "CN";
//        String devicesn = "SN";
//        double during = 10.01;

        String query = "INSERT INTO gridcommands(id, commandname, devicesn, during, version_number, build_number) VALUES(?, ?, ?, ?, ?, ?)";


        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id);
            pst.setString(2, commandname);
            pst.setString(3, devicesn);
            pst.setDouble(4, during);
            pst.setString(5, version_number);
            pst.setInt(6, build_number);

            pst.executeUpdate();

        } catch (SQLException ex) {

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
