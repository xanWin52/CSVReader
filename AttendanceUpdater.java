import java.io.FileReader; 
import java.io.FileWriter;
import java.io.IOException; 
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttendanceUpdater {
    public static void main(String[] args) throws IOException{
        BufferedReader attendance = new BufferedReader(new FileReader("Test Attendance.csv"));
        String line;
        ArrayList<List<String>> newAtt = new ArrayList<List<String>>();
        while((line = attendance.readLine())!= null){
            System.out.println(line);
            newAtt.add(Arrays.asList(line.split(",")));
        }
        newAtt = addEvent(newAtt,"Test");
        FileWriter result = new FileWriter(new File("Result.csv"));
        for(List<String> x : newAtt){
            result.write(x.toString());
        }
        attendance.close();
    }    

    public static ArrayList<List<String>> addEvent(ArrayList<List<String>> att, String title)throws IOException{
        BufferedReader signIn = new BufferedReader(new FileReader("Test Event Sign In.csv"));
        String line;
        signIn.readLine();
        while((line = signIn.readLine()) != null){
            String[] entry = line.split(",");
            for(int i = 5; i < att.size(); i ++){
                if((att.get(i).get(0).equals(entry[2]) && att.get(i).get(1).equals(entry[1])) || (att.get(i).get(0).equals(entry[1]) && att.get(i).get(1).equals(entry[2]))){
                    att.get(i).add(entry[0]);
                }
            }
        }
        signIn.close();
        return att;
    }
}
