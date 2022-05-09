<!DOCTYPE html>

<html>
	<head>
		<title>Login</title>\
        <link rel="stylesheet" href="Style.css">
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


    

	<body class="gridContainer">
        <div class="title">Text Based Adventure Game</div>
        <c:if test="${! empty errorMessage}">
			<div class = error>${errorMessage}</div>
		</c:if>

		<form action="login" method="post">

			<table>
                <tr>
                    <td class="label">Username:</td>
                    <td><input type="text" name="username" size="12" value="${username}" /></td>
                </tr>
                <tr>
                    <td class="label">Password:</td>
                    <td><input type="password" name="password" size="12" value="${password}" /></td>
                </tr>
            
            </table>

            <input type="Submit" name="submit" value="Login">
		</form>
	</body>
</html>