package com.example.r2dbc.mapper;

import com.example.r2dbc.domain.Beer;
import com.example.r2dbc.dto.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    BeerDTO toDto(Beer beer);

    Beer toDomain(BeerDTO dto);
}
