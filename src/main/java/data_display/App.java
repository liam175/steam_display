package data_display;

import data_display.display.display_main;
import data_display.display.display_table;
import data_display.file_things.data_from_phone;
import data_display.file_things.get_data;
import data_display.file_things.json_gen;

import javax.swing.*;

import org.json.simple.parser.ParseException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {
    app_input appIn = new app_input();
    display_main d = new display_main();
    json_gen jg = new json_gen();
    data_from_phone dff = new data_from_phone();

    JTextField teams[] = { new JTextField(4), new JTextField(4), new JTextField(4) };
    JTextField shown = new JTextField();
    JTextField selctedPort = new JTextField();
    JTextField rounds = new JTextField(2);
    JTextArea scanTerm = new JTextArea();
    JPanel graphFrame = new JPanel();
    JButton reloadButton = new JButton();
    JButton reAverageButton = new JButton();
    JButton connect = new JButton();
    JButton scanner = new JButton();

    display_table dt = new display_table();
    JTable table = dt.table;

    get_data data = new get_data();

    int teamNums[] = { -1, -1, -1 };

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
        d.init(data, table, graphFrame, teams, reloadButton, rounds, reAverageButton, shown, connect, scanner,
                selctedPort, scanTerm);
        reloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeTeams();
            }
        });
        reAverageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reCalcAverages();
            }
        });
        connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dff.connect(selctedPort.getText());
                if (dff.connected == true) {
                    if (dff.initIOStream() == true) {
                        dff.initListener();
                    }
                }
            }
        });
        scanner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dff.searchForPorts(scanTerm);
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
