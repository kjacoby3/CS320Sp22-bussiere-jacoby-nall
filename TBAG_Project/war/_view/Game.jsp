<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <head>
        <title>Text Based Adventure Game</title>

        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script src="https://unpkg.com/jquery.terminal/js/jquery.terminal.min.js"></script>
        <script src="js/app-ajax.js" type="text/javascript"></script>


        
        
        
        <style>
            body{
                background-color:#11013b;
            }
            .terminal{
                grid-column: 2;
                background-color: black;
                text-align:left;
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
            .grid{
                height:99vh;
                font-size: 150%;
                gap: 10px;
                display:grid;
                grid-template-columns: 3fr 4fr 3fr;
                grid-template-rows: 1fr;
                width:99vw;
                background-color: black;


            }
            #equipped{
                grid-column:1;
                display:grid;
                grid-template-columns: 1fr;
                grid-template-rows: repeat(16, 50px);
                align-items: center;
                vertical-align: center;
                justify-items: center;
            }
            #playerDetails{
                grid-column:3;
                display:grid;
                grid-template-columns: 1fr 1fr;
                grid-template-rows: repeat (20, 50px);
                align-items: center;
                vertical-align: center;
                justify-items: center;
            }
            #sectionTitle{
                grid-column: 1 / -1;
                grid-row:1/2;
                font-size:200%;
                color:white;
                vertical-align: center;
                text-align: center;
            }
            #weapon{
                vertical-align: center;
                justify-items: top;
                grid-row:4;
                font-size:150%;
                color:white;
            }
            #stats1{
                grid-row:5;
                color:hsl(208, 85%, 48%);
            }
            #weaponDamage{
                grid-row:6; 
                color:hsl(208, 85%, 48%);
            }
            #weaponPrice{
                grid-row:7;
                color:hsl(208, 85%, 48%);
            }
            #equipment{
                vertical-align: center;
                justify-items: top;
                grid-row:10;
                font-size:150%;
                color:white
            }
            #stats2{
                grid-row:11;
                color:hsl(208, 85%, 48%);
            }
            #equipmentName{
                grid-row: 12;
                color:hsl(208, 85%, 48%);
            }
            #equipmentPrice{
                grid-row: 12;
                color:hsl(208, 85%, 48%);
            }
            #equipmentDefenseMod{
                grid-row: 13;
                color:hsl(208, 85%, 48%);
            }
            #equipmentHPMod{
                grid-row: 14;
                color:hsl(208, 85%, 48%);
            }
            #equipmentSpeedMod{
                grid-row: 15;
                color:hsl(208, 85%, 48%);
            }
            #playerInfo{
                color:hsl(208, 85%, 48%);
                grid-row: 3/7;
                grid-column: 1/-1;
                border: 1px solid #fff;
                margin: 10px;
                font-size: 200%;
                text-align: left;
                margin-top: 40px;
            }
            #playerStats{
                
                margin: 10px;
                font-size: 200%;
                border: 1px solid #fff;
                color:hsl(208, 85%, 48%);
                padding: 10px;
                padding-top: 0%;

            }
            #location{
                color:hsl(208, 85%, 48%);
                text-align: center;
                padding:20px;
                font-size:150%;
            }
            #equipped > div{
                font-size:200%;
            }

        </style>
    </head>
    <body>
        <div class="grid">
            <div id = "equipped">
                <div id="sectionTitle">Equipped Items</div>
                <div id="weapon">Weapon: ${weapon.name}</div>
                <div id="weaponDamage">Damage: ${weapon.damage}</div>
                <div id="weaponPrice">Price: ${weapon.price}</div>
                <div id="equipment">Equipment: ${equipment.name}</div>
                <div id="equipmentDefenseMod">${equipment.defenseMod} Armor</div>
                <div id="equipmentHPMod">${equipment.HPMod} HP</div>
                <div id="equipmentSpeedMod">+${equipment.speedMod} Speed</div>
                <div id="equipmentPrice">Sell Price: ${equipment.sellPrice}</div>
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
            <div class = "playerDetails">

                <div id="sectionTitle">Player Information</div>
                <div id="location">Location: ${roomName}</div>
                <div id="playerInfo">Name: ${name}<br>
                <span style="text-align: left">Level ${playerStats.curLvl}</span><br>
                <span style="text-align: left"> XP:${playerStats.curExp}/${playerStats.maxExp} </span></div>
                <div id="playerStats"><div style="text-align: center;">Player Stats</div>
                    HP: ${playerStats.curHP}/${playerStats.maxHP}<br>
                    Damage: ${playerStats.dmg}<br>
                    Defense: ${playerStats.def}<br>
                    Speed: ${playerStats.spd}<br>
                </div>


            </div>
        </div>





        
        

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