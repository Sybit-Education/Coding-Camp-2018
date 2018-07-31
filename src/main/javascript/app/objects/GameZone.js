module.exports = class GameZone extends createjs.Stage {

    constructor(canvas, gameField, ) {
        super(canvas);

        this.gameField = gameField;

        this.mouseMoveOutside = true;
        this.enableMouseOver(10);


        for (let box of this.gameField.getBoxes()) {
            this.addChild(box);
        }

        this.update();
    }

    addShip(ship) {
        this.addChild(ship);
        this.update();
    }


    addShot(shot){
        this.addChild(shot);
        this.update();
    }

    enableMouse(){
        this.mouseChildren = true;
    }

    disableMouse(){
        this.mouseChildren = false;
    }

};
