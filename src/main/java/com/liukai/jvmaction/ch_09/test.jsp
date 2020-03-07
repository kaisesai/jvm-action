<%@ page contentType="text/html;charset=UTF-8" %>
<%--这里使用 utf8编码，防止class 文件中的中文输出会乱码--%>
<%@ page import="com.liukai.web.*" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.InputStream" %>

<%
    // 执行这个目标 class 文件
    InputStream is = new FileInputStream(
            "/Users/liukai/IdeaProjects/myproject/jvm-action/build/classes/java/main/com/liukai/jvmaction/ch_09/HelloMyClass.class");
    byte[] b = new byte[is.available()];
    is.read(b);
    is.close();

    out.println("<textarea style='width:1000;height=800'>");
    out.println(JavaClassExecuter.execute(b));
    out.println("</textarea>");
%>
