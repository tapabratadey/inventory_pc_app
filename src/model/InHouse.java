package model;

public class InHouse extends Part{

    private int machineID;

    public InHouse(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }

    public int getMachineID(){
        return machineID;
    }

    public void setMachineID(int machineID){
        this.machineID = machineID;
    }

}
