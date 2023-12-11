package Pomodoro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class pomodoroGUI {
    private JFrame frame;
    private JLabel workTimeLabel;
    private JLabel breakTimeLabel;
    private JButton startButton;
    private JButton stopButton;

    public void updateWorkTimeLabel(String text) {
        workTimeLabel.setText(text);
    }

    public void updateBreakTimeLabel(String text) {
        breakTimeLabel.setText(text);
    }

    public pomodoroGUI() {
        frame = new JFrame("Pomodoro Timer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);

        workTimeLabel = new JLabel("Work Time: 25 minutes");
        workTimeLabel.setBorder(new EmptyBorder(10, 10, 10, 10)); 
        workTimeLabel.setFont(new Font("Arial", Font.PLAIN, 18)); 

        breakTimeLabel = new JLabel("Break Time: 5 minutes");
        breakTimeLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        breakTimeLabel.setFont(new Font("Arial", Font.PLAIN, 18)); 

        startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.PLAIN, 18));

        stopButton = new JButton("Stop");
        stopButton.setFont(new Font("Arial", Font.PLAIN, 18)); 

        startButton.addActionListener(e -> {
            pomodoro.startTimer(pomodoro.workTime, pomodoro.breakTime);
        });

        stopButton.addActionListener(e -> {
            pomodoro.stop.set(true);
        });

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(workTimeLabel);
        panel.add(breakTimeLabel);
        panel.add(startButton);
        panel.add(stopButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new pomodoroGUI();
    }
}