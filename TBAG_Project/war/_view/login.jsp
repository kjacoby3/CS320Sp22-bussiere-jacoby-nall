<!DOCTYPE html>

<html>
	<head>
		<title>Login</title>
	</head>


    

	<body>
        <c:if test="${! empty errorMessage}">
			<div>${errorMessage}</div>
		</c:if>

		<form action="login" method="post">

			<table>
                <tr>
                    <td class="label">Username:</td>
                    <td><input type="text" name="username" size="12" value="${username}" /></td>
                </tr>
                <tr>
                    <td class="label">Password:</td>
                    <td><input type="text" name="password" size="12" value="${password}" /></td>
                </tr>
            
            </table>

            <input type="Submit" name="submit" value="Login">
		</form>
	</body>
</html>