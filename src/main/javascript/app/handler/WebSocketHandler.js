/* global Stomp, BASE_URL */

let utilHandler = require('./UtilHandler');

const socketEndpoint = getCleanBaseUrl(BASE_URL) + 'battleships-socket';
const matchQueue = '/user/match';
const gamefieldEndpoint = getCleanBaseUrl(BASE_URL) + 'battleships/match/gamefield';
const gamefieldDataEndpoint = getCleanBaseUrl(BASE_URL) + 'battleships/match/gamfielddata';
const shotEndpoint = getCleanBaseUrl(BASE_URL) + 'battleships/match/shot';
const currentPlayerEndpoint = getCleanBaseUrl(BASE_URL) + 'battleships/match/currentplayer';
const timerEndpoint = getCleanBaseUrl(BASE_URL) + 'battleships/match/timer';

let stompClient;

module.exports = {
    connect: function () {
        return new Promise(function (resolve, reject) {
            let socket = new SockJS(socketEndpoint);
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
    },
    
    getCleanBaseUrl: function (baseUrl){
        let baseUrlRegex = /(.*)(;)(.*)/g;
        let match = baseUrlRegex.exec(baseUrl);
        if(match === null){
            return baseUrl;
        }else{
            return match[1] !== null ? match[1] : baseUrl;
        }
    }
};
