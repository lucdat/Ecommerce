package com.ecommerce.service.impl;

import com.ecommerce.dto.domain.ImportItemDTO;
import com.ecommerce.service.ImportItemService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@SessionScope
public class ImportItemServiceImpl implements ImportItemService {
    private Map<String, ImportItemDTO> IMPORT_ITEM = new HashMap<>();

    @Override
    public String add(ImportItemDTO itemDTO) {
        if(itemDTO!=null){
            IMPORT_ITEM.put(itemDTO.getKey(), itemDTO);
            return "successfull";
        }
        return "error";
    }

    @Override
    public String delete(ImportItemDTO itemDTO) {
        if(itemDTO!=null){
            IMPORT_ITEM.remove(itemDTO.getKey());
            return "successfull";
        }
        return "error";
    }

    @Override
    public String update(ImportItemDTO itemDTO) {
        if(itemDTO!=null){
            if(IMPORT_ITEM.containsKey(itemDTO.getKey())){
                IMPORT_ITEM.put(itemDTO.getKey(), itemDTO);
                return "successfull";
            }else{
                return "error";
            }
        }
        return "error";
    }

    @Override
    public int amount() {
        int sum=0;
        for(ImportItemDTO dto: IMPORT_ITEM.values()){
            sum+=dto.getQuantity();
        }
        return sum;
    }

    @Override
    public Double totalPrice() {
        Double sum=0.0;
        for(ImportItemDTO dto: IMPORT_ITEM.values()){
            sum+=dto.getQuantity()*dto.getPrice();
        }
        return sum;
    }

    @Override
    public String clear() {
        IMPORT_ITEM.clear();
        return "successfull";
    }

    @Override
    public Collection<ImportItemDTO> importItem() {
        return IMPORT_ITEM.values();
    }
}
