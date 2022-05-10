<!DOCTYPE html>

<html>
	<!--<head>
		<title>Login</title>\
        <link rel="stylesheet" href="Style.css">
        <style type="text/css">
            .error{
                color: red
            }
            .title{
                text-align: center;
                width: 100vw;
                font-size: 200%;
                color: white;
            }
        </style>
	</head>


    

	<body>
        
        <div class="title">Text Based Adventure Game</div>
        <div class = "gridContainer">
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
        </div>
	</body>-->
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="js/app-ajax.js" type="text/javascript"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <style>
        body {font-family: Arial, Helvetica, sans-serif;}
        
        /* Full-width input fields */
        input[type=text], input[type=password] {
          width: 100%;
          padding: 12px 20px;
          margin: 8px 0;
          display: inline-block;
          border: 1px solid #ccc;
          box-sizing: border-box;
        }
        
        /* Set a style for all buttons */
        button {
          background-color: #04AA6D;
          color: white;
          padding: 14px 20px;
          margin: 8px 0;
          border: none;
          cursor: pointer;
          width: 100%;
        }
        
        button:hover {
          opacity: 0.8;
        }
        
        /* Extra styles for the cancel button */
        #cancelButton {
          width: auto;
          padding: 10px 18px;
          background-color: #f44336;
        }
        
        
        .container {
            background-color:white;
          padding: 16px;
        }
        
        span.psw {
          float: right;
          padding-top: 16px;
        }
        
        /* The Modal (background) */
        .modal {

          display: none; /* Hidden by default */
          position: fixed; /* Stay in place */
          z-index: 1; /* Sit on top */
          left: 0;
          top: 0;
          width: 100%; /* Full width */
          height: 100%; /* Full height */
          overflow: auto; /* Enable scroll if needed */
          background-color: rgb(0,0,0); /* Fallback color */
          background-color: rgba(0,0,0,0.4); /* Black w/ opacity */

          padding-top: 60px;
        }
        
        /* Modal Content/Box */
        .modal-content {
          background-color: #fefefe;
          margin: 5% auto 15% auto; /* 5% from the top, 15% from the bottom and centered */
          border: 1px solid #888;
          width: 80%; /* Could be more or less, depending on screen size */
        }
        
        #id01{
            background-color: rgba(0,0,0,0.4);
        }
        /* The Close Button (x) */
        .close {
          position: absolute;
          right: 25px;
          top: 0;
          color: #000;
          font-size: 35px;
          font-weight: bold;
        }
        
        .close:hover,
        .close:focus {
          color: red;
          cursor: pointer;
        }
        
        </style>
        </head>
        <body>
        
        <h2>Modal Login Form</h2>
        
        <button onclick="document.getElementById('id01').style.display='block'" style="width:auto;">Login</button>
        
        <div id="serverResponse"></div>
        <div id="id01" class="modal">
          
            <form action="${pageContext.servletContext.contextPath}/loginAjax" method="post" id="formID">
            <div class="container">
              <label for="uname"><b>Username</b></label>
              <input type="text" id="username" placeholder="Enter Username" name="username" required>
        
              <label for="psw"><b>Password</b></label>
              <input type="password" id = "password" placeholder="Enter Password" name="password" required>
                
              <button type="submit">Login</button>

              <div id="resultID"></div>
            </div>
        
            <div class="container" style="background-color:#f1f1f1">
              <button type="button" onclick="document.getElementById('id01').style.display='none'" id="cancelButton">Cancel</button>
              <span class="psw">Forgot <a href="#">password?</a></span>
            </div>
            </form>
        </div>
        <form action="/TBAG/game" method="get">

            <button type="submit">go to game</button>
        </form>
        
        <script>

            $(function(){
                $("#formID").on("submit",function(e){
                    e.preventDefault();
                    $.post(this.action, {username: $("#username").val(), password: $("#password").val()},
                    function(data){
                        //$("#resultID").html("Payment Successful");
                        if(data === "failure"){
                            $("#failureMessage").text("Incorrect Username or Password");
                        }
                        else{
                            $("#cancelButton").click();
                            $('#serverResponse').text("Hello " + data + ", Welcome to the game you have been successfully logged in");
                        }

                    }
                    );
                });
            });
            /*$("#form").submit(function (event) {
                event.preventDefault();

                var $form = $(this),
                user = $form.find("input[name='username']").val(),
                pass = $form.find("input[name='password']").val(),
                url = $form.attr("action");

                var posting = $.post(url, {username: user, password: pass});

                posting.done(function(data){
                    alert("failure");
                    if(data==="failure"){
                    $('failureMessage').text("Incorrect Username or Password");
                }
                else{
                    alert("success");
                    document.getElementById("cancelButton").click();
                    $('#serverResponse').text("Hello " + responseText + ", Welcome to the game you have been successfully logged in");
                }
                });
            });*/


        // Get the modal
        /*var modal = document.getElementById('id01');
        
        // When the user clicks anywhere outside of the modal, close it
        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }*/
        /*function loginFunction(){
            alert ("function callsed")
            $.ajax({
            
            url: 'localhost:8081/loginAjax',
            type: post,
            data: {
                username : $('#username').val(),
                password : $('#password').val()
            },
            success : function(responseText){
                if(responseText==="failure"){
                    $('failureMessage').text("Incorrect Username or Password")
                }
                else{
                    document.getElementById("cancelButton").click();
                    $('#serverResponse').text("Hello " + responseText + ", Welcome to the game you have been successfully logged in")
                }
            }

        })
        }*/
        /*function loginFunction(){
            $.post('localhost:8081/loginAjax'), {
                username: $('#username'),
                password: $('#password')
            },
            function(response){
                alert("success");
                if(response==="failure"){
                    $('failureMessage').text("Incorrect Username or Password")
                }
                else{
                    document.getElementById("cancelButton").click();
                    $('#serverResponse').text("Hello " + responseText + ", Welcome to the game you have been successfully logged in")
                }
            }
        }*/
        
        </script>
        
        </body>

</html>
