package lm_service;

import lm_dao.dao_thongtinsach;
import lm_dao.dao_thongtinsach_impl;
import lm_model.mdl_thongtinsach;
import java.util.List;

public class sv_thongtinsach_impl implements sv_thongtinsach {
    
    private dao_thongtinsach DAO_thongtinsach = null;

    public sv_thongtinsach_impl() {
        this.DAO_thongtinsach = new dao_thongtinsach_impl();
    }

    public List<mdl_thongtinsach> getList() {
        return DAO_thongtinsach.getList();
    }  
    
    @Override
    public int createOrUpdateOrDelete(mdl_thongtinsach thongtinsach) {
        return DAO_thongtinsach.createOrUpdateOrDelete(thongtinsach);
    }
}
