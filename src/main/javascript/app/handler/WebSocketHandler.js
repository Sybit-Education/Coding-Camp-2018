/* global Stomp */

let utilHandler = require('./UtilHandler');

const socketEndpoint = '/battleships-socket';
const matchQueue = '/user/match';
const gamefieldEndpoint = '/battleships/match/gamefield';
const gamefieldDataEndpoint = '/battleships/match/gamfielddata';
const shotEndpoint = '/battleships/match/shot';
const currentPlayerEndpoint = '/battleships/match/currentplayer';

let stompClient;

module.exports = {
    connect: function () {
        return new Promise(function (resolve, reject) {
            let socket = new SockJS(socketEndpoint);
            stompClient = Stomp.over(socket);
            // Disable console logging
            stompClient.debug = null;
            stompClient.connect({}, function (frame) {
                let userName = frame.headers['user-name'];
                utilHandler.setCookie("userName", userName, 30);
                resolve();
            }, function (error) {
                console.log('STOMP: ' + error);
            });
        });
    },

    disconnect: function () {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
    },

    subscribeToMatch: function (callback) {     
        let matchId = utilHandler.getCookie("matchId");
        stompClient.subscribe(matchQueue, function (msg) {              
            let message = JSON.parse(msg.body);
            callback(message);
        }, {matchId: matchId});
    },

    sendCurrentPlayer: function (messageObj) {
        stompClient.send(currentPlayerEndpoint, {}, JSON.stringify(messageObj));
    },

    sendGamefield: function (messageObj) {
        stompClient.send(gamefieldEndpoint, {}, JSON.stringify(messageObj));
    },

    requestGamefieldData: function (messageObj) {
        stompClient.send(gamefieldDataEndpoint, {}, JSON.stringify(messageObj));
    },

    sendShot: function (messageObj) {
        stompClient.send(shotEndpoint, {}, JSON.stringify(messageObj));
    }
};
