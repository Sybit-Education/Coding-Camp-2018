/* global Battleship */

let Message = require('./Message');

module.exports = class Box extends createjs.Shape {
    constructor(id, posX, posY, pixel, color, field) {
        super();
        this.id = id;
        this.name = id;
        this.posX = posX;
        this.posY = posY;
        this.boxPixel = pixel;
        this.color = color;
        this.field = field;
        this.status = "";
        this.content = "";
        this.graphics.beginFill("#37b6ee").drawRect(posX, posY, pixel, pixel);
        this.graphics.setStrokeStyle(1).beginStroke(color).rect(posX, posY, pixel, pixel);
        this.setBounds (posX, posY, pixel, pixel);

        if (field === true) {
            this.on("click", function (evt) {
                let message = new Message('shot', this);
                Battleship.sendShotToWebsocket(message);
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
