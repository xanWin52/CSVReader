import java.io.FileReader; 
import java.io.FileWriter;
import java.io.IOException; 
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

public class AttendanceUpdater {
    public static void main(String[] args) throws IOException{
        BufferedReader signIn = new BufferedReader(new FileReader("Test Event Sign In.csv"));
        BufferedReader attendance = new BufferedReader(new FileReader("Test Attendance.csv"));
        String line;
        ArrayList<String[]> newAtt = new ArrayList<>();
        while((line = attendance.readLine())!= null){
            newAtt.add(line.split(","));
        }
        FileWriter result = new FileWriter(new File("Result.csv"));
    }    
}
