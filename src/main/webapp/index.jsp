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


    $(document).ready(function () {
        typeText("Welcome to the QUEST GAME!", "CHOICE");
    });

    function typeText(text, condition) {
        let $displayText = $('#displayText');

        $('#choicesContainer, #buttonsContainer').hide();
        let index = 0;
        $displayText.html("");

        function type() {
            if (index < text.length) {
                $displayText.append(text.charAt(index));
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
        let $choicesContainer = $('#choicesContainer');
        let $buttonContainer = $('#buttonsContainer');
        switch (condition) {
            case 'CONTINUE':
                loadScene(globalScene);
                break;
            case 'QUIT':
                $choicesContainer.show().find('.proceedButton').hide();
                $('#quitButton').show();
                break;
            case 'CHOICE':
                $choicesContainer.show();
                break;
            case 'BUTTONS':
                let $choicesButton3 = $('#choiceButton3');
                $buttonContainer.show();
                $choicesButton3.toggle($choicesButton3.text() !== "[]");
                break;
            case 'NONE':
                $buttonContainer.hide();
                $choicesContainer.hide();
                break;
        }
    }

    function handleChoice(choice) {
        if (choice === "CONTINUE") {
            typeText("Okay, LET'S GO!", "CONTINUE");
        } else {
            typeText("Goodbye :(", "NONE");
            setTimeout(() => window.close(), 1000);
        }
    }

    function loadScene(scene) {
        console.log(scene);
        $.get(BASE_URL + "/rest/scene?scene=" + scene, function (response) {
            typeText(response.text, "BUTTONS");

            response.choices.forEach((choice, i) => {
                $('#choiceButton' + (i + 1))
                    .text("[" + choice + "]")
                    .off()
                    .click(() => makeMove(scene, choice));
            });
        }).fail(error => {
            typeText(error.status, error.responseText);
        });
    }

    function makeMove(scene, choice) {
        if (globalScene > 10) {
            typeText("CONGRATULATIONS! You won!", "QUIT");
            return;
        }
        $.get(BASE_URL + "/rest/move?scene=" + scene + "&choice=" + choice, function (response) {
            globalScene++;
            typeText(response.scene, response.choice === "right" ? "CONTINUE" : "QUIT");
        }).fail(error => {
            typeText(error.status, error.responseText);
        });
    }

</script>

</body>