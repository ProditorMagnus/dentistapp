/**
 * Created by Ravana on 12.04.2017.
 */
(function init() {
    if (typeof($) === 'undefined') {
        setTimeout(init, 500);
        return;
    }
    $(function () {
        $('.timepicker').timepicker({
            timeFormat: 'HH:mm',
            interval: 30,
            minTime: '10',
            maxTime: '6:00pm',
            defaultTime: '11',
            startTime: '10:00',
            dynamic: false,
            dropdown: true,
            scrollbar: true
        });

        $('.datepicker').datepicker({
            format: "dd.mm.yyyy",
            weekStart: 1,
            startDate: moment().format('DD.MM.YYYY'),
            daysOfWeekDisabled: "0,6"
        });
    });
})();
