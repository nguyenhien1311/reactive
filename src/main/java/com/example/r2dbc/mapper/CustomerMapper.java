package com.example.r2dbc.mapper;

import com.example.r2dbc.domain.Customer;
import com.example.r2dbc.dto.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    CustomerDTO toDTO(Customer customer);

    Customer toDomain(CustomerDTO dto);
}
