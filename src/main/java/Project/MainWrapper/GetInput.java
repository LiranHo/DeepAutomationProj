package Project.MainWrapper;

import Project.Main;
import Project.Settings.CloudUsers;
import Project.Settings.TestSuites;

import java.util.Scanner;

import static Project.Main.*;

public class GetInput {

    public static void getInputFromUser() {

        Scanner scan = new Scanner(System.in);

        printCurrentTunProperties();
        printRunOptions();

        System.err.println("If You Want To Change Press On The Relevant Number");

        String input = scan.nextLine();

        //T: Change the properties for this run
        while(!input.equals("")) { //If entering just "Enter" the program will start
            switch (input) {
                case "1": //Grid
                    System.out.println("Enter Value: true/false");
                    String getInput = scan.nextLine();
                    Grid=Boolean.valueOf(getInput);

                    System.out.println("Grid value is changed to: "+Grid);
                    break;
                case "2": //Cloud User
                    System.out.println("Enter Value: LiranCloud / LiranQaCloud");
                    getInput = scan.nextLine();
                    Main.cloudUser= CloudUsers.valueOf(getInput);
                    System.out.println("cloudUser value is changed to: "+Main.cloudUser.toString(true));
                    break;
                case "3": //chooseSpesificDevices
                    System.out.println("Enter Value: true/false");
                    getInput = scan.nextLine();
                    Main.chooseSpesificDevices=Boolean.valueOf(getInput);
                    System.out.println("chooseSpesificDevices value is changed to: "+Main.chooseSpesificDevices);
                    String Device="";
                    if(Main.chooseSpesificDevices) {
                        while (!Device.equals("-1")) {
                            System.out.println("Enter Devices Serial Number, To quit enter -1");
                            Device = scan.nextLine();
                            if(!Device.equals("-1")) {
                                Choosedevices.add(Device);
                                System.out.println("Device " + Device + " Enter To the Running devices");
                            }else System.out.println("Devices Add Is Ended");
                        }
                    }
                    break;
                case "4": //Runby_NumberOfRounds
                    System.out.println("Enter Value: true/false");
                    getInput = scan.nextLine();
                    Main.Runby_NumberOfRounds=Boolean.valueOf(getInput);
                    System.out.println("Runby_NumberOfRounds value is changed to: "+Main.Runby_NumberOfRounds);
                    break;

                case "5": //NumberOfRoundsToRun OR TimeToRun
                    if(Main.Runby_NumberOfRounds){
                        System.out.println("Enter Value for NumberOfRoundsToRun: Integer");

                        getInput = scan.nextLine();
                        Main.NumberOfRoundsToRun=Integer.valueOf(getInput);
                        System.out.println("NumberOfRoundsToRun value is changed to: "+Main.NumberOfRoundsToRun);
                    }else{
                        System.out.println("Enter Value for TimeToRun: time in milliseconds");
                        getInput = scan.nextLine();
                        Main.TimeToRun=Integer.valueOf(getInput);
                        System.out.println("TimeToRun value is changed to: "+Main.TimeToRun);
                    }

                    break;
                case "6": //testsSuites
                    System.out.println("Enter Value: AllTest / OneTimeTest");
                    getInput = scan.nextLine();
                    testsSuites= TestSuites.valueOf(getInput);
                    System.out.println("testsSuites value is changed to: "+testsSuites.toString());
                    break;

                case "0": //testsSuites
                    printCurrentTunProperties();
                    break;

                case "-10": //-10. AllTest, All Devices, Grid, 10 Hours
                    Grid=true;
                    Main.chooseSpesificDevices=false;
                    Main.Runby_NumberOfRounds=false;
                    Main.TimeToRun=60 * 60 * 10;
                    testsSuites=TestSuites.valueOf("AllTest");
                    break;

                case "-9": //-9. OneTimeTest, All Device, Grid, 1 Time
                    Grid=true;
                    Main.chooseSpesificDevices=false;
                    Main.Runby_NumberOfRounds=true;
                    Main.NumberOfRoundsToRun=1;
                    testsSuites=TestSuites.valueOf("OneTimeTest");
                    break;
            }
            System.err.println("If You Want To Change Press On The Relevant Number");
            input = scan.nextLine();
        }

        printCurrentTunProperties();

    }

    public static void printCurrentTunProperties(){
        System.err.println("********************");
        System.out.println("********************");
        System.out.println("**Run Properties**");
        System.out.println("0. Print the Setting");
        System.out.println("1. Grid: "+Grid);
        System.out.println("2. Cloud User: "+Main.cloudUser.toString(true));
        System.out.println("3. chooseSpesificDevices: "+Main.chooseSpesificDevices);
        if(Main.chooseSpesificDevices)

        {
            for (String DeviceSN : Choosedevices) {
                System.out.println("    " + DeviceSN);
            }

        }
        System.out.println("4. Runby_NumberOfRounds: "+Runby_NumberOfRounds);
        if(Runby_NumberOfRounds)

        {
            System.out.println("5. NumberOfRoundsToRun: " + NumberOfRoundsToRun);
        }
        else

        {
            System.out.println("5. TimeToRun: " + TimeToRun);
        }
        if(testsSuites.toString().equals("AllTest")) {
            System.out.println("6. testsSuites: " + testsSuites.toString());
        }else{
            System.err.println("!!!WARNING!!!");
            System.out.println("6. testsSuites: " + testsSuites.toString());
        }

        System.out.println("********************");
        System.out.println("********************");
    }

    public static void printRunOptions(){
        System.out.println("-10. AllTest, All Devices, Grid, 10 Hours");
        System.out.println("-9. OneTimeTest, All Device, Grid, 1 Time");

    }
}
