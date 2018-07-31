let utilHandler = require('../handler/UtilHandler');

module.exports = class Message{
    constructor(messageType, messageContent) {
        
        let matchId = utilHandler.getCoockie("matchId");
        let sendFrom = utilHandler.getCoockie("userName");
            
        this.massageType = messageType;
        this.messageContent = messageContent;
        this.sendFrom = sendFrom;
        this.matchId = matchId;
        
        return this;
    } 
};