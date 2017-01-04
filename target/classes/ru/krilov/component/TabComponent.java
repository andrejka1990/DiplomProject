/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.krilov.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import java.io.Serializable;

import java.time.format.TextStyle;

import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import ru.krilov.data.DataClass;
import ru.krilov.listener.TabChangeListener;

/**
 *
 * @author Home
 */
public class TabComponent implements Serializable{
    private final TableComponent table = new TableComponent();
    private final ButtonComponenet button = new ButtonComponenet();
    private final DataClass data = new DataClass();
    
    public Component showTabbedPane() {
        final JTabbedPane tabbedPane = new JTabbedPane(
            JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT
        );
        
        data.getMonthsValues().forEach(value -> addTabComponent(tabbedPane, value));        
        tabbedPane.addChangeListener(new TabChangeListener());        
        return tabbedPane;
    }
    
    private void addTabComponent(final JTabbedPane tabbedPane, final String tabName) {
        switch(tabName) {
            case "Gada budžets":
                tabbedPane.add(tabName, mainWindow());
                break;
            default:
                tabbedPane.add(tabName, addPanel());
        }
    }
    
    private Component addPanel() {
        final JPanel panel = new JPanel(new GridLayout(0, 2)); 
        final JPanel dataPanel = new JPanel(new BorderLayout());
        final JPanel informationPanel = new JPanel();
        final JPanel headerPanel = new JPanel();
        
        panel.add(
            new JScrollPane(
                table.mainTable(
                    data.getDaysValues().toArray(String[]::new)
                ), JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
            )
        );
        
        dataPanel.add(
            new JScrollPane(
                table.timetable(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
            ),BorderLayout.CENTER
        );  
        
        data.editButtonItems().forEach(value ->             
            informationPanel.add(button.mainButton(value))
        );      
        
        headerPanel.add(new JLabel());
        dataPanel.add(informationPanel, BorderLayout.SOUTH);
        dataPanel.add(headerPanel, BorderLayout.NORTH);        
        panel.add(dataPanel);
        return panel;
    }
    
    private Component mainWindow() {
        final JPanel panel = new JPanel(new GridLayout(0, 4)); 
        
        data.getMonthsFullData().forEach(month -> {
            drawGraphic(
                month.getDisplayName(
                    TextStyle.FULL, Locale.forLanguageTag("lv")
                ), panel, month.getValue()
            );
        });
        
        return panel;
    }
    
    private void drawGraphic(final String item, final JPanel parentPanel, final int monthNumber) {
        final JPanel newPanel = new JPanel(new BorderLayout());
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        /*
        data.dayInCurrentMonthCollection(monthNumber).forEach(value -> {
            final double count = Math.random()*200;
            dataset.addValue(500-count, "Atlikums", String.format("%s", value));
            dataset.addValue(count, "Pateres", String.format("%s", value));
        });
        */
        final JFreeChart chart = ChartFactory.createLineChart(item, null, null, dataset);
        newPanel.add(new ChartPanel(chart), BorderLayout.CENTER);
        parentPanel.add(newPanel);
    }
}
