/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.krilov.app;

import ru.krilov.component.TabComponent;
import ru.krilov.listener.MainWindowListener;

import java.awt.GridLayout;
import java.awt.Window;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Home
 */
public class MainClass implements Serializable{
    private static JFrame drawFrame(final String mainWindowValue) {
        Locale.setDefault(Locale.forLanguageTag("ru"));        
        final JFrame mainWindow = new JFrame(mainWindowValue);
        
        mainWindow.setLayout(new GridLayout(1, 1)); 
        mainWindow.add(new TabComponent().showTabbedPane());        
        mainWindow.setLocationByPlatform(Boolean.TRUE);
        mainWindow.setType(Window.Type.UTILITY);
        mainWindow.setResizable(Boolean.FALSE);
        mainWindow.setSize(890, 500);
        mainWindow.addWindowListener(new MainWindowListener());
        
        return mainWindow;
    }
    
    public static void main(final String... args) throws IOException { 
        /*
        final JFileChooser fileopen = new JFileChooser(".");        
        final int confirmDialog = JOptionPane.showConfirmDialog(
            null, "Ieladet datus no faila?", "Konponentu inicializēšanas logs",
            JOptionPane.OK_CANCEL_OPTION
        );        
        
        if(confirmDialog == JOptionPane.OK_OPTION) {
            final int opened = fileopen.showOpenDialog(fileopen);
            if(opened == JFileChooser.APPROVE_OPTION) {
                try(
                    final ObjectInputStream in = new ObjectInputStream(
                        new FileInputStream(fileopen.getSelectedFile())
                    )
                ) {
                    ((JFrame)in.readObject()).setVisible(Boolean.TRUE);
                }catch(Throwable exception) {
                    (drawFrame("Gada budžeta plānotais")).setVisible(Boolean.TRUE);
                } 
            } else
                (drawFrame("Gada budžeta plānotais")).setVisible(Boolean.TRUE);
        } else */
            (drawFrame("Gada budžeta plānotais")).setVisible(Boolean.TRUE);
    }
}