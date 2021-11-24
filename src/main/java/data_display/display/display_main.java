package data_display.display;

import javax.swing.*;

import data_display.file_things.get_data;
import java.awt.Color;
import java.awt.*;

public class display_main extends JFrame {
    int xMulti = 166;
    int yMulti = 10;
    public int rounds = 40;
    Color steamRedA = new Color(78,22,20);//dark and correct
    Color steamRedB = new Color(144,12,63);//light
    Color steamGold = new Color(178,159,76);//gold
    public Color colorTable[] = { Color.red, Color.blue, Color.green, Color.CYAN };
    public int shown = 1;
    public void init(get_data data, JTable table, JPanel graphFrame, JTextField teams[], JButton teamSetButton, JTextField rounds, JButton reAverageButton, JTextField shownData, JButton connect,JButton scan,JTextField selectedPort, JTextArea text) {
        setSize(1920, 1080);
        setLayout(null);
        setVisible(true);
        getContentPane().setBackground(Color.gray);
        //
        add(graphFrame);
        graphFrame.setBounds(0, 0, 1400, 600);
        graphFrame.setBackground(Color.lightGray);
        graphFrame.setVisible(true);

        //
        add(table);
        table.setBounds(0, 600, 1400, 1080);
        table.setBackground(Color.darkGray);
        table.setVisible(true);
        table.setForeground(Color.white);
        table.setRowHeight(35);
        //
        for (int t = 0; t < teams.length; t++) {
            add(teams[t]);
            teams[t].setBounds(1400, t * 25, 50, 20);
            teams[t].setBackground(colorTable[t]);
            teams[t].setForeground(Color.white);
            teams[t].setVisible(true);
        }

        add(teamSetButton);
        teamSetButton.setBounds(1480, 50, 20, 20);
        teamSetButton.setBackground(Color.red);
        teamSetButton.setVisible(true);

        add(rounds);
        rounds.setBounds(1455, 50, 20, 20);
        rounds.setVisible(true);

        add(reAverageButton);
        reAverageButton.setBounds(1455, 0, 20, 45);
        reAverageButton.setBackground(Color.cyan);
        reAverageButton.setVisible(true);

        add(shownData);
        shownData.setBackground(Color.pink);
        shownData.setBounds(1400, 75, 100, 20);

        add(connect);
        connect.setBounds(1480, 0, 20, 20);
        connect.setBackground(Color.green);
        connect.setVisible(true);

        add(scan);
        scan.setBounds(1480, 25, 20, 20);
        scan.setBackground(Color.yellow);
        scan.setVisible(true);

        add(selectedPort);
        selectedPort.setBounds(1505, 0, 20, 20);
        selectedPort.setBackground(Color.MAGENTA);
        selectedPort.setVisible(true);

        add(text);
        text.setColumns(5);
        text.setBounds(1505, 25, 100, 75);
        text.setBackground(Color.darkGray);
        text.setForeground(Color.white);
    }

    public void graph(int[] teams, get_data data, Graphics g, int tablePos) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        boolean displayStyleGames = false;// wether you show it as 12 x points or rounds as x points
        if (rounds == -1) {
            displayStyleGames = true;
            graphAverage(tablePos, data, g2);
        } else {
            displayStyleGames = false;
        }
        int roundX = 1400 / rounds;
        for (int t = 0; t < teams.length; t++) {
            g2.setColor(colorTable[t]);
            int points[][] = data.getTeam(teams[t], tablePos);
            for (int i = 0; i < points.length - 1; i++) {
                if (points[i+1][1] != -1) {
                    if (displayStyleGames) {
                        g2.drawLine(i * xMulti, 600 - points[i][1] * yMulti, (i + 1) * xMulti,
                                600 - points[i + 1][1] * yMulti);// each game is a pos
                    } else {
                        g2.drawLine(points[i][0] * roundX, 600 - points[i][1] * yMulti, points[i + 1][0] * roundX,
                                600 - points[i + 1][1] * yMulti);// each round is a pos
                    }

                }
            }
        }
        
    }
    public void graphAverage(int tablePos,get_data data, Graphics2D g2){
        int points[] = data.getAverage(tablePos);
        g2.setColor(colorTable[3]);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i+1] != -1) {
                g2.drawLine(i * xMulti, 600 - points[i] * yMulti, (i + 1) * xMulti,
                600 - points[i + 1] * yMulti);// each round is a pos

            }
        }
    }
}
