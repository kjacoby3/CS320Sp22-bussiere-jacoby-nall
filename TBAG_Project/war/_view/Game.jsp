<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <head>
        <title>Text Based Adventure Game</title>
        <style type = "text/css">
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

            .history{
                color: black;
                font-size: 100%;

            }

        </style>
        
        
        
        </style>
    </head>
    <body>
        <c:if test="${empty roomMessage}">
            <div class="roomMessage">Welcome to the Game! <br> Press The Button to Begin</div>
            <form action="${pageContext.servletContext.contextPath}/game" method="get">
                <input type="Submit" name = "enter" value ="Begin!">
            </form>
        </c:if>

        <c:if test="${! empty roomMessage}">
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

        </c:if>

        
        


    </body>

</html>