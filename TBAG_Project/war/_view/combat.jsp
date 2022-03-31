<!DOCTYPE html>
    <head>
        <title>Text Based Adventure Game</title>
        <style type = "text/css">
            .title{
                text-align: center;
                font-family: Rockwell;
                font-size: 250%;
                font-variant: small-caps;
                color:red;

            }
            .heading{
                text-align: left;
                font-size: 200%;
				font-family: Rockwell;
                font-variant: small-caps;

            }

            .body{
                text-align: left;
                font-size: 150%;
				font-family: Rockwell;
            }
			th{
				text-align: left;
				padding-right: 20px;
				font-size: 200%;
				font-family: Rockwell;
			}
			td{
				padding-right: 20px;
				font-family: Rockwell;
			}
			input{
				width: 200px;
			}
			.label{
				font-family: Rockwell;
			}
			.error{
				font-family: Rockwell;
				color:red;
			}
        </style>
        

    </head>
    <body>
        <div class="title">Combat</div>

		<table style="width:100%">
			<th style="width:30%;"> Enemy: ${enemy}</th>
			<th style="width:30%"> Player: ${player}</th>
			<th style="width:40%;text-align:center"> Console</th>
				<tr>
					<td class="label">Health: ${enemyHealth} </td>
					<td class="label">Health: ${playerHealth} </td>
				</tr>
				<tr>
					<td class="label"> </td>
					<td class="label">Damage: 10 </td>
				</tr>
				
		</table><br><br><br><br><br><br><br>

        <form style="text-align:center" action="${pageContext.servletContext.contextPath}/combat" method="post">

            <input style="height: 100px; width: 100px;" type="Submit" name="attack" value="Attack">
            <input  style="height: 100px; width: 100px;" type="Submit" name="run" value="Run">
        </form>
        <div class="error">${error}</div>


    </body>

</html>