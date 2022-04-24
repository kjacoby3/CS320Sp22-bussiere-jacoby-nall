<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <head>
        <title>Text Based Adventure Game</title>

        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script src="https://unpkg.com/jquery.terminal/js/jquery.terminal.min.js"></script>
        <script src="js/app-ajax.js" type="text/javascript"></script>
        <link rel="stylesheet" href="https://unpkg.com/jquery.terminal/css/jquery.terminal.min.css"/>

        <style type = "text/css">
            .grid-container{
                display: grid;
                grid-template-columns: 50vh auto 50vh;
                gap: 10px;
                background-color: #11013b;
                width:auto;
                height:auto;
                color: white;
                padding:10px;
            }

            .grid-container > div{
                background-color: black;
                font-size:30px;
                height:96vh;
                padding-bottom:10px;
            }
            

            .roomMessage {
                text-align: center;
                width: 540px;
                font-size: 200%;
                color: red;
            }

            .normal{
                text-align: right;
                font-size: 150%;
            }
            .error{
                color: red;
                font-weight: bold;
                
            }

            input[type=text]{
                width: 30vh;
                padding-left: 40px;
            }
            


            .history{
                color: black;
                font-size: 100%;
                background-color: #11013b;

            }


            .terminal{
                background-color: black;
                text-align:left;
                overflow: scroll;
                font-size: 100%;
            }
            .script{
                border-style:solid;
                overflow-y: scroll;
                color: white;
                width: auto;
                height: 80VH;

                border-width: medium;
                border-color: white;
                padding: 20px;
                margin: 20px;
                background-color: black;
            }

        </style>
        
        
        
        </style>
    </head>
    <body class="grid-container">
        <!--<c:if test="${empty roomMessage}">
            <div class="roomMessage">Welcome to the Game! <br> Press The Button to Begin</div>
            <form action="${pageContext.servletContext.contextPath}/game" method="get">
                <input type="Submit" name = "enter" value ="Begin!">
            </form>
        </c:if>-->

        <!--<c:if test="${! empty roomMessage}">
            <div class="roomMessage">${roomMessage}</div>
            
        
            <form action="${pageContext.servletContext.contextPath}/game" method="post">
                <table>
                    <tr>
                        <td class="label">User Input: </td> <br>
                        <td><input type ="text" name = "command" size="12" value="${command}"></td>
                    </tr>
                    
                </table>
                <div class="error">${errorMessage}</div>
            
                <input type="Submit" name="submit" value = "Enter">
            </form>

            <div class = "history"> Input History:<br>
                ${sessionScope['commandHistory']}
            </div>

        </c:if>-->
    
            <div class = "equipped">

            </div>
            <div class = "terminal">
                <div>
                <form action="${pageContext.servletContext.contextPath}/game" method="post">
                    <input type ="text" name = "command" size="12" value="${command}">
                <input type="Submit" name="submit" value = "Enter">
                </div>


                <div class = "script">
                    <c:forEach items = "${sessionScope['history']}" var= "input">
                        ${input}<br>

                    </c:forEach></div>
            </form>

            </div>
            <div class = "playerDetails"></div>
            





        
        

        <script>
            
            /*term = $(".terminal").terminal(function(input){
                $.post('http://localhost:8081/TBAG/game', { 
                    command: input
                        
                }, function(roomMessage) {
                    
                    if(roomMessage == 'inventory'){
                        window.location.assign("http://localhost:8081/TBAG/inventory");
                    }
                    else{
                        term.echo(roomMessage)
                    }
                    
                })
            },{
                greetings: 'Hello'
            }
            );*/
            </script>
    </body>
    
</html>