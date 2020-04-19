package DisplayMap;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

// Client class
public class DisplayClient  {

    public static final int ARRAY_WIDTH = 256;
    public static final int ARRAY_HEIGHT = 144;

    private static DataInputStream dis;
    private static DataOutputStream dos;
    private static boolean[][] blackPixels;


    public DisplayClient() {
        blackPixels = new boolean[ARRAY_WIDTH][ARRAY_HEIGHT];
        new DisplayProgram(blackPixels);
    }


    public static void main(String[] args) {
        try {
            new DisplayClient();

            Socket s = new Socket("192.168.1.88", 5000);

            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

            while (true) {
                // set priority as the reader (person that views the change to the 2D array)
                dos.write(1);

                //get
                for (int w = 0; w < ARRAY_WIDTH; w++) {
                    for (int h = 0; h < ARRAY_HEIGHT; h++) {
                        blackPixels[w][h] = dis.readBoolean();
                    }
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}