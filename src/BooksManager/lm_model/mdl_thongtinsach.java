package lm_model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Brown.D
 */
public class mdl_thongtinsach {
    
    private int MASACH;
    private String TENSACH;
    private int MATHELOAI;
    private int MATACGIA;
    private String NAMXUATBAN;
    private String NHAXUATBAN;
    Date today = Calendar.getInstance().getTime();
    DateFormat b = new SimpleDateFormat("dd/MM/yyyy");
    String c =  b.format(today.getTime());
    private String NGAYNHAP = c;
    private int TRIGIA;
    private String TINHTRANG;
    private boolean TRANGTHAI;

    public int getMASACH() {
        return MASACH;
    }

    public void setMASACH(int MASACH) {
        this.MASACH = MASACH;
    }

    public String getTENSACH() {
        return TENSACH;
    }

    public void setTENSACH(String TENSACH) {
        this.TENSACH = TENSACH;
    }

    public int getMATHELOAI() {
        return MATHELOAI;
    }

    public void setMATHELOAI(int MATHELOAI) {
        this.MATHELOAI = MATHELOAI;
    }

    public int getMATACGIA() {
        return MATACGIA;
    }

    public void setMATACGIA(int MATACGIA) {
        this.MATACGIA = MATACGIA;
    }

    public String getNAMXUATBAN() {
        return NAMXUATBAN;
    }

    public void setNAMXUATBAN(String NAMXUATBAN) {
        this.NAMXUATBAN = NAMXUATBAN;
    }

    public String getNHAXUATBAN() {
        return NHAXUATBAN;
    }

    public void setNHAXUATBAN(String NHAXUATBAN) {
        this.NHAXUATBAN = NHAXUATBAN;
    }

    public String getNGAYNHAP() {
        return NGAYNHAP;
    }

    public void setNGAYNHAP(String NGAYNHAP) {
        this.NGAYNHAP = NGAYNHAP;
    }

    public int getTRIGIA() {
        return TRIGIA;
    }

    public void setTRIGIA(int TRIGIA) {
        this.TRIGIA = TRIGIA;
    }

    public String getTINHTRANG() {
        return TINHTRANG;
    }

    public void setTINHTRANG(String TINHTRANG) {
        this.TINHTRANG = TINHTRANG;
    }

    public boolean isTRANGTHAI() {
        return TRANGTHAI;
    }

    public void setTRANGTHAI(boolean TRANGTHAI) {
        this.TRANGTHAI = TRANGTHAI;
    }  
}
