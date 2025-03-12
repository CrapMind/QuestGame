<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>QUEST GAME</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="icon" type="image/x-icon" href="favicon.ico">
    <link href="css/main.css" rel="stylesheet">
</head>
<body>

<div id="gameScreen">
</div>
<div id="displayText">
</div>
<div id="choicesContainer" style="display: none;">
    <span class="proceedButton" id="continueButton" onclick="handleChoice('CONTINUE')">[ CONTINUE ]</span>
    <span class="proceedButton" id= "quitButton" onclick="handleChoice('QUIT')">[ QUIT ]</span>
</div>
<div id="buttonsContainer">
    <span class="choiceButton" id="choiceButton1">[1]</span>
    <span class="choiceButton" id="choiceButton2">[2]</span>
    <span class="choiceButton" id="choiceButton3">[3]</span>
</div>

<script>
    const textContainer = document.getElementById('displayText');
    const BASE_URL = "http://localhost:8080";
    let globalScene = 1;


    document.addEventListener("DOMContentLoaded", function () {
        typeText("Welcome to the QUEST GAME!", "CHOICE");
    });

    function typeText(text, condition) {
        $('#choicesContainer').hide();
        $('#buttonsContainer').hide();
        let index = 0;
        textContainer.innerHTML = "";

        function type() {
            if (index < text.length) {
                textContainer.innerHTML += text.charAt(index);
                index++;
                setTimeout(type, 30);
            } else {
                setTimeout(() => {
                    displayButtons(condition)
                }, 500);
            }
        }
        type();
    }

    function displayButtons(condition) {

        switch (condition) {
            case 'CONTINUE': {
                loadScene(globalScene);
                break;
            }
            case 'QUIT': {
                $('#choicesContainer').show().find('.proceedButton').hide();
                $('#quitButton').show();
                break;}
            case 'CHOICE': {
                $('#choicesContainer').show();
                break;}
            case 'BUTTONS': {
                $('#buttonsContainer').show();
                let $button3 = $('#choiceButton3');
                $button3.text() === "[]" ? $button3.hide() : $button3.show();
                break;
            }
            case 'NONE': {
                $('#buttonsContainer', '#choicesContainer').hide();
                break;
            }
        }
    }

    function handleChoice(choice) {
        if (choice === "CONTINUE") {
            typeText("Okay, LET'S GO!", "CONTINUE");
        } else {
            typeText("Goodbye :(", "NONE");
            setTimeout(() => {
                window.close();
            }, 1000);

        }
    }

    function loadScene(scene) {
        console.log(scene);
        $.ajax({
            type: 'GET',
            url: BASE_URL + "/rest/scene?scene=" + scene,
            contentType: 'application/json',
        }).done(response => {
            typeText(response.text, "BUTTONS");

            let choices = response.choices;
            for (let i = 0; i < choices.length; i++) {
                $('#choiceButton' + (i + 1))
                    .text("[" + choices[i] + "]")
                    .off()
                    .click(() => makeMove(scene, choices[i]));
            }
        }).fail(error => {
            typeText(error.status, error.responseText);
        })
    }

    function makeMove(scene, choice) {
        if (globalScene > 10) {
            typeText("CONGRATULATIONS! You won!", "QUIT");
            return;
        }
        $.ajax({
            type: 'GET',
            url: BASE_URL + "/rest/move?scene=" + scene + "&choice=" + choice,
            contentType: 'application/json',
        }).done(response => {
            globalScene++;
            typeText(response.scene, response.choice === "right" ? "CONTINUE" : "QUIT");
        }).fail(error => {
            typeText(error.status, error.responseText);
        });
    }
</script>

</body>