/* global BASE_URL */

module.exports = {
    getCleanBaseUrl: function (baseUrl){
        let baseUrlRegex = /(.*)(;)(.*)/g;
        let match = baseUrlRegex.exec(baseUrl);
        if(match === null){
            return baseUrl;
        }else{
            return match[1] !== null ? match[1] : baseUrl;
        }
    },

    connectToMatch: function () {
        let newMatch = new XMLHttpRequest();
        let baseUrl = this.getCleanBaseUrl(BASE_URL);
        newMatch.onreadystatechange = function() {
            if (this.readyState === 4 && this.status === 200){
                window.location.href = baseUrl + "match/" + this.responseText;
            }
        };
        newMatch.open("GET", baseUrl + "match/newmatch", true);
        newMatch.send();
    },
    saveGamefield: function(matchId, message) {
        let xhttp = new XMLHttpRequest();
        let baseUrl = this.getCleanBaseUrl(BASE_URL);
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                console.log(this.responseText);
                window.location.href = baseUrl + "match/" + this.responseText;
            }
        };
        xhttp.open("POST", baseUrl + "playermatch/"+matchId, true);
        xhttp.send(message);
    }
};
