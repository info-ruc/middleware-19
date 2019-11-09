package com.printer.core.service.impl;

import com.printer.core.entity.FileOrder;
import com.printer.core.repository.FileOrderRepository;
import com.printer.core.service.FileOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FileOrderServiceImpl implements FileOrderService {
    @Resource
    private FileOrderRepository fileOrderRepository;

    @Override
    public FileOrder addFileOrder(FileOrder fileOrder) {
        return fileOrderRepository.save(fileOrder);
    }

    @Override
    public List<FileOrder> getOrdersByUserId(long userId) {
        return fileOrderRepository.findByUserIdOrderByOrderIdDesc(userId);
    }

    @Override
    public FileOrder getOrderById(long orderId) {
        return fileOrderRepository.findByOrderId(orderId);
    }

    @Override
    public void deleteOrders(long orderId) {
        fileOrderRepository.deleteById(orderId);
    }

    @Override
    public void updateOrders(long orderId, int copies, int startPage, int count, int isDuplex) {
        fileOrderRepository.updateByOrderId(copies, startPage, count, isDuplex, orderId);
    }
}
