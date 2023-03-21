package com.uestc.crm.query;

import com.uestc.crm.query.BasePageQuery;
import lombok.Data;

import java.util.Date;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/2 17:27
 */

@Data
public class ListCarQuery extends BasePageQuery {

    private String carId;
    private String title;
    private Double minPrice;
    private Double maxPrice;
    private String brand;
    private String series;
    private Double minEngine;
    private Double maxEngine;
    private Integer fuelType;
    private Integer carType;
    private Integer transmissionCase;
    private Double minKilometer;
    private Double maxKilometer;
    private Date licensingTime;




}
