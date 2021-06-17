package lm_utility;

import lm_model.mdl_thongtinsach;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ClassTableModel {
    
    public DefaultTableModel setTablethongtinsach(List<mdl_thongtinsach> listItem, String[] listColumn) {
        int columns = listColumn.length;
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 9 ? Boolean.class : String.class;
            }
        };
        dtm.setColumnIdentifiers(listColumn);
        Object[] obj;
        int num = listItem.size();
        mdl_thongtinsach thongtinsach = null;
        for (int i = 0; i < num; i++) {
            thongtinsach = listItem.get(i);
            obj = new Object[columns];
            obj[0] = thongtinsach.getMASACH();
            obj[1] = thongtinsach.getTENSACH();
            obj[2] = thongtinsach.getMATHELOAI();
            obj[3] = thongtinsach.getMATACGIA();
            obj[4] = thongtinsach.getNAMXUATBAN();
            obj[5] = thongtinsach.getNHAXUATBAN();
            obj[6] = thongtinsach.getNGAYNHAP();
            obj[7] = thongtinsach.getTRIGIA();
            obj[8] = thongtinsach.getTINHTRANG();
            obj[9] = thongtinsach.isTRANGTHAI();            
            dtm.addRow(obj);
        }
        return dtm;
    }
}
