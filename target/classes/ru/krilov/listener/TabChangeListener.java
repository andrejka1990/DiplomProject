/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.krilov.listener;

import java.io.Serializable;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ru.krilov.component.TableComponent;

/**
 *
 * @author Home
 */
public class TabChangeListener implements ChangeListener, Serializable{
    private final TableComponent tableComponent = new TableComponent();

    @Override
    public void stateChanged(ChangeEvent e) {
        final JTabbedPane tabbedPane = (JTabbedPane)e.getSource();
        final int selectedMonth = tabbedPane.getSelectedIndex();
        
        switch(selectedMonth) {
            case 0:
                break;
            default:
                final JPanel panel = (JPanel) tabbedPane.getSelectedComponent();
                
                tableComponent.showCalendarTableAfterTabSelected(
                    (JScrollPane) panel.getComponent(0), selectedMonth
                );
                
                panel.getComponent(1).setVisible(Boolean.FALSE);
        }
    }
}
