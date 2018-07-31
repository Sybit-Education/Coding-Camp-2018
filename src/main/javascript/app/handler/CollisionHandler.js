module.exports = {
    getAffectedBoxes: function (boxes, ship) {
        return new Promise(function (resolve, reject) {
            let affectedBoxes = [];
            for (let i = 0; i < boxes.length; i++) {
                if (compareValues(boxes[i], ship)) {
                    affectedBoxes.push(boxes[i]);
                }
            }
            if (affectedBoxes.length > 0) {
                resolve(affectedBoxes);
            } else {
                reject();
            }

        });
    },

    isActionValid: function (affectedBoxes, ship) {
        return new Promise(function (resolve, reject) {

            if (affectedBoxes.length < ship.boxSize) {
                reject();
            }

            affectedBoxes.forEach(function (box){
                ship.parent.gameField.boxes.filter(function (field) {
                    if(field.id === box.id) {
                        if(field.content !== '' && JSON.parse(field.content)["id"] !== JSON.parse(ship.toJSON())["id"]){
                            reject();
                        }
                        if(!oneFieldFreeAroundShip(box, ship)){
                            reject();
                        }
                    }
                })
            });
            resolve();
        });
    },

    setShipPositionInGamefield: function (gameField, ship) {
        return new Promise(function (resolve) {
            let boxes = gameField.getBoxes();
            let jsonShip = ship.toJSON();
            for (let i = 0; i < boxes.length; i++) {

                if (compareValues(boxes[i], ship)) {
                    boxes[i].content = jsonShip;
                } else if (boxes[i].content !== "" && JSON.parse(boxes[i].content)["id"] === JSON.parse(jsonShip)["id"]) {
                    boxes[i].content = '';
                }
            }
            gameField.boxes = boxes;
            resolve();
        });
    },

    snapToBox: function (box, ship) {
        return new Promise(function (resolve) {
            let boxBound = box.getBounds();
            let shipBound = ship.getBounds();
            let newX = boxBound.x;
            let newY = boxBound.y;
            ship.x = newX;
            ship.y = newY;
            ship.originX = newX;
            ship.originY = newY;
            ship.setBounds(newX, newY, shipBound.width, shipBound.height);
            resolve();
        });
    },

    snapToOrigin: function (ship) {
        return new Promise(function (resolve) {
            let shipBound = ship.getBounds();
            let newX = ship.originX;
            let newY = ship.originY;
            ship.x = newX;
            ship.y = newY;
            ship.setBounds(newX, newY, shipBound.width, shipBound.height);
            resolve();
        });
    }
};

function compareValues(box, ship) {
    if (ship.rotation > 0) {
        switchWidthAndHeight(ship);
    }
    let boxBounds = box._bounds;
    let shipBounds = ship._bounds;
    let hit = boxBounds.x < shipBounds.x + shipBounds.width &&
            boxBounds.x + boxBounds.width > shipBounds.x &&
            boxBounds.y < shipBounds.y + shipBounds.height &&
            boxBounds.height + boxBounds.y > shipBounds.y;
    if (ship.rotation > 0) {
        switchWidthAndHeight(ship);
    }
    return hit;
}

function switchWidthAndHeight(ship) {
    let shipBounds = ship._bounds;
    let tmp = shipBounds.height;
    shipBounds.height = shipBounds.width;
    shipBounds.width = tmp;
    ship._bounds = shipBounds;
}

function nextChar(c) {
    return String.fromCharCode(c.charCodeAt(0) + 1);
}

function previousChar(c) {
    return String.fromCharCode(c.charCodeAt(0) - 1);
}

function oneFieldFreeAroundShip(box, ship){

    let boxesAround = getBoxesAround(box, ship.parent.gameField);
    // console.log(JSON.stringify(ship.parent.gameField.boxes));

    let isFree = true;
    boxesAround.forEach(function (boxAround) {
        let foundBox = ship.parent.gameField.boxes.find(item => { return item.id === boxAround });

        if(foundBox.content !== ''){
            if(JSON.parse(foundBox.content)["id"] !== JSON.parse(ship.toJSON())["id"]){
                isFree = false;
            }
        }

    });
    return isFree;
}

function getBoxesAround(box, field){

    let letter = box.id.substring(0,1);
    let number = Number(box.id.substring(1,box.id.length));

    let boxesAround = [];

    if(nextChar(letter) >= 'A' && nextChar(letter) <= 'J'){
        if(number >= 1 && number <= field.boxYCount){
            boxesAround.push(nextChar(letter)+number);
        }
        if((number+1) >= 1 && (number+1) <= field.boxYCount){
            boxesAround.push(nextChar(letter)+(number+1));
        }
        if((number-1) >= 1 &&(number-1) <= field.boxYCount){
            boxesAround.push(nextChar(letter)+(number-1));
        }
    }

    if(previousChar(letter) >= 'A' && previousChar(letter) <= 'J'){
        if(number >= 1 && number <= field.boxYCount){
            boxesAround.push(previousChar(letter)+number);
        }
        if((number+1) >= 1 && (number+1) <= field.boxYCount){
            boxesAround.push(previousChar(letter)+(number+1));
        }
        if((number-1) >= 1 &&(number-1) <= field.boxYCount){
            boxesAround.push(previousChar(letter)+(number-1));
        }
    }

    if(letter >= 'A' && letter <= 'J'){
        if((number+1) >= 1 && (number+1) <= field.boxYCount){
            boxesAround.push(letter+(number+1));
        }
        if((number-1) >= 1 &&(number-1) <= field.boxYCount){
            boxesAround.push(letter+(number-1));
        }
    }

    return boxesAround;
}
