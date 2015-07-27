<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Start page</title>
    </head>
    <body>
        <h1>Start page</h1>
        <p>Send some messages</p>

        <form action="MessageServlet" method="GET">
            Message: <textarea name="message" cols="50" rows="4">Default</textarea>
            <br />
            Count  : <input type="text" name="txtCount" value="10">
            <br />
            <input type="submit" value="Send" />
        </form>
    </body>
</html>
