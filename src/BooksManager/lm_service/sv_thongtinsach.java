package lm_service;

import lm_model.mdl_thongtinsach;
import java.util.List;

public interface  sv_thongtinsach {
    public List<mdl_thongtinsach> getList();
    
    public int createOrUpdateOrDelete(mdl_thongtinsach thongtinsach);
}
