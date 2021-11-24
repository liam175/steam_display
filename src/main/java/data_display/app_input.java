package data_display;

import javax.swing.JTextField;

import data_display.file_things.get_data;

public class app_input {

    public int[] getInputs(JTextField teams[]) {
        int out[] = { -1, -1, -1 };
        for (int i = 0; i < teams.length; i++) {
            try {
                out[i] = Integer.parseInt(teams[i].getText());
            } catch (NumberFormatException e) {
                // e.printStackTrace();
            }
        }
        return out;
    }

    public int getFind(get_data data, JTextField find) {
        int returnVal = 1;
        for (int i = 0; i < data.intToJson.length; i++) {
            if (find.getText().equals(data.intToJson[i])) {
                returnVal = i;
            }
        }
        return returnVal;
    }

    public int getRounds(JTextField t) {
        try {
            return Integer.parseInt(t.getText());
        } catch (NumberFormatException e) {
            // e.printStackTrace();
            return 40;
        }
    }
}
