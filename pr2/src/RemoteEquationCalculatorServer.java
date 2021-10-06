import java.rmi.RemoteException;

public class RemoteEquationCalculatorServer implements EquationCalculator{

    @Override
    public Roots solve(double a,double b,double c) throws RemoteException {
        if(a == 0
                && b != 0
                && c != 0) {
            double x = (-1 * c) / b;
            return new Roots(x);
        }

        if(a == 0
                && b == 0
                &&  c != 0) {
            return new Roots();
        }

        if(a == 0
                && c == 0
                &&  b != 0) {
            return new Roots(0);
        }

        if(a == 0
                && b == 0
                && c == 0) {
            return new Roots(true);
        }

        double D = Math.pow(b, 2) - 4 * a * c;

        if(D == 0) {
            double x = (-1 * b) / (2 * a);
            return new Roots(x);
        }

        if(D < 0) {
            return new Roots();
        }

        double x1 = (-1 * b - Math.pow(D, 0.5)) / (2 * a);
        double x2 = (-1 * b + Math.pow(D, 0.5)) / (2 * a);
        return new Roots(x1,x2);
    }

}