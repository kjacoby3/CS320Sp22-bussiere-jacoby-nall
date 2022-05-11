<!DOCTYPE html>

<html>

    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="js/app-ajax.js" type="text/javascript"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <style>
        body {font-family: Arial, Helvetica, sans-serif;}
        

        .gridContainer{
            display: grid;
            width: 100%;
            grid-gap:1rem;
            grid-template-columns: 1fr 1fr 1fr;
            grid-template-rows: repeat(15, 50px);
            align-items: center;
            vertical-align: center;
            justify-items: center;
        }
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
          background-color:transparent;
          color: white;
          padding: 14px 20px;
          margin: 8px 0;
          border: none;
          cursor: pointer;
          width: 100%;
          font-size:200%;
        }
        
        button:hover {
          opacity: 0.8;
        }
        
        /* Extra styles for the cancel button */
        .cancelButton {
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

          background-color:black;
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
        #space{
            grid-row:1;
            grid-column: 1/-1;
        }
       
        #title{
            grid-row:2 / 3;
            grid-column:1 / -1;
            font-size:400%;
            color:white;
        }

        #login{
            grid-row: 4;
            grid-column:2;
        }
        #createAccount{
            grid-row: 5;
            grid-column: 2;
        }
        #loadGameID{
            grid-row: 6;
            grid-column: 2;
        }
        #startNewGame{
            grid-row: 7;
            grid-column: 2;
        }
        #goToGame{
            grid-row: 8;
            grid-column: 2;
        }
        body{
            background-color:rgba(0,0,0,0.95);
            background-image: url("https://i.pinimg.com/originals/d0/99/fb/d099fbe1334992232264f479a516983e.jpg");
            background-repeat:no-repeat;
            background-size:cover;
        }
        #serverResponse{
            color:white;
            grid-row:9;
            grid-column: 2;
        }
        #space2{
            grid-row:10;
            grid-column: 1/-1;
        }

        #failureMessage{
            color:red;
            font-size:125%;
        }

        #failureCreateMessage{
            color: #ff0000;
            font-size:125%;
        }
        
        </style>
        </head>
        <body>
        <div class="gridContainer">
            <div id="space"></div>
            <div id="title">Science Fiction TBAG</div>
            <button onclick="document.getElementById('id01').style.display='block'" id="login">Login</button>
            <button onclick="document.getElementById('id02').style.display='block'" id="createAccount">Create Account</button>
            <!--<button onclick="document.getElementById('id03').style.display='block'" id="loadGame">Load Game</button>-->
            <form action="${pageContext.servletContext.contextPath}/game" method = get id="loadGameID"><button type="submit" id="loadGame">Load Game</button></form>
            <form action="${pageContext.servletContext.contextPath}/game" method = get id="startNewGame"><button type= "submit" id="startNewGame">Create New Game</button></form>
            <form action="/TBAG/game" method="get" id="goToGame">

            <button type="submit" id ="goToGame">go to game</button>
            <div id="space2"></div>
            <div id="serverResponse"></div>
        </form>
        </div>
        




        <div id="id01" class="modal">
          
            <form action="${pageContext.servletContext.contextPath}/loginAjax" method="post" id="loginID">
            <div class="container">
              <label for="username"><b>Username</b></label>
              <input type="text" id="username" placeholder="Enter Username" name="username" required>
        
              <label for="password"><b>Password</b></label>
              <input type="password" id = "password" placeholder="Enter Password" name="password" required>
                
              <button type="submit" style="color:black">Login</button>

              <div id="resultID"></div>
              <div id="failureMessage"></div>
            </div>
        
            <div class="container" style="background-color:#f1f1f1">
              <button type="button" onclick="document.getElementById('id01').style.display='none'" id="cancelButton" class="cancelButton">Cancel</button>
            </div>
            </form>
        </div>




        <div id="id02" class="modal">
          
            <form action="${pageContext.servletContext.contextPath}/createAccountAjax" method="post" id="createAccountID">
            <div class="container">
              <label for="username"><b>Username</b></label>
              <input type="text" id="createUsername" placeholder="Enter Username" name="createUsername" required>
        
              <label for="password"><b>Password</b></label>
              <input type="password" id = "createPassword" placeholder="Enter Password" name="createPassword" required>

              <label for="confirmPassword"><b>Confirm Password</b></label>
              <input type="password" id = "confirmPassword" placeholder="Enter Password" name="confirmPassword" required>
                
              <button type="submit" style="color: black">CreateAccount</button>

              <div id="resultID"></div>
              <div id="failureCreateMessage"></div>
            </div>
        
            <div class="container" style="background-color:#f1f1f1">
              <button type="button" onclick="document.getElementById('id02').style.display='none'" id="cancelCreateButton" class="cancelButton">Cancel</button>
              <!--<span class="psw">Forgot <a href="#">password?</a></span>-->
            </div>
            </form>
        </div>

        <div id="id03" class="modal">
            
            <form action="${pageContext.servletContext.contextPath}/game" method="get" id="createGameID">
            <div class="container">
              <label for="playerName"><b>Choose a name for your Character</b></label>
              <input type="text" id="playerName" placeholder="Enter Character Name" name="playerName" value="${playerName}" required>
                
                
              <button type="submit" style="color: black">Create New Game</button>

              <div id="resultID"></div>
              <div id="failureCreateMessage"></div>
            </div>
        
            <div class="container" style="background-color:#f1f1f1">
              <button type="button" onclick="document.getElementById('id02').style.display='none'" id="cancelCreateButton" class="cancelButton">Cancel</button>
              <!--<span class="psw">Forgot <a href="#">password?</a></span>-->
            </div>
            </form>
        </div>





        
        <script>

            $(function(){
                var username;
                $("#loginID").on("submit",function(e){
                    e.preventDefault();
                    $.post(this.action, {username: $("#username").val(), password: $("#password").val()},
                    function(data){
                        //$("#resultID").html("Payment Successful");
                        alert(data);
                        if(data === "failure"){
                            $("#failureMessage").text("Incorrect Username or Password");
                        }
                        else{
                            username = data;
                            $("#cancelButton").click();
                            $('#serverResponse').text("You are currently logged in as: " + data);
                        }

                    }
                    );
                });

                $("#createAccountID").on("submit",function(e){
                    e.preventDefault();

                    if($("#createPassword").val()== $("#confirmPassword").val()){
                        $.post(this.action, {username: $("#createUsername").val(), password: $("#createPassword").val(), confirmPassword: $("#confirmPassword").val()},
                        function(data){
                            //$("#resultID").html("Payment Successful");
                            alert(data);
                            /*if(data === "match"){
                                $("#failureCreateMessage").text("Username and Password do not Match");
                            }
                            else{*/
                                //$("#cancelCreateButton").click();
                            username = data;
                            $('#id02').hide();
                            $('#serverResponse').text("You are currently logged in as: " + data);
                            //}
                        }
                        );
                    }
                    else{
                        $("#failureCreateMessage").text("Passwords do not Match");
                        $("#confirmPassword").css("borderColor", "#ff0000");
                    }
                });

                $("#loadGameID").on("submit",function(e){
                   /* e.preventDefault();
                    $.post(this.action,
                    function(data){
                        //$("#resultID").html("Payment Successful");
                        //alert(data);
                        if(data === "match"){
                           // $("#failureCreateMessage").text("Username and Password do not Match");
                        }
                        else{
                            //$("#cancelCreateButton").click();
                            //$('#id02').hide();
                            //$('#serverResponse').text("You are currently logged in as: " + data);
                        }
                    }
                    );*/
                });

                $("#startNewGame").on("submit",function(e){
                    if(username==null){
                        e.preventDefault();
                        alert("You must be logged in to start the game");
                    }
                })
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
