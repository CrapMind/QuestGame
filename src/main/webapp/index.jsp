<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>QUEST GAME</title>
    <!-- jQuery library for AJAX and DOM manipulation -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="icon" type="image/x-icon" href="favicon.ico">
    <link href="css/main.css" rel="stylesheet">
</head>
<body>
<!-- Main game screen background -->
<div id="gameScreen">
</div>

<!-- Player statistics panel -->
<div id="gameStat" style="display: none">
    <p><strong>Player:</strong> <span id="playerNameStat" class="stat-value"></span></p>
    <p><strong>Correct Moves:</strong> <span id="correctMoves" class="stat-value"></span></p>
    <p><strong>Wrong Moves:</strong> <span id="wrongMoves" class="stat-value"></span></p>
</div>

<!-- Text display area for scene narration -->
<div id="displayText">
</div>

<!-- Player name input screen -->
<div id="introContainer" style="display: none">
    <div id="nameInputContainer">
        <label for="nameInput">Enter your name:</label>
        <input type="text" id="nameInput" placeholder="Your name">
        <span class="tooltip">[?]
            <span class="tooltipText">Only English letters, 1-8 characters, no spaces</span>
        </span>
        <button id="saveName">OK</button>
    </div>
</div>

<!-- Choice selection buttons -->
<div id="choicesContainer" style="display: none;">
    <span class="proceedButton" id="continueButton" onclick="handleChoice('CONTINUE')">[ CONTINUE ]</span>
    <span class="proceedButton" id= "quitButton" onclick="handleChoice('QUIT')">[ QUIT ]</span>
    <span class="proceedButton" id="restartButton" onclick="restartGame()">[ RESTART ]</span>
    <span class="proceedButton" id="newGameButton" onclick="newGame()">[ NEW GAME ]</span>
</div>

<!-- Action buttons for game choices -->
<div id="buttonsContainer">
    <span class="choiceButton" id="choiceButton1">[1]</span>
    <span class="choiceButton" id="choiceButton2">[2]</span>
    <span class="choiceButton" id="choiceButton3">[3]</span>
</div>

<script>
    const textContainer = document.getElementById('displayText');
    const BASE_URL = "http://localhost:8080";
    let globalScene = 1; // global variable for dynamic change scenes

    // Load welcome screen when page is loaded
    $(window).on('load', welcomeScreen);

    // Displays winning text if the player reaches the final scene
    function typeWinnerText() {
        typeText("CONGRATULATIONS! You won!", "QUIT");
    }

    // Updates player statistics panel
    function displayStats(response) {
        let $gameStat = $("#gameStat");

        $("#playerNameStat").fadeOut(150, function() {
            $(this).text(response.playerName).fadeIn(150);
        });

        $("#correctMoves").fadeOut(150, function() {
            $(this).text(response.correctMoves).fadeIn(150);
        });
        $("#wrongMoves").fadeOut(150, function() {
            $(this).text(response.wrongMoves).fadeIn(150);
        });

        if (!$gameStat.is(":visible")) {
            $gameStat.fadeIn(300);
        }
    }

    // Displays welcome message and player name input field
    function welcomeScreen() {
        typeText("Welcome to the QUEST GAME!", "NONE");
        setTimeout(function () {
            $('#introContainer').fadeIn();
        }, 1000);
        $("#saveName").click(setPlayerName);
        $("#nameInput").keypress(function (event) {
            if (event.which === 13) {
                setPlayerName();
            }
        });
    }

    // Saves player's name and initializes the game session
    function setPlayerName() {
        let name = $("#nameInput").val().trim();
        let namePattern = /^[A-Za-z]{1,8}$/;

        if (!namePattern.test(name)) {
            alert("Name must be in English, 1-8 characters, and without spaces.");
            return;
        }

        localStorage.setItem("userName", name);
        $("#userName").text(name);
        $("#nameInputContainer").fadeOut();

        $.post(BASE_URL + "/rest/setPlayerName", { playerName: name });
        setTimeout(() => {
            typeText(`Okay, ` + name + `, let's play!`, "CONTINUE");
        }, 1000);
    }

    // Simulates typing effect for displayed text
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
                    displayButtons(condition);
                }, 500);
            }
        }
        type();
    }

    // Displays appropriate buttons based on game state
    function displayButtons(condition) {
        let $choicesContainer = $('#choicesContainer');
        let $buttonContainer = $('#buttonsContainer');
        let haveAWinner = globalScene >= 10;
        let $quitButton = $('#quitButton');
        let $restartButton = $('#restartButton');
        let $newGameButton = $('#newGameButton');
        switch (condition) {
            case 'CONTINUE':
                haveAWinner ? typeWinnerText() : loadScene(globalScene);
                break;
            case 'QUIT':
                $choicesContainer.show().find('.proceedButton').hide();
                $quitButton.show();
                $restartButton.show();
                $newGameButton.show();
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

    // Handles user selection for continue or quit
    function handleChoice(choice) {
        if (choice === "CONTINUE") {
            typeText("Okay, LET'S GO!", "CONTINUE");
        } else {
            typeText("Goodbye :(", "NONE");
            setTimeout(() => window.close(), 1000);
        }
    }

    // Loads scene details from the server
    function loadScene(scene) {
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

    // Handles player move and updates scene
    function makeMove(scene, choice) {
        $.get(BASE_URL + "/rest/move?scene=" + scene + "&choice=" + choice, function (response) {
            globalScene++;
            typeText(response.scene, response.moves ? "QUIT" : "CONTINUE");
            displayStats(response);
        }).fail(error => {
            typeText(error.status, error.responseText);
        });
    }
    // Starts a new game session
    function newGame() {
        $("#newGameButton, #restartButton, #quitButton").hide();
        $("#gameStat").hide();
        typeText(`READY TO A NEW GAME`, "NONE");
        globalScene = 1;

        $.get(BASE_URL + "/rest/newGame")
            .done(function () {
                $("#playerNameStat").text("");
                $("#correctMoves").text("0");
                $("#wrongMoves").text("0");
                setTimeout(() => location.reload(true), 1000);
            }).fail(error => {
            typeText(error.status, error.responseText);
        })
    }

    // Restarts the game session
    function restartGame() {
        typeText(`GAME WILL BE RESTARTED`, "NONE");
        globalScene = 1;
        setTimeout(() => {
            loadScene(globalScene)
        }, 1000);
    }

</script>

</body>