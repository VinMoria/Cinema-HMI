$(document).ready(function () {
    getMovieList();

    function getMovieList() {
        getRequest(
            '/ticket/get/' + sessionStorage.getItem('id'),
            function (res) {
                renderTicketList(res.content);
            },
            function (error) {
                alert(error);
            });
    }

    // TODO:填空
    function renderTicketList(list) {
        $("#ticketTable").empty()
        list.forEach(function (ticket) {
          addInfo(ticket)
        })
    }

    function addInfo(ticket) {
        getRequest(
            '/schedule/'+ticket.scheduleId,
            function (res) {
                var schedule = res.content;
                var movieName = schedule.movieName;
                var hallName = schedule.hallName;
                var startTime = schedule.startTime;
                var endTime = schedule.endTime;
                render(ticket,movieName,hallName,startTime,endTime)
            },
            function (error) {
                alert(error)
            }
        )
    }

    function render(ticket,movieName,hallName,startTime,endTime) {
        var ticketStr=
            "<tr>"+
            "<td>"+movieName+"</td>"+
            "<td>"+hallName+"</td>"+
            "<td>"+ticket.rowIndex+"排"+ticket.columnIndex+"座"+"</td>"+
            "<td>"+startTime+"</td>>"+//startTime.getMonth()+"月"+startTime.getDate()+"日 "+startTime.getHours+":"+startTime.getMinutes()+"</td>"+
            "<td>"+endTime+"</td>>"+//endTime.getMonth()+"月"+endTime.getDate()+"日 "+endTime.getHours+":"+endTime.getMinutes()+"</td>"+
            "<td>"+ticket.state+"</td>"+
            "</tr>";
        $("#ticketTable").append(ticketStr)
    }

});