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
        .projected{
            text-align: left;
            font-size: 150%;
            font-family: Rockwell;
            color: green;
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
        table input{
            background-color: #D3D3D3;
            float: center;
            border: none;
            outline: none;
            cursor: pointer;
            padding: 0px;
			vertical-align: bottom;
        }
    </style>
    

</head>
<body>
    <div class="title">Level Up</div>

    <table style="width:100%">
        <th style="width:20%"> ${player}</th>
        <th style="width:35%"> Level: ${level}
			<class id="levelInc" class="projected" style="font-size:100%"> </class>
        </th>
        <th style="width:45%">XP: ${curXP}/${maxXP}
			<class id="xpInc" class="projected" style="font-size:100%"> </class>
		</th>
            
            <tr>
                <td class="body">Health: </td>
                <td class="body">${curHP}/${maxHP}
                    <input id="hpPlus" style="height:30px; width:30px;" type="image" name="incHP" src="plusIcon.jpg"
					action="${pageContext.servletContext.contextPath}/levelUp" method="post">
					<class id="hpInc" class="projected" style="font-size:100%"></class>
                </td>
				<td class="body">Health: <class id="projHP" class="projected" style="font-size:100%; color:black">${curHP}/${maxHP} </class></td>
                
                
            </tr>

			<tr>
				<td class="body">Damage: </td>
				<td class="body">${dmg}
                    <input id="dmgPlus" style="height:30px; width:30px;" type="image" name="incDMG" src="plusIcon.jpg" 
					action="${pageContext.servletContext.contextPath}/levelUp" method="post">
					<class id="dmgInc" class="projected" style="font-size:100%"></class>
                </td>
				<td class="body">Damage: <class id="projDMG" class="projected" style="font-size:100%; color:black">${dmg} </class></td>
			</tr>
			
			<tr>
				<td class="body">Defense: </td>
				<td class="body">${def}
                    <form action="${pageContext.servletContext.contextPath}/levelUp" method="post">
                        <input id="defPlus" style="height:30px; width:30px;" type="image" name="incDEF" src="plusIcon.jpg"
					    action="${pageContext.servletContext.contextPath}/levelUp" method="post">
					    <class id="defInc" class="projected" style="font-size:100%"></class>
                    </form>
                </td>
				<td class="body">Defense: <class id="projDEF" class="projected" style="font-size:100%; color:black">${def} </class></td>
			</tr>
			
			<tr>
				<td class="body">Speed: </td>
				<td class="body">${spd}
                    <input id="spdPlus" style="height:30px; width:30px;" type="image" name="incSPD" src="plusIcon.jpg"
					action="${pageContext.servletContext.contextPath}/levelUp" method="post">
					<class id="spdInc" class="projected" style="font-size:100%"></class>
                </td>
				<td class="body">Speed: <class id="projSPD" class="projected" style="font-size:100%; color:black">${spd} </class></td>
			</tr>
    </table>

    <div> ${result} </div>

<script>
document.getElementById("hpPlus").addEventListener("mouseover", projHPOn);
document.getElementById("hpPlus").addEventListener("mouseout", projHPOff);

document.getElementById("dmgPlus").addEventListener("mouseover", projDMGOn);
document.getElementById("dmgPlus").addEventListener("mouseout", projDMGOff);

document.getElementById("defPlus").addEventListener("mouseover", projDEFOn);
document.getElementById("defPlus").addEventListener("mouseout", projDEFOff);

document.getElementById("spdPlus").addEventListener("mouseover", projSPDOn);
document.getElementById("spdPlus").addEventListener("mouseout", projSPDOff);

	function projHPOn() {
		document.getElementById("projHP").innerHTML = "${projCurHP}/${projMaxHP}";
		document.getElementById("projHP").style.color = "green";
		document.getElementById("hpInc").innerHTML = "${hpInc}";
		document.getElementById("levelInc").innerHTML = "+ 1 ${projLvl}"
		document.getElementById("xpInc").innerHTML = "${projCurXP}/${projMaxXP}"
	}
	function projHPOff() {
		document.getElementById("projHP").innerHTML = "${curHP}/${maxHP}";
		document.getElementById("projHP").style.color = "black";
		document.getElementById("hpInc").innerHTML = "";
		document.getElementById("levelInc").innerHTML = ""
		document.getElementById("xpInc").innerHTML = ""
	}

	function projDMGOn() {
		document.getElementById("projDMG").innerHTML = "${projDMG}";
		document.getElementById("projDMG").style.color = "green";
		document.getElementById("dmgInc").innerHTML = "${dmgInc}";
		document.getElementById("levelInc").innerHTML = "+ 1 ${projLvl}"
		document.getElementById("xpInc").innerHTML = "${projCurXP}/${projMaxXP}"
	}
	function projDMGOff() {
		document.getElementById("projDMG").innerHTML = "${dmg}";
		document.getElementById("projDMG").style.color = "black";
		document.getElementById("dmgInc").innerHTML = "";
		document.getElementById("levelInc").innerHTML = ""
		document.getElementById("xpInc").innerHTML = ""
	}	
	
	function projDEFOn() {
		document.getElementById("projDEF").innerHTML = "${projDEF}";
		document.getElementById("projDEF").style.color = "green";
		document.getElementById("defInc").innerHTML = "${defInc}";
		document.getElementById("levelInc").innerHTML = "+ 1 ${projLvl}"
		document.getElementById("xpInc").innerHTML = "${projCurXP}/${projMaxXP}"
	}
	function projDEFOff() {
		document.getElementById("projDEF").innerHTML = "${def}";
		document.getElementById("projDEF").style.color = "black";
		document.getElementById("defInc").innerHTML = "";
		document.getElementById("levelInc").innerHTML = ""
		document.getElementById("xpInc").innerHTML = ""
	}
	
	function projSPDOn() {
		document.getElementById("projSPD").innerHTML = "${projSPD}";
		document.getElementById("projSPD").style.color = "green";
		document.getElementById("spdInc").innerHTML = "${spdInc}";
		document.getElementById("levelInc").innerHTML = "+ 1 ${projLvl}"
		document.getElementById("xpInc").innerHTML = "${projCurXP}/${projMaxXP}"
	}
	function projSPDOff() {
		document.getElementById("projSPD").innerHTML = "${spd}";
		document.getElementById("projSPD").style.color = "black";
		document.getElementById("spdInc").innerHTML = "";
		document.getElementById("levelInc").innerHTML = ""
		document.getElementById("xpInc").innerHTML = ""
	}
	
	
</script>

</body>