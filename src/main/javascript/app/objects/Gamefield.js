let Box = require('./Box');



module.exports = class Gamefield {

    constructor(posX, posY, boxPixel, boxXCount, boxYCount, color, field) {
        this.posX = posX;
        this.posY = posY;
        this.boxPixel = boxPixel;
        this.boxXCount = boxXCount;
        this.boxYCount = boxYCount;
        this.color = color;
        this.field = field;
        this.boxes = [];
        this.initBoxes();
    }

    initBoxes() {
        let posX = this.posX;
        let posY = this.posY;
        let color = this.color;
        let field = this.field;
        let boxPixel = this.boxPixel;

        for (let j = 0; j < this.boxYCount; j++) {
            for (let i = 0; i < this.boxXCount; i++) {
                let name = Gamefield.convertToNumberingScheme(i) + (j+1);
                let box = new Box (name, posX, posY, boxPixel, color, field);
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