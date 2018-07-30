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

let gamefield = new Gamefield(gameFieldStartX, gameFieldStartY, boxPixel, boxCountXGameField, boxCountYGameField, color);
let ownGameField = undefined;
let ownGameZone = undefined;
let opponentGameField = undefined;
let opponentGameZone = undefined;
let ships = [];

function init() {
//TODO init Methode templating
}

function handleResize() {
    //TODO Resizing
}

function countAllShipParts(ships) {
//TODO init Methode templating

}

function allShipsOnStage() {
//TODO init Methode templating

}

function sendCurrentPlayer() {
//TODO init Methode templating

}

function saveGamefield() {
//TODO init Methode templating

}

function requestGamefieldData() {
//TODO init Methode templating

}

function receiveMessagesFromWebSocket(message) {
//TODO init Methode templating

}

function lockOpponentGameField(){
//TODO init Methode templating

}

function unlockOpponentGameField(){
//TODO init Methode templating

}

function updateGameFields(content, init) {
//TODO init Methode templating

}

function updateOpponentGameField(innerGameField, gameField, gameZone) {
//TODO init Methode templating

}

function updateOwnGameFieldInit(innerGameField, gameField, gameZone) {
//TODO init Methode templating

}

function updateOwnGameField(innerGameField, gameField, gameZone) {
//TODO init Methode templating

}

function buildShips(innerGameField, gameField, gameZone) {
//TODO init Methode templating

}

function buildShot(innerGameField, gameField, gameZone) {
   //TODO
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