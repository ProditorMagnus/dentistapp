/**
 * Created by Ravana on 13.04.2017.
 */

function fillForm() {
    var denNames = ["Praxis Dionysus", "Mneme Carme", "Aruna Helios", "Medousa Phrixos"];
    var docNames = ["Durga Patroklos", "Soroush Praxis", "Pekko Evandrus", ""];
    $("#dentistName").val(denNames[Math.floor((Math.random() * denNames.length))]);
    $("#docName").val(docNames[Math.floor((Math.random() * docNames.length))]);
}

function fillValidDate() {
    fillForm();
    var date = moment();
    do {
        date = date.add(1 + Math.floor((Math.random() * 3)), "days");
    } while (date.isoWeekday() === 6 || date.isoWeekday() === 7);
    $("#visitTime").val(date.format('DD.MM.YYYY'));
    $("#visitHours").val((10 + Math.floor((Math.random() * 9)) + ":" + (30 * Math.floor(Math.random() + 0.5))));
}
