package com.qwk.calendar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;

public class MButton extends JButton {

    public MButton(int day) {
        super(day + "");
        setFocusPainted(false);
        setBorderPainted(false);
        setBackground(Color.WHITE);
        setMargin(new Insets(0, 0, 0, 0));
        setFont(new Font("宋体", Font.BOLD, 16));
    }

    public MButton(String day) {
        super(day);
        setFocusPainted(false);
        setBorderPainted(false);
        setBackground(Color.WHITE);
        setMargin(new Insets(0, 0, 0, 0));
    }
}
