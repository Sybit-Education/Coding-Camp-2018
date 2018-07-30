/* global Battleship */

let Message = require('./Message');

module.exports = class Box extends createjs.Shape {
    constructor(id, posX, posY, pixel, color, field) {
        super();
        //TODO Sinnvolle eigenschaften überlegen

        if (field === true) {
            this.on("click", function (evt) {
                let message = new Message('shot', this);
                Battleship.webSocketHandler.sendShot(message);
                //TODO turn player function aufrufen
            });
        }

        this.addEventListener("mouseover", function (event) {
            if (event.currentTarget.parent.canvas.id === "enemyPlayer") {
                document.getElementById("enemy-current-field").innerHTML = event.currentTarget.id;
            } else if (event.currentTarget.parent.canvas.id === "friendlyPlayer") {
                document.getElementById("friendly-current-field").innerHTML = event.currentTarget.id;
            } else if(event.currentTarget.parent.canvas.id === "canvas"){
                document.getElementById("current-field").innerHTML = event.currentTarget.id;
            }
        });

    }

    toJSON() {
        return '{"id":"' + this.id + '","content":' + (this.content ? this.content : '{}') + ',"status": "' + this.status + '", "posX":"' + this.posX + '","posY":"' + this.posY + '"}';
    }
};
