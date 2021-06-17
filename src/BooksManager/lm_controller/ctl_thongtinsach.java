package BooksManager.lm_controller;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import lm_model.mdl_thongtinsach;
import lm_service.sv_thongtinsach;
import lm_service.sv_thongtinsach_impl;
import lm_dao.dao_thongtinsach_impl;
import lm_dao.dao_thongtinsach;
import lm_dao.DBConnect;
import lm_utility.ClassTableModel;
import java.util.Date;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import BooksManager.lm_view.jf_add;
import BooksManager.lm_view.jf_thongtinsach;
import BooksManager.lm_controller.ctl_add;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.toedter.calendar.JDateChooser;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JOptionPane;


public class ctl_thongtinsach  {
    
    private JPanel jpnView;
    private JButton btnAdd;
    private JButton btnDelete;
    private JTextField jtfSearch;

    private ClassTableModel classTableModel = null;

    private final String[] COLUMNS = {"Mã sách", "Tên sách", "Mã thể loại", "Mã tác giả", "Năm xuất bản", "Nhà xuất bản", "Ngày nhập", "Trị giá", "Tình trạng", "Trạng thái"};

    private sv_thongtinsach SV_thongtinsach = null;

    private TableRowSorter<TableModel> rowSorter = null;
    
    public ctl_thongtinsach(JPanel jpnView, JButton btnAdd, JButton btnDelete, JTextField jtfSearch) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.btnDelete = btnDelete;
        this.jtfSearch = jtfSearch;

        this.classTableModel = new ClassTableModel();

        this.SV_thongtinsach = new sv_thongtinsach_impl();
    }
    
    public void setDataToTable() {
        List<mdl_thongtinsach> listItem = SV_thongtinsach.getList();
        DefaultTableModel model = classTableModel.setTablethongtinsach(listItem, COLUMNS);
        JTable table = new JTable(model);

        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);

        jtfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int selectedRowIndex = table.getSelectedRow();
                    selectedRowIndex = table.convertRowIndexToModel(selectedRowIndex);
                    System.out.println(selectedRowIndex);
                    
                    mdl_thongtinsach thongtinsach = new mdl_thongtinsach();
                    thongtinsach.setMASACH((int) model.getValueAt(selectedRowIndex, 0));                   
                    thongtinsach.setTENSACH(model.getValueAt(selectedRowIndex, 1).toString());
                    thongtinsach.setMATHELOAI((int) model.getValueAt(selectedRowIndex, 2));                   
                    thongtinsach.setMATACGIA((int) model.getValueAt(selectedRowIndex, 3)); 
                    thongtinsach.setNAMXUATBAN(model.getValueAt(selectedRowIndex, 4).toString());
                    thongtinsach.setNHAXUATBAN(model.getValueAt(selectedRowIndex, 5).toString());
                    thongtinsach.setNGAYNHAP(model.getValueAt(selectedRowIndex, 6).toString());
                    thongtinsach.setTRIGIA((int) model.getValueAt(selectedRowIndex, 7));
                    thongtinsach.setTINHTRANG(model.getValueAt(selectedRowIndex, 8).toString());
                    thongtinsach.setTRANGTHAI((boolean) model.getValueAt(selectedRowIndex, 9));                  
                   
                    jf_add frame = new jf_add(thongtinsach);
                    frame.setTitle("Thông tin sách");
                    frame.setResizable(false);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            }           
        });
        
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {   
                
                mdl_thongtinsach thongtinsach = new mdl_thongtinsach();   
                jf_add frame = new jf_add(thongtinsach);
                frame.setTitle("Thông tin sách");
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        
        btnDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {   
                try {
                    Connection con = DBConnect.getConnection();
                    String sql = "DElETE FROM thongtinsach WHERE MASACH =?";
                    
                    if (JOptionPane.showConfirmDialog(btnDelete, "Do you want to delete?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        PreparedStatement pst = con.prepareStatement(sql);
                        pst.setString(1, table.getValueAt(table.getSelectedRow(), 0).toString());
                        pst.executeUpdate();       
                        model.removeRow(table.getSelectedRow());
                    }       
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }               
            }
        });
        
        // design
        table.getTableHeader().setFont(new Font("SF Pro Display", Font.PLAIN, 9));
        table.getTableHeader().setPreferredSize(new Dimension(100, 50));
        table.setRowHeight(50);
        table.validate();
        table.repaint();
        
        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(table);
        scroll.setPreferredSize(new Dimension(1350, 400));
        jpnView.removeAll();
        jpnView.setLayout(new CardLayout());
        jpnView.add(scroll);
        jpnView.validate();
        jpnView.repaint();
    
    }
}
