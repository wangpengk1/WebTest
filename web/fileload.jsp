<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>文件上传demo</title>
    <style type="text/css">
        h1 {
            text-align: center;
            text-shadow: 2px 2px 2px #999;
        }

        a {
            text-decoration: none;
            position: relative;
            display: block;
            width: 100px;
            height: 30px;
            line-height: 30px;
            background: #EEE;
            border: 1px solid #999;
            text-align: center;
            background: #EEE;
        }

        input {
            position: absolute;
            border-radius: 10px;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            z-index: 999;
            opacity: 0;
        }

        #myDiv {
            margin: auto;
            width: 100px;
            height: 30px;
        }

        a:HOVER {
            background: red;
        }

        .fileList {
            width: 600px;
            height: 400px;
            overflow: auto;
            position: absolute;
            bottom: 20px;
            right: 20px;
            position: absolute;
            border: 1px solid blue;
            position: absolute;
        }

        ul {
            list-style: none;
        }

        ul:after {
            clear: both;
            content: "";
            display: block;
        }

        ul:nth-child(odd) {
            background: #677077;
            color: #fff;
        }

        li {
            float: left;
        }
    </style>

    

    <script>
        function showAllElement()
        {
            showChiled(document.getElementById("root"));
        }
        
        function showNode(element)
        {
            console.log("noteName:"+element.nodeName);
            console.log("noteValue:"+element.nodeValue);
            console.log("noteType:"+element.nodeType);
        }

        function showChiled(element)
        {
            console.log(element.nodeName+"----------------------begin")
            showNode(element);

            if(element.firstChild!=undefined && element.firstChild!=null)
            {
                console.log(element.nodeName+"frist----------------------begin");
                showNode(element.firstChild);
                if(element.firstChild.firstChild!=undefined && element.firstChild.firstChild!=null)
                {
                    showChiled(element.firstChild);
                }
                console.log(element.nodeName+"frist----------------------end");

                let sibility = element.firstChild.nextSibling;
                while (sibility!=undefined && sibility!=null)
                {
                    console.log(element.nodeName+"sibility----------------------begin");
                    showNode(sibility);
                    if(sibility.firstChild!=undefined && sibility.firstChild!=null)
                    {
                        showChiled(sibility.firstChild);
                    }
                    sibility = sibility.nextSibling;
                    console.log(element.nodeName+"sibility----------------------end");
                }

                console.log(element.nodeName+"----------------------end")
            }
        }
    </script>
</head>
<body id="root">
<h1>文件上传demo</h1>
<div id="myDiv">
    <a href="javascript:;" id="fileUpload">文件上传
        <input type="file" multiple onchange="fileChange(this)" />
    </a>
</div>
<div class="fileList" id="fileList">
    <ul>
        <li style="width: 30%">文件名</li>
        <li style="width: 68%">上传情况</li>
    </ul>
</div>

<button style="position: absolute;left: 50%;top: 50%"  onclick="showAllElement()"></button>
</body>
<script>
    function fileChange(fileInput) {
        var files = fileInput.files;
        var tempHtml = "";
        for (var i = 0; i < files.length; i++) {
            var file = files[i];
            tempHtml += "<ul>"
                + "<li style='width: 30%'>"
                + file.name
                + "</li>"
                + "<li style='width: 68%'><progress id='p"+i+"' max='100' value=''></progress><span id='sp"+i+"'></span></li></ul>";
            fileUpload(file, i);
        }
        document.getElementById("fileList").innerHTML += tempHtml;
    }
    function fileUpload(file, index) {
        var formData = new FormData();
        formData.append(file.name, file);
        var xhr = new XMLHttpRequest();
        var oldUploaded=0;
        var curUploaded=0;
        var total=0;
        var percent=0;
        xhr.upload.addEventListener("progress",function(event){
            var loaded=event.loaded;
            if(oldUploaded==0){
                total=event.total;
                oldUploaded=event.loaded;
            }
            curUploaded=event.loaded;
            percent=Math.round(event.loaded/event.total*100);
            document.getElementById("p"+index).value=percent;
        });
        var upSpeed=setInterval(function(){
            if(oldUploaded!=0){
                //得到的是一个byte值
                var result=curUploaded-oldUploaded;
                var leave=total-curUploaded;
                var label=document.getElementById("sp"+index);
                label.innerHTML=percent+"%&nbsp;&nbsp;"+Math.round(result/(1024*1024)*100)/100+"M/S&nbsp;&nbsp;剩余"+Math.round(leave/result)+"秒";
                if(total==oldUploaded&&result==0){
                    clearInterval(upSpeed);
                }
                oldUploaded=curUploaded;
            }
        },1000);
        xhr.open("post", "upload", true);
        xhr.send(formData);
    }
</script>
</html>