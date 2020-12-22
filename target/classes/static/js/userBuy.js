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
        var date = new Date(startTime);
        var h = date.getHours();
        if(h<10) h = "0"+h;
        var m = date.getMinutes();
        if(m<10) m = "0"+m;
        var startStr = date.getMonth()+"月"+date.getDay()+"日 "+h+":"+m+" -- ";
        date = new Date(endTime);
        h = date.getHours();
        if(h<10) h = "0"+h;
        m = date.getMinutes();
        if(m<10) m = "0"+m;
        var endStr = date.getMonth()+"月"+date.getDay()+"日 "+h+":"+m;
        var ticketStr=
            "<div class='ticket'>"+
            "<p style='font-size:25px;color:#1caf9a'>"+movieName+"</p>"+
            "<p style='font-size:20px;'>"+hallName+'  '+ticket.rowIndex+"排"+ticket.columnIndex+"座"+"</p>"+
            "<p>"+startStr+"</p>"+//startTime.getMonth()+"月"+startTime.getDate()+"日 "+startTime.getHours+":"+startTime.getMinutes()+"</td>"+
            "<p>"+endStr+"</p>"+//endTime.getMonth()+"月"+endTime.getDate()+"日 "+endTime.getHours+":"+endTime.getMinutes()+"</td>"+
            "<p>"+ticket.state+"</p>"+
            "</div>";
        $("#ticketTable").append(ticketStr)
    }

});