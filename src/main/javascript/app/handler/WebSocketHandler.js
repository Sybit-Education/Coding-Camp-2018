/* global Stomp, BASE_URL */

let utilHandler = require('./UtilHandler');

const socketEndpoint = 'battleships-socket';
const matchQueue = '/user/match';
const gamefieldEndpoint = 'battleships/match/gamefield';
const gamefieldDataEndpoint = 'battleships/match/gamfielddata';
const shotEndpoint = 'battleships/match/shot';
const currentPlayerEndpoint = 'battleships/match/currentplayer';
const timerEndpoint = 'battleships/match/timer';
const toManyPlayersEndpoint = 'battleships/match/gamefield';
const giveUpEndpoint = 'battleships/match/giveUp';

let stompClient;

module.exports = {
    connect: function () {
        let endpoint = this.getCleanBaseUrl(BASE_URL) + socketEndpoint;
        
        return new Promise(function (resolve, reject) {
            let socket = new SockJS(endpoint);
            stompClient = Stomp.over(socket);
            // Disable console logging
            stompClient.debug = null;
            stompClient.connect({}, function (frame) {
                let userId = frame.headers['user-name'];
                utilHandler.setCookie("userId", userId, 30);
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
    
    getCleanBaseUrl: function (baseUrl){
        let baseUrlRegex = /(.*)(;)(.*)/g;
        let match = baseUrlRegex.exec(baseUrl);
        if(match === null){
            return baseUrl;
        }else{
            return match[1] !== null ? match[1] : baseUrl;
        }
    },

    sendCurrentPlayer: function (messageObj) {
        stompClient.send(this.getCleanBaseUrl(BASE_URL) + currentPlayerEndpoint, {}, JSON.stringify(messageObj));
    },

    sendGamefield: function (messageObj) {
        stompClient.send(this.getCleanBaseUrl(BASE_URL) + gamefieldEndpoint, {}, JSON.stringify(messageObj));
    },

    requestGamefieldData: function (messageObj) {
        stompClient.send(this.getCleanBaseUrl(BASE_URL) + gamefieldDataEndpoint, {}, JSON.stringify(messageObj));
    },

    sendShot: function (messageObj) {
        stompClient.send(this.getCleanBaseUrl(BASE_URL) + shotEndpoint, {}, JSON.stringify(messageObj));
    },
    
    sendToManyPlayers: function (messageObj) {
        stompClient.send(this.getCleanBaseUrl(BASE_URL) + toManyPlayersEndpoint, {}, JSON.stringify(messageObj));
    },
    sendGiveUp: function (messageObj) {
        stompClient.send(this.getCleanBaseUrl(BASE_URL) + giveUpEndpoint, {}, JSON.stringify(messageObj));
    }
};
