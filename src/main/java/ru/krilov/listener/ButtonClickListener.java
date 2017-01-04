/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.krilov.listener;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.Serializable;
import java.lang.reflect.Array;

import java.time.Month;
import java.time.format.TextStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ru.krilov.component.TimetableRowComponent;
import ru.krilov.data.InformationData;

/**
 *
 * @author AKS513
 */
public class ButtonClickListener implements MouseListener, Serializable {

    @Override
    public void mouseClicked(MouseEvent e) {
        final JButton currentButton = (JButton) e.getComponent();
        final TimetableRowComponent timetable = new TimetableRowComponent();

        final JTable table = (JTable) (
            (JScrollPane) currentButton.getParent().getParent().getComponent(0)
        ).getViewport().getComponent(0);
        
        final JTabbedPane tabbedPane = (JTabbedPane)
            currentButton.getParent().getParent().getParent().getParent();
        
        final JLabel label = (JLabel) (
            (JPanel)currentButton.getParent().getParent().getComponent(2)
        ).getComponent(0);
        
        final Matcher matcher = Pattern.compile("\\d{1,}").matcher(label.getText());
        final DefaultTableModel tablemodel = (DefaultTableModel) table.getModel();
       
        switch (currentButton.getText()) {
            case "Pievienot":
                final JPanel panel = timetable.showMainPanel();
                final int option = JOptionPane.showConfirmDialog(
                    currentButton, panel, "Datu pievinošana!",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
                );

                if (option == JOptionPane.OK_OPTION) {                    
                    final String[] values = IntStream.range(
                            0, panel.getComponentCount()
                    ).filter(
                            index -> index % 2 != 0
                    ).mapToObj(
                            componentIndex -> panel.getComponent(componentIndex)
                    ).map(
                            component -> ((JFormattedTextField) component).getText()
                    ).toArray(String[]::new);
                    
                    final String date = String.format("%s %s",
                        matcher.find()? matcher.group() : "",                        
                        Month.of(
                            tabbedPane.getSelectedIndex()
                        ).getDisplayName(
                            TextStyle.FULL, Locale.forLanguageTag("lv")
                        )
                    );
                                        
                    tablemodel.addRow(new Object[] {
                        date, String.format("%s", Array.get(values, 1)).isEmpty()? "0.00": Array.get(values, 1),
                        String.format("%s", Array.get(values, 2)).isEmpty()? "0.00": Array.get(values, 2),
                        String.format("%s", Array.get(values, 3)).isEmpty()? "Citas": Array.get(values, 3)
                    });
                } break;
            case "Dzēst":
                switch (table.getSelectedRow()) {
                    case -1:
                        JOptionPane.showMessageDialog(
                                currentButton, "Izvelēts ierāksts nav atrāsts.",
                                "Neeksistējoša ieraksta dzēšana!", JOptionPane.ERROR_MESSAGE
                        ); break;
                    default:
                        tablemodel.removeRow(table.getSelectedRow());
                }
                break;
            case "Rediģēt":
                switch (table.getSelectedRow()) {
                    case -1:
                        JOptionPane.showMessageDialog(
                                currentButton, "Izvelēts ierāksts nav atrāsts.",
                                "Neeksistējoša ieraksta rediģēšana!", JOptionPane.ERROR_MESSAGE
                        );
                        break;
                    default:
                        final JPanel editPanel = timetable.parametrMapping(
                                IntStream.range(
                                        0, table.getColumnCount()
                                ).mapToObj(
                                        column -> table.getValueAt(table.getSelectedRow(), column)
                                ).toArray(String[]::new)
                        );

                        final int editOption = JOptionPane.showConfirmDialog(
                                currentButton, editPanel, "Datu rediģēšana!",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
                        );

                        if (editOption == JOptionPane.OK_OPTION) {
                            final String[] values = IntStream.range(
                                    0, editPanel.getComponentCount()
                            ).filter(
                                    index -> index % 2 != 0
                            ).mapToObj(
                                    componentIndex -> editPanel.getComponent(componentIndex)
                            ).map(
                                    component -> ((JFormattedTextField) component).getText()
                            ).toArray(String[]::new);

                            IntStream.range(
                                    1, table.getColumnCount()
                            ).forEach(columnIndex -> {
                                table.setValueAt(
                                        values[columnIndex], table.getSelectedRow(), columnIndex
                                );
                            });
                        }
                }
                break;
            default:
                final List<InformationData> values = new ArrayList<>();
                final List<InformationData> valuesByDate = new ArrayList<>();

                IntStream.range(0, table.getRowCount()).forEach(row -> {
                    final Double difference = Double.parseDouble(
                            String.format("%s", table.getValueAt(row, 1))
                    ) - Double.parseDouble(
                            String.format("%s", table.getValueAt(row, 2))
                    );

                    final String description = String.format("%s", table.getValueAt(row, 3));
                    final InformationData data = new InformationData();
                    data.setDescription(description);
                    data.setPrice(difference);
                    values.add(data);
                });
                
                IntStream.range(0, table.getRowCount()).forEach(row -> {
                    final Double difference = Double.parseDouble(
                            String.format("%s", table.getValueAt(row, 1))
                    ) - Double.parseDouble(
                            String.format("%s", table.getValueAt(row, 2))
                    );

                    final String description = String.format("%s", table.getValueAt(row, 0));
                    final InformationData data = new InformationData();
                    data.setDescription(description);
                    data.setPrice(difference);
                    valuesByDate.add(data);
                });
                
                final Map<String, Double> summary = values.stream().collect(
                    Collectors.groupingBy(
                        InformationData::getDescription,
                        Collectors.summingDouble(InformationData::getPrice)
                    )
                );
                
                final Map<String, Double> summaryByDate = valuesByDate.stream().collect(
                    Collectors.groupingBy(
                        InformationData::getDescription,
                        Collectors.summingDouble(InformationData::getPrice)
                    )
                );
                               
                final JPanel informationPanel = new JPanel(new BorderLayout());
                informationPanel.add(timetable.informationPanelByDescription(summary), BorderLayout.WEST);
                informationPanel.add(timetable.informationPanelByDescription(summaryByDate), BorderLayout.EAST);
                informationPanel.add(timetable.totalInformationPanel(), BorderLayout.SOUTH);
                
                JOptionPane.showMessageDialog(
                    currentButton, informationPanel,
                    "Kopēja informācija!", JOptionPane.INFORMATION_MESSAGE
                );
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
