
package com.ruc.service;

import java.util.List;

import com.ruc.pojo.Product;
import com.ruc.pojo.PropertyValue;

public interface PropertyValueService {
    void init(Product p);
    void update(PropertyValue pv);

    PropertyValue get(int ptid, int pid);
    List<PropertyValue> list(int pid);
}

	
