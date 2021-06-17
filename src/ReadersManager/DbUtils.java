package ReadersManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class DbUtils {
    public static TableModel resultSetToTableModel(ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Vector columnNames = new Vector();

            // Get the column names
           // for (int column = 0; column < numberOfColumns; column++) {
                columnNames.addElement("Mã độc giả");
                columnNames.addElement("Họ tên");
                columnNames.addElement("Loại độc giả");
                columnNames.addElement("Địa chỉ");
                columnNames.addElement("Email");
                columnNames.addElement("Ngày sinh");
                columnNames.addElement("Ngày lập thẻ");
                columnNames.addElement("Ngày hết hạn");
                columnNames.addElement("Tổng nợ");
                
           // }

            // Get all rows.
            Vector rows = new Vector();

            while (rs.next()) {
                Vector newRow = new Vector();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.addElement(rs.getObject(i));
                }

                rows.addElement(newRow);
            }

            return new DefaultTableModel(rows, columnNames);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
