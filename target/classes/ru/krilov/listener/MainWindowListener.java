/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.krilov.listener;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author aks513
 */
public class MainWindowListener implements WindowListener, Serializable{

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        /*
        final JFileChooser filesave = new JFileChooser(".");
        
        final int confirmDialog = JOptionPane.showConfirmDialog(
            null, "Saglabat izmaiņas?", "Konponentu saglabašanas logs",
            JOptionPane.OK_CANCEL_OPTION
        );
        
        if(confirmDialog == JOptionPane.OK_OPTION) {
            final int saved = filesave.showSaveDialog(filesave);
            if(saved == JFileChooser.APPROVE_OPTION) {
                try (
                    final ObjectOutputStream out = new ObjectOutputStream(
                        new FileOutputStream(filesave.getSelectedFile())
                    )
                ) {
                    out.writeObject(e.getSource());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                        null, ex.getLocalizedMessage(),
                        "Objekta serializēšanas kļūda!", JOptionPane.ERROR_MESSAGE
                    );
                } 
            }
        } */
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}    
}
