package lm_dao;

import java.util.List;
import lm_model.mdl_thongtinsach;

public interface dao_thongtinsach {
    public List<mdl_thongtinsach> getList();
    
    public int createOrUpdateOrDelete(mdl_thongtinsach thongtinsach);
}
    