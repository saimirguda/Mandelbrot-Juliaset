package at.tugraz.oop2.shared;
import java.net.*;
import java.io.*;

public class SocketClass
{

    private InputStream in_streams;
    private OutputStream out_streams;
    private BufferedWriter in_buffer;
    private BufferedReader out_buffer;
    private Socket my_socket;


    public SocketClass(String port, int server_IP) throws IOException, UnknownHostException
    {
        my_socket = new Socket(port,server_IP);
    }

    public SocketClass(Socket new_socket)
    {
        this.my_socket = new_socket;
    }

    public void streamInit() throws IOException {
        in_buffer = new BufferedWriter(new OutputStreamWriter(my_socket.getOutputStream()));

        out_buffer = new BufferedReader(new InputStreamReader(my_socket.getInputStream()));
        
        out_streams = new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        };
        
        in_streams = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
    }
}
