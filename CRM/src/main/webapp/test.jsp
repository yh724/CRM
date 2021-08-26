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
        url:"/settings/user/login.do",
        data:{},
        dataType:"json",
        success:function () {

        }
        type:"post"
    })

</body>
</html>
