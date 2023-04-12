package com.uestc.crm;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.uestc.crm.mapper.BrandMapper;
import com.uestc.crm.mapper.BusinessMapper;
import com.uestc.crm.mapper.CarMapper;
import com.uestc.crm.mapper.SeriesMapper;
import com.uestc.crm.pojo.BrandPO;
import com.uestc.crm.pojo.BusinessPO;
import com.uestc.crm.pojo.CarPO;
import com.uestc.crm.pojo.SeriesPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author zhangqingyang
 * @create 2023-04-2023/4/7 20:18
 */

@SpringBootTest
public class CarBrandTest {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SeriesMapper seriesMapper;

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private CarMapper carMapper;

    @Test
    public void test01() {
        ArrayList<String> list6 = new ArrayList<>(Arrays.asList((
                "飞度 皓影 奥德赛 锐·混动 雅阁 凌派 冠道 奥德赛 缤智 锋范 歌诗图 皓影新能源 广汽本田VE-1 本田e NP1 极湃1 型格 艾力绅 本田X-NV 本田XR-V 本田CR-V 思域 杰德 本田UR-V 竞瑞 INSPIRE 哥瑞 思铂睿 本田e NS1"
        ).split(" ")));
        ArrayList<String> list7 = new ArrayList<>(Arrays.asList((
                "君威 凯越 微蓝 别克GL6 阅朗 别克GL8 英朗 昂科威 君越 别克VELITE6 威朗 昂科拉 VELITE5 微蓝6"
        ).split(" ")));
        ArrayList<String> list8 = new ArrayList<>(Arrays.asList((
                "天籁 轩逸 逍客 轩逸经典 奇骏 劲客 楼兰 轩逸·纯电 TIIDA 阳光 骊威 LANNIA 蓝鸟 西玛 玛驰 途乐 贵士 日产GT-R 碧莲 日产370Z 途达 日产NV200 帕拉丁 纳瓦拉 锐骐6 日产D22 日产ZN厢式车 帅客新能源"
        ).split(" ")));

        ArrayList<ArrayList<String>> list = new ArrayList<>();
        list.add(list6);
        list.add(list7);
        list.add(list8);

        long i = 6;
        for (ArrayList<String> arrayList : list) {
            for (String s : arrayList) {
                SeriesPO seriesPO = new SeriesPO();
                seriesPO.setSeries(s);
                seriesPO.setBrandId(i);
                seriesMapper.insert(seriesPO);
            }
            i++;
        }


    }

    @Test
    public void test02() throws ParseException {
        List<String> businessIds = businessMapper.selectList(new QueryWrapper<>()).stream().map(BusinessPO::getBizId).collect(Collectors.toList());
        List<BrandPO> brandPOS = brandMapper.selectList(new QueryWrapper<>());
        List<String> sheng = new ArrayList<>(Arrays.asList("京、津、冀、晋、蒙、辽、吉、黑、沪、苏、浙、皖、闽、赣、鲁、台、豫、鄂、湘、粤、桂、琼、港、澳、渝、川、贵、云、藏、陕、甘、青、宁、新".split("、")));
        List<String> zimu = new ArrayList<>(Arrays.asList("Q,W,E,R,T,Y,U,I,O,P,A,S,D,F,G,H,J,K,L,Z,X,C,V,B,N,M".split(",")));

        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            BrandPO brandPO = brandPOS.get(random.nextInt(brandPOS.size()));
            String brand = brandPO.getBrand();
            LambdaQueryWrapper<SeriesPO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SeriesPO::getBrandId, brandPO.getId());
            List<String> seriess = seriesMapper.selectList(queryWrapper).stream().map(SeriesPO::getSeries).collect(Collectors.toList());
            String s = seriess.get(random.nextInt(seriess.size()));
            CarPO carPO = new CarPO();
            carPO.setCarId(UUID.fastUUID().toString());
            carPO.setBizId(businessIds.get(random.nextInt(businessIds.size())));
            carPO.setPrice(Double.parseDouble(String.format("%.2f", random.nextDouble() * 100)));
            carPO.setIsBargain(false);
            carPO.setBrand(brand);
            long now = DateTime.now().getTime();
            //设置指定时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse("1999-11-30 00:00:00");
            long before = date.getTime();
            //产生long类型指定范围随机数
            long randomDate = before + (long) (random.nextFloat() * (now - before + 1));
            carPO.setLicensingTime(new Date(randomDate));
            carPO.setEngine(Double.parseDouble(String.format("%.2f", (random.nextDouble() * 5) + 1)));
            carPO.setFuelType(random.nextInt(4) + 1);
            carPO.setSeries(s);
            carPO.setCarType(random.nextInt(6) + 1);
            carPO.setKilometer(Double.parseDouble(String.format("%.2f", random.nextDouble() * 23)));
            String[] strings = GenerateUserUtil.place[GenerateUserUtil.getRandom(GenerateUserUtil.place.length)];
            carPO.setCity(strings[GenerateUserUtil.getRandom(strings.length)]);
            carPO.setLicensePlate(sheng.get(random.nextInt(sheng.size())) + zimu.get(random.nextInt(zimu.size())));
            carPO.setTransmissionCase(random.nextInt(2) + 1);
            carPO.setColor("");
            carPO.setInfo("电话联系");
            carPO.setTitle(brand + " " + s + " " + (random.nextInt(33) + 1990) + "款");
            carMapper.insert(carPO);

        }
    }

    @Test
    public void test03() {
    }
}
