<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<html>
<head>
    <base href="<%=basePath%>">
</head>
<body>

    $.ajax({
        url:"workbench/xxx/xx.do",
        data:{},
        dataType:"json",
        success:function (data) {

        },
        type:"post"
    })

</body>
</html>
