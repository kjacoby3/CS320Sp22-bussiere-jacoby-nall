<!DOCTYPE html>

<html>
	<head>
		<title>Create Account</title>
        <style type="text/css">
            .error{
                color: red
            }
            .title{
                text-align: center;
                width: 540px;
                font-size: 200%;
                color: blue;
            }

        </style>
	</head>


    

	<body>
        <div class="title">Welcome to the best Sci Fi TBAG <br> 
            Create an account or login if you already have an account</div>
        <c:if test="${! empty errorMessage}">
			<div class = error>${errorMessage}</div>
		</c:if>

		<form action="${pageContext.servletContext.contextPath}/createAccount" method="post">

			<table>
                <tr>
                    <td class="label">Username:</td>
                    <td><input type="text" name="username" size="12" value="${username}" /></td>
                </tr>
                <tr>
                    <td class="label">Password:</td>
                    <td><input type="password" name="password" size="12" value="${password}" /></td>
                </tr>
                <tr>
                    <td class ="label">Confirm Password:</td>
                    <td><input type ="password" name ="confirmPassword" size="12" value="${confirmPassword}"/></td>
                </tr>
            
            </table>

            <input type="Submit" name="submit" value="Create Account">
		</form>

        <form action = "${pageContext.servletContext.contextPath}/login" method="get">
            <br>
            <div>If you already have an Account:<br></div>
            <input type="Submit" name="login" value="login">

        </form>
	</body>
</html>