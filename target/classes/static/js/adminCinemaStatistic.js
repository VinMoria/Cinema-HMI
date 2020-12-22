$(document).ready(function () {
    var placeDate = formatDate(new Date());
    var popularDay = 1;
    var popularNum = 1;

    getScheduleRate();

    getBoxOffice();

    getAudiencePrice();

    getPlacingRate();

    getPolularMovie();

    //日期发生变化
    $("#placeRate-date-input").change(function () {
        placeDate = $("#placeRate-date-input").val();
        getPlacingRate();
    });

    //日期发生变化
    $("#popular-day-input").change(function () {
        popularDay = $("#popular-day-input").val();
        getPolularMovie();
    });
    //个数发生变化
    $("#popular-number-input").change(function () {
        popularNum = $("#popular-number-input").val();
        getPolularMovie();
    });

    function getScheduleRate() {

        getRequest(
            '/statistics/scheduleRate',
            function (res) {
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return {
                        value: item.time,
                        name: item.name
                    };
                });
                var nameList = data.map(function (item) {
                    return item.name;
                });
                var option = {
                    title: {
                        text: '今日排片率',
                        subtext: new Date().toLocaleDateString(),
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        x: 'center',
                        y: 'bottom',
                        data: nameList
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            magicType: {
                                show: true,
                                type: ['pie', 'funnel']
                            },
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    calculable: true,
                    series: [
                        {
                            name: '面积模式',
                            type: 'pie',
                            radius: [30, 110],
                            center: ['50%', '50%'],
                            roseType: 'area',
                            data: tableData
                        }
                    ]
                };
                var scheduleRateChart = echarts.init($("#schedule-rate-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function getBoxOffice() {

        getRequest(
            '/statistics/boxOffice/total',
            function (res) {
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return item.boxOffice;
                });
                var nameList = data.map(function (item) {
                    return item.name
                });
                var option = {
                    title: {
                        text: '所有电影票房',
                        subtext: '截止至' + new Date().toLocaleDateString(),
                        x: 'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: tableData,
                        type: 'bar'
                    }]
                };
                var scheduleRateChart = echarts.init($("#box-office-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }

    function getAudiencePrice() {
        getRequest(
            '/statistics/audience/price',
            function (res) {
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return item.price;
                });
                var nameList = data.map(function (item) {
                    return formatDate(new Date(item.date));
                });
                var option = {
                    title: {
                        text: '每日客单价',
                        x: 'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: tableData,
                        type: 'line'
                    }]
                };
                var scheduleRateChart = echarts.init($("#audience-price-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }

    function getPlacingRate() {
        // todo
        //获得日期
        $("#placeRate-date-input").val(placeDate)
        //柱状图
        getRequest(
            '/statistics/PlacingRate?dateString='+placeDate,
            function (res) {
                var data = res.content || [];
                var tableData = data.map(function(item){
                    return item.placeRate
                })
                var nameList = data.map(function (item) {
                    return item.name
                })
                var option = {
                    title: {
                        text: '上座率',
                        x: 'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: tableData,
                        type: 'bar'
                    }]
                };
                var scheduleRateChart = echarts.init($("#place-rate-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error))
            }
        )
    }

    function getPolularMovie() {
        // todo
        //获得天数
        $("#popular-day-input").val(popularDay)
        //获得个数
        $("#popular-number-input").val(popularNum)
        //建立柱状图
        getRequest(
            '/statistics/popular/movie?days='+popularDay+'&movieNum='+popularNum,
            function (res) {
                var data = res.content || []
                var tableData = data.map(function(item){
                    return  item.boxOffice
                })
                var nameList = data.map(function (item) {
                    return item.name
                })
                var option = {
                    title: {
                        text: '最受欢迎电影',
                        x: 'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: tableData,
                        type: 'bar'
                    }]
                };
                var scheduleRateChart = echarts.init($("#popular-movie-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error))
            }
        )
    }
})