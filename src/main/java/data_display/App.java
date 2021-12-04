package data_display;

import data_display.display.display_main;
import data_display.display.display_table;
import data_display.file_things.get_data;
import data_display.file_things.json_gen;

import javax.swing.*;

import org.apache.log4j.helpers.NullEnumeration;
import org.json.simple.parser.ParseException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Hello world!
 *
 */
public class App {
    app_input appIn = new app_input();
    display_main d = new display_main();
    json_gen jg = new json_gen();

    JTextField teams[] = { new JTextField(4), new JTextField(4), new JTextField(4),new JTextField(4), new JTextField(4), new JTextField(4) };
    JTextField shown = new JTextField();//catagory ex. bottom
    JTextField rounds = new JTextField(2);
    JPanel graphFrame = new JPanel();
    JButton reloadButton = new JButton();
    JButton reAverageButton = new JButton();
    JTextField jsonSource = new JTextField();
    display_table dt = new display_table();
    JTable table = dt.table;

    get_data data = new get_data();

    int teamNums[] = { -1, -1, -1,-1,-1,-1};

    public static void main(String[] args) {
        App a = new App();
        a.mainLoop();
    }

    public void changeTeams() {
        d.shown = appIn.getFind(data, shown);
        teamNums = appIn.getInputs(teams);
        d.rounds = appIn.getRounds(rounds);
        dt.resetTable(data, teamNums[0]);
        
        reGraph();
    }

    public void reGraph() {
        d.graph(teamNums, data, d.getGraphics(), d.shown);
    }

    public void reCalcAverages() {
        try {
            jg.reCalculate(data);
        } catch (IOException | ParseException e) {

        }

    }

    public void mainLoop() {
        try{
jsonSource.setText(Files.readString(Paths.get("/Users/lostl/Desktop/code/java/steam_display/src/main/java/data_display/lastRepo.txt")));
        }catch(FileNotFoundException e){

        }catch(IOException e){

        }
        
        d.init(data, table, graphFrame, teams, reloadButton, rounds, reAverageButton, shown, jsonSource);
        reloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                data.directory = jsonSource.getText().replaceAll("C:", "");
                try {
                    Files.writeString(Paths.get("/Users/lostl/Desktop/code/java/steam_display/src/main/java/data_display/lastRepo.txt"), data.directory);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                changeTeams();
                
            }
        });
        // loop
        while (true) {
            // reGraph();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("thread error");
            }
        }
    }
}
