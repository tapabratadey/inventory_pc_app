package model;

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
