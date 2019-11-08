<%@ page language="java" import="java.util.*,java.sql.*,
sop.dao.domain.*,sop.service.*,sop.service.impl.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>选择用户类型</title>
</head>
<body>
<form action="${pageContext.request.contextPath }/RegisterUIServlet" method="post">
									<input type="radio" name="type" value="0" checked="checked" /><label >接包方</label>
									<input type="radio" name="type" value="1" /><label >发包方</label>
									<input type="submit" value="选择" />
</form>
</body>
</html>