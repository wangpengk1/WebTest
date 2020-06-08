function AjaxPost(pUrl,pData,back) {
    ajaxInstance = AjaxFactory.getInstance().post(pUrl,pData,back);
}


var AjaxFactory =
{
    getInstance:function()
    {
        var ajax = {xmlhttp:null,back:Object};
        ajax.post = function(pUrl,pData,toBack)
        {
            this.back = toBack;
            this.getXmlHttp();
            if(this.xmlhttp==null)
            {
                alert("your brower not support XmlHttpRequset");
                return;
            }
            this.xmlhttp.open("POST", pUrl, true);
            //定义传输的文件HTTP头信息
            this.xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
            //发送POST数据
            this.xmlhttp.send(pData);
            this.xmlhttp.onreadystatechange = this.onReadStateChanged;
        }
        ajax.onReadStateChanged = function()
        {
            if(ajax.xmlhttp.readyState==4 && ajax.xmlhttp.status==200)
            {
                if(ajax.xmlhttp.response != null && ajax.back!=null)
                {
                    ajax.back(ajax.xmlhttp.response);
                }
            }
            else
            {
                console.log("ajax request fault");
            }
        }


        ajax.getXmlHttp = function ()
        {
            if(window.XMLHttpRequest)
            { //Mozilla 浏览器
                this.xmlhttp = new XMLHttpRequest();
                if (this.xmlhttp.overrideMimeType)
                { //设置MiME类别
                    this.xmlhttp.overrideMimeType("text/xml");
                }
            }
            else if (window.ActiveXObject)
            { // IE浏览器
                try
                {
                    this.xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
                }
                catch (e)
                {
                    try
                    {
                        this.xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                    catch (e) {}
                }
            }
            else
            {
                this.xmlhttp = null;
            }
        }


        return ajax;
    }
}
