/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.krilov.listener;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.Serializable;

import java.time.Month;
import java.time.format.TextStyle;

import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ru.krilov.data.DataClass;

/**
 *
 * @author Home
 */
public class TableCellClickListener implements MouseListener, Serializable {          
    private Object tableSelectedValue;
    
    @Override
    public void mouseClicked(MouseEvent e) {
        final JTable table = (JTable)e.getSource();
        tableSelectedValue = table.getValueAt(
            table.getSelectedRow(),
            table.getSelectedColumn()
        );
                
        final JPanel tabbedPanel = (JPanel) table.getParent().getParent().getParent();
        final JPanel dataPanel = (JPanel) tabbedPanel.getComponent(1);
        final JLabel label = (JLabel)((JPanel) dataPanel.getComponent(2)).getComponent(0);
        final Month currentMonth = Month.of(((JTabbedPane)tabbedPanel.getParent()).getSelectedIndex());
        
        final String currentDate = String.format(
            "Dati par %s.%s.2017", tableSelectedValue, currentMonth.getDisplayName(
                TextStyle.FULL_STANDALONE, Locale.forLanguageTag("lv")
            )
        );
                
        if(tableSelectedValue != null) {
            dataPanel.setVisible(Boolean.TRUE);            
            label.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 20));
            label.setText(currentDate);
            
            final DefaultTableModel tableModel = (DefaultTableModel)(
                (JTable)((JScrollPane)dataPanel.getComponent(0)).getViewport().getView()
            ).getModel();
            
            tableModel.setColumnIdentifiers(new DataClass().timeTableItems().toArray(String[]::new));            
        } else 
            dataPanel.setVisible(Boolean.FALSE);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public Object getTableSelectedValue() {
        return tableSelectedValue;
    }

    public void setTableSelectedValue(Object tableSelectedValue) {
        this.tableSelectedValue = tableSelectedValue;
    }
}
