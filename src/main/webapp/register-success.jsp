<html>
<h1> Welcome to Patient Portal at Childrens Nationals</h1>

<body>
 <%
   String info = request.getAttribute("status").toString();
   out.println(info);
 %>
 
<a href="/patient-portal/login.jsp">Login</a>

</body>
</html>
