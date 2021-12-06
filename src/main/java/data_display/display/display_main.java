package data_display.display;

import javax.swing.*;

import data_display.file_things.get_data;
import java.awt.Color;
import java.lang.reflect.Field;
import java.awt.*;

public class display_main extends JFrame {
    int xMulti = 166;
    int yMulti = 10;
    public int rounds = 40;
    Color steamRed = new Color(78, 22, 20);// correct
    Color steamRedA = new Color(98, 28, 25);// light
    Color steamRedB = new Color(46, 13, 12);// dark
    Color steamGold = new Color(178, 159, 76);// gold
    public Color colorTable[] = { Color.red, Color.blue, Color.green, Color.cyan, Color.MAGENTA, Color.ORANGE,Color.WHITE, Color.black};
    public int shown = 1;

    public void init(get_data data, JTable table, JPanel graphFrame, JTextField teams[], JButton teamSetButton,
            JTextField rounds, JButton reAverageButton, JTextField shownData, JTextField fileBar) {
        setSize(1455, 1080);
        setLayout(null);
        setVisible(true);
        setTitle("SteamTrackerHub 2021");
        getContentPane().setBackground(steamRedB);
        //
        add(graphFrame);
        graphFrame.setBounds(55, 0, 1400, 600);
        graphFrame.setBackground(steamRed);
        graphFrame.setVisible(true);

        //
        add(table);
        table.setBounds(0, 600, 1455, 600);
        table.setBackground(steamRedA);
        table.setVisible(true);
        table.setForeground(steamGold);
        table.setGridColor(steamGold);
        table.setRowHeight(35);
        //
        for (int t = 0; t < teams.length; t++) {
            add(teams[t]);
            teams[t].setBounds(0, t * 25, 50, 20);
            teams[t].setBackground(colorTable[t]);
            teams[t].setForeground(Color.gray);
            teams[t].setVisible(true);
        }

        add(teamSetButton);
        teamSetButton.setBounds(0, 450, 50, 20);
        teamSetButton.setBackground(steamGold);
        teamSetButton.setVisible(true);

        add(rounds);
        rounds.setBounds(0, 475, 50, 20);
        rounds.setBackground(steamRedA);
        rounds.setForeground(steamGold);
        rounds.setVisible(true);

        add(reAverageButton);
        reAverageButton.setBounds(0, 550, 50, 20);
        reAverageButton.setBackground(steamGold);
        reAverageButton.setVisible(true);

        add(shownData);
        shownData.setBackground(steamRedA);
        shownData.setForeground(steamGold);
        shownData.setBounds(0, 500, 50, 20);

        add(fileBar);
        fileBar.setBounds(0,575,50,20);
        fileBar.setBackground(steamRedA);
        fileBar.setForeground(steamGold);
    }

    int x = 63;

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
            int multi2 = 1;
            g2.setColor(colorTable[t]);
            float[][] points = data.getTeam(teams[t], tablePos);
            if(points[0][1]<1&&points[0][1]>0){
                multi2 = 10;
            }
            for (int i = 0; i < points.length - 1; i++) {
                if (points[i + 1][1] != -1) {
                    if (displayStyleGames) {
                        g2.drawLine(i * xMulti + x, (int)(600 - points[i][1] * yMulti*multi2), (i + 1) * xMulti + x,
                        (int) (600 - points[i + 1][1] * yMulti*multi2));// each game is a pos
                    } else {
                        g2.drawLine((int)(points[i][0] * roundX + x), (int)(600 - points[i][1] * yMulti*multi2),
                        (int)(points[i + 1][0] * roundX + x), (int)(600 - points[i + 1][1] * yMulti *multi2));// each round is a pos
                    }

                }
            }
        }

    }

    public void graphAverage(int tablePos, get_data data, Graphics2D g2) {
        int points[] = data.getAverage(tablePos);
        g2.setColor(colorTable[6]);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i + 1] != -1) {
                g2.drawLine(i * xMulti + x, 600 - points[i] * yMulti, (i + 1) * xMulti + x,
                        600 - points[i + 1] * yMulti);// each round is a pos

            }
        }
    }
}
