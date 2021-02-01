package Utility;

import java.io.FileWriter;
import java.io.IOException;

public class UserLogWriteClass {


    /**
     *
     * @param string method used to write to file login_activity takes in string to write
     */
    public static void writeToUserLog(String string) {
        try {
            FileWriter logWriter = new FileWriter("login_activity.txt", true);
            logWriter.write(string + "\n");
            logWriter.close();
        } catch (IOException e) {
            System.out.println("File write error");
            e.printStackTrace();
        }

    }





}
