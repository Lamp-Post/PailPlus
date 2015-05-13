package com.feildmaster.pailplus.pail;

import com.feildmaster.pailplus.monitor.Processing;
//import feildmaster.PailPlus.Monitors.Util;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class MonitorPanel extends JPanel {
	private static final long serialVersionUID = -6457233117909245809L;
	private JLabel memory = new JLabel();
    private JLabel threads = new JLabel();
    private JLabel tasks = new JLabel();

    public MonitorPanel() {
        initComponents();
        memory.setToolTipText("Max: "+(Processing.memoryMax()/1024L/1024L)+"mb");
    }

    private void initComponents() {
        // Spacers
        JSeparator spacer1 = newSpacer();
        JSeparator spacer2 = newSpacer();
        JSeparator spacer3 = newSpacer();

        // Layout format
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(4)
                    .addComponent(threads, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                    .addComponent(spacer1, GroupLayout.PREFERRED_SIZE, 5  , GroupLayout.PREFERRED_SIZE)
                    .addComponent(memory , GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(spacer2, GroupLayout.PREFERRED_SIZE, 5  , GroupLayout.PREFERRED_SIZE)
                    .addComponent(tasks  , GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                    .addComponent(spacer3, GroupLayout.PREFERRED_SIZE, 5  , GroupLayout.PREFERRED_SIZE)
                )
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(threads, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
                    .addComponent(spacer1, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
                    .addComponent(memory , GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
                    .addComponent(spacer2, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
                    .addComponent(tasks  , GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
                    .addComponent(spacer3, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
                )
        );

        setMonitors();
    }

    protected void setMonitors() {
        memory.setText("Memory: "+(Processing.memoryUsed() / 1024L / 1024L) + "/" + (Processing.memoryTotal() / 1024L / 1024L)+"mb");
        threads.setText("Threads: "+Processing.threadsUsed());
        tasks.setText("Tasks: "+Processing.tasks());
    }

    private JSeparator newSpacer() {
        JSeparator spacer = new JSeparator();
        spacer.setOrientation(SwingConstants.VERTICAL);
        return spacer;
    }

}
