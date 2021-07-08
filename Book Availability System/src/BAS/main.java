package BAS;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.sql.*;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.Toolkit;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class main extends JFrame {
	private static JTable table;
	/**
	 * Launch the application.
	 */
	public main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main frame = new main();
				   	frame.setVisible(true);
				   	
				   	conn.Connect();
					Table_load();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});


	}

	/**
	 * Create the frame.
 
	 */
	public main() throws InterruptedException {
		setTitle("Book Availability System");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 890, 613);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 137, 854, 361);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Book_id", "Book_name", "Author_name", "Issue_date", "Return_date"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(180);
		table.getColumnModel().getColumn(2).setPreferredWidth(180);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
		
		/*table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);*/
		scrollPane.setViewportView(table);
		
		searchField = new JTextField();
		searchField.addKeyListener(new KeyAdapter() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public void keyReleased(KeyEvent e) {
				String name = searchField.getText().trim();
				TableRowSorter rowSorter = new TableRowSorter(table.getModel());
				table.setRowSorter(rowSorter);

				if(name.length()!=0) {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)"+name));
					//?i case insensitive  
				}
				
				
			}
		});
		searchField.setBounds(320, 82, 251, 20);
		getContentPane().add(searchField);
		searchField.setColumns(10);
		

		JLabel lblNewLabel_1 = new JLabel("SEARCH");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(412, 106, 62, 20);
		getContentPane().add(lblNewLabel_1);
		
		JLabel marquee = new JLabel("*Books having Issue & Return Dates are not available till Return Date of respectives");
		marquee.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		marquee.setHorizontalAlignment(SwingConstants.CENTER);
		marquee.setBounds(20, 506, 844, 14);
		getContentPane().add(marquee);

		JButton login = new JButton("Login");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				login l = new login();
				l.setVisible(true);
				
			}
		});
		login.setHorizontalAlignment(SwingConstants.CENTER);
		login.setBounds(767, 11, 79, 23);
		getContentPane().add(login);
		
		JButton refresh = new JButton("Book Availability System");
		
		refresh.setFont(new Font("Tahoma", Font.BOLD, 22));
				
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conn.Connect();
				Table_load();
			}
		});
		refresh.setBounds(273, 11, 343, 60);
		getContentPane().add(refresh);
	}
	
	static PreparedStatement pst;

	static ResultSet rs;
	private JTextField searchField;
		
	

	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void Table_load() {
		int c;
	
			
		
		try {
			pst = conn.con.prepareStatement("Select * from bas.main");
			rs = pst.executeQuery();
			
			
			ResultSetMetaData rsd = rs.getMetaData();
			
			c= rsd.getColumnCount();
			
			DefaultTableModel d = (DefaultTableModel)table.getModel();
			d.setRowCount(0);
			
			while(rs.next())
			{
				Vector v2 = new Vector();
				for (int i=1; i<=c;i++) {
					v2.add(rs.getString("book_id"));
					v2.add(rs.getString("book_name"));
					v2.add(rs.getString("author_name"));
					v2.add(rs.getString("issue_date"));
					v2.add(rs.getString("return_date"));
				}
				d.addRow(v2);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
