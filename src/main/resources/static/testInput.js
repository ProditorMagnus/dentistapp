/**
 * Created by Ravana on 13.04.2017.
 */

function fillForm() {
    var denNames = ["den 1", "den 2", "den 3"];
    var docNames = ["doc 1", "doc 2", "doc 3", ""];
    $("#dentistName").val(denNames[Math.floor((Math.random() * denNames.length))]);
    $("#docName").val(docNames[Math.floor((Math.random() * docNames.length))]);
}

function fillValidDate() {
    fillForm();
    $("#visitTime").val(moment().add(1 + Math.floor((Math.random() * 10)), "days").format('DD.MM.YYYY'));
    $("#visitHours").val((10 + Math.floor((Math.random() * 9)) + ":" + (30 * Math.floor(Math.random() + 0.5))));
}
