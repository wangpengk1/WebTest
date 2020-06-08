package com.newasia.web;

import com.newasia.web.Util.RandomGUID;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.PageContext;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class JspTestBean {
    JspWriter out = null;
    private PageContext mContex = null;
    private TableAdapter mAdapter = null;
    private String id = "";
    public JspTestBean(PageContext context,TableAdapter adapter)
    {
        mContex = context;
        mAdapter = adapter;
        out = mContex.getOut();
        id = new RandomGUID().toString();
    }

    public String getId()
    {
        return id;
    }

    public void refresh() throws IOException
    {
        String strTable = MessageFormat.format("<table id=\"{0}\" class=\"xTable\">",id);
        out.println(strTable);
        if(mAdapter!=null)
        {
            int cols = mAdapter.columns();
            out.println("<tr>");
            out.println("<td align=\"center\" class=\"xtitle\" height=\"60\" colspan='"+cols+"'>");
            out.append(mAdapter.title());
            out.append("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td class=\"xtime\" align=\"right\" height=\"25\" colspan='"+cols+"'>");
            out.append(mAdapter.time());
            out.append("</td>");
            out.println("</tr>");
            refreshHead();
            refreshData();
        }
        out.println("</table>");
    }



    public void refreshHead() throws IOException
    {
        if(mAdapter!=null)
        {
            int cols = mAdapter.columns();
            int rowSpan = mAdapter.headSpanRows();
            Map<Integer, ArrayList<TableAdapter.Head>> headMap = mAdapter.headMap();
            for(int i=0;i<headMap.size();++i)
            {
                ArrayList<TableAdapter.Head> list = headMap.get(i+1);
                System.out.println("第"+i+"行开始");
                int rowCount = rowSpan-i;
                out.println("<tr class='xHead xHead%1'>".replaceAll("%1",i+""));
                for(TableAdapter.Head head:list)
                {
                    int colDeep = TableAdapter.calcColumnDeep(head);
                    int rowDeep = head.childs.size()>0?1:rowCount;
                    System.out.println(head.name+"col deep:"+colDeep+"  rowDeep:"+rowDeep);

                    out.println("<td class='xHeadCell xHeadCell%3' rowspan='%1' colspan='%2' >".replaceAll("%1",rowDeep+"")
                            .replaceAll("%2",colDeep+"").replaceAll("%3",i+""));
                    out.println(head.name);
                    out.println("</td>");
                }
                out.println("</tr>");
            }

        }
    }


    public void refreshData() throws IOException
    {
        if(mAdapter!=null)
        {
            int cols = mAdapter.columns();
            for(int i=0;i<mAdapter.rows();++i)
            {
                out.println("<tr class='xDataRow  xDataRow%1'>".replaceAll("%1",i+""));
                ArrayList<String> rowData = mAdapter.getRow(i);
                for(int j=0;j<cols;++j)
                {
                    out.println("<td  class='xDataCell xDataCell_row%1_col%2'>".replaceAll("%1",i+"").replaceAll("%2",j+""));
                    if(j<rowData.size())
                    {
                        out.println(rowData.get(j));
                    }
                    else
                    {
                        out.println("&nbsp;");
                    }
                    out.println("</td>");
                }
                out.println("</tr>");
            }
        }
    }
}
