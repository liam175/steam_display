package data_display.file_things;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TooManyListenersException;

import javax.comm.CommPort;
import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.swing.JTextArea;

public class data_from_phone implements SerialPortEventListener {

    private Enumeration ports = null;
    private HashMap portMap = new HashMap();
    
    private CommPortIdentifier selectedPortIdentifier = null;
    private SerialPort serialPort = null;

    private InputStream input = null;
    private OutputStream output = null;
    public boolean connected = false;

    private String portsOpen[] = new String[10];
    public void connect(String selected){
        selectedPortIdentifier = (CommPortIdentifier)portMap.get(portsOpen[Integer.parseInt(selected)]);
        System.out.println(portsOpen[Integer.parseInt(selected)]);
        CommPort commPort = null;
        
        try
        {
            //the method below returns an object of type CommPort
            commPort = selectedPortIdentifier.open("Serial USB Terminal", 2000);
            //the CommPort object can be casted to a SerialPort object
            serialPort = (SerialPort)commPort;


            //logging
            System.out.println("succesful connection");
            connected = true;
        }
        catch (PortInUseException e)
        {
            System.out.println("port in use");
        }
        catch (Exception e)
        {
            System.out.println(e.toString()+ " is the error");
        }
    }
    @Override
    public void serialEvent(SerialPortEvent evt) {
        if (evt.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        {
            try
            {
                byte singleData = (byte)input.read();

                if (singleData != 10)
                {
                    importData(new String(new byte[] {singleData}));
                }
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }
    public void searchForPorts(JTextArea text)
    {
        ports = CommPortIdentifier.getPortIdentifiers();
        portsOpen = new String[10];
        String pts = "";
        int i = 0;
        while (ports.hasMoreElements())
        {
            CommPortIdentifier curPort = (CommPortIdentifier)ports.nextElement();

            //get only serial ports
            if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL)
            {portsOpen[i] = curPort.getName();
                i++;
                pts+=" "+curPort.getName();
                portMap.put(curPort.getName(), curPort);
            }
        }
        text.setText(pts);
    }
    public boolean initIOStream()
    {
        //return value for whether opening the streams is successful or not
        boolean successful = false;

        try {
            //
            input = serialPort.getInputStream();
            output = serialPort.getOutputStream();

            successful = true;
            return successful;
        }
        catch (IOException e) {
            System.out.println("failed init io stream");
            return successful;
        }
    }
    public void initListener()
    {
        try
        {
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        }
        catch (TooManyListenersException e)
        {
            System.out.println(e);
        }catch(UnsupportedOperationException e){
            System.out.println(e);
        }
    }
    public void disconnect()
    {
        //close the serial port
        try
        {
            serialPort.removeEventListener();
            serialPort.close();
            input.close();
            output.close();
        }
        catch (Exception e)
        {
            System.out.println("failed to close "+e.toString());
        }
    }
    public void importData(String str){
        System.out.println(str);
        /*int team = 525;
        JSONParser parser = new JSONParser();
        if(!new File("/Users/lostl/Desktop/code/java/steam_display/src/main/java/data_display/file_things/files/t"+ team + ".json").exists()){
            try {
                File myObj = new File("/Users/lostl/Desktop/code/java/steam_display/src/main/java/data_display/file_things/files/t"+ team + ".json");
                myObj.createNewFile();
              } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
              }
        }
        try {
            Object s = parser.parse(new FileReader(
                    "/Users/lostl/Desktop/code/java/steam_display/src/main/java/data_display/file_things/files/t"
                            + team + ".json"));
            JSONArray main_array = (JSONArray) s;
            
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
        */
    }
}
