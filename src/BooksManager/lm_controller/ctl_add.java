package BooksManager.lm_controller;

import lm_model.mdl_thongtinsach;
import lm_service.sv_thongtinsach;
import lm_service.sv_thongtinsach_impl;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import BooksManager.lm_view.jf_thongtinsach;
import BooksManager.lm_view.jf_add;

public class ctl_add {
    

    private JButton btnSave;
    private JButton btnExit;
    private JTextField jtfMaSach;
    private JTextField jtfTenSach;
    private JTextField jtfMaTheLoai;
    private JTextField jtfMaTacGia;
    private JTextField jtfNamXuatBan;
    private JTextField jtfNhaXuatBan;
    private JDateChooser jdcNgayNhap;
    private JTextField jtfTriGia;
    private JTextField jtfTinhTrang;
    private JCheckBox jcbDaMuon;
    private JLabel jlbMsg;

    private mdl_thongtinsach thongtinsach = null;

    private sv_thongtinsach SV_thongtinsach = null;
    
    public ctl_add(JButton btnSave, JButton btnExit, JTextField jtfMaSach, JTextField jtfTenSach, JTextField jtfMaTheLoai, JTextField jtfMaTacGia, JTextField jtfNamXuatBan, JTextField jtfNhaXuatBan, JDateChooser jdcNgayNhap, JTextField jtfTriGia, JTextField jtfTinhTrang, JCheckBox jcbDaMuon, JLabel jlbMsg) {
        this.btnSave = btnSave;
        this.btnExit = btnExit;
        this.jtfMaSach = jtfMaSach;
        this.jtfTenSach = jtfTenSach;
        this.jtfMaTheLoai = jtfMaTheLoai;
        this.jtfMaTacGia = jtfMaTacGia;
        this.jtfNamXuatBan = jtfNamXuatBan;
        this.jtfNhaXuatBan = jtfNhaXuatBan;
        this.jdcNgayNhap = jdcNgayNhap;
        this.jtfTriGia = jtfTriGia;
        this.jtfTinhTrang = jtfTinhTrang;
        this.jcbDaMuon = jcbDaMuon;
        this.jlbMsg = jlbMsg;

        this.SV_thongtinsach = new sv_thongtinsach_impl();
    }
    
    public void setView(mdl_thongtinsach thongtinsach) {
        try {
            this.thongtinsach = thongtinsach;
            // set data
            jtfMaSach.setText("" + thongtinsach.getMASACH());
            jtfTenSach.setText(thongtinsach.getTENSACH());
            jtfMaTheLoai.setText("" + thongtinsach.getMATHELOAI());
            jtfMaTacGia.setText("" + thongtinsach.getMATACGIA());
            jtfNamXuatBan.setText(thongtinsach.getNAMXUATBAN());
            jtfNhaXuatBan.setText(thongtinsach.getNHAXUATBAN());            
            jtfTriGia.setText("" + thongtinsach.getTRIGIA());
            jtfTinhTrang.setText(thongtinsach.getTINHTRANG());
            jcbDaMuon.setSelected(thongtinsach.isTRANGTHAI());
            
            if (jtfMaSach.getText() == null) {
            String a = "01/01/2020" + thongtinsach.getNGAYNHAP();
            SimpleDateFormat DF = new SimpleDateFormat("dd/MM/yyyy");
            Date date = DF.parse((String)a);
            jdcNgayNhap.setDate(date); } 
            else {
            String a = thongtinsach.getNGAYNHAP();
            SimpleDateFormat DF = new SimpleDateFormat("dd/MM/yyyy");
            Date date = DF.parse((String)a);
            jdcNgayNhap.setDate(date); }
            
            // set event
            setEvent();
        } catch (ParseException ex) {
            Logger.getLogger(ctl_add.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setEvent() {
        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (!checkNotNull()) {
                        jlbMsg.setText("Vui lòng nhập dữ liệu bắt buộc!");
                    } else {
                        thongtinsach.setMASACH(Integer.parseInt(jtfMaSach.getText()));
                        thongtinsach.setMATACGIA(Integer.parseInt(jtfMaTacGia.getText()));
                        thongtinsach.setMATHELOAI(Integer.parseInt(jtfMaTheLoai.getText()));
                        thongtinsach.setTRIGIA(Integer.parseInt(jtfTriGia.getText()));
                        thongtinsach.setTENSACH(jtfTenSach.getText().trim());
                        
                        DateFormat b = new SimpleDateFormat("dd/MM/yyyy");
                        String c =  b.format(jdcNgayNhap.getDate());
                        thongtinsach.setNGAYNHAP(c);
                        
                        thongtinsach.setNAMXUATBAN((jtfNamXuatBan.getText()));                                                
                        thongtinsach.setNHAXUATBAN(jtfNhaXuatBan.getText());    
                        thongtinsach.setTINHTRANG(jtfTinhTrang.getText());                                               
                        thongtinsach.setTRANGTHAI(jcbDaMuon.isSelected());
                        if (showDialog()) {
                            int lastId = SV_thongtinsach.createOrUpdateOrDelete(thongtinsach);
                            if (lastId != 0) {
                                thongtinsach.setMASACH(lastId);
                                jtfMaSach.setText("#" + thongtinsach.getMASACH());
                                jlbMsg.setText("Xử lý cập nhật dữ liệu thành công!");
                                //new jf_add(thongtinsach).setEnabled(true);
                                //new jf_thongtinsach().setVisible(true);
                            } else {
                                jlbMsg.setText("Có lỗi xảy ra, vui lòng thử lại!");
                            }
                        }                   
                    }
                } catch (Exception ex) {
                    jlbMsg.setText(ex.toString());
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnSave.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSave.setBackground(new Color(100, 221, 23));
            }
        });
        
    }

    private boolean checkNotNull() {
        return jtfTenSach.getText() != null && !jtfTenSach.getText().equalsIgnoreCase("");
    }

    private boolean showDialog() {
        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Bạn muốn cập nhật dữ liệu hay không?", "Thông báo", JOptionPane.YES_NO_OPTION);
        return dialogResult == JOptionPane.YES_OPTION;
    }       
}
