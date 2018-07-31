let Box = require('./Box');



module.exports = class Gamefield {

    constructor(posX, posY, boxPixel, boxXCount, boxYCount, color) {
        this.posX = posX;
        this.posY = posY;
        this.boxPixel = boxPixel;
        this.boxXCount = boxXCount;
        this.boxYCount = boxYCount;
        this.color = color;
        this.boxes = [];
        this.initBoxes();
    }
   //todo Gamefield um field erweitern

    initBoxes() {
        let posX = this.posX;
        let posY = this.posY;
        let color = this.color;
        let field = this.field;
        let boxPixel = this.boxPixel;
        //TODO boxen erzeugen

        for (let i = 0; i < this.boxYCount; i++) {
            for (let i = 0; i < this.boxXCount; i++) {
                let box = new Box (id, posX, posY, pixel, color, field, status);
                this.boxes.push (box);
                posX = posX + boxPixel;
                
            }
            posY = posY + boxPixel;
            posX = this.posX;

        }


    }

    getBoxes() {
        return this.boxes;
    }

    convertToJSON() {
        let boxJsonArray = [];
        for (let box of this.boxes) {
            let boxJson = box.toJSON();
            boxJsonArray.push(boxJson);
        }
        return "{\"gameField\":[" + boxJsonArray + "]}";
    }

    static convertToNumberingScheme(number) {
        let baseChar = ("A").charCodeAt(0),
                letters = "";
        do {
            letters = String.fromCharCode(baseChar + (number % 26)) + letters;
            number = (number / 26) >> 0; // quick `floor`
        } while (number > 0);
        return letters;
    }
};