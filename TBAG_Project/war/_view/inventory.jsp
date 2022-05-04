<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <head>
        <title>Text Based Adventure Game</title>


<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
* {box-sizing: border-box}
body {
    background-color: hsl(257, 95%, 8%);
    font-family: "Lato", sans-serif;
    min-height:600px;
}

/* Style the tab */
.tab {
  float: left;
  border: 1px solid #ccc;
  background-color: hsl(0, 0%, 2%);
  width: 30%;
  height: 80vh;
  min-height:400px;
}
.header{
    text-align: center;
    font-weight:bold;
    font-size: 300%;
    color:white;
}

/* Style the buttons inside the tab */
.tab button {
  display: block;
  background-color: hsl(0, 0%, 2%);
  color: white;
  padding: 22px 16px;
  width: 100%;
  border: none;
  outline: none;
  text-align: left;
  cursor: pointer;
  transition: 0.3s;
  font-size: 17px;
}

.submit{
  display: block;
  background-color: inherit;
  color: white;
  padding: 22px 16px;
  width: 10vh;
  border: none;
  outline: none;
  text-align: center;
  cursor: pointer;
  transition: 0.3s;
  font-size: 17px;
}

.submit button:hover{
    background-color: hsl(0, 0%, 20%);
}

/* Change background color of buttons on hover */
.tab button:hover {
  background-color: hsl(0, 0%, 20%);
}

/* Create an active/current "tab button" class */
.tab button.active {
  background-color: hsl(0, 0%, 40%);
}

/* Style the tab content */
.tabcontent {
    background-color: black;
    display:grid;
    grid-template-columns: auto, auto;
    padding: 0px 12px;
    border: 1px solid #ccc;
    min-height:400px;
    overflow-y:scroll;
    height: 80vh;
}
.hide{
    display: none;
    text-align: center;
}

.item:hover + .hide{
    display: block;
    color: white;
    font-weight: bold;
    font-size: 100%;
    text-align: center;
}

.item{
    text-align: left;
    background-color: black;
    color:white;
    font-style:oblique;
    font-weight:bolder;
    font-size: 200%;
}
</style>
</head>
<body>

<h2 class="header">Inventory</h2>

<div class="tab">
  <button class="tablinks" onclick="openInventory(event, 'Weapons')" id="defaultOpen">Weapons</button>
  <button class="tablinks" onclick="openInventory(event, 'Equipment')">Equipment</button>
  <button class="tablinks" onclick="openInventory(event, 'Trophies')">Trophies</button>
  <button class="tablinks" onclick="openInventory(event, 'Usables')">Usables</button>
  <button class="tablinks" onclick="openInventory(event, 'Treasures')">Treasures</button>
  <button class="tablinks" onclick="openInventory(event, 'Consumables')">Consumables</button>
</div>

<div id="Weapons" class="tabcontent">
    <h3 style="text-align: center; color:white;">Weapons</h3>
    
    <p><c:forEach var="weapon" items="${weapons}">
        <c:if test="${! empty weapons}">
        <div class="ite">${weapon.name}</div>
        <div class="hide" >
            Damage: ${weapon.damage}<br>
            Price: ${weapon.sellPrice}
        </div>
    </c:if>
    </c:forEach></p>
</div>

<div id="Equipment" class="tabcontent">
    <h3 style="text-align: center; color:white;">Equipment</h3>
    
    <p><c:forEach var="equip" items="${equipment}">
        <c:if test="${! empty equip}">
            <div class="item">${equip.name}</div>
            <div class="hide" >
                Price: ${equip.price}<br>
                Defense Modifier: ${equip.defenseMod}<br>
                HP Modifier: ${equip.HPMod}<br>
                Speed Modifier: ${equip.speedMod}<br>
                Sell Price: ${equip.sellPrice}
            </div>
        </c:if>
        </c:forEach></p>
  </div>

<div id="Trophies" class="tabcontent">
    <h3 style="text-align: center;color:white;">Trophies</h3>
    
    <p><c:forEach var="trophy" items="${trophies}">
        <c:if test="${! empty trophy}">
        <div class="item">${trophy.name}</div>
        <div class="hide" >
            Price: ${trophy.sellPrice}
        </div>
        </c:if>
    </c:forEach></p>
    
</div>

<div id="Usables" class="tabcontent">
    <h3 style="text-align: center;color:white;">Usables</h3>
    
    <p><c:forEach var="usable" items="${usables}">
        <c:if test="${! empty usables}">
        <div class="item">${usable.name}</div>
        <div class="hide" >
            Price: ${usable.sellPrice}
        </div>
    </c:if>
    </c:forEach></p>
</div>


<div id="Treasures" class="tabcontent">
    <h3 style="text-align: center;color: white;">Treasures</h3>
    <!--<c:if test="! empty treasures">-->
    <c:forEach var="treasure" items="${treasures}">
        <div class="item">${treasure.name}</div>
        <div class="hide" >
            HP Modifier: ${treasure.HPMod}<br>
            Defense Modifier: ${treasure.defMod}<br>
            Speed Modifier: ${treasure.spdMod}<br>
            Damage Modifier: ${treasure.dmgMod}
        </div>
    </c:forEach>
    <!--</c:if>-->
</div>

<div id="Consumables" class="tabcontent">
    <h3 style="text-align: center; color: white;">Consumables</h3>
    <c:if test="! empty consumables">
    <p><c:forEach var="consumable" items="${consumables}">
        <div class="item">${consumable.name}</div>
        <div class="hide" >
            Healing: ${consumable.curHPMod}<br>
            Temporary Max Health Increase: ${consumable.maxHPMod}<br>
            Damage Modifier: ${consumable.dmgMod}<br>
            Defense Modifier: ${consumable.defMod}<br>
            Speed Modifier: ${consumable.spdMod}<br>
            Price: ${consumable.sellPrice}
        </div>
    </c:forEach></p>
    </c:if>
</div>
<form action="${pageContext.servletContext.contextPath}/game" method="post">

<input type="Submit" name="back" value="Back" class="submit">
</form>

<script>
function openInventory(evt, inventorySection) {
  var i, tabcontent, tablinks;
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }
  document.getElementById(inventorySection).style.display = "block";
  evt.currentTarget.className += " active";
}

// Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();
</script>
   
</body>
</html> 


</html>