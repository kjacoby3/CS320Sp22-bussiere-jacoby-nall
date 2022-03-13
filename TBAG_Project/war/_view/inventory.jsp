<!DOCTYPE html>
    <head>
        <title>Text Based Adventure Game</title>
    </head>
    <body>
        <div class="label">${roomMessage}</div>
        
        <form action="game" method="post">
            <table>
                <tr>
                    <td class="label">Command</td>
                    <td><input type ="text" name = "userCommand" size="12" value="${command}"></td>
                </tr>
                
            </table>
        
            <input type="Submit" name="submit" value = "Enter">
        </form>


    </body>

</html>