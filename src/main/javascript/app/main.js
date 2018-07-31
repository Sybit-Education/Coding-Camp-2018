/* global createjs */

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

let field = false;
let ownGameField = undefined;
let ownGameZone = undefined;
let opponentGameField = undefined;
let opponentGameZone = undefined;
let ships = [];

let gamefield = new Gamefield (gameFieldStartX, gameFieldStartY, boxPixel, boxCountXGameField, boxCountYGameField, color, field);

function init() {
    console.log("HACKS");
    let friendlyPlayerCanvas = document.getElementById("friendlyPlayer");
    let enemyPlayerCanvas = document.getElementById("enemyPlayer");
    let canvas = document.getElementById("canvas");
    let field = false;
    console.log("canvas: "+canvas);
    console.log("enemyPlayer: "+enemyPlayerCanvas);
    console.log("freindlyPlayer: "+ friendlyPlayerCanvas);
    if (canvas){
        let harbour = new Gamefield(harbourStartX, harbourStartY, boxPixel, boxCountXHarbour, boxCountYHarbour, color, field);
        gameZone = new HarbourZone(canvas, gamefield, harbour);
        
    }else if (friendlyPlayerCanvas){ 
        console.log("HACKS2");
        let field = false;
        ownGameField = new Gamefield(10, gameFieldStartY, boxPixel, boxCountXGameField, boxCountYGameField, color, field);
        ownGameZone = new GameZone(friendlyPlayerCanvas, ownGameField);  
        
        if(enemyPlayerCanvas){
            console.log("HACKS3");
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
        }
        else if (Math.ceil(ships[i].boxSize) === 3) {
            ShipParts = ShipParts + 3;
        }
        else if (Math.ceil(ships[i].boxSize) === 4) {
            ShipParts = ShipParts + 4;
        }
        else if (Math.ceil(ships[i].boxSize) === 5) {
            ShipParts = ShipParts + 5;
        }
    }
    return ShipParts;
}

function allShipsOnStage() {
    //TODO Alle Boxen holen

    //TODO Prüfen ob die Schiffe platziert sind

    //TODO Wenn alle Schiffe gefunden wurden (Tipp: let gamefieldJSON = gameZone.gameField.convertToJSON();)

    //TODO Andernfalls eine Snackbar anzeigen (Tipp: Auf der jps/html nachschauen)
}

function sendCurrentPlayer() {
//TODO Das Message Objekt bauen 
//TODO Mit dem WebSocketHandler die Message verschiken
}

function saveGamefield() {
//TODO Tipp Das Spielfeld aus dem LocalStorage laden
//TODO die Message bauen und mit dem WebSocketHandler verschiken
//TODO Tipp am Ende return Promise.resolve 
}

function requestGamefieldData() {
//TODO Message bauen
//TODO Den WebsocketHander benutzen um die Message zu senden
}

function receiveMessagesFromWebSocket(message) {
    switch (message.messageType) {
        case "saveResponse": {
            let messageContent = JSON.parse(message.messageContent);
            if (messageContent.saveState === true) {
                requestGamefieldData();
            }
            break;
        }
        case "gamefieldDataInit":{
            let messageContent = JSON.parse(message.messageContent);
            updateGameFields(messageContent, true);
            break;
        }
        case "gamefieldData": {
            let messageContent = JSON.parse(message.messageContent);
            updateGameFields(messageContent, false);
            break;
        }
        case "matchInfo": {
            let messageContent = JSON.parse(message.messageContent);
            let playerId = messageContent.currentPlayer;
            if(playerId !== utilHandler.getCookie("userName")){
                lockOpponentGameField();
            }else{
                unlockOpponentGameField();
            }
            console.log(messageContent);
            break;
        }
        case "gameOver":{
            window.location = window.location.origin+'/playermatch/'+utilHandler.getCookie('matchId')+'/over';
            break;
        }
        default: {
            console.log('MessageType not found!');
            break;
        }
    }

}

function lockOpponentGameField(){
//TODO Das Spielfeld sperren und eine Message anzeigen
}

function unlockOpponentGameField(){
//TODO Das Spielfeld sperren und eine Message anzeigen
}

function updateGameFields(content, init) {
    if(init === true){
        updateOwnGameFieldInit(content.ownGameField.gameField, ownGameField, ownGameZone);
        if(JSON.stringify(content.opponentGameField) !== "{}"){
            updateOpponentGameField(content.opponentGameField.gameField, opponentGameField, opponentGameZone);
        }
    }else{
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
                //TODO "Submarine" currentShips.push(shipFactory.createSubmarineClass(box.content.posX - ((boxPixel * 4) - 10), box.content.posY, box.content.rotation, true, gameField));

                //TODO "Cruiser" currentShips.push(shipFactory.createCruiserClass(box.content.posX - ((boxPixel * 4) - 10), box.content.posY, box.content.rotation, true, gameField));

                //TODO "Battleship" currentShips.push(shipFactory.createBattleshipClass(box.content.posX - ((boxPixel * 4) - 10), box.content.posY, box.content.rotation, true, gameField));
        
                //TODO "Carrier" currentShips.push(shipFactory.createCarrierClass(box.content.posX - ((boxPixel * 4) - 10), box.content.posY, box.content.rotation, true, gameField));      
            }
    });
    //TODO Der Gamezone die Schiffe hinzufügen 
    //TODO Mithilfe des Collision Handlers die Schiffposition setzen
}

function buildShot(innerGameField, gameField, gameZone) {
    let currentShots = [];
    let shotFactory = new ShotFactory(gameZone, boxPixel);
    innerGameField.forEach(function (box) {
        if (box.status) {
            //TODO "x" currentShots.push(shotFactory.createFieldHit(box.posX - ((boxPixel * 4) - 12), box.posY + 2));
 
            //TODO "v" currentShots.push(shotFactory.createFieldSunk(box.posX - ((boxPixel * 4) - 12), box.posY + 2));
   
            //TODO "o" currentShots.push(shotFactory.createFieldShot(box.posX - ((boxPixel * 4) - 12), box.posY + 2));   
        }
    });
    //TODO Die Schüsse dem Gamefield hinzufügen
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
    matchHandler: matchHandler
};
