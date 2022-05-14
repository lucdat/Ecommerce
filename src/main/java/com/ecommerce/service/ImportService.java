package com.ecommerce.service;

import java.util.HashMap;
import java.util.Map;

public interface ImportService {
    Map<String,String> checkOut(ImportItemService importService);
}
