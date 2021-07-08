package BAS;

import java.awt.*;
import javax.swing.*;
import java.sql.*;

@SuppressWarnings("serial")
public class loading extends JFrame implements Runnable {

	private JPanel contentPane;
	private JProgressBar progressBar;
	Connection conn;
	int s;
	Thread th;

	public static void main(String[] args) {
            new loading().setVisible(true);
	}

	public void setUploading() {
            setVisible(false);
            th.start();
	}

	public void run() {
            try {
                for (int i = 0; i < 200; i++) {
                    s = s + 1;
                    int m = progressBar.getMaximum();
                    int v = progressBar.getValue();
                    if (v < m) {
                        progressBar.setValue(progressBar.getValue() + 1);
                    } else {
                        i = 201;
                        setVisible(false);
                        main mn =new main();
                        mn.setVisible(true);
                        BAS.main.Table_load();
                    }
                    Thread.sleep(50);
                }
            } catch (Exception e) {
		e.printStackTrace();
            }
	}

	public loading() {
           
            super("Loading");
            th = new Thread((Runnable) this);

            setBounds(600, 300, 600, 400);
            contentPane = new JPanel();
            setContentPane(contentPane);
            contentPane.setLayout(null);
	
            progressBar = new JProgressBar();
            progressBar.setForeground(new Color(0, 255, 127));
            progressBar.setFont(new Font("Tahoma", Font.BOLD, 12));
            progressBar.setStringPainted(true);
            progressBar.setBounds(132, 161, 300, 25);
            contentPane.add(progressBar);

            JLabel lblNewLabel_2 = new JLabel("Please Wait....");
            lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
            lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblNewLabel_2.setForeground(new Color(160, 82, 45));
            lblNewLabel_2.setBounds(198, 216, 165, 39);
            contentPane.add(lblNewLabel_2);
            
            JLabel lbllibraryManagement = new JLabel("Book Availability System");
            lbllibraryManagement.setForeground(new Color(0, 0, 0));
            lbllibraryManagement.setFont(new Font("Tahoma", Font.BOLD, 35));
            lbllibraryManagement.setBounds(80, 57, 436, 43);
            contentPane.add(lbllibraryManagement);
                
            setUploading();
	}
}
