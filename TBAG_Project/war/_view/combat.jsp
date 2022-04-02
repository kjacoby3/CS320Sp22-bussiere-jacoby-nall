<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
            .buttons input{
                background-color: #808080;
                float: center;
                border: none;
                outline: none;
                cursor: pointer;
                padding: 5px;

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
                    <td class="label">${result1} </td>
				</tr>
				<tr>
					<td class="label">Damage: ${enemyDMG} </td>
					<td class="label">Damage: ${playerDMG} </td>
                    <td class="label">${result2} </td>
				</tr>
                <tr>
                    <td class="label">Defense: ${enemyDEF}</td>
                    <td class="label">Defense: ${playerDEF}</td>
                    <td class="label">${result3} </td>
                </tr>
                <tr>
                    <td class="label">Speed: ${enemySPD}</td>
                    <td class="label">Speed: ${playerSPD}</td>
                </tr>
                <tr>
                    <td class="label">Weapon: ${enemyWeapon}</td>
                    <td class="label">Weapon: ${playerWeapon}</td>
                </tr>
                <tr>
                    <td></td>
                    <td class="label">Level: ${playerLVL}</td>
                </tr>
                <tr>
                    <td></td>
                    <td class="label">XP: ${playerEXP}/${playerMaxEXP}</td>
                </tr>
				
		</table><br><br><br><br>
        <div class="buttons">
            <form style="text-align:center" action="${pageContext.servletContext.contextPath}/combat" method="post">
                <c:if test="${empty finished}">
                    <input style="height: 100px; width: 100px;" type="Submit" name="attack" value="Attack">
                    <input style="height: 100px; width: 100px;" type="Submit" name="run" value="Run">
                    <input style="height: 100px; width: 100px;" type="Submit" name="items" value="Items">
                </c:if>
            </form>
            <c:if test="${! empty finished}">
                <form style="text-align:center" action="${pageContext.servletContext.contextPath}/game" method="post">
                    <input style="height: 100px; width: 100px;" type="Submit" name="back" value="Back"> </input>
                </form>
            </c:if>
        </div>
        <div class="error">${error}</div>


    </body>

</html>