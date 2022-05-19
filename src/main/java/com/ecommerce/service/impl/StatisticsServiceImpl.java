package com.ecommerce.service.impl;
import com.ecommerce.domain.Import;
import com.ecommerce.domain.OrderItem;
import com.ecommerce.domain.OrderStatus;
import com.ecommerce.domain.Orders;
import com.ecommerce.dto.domain.ImportFilter;
import com.ecommerce.dto.domain.OrderFilter;
import com.ecommerce.dto.domain.ProductFilter;
import com.ecommerce.dto.domain.Statistics;
import com.ecommerce.repositories.ImportRepo;
import com.ecommerce.repositories.OrderRepo;
import com.ecommerce.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;



@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final OrderRepo orderRepo;
    private final ImportRepo importRepo;

    @Override
    public Statistics orderFilter(OrderFilter orderFilter) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String date[] = simpleDateFormat.format(orderFilter.getDate()).split("-");
        Collection<Orders> orders;
        switch (orderFilter.getType()){
            case 1:{
                orders =  orderRepo
                    .gitListOrdersByMonTh(Integer.parseInt(date[0]),Integer.parseInt(date[1]), OrderStatus.valueOf(orderFilter.getStatus()));
            }break;
            case 2:{
                orders =  orderRepo
                        .gitListOrdersByYear(Integer.parseInt(date[0]),OrderStatus.valueOf(orderFilter.getStatus()));
            }break;
            default:
                orders =  orderRepo
                        .gitListOrdersByDay(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]), OrderStatus.valueOf(orderFilter.getStatus()));
        }
        int quantity=0;
        Double totalPrice=0.0;
        for (Orders order : orders) {
            quantity += order.getAmount();
            totalPrice +=order.getTotalPrice();
        }
        return new Statistics(orders.size(),quantity,totalPrice);
    }

    @Override
    public Statistics importFilter(ImportFilter importFilter) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String date[] = simpleDateFormat.format(importFilter.getDate()).split("-");
        Collection<Import> imports;
        switch (importFilter.getType()){
            case 1:{
                imports =  importRepo
                        .gitListOrdersByMonTh(Integer.parseInt(date[0]),Integer.parseInt(date[1]));
            }break;
            case 2:{
                imports =  importRepo
                        .gitListOrdersByYear(Integer.parseInt(date[0]));
            }break;
            default:
                imports = importRepo
                        .gitListOrdersByDay(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]));
        }
        int quantity=0;
        Double totalPrice=0.0;
        for (Import anImport : imports) {
            quantity += anImport.getAmount();
            totalPrice +=anImport.getTotalPrice();
        }
        return new Statistics(imports.size(),quantity,totalPrice);
    }

    @Override
    public Statistics productFilter(ProductFilter productFilter) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String date[] = simpleDateFormat.format(productFilter.getDate()).split("-");
        Collection<OrderItem> orderItems=new ArrayList<>();
        switch (productFilter.getType()){
            case 1:{
                orderItems =  orderRepo
                        .gitListOrdersItemByMonTh(Integer.parseInt(date[0]),Integer.parseInt(date[1]), OrderStatus.valueOf(productFilter.getStatus()), productFilter.getProductId());
            }break;
            case 2:{
                orderItems =  orderRepo
                        .gitListOrdersItemByYear(Integer.parseInt(date[0]),OrderStatus.valueOf(productFilter.getStatus()), productFilter.getProductId());
            }break;
            default:
                orderItems =  orderRepo
                        .gitListOrdersItemByDay(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]), OrderStatus.valueOf(productFilter.getStatus()),productFilter.getProductId());
        }
        int quantity=0;
        Double totalPrice=0.0;
        for(OrderItem item : orderItems){
            quantity += item.getQuantity();
            totalPrice += item.getTotalPrice();
        }
        return new Statistics(orderItems.size(),quantity,totalPrice);
    }
}
