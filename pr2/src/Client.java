import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class Client{

    public static final String UNIQUE_BINDING_NAME = "server.equation_solver";

    public static void main(String[] args) throws RemoteException, NotBoundException {
        try{
            final Registry registry=LocateRegistry.getRegistry(2732);

            EquationCalculator equationCalculator=(EquationCalculator)registry.lookup(UNIQUE_BINDING_NAME);

            System.out.println("Example1:\n" +
                                       "a = 0;\n" +
                                       "b = 0;\n" +
                                       "c = 0:\n"); //a=0,b=0,c=0
            showRoots(equationCalculator.solve(0,0,0));

            System.out.println("Example2:\n" +
                                       "a = 1;\n" +
                                       "b = 2;\n" +
                                       "c = 3:\n"); //a=1,b=2,c=3
            showRoots(equationCalculator.solve(1,2,3));

            System.out.println("Example3:\n" +
                                       "a = 10;\n" +
                                       "b = 1;\n" +
                                       "c = 2:\n"); //a=10,b=1,c=2
            showRoots(equationCalculator.solve(10,1,2));

            System.out.println("Example4:\n" +
                                       "a = 5;\n" +
                                       "b = 10;\n" +
                                       "c = -10:\n"); //a=5,b=10,c=10
            showRoots(equationCalculator.solve(5,10,-10));

        } catch (Exception e){
            System.err.println("Client exception" + e.toString());
            e.printStackTrace();
        }

    }

    private static void showRoots(Roots roots) {
        if(roots.isInfinity()){
            System.out.println("Infinity roots");
            return;
        }
        System.out.println("Equation roots:"+Arrays.toString(roots.getSolutions()));

    }
}