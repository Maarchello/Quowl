function incrementString(str) {
    var parseStr = parseInt(str);
    return ++parseStr;
}

function decrementString(str) {
    var parseStr = parseInt(str);
    parseStr -= 1;
    if (parseStr <= 0) return 0;
    return parseStr;
}