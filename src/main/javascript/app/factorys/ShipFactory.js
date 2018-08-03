/* global createjs, BASE_URL */

let Ship = require('../objects/Ship');
let webSocketHandler = require('../handler/WebSocketHandler');

module.exports = class ShipFactory {
    constructor(gamezone, boxPixel) {
        this.gameZone = gamezone;
        this.boxPixel = boxPixel;
        this.scaleX = (boxPixel * 0.9) / 100;
        let queue = new createjs.LoadQueue(true);
        let manifest = ShipFactory.getManifest();

        queue.on("fileload", this.handleFileLoad, this);
        queue.loadManifest(manifest);
        queue.load();
    }

    createSubmarineClass(startPosX, startPosY, rotation, lock, parent) {
        let image = new createjs.Bitmap(ShipFactory.getImageSrc("Submarine"));
        image.scaleX = this.scaleX;
        image.scaleY = image.scaleX + (this.boxPixel *1 / 100);
        let ship = new Ship ("Submarine", image, startPosX, startPosY, rotation, image.scaleX*100, image.scaleY*100, this.boxPixel, lock);
        if (rotation!==0&&parent) {
            ship.loadRotation(parent);
        }
        return ship;
    }

    createCruiserClass(startPosX, startPosY, rotation, lock, parent) {
        let image = new createjs.Bitmap(ShipFactory.getImageSrc("Cruiser"));
        image.scaleX = this.scaleX;
        image.scaleY = image.scaleX  + (this.boxPixel *2 /100);
        let ship = new Ship ("Cruiser", image, startPosX, startPosY, rotation, image.scaleX*100, image.scaleY*100, this.boxPixel, lock);
        if (rotation!==0&&parent) {
            ship.loadRotation(parent);
        }
        return ship;
    }

    createBattleshipClass(startPosX, startPosY, rotation, lock, parent) {
        let image = new createjs.Bitmap(ShipFactory.getImageSrc("Battleship"));
        image.scaleX = this.scaleX;
        image.scaleY = image.scaleX  + (this.boxPixel *3 /100);
        let ship = new Ship ("Battleship", image, startPosX, startPosY, rotation, image.scaleX*100, image.scaleY*100, this.boxPixel, lock);
        if (rotation!==0&&parent) {
            ship.loadRotation(parent);
        }
        return ship;
    }

    createCarrierClass(startPosX, startPosY, rotation, lock, parent) {
        let image = new createjs.Bitmap(ShipFactory.getImageSrc("Carrier"));
        image.scaleX = this.scaleX;
        image.scaleY = image.scaleX  + (this.boxPixel *4 /100);
        let ship = new Ship ("Carrier", image, startPosX, startPosY, rotation, image.scaleX*100, image.scaleY*100, this.boxPixel, lock);
        if (rotation!==0&&parent) {
            ship.loadRotation(parent);
        }
        return ship;
    }

    static getManifest() {
        var manifest = [
            {src: webSocketHandler.getCleanBaseUrl(BASE_URL) + "resources/images/Submarine.png", id: "Submarine"},
            {src: webSocketHandler.getCleanBaseUrl(BASE_URL) + "resources/images/Cruiser.png", id: "Cruiser"},
            {src: webSocketHandler.getCleanBaseUrl(BASE_URL) + "resources/images/battleship_field.png", id: "Battleship"},
            {src: webSocketHandler.getCleanBaseUrl(BASE_URL) + "resources/images/Carrier.png", id: "Carrier"}
        ];

        return manifest;
    }

    static getImageSrc(id) {
        return ShipFactory.getManifest().find(
            function (obj) {
                return obj.id === id;
            }).src;
    }

    handleFileLoad(event) {
        let item = event.item; // A reference to the item that was passed in to the LoadQueue
        let type = item.type;
        this.gameZone.update();
    }
};
