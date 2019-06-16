package Project.TestWrapper;

import org.boon.Str;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Agent {
    private String agentName;
    private String agentNumber;
    public static List<Device> deviceInAgent;
    protected int numberOfDevices;
    protected double numberOfTests_Total;
    protected double numberOfTests_Pass;
    protected double numberOfTests_Incomplete;
    protected double numberOfTests_Failed;

    private static DecimalFormat df2 = new DecimalFormat("#.##");



    public Agent(String agentName){
        this.agentName = agentName;
        this.agentNumber = "-";
        deviceInAgent = new ArrayList<>();
        numberOfDevices = 0;
        numberOfTests_Total = 0;
        numberOfTests_Pass= 0;
        numberOfTests_Incomplete =0;
        numberOfTests_Failed = 0;

    }

    public Agent(String agentName, String agentNumber){
        this.agentName = agentName;
        this.agentNumber = agentNumber;
        deviceInAgent = new ArrayList<>();
        numberOfDevices = 0;
        numberOfTests_Total = 0;
        numberOfTests_Pass= 0;
        numberOfTests_Incomplete = 0;
        numberOfTests_Failed = 0;

    }

    public void addDeviceToAgent(Device device){
        deviceInAgent.add(device);

    }

    public void addTestToAgent(boolean passed){
        System.out.println("Add test result to agent "+this.getAgentName()+" - result is "+passed);
        numberOfTests_Total++;
        if(passed){
            numberOfTests_Pass++;
        }else {
            numberOfTests_Failed++;
        }
    }

    public void addTestToAgent(String TestStatus) {
        System.out.println("Add test result to agent " + this.getAgentName() + " - result is " + TestStatus);
        numberOfTests_Total++;
        if (TestStatus.equals("Incomplete")) {
            numberOfTests_Incomplete++;
        }
    }

    public String toString(){
        return this.agentName;
    }

    public String getAgentName(){
        return agentName;
    }

    public String getAgentNumber(){
        return agentNumber;
    }

    public String getAgentPassedPercentage(){

        System.out.println("Calculate getAgentPassedPercentage for agent"+this.getAgentName() );
        if(numberOfTests_Total>0) {
            double result = (numberOfTests_Pass / numberOfTests_Total) * 100;
            System.out.println("Calculation is "+result+" | " +this.getAgentName() );
            return df2.format(result);
        }else
            return "0";
    }

        public String getAgentIncompletePercentage(){
        System.out.println("Calculate getAgentIncompletePercentage for agent"+this.getAgentName() );
        if(numberOfTests_Total>0) {
            double result = (numberOfTests_Incomplete / numberOfTests_Total) * 100;
            System.out.println("Calculation is "+result+" | " +this.getAgentName() );
            return df2.format(result);
        }else
            return "0";
    }






}
