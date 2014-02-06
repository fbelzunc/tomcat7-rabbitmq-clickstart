<%@ page import="java.io.PrintWriter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tomcat 7 RabitMQ ClickStart</title>
</head>

<body>

<h1>Tomcat 7 RabitMQ ClickStart</h1>


<form action="${pageContext.request.contextPath}/rabbitmq/server" method="post">

    <div>
        <label>Message:</label>
        <input type="text" name="message" id="message"/>
        <input type="submit" name="add" value="Add new product"/>
    </div>
</form>

<form action="${pageContext.request.contextPath}/rabbitmq/client" method="post">

    <div>
        <input type="submit" name="Get" value="Get the messagr"/>
    </div>
</form>


<table border="1">
    <thead>
    <tr>
        <th>Message</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${message}" var="message">
        <tr>
            <td>${message}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>