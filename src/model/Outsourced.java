package model;
/**
 * Outsourced parts get initialized with incoming part id, name, price,
 *  *  stock, min, max, and machineID
 *  * getComName() is a getter that returns the company name of the manufactured part;
 */
public class Outsourced extends Part{

    private String compName;

    public Outsourced(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }

    public String getCompName(){
        return compName;
    }

    public void setCompName(String compName){
        this.compName = compName;
    }
}
