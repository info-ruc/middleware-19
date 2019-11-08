package com.printer.core.service;

import com.printer.core.entity.FileOrder;

import java.util.List;

public interface FileOrderService {
    FileOrder addFileOrder(FileOrder fileOrder);

    List<FileOrder> getOrdersByUserId(long userId);

    FileOrder getOrderById(long orderId);

    void deleteOrders(long orderId);

    void updateOrders(long orderId, int copies, int startPage, int count, int isDuplex);
}
