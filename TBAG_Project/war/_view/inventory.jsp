<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <head>
        <title>Text Based Adventure Game</title>


<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
* {box-sizing: border-box}
body {font-family: "Lato", sans-serif;}

/* Style the tab */
.tab {
  float: left;
  border: 1px solid #ccc;
  background-color: #f1f1f1;
  width: 30%;
  height: 390px;
}
.header{
    text-align: center;
    font-weight:bold;
    font-size: 300%;
}

/* Style the buttons inside the tab */
.tab button {
  display: block;
  background-color: #ccc;
  color: black;
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
  color: black;
  padding: 22px 16px;
  width: 100%;
  border: none;
  outline: none;
  text-align: left;
  cursor: pointer;
  transition: 0.3s;
  font-size: 17px;
}

.submit button:hover{
    background-color: #ddd;
}

/* Change background color of buttons on hover */
.tab button:hover {
  background-color: #ddd;
}

/* Create an active/current "tab button" class */
.tab button.active {
  background-color: #ccc;
}

/* Style the tab content */
.tabcontent {
  float: left;
  padding: 0px 12px;
  border: 1px solid #ccc;
  width: 70%;
  border-left: none;
  height: 390px;
}
.hide{
    display: none;
    text-align: center;
}

.weapon:hover + .hide{
    display: block;
    color: red;
    font-weight: bold;
    font-size: 100%;
    text-align: center;
}

.weapon{
    text-align: left;
    background-color: white;
    color:blue;
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
    <h3 style="text-align: center;">Weapons</h3>
    
    <p><c:forEach var="weapon" items="${weapons}">
        <c:if test="${! empty weapons}">
        <div class="weapon">${weapon.name}</div>
        <div class="hide" >
            Damage: ${weapon.damage}<br>
            Price: ${weapon.sellPrice}
        </div>
    </c:if>
    </c:forEach></p>
</div>

<div id="Equipment" class="tabcontent">
    <h3 style="text-align: center;">Weapons</h3>
    
    <p><c:forEach var="equip" items="${equipment}">
        <c:if test="${! empty equip}">
            <div class="weapon">${equip.name}</div>
            <div class="hide" >
                Defense Modifier: ${equip.defenseMod}<br>
                HP Modifier: ${equip.HPMod}<br>
                Speed Modifier: ${equip.speedMod}<br>
                Sell Price: ${equip.sellPrice}
            </div>
        </c:if>
        </c:forEach></p>
  </div>

<div id="Trophies" class="tabcontent">
    <h3 style="text-align: center;">Trophies</h3>
    
    <p><c:forEach var="trophy" items="${trophies}">
        <c:if test="${! empty trophy}">
        <div class="weapon">${trophy.name}</div>
        <div class="hide" >
            Price: ${trophy.sellPrice}
        </div>
        </c:if>
    </c:forEach></p>
    
</div>

<div id="Usables" class="tabcontent">
    <h3 style="text-align: center;">Usables</h3>
    
    <p><c:forEach var="usable" items="${usables}">
        <c:if test="${! empty usables}">
        <div class="weapon">${usable.name}</div>
        <div class="hide" >
            Price: ${usable.sellPrice}
        </div>
    </c:if>
    </c:forEach></p>
</div>


<div id="Treasures" class="tabcontent">
    <h3 style="text-align: center;">Treasures</h3>
    <!--<c:if test="! empty treasures">-->
    <c:forEach var="treasure" items="${treasures}">
        <div class="weapon">${treasure.name}</div>
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
    <h3 style="text-align: center;">Consumables</h3>
    <c:if test="! empty consumables">
    <p><c:forEach var="consumable" items="${consumables}">
        <div class="weapon">${consumable.name}</div>
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