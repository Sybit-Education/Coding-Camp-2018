/* global createjs */

let Ship = require('../objects/Ship');

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
        //TODO Ein Schiff der Submarine Klasse erzeugen. Beachte die Größe des Schiffes, eine Submarine soll 1 Box groß sein.

        // scaleY = scaleX (boxPixel / 100)

        //TODO Rotation beachten
        return ship;
    }

    createCruiserClass(startPosX, startPosY, rotation, lock, parent) {
        //TODO Ein Schiff der Cruiser Klasse erzeugen. Beachte die Größe des Schiffes, eine Submarine soll 2 Box groß sein.

        // scaleY = scaleX (boxPixel *2 / 100)

        //TODO Rotation beachten
        return ship;
    }

    createBattleshipClass(startPosX, startPosY, rotation, lock, parent) {
        //TODO Ein Schiff der Battleship Klasse erzeugen. Beachte die Größe des Schiffes, eine Submarine soll 3 Box groß sein.

        // scaleY = scaleX (boxPixel *3 / 100)

        //TODO Rotation beachten
        return ship;
    }

    createCarrierClass(startPosX, startPosY, rotation, lock, parent) {
        //TODO Ein Schiff der Carrier Klasse erzeugen. Beachte die Größe des Schiffes, eine Submarine soll 1 Box groß sein.

        // scaleY = scaleX (boxPixel *4 / 100)

        //TODO Rotation beachten
        return ship;
    }

    static getManifest() {
        var manifest = [
            {src: "resources/images/grey.jpg", id: "Submarine"},
            {src: "resources/images/grey.jpg", id: "Cruiser"},
            {src: "resources/images/grey.jpg", id: "Battleship"},
            {src: "resources/images/grey.jpg", id: "Carrier"}
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
