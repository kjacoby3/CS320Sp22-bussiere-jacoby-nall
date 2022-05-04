<!DOCTYPE html>

<html>
	<head>
		<title>Create Account</title>
        <link rel="stylesheet" href="Style.css">
        
	</head>


    

	<body class="body" style="background-color: black;">
        <div class="text">Welcome to the best Sci Fi TBAG <br> 
            Create an account or login if you already have an account</div>
        <c:if test="${! empty errorMessage}">
			<div class = error>${errorMessage}</div>
		</c:if>

		<form action="${pageContext.servletContext.contextPath}/createAccount" method="post">

			<table>
                <tr>
                    <td class="text">Username:</td>
                    <td><input type="text" name="username" size="12" value="${username}" /></td>
                </tr>
                <tr>
                    <td class="text">Password:</td>
                    <td><input type="password" name="password" size="12" value="${password}" /></td>
                </tr>
                <tr>
                    <td class ="text">Confirm Password:</td>
                    <td><input type ="password" name ="confirmPassword" size="12" value="${confirmPassword}"/></td>
                </tr>
            
            </table>

            <input class = "submit" type="Submit" name="submit" value="Create Account">
		</form>

        <form action = "${pageContext.servletContext.contextPath}/login" method="get">
            <br>
            <div class = "text">If you already have an Account:<br></div>
            <input class = "submit" type="Submit" name="login" value="login">

        </form>
	</body>
</html>