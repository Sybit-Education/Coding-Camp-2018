/* global createjs, BASE_URL */

let Shot = require('../objects/Shot');

module.exports = class ShotFactory {
    constructor(gamezone, boxPixel) {
        this.gameZone = gamezone;
        this.boxPixel = boxPixel;
        this.scaleX = (boxPixel * 0.9) / 100;
        this.scaleY = (boxPixel * 0.9) / 100;
        let queue = new createjs.LoadQueue(true);
        let manifest = ShotFactory.getManifest();

        queue.on("fileload", this.handleFileLoad, this);
        queue.loadManifest(manifest);
        queue.load();
    }


    // x
    createFieldHit(startPosX, startPosY) {
        let image = new createjs.Bitmap(ShotFactory.getImageSrc("fieldHit"));
        image.scaleX = this.scaleX;
        image.scaleY = this.scaleY;
        let shot = new Shot("fieldHit", image, startPosX, startPosY, image.scaleX * 100, image.scaley * 100, this.boxPixel);
        return shot;
    }

    // v
    createFieldSunk(startPosX, startPosY) {
        let image = new createjs.Bitmap(ShotFactory.getImageSrc("fieldSunk"));
        image.scaleX = this.scaleX;
        image.scaleY = this.scaleY;
        let shot = new Shot("fieldHit", image, startPosX, startPosY, image.scaleX * 100, image.scaley * 100, this.boxPixel);
        return shot;
    }

    // o
    createFieldShot(startPosX, startPosY) {
        let image = new createjs.Bitmap(ShotFactory.getImageSrc("fieldShot"));
        image.scaleX = this.scaleX;
        image.scaleY = this.scaleY;
        let shot = new Shot("fieldHit", image, startPosX, startPosY, image.scaleX * 100, image.scaley * 100, this.boxPixel);
        return shot;
    }


    static getManifest() {
        let manifest = [
            {src: BASE_URL + "resources/images/hit.png", id: "fieldHit"},
            {src: BASE_URL + "resources/images/sunk.png", id: "fieldSunk"},
            {src: BASE_URL + "resources/images/shot.png", id: "fieldShot"}
        ];

        return manifest;
    }

    static getImageSrc(id) {
        return ShotFactory.getManifest().find(
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