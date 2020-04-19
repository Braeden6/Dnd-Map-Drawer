package MainMap;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// Client class
public class MainClient {

    public static final int ARRAY_WIDTH = 256;
    public static final int ARRAY_HEIGHT = 144;
    public static final int INTERVAL = 40;

    private static DataInputStream dis;
    private static DataOutputStream dos;
    private static boolean[][] blackPixels;
    private static boolean mainProgram;
    private static boolean sendInfo;
    private static boolean characterLocation;


    public MainClient() {
        mainProgram = false;
        characterLocation = false;
        blackPixels = new boolean[ARRAY_WIDTH][ARRAY_HEIGHT];
        new DrawingProgram(blackPixels, this);
    }

    public void signalSendInfo() {
        sendInfo = true;
    }

    //setter
    public void setMainProgram(boolean mainProgram) {
        this.mainProgram = mainProgram;
    }

    private static void addTimer() {
        Timer timer = new Timer(INTERVAL, ae -> {
            try {
                // set priority as the writer (person that changes the 2D array)
                dos.writeBoolean(mainProgram);
                dos.writeBoolean(sendInfo);
                dos.writeBoolean(characterLocation);
                //get
                if (mainProgram && sendInfo) {
                    sendMainView();
                    sendInfo = false;
                } else if (!mainProgram) {
                    receiveMainView();
                }
                if (characterLocation) {
                    sendCharacterLocation();
                }
            } catch(Exception e){
                System.out.println("Error receiving or sending info");
            }
        });
        timer.start();
    }

    // EFFECTS: send boolean of if location is enabled and then int value of x and y location
    private static void sendCharacterLocation() {
        //stub
    }

    // EFFECTS: send the 2D array for the blackPixels screen to the sever
    private static void sendMainView() {
        try {
            for (int w = 0; w < ARRAY_WIDTH; w++) {
                for (int h = 0; h < ARRAY_HEIGHT; h++) {
                    dos.writeBoolean(blackPixels[w][h]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: get the 2D array for the blackPixels screen from the server
    private static void receiveMainView() {
        try {
            for (int w = 0; w < ARRAY_WIDTH; w++) {
                for (int h = 0; h < ARRAY_HEIGHT; h++) {
                    blackPixels[w][h] = dis.readBoolean();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
            new MainClient();
            Socket s = new Socket("192.168.1.86", 5000);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
        }catch(Exception e){
            System.out.println("Socket was not started");
        }
        addTimer();
    }
}