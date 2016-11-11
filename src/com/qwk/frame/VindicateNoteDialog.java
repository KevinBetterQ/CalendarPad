
package com.qwk.frame;

import com.qwk.dao.Dao;
import com.qwk.tool.AwakeThread;
import com.qwk.tool.ScreenSize;

import java.util.Vector;

public class VindicateNoteDialog extends javax.swing.JDialog {
	
	// Variables declaration - do not modify                     
    private javax.swing.ButtonGroup awokeButtonGroup;
    private javax.swing.JPanel awokePanel;
    private com.qwk.calendar.CalendarComboBox calendarComboBox;
    private javax.swing.JLabel contentLabel;
    private javax.swing.JScrollPane contentScrollPane;
    private javax.swing.JTextArea contentTextArea;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JButton exitButton;
    private javax.swing.JRadioButton noRadioButton;
    private javax.swing.JButton submitButton;
    private com.qwk.mwing.TimeField timeField;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField titleTextField;
    private javax.swing.JRadioButton yesRadioButton;
    // End of variables declaration      

    private static final Dao dao = Dao.getInstance();
    private String type;
    private Vector updateRow;

    /** Creates new form CreateDialog */
    public VindicateNoteDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        ScreenSize.centered(this);
    }

    @Override
    public void setVisible(boolean b) {
        if (updateRow == null) {
            setTitle("添加" + type);
            timeField.fillWithSystemTime();
        } else {
            setTitle("修改" + updateRow.get(1));
            calendarComboBox.getTextField().setText(updateRow.get(2).toString());
            timeField.setTime(updateRow.get(3).toString());
            titleTextField.setText(updateRow.get(4).toString());
            contentTextArea.setText(updateRow.get(6).toString());
            if (updateRow.get(5).toString().equals("1")) {
                yesRadioButton.setSelected(true);
            }
        }
        super.setVisible(b);
    }
                
    private void initComponents() {

        awokeButtonGroup = new javax.swing.ButtonGroup();
        dateLabel = new javax.swing.JLabel();
        calendarComboBox = new com.qwk.calendar.CalendarComboBox();
        timeLabel = new javax.swing.JLabel();
        timeField = new com.qwk.mwing.TimeField();
        titleLabel = new javax.swing.JLabel();
        titleTextField = new javax.swing.JTextField();
        contentLabel = new javax.swing.JLabel();
        contentScrollPane = new javax.swing.JScrollPane();
        contentTextArea = new javax.swing.JTextArea();
        awokePanel = new javax.swing.JPanel();
        yesRadioButton = new javax.swing.JRadioButton();
        noRadioButton = new javax.swing.JRadioButton();
        submitButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        dateLabel.setText("日期：");

        timeLabel.setText("时间：");

        titleLabel.setText("主题：");

        titleTextField.setColumns(40);
        titleTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                titleTextFieldKeyTyped(evt);
            }
        });

        contentLabel.setText("内容：");

        contentTextArea.setColumns(40);
        contentTextArea.setRows(5);
        contentTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                contentTextAreaKeyTyped(evt);
            }
        });
        contentScrollPane.setViewportView(contentTextArea);

        awokePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("提醒"));

        awokeButtonGroup.add(yesRadioButton);
        yesRadioButton.setText("是");

        awokeButtonGroup.add(noRadioButton);
        noRadioButton.setSelected(true);
        noRadioButton.setText("否");

        javax.swing.GroupLayout awokePanelLayout = new javax.swing.GroupLayout(awokePanel);
        awokePanel.setLayout(awokePanelLayout);
        awokePanelLayout.setHorizontalGroup(
            awokePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(awokePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(yesRadioButton)
                .addGap(18, 18, 18)
                .addComponent(noRadioButton)
                .addContainerGap(172, Short.MAX_VALUE))
        );
        awokePanelLayout.setVerticalGroup(
            awokePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(awokePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(awokePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yesRadioButton)
                    .addComponent(noRadioButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        submitButton.setText("确定");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        exitButton.setText("退出");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(awokePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(dateLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(calendarComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(titleLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(titleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(contentLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(contentScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(timeLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(timeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(submitButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(exitButton))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(dateLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(calendarComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(timeLabel)
                    .addComponent(timeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(titleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(titleLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contentLabel)
                    .addComponent(contentScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(awokePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exitButton)
                    .addComponent(submitButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

private void titleTextFieldKeyTyped(java.awt.event.KeyEvent evt) {                                        
// TODO add your handling code here:
    if (titleTextField.getText().length() == 5) {
        evt.consume();
    }
}                                       

private void contentTextAreaKeyTyped(java.awt.event.KeyEvent evt) {                                         
// TODO add your handling code here:
}                                        

private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
// TODO add your handling code here:
    String[] values = new String[6];
    values[1] = calendarComboBox.getTextField().getText();
    values[2] = timeField.getTime();
    values[3] = titleTextField.getText();
    values[4] = yesRadioButton.isSelected() ? "1" : "0";
    values[5] = contentTextArea.getText();
    if (updateRow == null) {
        values[0] = type;
        dao.iNote(values);
    } else {
        values[0] = updateRow.get(0).toString();
        dao.uNote(values);
    }
    AwakeThread awakeThread = AwakeThread.getInstance();
    if (awakeThread.isAlive()) {
        awakeThread.refresh();
        System.out.println("refresh");
    } else {
        if (yesRadioButton.isSelected()) {
            awakeThread.turnOn();
            System.out.println("turnon");
        }
    }
    this.dispose();
}                                            

private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
// TODO add your handling code here:
    dispose();
}                                          

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                VindicateNoteDialog dialog = new VindicateNoteDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

                 

    public void setType(String type) {
        this.type = type;
    }

    public void setUpdateRow(Vector values) {
        this.updateRow = values;
    }
}
