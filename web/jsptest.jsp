<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/7 0007
  Time: 19:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"
         import="java.text.SimpleDateFormat,java.util.Date,java.io.IOException,java.io.PrintWriter"  %>
<%@ page import="jakarta.servlet.jsp.JspWriter,com.newasia.web.JspTestBean,com.newasia.web.TableAdapter,
com.newasia.web.Util.DateUtil" %>
<%@ page import="java.util.ArrayList" %>
<%!
    private int initVar=0;
    private int serviceVar=0;
    private int destroyVar=0;
%>

<%!
    public void jspInit(){
        initVar++;
        System.out.println("jspInit(): JSP被初始化了"+initVar+"次");
    }
    public void jspDestroy(){
        destroyVar++;
        System.out.println("jspDestroy(): JSP被销毁了"+destroyVar+"次");
    }
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.js"></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="css/XTable.css" type="text/css" rel="stylesheet">
</head>
<%--<%!--%>
<%--    protected String tableTitle(PageContext context)--%>
<%--    {--%>
<%--        ServletRequest req = context.getRequest();--%>
<%--        if(req.getParameterMap().containsKey("title"))--%>
<%--        {--%>
<%--            return req.getParameter("title");--%>
<%--        }--%>
<%--        else--%>
<%--        {--%>
<%--            return "受理员业务统计表";--%>
<%--        }--%>
<%--    }--%>

<%--    protected String tableTime(PageContext context)--%>
<%--    {--%>
<%--        ServletRequest req = context.getRequest();--%>
<%--        if(req.getParameterMap().containsKey("tabletime"))--%>
<%--        {--%>
<%--            return req.getParameter("tabletime");--%>
<%--        }--%>
<%--        else--%>
<%--        {--%>
<%--            SimpleDateFormat format = new SimpleDateFormat("yyy年MM月dd日 HH:mm");--%>
<%--            return format.format(new Date(System.currentTimeMillis()));--%>
<%--        }--%>
<%--    }--%>

<%--    protected void createSection(PageContext context) throws IOException--%>
<%--    {--%>
<%--        JspWriter outer = context.getOut();--%>
<%--        for(int i=0;i<10;i++)--%>
<%--        {--%>
<%--            outer.println("<tr>");--%>
<%--            outer.append("  ");--%>
<%--            for(int j=0;j<10;++j)--%>
<%--            {--%>
<%--                outer.println("<td>");--%>
<%--                outer.append("row"+i+"column"+j);--%>
<%--                outer.append("</td>");--%>
<%--            }--%>
<%--            outer.println("  ");--%>
<%--            outer.append("</tr>");--%>

<%--        }--%>
<%--    }--%>
<%--%>--%>
<body>
<h1>菜鸟教程 JSP 测试实例</h1>
<%
    ArrayList<TableAdapter.Head> heads = new ArrayList<>();
    ArrayList<ArrayList<String>> dataList = new ArrayList<>();

    heads.add(new TableAdapter.Head("受理员"));
    heads.add(new TableAdapter.Head("受理数"));
    heads.add(new TableAdapter.Head("自办数"));
    heads.add(new TableAdapter.Head("直接解答"));

    TableAdapter.Head head = new TableAdapter.Head("协办意见");
    head.childs.add(new TableAdapter.Head("同意"));
    head.childs.add(new TableAdapter.Head("比例"));
    heads.add(head);

    head = new TableAdapter.Head("返回修改");
    head.childs.add(new TableAdapter.Head("数量"));
    head.childs.add(new TableAdapter.Head("比例"));
    heads.add(head);

    head = new TableAdapter.Head("工单类型");

    TableAdapter.Head head2 = new TableAdapter.Head("建议件");
    head2.childs.add(new TableAdapter.Head("件1"));
    head2.childs.add(new TableAdapter.Head("件2"));
    head.childs.add(head2);
//    head.childs.add(new TableAdapter.Head("建议件"));
    head.childs.add(new TableAdapter.Head("诉求件"));
    head.childs.add(new TableAdapter.Head("自寻件"));
    heads.add(head);


    for(int i=0;i<10;++i)
    {
        ArrayList<String> rowData = new ArrayList<>();
        dataList.add(rowData);
    }



    TableAdapter adapter = new TableAdapter("受理员业务统计表",DateUtil.curDate("yyy年MM月dd日 HH:mm"),heads,dataList);
    JspTestBean bean = new JspTestBean(pageContext,adapter);
    bean.refresh();
    String tb_id = bean.getId();
%>

<script>
    $(".xDataCell").click(function () {
        this.onclick = onTablCellClick(this);
    });


    function onTextAreaBlur(elmnt)
    {
        let text = elmnt.value;
        let clist = elmnt.classList;
        $(elmnt).replaceWith(function () {
            let strRet = "<td onclick=\"onTablCellClick(this)\" class=\"%1 %2\" >%3</td>";
            return strRet.replace("%1",clist[0]).replace("%2",clist[1]).replace("%3",text);
        })
    }


    function onTablCellClick(e) {
        let clist = e.classList;
        let text = e.innerHTML;
        let parent = e.parentNode;
        let textArea = document.createElement("textarea");
        if($(".xTable").get()[0].ActiveCell != null)
        {
            onTextAreaBlur($(".xTable").get()[0].ActiveCell);
        }
        textArea.setAttribute("rows","1");
        textArea.setAttribute("wrap","hard");
        textArea.setAttribute("class","%1 %2".replace("%1",clist[0]).replace("%2",clist[1]));
        textArea.innerHTML = text;
        textArea.setAttribute("onBlur","onTextAreaBlur(this)")
        parent.replaceChild(textArea,e);
        textArea.focus();
        $(".xTable").get()[0].ActiveCell = textArea;
    }


</script>
</body>
</html>
