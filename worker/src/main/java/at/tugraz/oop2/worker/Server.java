package at.tugraz.oop2.worker;
import at.tugraz.oop2.shared.*;

import java.io.IOException;
import java.net.*;

public class Server {
    private static final int port_no = 8010;
    private static final String port = "port";
    public static void main(String[] args) throws IOException{
        int actual_port = getPortInfo(args);
        ServerSocket socket = new ServerSocket(actual_port);
        FractalLogger.logStartWorker(actual_port);
        while(true)
        {
            try
            {
                //ServerSocket new_socket = new ServerSocket(socket.accept());
                Socket sock = socket.accept();
                FractalLogger.logConnectionOpenedWorker();
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }
            
        }
   
    }

    private static int getPortInfo(String [] main_args)
    {
        String [] str_saver;
        for(String arg_check : main_args)
        {
            str_saver = arg_check.split("=",0);
            if(arg_check.startsWith("--") && arg_check.contains("="))
            {
                str_saver[0] = str_saver[0].substring(2);
            
                    if(port == str_saver[0])
                    {
                        int ret_val = Integer.parseInt(str_saver[1]);
                        return ret_val;
                    }
                
            }
        }
        return port_no;
    }

}
