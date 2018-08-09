/* global createjs, BASE_URL, Promise */

require('yuki-createjs');
let Gamefield = require('./objects/Gamefield');
let ShipFactory = require('./factorys/ShipFactory');
let ShotFactory = require('./factorys/ShotFactory');
let GameZone = require('./objects/GameZone');
let HarbourZone = require('./objects/HarbourZone');
let webSocketHandler = require('./handler/WebSocketHandler');
let matchHandler = require('./handler/MatchHandler');
let Message = require('./objects/Message');
let utilHandler = require('./handler/UtilHandler');
let collisionHandler = require('./handler/CollisionHandler');


let gameZone;

const boxPixel = 40;

const boxCountXGameField = 10;
const boxCountYGameField = 10;
const gameFieldStartX = 4 * boxPixel;
const gameFieldStartY = 0;

const boxCountXHarbour = 3;
const boxCountYHarbour = 10;
const harbourStartX = 0;
const harbourStartY = 0;

const color = "#FFFFFF";

let gamefield = new Gamefield(gameFieldStartX, gameFieldStartY, boxPixel, boxCountXGameField, boxCountYGameField, color);
let ownGameField = undefined;
let ownGameZone = undefined;
let opponentGameField = undefined;
let opponentGameZone = undefined;
let countDownSeconds = 60;
let intervalId = undefined;
let ships = [];

let field = false;
let canvas = document.getElementById("canvas");
let harbour = new Gamefield(harbourStartX, harbourStartY, boxPixel, boxCountXHarbour, boxCountYHarbour, color, field);
gameZone = new HarbourZone(canvas, gamefield, harbour);

let shipfactory = new ShipFactory (gameZone, boxPixel);
let boxes = gamefield.getBoxes();

function init() {

    let friendlyPlayerCanvas = document.getElementById("friendlyPlayer");
    let enemyPlayerCanvas = document.getElementById("enemyPlayer");


    if (canvas){

        ships.push(shipfactory.createSubmarineClass(0, 8*boxPixel, 0, false, undefined));
        ships.push(shipfactory.createSubmarineClass(2*boxPixel, 4*boxPixel, 0, false, undefined));
        ships.push(shipfactory.createSubmarineClass(2*boxPixel, 6*boxPixel, 0, false, undefined));
        ships.push(shipfactory.createSubmarineClass(2*boxPixel, 8*boxPixel, 0, false, undefined));


        ships.push(shipfactory.createCruiserClass(0*boxPixel, 5*boxPixel, 0, false, undefined));
        ships.push(shipfactory.createCruiserClass(1*boxPixel, 4*boxPixel, 0, false, undefined));
        ships.push(shipfactory.createCruiserClass(1*boxPixel, 7*boxPixel, 0, false, undefined));


        ships.push(shipfactory.createBattleshipClass(1*boxPixel, 0*boxPixel, 0, false, undefined));
        ships.push(shipfactory.createBattleshipClass(2*boxPixel, 0*boxPixel, 0, false, undefined));

        ships.push(shipfactory.createCarrierClass(0*boxPixel, 0*boxPixel, 0, false, undefined));
    
    for (let ship of ships){
        gameZone.addShip(ship);
    }
    countAllShipParts(ships);
        
    }else if (friendlyPlayerCanvas){ 
        let field = false;
        ownGameField = new Gamefield(10, gameFieldStartY, boxPixel, boxCountXGameField, boxCountYGameField, color, field);
        ownGameZone = new GameZone(friendlyPlayerCanvas, ownGameField);  
        
        if(enemyPlayerCanvas){
            let field = true;
            opponentGameField = new Gamefield(10, gameFieldStartY, boxPixel, boxCountXGameField, boxCountYGameField, color, field);
            opponentGameZone = new GameZone(enemyPlayerCanvas, opponentGameField);
        }
    }
 }



function countAllShipParts(ships) {
    let ShipParts = 0;

    for (let i = 0; i < ships.length; i++) {
        if (Math.ceil(ships[i].boxSize) === 2) {
            ShipParts = ShipParts + 2;
        } else if (Math.ceil(ships[i].boxSize) === 3) {
            ShipParts = ShipParts + 3;
        } else if (Math.ceil(ships[i].boxSize) === 4) {
            ShipParts = ShipParts + 4;
        } else if (Math.ceil(ships[i].boxSize) === 5) {
            ShipParts = ShipParts + 5;
        }
    }
    return ShipParts;
}

function allShipsOnStage() {

    let boxes = gamefield.getBoxes();
    let allShipParts = 0;
    for (let i = 0; i < boxes.length; i++) {
        if (boxes[i].content !== "") {
            allShipParts++;
        }
    }
    if (countAllShipParts(ships) === allShipParts) {
        let gamefieldJSON = gameZone.gameField.convertToJSON();
        window.localStorage.setItem('gamefieldJSON', gamefieldJSON);
        let matchId = utilHandler.getCookie("matchId");
        window.location.href = webSocketHandler.getCleanBaseUrl(BASE_URL) + 'playermatch/' + matchId;
    } else {
        showSnackbarNotAllShipsArePlaced();
    }
}


function setAllShips() {

    if (harbour.content !== "") {
//sub1 t {x: 4, y: 320, width: 36, height: 76}
        ships[0].x = 4 * boxPixel;
        let bound = ships[0].bound;
        ships[0].setBounds(ships[0].x, ships[0].y, 36, 76);

//sub2 t {x: 80, y: 160, width: 36, height: 76}
        ships[1].x = 8 * boxPixel;
        ships[1].y = 5 * boxPixel;
        let bound1 = ships[1].bound;
        ships[1].setBounds(ships[1].x, ships[1].y, 36, 76);

//sub3 t {x: 80, y: 240, width: 36, height: 76}
        ships[2].x = 11 * boxPixel;
        let bound2 = ships[2].bound;
        ships[2].setBounds(ships[2].x, ships[2].y, 36, 76);

//sub4 t {x: 80, y: 320, width: 36, height: 76}
        ships[3].x = 9 * boxPixel;
        let bound3 = ships[3].bound;
        ships[3].setBounds(ships[3].x, ships[3].y, 36, 76);

//C1 {x: 0, y: 200, width: 36, height: 116.00000000000001}
        ships[4].x = 6 * boxPixel;
        ships[4].y = 7 * boxPixel;
        let bound4 = ships[4].bound;
        ships[4].setBounds(ships[4].x, ships[4].y, 36, 116.00000000000001);

//C2 {x: 40, y: 160, width: 36, height: 116.00000000000001}
        ships[5].x = 10 * boxPixel;
        ships[5].y = 2 * boxPixel;
        let bound5 = ships[5].bound;
        ships[5].setBounds(ships[5].x, ships[5].y, 36, 116.00000000000001);

// C3 {x: 40, y: 280, width: 36, height: 116.00000000000001}
        ships[6].x = 13 * boxPixel;
        let bound6 = ships[4].bound;
        ships[6].setBounds(ships[6].x, ships[6].y, 36, 116.00000000000001);

//Battelship  {x: 40, y: 0, width: 36, height: 156}
        ships[7].x = 8 * boxPixel;
        let bound7 = ships[7].bound;
        ships[7].setBounds(ships[7].x, ships[7].y, 36, 156);

//Battelship 2 t {x: 80, y: 0, width: 36, height: 156}
        ships[8].x = 12 * boxPixel;
        ships[8].y = 1 * boxPixel;
        let bound8 = ships[8].bound;
        ships[8].setBounds(ships[8].x, ships[8].y, 36, 156);

//Carrier t {x: 160, y: 0, width: 36, height: 196}
        ships[9].x = 5 * boxPixel;
        ships[9].y = 1 * boxPixel;
        let bound9 = ships[9].bound;
        ships[9].setBounds(ships[9].x, ships[9].y, 36, 196);

        for (let ship of ships) {
            gameZone.addShip(ship);
        }
    }
}


function decrement() {
  
    if (countDownSeconds > 0){
        countDownSeconds = countDownSeconds -1;
        document.getElementById("countDownSeconds").innerHTML = countDownSeconds;
    } else {
        countDownSeconds = 0;
        document.getElementById("countDownSeconds").innerHTML = countDownSeconds;
    }
}
function timeToShoot() {
    intervalId = setInterval(decrement, 1000);
}

function stopTimer() {
    clearInterval(intervalId);
    countDownSeconds = 60;
    document.getElementById("countDownSeconds").innerHTML = countDownSeconds;
}

function sendTimer() {
    let message = new Message("timerMessage", timeToShoot());
    webSocketHandler.sendTimer(message);
}

function sendCurrentPlayer() {
    let message = new Message("currentPlayerMessage", 1);
    webSocketHandler.sendCurrentPlayer(message);
}

function saveGamefield() {
    let gamefieldJSON = window.localStorage.getItem("gamefieldJSON");
    let message = new Message('gamefieldMessage', gamefieldJSON);
    webSocketHandler.sendGamefield(message);
    return Promise.resolve();

}

function requestGamefieldData() {
let message = new Message("gamefieldMessage", "");
webSocketHandler.requestGamefieldData(message);
}

function receiveMessagesFromWebSocket(message) {
    switch (message.messageType) {
        case "saveResponse":
        {
            let messageContent = JSON.parse(message.messageContent);
            if (messageContent.saveState === true) {
                requestGamefieldData();
            }    
            break;
        }
        case "gamefieldDataInit":
        {
            let messageContent = JSON.parse(message.messageContent);
            updateGameFields(messageContent, true);
            break;
        }
        case "gamefieldData":
        {
            let messageContent = JSON.parse(message.messageContent);
            updateGameFields(messageContent, false);
            break;
        }
        case "matchInfo": {
            let messageContent = JSON.parse(message.messageContent);
            let playerId = messageContent.currentPlayer;
            let playerName = messageContent.currentPlayerName;
            if(playerId !== utilHandler.getCookie("userId")){
                stopTimer();
                lockOpponentGameField(playerName);
            }else{
                unlockOpponentGameField(playerName);
                timeToShoot();
            }
            console.log(messageContent);
            break;
        }
        case "gameOver":
        {
            window.location = webSocketHandler.getCleanBaseUrl(BASE_URL) + '/playermatch/' + utilHandler.getCookie('matchId') + '/over';
            break;
        }
        case "toManyPlayersMessage":
        {   
            alert("Es sind bereits 2 Spieler miteinander verbunden.");
            window.location = "/";
            break;
        }
        default:
        {
            console.log('MessageType not found!');
            break;
        }
    }

}

function lockOpponentGameField(playerName){
    opponentGameZone.disableMouse();
    document.getElementById("turn-field").innerHTML = "Dein Gegner ("+playerName+") ist am Zug!";
}

function unlockOpponentGameField(playerName){
    opponentGameZone.enableMouse();
    document.getElementById("turn-field").innerHTML = "Du ("+playerName+") bist am Zug!";
}

function updateGameFields(content, init) {
    if (init === true) {
        updateOwnGameFieldInit(content.ownGameField.gameField, ownGameField, ownGameZone);
        if (JSON.stringify(content.opponentGameField) !== "{}") {
            updateOpponentGameField(content.opponentGameField.gameField, opponentGameField, opponentGameZone);
        }
    } else {
        updateOwnGameField(content.ownGameField.gameField, ownGameField, ownGameZone);
        updateOpponentGameField(content.opponentGameField.gameField, opponentGameField, opponentGameZone);
    }
}

function updateOpponentGameField(innerGameField, gameField, gameZone) {
    buildShot(innerGameField, gameField, gameZone);
}

function updateOwnGameFieldInit(innerGameField, gameField, gameZone) {
    buildShips(innerGameField, gameField, gameZone);
    buildShot(innerGameField, gameField, gameZone);
}

function updateOwnGameField(innerGameField, gameField, gameZone) {
    buildShot(innerGameField, gameField, gameZone);
}

function buildShips(innerGameField, gameField, gameZone) {
    let currentShips = [];
    let usedIds = [];
    let shipFactory = new ShipFactory(gameZone, boxPixel);
    innerGameField.forEach(function (box) {
        if (box.content.shipType && !usedIds.includes(box.content.id)) {
            switch (box.content.shipType) {
                case "Submarine":
                {
                    currentShips.push(shipFactory.createSubmarineClass(box.content.posX - ((boxPixel * 4) - 10), box.content.posY, box.content.rotation, true, gameField));
                    usedIds.push(box.content.id);
                    break;
                }
                case "Cruiser":
                {
                    currentShips.push(shipFactory.createCruiserClass(box.content.posX - ((boxPixel * 4) - 10), box.content.posY, box.content.rotation, true, gameField));
                    usedIds.push(box.content.id);
                    break;
                }
                case "Battleship":
                {
                    currentShips.push(shipFactory.createBattleshipClass(box.content.posX - ((boxPixel * 4) - 10), box.content.posY, box.content.rotation, true, gameField));
                    usedIds.push(box.content.id);
                    break;
                }
                case "Carrier":
                {
                    currentShips.push(shipFactory.createCarrierClass(box.content.posX - ((boxPixel * 4) - 10), box.content.posY, box.content.rotation, true, gameField));
                    usedIds.push(box.content.id);
                    break;
                }
                default:
                {
                    console.log('ShipType not found!');
                    break;
                }
            }
        }
    });
    for (let ship of currentShips) {
        gameZone.addShip(ship);
        collisionHandler.setShipPositionInGamefield(gameField, ship);
    }
}

function sendShotToWebsocket(messageObj){
    //TODO: Reset Timer
    webSocketHandler.sendShot(messageObj);
}

function buildShot(innerGameField, gameField, gameZone) {
    let currentShots = [];
    let shotFactory = new ShotFactory(gameZone, boxPixel);
    innerGameField.forEach(function (box) {
        if (box.status) {
            switch (box.status) {
                case "x":
                {
                    currentShots.push(shotFactory.createFieldHit(box.posX - ((boxPixel * 4) - 12), box.posY + 2));
                    break;
                }
                case "v":
                {
                    currentShots.push(shotFactory.createFieldSunk(box.posX - ((boxPixel * 4) - 12), box.posY + 2));
                    break;
                }
                case "o":
                {
                    currentShots.push(shotFactory.createFieldShot(box.posX - ((boxPixel * 4) - 12), box.posY + 2));
                    break;
                }
                default:
                {
                    console.log('ShotType not found!');
                    break;
                }
            }
        }
    });
    for (let shot of currentShots) {
        gameZone.addShot(shot);
    }
}

module.exports = {
    boxPixel: boxPixel,
    init: init,
    allShipsOnStage: allShipsOnStage,
    saveGamefield: saveGamefield,
    sendCurrentPlayer: sendCurrentPlayer,
    receiveMessagesFromWebSocket: receiveMessagesFromWebSocket,
    requestGamefieldData: requestGamefieldData,
    webSocketHandler: webSocketHandler,
    matchHandler: matchHandler,
    utilHandler: utilHandler,
    sendShotToWebsocket: sendShotToWebsocket,
    setAllShips: setAllShips
};
