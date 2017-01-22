package com.zx.copydatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 周旭 on 2017/1/21.
 * "省市区"数据库操作类
 */

public class AddressManager {

    //ssq表的字段的名字
    private static final String TABLE_NAME = "ssq"; //表名
    private static final String PROVINCE = "province"; //省份名称
    private static final String CITY = "city"; //市的名称
    private static final String AREA = "area"; //区的名称

    private SQLiteDatabase database; //sqlite数据库对象

    /**
     * @param dbPath 数据库中“省市区”表的路径
     */
    public AddressManager(String dbPath) {
        database = SQLiteDatabase.openOrCreateDatabase(dbPath, null);
    }

    /**
     * 查询所有省份
     *
     * @return 所有的省份名称
     */
    public List<String> getProvinces() {

        String sql = "select distinct " + PROVINCE + " from " + TABLE_NAME;
        Cursor cursor = database.rawQuery(sql, null);
        List<String> provinceList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String province = cursor.getString(cursor.getColumnIndex("province"));
                Log.i("tag", "province----->" + province);
                provinceList.add(province);
            }
            cursor.close();
        }
        return provinceList;
    }

    /**
     * 查询指定省份或直辖市的所有市的集合
     *
     * @param province 省份名称
     * @return 查询到的所有城市
     */
    public List<String> getCities(String province) {
        String sql = "select distinct " + CITY + " from " + TABLE_NAME + " where " + PROVINCE + " = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{province});
        List<String> addressList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String city = cursor.getString(cursor.getColumnIndex(CITY));
            Log.i("tag", "city----->" + city);
            addressList.add(city);
        }
        //关闭游标
        cursor.close();
        return addressList;
    }


    /**
     * 查询指定市的所有区的列表的List集合
     *
     * @param city 市的名称
     * @return 查询到的所有区
     */
    public List<String> getAreas(String city) {
        //获取指定市的所有区的列表的sql语句
        String sql = "select distinct " + AREA + " from " + TABLE_NAME + " where " + CITY + " = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{city});
        List<String> addressList = new ArrayList<>();
        while (cursor.moveToNext()) {
            //获取区的名称
            String area = cursor.getString(cursor.getColumnIndex(AREA));
            Log.i("tag", "area----->" + area);
            //把所有的区添加到List集合
            addressList.add(area);
        }
        //关闭游标
        cursor.close();
        return addressList;
    }
}
