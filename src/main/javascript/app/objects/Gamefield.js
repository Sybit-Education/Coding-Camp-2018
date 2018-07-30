let Box = require('./Box');

module.exports = class Gamefield {

    constructor(posX, posY, boxPixel, boxXCount, boxYCount, color, field) {
        //TODO sinnvolle Attribute überlegen
        this.initBoxes();
    }

    initBoxes() {
        let posX = this.posX;
        let posY = this.posY;
        let color = this.color;
        let field = this.field;
        let boxPixel = this.boxPixel;
        //TODO boxen erzeugen
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
