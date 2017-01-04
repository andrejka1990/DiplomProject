/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.krilov.component;

import java.awt.GridLayout;

import java.io.Serializable;
import java.text.DecimalFormat;

import java.text.DecimalFormatSymbols;

import java.util.Map;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import ru.krilov.data.DataClass;

/**
 *
 * @author AKS513
 */
public class TimetableRowComponent implements Serializable{    
    private double sum;
    
    public JPanel showMainPanel() {
        final DataClass data = new DataClass();        
        final JPanel panel = new JPanel(new GridLayout(0, 2));
        
        data.timeTableItems().forEach(value -> {
            final JFormattedTextField txt = new JFormattedTextField();
            panel.add(new JLabel(value));
            
            switch(value) {
                case "Laiks":
                    txt.setEditable(Boolean.FALSE);
                    break;
                case "Ienākumi":
                case "Izmaksas":
                    final DecimalFormat numberFormat = new DecimalFormat();
                    final DecimalFormatSymbols custom = new DecimalFormatSymbols();
                   
                    custom.setDecimalSeparator('.');
                    numberFormat.setDecimalFormatSymbols(custom);
                    numberFormat.setMinimumIntegerDigits(1);
                    numberFormat.setMaximumIntegerDigits(3);
                    numberFormat.setMinimumFractionDigits(2);
                    numberFormat.setMaximumFractionDigits(2);
                    
                    txt.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(numberFormat)));
                    break;
            }
            panel.add(txt);
        }); 
        
        return panel;
    }
    
    public JPanel parametrMapping(final String... values) {
        final JPanel panel = showMainPanel();
        
        ((JFormattedTextField)panel.getComponent(1)).setText(values[0]);
        ((JFormattedTextField)panel.getComponent(3)).setText(values[1]);
        ((JFormattedTextField)panel.getComponent(5)).setText(values[2]);
        ((JFormattedTextField)panel.getComponent(7)).setText(values[3]);
        
        return panel;
    }
    
    public JPanel informationPanelByDescription(final Map<String, Double> values) {
        this.sum = values.values().stream().mapToDouble(i -> i).sum();
        final JPanel panel = new JPanel(new GridLayout(0,2));
        
        values.entrySet().stream().sorted(
            Map.Entry.<String, Double>comparingByKey()
        ).forEachOrdered(item -> {
            panel.add(new JLabel(String.format("%s:", item.getKey())));
            panel.add(new JLabel(String.format("%s", item.getValue())));
        });
        
        return panel;
    }
    
    public JPanel informationPanelByDate(final Map<String, Double> values) {
        final JPanel panel = new JPanel(new GridLayout(0,2));
        
        values.entrySet().stream().sorted(
            Map.Entry.<String, Double>comparingByKey()
        ).forEachOrdered(item -> {
            panel.add(new JLabel(String.format("%s:", item.getKey())));
            panel.add(new JLabel(String.format("%s", item.getValue())));
        });
        
        return panel;
    }
    
    public JPanel totalInformationPanel() {  
        final JPanel panel = new JPanel();
                
        panel.add(new JLabel("Pieejamais atlikums: "));
        panel.add(new JLabel(String.format("%s", sum)));
        
        return panel;
    }
}