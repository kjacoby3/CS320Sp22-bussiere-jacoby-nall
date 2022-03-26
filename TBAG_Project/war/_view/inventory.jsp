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
        <div class="title">Inventory</div>

        <div class="heading">Weapons</div><br>
         <div class="body">${weapons}</div>

        <div class="heading">Equipment</div>
         <div class="body">${equipment}</div>

        <div class="heading">Urophies</div>
         <div class="body">${trophies}</div>

        <div class="heading">Usables</div>
         <div class="body">${usables}</div>

        <div class="heading">Treasures</div>
         <div class="body">${treasures}</div>

        </div>

        <form action="${pageContext.servletContext.contextPath}/game" method="post">

            <input type="Submit" name="back" value="Back">
        </form>


    </body>

</html>