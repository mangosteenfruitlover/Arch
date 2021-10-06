import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server{

    public static final String UNIQUE_BINDING_NAME = "server.equation_solver";

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException {
        try{
            final RemoteEquationCalculatorServer server=new RemoteEquationCalculatorServer();

            final Registry registry=LocateRegistry.createRegistry(2732);

            Remote stub=UnicastRemoteObject.exportObject(server,0);
            registry.bind(UNIQUE_BINDING_NAME,stub);

            System.err.println("Server ready");
            Thread.sleep(Integer.MAX_VALUE);
        }   catch (Exception e){
            System.err.println("ServerMain exception" + e.toString());
            e.printStackTrace();
        }

    }
}