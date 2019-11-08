package com.printer.core.repository;

import com.printer.core.entity.FileOrder;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FileOrderRepository extends JpaRepository<FileOrder, Long> {

    List<FileOrder> findByUserIdOrderByOrderIdDesc(long userId);
    FileOrder findByOrderId(long orderId);

    @Transactional
    @Modifying
    @Query("update FileOrder fo SET fo.copies = :copies, fo.startPage = :startPage, fo.count = :count, fo.isDuplex = :isDuplex where fo.orderId = :orderId")
    void updateByOrderId(@Param("copies") int copies, @Param("startPage") int startPage, @Param("count") int count,
                        @Param("isDuplex") int isDuplex, @Param("orderId") long orderId);
}
