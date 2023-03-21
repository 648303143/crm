package com.uestc.crm.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.uestc.crm.mapper.BrandMapper;
import com.uestc.crm.mapper.CarMapper;
import com.uestc.crm.mapper.CarPictureMapper;
import com.uestc.crm.mapper.SeriesMapper;
import com.uestc.crm.pojo.BrandPO;
import com.uestc.crm.pojo.CarPO;
import com.uestc.crm.pojo.CarPicturePO;
import com.uestc.crm.pojo.SeriesPO;
import com.uestc.crm.query.ListCarQuery;
import com.uestc.crm.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:44
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, CarPO> implements CarService {

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CarPictureMapper carPictureMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SeriesMapper seriesMapper;

    public int addCar(CarPO carPO, MultipartFile[] files) {
        if (files != null) {
            for (MultipartFile file : files) {
                // 获取文件原本的名字
                String originName = file.getOriginalFilename();
                // 判断文件是否是图片文件
                Set<String> set = new HashSet<>();
                set.add(".jpg");
                set.add(".jpeg");
                set.add(".gif");
                set.add(".png");
                set.add(".bmp");
                // 取出文件的后缀
                int count = 0;
                for (int i = 0; i < originName.length(); i++) {
                    if (originName.charAt(i) == '.') {
                        count = i;
                        break;
                    }
                }
                String endName = originName.substring(count); //取出文件类型
                if (!set.contains(endName)) {
                    throw new RuntimeException("上传的文件类型错误,只能上传pdf,doc,docx类型的文件");
                }
                // 创建保存路径
                String savePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\picture\\";
                // 保存文件的文件夹
                File folder = new File(savePath);
                // 判断路径是否存在,不存在则自动创建
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                try {
                    file.transferTo(new File(folder, originName));
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
                CarPicturePO carPicturePO = new CarPicturePO();
                carPicturePO.setCarId(carPO.getCarId());
                carPicturePO.setUrl("http://localhost:8080/static/picture/" + originName);
                carPictureMapper.insert(carPicturePO);
            }
        }
        LambdaQueryWrapper<CarPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CarPO::getCarId, carPO.getCarId());
        Long count = carMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new RuntimeException();
        }
        return carMapper.insert(carPO);
    }

    public int updateCarById(CarPO carPO) {
        LambdaQueryWrapper<CarPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CarPO::getCarId, carPO.getCarId());
        int update = carMapper.update(carPO, queryWrapper);
        return update;
    }

    public CarPO getCarById(String carId) {
        LambdaQueryWrapper<CarPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CarPO::getCarId, carId);
        CarPO carPO = carMapper.selectOne(queryWrapper);
        return carPO;
    }

    public Page<CarPO> listCar(ListCarQuery query) {
        LambdaQueryWrapper<CarPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(query.getCarId()), CarPO::getCarId, query.getCarId())
                .like(StrUtil.isNotBlank(query.getTitle()), CarPO::getTitle, query.getTitle())
                .eq(StrUtil.isNotBlank(query.getBrand()), CarPO::getBrand, query.getBrand())
                .eq(StrUtil.isNotBlank(query.getSeries()), CarPO::getSeries, query.getSeries())
                .ge(query.getMinPrice() != null, CarPO::getPrice, query.getMinPrice())
                .le(query.getMaxPrice() != null, CarPO::getPrice, query.getMaxPrice())
                .ge(query.getMinEngine() != null, CarPO::getEngine, query.getMinEngine())
                .le(query.getMaxEngine() != null, CarPO::getEngine, query.getMaxEngine())
                .ge(query.getMinKilometer() != null, CarPO::getPrice, query.getMinKilometer())
                .le(query.getMaxKilometer() != null, CarPO::getPrice, query.getMaxKilometer())
                .eq(query.getCarType() != null, CarPO::getCarType, query.getCarType())
                .eq(query.getFuelType() != null, CarPO::getFuelType, query.getFuelType())
                .eq(query.getTransmissionCase() != null, CarPO::getTransmissionCase, query.getTransmissionCase())
                .ge(query.getLicensingTime() != null, CarPO::getLicensingTime, query.getLicensingTime());

        Page<CarPO> page = carMapper.selectPage(new Page<>(query.getCurrent(), query.getSize()), queryWrapper);
        return page;
    }

    public List<String> listBrand() {
        LambdaQueryWrapper<BrandPO> queryWrapper = new LambdaQueryWrapper<>();
        List<BrandPO> brandPOS = brandMapper.selectList(queryWrapper);
        List<String> brandList = brandPOS.stream().map(BrandPO::getBrand).collect(Collectors.toList());
        return brandList;
    }

    public List<String> getSeriesListByBrand(String brand) {
        LambdaQueryWrapper<BrandPO> brandQueryWrapper = new LambdaQueryWrapper<>();
        brandQueryWrapper.eq(BrandPO::getBrand, brand);
        BrandPO brandPO = brandMapper.selectOne(brandQueryWrapper);

        LambdaQueryWrapper<SeriesPO> seriesQueryWrapper = new LambdaQueryWrapper<>();
        seriesQueryWrapper.eq(SeriesPO::getBrandId, brandPO.getId());
        List<SeriesPO> seriesPOS = seriesMapper.selectList(seriesQueryWrapper);
        List<String> seriesList = seriesPOS.stream().map(SeriesPO::getSeries).collect(Collectors.toList());
        return seriesList;
    }

    public List<String> getPicturesByCarId(String carId) {
        LambdaQueryWrapper<CarPicturePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CarPicturePO::getCarId, carId);
        List<CarPicturePO> picturePOS = carPictureMapper.selectList(queryWrapper);
        List<String> list = picturePOS.stream().map(CarPicturePO::getUrl).collect(Collectors.toList());
        return list;
    }
}
