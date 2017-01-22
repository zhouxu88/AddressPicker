package com.zx.copydatabase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.NumberPicker;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String DB_NAME = "mydb.db"; //数据库名称

    private NumberPicker provincePicker, cityPicker, areaPicker; //省市区选择器
    private AddressManager manager; //自定义的地址管理器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //拷贝assets下的文件"mydb.db"到 应用的databases目录下
        try {
            MyUtils.copyAssetsToDB(this, DB_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //初始化“地址管理器”对象
        manager = new AddressManager(getDatabasePath(DB_NAME).getPath());

        initView();
        initProvince();
    }

    //初始化View
    private void initView() {
        provincePicker = (NumberPicker) findViewById(R.id.province_picker);
        cityPicker = (NumberPicker) findViewById(R.id.city_picker);
        areaPicker = (NumberPicker) findViewById(R.id.area_picker);
    }

    //设置“省份”选择器的数据
    private void initProvince() {
        List<String> provinceList = manager.getProvinces(); //获取所有的省份
        //设置省份的值
        final String[] provinces = provinceList.toArray(new String[provinceList.size()]);
        provincePicker.setDisplayedValues(provinces);
        provincePicker.setMinValue(0); //设置第一个值
        provincePicker.setMaxValue(provinces.length - 1); //设置最后一个值
        //默认的省份的位置
        int defaultProvince = provinces.length / 2 == 0 ? provinces.length / 2 : provinces.length / 2 + 1;
        provincePicker.setValue(defaultProvince); //设置当前值

        //根据当前默认的省份来设置对应的市
        showCityByProvince(provinces[provincePicker.getValue()]);

        //省份的选择事件
        provincePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.i("tag", "newVal--->" + newVal);
                showCityByProvince(provinces[newVal]);
            }
        });
    }

    //根据选择的省份名来显示城市名
    private void showCityByProvince(String province) {
        List<String> cityList = manager.getCities(province);
        //设置城市的值
        final String[] cities = cityList.toArray(new String[cityList.size()]);
        cityPicker.setDisplayedValues(null); //清空之前的选择的数据
        cityPicker.setMinValue(0); //设置第一个值
        cityPicker.setMaxValue(cities.length - 1); //设置最后一个值
        cityPicker.setDisplayedValues(cities);

        //根据当前默认的城市来设置对应的区
        showAreaByCity(cities[cityPicker.getValue()]);

        //城市的选择事件
        cityPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                showAreaByCity(cities[newVal]);
            }
        });
    }

    //根据选择的城市名来显示区名
    private void showAreaByCity(String city) {
        List<String> areaList = manager.getAreas(city);
        //设置区的值
        String[] areas = areaList.toArray(new String[areaList.size()]);
        areaPicker.setDisplayedValues(null);
        areaPicker.setMinValue(0); //设置第一个值
        areaPicker.setMaxValue(areas.length - 1); //设置最后一个值
        areaPicker.setDisplayedValues(areas);
    }
}
