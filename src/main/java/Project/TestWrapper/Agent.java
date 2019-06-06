package Project.TestWrapper;

import org.boon.Str;

import java.util.ArrayList;
import java.util.List;

public class Agent {
    private String agentName;
    private String agentNumber;
    public static List<Device> deviceInAgent;
    protected int numberOfDevices;
    protected double numberOfTests_Total;
    protected double numberOfTests_Pass;
    protected double numberOfTests_Failed;


    public Agent(String agentName){
        this.agentName = agentName;
        this.agentNumber = "-";
        deviceInAgent = new ArrayList<>();
        numberOfDevices = 0;
        numberOfTests_Total = 0;
        numberOfTests_Pass= 0;
        numberOfTests_Failed = 0;

    }

    public Agent(String agentName, String agentNumber){
        this.agentName = agentName;
        this.agentNumber = agentNumber;
        deviceInAgent = new ArrayList<>();
        numberOfDevices = 0;
        numberOfTests_Total = 0;
        numberOfTests_Pass= 0;
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

    public String toString(){
        return this.agentName;
    }

    public String getAgentName(){
        return agentName;
    }

    public String getAgentNumber(){
        return agentNumber;
    }

    public double getAgentPassedPercentage(){

        System.out.println("Calculate getAgentPassedPercentage for agent"+this.getAgentName() );
        if(numberOfTests_Total>0) {
            double result = (numberOfTests_Pass / numberOfTests_Total) * 100;
            System.out.println("Calculation is "+result+" | " +this.getAgentName() );
            return result;
        }else
            return 0;
    }






}
