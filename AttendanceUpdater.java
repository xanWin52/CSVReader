import java.io.FileReader; 
import java.io.FileWriter;
import java.io.IOException; 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttendanceUpdater {
    public static void main(String[] args) throws IOException{

        BufferedReader attendance = new BufferedReader(new FileReader("Test Attendance.csv"));
        BufferedWriter result = new BufferedWriter(new FileWriter("Result.csv"));

        String line;
        ArrayList<List<String>> newAtt = new ArrayList<List<String>>();
        while((line = attendance.readLine())!= null){
            ArrayList<String> lineTemp = new ArrayList<>(Arrays.asList(line.split(",")));
            newAtt.add(lineTemp);
        }
        newAtt = addEvent(newAtt,"Test");
        for(List<String> x : newAtt){
            result.write(x.toString());
            result.write("\n");
        }
        attendance.close();
        result.close();
    }    

    public static ArrayList<List<String>> addEvent(ArrayList<List<String>> att, String title)throws IOException{
        BufferedReader signIn = new BufferedReader(new FileReader("Test Event Sign In.csv"));
        att.get(1).add(title);
        String line;
        signIn.readLine();
        while((line = signIn.readLine()) != null){
            String[] entry = line.split(",");
            for(int i = 5; i < att.size(); i ++){
                att.get(i).add("Test");
            }
        }
        signIn.close();
        return att;
    }
}
