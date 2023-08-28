import java.io.FileReader; 
import java.io.FileWriter;
import java.io.IOException; 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class AttendanceUpdater {
    public static void main(String[] args) throws IOException{

        BufferedReader attendance = new BufferedReader(new FileReader("Test Attendance.csv"));
        BufferedWriter result = new BufferedWriter(new FileWriter("Result.csv"));

        String line;
        ArrayList<ArrayList<String>> newAtt = new ArrayList<ArrayList<String>>();
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

    public static ArrayList<ArrayList<String>> addEvent(ArrayList<ArrayList<String>> att, String title)throws IOException{
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        BufferedReader signIn = new BufferedReader(new FileReader("Test Event Sign In.csv"));
        int length = att.get(5).size();
        att.get(1).add(title);
        String line;
        signIn.readLine();
        while((line = signIn.readLine()) != null){
            String[] entry = line.split(",");
            String[] timestamp = entry[0].split(" ");
            for(int i = 5; i < att.size(); i ++){
                if((att.get(i).get(0).toLowerCase().equals(entry[2].toLowerCase()) && att.get(i).get(1).toLowerCase().equals(entry[1].toLowerCase())) || (att.get(i).get(0).toLowerCase().equals(entry[1].toLowerCase()) && att.get(i).get(1).toLowerCase().equals(entry[2].toLowerCase()))){
                    if(att.get(i).size() == length){
                        att.get(i).add(timestamp[1]);
                        break;
                    } else {
                        try{
                            Date time1 = format.parse(att.get(i).get(length));
                            Date time2 = format.parse(timestamp[1].trim());
                            att.get(i).set(length, convertToHours(Math.abs(time1.getTime() - time2.getTime())));
                        } catch (Exception e){
                            System.out.println(e);
                            break;
                        }
                    }
                }
            }
            
        }
        signIn.close();
        return att;
    }

    public static String convertToHours(double timeInMils){
        long hrs = (long) (timeInMils / (60 * 60 * 1000)) % 24;
        long min = (long) (timeInMils / (60 * 1000)) % 60;

        if(min >= 30) {
            hrs ++;
        }

        return Long.toString(hrs);
    }
}
