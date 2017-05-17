package com.lexicvisualization;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by erikn.
 */
public class LexicVisualizeApp {
    private JTextField ListFileTextFeild;
    private JPanel panelMain;
    private JButton enterListButton;
    private JTable lexicSquareTable;

    public LexicVisualizeApp() {
        enterListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String listDir = ListFileTextFeild.getText();
                File file = new File(listDir);

                if(file.isFile()){
                    JOptionPane.showMessageDialog(null, "File found.");

                     try{
                         FileReader reader = new FileReader(file);
                         BufferedReader br = new BufferedReader(reader);
                         String firstLine = br.readLine();
                         int squareSize = Integer.parseInt(firstLine);
                         DefaultTableModel model = new DefaultTableModel(squareSize,squareSize);
                         lexicSquareTable.setVisible(true);
                         lexicSquareTable.setModel(model);
                         lexicSquareTable.setShowGrid(true);
                         lexicSquareTable.setGridColor(Color.black);
                         lexicSquareTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                         lexicSquareTable.setTableHeader(null);
                         int cellSize = 20;
                         for(int i = 0;i<lexicSquareTable.getColumnCount();i++){
                             lexicSquareTable.getColumnModel().getColumn(i).setPreferredWidth(cellSize);
                         }
                         lexicSquareTable.setRowHeight(lexicSquareTable.getColumnModel().getColumn(0).getPreferredWidth());

                         String line;
                         while((line = br.readLine()) != null){
                             int xCord = getXCord(line);
                             int yCord = getYCord(line);
                             String word = getWord(line);
                             String direction = getDir(line);
                             if(direction.equalsIgnoreCase("F")){
                                 JOptionPane.showMessageDialog(null, "Direction Failure");
                             }
                             else if(direction.equalsIgnoreCase("N")){
                                 for(int i=0;i<word.length();i++){
                                     lexicSquareTable.setValueAt(word.charAt(i), yCord - i, xCord);
                                 }
                             }
                             else if(direction.equalsIgnoreCase("S")){
                                 for(int i=0;i<word.length();i++){
                                     lexicSquareTable.setValueAt(word.charAt(i), yCord + i, xCord);
                                 }
                             }
                             else if(direction.equalsIgnoreCase("E")){
                                 for(int i=0;i<word.length();i++){
                                     lexicSquareTable.setValueAt(word.charAt(i), yCord  , xCord + i);
                                 }
                             }
                             else if(direction.equalsIgnoreCase("W")){
                                 for(int i=0;i<word.length();i++){
                                     lexicSquareTable.setValueAt(word.charAt(i), yCord , xCord - i);
                                 }
                             }
                             else if(direction.equalsIgnoreCase("NE")){
                                 for(int i=0;i<word.length();i++){
                                     lexicSquareTable.setValueAt(word.charAt(i), yCord - i, xCord + i);
                                 }
                             }
                             else if(direction.equalsIgnoreCase("SE")){
                                 for(int i=0;i<word.length();i++){
                                     lexicSquareTable.setValueAt(word.charAt(i), yCord + i, xCord + i);
                                 }
                             }
                             else if(direction.equalsIgnoreCase("NW")){
                                 for(int i=0;i<word.length();i++){
                                     lexicSquareTable.setValueAt(word.charAt(i), yCord - i, xCord - i);
                                 }
                             }
                             else if(direction.equalsIgnoreCase("SW")){
                                 for(int i=0;i<word.length();i++){
                                     lexicSquareTable.setValueAt(word.charAt(i), yCord + i, xCord - i);
                                 }
                             }

                         }




                     }catch (IOException e1){
                         System.out.println("Remove Numbers Failed");
                         e1.printStackTrace();
                }


                }
                else{
                    JOptionPane.showMessageDialog(null, "File not found, please try again.");
                }


            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LexicVisualizeApp");
        frame.setContentPane( new LexicVisualizeApp().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static int getXCord(String line){
        StringBuilder stringNum = new StringBuilder();
        for(int i=0;i<line.length();i++){
            if(line.charAt(i) == '('){
                int digiParse = 1;
                while (Character.isDigit(line.charAt(i+digiParse))){
                    stringNum.append(line.charAt(i+digiParse));
                    digiParse++;
                }
            }
        }
        return  Integer.parseInt(stringNum.toString());
    }

    public static int getYCord(String line){
        StringBuilder stringNum = new StringBuilder();
        for(int i=0;i<line.length();i++){
            if(line.charAt(i) == ','){
                int digiParse = 1;
                while (Character.isDigit(line.charAt(i+digiParse))){
                    stringNum.append(line.charAt(i+digiParse));
                    digiParse++;
                }
            }
        }
        return  Integer.parseInt(stringNum.toString());
    }

    public static String getWord(String line){
        int wordLength = 0;
        while(Character.isAlphabetic(line.charAt(wordLength))){
             wordLength++;
        }
        return line.substring(0,wordLength);
    }

    public static String getDir(String line){
        String returner = "F";
        if(line != null && line.length() >= 2 ){
            String dirString = line.substring(Math.max(line.length() - 2,0));
            if(dirString.equalsIgnoreCase("NN")){
                returner = "N";
            }
            if(dirString.equalsIgnoreCase("SS")){
                returner = "S";
            }
            if(dirString.equalsIgnoreCase("EE")){
                returner =  "E";
            }
            if(dirString.equalsIgnoreCase("WW")){
                return "W";
            }
            if(dirString.equalsIgnoreCase("NE")){
                returner = "NE";
            }
            if(dirString.equalsIgnoreCase("SE")){
                returner = "SE";
            }
            if(dirString.equalsIgnoreCase("NW")){
                returner = "NW";
            }
            if(dirString.equalsIgnoreCase("SW")){
                returner = "SW";
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Critical failure, defective line");
            returner = "F";
        }
        return returner;
    }

}
