module.exports = class HarbourZone extends createjs.Stage {

    constructor(canvas, gameField, harbour) {
        super(canvas);

        this.gameField = gameField;
        this.harbour = harbour;
        this.mouseMoveOutside = true;
        this.enableMouseOver(10);

        for (let box of this.gameField.getBoxes()) {
            this.addChild(box);
        }

        for (let box of this.harbour.getBoxes()) {
            this.addChild(box);
        }
        this.update();
    }

    addShip(ship) {
        this.addChild(ship);
        this.update();
    }
};
