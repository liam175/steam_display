package data_display.file_things;

import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JProgressBar;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import data_display.display.display_main;

import org.json.simple.parser.JSONParser;

public class json_gen {
    int tablesPoss = 4;

    public void reCalculate(get_data data) throws IOException, ParseException {//
        int vals[][] = { new int[tablesPoss], new int[tablesPoss], new int[tablesPoss], new int[tablesPoss],
                new int[tablesPoss], new int[tablesPoss], new int[tablesPoss], new int[tablesPoss], new int[tablesPoss],
                new int[tablesPoss], new int[tablesPoss], new int[tablesPoss] };
        for (int i = 0; i < 10000; i++) {
            for (int z = 0; z < vals.length; z++) {
                Float input = data.getData(i, z, 0);
                for (int j = 0; j < tablesPoss - 1; j++) {
                    input = data.getData(i, z, j + 1);
                    if (input != -1) {
                        vals[z][j + 1] += input;
                    } else {
                        z = 12;
                        j = tablesPoss;
                    }
                }
                if (input != -1) {
                    vals[z][0]++;
                }
            }
        }
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject objs[] = { new JSONObject(), new JSONObject(), new JSONObject(), new JSONObject(),
                    new JSONObject(), new JSONObject(), new JSONObject(), new JSONObject(), new JSONObject(),
                    new JSONObject(), new JSONObject(), new JSONObject() };
            for (int x = 0; x < vals.length; x++) {
                for (int j = 0; j < tablesPoss - 1; j++) {
                    if (vals[x][0] != 0) {
                        objs[x].put(data.intToJson[j+1],vals[x][j+1]/vals[x][0]);
                    }
                }
                jsonArray.add(objs[x]);
            }
            FileWriter file = new FileWriter(
                data.directory.replace('\'', '/')+"/average.json");
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("average file not found");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
