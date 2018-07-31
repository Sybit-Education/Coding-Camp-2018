let collisionHandler = require('../handler/CollisionHandler');

module.exports = class Ship extends createjs.Container {
    constructor(shipType, image, posX, posY, rotation, width, height, boxPixel, lock) {
        super();
        this.shipType = shipType;
        this.originX = posX;
        this.originY = posY;

        this.regX = this.regY = boxPixel / (-20);
        this.x = posX;
        this.y = posY;
        this.rotation = rotation;
        this.boxSize = Ship.getShipBoxSize(width, height, boxPixel);
        this.setBounds(posX, posY, width, height);
        this.addChild(image);
        this.lock = lock;

        if (!lock) {
            this.on("dblclick", function (evt) {
                rotateShip(this, evt);
            });

            this.on("mousedown", function (evt) {
                this.parent.addChild(this);
                this.offset = {x: this.x - evt.stageX, y: this.y - evt.stageY};
            });

            this.on("pressmove", function (evt) {
                this.x = evt.stageX + this.offset.x;
                this.y = evt.stageY + this.offset.y;
                let bound = this._bounds;
                this.setBounds(this.x, this.y, bound.width, bound.height);
                this.parent.update(evt);
            });

            this.on("pressup", function (evt) {
                //TODO was soll passieren wenn die Maus losgelassen wird
            });
        }

        return this;
    }

    toJSON() {
        return '{"id":"' + this.id + '","shipType":"' + this.shipType + '","rotation": "' + this.rotation + '","posX":"' + this.x + '","posY":"' + this.y + '"}';
    }

    static getShipBoxSize(width, height, boxPixel) {
        let shipBoxWidth = width / boxPixel;
        let shipBoxHeight = height / boxPixel;
        return Math.max(shipBoxWidth, shipBoxHeight);
    }

    loadRotation(parent){
        loadRotationForShip(this, parent);
    }
};

function rotateShip(ship, evt) {
    setShipRotation(ship);
    collisionHandler.getAffectedBoxes(ship.parent.gameField.getBoxes(), ship).then((affectedBoxes) => {
        collisionHandler.isActionValid(affectedBoxes, ship).then(() => {
            collisionHandler.setShipPositionInGamefield(ship.parent.gameField, ship).then(() => {
                ship.parent.update(evt);
            });
        }).catch(() => {
            setShipRotation(ship);
            showSnackbarShipRotationNotPossible();
            collisionHandler.snapToOrigin(ship).then(() => {
                ship.parent.update(evt);
            });
        });
    });

}

function moveShip(ship, evt) {
    collisionHandler.getAffectedBoxes(ship.parent.gameField.getBoxes().concat(ship.parent.harbour.getBoxes()), ship).then((affectedBoxes) => {
        collisionHandler.isActionValid(affectedBoxes, ship).then(() => {
            collisionHandler.snapToBox(affectedBoxes[0], ship).then(() => {
                collisionHandler.setShipPositionInGamefield(ship.parent.gameField, ship).then(() => {
                    ship.parent.update(evt);
                });
            });
        }).catch(() => {
            showSnackbarShipPlacementNotPossible();

            collisionHandler.snapToOrigin(ship).then(() => {
                ship.parent.update(evt);
            });
        });
    });
}

function setShipRotation(ship) {
    if (ship.rotation === 0) {
        ship.rotation = 270;
        ship.regX += ship.parent.gameField.boxPixel;
    } else {
        ship.rotation = 0;
        ship.regX -= ship.parent.gameField.boxPixel;
    }
}

function loadRotationForShip(ship, parent) {
    ship.regX += parent.boxPixel;
}