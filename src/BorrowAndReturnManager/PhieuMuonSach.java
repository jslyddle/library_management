package BorrowAndReturnManager;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
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
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PhieuMuonSach extends JFrame {
	
	private String url = "jdbc:mysql://remotemysql.com/";
	private String dbName = "K74c1CEtnS";
	
	private JTextField phieumuonsach_ID_textfield;
	private JTextField phieumuonsach_docgia_textfield;
	private JTextField phieumuonsach_sach_textfield;
	private JDateChooser dateChooser;
	JTextField phieumuonsach_find_textfield;
	JPanel phieumuonsach_panel;
	JScrollPane phieumuonsach_scrollpane;
	DefaultTableModel defaultTable;
	JTable jt;
	
	boolean click_sign = false;
	Click_GetID click_GetID;
	Click_GetMASACH click_GetMASACH;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PhieuMuonSach frame = new PhieuMuonSach();
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
	public PhieuMuonSach() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                this.setLocationRelativeTo(null);
                this.setTitle("Borrow Books");
                ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("assets/borrow_and_return.png"));
                setIconImage(icon.getImage());
		setBounds(100, 100, 926, 626);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		phieumuonsach_panel = new JPanel();
		phieumuonsach_panel.setBackground(new Color(255, 239, 213));
		phieumuonsach_panel.setBounds(199, 41, 670, 500);
		contentPane.add(phieumuonsach_panel);
		phieumuonsach_panel.setLayout(null);
		
		
		// ID
		JLabel phieumuonsach_ID_label = new JLabel("ID: ");
		phieumuonsach_ID_label.setFont(new Font("Tahoma", Font.BOLD, 18));
		phieumuonsach_ID_label.setBounds(25, 23, 44, 22);
		phieumuonsach_panel.add(phieumuonsach_ID_label);
		
		phieumuonsach_ID_textfield = new JTextField(5);
		phieumuonsach_ID_textfield.setFocusable(false);
		phieumuonsach_ID_textfield.setBounds(63, 23, 72, 24);
		phieumuonsach_ID_textfield.setEditable(false);
		phieumuonsach_panel.add(phieumuonsach_ID_textfield);
		phieumuonsach_ID_textfield.setColumns(10);
		
		
		// Doc Gia
		JLabel phieumuonsach_docgia_label = new JLabel("M\u00E3 \u0111\u1ED9c gi\u1EA3:");
		phieumuonsach_docgia_label.setFont(new Font("Tahoma", Font.BOLD, 18));
		phieumuonsach_docgia_label.setBounds(173, 23, 111, 22);
		phieumuonsach_panel.add(phieumuonsach_docgia_label);
		
		phieumuonsach_docgia_textfield = new JTextField(5);
		phieumuonsach_docgia_textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if(Character.isLetter(c)) {
					phieumuonsach_docgia_textfield.setEditable(false);
				}
				else phieumuonsach_docgia_textfield.setEditable(true);
				
			}
		});
		phieumuonsach_docgia_textfield.setDocument(new JTextFieldCharLimit(5));
		phieumuonsach_docgia_textfield.setColumns(10);
		phieumuonsach_docgia_textfield.setBounds(284, 21, 111, 24);
		phieumuonsach_panel.add(phieumuonsach_docgia_textfield);
		
		
		// Sach
		JLabel phieumuonsach_sach_label = new JLabel("M\u00E3 s\u00E1ch:");
		phieumuonsach_sach_label.setFont(new Font("Tahoma", Font.BOLD, 18));
		phieumuonsach_sach_label.setBounds(436, 23, 86, 22);
		phieumuonsach_panel.add(phieumuonsach_sach_label);
		
		phieumuonsach_sach_textfield = new JTextField(5);
		phieumuonsach_sach_textfield.setDocument(new JTextFieldCharLimit(5));
		phieumuonsach_sach_textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if(Character.isLetter(c)) {
					phieumuonsach_sach_textfield.setEditable(false);
				}
				else phieumuonsach_sach_textfield.setEditable(true);
			}
		});
		phieumuonsach_sach_textfield.setColumns(10);
		phieumuonsach_sach_textfield.setBounds(522, 23, 111, 24);
		phieumuonsach_panel.add(phieumuonsach_sach_textfield);
		
		
		// Ngay Muon
		JLabel phieumuonsach_ngaymuon_label = new JLabel("Ng\u00E0y m\u01B0\u1EE3n:");
		phieumuonsach_ngaymuon_label.setFont(new Font("Tahoma", Font.BOLD, 18));
		phieumuonsach_ngaymuon_label.setBounds(25, 70, 119, 22);
		phieumuonsach_panel.add(phieumuonsach_ngaymuon_label);
		
		dateChooser = new JDateChooser();
		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
		editor.setEditable(false);
		dateChooser.setBounds(154, 70, 130, 22);
		dateChooser.setDateFormatString("dd/MM/yyy");
		phieumuonsach_panel.add(dateChooser);
		
		// Tim kiem
		
		JLabel phieumuonsach_find_label = new JLabel("Search ID:");
		phieumuonsach_find_label.setFont(new Font("Tahoma", Font.ITALIC, 18));
		phieumuonsach_find_label.setBounds(333, 70, 93, 22);
		phieumuonsach_panel.add(phieumuonsach_find_label);
		
		phieumuonsach_find_textfield = new JTextField(5);
		phieumuonsach_find_textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if(Character.isLetter(c)) {
					phieumuonsach_find_textfield.setEditable(false);
				}
				else phieumuonsach_find_textfield.setEditable(true);
			}
		});
		phieumuonsach_find_textfield.setColumns(10);
		phieumuonsach_find_textfield.setDocument(new JTextFieldCharLimit(5));
		phieumuonsach_find_textfield.setBounds(424, 70, 104, 24);
		phieumuonsach_panel.add(phieumuonsach_find_textfield);
		
		
		//Find Button Event
		JLabel phieumuonsach_find_button = new JLabel("");
		phieumuonsach_find_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ImageIcon img_find_clickedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/find_clickedbutton.png"));
				Image img1 = img_find_clickedbutton.getImage();
				Image img2 = img1.getScaledInstance(93, 46,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieumuonsach_find_button.setIcon(imageIcon);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				ImageIcon img_find_releasedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/fiind_button.png"));
				Image img1 = img_find_releasedbutton.getImage();
				Image img2 = img1.getScaledInstance(93, 46,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieumuonsach_find_button.setIcon(imageIcon);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				FindButtonEvent();
			}
		});
		phieumuonsach_find_button.setBounds(556, 58, 93, 46);
		ImageIcon img_find_button = new ImageIcon(this.getClass().getClassLoader().getResource("assets/fiind_button.png"));
		Image img1 = img_find_button.getImage();
		Image img2 = img1.getScaledInstance(93, 46,Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(img2);
		phieumuonsach_find_button.setIcon(imageIcon);
		phieumuonsach_panel.add(phieumuonsach_find_button);
		
		
		//Add Button Event
		JLabel phieumuonsach_add_button = new JLabel("");
		phieumuonsach_add_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ImageIcon img_add_clickedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/add_clickedbutton.png"));
				Image img1 = img_add_clickedbutton.getImage();
				Image img2 = img1.getScaledInstance(130, 46,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieumuonsach_add_button.setIcon(imageIcon);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				ImageIcon img_add_releasedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/add_button.png"));
				Image img1 = img_add_releasedbutton.getImage();
				Image img2 = img1.getScaledInstance(130, 46,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieumuonsach_add_button.setIcon(imageIcon);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				AddButtonEvent();
			}
		});
		phieumuonsach_add_button.setBounds(519, 135, 130, 46);
		ImageIcon img_add_button = new ImageIcon(this.getClass().getClassLoader().getResource("assets/add_button.png"));
		img1 = img_add_button.getImage();
		img2 = img1.getScaledInstance(130, 46,Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(img2);
		phieumuonsach_add_button.setIcon(imageIcon);
		phieumuonsach_panel.add(phieumuonsach_add_button);
		
		//Select Button Event
		JLabel phieumuonsach_select_button = new JLabel("");
		phieumuonsach_select_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ImageIcon img_select_clickedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/select_clickedbutton.png"));
				Image img1 = img_select_clickedbutton.getImage();
				Image img2 = img1.getScaledInstance(130, 46,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieumuonsach_select_button.setIcon(imageIcon);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				ImageIcon img_select_releasedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/select_button.png"));
				Image img1 = img_select_releasedbutton.getImage();
				Image img2 = img1.getScaledInstance(130, 46,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieumuonsach_select_button.setIcon(imageIcon);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				ClickSelectButton(jt);
			}
		});
		phieumuonsach_select_button.setBounds(519, 204, 130, 46);
		ImageIcon img_select_button = new ImageIcon(this.getClass().getClassLoader().getResource("assets/select_button.png"));
		img1 = img_select_button.getImage();
		img2 = img1.getScaledInstance(130, 46,Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(img2);
		phieumuonsach_select_button.setIcon(imageIcon);
		phieumuonsach_panel.add(phieumuonsach_select_button);
		
		
		// Update Button Event
		JLabel phieumuonsach_update_button = new JLabel("");
		phieumuonsach_update_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ImageIcon img_update_clickedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/update_clickedbutton.png"));
				Image img1 = img_update_clickedbutton.getImage();
				Image img2 = img1.getScaledInstance(130, 46,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieumuonsach_update_button.setIcon(imageIcon);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				ImageIcon img_update_releasedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/update_button.png"));
				Image img1 = img_update_releasedbutton.getImage();
				Image img2 = img1.getScaledInstance(130, 46,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieumuonsach_update_button.setIcon(imageIcon);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				UpdateButtonEvent();
			}
		});
		phieumuonsach_update_button.setBounds(522, 273, 130, 46);
		ImageIcon img_update_button = new ImageIcon(this.getClass().getClassLoader().getResource("assets/update_button.png"));
		img1 = img_update_button.getImage();
		img2 = img1.getScaledInstance(130, 46,Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(img2);
		phieumuonsach_update_button.setIcon(imageIcon);
		phieumuonsach_panel.add(phieumuonsach_update_button);
		
		
		// Delete Button Event
		JLabel phieumuonsach_delete_button = new JLabel("");
		phieumuonsach_delete_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ImageIcon img_delete_clickedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/delete_clickedbutton.png"));
				Image img1 = img_delete_clickedbutton.getImage();
				Image img2 = img1.getScaledInstance(130, 46,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieumuonsach_delete_button.setIcon(imageIcon);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				ImageIcon img_delete_releasedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/delete_button.png"));
				Image img1 = img_delete_releasedbutton.getImage();
				Image img2 = img1.getScaledInstance(130, 46,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieumuonsach_delete_button.setIcon(imageIcon);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				DeleteButtonEvent();
			}
		});
		phieumuonsach_delete_button.setBounds(522, 340, 130, 46);
		ImageIcon img_delete_button = new ImageIcon(this.getClass().getClassLoader().getResource("assets/delete_button.png"));
		img1 = img_delete_button.getImage();
		img2 = img1.getScaledInstance(130, 46,Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(img2);
		phieumuonsach_delete_button.setIcon(imageIcon);
		phieumuonsach_panel.add(phieumuonsach_delete_button);
		
		
		// Reload Button Event
		JLabel phieumuonsach_reload_button = new JLabel("");
		phieumuonsach_reload_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ImageIcon img_reload_clickedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/reload_clickedbutton.png"));
				Image img1 = img_reload_clickedbutton.getImage();
				Image img2 = img1.getScaledInstance(130, 46,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieumuonsach_reload_button.setIcon(imageIcon);
				
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				ImageIcon img_reload_releasedbutton = new ImageIcon(this.getClass().getClassLoader().getResource("assets/reload_button.png"));
				Image img1 = img_reload_releasedbutton.getImage();
				Image img2 = img1.getScaledInstance(130, 46,Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(img2);
				phieumuonsach_reload_button.setIcon(imageIcon);
				
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				ReloadButtonEvent();
			}
		});
		phieumuonsach_reload_button.setBounds(522, 409, 130, 46);
		ImageIcon img_reload_button = new ImageIcon(this.getClass().getClassLoader().getResource("assets/reload_button.png"));
		img1 = img_reload_button.getImage();
		img2 = img1.getScaledInstance(130, 46,Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(img2);
		phieumuonsach_reload_button.setIcon(imageIcon);
		phieumuonsach_panel.add(phieumuonsach_reload_button);
		
		
		// Table
		String column[]={"STT","MÃ PHIẾU MƯỢN SÁCH","MÃ ĐỘC GIẢ","MÃ SÁCH","NGÀY MƯỢN"};
		defaultTable = new DefaultTableModel(null, column);
		jt=new JTable(defaultTable);
		jt.setSize(300,300);
	    //jt.setBounds(30,40,00,200);  
	    jt.setPreferredScrollableViewportSize(new Dimension(500, 70));
	    jt.setFillsViewportHeight(true);
	    //jt.setBorder(BorderFactory.createLineBorder(Color.BLUE));
	    
	    ArrayList<PhieuMuonSachModel> phieumuonsachList = new ArrayList<>();
	    
	    try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url + dbName, "K74c1CEtnS","5HlVA1v9rX");
			PreparedStatement preparedStatement_GETTABLE = (PreparedStatement)
					connection.prepareStatement("Select * from phieumuonsach;");
			    
			ResultSet resultSet_getTABLE = preparedStatement_GETTABLE.executeQuery();
			while (resultSet_getTABLE.next()) {
				int MAPHIEUMUONSACH = resultSet_getTABLE.getInt("MAPHIEUMUONSACH");
				int MADOCGIA = resultSet_getTABLE.getInt("MADOCGIA");
				int MASACH = resultSet_getTABLE.getInt("MASACH");
				String NGAYMUON = resultSet_getTABLE.getString("NGAYMUON");
				PhieuMuonSachModel phieuMuonSachModel = new PhieuMuonSachModel(MAPHIEUMUONSACH, MADOCGIA, MASACH, NGAYMUON);
				phieumuonsachList.add(phieuMuonSachModel);
			}
				
			Object[] row = new Object[5];
			for(int i = 0; i <phieumuonsachList.size(); i++) {
				row[0] = i+1;
				row[1] = phieumuonsachList.get(i).getMAPHIEUMUONSACH();
				row[2] = phieumuonsachList.get(i).getMADOCGIA();
				row[3] = phieumuonsachList.get(i).getMASACH();;
				row[4] = phieumuonsachList.get(i).getNGAYMUON();;
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
		            		int maphieumuonsach = (Integer)defaultTable.getValueAt(index_row, i);
		            		String sMaphieumuonsachString = Integer.toString(maphieumuonsach);
		            		click_GetID = new Click_GetID(maphieumuonsach);
		            		phieumuonsach_ID_textfield.setText(sMaphieumuonsachString);
		            	}
		            	if(i==2) {
		            		int madocgia = (Integer)defaultTable.getValueAt(index_row, i);
		            		String sMadocgia = Integer.toString(madocgia);
		            		phieumuonsach_docgia_textfield.setText(sMadocgia);
		            	}
		            	if(i==3) {
		            		int masach = (Integer)defaultTable.getValueAt(index_row, i);
		            		String sMasach = Integer.toString(masach);
		            		click_GetMASACH = new Click_GetMASACH(masach);
		            		phieumuonsach_sach_textfield.setText(sMasach);
		            	}
		            	if(i==4) {
		            		String ngaymuon = (String)defaultTable.getValueAt(index_row, i);
		            		java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse(ngaymuon);
		            		dateChooser.setDate(date);
		            		
		            	}
		            }
		            click_sign = true;
				} catch (Exception e2) {
					
				} 
				
			}
		});
	   
	
	    phieumuonsach_scrollpane = new JScrollPane(jt);
		phieumuonsach_scrollpane.setBounds(45, 117, 449, 350);
		phieumuonsach_panel.add(phieumuonsach_scrollpane);
	}
	
	// Add Button Event
	public void AddButtonEvent() {
		
		click_sign = false;
		
		boolean CheckMADOCGIA_Is_Exist = false;
		boolean CheckMASACH_Is_Exist = false;
		boolean CheckNGAYHETHAN_Is_Available = false;
		boolean CheckTRANGTHAI_Is_Available = false;
		
		
		String madocgia = phieumuonsach_docgia_textfield.getText().toString();
		String masach = phieumuonsach_sach_textfield.getText().toString();
		String ngaymuon = "";
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			ngaymuon = dateFormat.format(dateChooser.getDate());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (!madocgia.trim().equals("") & !masach.trim().equals("") && !ngaymuon.trim().equals(""))
		{
			int iMadocgia = Integer.parseInt(madocgia);
			int iMasach = Integer.parseInt(masach);
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = (Connection) DriverManager.getConnection(url + dbName, "K74c1CEtnS","5HlVA1v9rX");
				PreparedStatement preparedStatement_checkMADOCGIA = (PreparedStatement)
						connection.prepareStatement("SELECT * FROM thedocgia WHERE MADOCGIA='" + iMadocgia + "';");
				ResultSet resultSet_checkMADOCGIA = preparedStatement_checkMADOCGIA.executeQuery();
				
				while(resultSet_checkMADOCGIA.next()) {
					CheckMADOCGIA_Is_Exist = true;
				}
				
				if(CheckMADOCGIA_Is_Exist == true) {
					PreparedStatement preparedStatement_checkNGAYHETHAN = (PreparedStatement)
							connection.prepareStatement("SELECT NGAYHETHAN FROM thedocgia WHERE MADOCGIA='" + iMadocgia + "';");
					
					ResultSet resulSet_checkNGAYHETHAN = preparedStatement_checkNGAYHETHAN.executeQuery();
					
					while(resulSet_checkNGAYHETHAN.next())
					{
						String ngayhethan = resulSet_checkNGAYHETHAN.getString("NGAYHETHAN");
						java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(ngayhethan);
						java.util.Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(ngaymuon);
						CheckNGAYHETHAN_Is_Available = CheckThoiHanTheDocGia(date1, date2);	
					}
					
					if (CheckNGAYHETHAN_Is_Available == true) {
						PreparedStatement preparedStatement_checkMASACH = (PreparedStatement)
								connection.prepareStatement("SELECT * FROM thongtinsach WHERE MASACH='" + iMasach + "';");
						ResultSet resultSet_checkMASACH = preparedStatement_checkMASACH.executeQuery();
						
						while(resultSet_checkMASACH.next()) {
							CheckMASACH_Is_Exist = true;
						}
						
						if(CheckMASACH_Is_Exist == true) {
							PreparedStatement preparedStatement_checkTRANGTHAI = (PreparedStatement)
									connection.prepareStatement("SELECT TRANGTHAI FROM thongtinsach WHERE MASACH='" + iMasach + "';");
							
							ResultSet resultSet_checkTRANGTHAI = preparedStatement_checkTRANGTHAI.executeQuery();
							
							while(resultSet_checkTRANGTHAI.next()) {
								int trangthai = resultSet_checkTRANGTHAI.getInt("TRANGTHAI");
								if(trangthai == 0) CheckTRANGTHAI_Is_Available = true;
							}
							
							if(CheckTRANGTHAI_Is_Available == true) {
								phieumuonsach_docgia_textfield.setText("");
								phieumuonsach_sach_textfield.setText("");
								dateChooser.setDate(null);
								PreparedStatement preparedStatement_ADDPHIEUMUONSASCH = (PreparedStatement)
										connection.prepareStatement("INSERT INTO phieumuonsach(MADOCGIA,MASACH,NGAYMUON) values(?,?,?);");
								preparedStatement_ADDPHIEUMUONSASCH.setInt(1, iMadocgia);
								preparedStatement_ADDPHIEUMUONSASCH.setInt(2, iMasach);
								preparedStatement_ADDPHIEUMUONSASCH.setString(3, ngaymuon);
								
								PreparedStatement preparedStatement_UPDATETRANGTHAISACH = (PreparedStatement)
										connection.prepareStatement("UPDATE thongtinsach SET TRANGTHAI=? WHERE MASACH='" + iMasach + "';");
								preparedStatement_UPDATETRANGTHAISACH.setInt(1, 1);
								
								int resultSet_ADDPHIEUMUONSACH = preparedStatement_ADDPHIEUMUONSASCH.executeUpdate();
								int resultSet_UPDATETRANGTHAISACH = preparedStatement_UPDATETRANGTHAISACH.executeUpdate();
								
								if(resultSet_ADDPHIEUMUONSACH > 0 && resultSet_UPDATETRANGTHAISACH > 0) {
									phieumuonsach_panel.remove(phieumuonsach_scrollpane);
									String column[]={"STT","MÃ PHIẾU MƯỢN SÁCH","MÃ ĐỘC GIẢ","MÃ SÁCH","NGÀY MƯỢN"};
									defaultTable = new DefaultTableModel(null, column);
									jt=new JTable(defaultTable);
									jt.setSize(300,300);
								    //jt.setBounds(30,40,00,200);  
								    jt.setPreferredScrollableViewportSize(new Dimension(500, 70));
								    jt.setFillsViewportHeight(true);
								    //jt.setBorder(BorderFactory.createLineBorder(Color.BLUE));
								    
								    ArrayList<PhieuMuonSachModel> phieumuonsachList = new ArrayList<>();
								    
								    PreparedStatement preparedStatement_GETTABLE = (PreparedStatement)
											connection.prepareStatement("Select * from phieumuonsach;");
								    
									ResultSet resultSet_getTABLE = preparedStatement_GETTABLE.executeQuery();
									while (resultSet_getTABLE.next()) {
										int MAPHIEUMUONSACH = resultSet_getTABLE.getInt("MAPHIEUMUONSACH");
										int MADOCGIA = resultSet_getTABLE.getInt("MADOCGIA");
										int MASACH = resultSet_getTABLE.getInt("MASACH");
										String NGAYMUON = resultSet_getTABLE.getString("NGAYMUON");
										PhieuMuonSachModel phieuMuonSachModel = new PhieuMuonSachModel(MAPHIEUMUONSACH, MADOCGIA, MASACH, NGAYMUON);
										phieumuonsachList.add(phieuMuonSachModel);
									}
									
									Object[] row = new Object[5];
									for(int i = 0; i <phieumuonsachList.size(); i++) {
									    row[0] = i+1;
									    row[1] = phieumuonsachList.get(i).getMAPHIEUMUONSACH();
									    row[2] = phieumuonsachList.get(i).getMADOCGIA();
									    row[3] = phieumuonsachList.get(i).getMASACH();;
									    row[4] = phieumuonsachList.get(i).getNGAYMUON();;
									    defaultTable.addRow(row);
									}
								    phieumuonsach_scrollpane = new JScrollPane(jt);
								    phieumuonsach_scrollpane.setBounds(45, 117, 449, 350);
									phieumuonsach_panel.add(phieumuonsach_scrollpane);
									phieumuonsach_panel.repaint();
									phieumuonsach_panel.revalidate();
									
									JOptionPane.showMessageDialog(null, "Bạn đã thêm phiếu mượn sách thành công!!!");
									preparedStatement_GETTABLE.close();
									resultSet_getTABLE.close();
								}
								else JOptionPane.showMessageDialog(null, "Phiếu mượn sách bạn vừa thêm không thành công!!! Vui lòng thử lại!!!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
								preparedStatement_ADDPHIEUMUONSASCH.close();
								preparedStatement_UPDATETRANGTHAISACH.close();
								
							}
							else JOptionPane.showMessageDialog(null, "Sách này hiện đã có người mượn!!!", "Việc thêm phiếu mượn sách không thành công!!!", JOptionPane.ERROR_MESSAGE);
							preparedStatement_checkTRANGTHAI.close();
							resultSet_checkTRANGTHAI.close();
						}
						
						else JOptionPane.showMessageDialog(null, "Mã sách vừa nhập không tồn tại trong hệ thống!!!", "Việc thêm phiếu mượn sách không thành công!!!", JOptionPane.ERROR_MESSAGE);
						preparedStatement_checkMASACH.close();
						resultSet_checkMASACH.close();
					}
					else JOptionPane.showMessageDialog(null, "Thẻ độc giả này đã hết hạn sử dụng!!!", "Việc thêm phiếu mượn sách không thành công!!!", JOptionPane.ERROR_MESSAGE);
					preparedStatement_checkNGAYHETHAN.close();
					resulSet_checkNGAYHETHAN.close();
					
				}
				else JOptionPane.showMessageDialog(null, "Mã độc giả vừa nhập không tồn tại trong hệ thống!!!", "Việc thêm phiếu mượn sách không thành công!!", JOptionPane.ERROR_MESSAGE);
				preparedStatement_checkMADOCGIA.close();
				resultSet_checkMADOCGIA.close();
				connection.close();
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Thông tin chưa đầy đủ!!! Vui lòng điền đầy đủ thông tin!!!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public boolean CheckThoiHanTheDocGia(java.util.Date date1, java.util.Date date2)
	{
		if(date1.compareTo(date2) > 0) {
			 return true;
		}
		else return false;
	}
	
	// Find Button Event
	public void FindButtonEvent() {
		click_sign = false;
		
		ArrayList<PhieuMuonSachModel> phieumuonsachList = new ArrayList<>();
		 if(!phieumuonsach_find_textfield.getText().toString().trim().equals(""))
		 {
		    int maphieumuonsach = Integer.parseInt(phieumuonsach_find_textfield.getText().toString());
			boolean find_sign = false;
			phieumuonsach_find_textfield.setText("");
			
		    try { 
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = (Connection) DriverManager.getConnection(url + dbName, "K74c1CEtnS","5HlVA1v9rX");
				
				PreparedStatement preparedStatement_FINDPHIEUMUONSACH = (PreparedStatement)
						connection.prepareStatement("Select * from phieumuonsach where MAPHIEUMUONSACH='" + maphieumuonsach +"';");
				ResultSet resultSet_findPHIEUMUONSACH = preparedStatement_FINDPHIEUMUONSACH.executeQuery();
				while (resultSet_findPHIEUMUONSACH.next()) {
					int MAPHIEUMUONSACH = resultSet_findPHIEUMUONSACH.getInt("MAPHIEUMUONSACH");
					int MADOCGIA = resultSet_findPHIEUMUONSACH.getInt("MADOCGIA");
					int MASACH = resultSet_findPHIEUMUONSACH.getInt("MASACH");
					String NGAYMUON = resultSet_findPHIEUMUONSACH.getString("NGAYMUON");
					PhieuMuonSachModel phieuMuonSachModel = new PhieuMuonSachModel(MAPHIEUMUONSACH, MADOCGIA, MASACH, NGAYMUON);
					phieumuonsachList.add(phieuMuonSachModel);
					find_sign = true;
				}
				preparedStatement_FINDPHIEUMUONSACH.close();
				resultSet_findPHIEUMUONSACH.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		    if(find_sign == true) {
		    	phieumuonsach_panel.remove(phieumuonsach_scrollpane);
				String column[]={"STT","MÃ PHIẾU MƯỢN SÁCH","MÃ ĐỘC GIẢ","MÃ SÁCH","NGÀY MƯỢN"};
				defaultTable = new DefaultTableModel(null, column);
				jt=new JTable(defaultTable);
				jt.setSize(300,300);
			    //jt.setBounds(30,40,00,200);  
			    jt.setPreferredScrollableViewportSize(new Dimension(500, 70));
			    jt.setFillsViewportHeight(true);
			    //jt.setBorder(BorderFactory.createLineBorder(Color.BLUE));
			    
		    
			    Object[] row = new Object[5];
				for(int i = 0; i <phieumuonsachList.size(); i++) {
				    row[0] = i+1;
				    row[1] = phieumuonsachList.get(i).getMAPHIEUMUONSACH();
				    row[2] = phieumuonsachList.get(i).getMADOCGIA();
				    row[3] = phieumuonsachList.get(i).getMASACH();;
				    row[4] = phieumuonsachList.get(i).getNGAYMUON();;
				    defaultTable.addRow(row);
				}
			    phieumuonsach_scrollpane = new JScrollPane(jt);
			    phieumuonsach_scrollpane.setBounds(45, 117, 449, 350);
				phieumuonsach_panel.add(phieumuonsach_scrollpane);
				phieumuonsach_panel.repaint();
				phieumuonsach_panel.revalidate();
				JOptionPane.showMessageDialog(null, "Đã tìm thấy phiếu mượn sách!!!");
		    }
		    else {
		    	JOptionPane.showMessageDialog(null, "Mã phiếu mượn sách ở phần ID bạn vừa nhập không tồn tại trong danh sách!!!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
		    }
		 }
		 else {
			 JOptionPane.showMessageDialog(null, "Bạn chưa nhập thông tin mã phiếu mượn sách cần tìm!!! Vui lòng điền mã phiếu mượn sách ở phần ID!!!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
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
		            		int maphieumuonsach = (Integer)defaultTable.getValueAt(index_row, i);
		            		String sMaphieumuonsachString = Integer.toString(maphieumuonsach);
		            		click_GetID = new Click_GetID(maphieumuonsach);
		            		phieumuonsach_ID_textfield.setText(sMaphieumuonsachString);
		            	}
		            	if(i==2) {
		            		int madocgia = (Integer)defaultTable.getValueAt(index_row, i);
		            		String sMadocgia = Integer.toString(madocgia);
		            		phieumuonsach_docgia_textfield.setText(sMadocgia);
		            	}
		            	if(i==3) {
		            		int masach = (Integer)defaultTable.getValueAt(index_row, i);
		            		String sMasach = Integer.toString(masach);
		            		click_GetMASACH = new Click_GetMASACH(masach);
		            		phieumuonsach_sach_textfield.setText(sMasach);
		            	}
		            	if(i==4) {
		            		String ngaymuon = (String)defaultTable.getValueAt(index_row, i);
		            		java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse(ngaymuon);
		            		dateChooser.setDate(date);
		            		
		            	}
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
			boolean CheckMADOCGIA_Is_Exist = false;
			boolean CheckMASACH_Is_Exist = false;
			boolean CheckNGAYHETHAN_Is_Available = false;
			boolean CheckTRANGTHAI_Is_Available = false;
			
			String madocgia = phieumuonsach_docgia_textfield.getText().toString();
			String masach = phieumuonsach_sach_textfield.getText().toString();
			String ngaymuon = "";
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				ngaymuon = dateFormat.format(dateChooser.getDate());
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			int maphieumuonsach_selected = click_GetID.getID();
			int previous_masach = click_GetMASACH.getMASACH();
			
			if (!madocgia.trim().equals("") & !masach.trim().equals("") && !ngaymuon.trim().equals(""))
			{
				int iMadocgia = Integer.parseInt(madocgia);
				int iMasach = Integer.parseInt(masach);
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection connection = (Connection) DriverManager.getConnection(url + dbName, "K74c1CEtnS","5HlVA1v9rX");
					PreparedStatement preparedStatement_checkMADOCGIA = (PreparedStatement)
							connection.prepareStatement("SELECT * FROM thedocgia WHERE MADOCGIA='" + iMadocgia + "';");
					ResultSet resultSet_checkMADOCGIA = preparedStatement_checkMADOCGIA.executeQuery();
					
					while(resultSet_checkMADOCGIA.next()) {
						CheckMADOCGIA_Is_Exist = true;
					}
					
					if(CheckMADOCGIA_Is_Exist == true) {
						PreparedStatement preparedStatement_checkNGAYHETHAN = (PreparedStatement)
								connection.prepareStatement("SELECT NGAYHETHAN FROM thedocgia WHERE MADOCGIA='" + iMadocgia + "';");
						
						ResultSet resulSet_checkNGAYHETHAN = preparedStatement_checkNGAYHETHAN.executeQuery();
						
						while(resulSet_checkNGAYHETHAN.next())
						{
							String ngayhethan = resulSet_checkNGAYHETHAN.getString("NGAYHETHAN");
							java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(ngayhethan);
							java.util.Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(ngaymuon);
							CheckNGAYHETHAN_Is_Available = CheckThoiHanTheDocGia(date1, date2);	
						}
						
						if (CheckNGAYHETHAN_Is_Available == true) {
							PreparedStatement preparedStatement_checkMASACH = (PreparedStatement)
									connection.prepareStatement("SELECT * FROM thongtinsach WHERE MASACH='" + iMasach + "';");
							ResultSet resultSet_checkMASACH = preparedStatement_checkMASACH.executeQuery();
							
							while(resultSet_checkMASACH.next()) {
								CheckMASACH_Is_Exist = true;
							}
							
							if(CheckMASACH_Is_Exist == true) {
								PreparedStatement preparedStatement_checkTRANGTHAI = (PreparedStatement)
										connection.prepareStatement("SELECT TRANGTHAI FROM thongtinsach WHERE MASACH='" + iMasach + "';");
								
								ResultSet resultSet_checkTRANGTHAI = preparedStatement_checkTRANGTHAI.executeQuery();
								
								while(resultSet_checkTRANGTHAI.next()) {
									int trangthai = resultSet_checkTRANGTHAI.getInt("TRANGTHAI");
									if(trangthai == 0) CheckTRANGTHAI_Is_Available = true;
								}
								
								if(CheckTRANGTHAI_Is_Available == true) {
									
									phieumuonsach_docgia_textfield.setText("");
									phieumuonsach_sach_textfield.setText("");
									dateChooser.setDate(null);
									
									PreparedStatement preparedStatement_UPDATEPHIEUMUONSACH = (PreparedStatement)
											connection.prepareStatement("UPDATE phieumuonsach SET MADOCGIA='" + iMadocgia 
													+ "', MASACH='" + iMasach 
													+ "', NGAYMUON='" + ngaymuon 
													+ "' WHERE MAPHIEUMUONSACH='" + maphieumuonsach_selected + "';");
									
									PreparedStatement preparedStatement_UPDATETRANGTHAISACH_NOW = (PreparedStatement)
											connection.prepareStatement("UPDATE thongtinsach SET TRANGTHAI=? WHERE MASACH='" + iMasach + "';");
									preparedStatement_UPDATETRANGTHAISACH_NOW.setInt(1, 1);
									
									PreparedStatement preparedStatement_UPDATETRANGTHAISACH_PREVIOUS = (PreparedStatement)
											connection.prepareStatement("UPDATE thongtinsach SET TRANGTHAI=? WHERE MASACH='" + previous_masach + "';");
									preparedStatement_UPDATETRANGTHAISACH_PREVIOUS.setInt(1, 0);
									int resulSet_UPDATETRANGTHAISACH_PREVIOUS;
									
									if(previous_masach != iMasach) {
										resulSet_UPDATETRANGTHAISACH_PREVIOUS = preparedStatement_UPDATETRANGTHAISACH_PREVIOUS.executeUpdate();
									}
									
									int resultSet_UPDATEPHIEUMUONSACH = preparedStatement_UPDATEPHIEUMUONSACH.executeUpdate();
									int resultSet_UPDATETRANGTHAISACH_NOW = preparedStatement_UPDATETRANGTHAISACH_NOW.executeUpdate();
									
									if(resultSet_UPDATEPHIEUMUONSACH > 0 && resultSet_UPDATETRANGTHAISACH_NOW > 0) {
										phieumuonsach_panel.remove(phieumuonsach_scrollpane);
										String column[]={"STT","MÃ PHIẾU MƯỢN SÁCH","MÃ ĐỘC GIẢ","MÃ SÁCH","NGÀY MƯỢN"};
										defaultTable = new DefaultTableModel(null, column);
										jt=new JTable(defaultTable);
										jt.setSize(300,300);
									    //jt.setBounds(30,40,00,200);  
									    jt.setPreferredScrollableViewportSize(new Dimension(500, 70));
									    jt.setFillsViewportHeight(true);
									    //jt.setBorder(BorderFactory.createLineBorder(Color.BLUE));
									    
									    ArrayList<PhieuMuonSachModel> phieumuonsachList = new ArrayList<>();
									    
									    PreparedStatement preparedStatement_GETTABLE = (PreparedStatement)
												connection.prepareStatement("Select * from phieumuonsach;");
									    
										ResultSet resultSet_getTABLE = preparedStatement_GETTABLE.executeQuery();
										while (resultSet_getTABLE.next()) {
											int MAPHIEUMUONSACH = resultSet_getTABLE.getInt("MAPHIEUMUONSACH");
											int MADOCGIA = resultSet_getTABLE.getInt("MADOCGIA");
											int MASACH = resultSet_getTABLE.getInt("MASACH");
											String NGAYMUON = resultSet_getTABLE.getString("NGAYMUON");
											PhieuMuonSachModel phieuMuonSachModel = new PhieuMuonSachModel(MAPHIEUMUONSACH, MADOCGIA, MASACH, NGAYMUON);
											phieumuonsachList.add(phieuMuonSachModel);
										}
										
										Object[] row = new Object[5];
										for(int i = 0; i <phieumuonsachList.size(); i++) {
										    row[0] = i+1;
										    row[1] = phieumuonsachList.get(i).getMAPHIEUMUONSACH();
										    row[2] = phieumuonsachList.get(i).getMADOCGIA();
										    row[3] = phieumuonsachList.get(i).getMASACH();;
										    row[4] = phieumuonsachList.get(i).getNGAYMUON();;
										    defaultTable.addRow(row);
										}
									    phieumuonsach_scrollpane = new JScrollPane(jt);
									    phieumuonsach_scrollpane.setBounds(45, 117, 449, 350);
										phieumuonsach_panel.add(phieumuonsach_scrollpane);
										phieumuonsach_panel.repaint();
										phieumuonsach_panel.revalidate();
										
										JOptionPane.showMessageDialog(null, "Bạn đã cập nhật phiếu mượn sách thành công!!!");
										preparedStatement_GETTABLE.close();
										resultSet_getTABLE.close();
									}
									else JOptionPane.showMessageDialog(null, "Phiếu mượn sách bạn vừa cập nhật không thành công!!! Vui lòng thử lại!!!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
									preparedStatement_UPDATEPHIEUMUONSACH.close();
									preparedStatement_UPDATETRANGTHAISACH_PREVIOUS.close();
									preparedStatement_UPDATETRANGTHAISACH_NOW.close();

									click_sign = false;
								}
								else JOptionPane.showMessageDialog(null, "Sách này hiện đã có người mượn!!!", "Việc cập nhật phiếu mượn sách không thành công!!!", JOptionPane.ERROR_MESSAGE);
								preparedStatement_checkTRANGTHAI.close();
								resultSet_checkTRANGTHAI.close();
							}
							
							else JOptionPane.showMessageDialog(null, "Mã sách vừa nhập không tồn tại trong hệ thống!!!", "Việc cập nhật phiếu mượn sách không thành công!!!", JOptionPane.ERROR_MESSAGE);
							preparedStatement_checkMASACH.close();
							resultSet_checkMASACH.close();
						}
						else JOptionPane.showMessageDialog(null, "Thẻ độc giả này đã hết hạn sử dụng!!!", "Việc cập nhật phiếu mượn sách không thành công!!!", JOptionPane.ERROR_MESSAGE);
						preparedStatement_checkNGAYHETHAN.close();
						resulSet_checkNGAYHETHAN.close();
						
					}
					else JOptionPane.showMessageDialog(null, "Mã độc giả vừa nhập không tồn tại trong hệ thống!!!", "Việc cập nhật phiếu mượn sách không thành công!!", JOptionPane.ERROR_MESSAGE);
					preparedStatement_checkMADOCGIA.close();
					resultSet_checkMADOCGIA.close();
					connection.close();

					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Thông tin chưa đầy đủ!!! Vui lòng điền đầy đủ thông tin!!!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
			}
			
				
	
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn phiếu mượn sách cần cập nhật trong bảng!!! Vui lòng chọn!!!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//Delete Button Event
	public void DeleteButtonEvent() {
		if(click_sign == true) {
			int maphieumuonsach_selected = click_GetID.getID();
			int masach_selected = click_GetMASACH.getMASACH();
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = (Connection) DriverManager.getConnection(url + dbName, "K74c1CEtnS","5HlVA1v9rX");
				
				PreparedStatement preparedStatement_DELETEPHIEUMUONSACH = (PreparedStatement)
						connection.prepareStatement("Delete from phieumuonsach where MAPHIEUMUONSACH='" + maphieumuonsach_selected + "';");
				int resultSet_DELETEPHIEUMUONSACH = preparedStatement_DELETEPHIEUMUONSACH.executeUpdate();
				
				PreparedStatement preparedStatement_UPDATETRANGTHAISACH = (PreparedStatement)
						connection.prepareStatement("UPDATE thongtinsach SET TRANGTHAI=? WHERE MASACH='" + masach_selected + "';");
				preparedStatement_UPDATETRANGTHAISACH.setInt(1, 0);
				int resultSet_UPDATETRANGTHAISACH = preparedStatement_UPDATETRANGTHAISACH.executeUpdate();
				
				if(resultSet_DELETEPHIEUMUONSACH > 0 && resultSet_UPDATETRANGTHAISACH > 0) {
					phieumuonsach_panel.remove(phieumuonsach_scrollpane);
					String column[]={"STT","MÃ PHIẾU MƯỢN SÁCH","MÃ ĐỘC GIẢ","MÃ SÁCH","NGÀY MƯỢN"};
					defaultTable = new DefaultTableModel(null, column);
					jt=new JTable(defaultTable);
					jt.setSize(300,300);
				    //jt.setBounds(30,40,00,200);  
				    jt.setPreferredScrollableViewportSize(new Dimension(500, 70));
				    jt.setFillsViewportHeight(true);
				    //jt.setBorder(BorderFactory.createLineBorder(Color.BLUE));
				    
				    ArrayList<PhieuMuonSachModel> phieumuonsachList = new ArrayList<>();
				    
				    PreparedStatement preparedStatement_GETTABLE = (PreparedStatement)
							connection.prepareStatement("Select * from phieumuonsach;");
				    
					ResultSet resultSet_getTABLE = preparedStatement_GETTABLE.executeQuery();
					while (resultSet_getTABLE.next()) {
						int MAPHIEUMUONSACH = resultSet_getTABLE.getInt("MAPHIEUMUONSACH");
						int MADOCGIA = resultSet_getTABLE.getInt("MADOCGIA");
						int MASACH = resultSet_getTABLE.getInt("MASACH");
						String NGAYMUON = resultSet_getTABLE.getString("NGAYMUON");
						PhieuMuonSachModel phieuMuonSachModel = new PhieuMuonSachModel(MAPHIEUMUONSACH, MADOCGIA, MASACH, NGAYMUON);
						phieumuonsachList.add(phieuMuonSachModel);
					}
					
					Object[] row = new Object[5];
					for(int i = 0; i <phieumuonsachList.size(); i++) {
					    row[0] = i+1;
					    row[1] = phieumuonsachList.get(i).getMAPHIEUMUONSACH();
					    row[2] = phieumuonsachList.get(i).getMADOCGIA();
					    row[3] = phieumuonsachList.get(i).getMASACH();;
					    row[4] = phieumuonsachList.get(i).getNGAYMUON();;
					    defaultTable.addRow(row);
					}
				    phieumuonsach_scrollpane = new JScrollPane(jt);
				    phieumuonsach_scrollpane.setBounds(45, 117, 449, 350);
					phieumuonsach_panel.add(phieumuonsach_scrollpane);
					phieumuonsach_panel.repaint();
					phieumuonsach_panel.revalidate();
					
					JOptionPane.showMessageDialog(null, "Bạn đã xóa phiếu mượn sách thành công!!!");
					preparedStatement_GETTABLE.close();
					resultSet_getTABLE.close();
				}
				else {
					JOptionPane.showMessageDialog(null, "Việc xóa phiếu mượn sách vừa chọn không thành công!!!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				preparedStatement_DELETEPHIEUMUONSACH.close();
				preparedStatement_UPDATETRANGTHAISACH.close();
				connection.close();
				click_sign = false;
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
		else {
			JOptionPane.showMessageDialog(null, "Chưa chọn phiếu mượn sách cần xóa!!! Vui lòng chọn!!!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//Reload Button Event
	public void ReloadButtonEvent() {
		click_sign = false;
		
		phieumuonsach_panel.remove(phieumuonsach_scrollpane);
		String column[]={"STT","MÃ PHIẾU MƯỢN SÁCH","MÃ ĐỘC GIẢ","MÃ SÁCH","NGÀY MƯỢN"};
		defaultTable = new DefaultTableModel(null, column);
		jt=new JTable(defaultTable);
		jt.setSize(300,300);
	    //jt.setBounds(30,40,00,200);  
	    jt.setPreferredScrollableViewportSize(new Dimension(500, 70));
	    jt.setFillsViewportHeight(true);
	    //jt.setBorder(BorderFactory.createLineBorder(Color.BLUE));
	    
	    ArrayList<PhieuMuonSachModel> phieumuonsachList = new ArrayList<>();
	    
	    try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url + dbName, "K74c1CEtnS","5HlVA1v9rX");
			PreparedStatement preparedStatement_GETTABLE = (PreparedStatement)
					connection.prepareStatement("Select * from phieumuonsach;");
			    
			ResultSet resultSet_getTABLE = preparedStatement_GETTABLE.executeQuery();
			while (resultSet_getTABLE.next()) {
				int MAPHIEUMUONSACH = resultSet_getTABLE.getInt("MAPHIEUMUONSACH");
				int MADOCGIA = resultSet_getTABLE.getInt("MADOCGIA");
				int MASACH = resultSet_getTABLE.getInt("MASACH");
				String NGAYMUON = resultSet_getTABLE.getString("NGAYMUON");
				PhieuMuonSachModel phieuMuonSachModel = new PhieuMuonSachModel(MAPHIEUMUONSACH, MADOCGIA, MASACH, NGAYMUON);
				phieumuonsachList.add(phieuMuonSachModel);
			}
				
			Object[] row = new Object[5];
			for(int i = 0; i <phieumuonsachList.size(); i++) {
				row[0] = i+1;
				row[1] = phieumuonsachList.get(i).getMAPHIEUMUONSACH();
				row[2] = phieumuonsachList.get(i).getMADOCGIA();
				row[3] = phieumuonsachList.get(i).getMASACH();;
				row[4] = phieumuonsachList.get(i).getNGAYMUON();;
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
		            		int maphieumuonsach = (Integer)defaultTable.getValueAt(index_row, i);
		            		String sMaphieumuonsachString = Integer.toString(maphieumuonsach);
		            		click_GetID = new Click_GetID(maphieumuonsach);
		            		phieumuonsach_ID_textfield.setText(sMaphieumuonsachString);
		            	}
		            	if(i==2) {
		            		int madocgia = (Integer)defaultTable.getValueAt(index_row, i);
		            		String sMadocgia = Integer.toString(madocgia);
		            		phieumuonsach_docgia_textfield.setText(sMadocgia);
		            	}
		            	if(i==3) {
		            		int masach = (Integer)defaultTable.getValueAt(index_row, i);
		            		String sMasach = Integer.toString(masach);
		            		click_GetMASACH = new Click_GetMASACH(masach);
		            		phieumuonsach_sach_textfield.setText(sMasach);
		            	}
		            	if(i==4) {
		            		String ngaymuon = (String)defaultTable.getValueAt(index_row, i);
		            		java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse(ngaymuon);
		            		dateChooser.setDate(date);
		            		
		            	}
		            }
		            click_sign = true;
				} catch (Exception e2) {
					
				} 
				
			}
		});
	    phieumuonsach_scrollpane = new JScrollPane(jt);
	    phieumuonsach_scrollpane.setBounds(45, 117, 449, 350);
		phieumuonsach_panel.add(phieumuonsach_scrollpane);
		phieumuonsach_panel.repaint();
		phieumuonsach_panel.revalidate();
		
		
	}
}
