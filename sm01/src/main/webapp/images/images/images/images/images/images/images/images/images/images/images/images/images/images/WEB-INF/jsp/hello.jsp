<%@ page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>hello</title></head>
<body>
<%--<h1>Hello,${sessionScope.name}!</h1>--%>
<%--<h1>Hello,${requestScope.msg}!</h1>--%>
<h2>hello,${sessionScope.user.username}</h2>
<div>
    <p>${CRUDmsg}</p>
    <a href="${pageContext.request.contextPath}/user/welcome.do">返回欢迎页面</a>
</div>

<div>
    <form action="${pageContext.request.contextPath}/user/upload.do" enctype="multipart/form-data" method="post">
        上传头像:<input type="file" name="image"><br>
                <input type="submit" value="上传"><br>
    </form>
</div>
</body>

</html>
