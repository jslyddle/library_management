package lm_dao;

import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.sql.ResultSet;
import lm_model.mdl_thongtinsach;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 *
 * @author Brown.D
 */
public class dao_thongtinsach_impl implements dao_thongtinsach {

    @Override
    public List<mdl_thongtinsach> getList() {
       
        try {
            Connection con = DBConnect.getConnection();
            String sql ="SELECT * FROM thongtinsach";
            List<mdl_thongtinsach> list = new ArrayList<>();
            PreparedStatement ps = con.prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mdl_thongtinsach thongtinsach = new mdl_thongtinsach();
                thongtinsach.setMASACH(rs.getInt("MASACH"));
                thongtinsach.setTENSACH(rs.getString("TENSACH"));
                thongtinsach.setMATHELOAI(rs.getInt("MATHELOAI"));
                thongtinsach.setMATACGIA(rs.getInt("MATACGIA"));
                thongtinsach.setNAMXUATBAN(rs.getString("NAMXUATBAN"));
                thongtinsach.setNHAXUATBAN(rs.getString("NHAXUATBAN"));
                thongtinsach.setNGAYNHAP(rs.getString("NGAYNHAP"));
                thongtinsach.setTRIGIA(rs.getInt("TRIGIA"));
                thongtinsach.setTINHTRANG(rs.getString("TINHTRANG"));
                thongtinsach.setTRANGTHAI(rs.getBoolean("TRANGTHAI"));
                list.add(thongtinsach);
            }
            ps.close();
            rs.close();
            con.close();
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    return  null;
    }
    
    @Override
    public int createOrUpdateOrDelete(mdl_thongtinsach thongtinsach) {
        try {
            Connection con = DBConnect.getConnection();
            String sql = "INSERT INTO thongtinsach(MASACH, TENSACH, MATHELOAI, MATACGIA, NAMXUATBAN, NHAXUATBAN, NGAYNHAP, TRIGIA, TINHTRANG, TRANGTHAI) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE MASACH = VALUES(MASACH), TENSACH = VALUES(TENSACH), MATHELOAI = VALUES(MATHELOAI), MATACGIA = VALUES(MATACGIA), NAMXUATBAN = VALUES(NAMXUATBAN), NHAXUATBAN = VALUES(NHAXUATBAN), NGAYNHAP = VALUES(NGAYNHAP), TRIGIA = VALUES(TRIGIA), TINHTRANG = VALUES(TINHTRANG), TRANGTHAI = VALUES(TRANGTHAI);";
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, thongtinsach.getMASACH());
            ps.setString(2, thongtinsach.getTENSACH());
            ps.setInt(3, thongtinsach.getMATHELOAI());
            ps.setInt(4, thongtinsach.getMATACGIA());
            ps.setString(5, thongtinsach.getNAMXUATBAN());            
            ps.setString(6, thongtinsach.getNHAXUATBAN());
            ps.setString(7, thongtinsach.getNGAYNHAP());
            ps.setInt(8, thongtinsach.getTRIGIA());
            ps.setString(9, thongtinsach.getTINHTRANG());
            ps.setBoolean(10, thongtinsach.isTRANGTHAI());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            ps.close();
            con.close();
            return generatedKey;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    
    public static void main(String[] args) {
        dao_thongtinsach DAO_thongtinsach = new dao_thongtinsach_impl();
        System.out.println(DAO_thongtinsach.getList());
    }
}
