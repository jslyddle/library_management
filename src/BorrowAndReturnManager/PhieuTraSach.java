package BorrowAndReturnManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PhieuTraSach extends JFrame {

	private JPanel contentPane;
	private JTextField phieutrasach_ID_textfield;
	private JTextField phieutrasach_phieumuonsach_textfield;
	private JTextField phieutrasach_find_textfield;
	DefaultTableModel defaultTable;
	JTable jt;
	JScrollPane phieutrasach_scrollpane;
	JPanel phieutrasach_panel;
	
	Click_GetID click_GetMAPHIEUTRASACH;
	Click_GetID click_GETMAPHIEUMUONSACH;
	
	JDateChooser dateChooser;
	
	private String url = "jdbc:mysql://remotemysql.com/";
	private String dbName = "K74c1CEtnS";
	
	double TIENNOKYNAY = 0;
	
	boolean click_sign = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PhieuTraSach frame = new PhieuTraSach();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PhieuTraSach() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                this.setLocationRelativeTo(null);
                this.setTitle("Return Books");
                ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("assets/borrow_and_return.png"));
                setIconImage(icon.getImage());                
		setBounds(100, 100, 926, 626);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		phieutrasach_panel = new JPanel();
		phieutrasach_panel.setBackground(new Color(255, 239, 213));
		phieutrasach_panel.setBounds(199, 41, 670, 500);
		contentPane.add(phieutrasach_panel);
		phieutrasach_panel.setLayout(null);
		
		// ID
		JLabel phieutrasach_ID_label = new JLabel("ID:");
		phieutrasach_ID_label.setFont(new Font("Tahoma", Font.BOLD, 18));
		phieutrasach_ID_label.setBounds(31, 21, 46, 30);
		phieutrasach_panel.add(phieutrasach_ID_label);
		
		phieutrasach_ID_textfield = new JTextField();
		phieutrasach_ID_textfield.setFocusable(false);
		phieutrasach_ID_textfield.setBounds(72, 21, 74, 30);
		phieutrasach_ID_textfield.setEditable(false);
		phieutrasach_panel.add(phieutrasach_ID_textfield);
		phieutrasach_ID_textfield.setColumns(10);
		
		
		// Phieu Muon Sach
		JLabel phieutrasach_phieumuonsach_label = new JLabel("M\u00E3 phi\u1EBFu m\u01B0\u1EE3n s\u00E1ch:");
		phieutrasach_phieumuonsach_label.setFont(new Font("Tahoma", Font.BOLD, 18));
		phieutrasach_phieumuonsach_label.setBounds(236, 21, 208, 30);
		phieutrasach_panel.add(phieutrasach_phieumuonsach_label);
		
		phieutrasach_phieumuonsach_textfield = new JTextField(5);
		phieutrasach_phieumuonsach_textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if(Character.isLetter(c)) {
					phieutrasach_phieumuonsach_textfield.setEditable(false);
				}
				else phieutrasach_phieumuonsach_textfield.setEditable(true);
			}
		});
		phieutrasach_phieumuonsach_textfield.setDocument(new JTextFieldCharLimit(5));
		phieutrasach_phieumuonsach_textfield.setColumns(10);
		phieutrasach_phieumuonsach_textfield.setBounds(448, 21, 76, 30);
		phieutrasach_panel.add(phieutrasach_phieumuonsach_textfield);
		
		
		// Ngay Tra
		JLabel phieutrasach_ngaytra_label = new JLabel("Ng\u00E0y tr\u1EA3:");
		phieutrasach_ngaytra_label.setFont(new Font("Tahoma", Font.BOLD, 18));
		phieutrasach_ngaytra_label.setBounds(31, 71, 99, 30);
		phieutrasach_panel.add(phieutrasach_ngaytra_label);
		
		dateChooser = new JDateChooser();
		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
		editor.setEditable(false);
		dateChooser.setDateFormatString("dd/MM/yyy");
		dateChooser.setBounds(125, 71, 124, 30);
		phieutrasach_panel.add(dateChooser);
		
		// Find ID
		JLabel phieutrasach_find_label = new JLabel("Search ID:");
		phieutrasach_find_label.setFont(new Font("Tahoma", Font.ITALIC, 18));
		phieutrasach_find_label.setBounds(304, 71, 99, 30);
		phieutrasach_panel.add(phieutrasach_find_label);
		
		phieutrasach_find_textfield = new JTextField(5);
		phieutrasach_find_textfield.setDocument(new JTextFieldCharLimit(5));
		phieutrasach_find_textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if(Character.isLetter(c)) {
					phieutrasach_find_textfield.setEditable(false);
				}
				else phieutrasach_find_textfield.setEditable(true);
			}
		});
		phieutrasach_find_textfield.setColumns(10);
		phieutrasach_find_textfield.setBounds(413, 62, 74, 30);
		phieutrasach_panel.add(phieutrasach_find_textfield);
		
		// Find Button Event
		JLabel phieutrasach_find_button = new JLabel("");
		phieutrasach_find_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ImageIcon img_find_clickedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/find_clickedbutton.png"));
				Image img1 = img_find_clickedbutton.getImage();
				Image img2 = img1.getScaledInstance(104, 39,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieutrasach_find_button.setIcon(imageIcon);
				
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				ImageIcon img_find_releasedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/fiind_button.png"));
				Image img1 = img_find_releasedbutton.getImage();
				Image img2 = img1.getScaledInstance(104, 39,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieutrasach_find_button.setIcon(imageIcon);
				
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				FindButtonEvent();
			}
		});
		phieutrasach_find_button.setBounds(546, 65, 104, 39);
		ImageIcon img_find_button = new ImageIcon(this.getClass().getClassLoader().getResource("assets/fiind_button.png"));
		Image img1 = img_find_button.getImage();
		Image img2 = img1.getScaledInstance(104, 39,Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(img2);
		phieutrasach_find_button.setIcon(imageIcon);
		phieutrasach_panel.add(phieutrasach_find_button);
		
		
		// Add Button Event
		JLabel phieutrasach_add_button = new JLabel("");
		phieutrasach_add_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ImageIcon img_add_clickedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/add_clickedbutton.png"));
				Image img1 = img_add_clickedbutton.getImage();
				Image img2 = img1.getScaledInstance(137, 39,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieutrasach_add_button.setIcon(imageIcon);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				ImageIcon img_add_releasedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/add_button.png"));
				Image img1 = img_add_releasedbutton.getImage();
				Image img2 = img1.getScaledInstance(137, 39,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieutrasach_add_button.setIcon(imageIcon);
				
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				AddButtonEvent();
			}
		});
		phieutrasach_add_button.setBounds(517, 134, 137, 39);
		ImageIcon img_add_button = new ImageIcon(this.getClass().getClassLoader().getResource("assets/add_button.png"));
		img1 = img_add_button.getImage();
		img2 = img1.getScaledInstance(137, 39,Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(img2);
		phieutrasach_add_button.setIcon(imageIcon);
		phieutrasach_panel.add(phieutrasach_add_button);
		
		
		// Select Button Event
		JLabel phieutrasach_select_button = new JLabel("");
		phieutrasach_select_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ImageIcon img_select_clickedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/select_clickedbutton.png"));
				Image img1 = img_select_clickedbutton.getImage();
				Image img2 = img1.getScaledInstance(137, 39,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieutrasach_select_button.setIcon(imageIcon);
				
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				ImageIcon img_select_releasedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/select_button.png"));
				Image img1 = img_select_releasedbutton.getImage();
				Image img2 = img1.getScaledInstance(137, 39,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieutrasach_select_button.setIcon(imageIcon);
				
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				ClickSelectButton(jt);
			}
		});
		phieutrasach_select_button.setBounds(517, 203, 137, 39);
		ImageIcon img_select_button = new ImageIcon(this.getClass().getClassLoader().getResource("assets/select_button.png"));
		img1 = img_select_button.getImage();
		img2 = img1.getScaledInstance(137, 39,Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(img2);
		phieutrasach_select_button.setIcon(imageIcon);
		phieutrasach_panel.add(phieutrasach_select_button);
		
		
		// Update Button Event
		JLabel phieutrasach_update_button = new JLabel("");
		phieutrasach_update_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ImageIcon img_update_clickedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/update_clickedbutton.png"));
				Image img1 = img_update_clickedbutton.getImage();
				Image img2 = img1.getScaledInstance(137, 39,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieutrasach_update_button.setIcon(imageIcon);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				ImageIcon img_update_releasedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/update_button.png"));
				Image img1 = img_update_releasedbutton.getImage();
				Image img2 = img1.getScaledInstance(137, 39,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieutrasach_update_button.setIcon(imageIcon);
				
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				UpdateButtonEvent();
			}
		});
		phieutrasach_update_button.setBounds(517, 280, 137, 39);
		ImageIcon img_update_button = new ImageIcon(this.getClass().getClassLoader().getResource("assets/update_button.png"));
		img1 = img_update_button.getImage();
		img2 = img1.getScaledInstance(137, 39,Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(img2);
		phieutrasach_update_button.setIcon(imageIcon);
		phieutrasach_panel.add(phieutrasach_update_button);
		
		
		// Delete Button Event
		JLabel phieutrasach_delete_button = new JLabel("");
		phieutrasach_delete_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ImageIcon img_delete_clickedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/delete_clickedbutton.png"));
				Image img1 = img_delete_clickedbutton.getImage();
				Image img2 = img1.getScaledInstance(137, 39,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieutrasach_delete_button.setIcon(imageIcon);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				ImageIcon img_delete_releasedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/delete_button.png"));
				Image img1 = img_delete_releasedbutton.getImage();
				Image img2 = img1.getScaledInstance(137, 39,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieutrasach_delete_button.setIcon(imageIcon);
				
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				DeleteButtonEvent();
			}
		});
		phieutrasach_delete_button.setBounds(517, 357, 137, 39);
		ImageIcon img_delete_button = new ImageIcon(this.getClass().getClassLoader().getResource("assets/delete_button.png"));
		img1 = img_delete_button.getImage();
		img2 = img1.getScaledInstance(137, 39,Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(img2);
		phieutrasach_delete_button.setIcon(imageIcon);
		phieutrasach_panel.add(phieutrasach_delete_button);
		
		
		// Reload Button Event
		JLabel phieutrasach_reload_button = new JLabel("");
		phieutrasach_reload_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ImageIcon img_reload_clickedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/reload_clickedbutton.png"));
				Image img1 = img_reload_clickedbutton.getImage();
				Image img2 = img1.getScaledInstance(137, 39,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieutrasach_reload_button.setIcon(imageIcon);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				ImageIcon img_reload_releasedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/reload_button.png"));
				Image img1 = img_reload_releasedbutton.getImage();
				Image img2 = img1.getScaledInstance(137, 39,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieutrasach_reload_button.setIcon(imageIcon);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				ReloadButtonEvent();
			}
		});
		phieutrasach_reload_button.setBounds(523, 434, 137, 39);
		ImageIcon img_reload_button = new ImageIcon(this.getClass().getClassLoader().getResource("assets/reload_button.png"));
		img1 = img_reload_button.getImage();
		img2 = img1.getScaledInstance(137, 39,Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(img2);
		phieutrasach_reload_button.setIcon(imageIcon);
		phieutrasach_panel.add(phieutrasach_reload_button);
		
		//Table
		String column[]={"STT","MÃ PHIẾU TRẢ SÁCH", "MÃ PHIẾU MƯỢN SÁCH", "NGÀY TRẢ","SỐ NGÀY MƯỢN","TIỀN PHẠT KỲ NÀY", "TIỀN NỢ KỲ NÀY"};
		defaultTable = new DefaultTableModel(null, column);
		jt=new JTable(defaultTable);
		jt.setSize(300,300);
	    //jt.setBounds(30,40,00,200);  
	    jt.setPreferredScrollableViewportSize(new Dimension(500, 70));
	    jt.setFillsViewportHeight(true);
	    //jt.setBorder(BorderFactory.createLineBorder(Color.BLUE));
	    
	    ArrayList<PhieuTraSachModel> phieutrasachList = new ArrayList<>();
	    
	    try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url + dbName, "K74c1CEtnS","5HlVA1v9rX");
			  PreparedStatement preparedStatement_GETTABLE = (PreparedStatement)
						connection.prepareStatement("Select * from phieutrasach;");
			    
				ResultSet resultSet_getTABLE = preparedStatement_GETTABLE.executeQuery();
				while (resultSet_getTABLE.next()) {
					int MAPHIEUTRASACH = resultSet_getTABLE.getInt("MAPHIEUTRASACH");
					int MAPHIEUMUONSACH = resultSet_getTABLE.getInt("MAPHIEUMUONSACH");
					String NGAYTRA = resultSet_getTABLE.getString("NGAYTRA");
					int SONGAYMUON = resultSet_getTABLE.getInt("SONGAYMUON");
					double TIENPHATKYNAY = resultSet_getTABLE.getDouble("TIENPHATKYNAY");
					double TIENNOKYNAY = resultSet_getTABLE.getDouble("TIENNOKYNAY");
					PhieuTraSachModel phieuTraSachModel = new PhieuTraSachModel(MAPHIEUTRASACH, MAPHIEUMUONSACH, NGAYTRA, SONGAYMUON, TIENPHATKYNAY, TIENNOKYNAY);
					phieutrasachList.add(phieuTraSachModel);
				}
				
				Object[] row = new Object[7];
				for(int i = 0; i <phieutrasachList.size(); i++) {
				    row[0] = i+1;
				    row[1] = phieutrasachList.get(i).getMAPHIEUTRASACH();
				    row[2] = phieutrasachList.get(i).getMAPHIEUMUONSACH();
				    row[3] = phieutrasachList.get(i).getNGAYTRA();;
				    row[4] = phieutrasachList.get(i).getSONGAYMUON();
				    row[5] = phieutrasachList.get(i).getTIENPHATKYNAY();
				    row[6] = phieutrasachList.get(i).getTIENNOKYNAY();
				    defaultTable.addRow(row);
				}
			preparedStatement_GETTABLE.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    jt.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
					
			}
				
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
					
			}
				
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
					
			}
				
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
					
			}
				
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
					int index_row = jt.rowAtPoint(e.getPoint());
			           int numcols = defaultTable.getColumnCount();
			           for (int i = 0; i<numcols; i++) {
			           	if(i==1) {
			           		int maphieutrasach = (Integer)defaultTable.getValueAt(index_row, i);
			           		String sMaphieutrasachString = Integer.toString(maphieutrasach);
			           		click_GetMAPHIEUTRASACH = new Click_GetID(maphieutrasach);
			           		phieutrasach_ID_textfield.setText(sMaphieutrasachString);
			           	}
			           	if(i==2) {
			           		int maphieumuonsach = (Integer)defaultTable.getValueAt(index_row, i);
			           		click_GETMAPHIEUMUONSACH = new Click_GetID(maphieumuonsach);
			           		String sMaphieumuonsachString = Integer.toString(maphieumuonsach);
			           		phieutrasach_phieumuonsach_textfield.setText(sMaphieumuonsachString);
			            }
			           	if(i==3) {
			           		String ngaytra = (String)defaultTable.getValueAt(index_row, i);
			            	java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse(ngaytra);
			            	dateChooser.setDate(date);
			            }
			           	if(i==6) TIENNOKYNAY = (Double)defaultTable.getValueAt(index_row, i);
			         
			        }
			           click_sign = true;
				} catch (Exception e2) {
						
				} 
					
			}
		});
	    
	    
	    phieutrasach_scrollpane = new JScrollPane(jt);
		phieutrasach_scrollpane.setBounds(41, 134, 466, 339);
		phieutrasach_panel.add(phieutrasach_scrollpane);
	  
	}
	
	// Add Button Event
	public void AddButtonEvent() {
		click_sign = false;
		
		boolean CheckMAPHIEUMUONSACH_Is_Exist = false;
		
		String maphieumuonsach = phieutrasach_phieumuonsach_textfield.getText().toString();
		String sNgaytra = "";
		double tienphatkynay = 0;
		double tiennokynay = 0;
		int songaymuontoida = 4;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			sNgaytra = dateFormat.format(dateChooser.getDate());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if (!maphieumuonsach.trim().equals("") & !sNgaytra.trim().equals(""))
		{
			int iMaphieumuonsach = Integer.parseInt(maphieumuonsach);
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = (Connection) DriverManager.getConnection(url + dbName, "K74c1CEtnS","5HlVA1v9rX");
				PreparedStatement preparedStatement_checkMAPHIEUMUONSACH = (PreparedStatement)
						connection.prepareStatement("SELECT * FROM phieumuonsach WHERE MAPHIEUMUONSACH='" + iMaphieumuonsach + "';");
				ResultSet resultSet_checkMAPHIEUMUONSACH = preparedStatement_checkMAPHIEUMUONSACH.executeQuery();
				while(resultSet_checkMAPHIEUMUONSACH.next()) {
					CheckMAPHIEUMUONSACH_Is_Exist = true;
				}
				
				if(CheckMAPHIEUMUONSACH_Is_Exist == true) {
					phieutrasach_phieumuonsach_textfield.setText("");
					dateChooser.setDate(null);
					Date ngaymuon;
					int songaymuon = 0;
					int masach = -1;
					int madocgia = -1;
					double tongnodocgia = 0;
					Date ngaytra = new SimpleDateFormat("dd/MM/yyyy").parse(sNgaytra); 
					
					PreparedStatement preparedStatement_GETNGAYMUON = (PreparedStatement)
							connection.prepareStatement("SELECT NGAYMUON FROM phieumuonsach WHERE MAPHIEUMUONSACH='" + iMaphieumuonsach + "';");
					ResultSet resultSet_GETNGAYMUON = preparedStatement_GETNGAYMUON.executeQuery();
					while(resultSet_GETNGAYMUON.next()) {
						String sNgayMuon = resultSet_GETNGAYMUON.getString("NGAYMUON");
						ngaymuon = new SimpleDateFormat("dd/MM/yyyy").parse(sNgayMuon);
						long lSongaymuon = ngaytra.getTime() - ngaymuon.getTime();
						long lSONGAYMUON = TimeUnit.MILLISECONDS.toDays(lSongaymuon);
						String sSongaymuon = Long.toString(lSONGAYMUON);
						songaymuon = Integer.parseInt(sSongaymuon);
						if(songaymuon > songaymuontoida) {
							tienphatkynay = (songaymuon - songaymuontoida) * 1000;
							tiennokynay += tienphatkynay;
						}
						
					}
					
					PreparedStatement preparedStatement_ADDPHIEUTRASACH = (PreparedStatement)
							connection.prepareStatement("INSERT INTO phieutrasach(MAPHIEUMUONSACH,NGAYTRA,SONGAYMUON,TIENPHATKYNAY,TIENNOKYNAY) VALUES(?,?,?,?,?);");
					preparedStatement_ADDPHIEUTRASACH.setInt(1, iMaphieumuonsach);
					preparedStatement_ADDPHIEUTRASACH.setString(2, sNgaytra);
					preparedStatement_ADDPHIEUTRASACH.setInt(3, songaymuon);
					preparedStatement_ADDPHIEUTRASACH.setDouble(4, tienphatkynay);
					preparedStatement_ADDPHIEUTRASACH.setDouble(5, tiennokynay);
					int resultSet_ADDPHIEUTRASACH = preparedStatement_ADDPHIEUTRASACH.executeUpdate();
					
					PreparedStatement preparedStatement_GETMASACH = (PreparedStatement)
							connection.prepareStatement("SELECT MASACH FROM phieumuonsach WHERE MAPHIEUMUONSACH='" + iMaphieumuonsach + "';");
					ResultSet resultSet_GETMASACH = preparedStatement_GETMASACH.executeQuery();
					while(resultSet_GETMASACH.next()) {
						masach = resultSet_GETMASACH.getInt("MASACH");
					}
					
					PreparedStatement preparedStatement_UPDATETRANGTHAISACH = (PreparedStatement)
							connection.prepareStatement("UPDATE thongtinsach SET TRANGTHAI=? WHERE MASACH='" + masach + "';");
					preparedStatement_UPDATETRANGTHAISACH.setInt(1, 0);
					int resultSet_UPDATETRANGTHAISACH = preparedStatement_UPDATETRANGTHAISACH.executeUpdate();
					
					PreparedStatement preparedStatement_GETMADOCGIA = (PreparedStatement)
							connection.prepareStatement("SELECT MADOCGIA FROM phieumuonsach WHERE MAPHIEUMUONSACH='" + iMaphieumuonsach + "';");
					ResultSet resultSet_GETMADOCGIA = preparedStatement_GETMADOCGIA.executeQuery();
					while(resultSet_GETMADOCGIA.next()) {
						madocgia = resultSet_GETMADOCGIA.getInt("MADOCGIA");
					}
					
					PreparedStatement preparedStatement_GETTONGNODOCGIA = (PreparedStatement)
							connection.prepareStatement("SELECT TONGNODOCGIA FROM thedocgia WHERE MADOCGIA ='" + madocgia + "';");
					ResultSet resultSet_GETTONGNODOCGIA = preparedStatement_GETTONGNODOCGIA.executeQuery();
					while(resultSet_GETTONGNODOCGIA.next()) {
						tongnodocgia = resultSet_GETTONGNODOCGIA.getDouble("TONGNODOCGIA");
					}
					
					PreparedStatement preparedStatement_UPDATETONGNODOCGIA = (PreparedStatement)
							connection.prepareStatement("UPDATE thedocgia SET TONGNODOCGIA=? WHERE MADOCGIA='" + madocgia + "';");
					preparedStatement_UPDATETONGNODOCGIA.setDouble(1, tongnodocgia + tiennokynay);
					int resultSet_UPDATETONGNODOCGIA = preparedStatement_UPDATETONGNODOCGIA.executeUpdate();
					
					if(resultSet_ADDPHIEUTRASACH > 0 && resultSet_UPDATETRANGTHAISACH > 0 && resultSet_UPDATETONGNODOCGIA > 0) {
						phieutrasach_panel.remove(phieutrasach_scrollpane);;
						String column[]={"STT","MÃ PHIẾU TRẢ SÁCH", "MÃ PHIẾU MƯỢN SÁCH", "NGÀY TRẢ","SỐ NGÀY MƯỢN","TIỀN PHẠT KỲ NÀY", "TIỀN NỢ KỲ NÀY"};
						defaultTable = new DefaultTableModel(null, column);
						jt=new JTable(defaultTable);
						jt.setSize(300,300);
					    //jt.setBounds(30,40,00,200);  
					    jt.setPreferredScrollableViewportSize(new Dimension(500, 70));
					    jt.setFillsViewportHeight(true);
					    //jt.setBorder(BorderFactory.createLineBorder(Color.BLUE));
					    
					    ArrayList<PhieuTraSachModel> phieutrasachList = new ArrayList<>();
					    
					    PreparedStatement preparedStatement_GETTABLE = (PreparedStatement)
								connection.prepareStatement("Select * from phieutrasach;");
					    
						ResultSet resultSet_getTABLE = preparedStatement_GETTABLE.executeQuery();
						while (resultSet_getTABLE.next()) {
							int MAPHIEUTRASACH = resultSet_getTABLE.getInt("MAPHIEUTRASACH");
							int MAPHIEUMUONSACH = resultSet_getTABLE.getInt("MAPHIEUMUONSACH");
							String NGAYTRA = resultSet_getTABLE.getString("NGAYTRA");
							int SONGAYMUON = resultSet_getTABLE.getInt("SONGAYMUON");
							double TIENPHATKYNAY = resultSet_getTABLE.getDouble("TIENPHATKYNAY");
							double TIENNOKYNAY = resultSet_getTABLE.getDouble("TIENNOKYNAY");
							PhieuTraSachModel phieuTraSachModel = new PhieuTraSachModel(MAPHIEUTRASACH, MAPHIEUMUONSACH, NGAYTRA, SONGAYMUON, TIENPHATKYNAY, TIENNOKYNAY);
							phieutrasachList.add(phieuTraSachModel);
						}
						
						Object[] row = new Object[7];
						for(int i = 0; i <phieutrasachList.size(); i++) {
						    row[0] = i+1;
						    row[1] = phieutrasachList.get(i).getMAPHIEUTRASACH();
						    row[2] = phieutrasachList.get(i).getMAPHIEUMUONSACH();
						    row[3] = phieutrasachList.get(i).getNGAYTRA();;
						    row[4] = phieutrasachList.get(i).getSONGAYMUON();
						    row[5] = phieutrasachList.get(i).getTIENPHATKYNAY();
						    row[6] = phieutrasachList.get(i).getTIENNOKYNAY();
						    defaultTable.addRow(row);
						}
					    phieutrasach_scrollpane = new JScrollPane(jt);
					    phieutrasach_scrollpane.setBounds(41, 134, 466, 339);
						phieutrasach_panel.add(phieutrasach_scrollpane);
						phieutrasach_panel.repaint();
						phieutrasach_panel.revalidate();
						
						JOptionPane.showMessageDialog(null, "Bạn đã thêm phiếu trả sách thành công!!!");
						preparedStatement_GETTABLE.close();
						resultSet_getTABLE.close();
						
					}
					else JOptionPane.showMessageDialog(null, "Phiếu trả sách bạn vừa thêm không thành công!!! Vui lòng thử lại!!!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
					preparedStatement_GETNGAYMUON.close();
					resultSet_GETNGAYMUON.close();
					preparedStatement_ADDPHIEUTRASACH.close();
					preparedStatement_GETMASACH.close();
					resultSet_GETMASACH.close();
					preparedStatement_UPDATETRANGTHAISACH.close();
					preparedStatement_GETMADOCGIA.close();
					resultSet_GETMADOCGIA.close();
					preparedStatement_GETTONGNODOCGIA.close();
					resultSet_GETTONGNODOCGIA.close();
					preparedStatement_UPDATETONGNODOCGIA.close();
					
					
				}
				else JOptionPane.showMessageDialog(null, "Mã phiếu mượn sách vừa nhập không tồn tại trong hệ thống!!! Việc thêm phiếu trả sách không thành công!!!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
				preparedStatement_checkMAPHIEUMUONSACH.close();
				resultSet_checkMAPHIEUMUONSACH.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		else {
			JOptionPane.showMessageDialog(null, "Thông tin chưa đầy đủ!!! Vui lòng điền đầy đủ thông tin!!!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	// Find Button Event
	public void FindButtonEvent() {
		click_sign = false;
			
		 ArrayList<PhieuTraSachModel> phieutrasachList = new ArrayList<>();
		 if(!phieutrasach_find_textfield.getText().toString().trim().equals(""))
		 {
			int maphieutrasach = Integer.parseInt(phieutrasach_find_textfield.getText().toString());
			phieutrasach_find_textfield.setText("");
			boolean find_sign = false;
				
			try { 
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = (Connection) DriverManager.getConnection(url + dbName, "K74c1CEtnS","5HlVA1v9rX");
					
				PreparedStatement preparedStatement_FINDPHIEUTRASACH = (PreparedStatement)
						connection.prepareStatement("Select * from phieutrasach where MAPHIEUTRASACH='" + maphieutrasach +"';");
					ResultSet resultSet_findPHIEUTRASACH = preparedStatement_FINDPHIEUTRASACH.executeQuery();
					while (resultSet_findPHIEUTRASACH.next()) {
						int MAPHIEUTRASACH = resultSet_findPHIEUTRASACH.getInt("MAPHIEUTRASACH");
						int MAPHIEUMUONSACH = resultSet_findPHIEUTRASACH.getInt("MAPHIEUMUONSACH");
						String NGAYTRA = resultSet_findPHIEUTRASACH.getString("NGAYTRA");
						int SONGAYMUON = resultSet_findPHIEUTRASACH.getInt("SONGAYMUON");
						double TIENPHATKYNAY = resultSet_findPHIEUTRASACH.getDouble("TIENPHATKYNAY");
						double TIENNOKYNAY = resultSet_findPHIEUTRASACH.getDouble("TIENNOKYNAY");
						PhieuTraSachModel phieuTraSachModel = new PhieuTraSachModel(MAPHIEUTRASACH, MAPHIEUMUONSACH, NGAYTRA, SONGAYMUON, TIENPHATKYNAY, TIENNOKYNAY);
						phieutrasachList.add(phieuTraSachModel);
						find_sign = true;
					}
					preparedStatement_FINDPHIEUTRASACH.close();
					resultSet_findPHIEUTRASACH.close();
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			    if(find_sign == true) {
			    	phieutrasach_panel.remove(phieutrasach_scrollpane);;
					String column[]={"STT","MÃ PHIẾU TRẢ SÁCH", "MÃ PHIẾU MƯỢN SÁCH", "NGÀY TRẢ","SỐ NGÀY MƯỢN","TIỀN PHẠT KỲ NÀY", "TIỀN NỢ KỲ NÀY"};
					defaultTable = new DefaultTableModel(null, column);
					jt=new JTable(defaultTable);
					jt.setSize(300,300);
				    //jt.setBounds(30,40,00,200);  
				    jt.setPreferredScrollableViewportSize(new Dimension(500, 70));
				    jt.setFillsViewportHeight(true);
				    //jt.setBorder(BorderFactory.createLineBorder(Color.BLUE));
					
					Object[] row = new Object[7];
					for(int i = 0; i <phieutrasachList.size(); i++) {
					    row[0] = i+1;
					    row[1] = phieutrasachList.get(i).getMAPHIEUTRASACH();
					    row[2] = phieutrasachList.get(i).getMAPHIEUMUONSACH();
					    row[3] = phieutrasachList.get(i).getNGAYTRA();;
					    row[4] = phieutrasachList.get(i).getSONGAYMUON();
					    row[5] = phieutrasachList.get(i).getTIENPHATKYNAY();
					    row[6] = phieutrasachList.get(i).getTIENNOKYNAY();
					    defaultTable.addRow(row);
					}
				    phieutrasach_scrollpane = new JScrollPane(jt);
				    phieutrasach_scrollpane.setBounds(41, 134, 466, 339);
					phieutrasach_panel.add(phieutrasach_scrollpane);
					phieutrasach_panel.repaint();
					phieutrasach_panel.revalidate();
					JOptionPane.showMessageDialog(null, "Đã tìm thấy phiếu trả sách!!!");
			    }
			    else {
			    	JOptionPane.showMessageDialog(null, "Mã phiếu trả sách ở phần ID bạn vừa nhập không tồn tại trong danh sách!!!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
			    }
			 }
			 else {
				 JOptionPane.showMessageDialog(null, "Bạn chưa nhập thông tin mã phiếu trả sách cần tìm!!! Vui lòng điền mã phiếu trả sách ở phần ID!!!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
			 }
	}
	// Select Button Event
	public void ClickSelectButton(JTable jt) {
		click_sign = false;
		jt.addMouseListener(new MouseListener() {
				
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
					
			}
				
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
					
			}
				
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
					
			}
				
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
					
			}
				
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
					int index_row = jt.rowAtPoint(e.getPoint());
			           int numcols = defaultTable.getColumnCount();
			           for (int i = 0; i<numcols; i++) {
			           	if(i==1) {
			           		int maphieutrasach = (Integer)defaultTable.getValueAt(index_row, i);
			           		String sMaphieutrasachString = Integer.toString(maphieutrasach);
			           		click_GetMAPHIEUTRASACH = new Click_GetID(maphieutrasach);
			           		phieutrasach_ID_textfield.setText(sMaphieutrasachString);
			           	}
			           	if(i==2) {
			           		int maphieumuonsach = (Integer)defaultTable.getValueAt(index_row, i);
			           		click_GETMAPHIEUMUONSACH = new Click_GetID(maphieumuonsach);
			           		String sMaphieumuonsachString = Integer.toString(maphieumuonsach);
			           		phieutrasach_phieumuonsach_textfield.setText(sMaphieumuonsachString);
			            }
			           	if(i==3) {
			           		String ngaytra = (String)defaultTable.getValueAt(index_row, i);
			            	java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse(ngaytra);
			            	dateChooser.setDate(date);
			            }
			           	if(i==6) TIENNOKYNAY = (Double)defaultTable.getValueAt(index_row, i);
			         
			        }
			           click_sign = true;
				} catch (Exception e2) {
						
				} 
					
			}
		});
	}
	
	// Update Button Event
	public void UpdateButtonEvent() {
		if(click_sign == true) {
			boolean CheckMAPHIEUMUONSACH_Is_Exist = false;
			
			String maphieumuonsach = phieutrasach_phieumuonsach_textfield.getText().toString();
			String sNgaytra = "";
			double tienphatkynay = 0;
			double tiennokynay = 0;
			int songaymuontoida = 4;
			
			int previouse_maphieumuonsach = click_GETMAPHIEUMUONSACH.getID();
			int maphieutrasach_selected = click_GetMAPHIEUTRASACH.getID();
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				sNgaytra = dateFormat.format(dateChooser.getDate());
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			if (!maphieumuonsach.trim().equals("") & !sNgaytra.trim().equals(""))
			{
				int iMaphieumuonsach = Integer.parseInt(maphieumuonsach);
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection connection = (Connection) DriverManager.getConnection(url + dbName, "K74c1CEtnS","5HlVA1v9rX");
					PreparedStatement preparedStatement_checkMAPHIEUMUONSACH = (PreparedStatement)
							connection.prepareStatement("SELECT * FROM phieumuonsach WHERE MAPHIEUMUONSACH='" + iMaphieumuonsach + "';");
					ResultSet resultSet_checkMAPHIEUMUONSACH = preparedStatement_checkMAPHIEUMUONSACH.executeQuery();
					while(resultSet_checkMAPHIEUMUONSACH.next()) {
						CheckMAPHIEUMUONSACH_Is_Exist = true;
					}
					
					if(CheckMAPHIEUMUONSACH_Is_Exist == true) {
						Date ngaymuon;
						int songaymuon = 0;
						int previous_masach = -1;
						int now_masach = -1;
						int previous_madocgia = -1;
						int now_madocgia = -1;
						double previous_tongnodocgia = 0;
						double now_tongnodocgia = 0;
						Date ngaytra = new SimpleDateFormat("dd/MM/yyyy").parse(sNgaytra); 
						
						PreparedStatement preparedStatement_GETNGAYMUON = (PreparedStatement)
								connection.prepareStatement("SELECT NGAYMUON FROM phieumuonsach WHERE MAPHIEUMUONSACH='" + iMaphieumuonsach + "';");
						ResultSet resultSet_GETNGAYMUON = preparedStatement_GETNGAYMUON.executeQuery();
						while(resultSet_GETNGAYMUON.next()) {
							String sNgayMuon = resultSet_GETNGAYMUON.getString("NGAYMUON");
							ngaymuon = new SimpleDateFormat("dd/MM/yyyy").parse(sNgayMuon);
							long lSongaymuon = ngaytra.getTime() - ngaymuon.getTime();
							long lSONGAYMUON = TimeUnit.MILLISECONDS.toDays(lSongaymuon);
							String sSongaymuon = Long.toString(lSONGAYMUON);
							songaymuon = Integer.parseInt(sSongaymuon);
							if(songaymuon > songaymuontoida) {
								tienphatkynay = (songaymuon - songaymuontoida) * 1000;
								tiennokynay += tienphatkynay;
							}
							
						}
						
						PreparedStatement preparedStatement_UPDATEPHIEUTRASACH = (PreparedStatement)
								connection.prepareStatement("UPDATE phieutrasach SET MAPHIEUMUONSACH='" + iMaphieumuonsach 
										+ "', NGAYTRA='" + sNgaytra 
										+ "', SONGAYMUON='" + songaymuon
										+ "', TIENPHATKYNAY='" + tienphatkynay
										+ "', TIENNOKYNAY='" + tiennokynay
										+ "' WHERE MAPHIEUTRASACH='" + maphieutrasach_selected + "';");
						int resultSet_UPDATEPHIEUTRASACH = preparedStatement_UPDATEPHIEUTRASACH.executeUpdate();
						
						PreparedStatement preparedStatement_GETMASACH_PREVIOUS = (PreparedStatement)
								connection.prepareStatement("SELECT MASACH FROM phieumuonsach WHERE MAPHIEUMUONSACH='" + previouse_maphieumuonsach + "';");
						ResultSet resultSet_GETMASACH_PREVIOUS = preparedStatement_GETMASACH_PREVIOUS.executeQuery();
						while(resultSet_GETMASACH_PREVIOUS.next()) {
							previous_masach = resultSet_GETMASACH_PREVIOUS.getInt("MASACH");
						}
						
						PreparedStatement preparedStatement_UPDATETRANGTHAISACH_PREVIOUS = (PreparedStatement)
								connection.prepareStatement("UPDATE thongtinsach SET TRANGTHAI=? WHERE MASACH='" + previous_masach + "';");
						preparedStatement_UPDATETRANGTHAISACH_PREVIOUS.setInt(1, 1);
						int resultSet_UPDATETRANGTHAISACH_PREVIOUS = preparedStatement_UPDATETRANGTHAISACH_PREVIOUS.executeUpdate();
						
						PreparedStatement preparedStatement_GETMASACH_NOW = (PreparedStatement)
								connection.prepareStatement("SELECT MASACH FROM phieumuonsach WHERE MAPHIEUMUONSACH='" + iMaphieumuonsach + "';");
						ResultSet resultSet_GETMASACH_NOW = preparedStatement_GETMASACH_NOW.executeQuery();
						while(resultSet_GETMASACH_NOW.next()) {
							now_masach = resultSet_GETMASACH_NOW.getInt("MASACH");
						}
						
						PreparedStatement preparedStatement_UPDATETRANGTHAISACH_NOW = (PreparedStatement)
								connection.prepareStatement("UPDATE thongtinsach SET TRANGTHAI=? WHERE MASACH='" + now_masach + "';");
						preparedStatement_UPDATETRANGTHAISACH_NOW.setInt(1, 0);
						int resultSet_UPDATETRANGTHAISACH_NOW = preparedStatement_UPDATETRANGTHAISACH_NOW.executeUpdate();
						
						PreparedStatement preparedStatement_GETMADOCGIA_PREVIOUS = (PreparedStatement)
								connection.prepareStatement("SELECT MADOCGIA FROM phieumuonsach WHERE MAPHIEUMUONSACH='" + previouse_maphieumuonsach + "';");
						ResultSet resultSet_GETMADOCGIA_PREVIOUS = preparedStatement_GETMADOCGIA_PREVIOUS.executeQuery();
						while(resultSet_GETMADOCGIA_PREVIOUS.next()) {
							previous_madocgia = resultSet_GETMADOCGIA_PREVIOUS.getInt("MADOCGIA");
						}
						
						PreparedStatement preparedStatement_GETTONGNODOCGIA_PREVIOUS = (PreparedStatement)
								connection.prepareStatement("SELECT TONGNODOCGIA FROM thedocgia WHERE MADOCGIA ='" + previous_madocgia + "';");
						ResultSet resultSet_GETTONGNODOCGIA_PREVIOUS = preparedStatement_GETTONGNODOCGIA_PREVIOUS.executeQuery();
						while(resultSet_GETTONGNODOCGIA_PREVIOUS.next()) {
							previous_tongnodocgia = resultSet_GETTONGNODOCGIA_PREVIOUS.getDouble("TONGNODOCGIA");
						}
						
						PreparedStatement preparedStatement_UPDATETONGNODOCGIA_PREVIOUS = (PreparedStatement)
								connection.prepareStatement("UPDATE thedocgia SET TONGNODOCGIA=? WHERE MADOCGIA='" + previous_madocgia + "';");
						preparedStatement_UPDATETONGNODOCGIA_PREVIOUS.setDouble(1, previous_tongnodocgia - TIENNOKYNAY);
						int resultSet_UPDATETONGNODOCGIA_PREVIOUS = preparedStatement_UPDATETONGNODOCGIA_PREVIOUS.executeUpdate();
						
						PreparedStatement preparedStatement_GETMADOCGIA_NOW = (PreparedStatement)
								connection.prepareStatement("SELECT MADOCGIA FROM phieumuonsach WHERE MAPHIEUMUONSACH='" + iMaphieumuonsach + "';");
						ResultSet resultSet_GETMADOCGIA_NOW = preparedStatement_GETMADOCGIA_NOW.executeQuery();
						while(resultSet_GETMADOCGIA_NOW.next()) {
							now_madocgia = resultSet_GETMADOCGIA_NOW.getInt("MADOCGIA");
						}
						
						PreparedStatement preparedStatement_GETTONGNODOCGIA_NOW = (PreparedStatement)
								connection.prepareStatement("SELECT TONGNODOCGIA FROM thedocgia WHERE MADOCGIA ='" + now_madocgia + "';");
						ResultSet resultSet_GETTONGNODOCGIA_NOW = preparedStatement_GETTONGNODOCGIA_PREVIOUS.executeQuery();
						while(resultSet_GETTONGNODOCGIA_NOW.next()) {
							now_tongnodocgia = resultSet_GETTONGNODOCGIA_NOW.getDouble("TONGNODOCGIA");
						}
						
						PreparedStatement preparedStatement_UPDATETONGNODOCGIA_NOW = (PreparedStatement)
								connection.prepareStatement("UPDATE thedocgia SET TONGNODOCGIA=? WHERE MADOCGIA='" + now_madocgia + "';");
						preparedStatement_UPDATETONGNODOCGIA_NOW.setDouble(1, now_tongnodocgia + tiennokynay);
						int resultSet_UPDATETONGNODOCGIA_NOW = preparedStatement_UPDATETONGNODOCGIA_NOW.executeUpdate();
						
						if(resultSet_UPDATEPHIEUTRASACH > 0 && resultSet_UPDATETRANGTHAISACH_NOW > 0 && resultSet_UPDATETONGNODOCGIA_NOW > 0) {
							phieutrasach_panel.remove(phieutrasach_scrollpane);;
							String column[]={"STT","MÃ PHIẾU TRẢ SÁCH", "MÃ PHIẾU MƯỢN SÁCH", "NGÀY TRẢ","SỐ NGÀY MƯỢN","TIỀN PHẠT KỲ NÀY", "TIỀN NỢ KỲ NÀY"};
							defaultTable = new DefaultTableModel(null, column);
							jt=new JTable(defaultTable);
							jt.setSize(300,300);
						    //jt.setBounds(30,40,00,200);  
						    jt.setPreferredScrollableViewportSize(new Dimension(500, 70));
						    jt.setFillsViewportHeight(true);
						    //jt.setBorder(BorderFactory.createLineBorder(Color.BLUE));
						    
						    ArrayList<PhieuTraSachModel> phieutrasachList = new ArrayList<>();
						    
						    PreparedStatement preparedStatement_GETTABLE = (PreparedStatement)
									connection.prepareStatement("Select * from phieutrasach;");
						    
							ResultSet resultSet_getTABLE = preparedStatement_GETTABLE.executeQuery();
							while (resultSet_getTABLE.next()) {
								int MAPHIEUTRASACH = resultSet_getTABLE.getInt("MAPHIEUTRASACH");
								int MAPHIEUMUONSACH = resultSet_getTABLE.getInt("MAPHIEUMUONSACH");
								String NGAYTRA = resultSet_getTABLE.getString("NGAYTRA");
								int SONGAYMUON = resultSet_getTABLE.getInt("SONGAYMUON");
								double TIENPHATKYNAY = resultSet_getTABLE.getDouble("TIENPHATKYNAY");
								double TIENNOKYNAY = resultSet_getTABLE.getDouble("TIENNOKYNAY");
								PhieuTraSachModel phieuTraSachModel = new PhieuTraSachModel(MAPHIEUTRASACH, MAPHIEUMUONSACH, NGAYTRA, SONGAYMUON, TIENPHATKYNAY, TIENNOKYNAY);
								phieutrasachList.add(phieuTraSachModel);
							}
							
							Object[] row = new Object[7];
							for(int i = 0; i <phieutrasachList.size(); i++) {
							    row[0] = i+1;
							    row[1] = phieutrasachList.get(i).getMAPHIEUTRASACH();
							    row[2] = phieutrasachList.get(i).getMAPHIEUMUONSACH();
							    row[3] = phieutrasachList.get(i).getNGAYTRA();;
							    row[4] = phieutrasachList.get(i).getSONGAYMUON();
							    row[5] = phieutrasachList.get(i).getTIENPHATKYNAY();
							    row[6] = phieutrasachList.get(i).getTIENNOKYNAY();
							    defaultTable.addRow(row);
							}
						    phieutrasach_scrollpane = new JScrollPane(jt);
						    phieutrasach_scrollpane.setBounds(41, 134, 466, 339);
							phieutrasach_panel.add(phieutrasach_scrollpane);
							phieutrasach_panel.repaint();
							phieutrasach_panel.revalidate();
							
							JOptionPane.showMessageDialog(null, "Bạn đã cập nhật phiếu trả sách thành công!!!");
							preparedStatement_GETTABLE.close();
							resultSet_getTABLE.close();
							
						}
						else JOptionPane.showMessageDialog(null, "Phiếu trả sách bạn vừa cập nhật không thành công!!! Vui lòng thử lại!!!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
						preparedStatement_GETNGAYMUON.close();
						resultSet_GETNGAYMUON.close();
						preparedStatement_UPDATEPHIEUTRASACH.close();
						preparedStatement_GETMASACH_PREVIOUS.close();
						resultSet_GETMASACH_PREVIOUS.close();
						preparedStatement_UPDATETRANGTHAISACH_PREVIOUS.close();
						preparedStatement_GETMADOCGIA_PREVIOUS.close();
						resultSet_GETMADOCGIA_PREVIOUS.close();
						preparedStatement_GETTONGNODOCGIA_PREVIOUS.close();
						resultSet_GETTONGNODOCGIA_PREVIOUS.close();
						preparedStatement_UPDATETONGNODOCGIA_PREVIOUS.close();
						preparedStatement_GETMASACH_NOW.close();
						resultSet_GETMASACH_NOW.close();
						preparedStatement_UPDATETRANGTHAISACH_NOW.close();
						preparedStatement_GETMADOCGIA_NOW.close();
						resultSet_GETMADOCGIA_NOW.close();
						preparedStatement_GETTONGNODOCGIA_NOW.close();
						resultSet_GETTONGNODOCGIA_NOW.close();
						preparedStatement_UPDATETONGNODOCGIA_NOW.close();
						click_sign = false;
						
						
					}
					else JOptionPane.showMessageDialog(null, "Mã phiếu mượn sách vừa nhập không tồn tại trong hệ thống!!! Việc thêm phiếu trả sách không thành công!!!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
					preparedStatement_checkMAPHIEUMUONSACH.close();
					resultSet_checkMAPHIEUMUONSACH.close();
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			else {
				JOptionPane.showMessageDialog(null, "Thông tin chưa đầy đủ!!! Vui lòng điền đầy đủ thông tin!!!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
			}
	
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn phiếu trả sách cần cập nhật trong bảng!!! Vui lòng chọn!!!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	// Delete Button Event
	public void DeleteButtonEvent() {
		if(click_sign == true) {
			int maphieutrasach_selected = click_GetMAPHIEUTRASACH.getID();
			int maphieumuonsach_selected = click_GETMAPHIEUMUONSACH.getID();
			int masach = -1;
			int madocgia = -1;
			double tongnodocgia = 0;
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = (Connection) DriverManager.getConnection(url + dbName, "K74c1CEtnS","5HlVA1v9rX");
				
				PreparedStatement preparedStatement_DELETEPHIEUTRASACH = (PreparedStatement)
						connection.prepareStatement("Delete from phieutrasach where MAPHIEUTRASACH='" + maphieutrasach_selected + "';");
				int resultSet_DELETEPHIEUTRASACH = preparedStatement_DELETEPHIEUTRASACH.executeUpdate();
				
				PreparedStatement preparedStatement_GETMASACH = (PreparedStatement)
						connection.prepareStatement("SELECT MASACH FROM phieumuonsach WHERE MAPHIEUMUONSACH='" + maphieumuonsach_selected + "';");
				ResultSet resultSet_GETMASACH = preparedStatement_GETMASACH.executeQuery();
				while(resultSet_GETMASACH.next()) {
					masach = resultSet_GETMASACH.getInt("MASACH");
				}
				
				PreparedStatement preparedStatement_UPDATETRANGTHAISACH = (PreparedStatement)
						connection.prepareStatement("UPDATE thongtinsach SET TRANGTHAI=? WHERE MASACH='" + masach + "';");
				preparedStatement_UPDATETRANGTHAISACH.setInt(1, 1);
				int resultSet_UPDATETRANGTHAISACH = preparedStatement_UPDATETRANGTHAISACH.executeUpdate();
				
				PreparedStatement preparedStatement_GETMADOCGIA = (PreparedStatement)
						connection.prepareStatement("SELECT MADOCGIA FROM phieumuonsach WHERE MAPHIEUMUONSACH='" + maphieumuonsach_selected + "';");
				ResultSet resultSet_GETMADOCGIA = preparedStatement_GETMADOCGIA.executeQuery();
				while(resultSet_GETMADOCGIA.next()) {
					madocgia = resultSet_GETMADOCGIA.getInt("MADOCGIA");
				}
				
				PreparedStatement preparedStatement_GETTONGNODOCGIA = (PreparedStatement)
						connection.prepareStatement("SELECT TONGNODOCGIA FROM thedocgia WHERE MADOCGIA ='" + madocgia + "';");
				ResultSet resultSet_GETTONGNODOCGIA = preparedStatement_GETTONGNODOCGIA.executeQuery();
				while(resultSet_GETTONGNODOCGIA.next()) {
					tongnodocgia = resultSet_GETTONGNODOCGIA.getDouble("TONGNODOCGIA");
				}
				
				PreparedStatement preparedStatement_UPDATETONGNODOCGIA = (PreparedStatement)
						connection.prepareStatement("UPDATE thedocgia SET TONGNODOCGIA=? WHERE MADOCGIA='" + madocgia + "';");
				preparedStatement_UPDATETONGNODOCGIA.setDouble(1, tongnodocgia - TIENNOKYNAY);
				int resultSet_UPDATETONGNODOCGIA = preparedStatement_UPDATETONGNODOCGIA.executeUpdate();
				
				
				
				if(resultSet_DELETEPHIEUTRASACH > 0 && resultSet_UPDATETRANGTHAISACH > 0 && resultSet_UPDATETONGNODOCGIA > 0) {
					phieutrasach_panel.remove(phieutrasach_scrollpane);;
					String column[]={"STT","MÃ PHIẾU TRẢ SÁCH", "MÃ PHIẾU MƯỢN SÁCH", "NGÀY TRẢ","SỐ NGÀY MƯỢN","TIỀN PHẠT KỲ NÀY", "TIỀN NỢ KỲ NÀY"};
					defaultTable = new DefaultTableModel(null, column);
					jt=new JTable(defaultTable);
					jt.setSize(300,300);
				    //jt.setBounds(30,40,00,200);  
				    jt.setPreferredScrollableViewportSize(new Dimension(500, 70));
				    jt.setFillsViewportHeight(true);
				    //jt.setBorder(BorderFactory.createLineBorder(Color.BLUE));
				    
				    ArrayList<PhieuTraSachModel> phieutrasachList = new ArrayList<>();
				    
				    PreparedStatement preparedStatement_GETTABLE = (PreparedStatement)
							connection.prepareStatement("Select * from phieutrasach;");
				    
					ResultSet resultSet_getTABLE = preparedStatement_GETTABLE.executeQuery();
					while (resultSet_getTABLE.next()) {
						int MAPHIEUTRASACH = resultSet_getTABLE.getInt("MAPHIEUTRASACH");
						int MAPHIEUMUONSACH = resultSet_getTABLE.getInt("MAPHIEUMUONSACH");
						String NGAYTRA = resultSet_getTABLE.getString("NGAYTRA");
						int SONGAYMUON = resultSet_getTABLE.getInt("SONGAYMUON");
						double TIENPHATKYNAY = resultSet_getTABLE.getDouble("TIENPHATKYNAY");
						double TIENNOKYNAY = resultSet_getTABLE.getDouble("TIENNOKYNAY");
						PhieuTraSachModel phieuTraSachModel = new PhieuTraSachModel(MAPHIEUTRASACH, MAPHIEUMUONSACH, NGAYTRA, SONGAYMUON, TIENPHATKYNAY, TIENNOKYNAY);
						phieutrasachList.add(phieuTraSachModel);
					}
					
					Object[] row = new Object[7];
					for(int i = 0; i <phieutrasachList.size(); i++) {
					    row[0] = i+1;
					    row[1] = phieutrasachList.get(i).getMAPHIEUTRASACH();
					    row[2] = phieutrasachList.get(i).getMAPHIEUMUONSACH();
					    row[3] = phieutrasachList.get(i).getNGAYTRA();;
					    row[4] = phieutrasachList.get(i).getSONGAYMUON();
					    row[5] = phieutrasachList.get(i).getTIENPHATKYNAY();
					    row[6] = phieutrasachList.get(i).getTIENNOKYNAY();
					    defaultTable.addRow(row);
					}
				    phieutrasach_scrollpane = new JScrollPane(jt);
				    phieutrasach_scrollpane.setBounds(41, 134, 466, 339);
					phieutrasach_panel.add(phieutrasach_scrollpane);
					phieutrasach_panel.repaint();
					phieutrasach_panel.revalidate();
					
					JOptionPane.showMessageDialog(null, "Bạn đã xóa phiếu trả sách thành công!!!");
					preparedStatement_GETTABLE.close();
					resultSet_getTABLE.close();
				}
				else {
					JOptionPane.showMessageDialog(null, "Việc xóa phiếu mượn sách vừa chọn không thành công!!!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
				preparedStatement_DELETEPHIEUTRASACH.close();
				preparedStatement_GETMASACH.close();
				resultSet_GETMASACH.close();
				preparedStatement_UPDATETRANGTHAISACH.close();
				preparedStatement_GETMADOCGIA.close();
				resultSet_GETMADOCGIA.close();
				preparedStatement_GETTONGNODOCGIA.close();
				resultSet_GETTONGNODOCGIA.close();
				preparedStatement_UPDATETONGNODOCGIA.close();
				connection.close();
				click_sign = false;
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
		else {
			JOptionPane.showMessageDialog(null, "Chưa chọn phiếu trả sách cần xóa!!! Vui lòng chọn!!!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void ReloadButtonEvent() {
		click_sign = false;
		
		phieutrasach_panel.remove(phieutrasach_scrollpane);;
		String column[]={"STT","MÃ PHIẾU TRẢ SÁCH", "MÃ PHIẾU MƯỢN SÁCH", "NGÀY TRẢ","SỐ NGÀY MƯỢN","TIỀN PHẠT KỲ NÀY", "TIỀN NỢ KỲ NÀY"};
		defaultTable = new DefaultTableModel(null, column);
		jt=new JTable(defaultTable);
		jt.setSize(300,300);
	    //jt.setBounds(30,40,00,200);  
	    jt.setPreferredScrollableViewportSize(new Dimension(500, 70));
	    jt.setFillsViewportHeight(true);
	    //jt.setBorder(BorderFactory.createLineBorder(Color.BLUE));
	    
	    ArrayList<PhieuTraSachModel> phieutrasachList = new ArrayList<>();
	    
	    try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url + dbName, "K74c1CEtnS","5HlVA1v9rX");
			 PreparedStatement preparedStatement_GETTABLE = (PreparedStatement)
						connection.prepareStatement("Select * from phieutrasach;");
			    
				ResultSet resultSet_getTABLE = preparedStatement_GETTABLE.executeQuery();
				while (resultSet_getTABLE.next()) {
					int MAPHIEUTRASACH = resultSet_getTABLE.getInt("MAPHIEUTRASACH");
					int MAPHIEUMUONSACH = resultSet_getTABLE.getInt("MAPHIEUMUONSACH");
					String NGAYTRA = resultSet_getTABLE.getString("NGAYTRA");
					int SONGAYMUON = resultSet_getTABLE.getInt("SONGAYMUON");
					double TIENPHATKYNAY = resultSet_getTABLE.getDouble("TIENPHATKYNAY");
					double TIENNOKYNAY = resultSet_getTABLE.getDouble("TIENNOKYNAY");
					PhieuTraSachModel phieuTraSachModel = new PhieuTraSachModel(MAPHIEUTRASACH, MAPHIEUMUONSACH, NGAYTRA, SONGAYMUON, TIENPHATKYNAY, TIENNOKYNAY);
					phieutrasachList.add(phieuTraSachModel);
				}
				
				Object[] row = new Object[7];
				for(int i = 0; i <phieutrasachList.size(); i++) {
				    row[0] = i+1;
				    row[1] = phieutrasachList.get(i).getMAPHIEUTRASACH();
				    row[2] = phieutrasachList.get(i).getMAPHIEUMUONSACH();
				    row[3] = phieutrasachList.get(i).getNGAYTRA();;
				    row[4] = phieutrasachList.get(i).getSONGAYMUON();
				    row[5] = phieutrasachList.get(i).getTIENPHATKYNAY();
				    row[6] = phieutrasachList.get(i).getTIENNOKYNAY();
				    defaultTable.addRow(row);
				}
				
				preparedStatement_GETTABLE.close();
				resultSet_getTABLE.close();
				connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    jt.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
					
			}
				
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
					
			}
				
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
					
			}
				
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
					
			}
				
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
					int index_row = jt.rowAtPoint(e.getPoint());
			           int numcols = defaultTable.getColumnCount();
			           for (int i = 0; i<numcols; i++) {
			           	if(i==1) {
			           		int maphieutrasach = (Integer)defaultTable.getValueAt(index_row, i);
			           		String sMaphieutrasachString = Integer.toString(maphieutrasach);
			           		click_GetMAPHIEUTRASACH = new Click_GetID(maphieutrasach);
			           		phieutrasach_ID_textfield.setText(sMaphieutrasachString);
			           	}
			           	if(i==2) {
			           		int maphieumuonsach = (Integer)defaultTable.getValueAt(index_row, i);
			           		click_GETMAPHIEUMUONSACH = new Click_GetID(maphieumuonsach);
			           		String sMaphieumuonsachString = Integer.toString(maphieumuonsach);
			           		phieutrasach_phieumuonsach_textfield.setText(sMaphieumuonsachString);
			            }
			           	if(i==3) {
			           		String ngaytra = (String)defaultTable.getValueAt(index_row, i);
			            	java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse(ngaytra);
			            	dateChooser.setDate(date);
			            }
			           	if(i==6) TIENNOKYNAY = (Double)defaultTable.getValueAt(index_row, i);
			         
			        }
			           click_sign = true;
				} catch (Exception e2) {
						
				} 
					
			}
		});
	    phieutrasach_scrollpane = new JScrollPane(jt);
	    phieutrasach_scrollpane.setBounds(41, 134, 466, 339);
		phieutrasach_panel.add(phieutrasach_scrollpane);
		phieutrasach_panel.repaint();
		phieutrasach_panel.revalidate();
		
	}
	
}
