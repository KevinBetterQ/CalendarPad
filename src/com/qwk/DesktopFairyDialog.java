package com.qwk;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.qwk.tabbedPane.CalendarPanel;
import com.qwk.tabbedPane.ListPanel;
import com.qwk.tool.ScreenSize;

//
public class DesktopFairyDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel scrollCaptionPanel;
	private JTabbedPane tabbedPane;

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DesktopFairyDialog.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(DesktopFairyDialog.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(DesktopFairyDialog.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			Logger.getLogger(DesktopFairyDialog.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DesktopFairyDialog dialog = new DesktopFairyDialog();
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog
	 */
	public DesktopFairyDialog() {
		super();
		setSize(482, 520);
		setTitle("日历记事本");

		tabbedPane = new JTabbedPane();
		tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
		/*tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				tabbedPaneStateChanged(evt);
			}
		});*/
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		CalendarPanel calendarPanel = new CalendarPanel();
		calendarPanel.setName("日历"); // NOI18N
		tabbedPane.addTab("日历", calendarPanel);

		ListPanel planListPanel = new ListPanel();
		planListPanel.setName("计划"); // NOI18N
		planListPanel.setDatas();
		tabbedPane.addTab("计划", planListPanel);

		ScreenSize.centered(this);
		
	}
}
