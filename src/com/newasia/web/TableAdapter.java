package com.newasia.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class TableAdapter
{
    private ArrayList<Head> headList = new ArrayList<>();
    private ArrayList<ArrayList<String>> dataList = new ArrayList<>();
    private Map<Integer,ArrayList<Head>> headRowMap = new HashMap<Integer,ArrayList<Head>>(); //对头部按层级分组
    private String title = "";
    private String time = "";
    private int HeadSpanRow = 0;//标题占几行
    private int nColumns = 0;//一共有多少列

    public TableAdapter(String ptitle,String ptime,ArrayList<Head> heads,ArrayList<ArrayList<String>> datas)
    {
        title = ptitle;
        time =  ptime;
        dataList = datas;
        headList = heads;
        nColumns = calcColumns();
    }


    public String title()
    {
        return title;
    }

    public String time()
    {
        return time;
    }



    public  int headSpanRows()
    {
        return HeadSpanRow;
    }

    public int columns()
    {
        return nColumns;
    }


    public Map<Integer,ArrayList<Head>> headMap()
    {
        return headRowMap;
    }

    /*
     返回
     */
    public int rows()
    {
        return dataList.size()+headSpanRows();
    }

    public ArrayList<String> getRow(int nRow)
    {
        if(nRow<dataList.size())
        {
            return dataList.get(nRow);
        }
        else
        {
            return new ArrayList<>();
        }
    }


    public int calcColumns()
    {
        HeadSpanRow = 0;
        int ret = 0;
        for(Head h:headList)
        {
            if(h.childs.size()>0)
            {
                ret += calcChiled(h,2);
            }
            else
            {
                ++ret;
            }
        }

        if(headList.size()>0)//放入第一级的头部元素
        {
            headRowMap.put(1,headList);
        }

        if(HeadSpanRow ==0 && headList.size()>0)
        {
            HeadSpanRow = 1;
        }

        return ret;
    }

    //递归统计子标题 通过传递tier统计标题需要占几行
    private int calcChiled(Head h,int tier)
    {
        int ret = 0;
        int curTier = tier;

        if(HeadSpanRow<curTier)
        {
            HeadSpanRow = curTier;
        }

        if(!headRowMap.containsKey(curTier))
        {
            headRowMap.put(curTier,new ArrayList<Head>());
        }


        ArrayList<Head> list = h.childs;
        for(Head head:list)
        {
            if(head.childs.size()>0)
            {
                ret += calcChiled(head,tier+1);
            }
            else
            {
                ++ret;
            }

            headRowMap.get(curTier).add(head);
        }
        return ret;
    }



    public static int calcColumnDeep(Head head)
    {
        int ret = 0;
        if(head.childs.size()>0)
        {
            for(Head h:head.childs)
            {
                ret += calcColumnDeep(h);
            }
        }
        else
        {
            ret =1;
        }
        return ret;

    }




    public static class Head
    {
        public Head(String pName)
        {
            name = pName;
        }
        public String name;
        public ArrayList<Head> childs = new ArrayList<>();
    }


}


