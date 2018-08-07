let utilHandler = require('../handler/UtilHandler');

module.exports = class Message{
    constructor(messageType, messageContent, showShips) {
        
        this.showShips = showShips;
        
        let matchId = utilHandler.getCookie("matchId");
        let sendFrom = utilHandler.getCookie("userId");  
        let userName = utilHandler.getCookie("userName");
        
        this.massageType = messageType;
        this.messageContent = messageContent;
        this.sendFrom = sendFrom;
        this.matchId = matchId;
        this.playerName = userName;
        
        return this;
    } 
};