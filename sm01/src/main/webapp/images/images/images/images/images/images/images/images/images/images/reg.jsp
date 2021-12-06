<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <form method="post" action="${pageContext.request.contextPath}/param2/reg.do">
        账号<input name="username"/><br>
        密码<input name="pwd"/><br>
        生日<input name="birthday"/><br>
        城市<input name="address.city"/><br>
        街道<input name="address.street"/><br>
        电话<input name="address.phone"/><br>
        <input type="submit" value="reg"/>
    </form>
</body>
</html>
