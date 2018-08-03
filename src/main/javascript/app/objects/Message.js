let utilHandler = require('../handler/UtilHandler');

module.exports = class Message{
    constructor(messageType, messageContent, showShips) {
        
        let matchId = utilHandler.getCookie("matchId");
        let sendFrom = utilHandler.getCookie("userId");
        
        this.showShips = showShips;
        this.massageType = messageType;
        this.messageContent = messageContent;
        this.sendFrom = sendFrom;
        this.matchId = matchId;
        
        return this;
    } 
};