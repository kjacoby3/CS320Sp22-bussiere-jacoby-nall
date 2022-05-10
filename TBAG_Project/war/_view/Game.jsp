<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <head>
        <title>Text Based Adventure Game</title>

        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script src="https://unpkg.com/jquery.terminal/js/jquery.terminal.min.js"></script>
        <script src="js/app-ajax.js" type="text/javascript"></script>
        <link rel="stylesheet" href="Style.css"/>

        
        
        
        <style>
            #equipped{
                grid-column:1/2;
                display:grid;
                grid-template-columns: 1fr;
                grid-template-rows: repeat(16, 50px);
                align-items: center;
                vertical-align: center;
                justify-items: center;
            }
            #playerDetails{
                grid-column:8/10;
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

            }
            #weapon{
                vertical-align: center;
                justify-items: top;
                grid-row:4;
                font-size:150%;
                color:hsl(208, 100%, 48%);
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
                color:hsl(208, 100%, 48%);
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
        </style>
    </head>
    <body class="gridContainer">
            <div id = "equipped">
                <div id="sectionTitle">Equipped Items</div>
                <div id="weapon">Equipped Weapon: Sword</div>
                <div id="stats1">Stats</div>
                <div id="weaponDamage">Damage: 150</div>
                <div id="weaponPrice">Price: 750</div>
                <div id="equipment">Equipped Equipment: Cloth Armor</div>
                <div id="stats2">Stats</div>
                <div id="equipmentDefenseMod">150 Armor</div>
                <div id="equipmentHPMod">+150 HP</div>
                <div id="equipmentSpeedMod">+10 Speed</div>
                <div id="equipmentPrice">Price: 1000</div>
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