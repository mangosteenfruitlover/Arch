import java.io.Serializable;

public class Roots implements Serializable{
    private boolean infinity= false;
    private double[] solutions = {};

    public Roots(double... solutions) {
        this.solutions = solutions;
    }

    public Roots(boolean infinity) {
        this.infinity=infinity;
    }

    public Roots() {

    }

    public boolean isInfinity(){
        return infinity;
    }

    public void setInfinity(boolean infinity){
        this.infinity=infinity;
    }

    public double[] getSolutions(){
        return solutions;
    }

    public void setSolutions(double[] solutions){
        this.solutions=solutions;
    }
}