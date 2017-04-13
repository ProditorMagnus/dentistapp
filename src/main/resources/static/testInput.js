/**
 * Created by Ravana on 13.04.2017.
 */

function fillForm() {
    var denNames = ["den 1", "den 2", "den 3"];
    var docNames = ["doc 1", "doc 2", "doc 3", ""];
    $("#dentistName").val(denNames[Math.floor((Math.random() * denNames.length))]);
    $("#docName").val(docNames[Math.floor((Math.random() * docNames.length))]);
}
