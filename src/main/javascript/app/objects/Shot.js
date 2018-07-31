module.exports = class Shot extends createjs.Container {
    constructor(shotType, image, posX, posY,width, height, boxPixel,){
        super();
        this.shotType = shotType;
        this.originX = posX;
        this.originY = posY;

        this.x = posX;
        this.y = posY;
        this.boxSize = Shot.getShotBoxSize(width, height, boxPixel);
        this.setBounds(posX, posY, width, height);
        this.addChild(image);

        return this;
    }

    static getShotBoxSize(width, height, boxPixel) {
        let shotBoxWidth = width / boxPixel;
        let shotBoxHeight = height / boxPixel;
        return Math.max(shotBoxWidth, shotBoxHeight);
    }
};