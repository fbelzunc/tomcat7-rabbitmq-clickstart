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

<a href="/rabitmq/configuration">Launch RabbitMQ server</a>
</br>
<a href="/rabitmq/client">Launch RabbitMQ client</a>


<form action="${pageContext.request.contextPath}/rabitmq/configuration" method="post">

    <div>
        <label>Message:</label>
        <input type="text" name="message" id="message"/>
    </div>

    <div>
        <input type="submit" name="add" value="Add new product"/>
    </div>
</form>


<table border="1">
    <thead>
    <tr>
        <th>Message</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${message}" var="product">
        <tr>
            <td>${message}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>