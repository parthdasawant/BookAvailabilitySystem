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

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

@SuppressWarnings("serial")
public class adminMain extends JFrame {
	private static JTable table;
	/**
	 * Launch the application.
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adminMain frame = new adminMain();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		main mn = new main();
		mn.setVisible(false);
		conn.Connect();
		Table_load();
		

	}

	/**
	 * Create the frame.
	 */
	public adminMain() {
		setResizable(false);
		setTitle("Admin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			

		});
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				DefaultTableModel d1 = (DefaultTableModel)table.getModel();
				int selectIndex = table.getSelectedRow();
				
				
				if(d1.getValueAt(selectIndex, 3)==null||d1.getValueAt(selectIndex, 4)==null) {
					textField.setText(d1.getValueAt(selectIndex, 0).toString());
					issueDate.setText("");
					returnDate.setText("");
				}
				else{
					
					textField.setText(d1.getValueAt(selectIndex, 0).toString());
					issueDate.setText(d1.getValueAt(selectIndex, 3).toString());
				
					returnDate.setText(d1.getValueAt(selectIndex, 4).toString());	
				}
				
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Book_id", "Book_name", "Author_name", "Issue_date", "Return_date"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, true, true
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
		

		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Book Availability System");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
		
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
					//?i is for case insensitive for searching string throughout table 
				}
				
				
			}
		});
		searchField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("SEARCH");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton lblNewLabel_2 = new JButton("LOGOUT");
		lblNewLabel_2.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
				
				adminMain.this.hide();
				
				BAS.main.Table_load();
			
				
				
			}
		});
		
		issueDate = new JTextField();
		issueDate.setColumns(10);
		
		returnDate = new JTextField();
		returnDate.setColumns(10);
		
		JButton updateButton = new JButton("UPDATE");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//DefaultTableModel d1 = (DefaultTableModel)table.getModel();
				//int selectIndex = table.getSelectedRow();
				
				//int id = Integer.parseInt(d1.getValueAt(selectIndex, 0).toString());
				String id = textField.getText();
				
				
				String issueD = issueDate.getText();

				String returnD = returnDate.getText();
				
				try {
					if(issueD.equals(returnD) ) 
					{
						//pst = conn.con.prepareStatement("update bas.main set issue_date = null, return_date = null where book_id = ?");
						
						pst = conn.con.prepareStatement("update bas.main set issue_date = null, return_date = null where book_id = ?");
					
							pst.setString(1, id);
					}
					else
					{
						pst = conn.con.prepareStatement("update bas.main set issue_date = ?, return_date = ? where book_id = ?");
						pst.setString(1, issueD);
						pst.setString(2, returnD);
						pst.setString(3, id);
						
					}
					int k = pst.executeUpdate();
					
					if(k==1) {
						JOptionPane.showMessageDialog(null, "Updated");
						issueDate.setText("");
						returnDate.setText("");
						issueDate.requestFocus();
						
						
					}
					
					else
					{
						JOptionPane.showMessageDialog(null, "Error");
						issueDate.setText("");
						returnDate.setText("");
						issueDate.requestFocus();
						
					}
					
				} catch (SQLException e1) {
					
					JOptionPane.showMessageDialog(null, "Error");
					textField.setText("");
					issueDate.setText("");
					returnDate.setText("");
					issueDate.requestFocus();
				    //e1.printStackTrace();
				}
				Table_load();
				BAS.main.Table_load();

							
				
			}
		});
		
		JLabel lblNewLabel_3 = new JLabel("Issue Date");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_3_1 = new JLabel("Return Date");
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblNewLabel_3_2 = new JLabel("Book ID");
		lblNewLabel_3_2.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(379)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
					.addGap(170)
					.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
					.addGap(32))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(392)
					.addComponent(searchField, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
					.addGap(341))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(493)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
					.addGap(75)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(15)
							.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(5)
							.addComponent(issueDate, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(15)
							.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(5)
							.addComponent(returnDate, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(15)
							.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(26)
							.addComponent(updateButton, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
							.addGap(28)))
					.addGap(79))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(13)
							.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(3)))
					.addGap(38)
					.addComponent(searchField, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
					.addGap(1)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addGap(58)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(19)
									.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(textField)
									.addGap(26)))
							.addGap(26)
							.addComponent(issueDate, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
							.addGap(2)
							.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addGap(37)
							.addComponent(returnDate, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
							.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addGap(50)
							.addComponent(updateButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(105)))
					.addGap(80))
		);
		getContentPane().setLayout(groupLayout);
	}	
	static PreparedStatement pst;
	static PreparedStatement getData;
	static ResultSet rs;
	private JTextField searchField;
	private JTextField issueDate;
	private JTextField returnDate;
	private JTextField textField;

	
	
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
