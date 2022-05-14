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
    public Map<String,String> add(ImportItemDTO itemDTO) {
        Map<String,String> response = new HashMap<>();
        if(itemDTO!=null){
            IMPORT_ITEM.put(itemDTO.getKey(), itemDTO);
            response.put("message","success");
        }else response.put("message","error");
        return response;
    }

    @Override
    public Map<String,String> delete(ImportItemDTO itemDTO) {
        Map<String,String> response = new HashMap<>();
        if(itemDTO!=null){
            IMPORT_ITEM.remove(itemDTO.getKey());
            response.put("message","success");
        }else response.put("message","error");
        return response;
    }

    @Override
    public Map<String,String> update(ImportItemDTO itemDTO) {
        Map<String,String> response = new HashMap<>();
        if(itemDTO!=null){
            if(IMPORT_ITEM.containsKey(itemDTO.getKey())){
                IMPORT_ITEM.put(itemDTO.getKey(), itemDTO);
                response.put("message","success");
            }else response.put("message","error");
            return response;
        }
        return response;
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
    public Map<String,String> clear() {
        Map<String,String> response = new HashMap<>();
        IMPORT_ITEM.clear();
        response.put("message","success");
        return response;
    }

    @Override
    public Collection<ImportItemDTO> importItem() {
        return IMPORT_ITEM.values();
    }
}
