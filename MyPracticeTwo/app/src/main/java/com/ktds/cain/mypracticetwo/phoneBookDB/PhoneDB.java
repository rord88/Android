package com.ktds.cain.mypracticetwo.phoneBookDB;

import com.ktds.cain.mypracticetwo.phoneBookVO.PhoneVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 206-013 on 2016-06-08.
 */
public class PhoneDB {
    private static Map<String, PhoneVO> db = new HashMap<String, PhoneVO>();

    public static void addPhoneInfo(String index, PhoneVO phoneVO) {
        db.put(index, phoneVO);
    }

    public static PhoneVO getPhoneInfo(String index) {
        return db.get(index);
    }

    public static List<String> getIndexes() {
        Iterator<String> keys = db.keySet().iterator();

        List<String> keyList = new ArrayList<String>();
        String key = "";
        while (keys.hasNext()) {
            key = keys.next();
            keyList.add(key);
        }
        return keyList;
    }


}