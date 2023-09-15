import java.io.FileReader; 
import java.io.FileWriter;
import java.io.IOException; 
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.swing.*;

public class AttendanceUpdater {
    public static void main(String[] args) throws IOException{  
        JFileChooser open = new JFileChooser();
        int option = open.showOpenDialog(null);
        BufferedReader attendance = null;
        BufferedReader signIn = null;
        String attPath = "";
        if(option == JFileChooser.APPROVE_OPTION){
            try{
                attendance = new BufferedReader(new FileReader(open.getSelectedFile().getPath()));
                attPath = open.getSelectedFile().getPath();
            } catch (Exception e){
                System.out.println(e);
            }
        }
        option = 0;
        option = open.showOpenDialog(null);
        if(option == JFileChooser.APPROVE_OPTION){
            try{
                signIn = new BufferedReader(new FileReader(open.getSelectedFile().getPath()));
            } catch (Exception e){
                System.out.println(e);
            }
        }
        if(signIn != null && attendance!= null){
            String line;
            ArrayList<ArrayList<String>> newAtt = new ArrayList<ArrayList<String>>();
            while((line = attendance.readLine())!= null){
                ArrayList<String> lineTemp = new ArrayList<>(Arrays.asList(line.split(",")));
                newAtt.add(lineTemp);
            }
            newAtt = addEvent(newAtt, signIn,"Test");
            String text = getString(newAtt);
            FileWriter att = new FileWriter(attPath);
            att.write(text);
            att.close();
        }
        attendance.close();
        signIn.close();
    }    

    public static ArrayList<ArrayList<String>> addEvent(ArrayList<ArrayList<String>> att, BufferedReader signIn, String title)throws IOException{
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        int length = att.get(5).size();
        att.get(1).add(title);
        String line;
        signIn.readLine();
        ArrayList<Integer> unfinishedIndexes = new ArrayList<Integer>();
        ArrayList<Integer> updatedIndexes = new ArrayList<Integer>();
        while((line = signIn.readLine()) != null){
            String[] entry = line.split(",");
            String[] timestamp = entry[0].split(" ");
            for(int i = 5; i < att.size(); i ++){
                if((att.get(i).get(0).toLowerCase().equals(entry[2].toLowerCase()) && att.get(i).get(1).toLowerCase().equals(entry[1].toLowerCase())) || (att.get(i).get(0).toLowerCase().equals(entry[1].toLowerCase()) && att.get(i).get(1).toLowerCase().equals(entry[2].toLowerCase()))){
                    if(unfinishedIndexes.contains(i)){
                        try{
                            Date time1 = format.parse(att.get(i).get(length));
                            Date time2 = format.parse(timestamp[1].trim());
                            att.get(i).set(length, convertToHours(Math.abs(time1.getTime() - time2.getTime())));
                            unfinishedIndexes.remove(unfinishedIndexes.indexOf(i));
                        } catch (Exception e){
                            System.out.println(e);
                        }
                    } else {
                        att.get(i).add(timestamp[1]);
                        unfinishedIndexes.add(i);
                        updatedIndexes.add(i);
                    }
                    break;
                }
            }
            
        }
        for(int i = 5; i < att.size(); i ++){
            if(!updatedIndexes.contains(i)){
                att.get(i).add("0");
            }
        }
        for(int i : unfinishedIndexes){
            att.get(i).set(att.get(i).size()-1,"1");
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
    
    public static String getString(ArrayList<ArrayList<String>> arr){
        String res = "";
        for(ArrayList<String> x : arr){
            String temp = x.toString();
            temp = temp.substring(1,temp.length() - 1) + "\n";
            res += temp;
        }
        return res;
    }
}
