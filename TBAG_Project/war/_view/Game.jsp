<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <head>
        <title>Text Based Adventure Game</title>

        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script src="https://unpkg.com/jquery.terminal/js/jquery.terminal.min.js"></script>
        <link rel="stylesheet" href="https://unpkg.com/jquery.terminal/css/jquery.terminal.min.css"/>
        <script src="js/app-ajax.js" type="text/javascript"></script>
        <link rel="stylesheet" href="Style.css"/>

        
        
        
        
        </style>
    </head>
    <body class="gridContainer">
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
                <!--<div>
                <form action="${pageContext.servletContext.contextPath}/game" method="post">
                    <input type ="text" name = "command" size="12" value="${command}">
                <input type="Submit" name="submit" value = "Enter">
                </div>


                <div class = "script">
                    <c:forEach items = "${sessionScope['history']}" var= "input">
                        ${input}<br>

                    </c:forEach></div>
            </form>-->

            </div>
            <div class = "playerDetails"></div>
            





        
        

        <script>
            $(document).ready(function(){
            term = $(".terminal").terminal(function(input){
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
                greetings: getAjax('http://localhost:8081/TBAG/game').done(function(response){
                    return response
                })
            
        })

        function getAjax(url){
            return $.ajax({
                type:'get',
                url : url,
                data : null,
                success: function(response){
            }
        }
            </script>
    </body>
    
</html>