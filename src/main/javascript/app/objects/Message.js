let utilHandler = require('../handler/UtilHandler');

module.exports = class Message{
    constructor(messageType, messageContent) {
        
        let matchId = utilHandler.getCookie("matchId");
        let sendFrom = utilHandler.getCookie("userId");
            
        this.massageType = messageType;
        this.messageContent = messageContent;
        this.sendFrom = sendFrom;
        this.matchId = matchId;
        
        return this;
    } 
};