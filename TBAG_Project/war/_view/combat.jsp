<!DOCTYPE html>
    <head>
        <title>Text Based Adventure Game</title>
        <style type = "text/css">
            .title{
                text-align: center;
                width: 540px;
                font-size: 250%;
                font-variant: small-caps;
                color:red;

            }
            .heading{
                text-align: left;
                width: 540px;
                font-size: 200%;
                font-variant: small-caps;

            }

            .body{
                text-align: left;
                width: 540px;
                font-size: 150%;
            }



        </style>
        

    </head>
    <body>
        <div class="title">Combat</div>

        <div class="heading">Enemy</div><br>
         <div class="body">${enemy}</div>
            <table>
                <tr>
                    <td class="label">Health: </td> <br>
                    <td class="label">${enemyHealth} </td>
                </tr>
            
        </table>

        </div>

        <div class="heading">Player</div><br>
         <div class="body">${player}</div>
            <table>
                <tr>
                    <td class="label">Health: </td> <br>
                    <td class="label">${playerHealth} </td>
                </tr>
            
        </table>

        </div>

        <form action="${pageContext.servletContext.contextPath}/combat" method="post">

            <input type="Submit" name="attack" value="Attack">
            <input type="Submit" name="run" value="Run">
        </form>
        <div class="error">${errorMessage}</div>


    </body>

</html>