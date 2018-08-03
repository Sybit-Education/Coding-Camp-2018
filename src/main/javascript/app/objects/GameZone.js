module.exports = class GameZone extends createjs.Stage {

    constructor(canvas, gameField,) {
        super(canvas);

        this.gameField = gameField;

        this.mouseMoveOutside = true;
        this.enableMouseOver(10);

        this.cleanChildren = false;

        for (let box of this.gameField.getBoxes()) {
            this.addChild(box);
        }

        this.update();
    }

    addShip(ship) {
        this.addChild(ship);
        this.setChildIndex(ship, 100);
        this.update();
    }


    addShot(shot) {
        for (let child of this.children) {
            if (child.shotType && child.x === shot.x && child.y === shot.y) {
                this.removeChild(child);
            }
        }
        this.addChild(shot);
        this.setChildIndex(shot, 1000);
        this.update();
    }

    enableMouse() {
        this.mouseChildren = true;
    }

    disableMouse() {
        this.mouseChildren = false;
    }

    removeAllShips(){
        for (let child of this.children) {
            if (child.shipType) {
                this.removeChild(child);
            }
        }
    }
};
