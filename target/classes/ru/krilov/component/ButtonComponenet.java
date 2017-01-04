/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.krilov.component;

import java.io.Serializable;

import javax.swing.JButton;

import ru.krilov.listener.ButtonClickListener;

/**
 *
 * @author AKS513
 */
public class ButtonComponenet implements Serializable{
    public JButton mainButton(final String name) {
        final JButton button = new JButton(name);
        button.addMouseListener(new ButtonClickListener());
        return button;
    }
}
