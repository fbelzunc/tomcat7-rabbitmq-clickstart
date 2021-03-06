<%@ page import="java.io.PrintWriter"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<title>Tomcat 7 RabbitMQ ClickStart</title>
</head>

<body>

    <h1>Tomcat 7 RabbitMQ ClickStart</h1>

    <%
        if (request.getAttribute("throwable") != null) {
            Throwable t = (Throwable) request.getAttribute("throwable");
    %><p>An exception occurred: <strong><code><%= t.getMessage() %></code></strong></p><%
        }
    %>

    <h2>Send a message</h2>
    <form action="${pageContext.request.contextPath}/rabbitmq/server"
        method="post">
        <div>
            <textarea name="message" id="message" style="width: 500px; height: 150px;"></textarea>
            <br /> <input type="submit" name="add" value="Send message" />
        </div>
    </form>

    <h2>Get a messages</h2>
    <form action="${pageContext.request.contextPath}/rabbitmq/client" method="post">
        <div>
            <input type="submit" name="Get" value="Get message" />
        </div>
    </form>
    <br/> 
    Messages available on the queue:
    <br/> 
    ${messages}
    
    <%
        if (request.getAttribute("throwable") != null) {
            Throwable t = (Throwable) request.getAttribute("throwable");
    %><h1>Detailed exception</h1>
    <code><pre><%
        t.printStackTrace(new PrintWriter(out));
    %></pre>
    </code><%
        }
    %>
</body>
</html>