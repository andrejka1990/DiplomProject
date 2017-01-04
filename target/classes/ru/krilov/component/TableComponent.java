/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.krilov.component;

import java.io.Serializable;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ru.krilov.data.DataClass;
import ru.krilov.listener.TableCellClickListener;

/**
 *
 * @author Home
 */
public class TableComponent implements Serializable{    
    public JTable mainTable(final String... tableHeaders) {      
        final JTable mainTable = new JTable(tableModel(tableHeaders));
                
        mainTable.getTableHeader().setResizingAllowed(Boolean.FALSE);
        mainTable.getTableHeader().setReorderingAllowed(Boolean.FALSE); 
        mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        mainTable.getColumnModel().getColumn(1).setWidth(500);
        
        return mainTable;
    }
    
    public JTable showCalendarTableAfterTabSelected(final JScrollPane pane, final int selectedTabIndex) {
        final JTable mainTable = (JTable)pane.getViewport().getComponent(0);        
        final DataClass data = new DataClass();
        final int week = data.getWeekCountInCurrentMonth(selectedTabIndex);
        
        ((DefaultTableModel)mainTable.getModel()).setRowCount(week);
        mainTable.addMouseListener(new TableCellClickListener());
                
        mainTable.setRowHeight(
            (
                pane.getHeight() - pane.getColumnHeader().getHeight()
            ) / mainTable.getRowCount()
        );
                
        data.dayInCurrentMonthCollection(
            selectedTabIndex
        ).forEach(value -> 
            mainTable.setValueAt(
                String.format("%d", value.getValue()), 
                value.getWeekNumber() - 1,
                value.getDayNumber() - 1
            )
        );
                
        return mainTable;
    }
    
    public JTable timetable() {
        return new JTable(new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return Boolean.FALSE;
            }
        });
    }
     
    private DefaultTableModel tableModel(final String... tableHeaders) {
       return new DefaultTableModel(tableHeaders, tableHeaders.length) {
           
            @Override
            public boolean isCellEditable(int row, int column) {
                return Boolean.FALSE;
            }
        };
    }
}