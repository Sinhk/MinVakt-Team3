/**
 * Created by OlavH on 13-Jan-17.
 */

function isValidPassword(password) {

    var length = password.length;
    var upper = new RegExp(".*\\p{Upper}+.*");
    var lower = new RegExp(".*\\p{Lower}+.*");
    var nonWord = new RegExp(".*\\W{2,}.*");

    return length >= 8 && upper.test(password) && lower.test(password) && nonWord.test(password);

}

function isValidEmail(email) {

    var reg = new RegExp(".+@.+\\..+");

    return reg.test(email);

}