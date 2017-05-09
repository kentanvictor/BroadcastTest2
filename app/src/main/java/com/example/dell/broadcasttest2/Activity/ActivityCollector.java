package com.example.dell.broadcasttest2.activity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by DELL on 2017/5/6.
 */
  //当在项目中觉得Activity过多不好管理时，写一个ActivityCollector进行管理
public class ActivityCollector{
    //创建一个ActivityCollector类
    private static List<Activity> activities = new ArrayList<>();//声明一个静态的Activity集合，用来存储创建的Activity
    public static void addActivity(Activity activity)
    {
        activities.add(activity);//把传入的Activity添加到List中
    }
    public static void removeActivity(Activity activity)
    {
        activities.remove(activity);//根据传入的Activity来删除
    }
    public static void finishAll()
    {
        for (Activity activity : activities)
        {
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
