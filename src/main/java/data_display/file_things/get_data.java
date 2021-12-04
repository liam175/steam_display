package data_display.file_things;

import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class get_data {
    // gets right name for table pos
    public String intToJson[] = { "round", "inner", "outer", "bottom" };
    public String directory = "";
    public Object[] getDataTable(int team) {
        Object out[] = {};
        JSONParser parser = new JSONParser();
        Gson gson = new Gson();
        try {
            Object s = parser.parse(new FileReader(
                directory.replace('\'', '/')+"/t" + team
                            + ".json"));

            out = gson.fromJson(s.toString(), Object[].class);
        } catch (ParseException pe) {

            System.out.println("position: " + pe.getPosition());
            System.out.println(pe);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println("team not found");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return out;
    }

    public int getData(int team, int roundNum, int tablePos) {
        long fin = -1;
        JSONParser parser = new JSONParser();
        if (team > 0) {
            try {
                Object s = parser.parse(new FileReader(directory.replace('\'', '/')+"/t"+ team + ".json"));
                JSONArray main_array = (JSONArray) s;
                if (main_array.size() > roundNum) {
                    JSONObject round = (JSONObject) main_array.get(roundNum);
                    fin = (long) round.get(intToJson[tablePos]);
                }
            } catch (ParseException pe) {

                System.out.println("position: " + pe.getPosition());
                // System.out.println(pe);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
                // System.out.println("team not found");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("Null pointer exeption");
            }
        }
        return (int) fin;
    }

    public int[] getAverage(int tablePos) {
        int fin[] = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
        JSONParser parser = new JSONParser();
        try {
            Object s = parser.parse(new FileReader(
                directory.replace('\'', '/')+"/average.json"));
            JSONArray main_array = (JSONArray) s;
            for (int r = 0; r < main_array.size(); r++) {
                JSONObject round = (JSONObject) main_array.get(r);
                fin[r] = (int) (long) round.get(intToJson[tablePos]);
            }

        } catch (ParseException pe) {

            System.out.println("position: " + pe.getPosition());
            // System.out.println(pe);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            // System.out.println("team not found");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Null pointer exeption");
        }
        return fin;
    }

    public int[][] getTeam(int team, int tablePos) {
        int testArray[][] = { { 0, -1 }, { 0, -1 }, { 0, -1 }, { 0, -1 }, { 0, -1 }, { 0, -1 }, { 0, -1 }, { 0, -1 },
                { 0, -1 }, { 0, -1 }, { 0, -1 }, { 0, -1 } };
        for (int j = 0; j < testArray.length; j++) {
            int in[] = { 0, -1 };
            if (getData(team, 0, 0) != -1) {
                in[0] = getData(team, j, 0);
                in[1] = getData(team, j, tablePos);
                testArray[j] = in;
            }

        }

        return testArray;
    }
}
