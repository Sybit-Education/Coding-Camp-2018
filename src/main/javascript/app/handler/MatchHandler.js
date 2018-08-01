/* global BASE_URL */

module.exports = {
    connectToMatch: function () {
        let newMatch = new XMLHttpRequest();
        newMatch.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200){
                window.location.href = BASE_URL + "match/" + this.responseText;
            }
        }
        newMatch.open("GET", BASE_URL + "match/newmatch", true);
        newMatch.send();
    },
    saveGamefield: function(matchId, message) {
           //TODO Abfrage um das Spielfeld zu speichern
    }
};
