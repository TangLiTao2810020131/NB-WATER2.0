<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title> New Document </title>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="Generator" content="EditPlus">
    <meta name="Author" content="">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <script src="${ctx}/resources/echarts/echarts.min.js"></script>
    <script src="${ctx}/resources/echarts/jquery-3.3.1.min.js"></script>

</head>

<body>
<div id="chart-panel" style="width: 1366px;height:900px;"></div>
</body>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    // var myChart = echarts.init(document.getElementById('main'));
    var zhongguo = "${ctx}/resources/echarts/map/data-china.json";
    var hainan = "${ctx}/resources/echarts/map/data-hainan.json";
    var xizang = "${ctx}/resources/echarts/map/data-xizang.json";
    var zhejiang = "${ctx}/resources/echarts/map/data-zhejiang.json";
    var yunnan = "${ctx}/resources/echarts/map/data-yunnan.json";
    var xinjiang = "${ctx}/resources/echarts/map/data-xinjiang.json";
    var tianjin = "${ctx}/resources/echarts/map/data-tianjin.json";
    var sichuan = "${ctx}/resources/echarts/map/data-sichuan.json";
    var shanxi = "${ctx}/resources/echarts/map/data-shanxi.json";
    var shangxi = "${ctx}/resources/echarts/map/data-shangxi.json";
    var shanghai = "${ctx}/resources/echarts/map/data-shanghai.json";
    var shangdong = "${ctx}/resources/echarts/map/data-shangdong.json";
    var qinghai = "${ctx}/resources/echarts/map/data-qinghai.json";
    var ningxia = "${ctx}/resources/echarts/map/data-ningxia.json";
    var neimenggu = "${ctx}/resources/echarts/map/data-neimenggu.json";
    var liaoning = "${ctx}/resources/echarts/map/data-liaoning.json";
    var jilin = "${ctx}/resources/echarts/map/data-jilin.json";
    var jiangxi = "${ctx}/resources/echarts/map/data-jiangxi.json";
    var jiangsu = "${ctx}/resources/echarts/map/data-jiangsu.json";
    var hunan = "${ctx}/resources/echarts/map/data-hunan.json";
    var hubei = "${ctx}/resources/echarts/map/data-hubei.json";
    var henan = "${ctx}/resources/echarts/map/data-henan.json";
    var heilongjiang = "${ctx}/resources/echarts/map/data-heilongjiang.json";
    var hebei = "${ctx}/resources/echarts/map/data-hebei.json";
    var guizhou = "${ctx}/resources/echarts/map/data-guizhou.json";
    var guangxi = "${ctx}/resources/echarts/map/data-guangxi.json";
    var guangdong = "${ctx}/resources/echarts/map/data-guangdong.json";
    var gansu = "${ctx}/resources/echarts/map/data-gansu.json";
    var chongqing = "${ctx}/resources/echarts/map/data-chongqing.json";
    var aomen = "${ctx}/resources/echarts/map/data-.json";
    var anhui = "${ctx}/resources/echarts/map/data-anhui.json";
    var beijing = "${ctx}/resources/echarts/map/data-beijing.json";
    var fujian = "${ctx}/resources/echarts/map/data-fujian.json";
    var xianggang = "${ctx}/resources/echarts/map/data-xianggang.json";

    var hefei = "${ctx}/resources/echarts/map/city/340100.json";

    echarts.extendsMap = function(id, opt) {
        // 实例
        var chart = this.init(document.getElementById(id));

        var curGeoJson = {};
        /*
        var cityMap = {
            '中国': zhongguo,
            '上海': shanghai,
            '河北': hebei,
            '山西': shangxi,
            '内蒙古': neimenggu,
            '辽宁': liaoning,
            '吉林': jilin,
            '黑龙江': heilongjiang,
            '江苏': jiangsu,
            '浙江': zhejiang,
            '安徽': anhui,
            '福建': fujian,
            '江西': jiangxi,
            '山东': shangdong,
            '河南': henan,
            '湖北': hubei,
            '湖南': hunan,
            '广东': guangdong,
            '广西': guangxi,
            '海南': hainan,
            '四川': sichuan,
            '贵州': guizhou,
            '云南': yunnan,
            '西藏': xizang,
            '陕西': shanxi,
            '甘肃': gansu,
            '青海': qinghai,
            '宁夏': ningxia,
            '新疆': xinjiang,
            '北京': beijing,
            '天津': tianjin,
            '重庆': chongqing,
            '香港': xianggang,
            '澳门': aomen,
            '合肥市': hefei
        };
        */
        var geoCoordMap = {
            "磴口": [107.012225,40.337792],
            "沈海": [123.476404,41.811854],
            "盘锦": [122.150954,41.146597],
            "温州苍南": [120.434446,27.523467],
            "沧州": [116.845318,38.310486],
            "曹妃甸": [118.504805,39.004067],
            "唐山丰润": [118.088961,39.599362],
            "渤海新区": [117.639472,38.365606],
            "菏泽": [115.182225,35.326397],
            "海丰": [115.050769,22.762251],
            "贺州": [111.371802,24.741613],
            "鲤鱼江A": [113.233978,25.942699],
            "鲤鱼江B": [113.205002,25.440064],
            "广州热电": [113.535842,22.863521],
            "湖北一期": [113.888854,29.667558],
            "湖北二期": [114.119833,29.669091],
            "涟源": [111.906695,27.767901],
            "宜昌": [111.456005,30.50378],
            "徐州一、二期": [117.091077,34.378713],
            "南京热电": [118.828047,32.273218],
            "宜兴": [119.801641,31.36496],
            "镇江": [119.383774,32.188708],
            "华鑫": [113.888833,29.669091],
            "常熟": [119.283774,32.188708],
            "化工园一、二期": [118.927993,32.27431],
            "南京板桥": [118.64423,31.953574],
            "徐州三期": [117.291077,34.378713],
            "六枝": [105.403904,26.387848],
            "首阳山": [112.695363,34.744379],
            "焦作": [113.113088,35.222327],
            "登封一、二期": [113.215689,34.396153],
            "洛阳": [111.984763,34.746303],
            "古城": [114.061156,32.878627],
        };

        var levelColorMap = {
            '1': 'rgba(241, 109, 115, .8)',
            '2': 'rgba(255, 235, 59, .7)',
            '3': 'rgba(147, 235, 248, 1)'
        };

        var defaultOpt = {
            mapName: 'china', // 地图展示
            goDown: true, // 是否下钻
            bgColor: '#404a59', // 画布背景色
            activeArea: [], // 区域高亮,同echarts配置项
            data: [],
            // 下钻回调(点击的地图名、实例对象option、实例对象)
            callback: function(name, option, instance) {
                alert("123");
            }
        };
        if (opt) opt = this.util.extend(defaultOpt, opt);

        // 层级索引
        var name = [opt.mapName];
        var idx = 0;
        var pos = {
            leftPlus: 115,
            leftCur: 150,
            left: 198,
            top: 50
        };

        var line = [
            [0, 0],
            [8, 11],
            [0, 22]
        ];
        // style
        var style = {
            font: '18px "Microsoft YaHei", sans-serif',
            textColor: '#eee',
            lineColor: 'rgba(147, 235, 248, .8)'
        };

        var handleEvents = {
            /**
             * i 实例对象
             * o option
             * n 地图名
             **/
            resetOption: function(i, o, n) {
                var breadcrumb = this.createBreadcrumb(n);
                var j = name.indexOf(n);
                var l = o.graphic.length;
                if (j < 0) {
                    o.graphic.push(breadcrumb);
                    o.graphic[0].children[0].shape.x2 = 145;
                    o.graphic[0].children[1].shape.x2 = 145;
                    if (o.graphic.length > 2) {
                        var cityData = [];
                        var cityJson;
                        for (var x = 0; x < opt.data.length; x++) {
                            if(n === opt.data[x].city){
                                $([opt.data[x]]).each(function(index,data){
                                    cityJson = {city:data.city,name:data.name,value:data.value,crew:data.crew,company:data.company,position:data.position,region:data.region};
                                    cityData.push(cityJson)
                                })
                            }
                        }

                        if(cityData!=null){
                            o.series[0].data = handleEvents.initSeriesData(cityData);
                        }else{
                            o.series[0].data = [];
                        }


                    }
                    name.push(n);
                    idx++;
                } else {
                    o.graphic.splice(j + 2, l);
                    if (o.graphic.length <= 2) {
                        o.graphic[0].children[0].shape.x2 = 60;
                        o.graphic[0].children[1].shape.x2 = 60;
                        o.series[0].data = handleEvents.initSeriesData(opt.data);
                    };
                    name.splice(j + 1, l);
                    idx = j;
                    pos.leftCur -= pos.leftPlus * (l - j - 1);
                };

                o.geo.map = n;
                o.geo.zoom = 0.4;
                i.clear();
                i.setOption(o);
                this.zoomAnimation();
                opt.callback(n, o, i);
            },

            /**
             * name 地图名
             **/
            createBreadcrumb: function(name) {
                var cityToPinyin = {
                    '中国': 'zhongguo',

                };
                var breadcrumb = {
                    type: 'group',
                    id: name,
                    left: pos.leftCur + pos.leftPlus,
                    top: pos.top + 3,
                    children: [{
                        type: 'polyline',
                        left: -90,
                        top: -5,
                        shape: {
                            points: line
                        },
                        style: {
                            stroke: '#fff',
                            key: name
                            // lineWidth: 2,
                        },
                        onclick: function() {
                            var name = this.style.key;
                            handleEvents.resetOption(chart, option, name);
                        }
                    }, {
                        type: 'text',
                        left: -68,
                        top: 'middle',
                        style: {
                            text: name,
                            textAlign: 'center',
                            fill: style.textColor,
                            font: style.font
                        },
                        onclick: function() {
                            var name = this.style.text;
                            handleEvents.resetOption(chart, option, name);
                        }
                    }, {
                        type: 'text',
                        left: -68,
                        top: 10,
                        style: {

                            name: name,
                            text: cityToPinyin[name] ? cityToPinyin[name].toUpperCase() : '',
                            textAlign: 'center',
                            fill: style.textColor,
                            font: '12px "Microsoft YaHei", sans-serif',
                        },
                        onclick: function() {
                            // console.log(this.style);
                            var name = this.style.name;
                            handleEvents.resetOption(chart, option, name);
                        }
                    }]
                }

                pos.leftCur += pos.leftPlus;

                return breadcrumb;
            },

            // 设置effectscatter
            initSeriesData: function(data) {
                var temp = [];
                for (var i = 0; i < data.length; i++) {
                    var geoCoord = geoCoordMap[data[i].name];
                    if (geoCoord) {
                        temp.push({
                            name: data[i].name,
                            value: geoCoord.concat(data[i].value),
                            crew:data[i].crew,
                            company:data[i].company,
                            position:data[i].position,
                            region:data[i].region
                        });
                    }
                };
                return temp;
            },
            zoomAnimation: function() {
                var count = null;
                var zoom = function(per) {
                    if (!count) count = per;
                    count = count + per;
                    // console.log(per,count);
                    chart.setOption({
                        geo: {
                            zoom: count
                        }
                    });
                    if (count < 1) window.requestAnimationFrame(function() {
                        zoom(0.2);
                    });
                };
                window.requestAnimationFrame(function() {
                    zoom(0.2);
                });
            }
        };

        var option = {
            backgroundColor: opt.bgColor,
            tooltip: {
                show: true,
                trigger:'item',
                alwaysShowContent:false,
                backgroundColor:'rgba(50,50,50,0.7)',
                hideDelay:100,
                triggerOn:'mousemove',
                enterable:true,
                position:['60%','70%'],
                formatter:function(params, ticket, callback){
                    return '简称：'+params.data.name+'<br/>'+'机组：'+params.data.crew+'万千瓦'+'<br/>'+'公司名称：'+params.data.company+'<br/>'+'所属大区：'+params.data.region+'<br/>'+'所在位置：'+params.data.position
                }
            },
            graphic: [{
                type: 'group',
                left: pos.left,
                top: pos.top - 4,
                children: [{
                    type: 'line',
                    left: 0,
                    top: -20,
                    shape: {
                        x1: 0,
                        y1: 0,
                        x2: 60,
                        y2: 0
                    },
                    style: {
                        stroke: style.lineColor,
                    }
                }, {
                    type: 'line',
                    left: 0,
                    top: 20,
                    shape: {
                        x1: 0,
                        y1: 0,
                        x2: 60,
                        y2: 0
                    },
                    style: {
                        stroke: style.lineColor,
                    }
                }]
            },
                {
                    id: name[idx],
                    type: 'group',
                    left: pos.left + 2,
                    top: pos.top,
                    children: [{
                        type: 'polyline',
                        left: 90,
                        top: -12,
                        shape: {
                            points: line
                        },
                        style: {
                            stroke: 'transparent',
                            key: name[0]
                        },
                        onclick: function() {
                            var name = this.style.key;
                            handleEvents.resetOption(chart, option, name);
                        }
                    }, {
                        type: 'text',
                        left: 0,
                        top: 'middle',
                        style: {
                            text: '中国',
                            textAlign: 'center',
                            fill: style.textColor,
                            font: style.font
                        },
                        onclick: function() {
                            handleEvents.resetOption(chart, option, '中国');
                        }
                    }, {
                        type: 'text',
                        left: 0,
                        top: 10,
                        style: {
                            text: 'China',
                            textAlign: 'center',
                            fill: style.textColor,
                            font: '12px "Microsoft YaHei", sans-serif',
                        },
                        onclick: function() {
                            handleEvents.resetOption(chart, option, '中国');
                        }
                    }]
                }],
            geo: {
                map: opt.mapName,
                roam: true,
                zoom: 1,
                label: {
                    normal: {
                        show: true,
                        textStyle: {
                            color: '#fff'
                        }
                    },
                    emphasis: {
                        textStyle: {
                            color: '#fff'
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        borderColor: 'rgba(147, 235, 248, 1)',
                        borderWidth: 1,
                        areaColor: {
                            type: 'radial',
                            x: 0.5,
                            y: 0.5,
                            r: 0.8,
                            colorStops: [{
                                offset: 0,
                                color: 'rgba(147, 235, 248, 0)' // 0% 处的颜色
                            }, {
                                offset: 1,
                                color: 'rgba(147, 235, 248, .2)' // 100% 处的颜色
                            }],
                            globalCoord: false // 缺省为 false
                        },
                        shadowColor: 'rgba(128, 217, 248, 1)',
                        // shadowColor: 'rgba(255, 255, 255, 1)',
                        shadowOffsetX: -2,
                        shadowOffsetY: 2,
                        shadowBlur: 10
                    },
                    emphasis: {
                        areaColor: '#389BB7',
                        borderWidth: 0
                    }
                },
                regions: opt.activeArea.map(function(item) {
                    if (typeof item !== 'string') {
                        return {
                            name: item.name,
                            itemStyle: {
                                normal: {
                                    areaColor: item.areaColor || '#389BB7'
                                }
                            },
                            label: {
                                normal: {
                                    show: item.showLabel,
                                    textStyle: {
                                        color: '#fff'
                                    }
                                }
                            }
                        }
                    } else {
                        return {
                            name: item,
                            itemStyle: {
                                normal: {
                                    borderColor: '#91e6ff',
                                    areaColor: '#389BB7'
                                }
                            }
                        }
                    }
                })
            },
            series: [{
                type: 'effectScatter',
                coordinateSystem: 'geo',
                showEffectOn: 'render',
                rippleEffect: {
                    period:15,
                    scale: 4,
                    brushType: 'fill'
                },
                hoverAnimation: true,
                itemStyle: {
                    normal: {
                        color: '#FFC848',
                        shadowBlur: 10,
                        shadowColor: '#333'
                    }
                },
                data: handleEvents.initSeriesData(opt.data)
            }]
        };

        chart.setOption(option);
        // 添加事件
        chart.on('click', function(params) {
            var _self = this;
            if (opt.goDown && params.name !== name[idx]) {
                if (cityMap[params.name]) {
                    var url = cityMap[params.name];
                   // alert(url);
                    if (isNumber(url)) url = "${ctx}/resources/echarts/map/city/"+url+".json";

                    //alert(url);
                    $.get(url, function(response) {
                        //console.log(response);
                        curGeoJson = response;
                        echarts.registerMap(params.name, response);
                        handleEvents.resetOption(_self, option, params.name);
                    });
                }
            }
        });

        chart.setMap = function(mapName) {
            var _self = this;
            if (mapName.indexOf('市') < 0) mapName = mapName + '市';
            var citySource = cityMap[mapName];
            if (citySource) {
                var url = './map/' + citySource + '.json';
                $.get(url, function(response) {
                    // console.log(response);
                    curGeoJson = response;
                    echarts.registerMap(mapName, response);
                    handleEvents.resetOption(_self, option, mapName);
                });
            }

        };

        return chart;
    };

    $.getJSON(zhongguo, function(geoJson) {
        echarts.registerMap('中国', geoJson);
        var myChart = echarts.extendsMap('chart-panel', {
            bgColor: '#154e90', // 画布背景色
            mapName: '中国', // 地图名
            text:'火电业务',
            goDown: true, // 是否下钻
            // 下钻回调
            callback: function(name, option, instance) {
                console.log(name, option, instance);
            },
            // 数据展示
            data: [{city:'内蒙古',name: "磴口",value: 2*30,crew:'2*30',company:"磴口金牛煤电",position:'内蒙古自治区巴彦淖尔市磴口县',region:'北方大区'},
                {city:'辽宁',name: "沈海",value: 3*20,crew:'3*20',company:"沈阳沈海热电",position:'辽宁省沈阳市大东区',region:'东北大区'},
                {city:'辽宁',name: "盘锦",value: 2*35,crew:'2*35',company:"华润电力盘锦",position:'辽宁省盘锦市大洼区后胡嘴子',region:'东北大区'},
                {city:'浙江',name: "温州苍南",value: 2*100,crew:'2*100',company:"华润电力(温州)有限公司",position:'浙江省温州市苍南县龙港镇',region:'东南大区'},
                {city:'河北',name: "沧州",value: 2*33,crew:'2*33',company:"沧州华润热电",position:'河北省沧州市运河区北环西路',region:'华北大区'},
                {city:'河北',name: "曹妃甸",value: 2*30,crew:'2*30',company:"唐山曹妃甸",position:'河北省唐山市曹妃甸工业区',region:'华北大区'},
                {city:'河北',name: "唐山丰润",value: 2*35,crew:'2*35',company:"华润电力唐山丰润",position:'河北省唐山市路北区韩城镇',region:'华北大区'},
                {city:'河北',name: "渤海新区",value: 2*35,crew:'2*35',company:"渤海华润电力",position:'河北省沧州市临港经济技术开发区',region:'华北大区'},
                {city:'山东',name: "菏泽",value: 2*60,crew:'2*60',company:"华润热电（菏泽）有限公司",position:'山东省菏泽市东明县武胜桥镇',region:'华东大区'},
                {city:'安徽',name: "阜阳",value: 2*64,crew:'2*64',company:"阜阳华润电力有限公司",position:'安徽省阜阳市颍泉区',region:'华东大区'},
                {city:'广东',name: "海丰",value: 2*100,crew:'2*100',company:"华润电力(海丰)有限公司",position:'广东省汕尾市海丰县小漠镇',region:'华南大区'},
                {city:'广西',name: "贺州",value: 2*100,crew:'2*100',company:"华润电力(贺州)有限公司",position:'广西贺州市贺州大道',region:'华南大区'},
                {city:'湖南',name: "鲤鱼江A",value: 2*30,crew:'2*30',company:"华润电力湖南有限公司",position:'湖南省资兴市鲤鱼江镇',region:'华南大区'},
                {city:'湖南',name: "鲤鱼江B",value: 2*65,crew:'2*65',company:"湖南华润电力鲤鱼江有限公司",position:'湖南省资兴市鲤鱼江镇',region:'华南大区'},
                {city:'广东',name: "广州热电",value: 2*30,crew:'2*30',company:"广州华润热电有限公司",position:'广州市南沙区黄阁镇',region:'华南大区'},
                {city:'湖北',name: "湖北一期",value: 2*30,crew:'2*30',company:"华润电力湖北有限公司",position:'湖北省赤壁市陆水大道99号',region:'华中大区'},
                {city:'湖北',name: "湖北二期",value: 2*100,crew:'2*100',company:"华润电力湖北有限公司二期",position:'湖北省赤壁市陆水大道99号',region:'华中大区'},
                {city:'湖南',name: "涟源",value: 2*30,crew:'2*30',company:"华润电力(涟源)有限公司",position:'湖南省娄底市涟源市',region:'华中大区'},
                {city:'湖北',name: "宜昌",value: 2*35,crew:'2*35',company:"华润电力(宜昌)有限公司",position:'湖北省宜昌市猇亭区',region:'华中大区'},
                {city:'江苏',name: "徐州一、二期",value: 4*32,crew:'4*32',company:"徐州华润电力有限公司",position:'江苏省徐州市华润路1号',region:'江苏大区'},
                {city:'江苏',name: "南京热电",value: 2*60,crew:'2*60',company:"江苏南热发电有限责任公司",position:'江苏省南京市六合区',region:'江苏大区'},
                {city:'江苏',name: "宜兴",value: 2*15,crew:'2*6',company:"宜兴华润热电有限公司",position:'江苏省无锡市宜兴市环保科技工业园',region:'江苏大区'},
                {city:'江苏',name: "镇江",value: 2*63+2*13.5,crew:'2*63+2*13.5',company:"江苏镇江发电有限公司",position:'江苏省镇江市丹徒区高资镇',region:'江苏大区'},
                {city:'湖北',name: "华鑫",value: 2*33,crew:'2*33',company:"徐州华鑫发电有限公司",position:'徐州市铜山区茅村工业园',region:'江苏大区'},
                {city:'江苏',name: "常熟",value: 2*65,crew:'2*65',company:"华润电力(常熟)有限公司",position:'江苏省常熟市珠江东路',region:'江苏大区'},
                {city:'江苏',name: "化工园一、二期",value: 2*5.5+2*30,crew:'2*5.5+2*30',company:"南京化学工业园热电有限公司",position:'南京市雨花经济开发区',region:'江苏大区'},
                {city:'江苏',name: "南京板桥",value: 2*33,crew:'2*33',company:"南京华润热电有限公司",position:'江苏省徐州市铜山区',region:'江苏大区'},
                {city:'江苏',name: "徐州三期",value: 2*100,crew:'2*100',company:"铜山华润电力有限公司",position:'江苏省徐州市铜山区',region:'江苏大区'},
                {city:'贵州',name: "六枝",value: 2*66,crew:'2*66',company:"六枝华润电力",position:'贵州省六盘水市六枝特区岩脚镇',region:'西南大区'},
                {city:'河南',name: "首阳山",value: 2*60,crew:'2*60',company:"河南华润电力首阳山有限公司",position:'河南省偃师市首阳山镇',region:'中西大区'},
                {city:'河南',name: "焦作",value: 2*66,crew:'2*66',company:"华润电力焦作有限公司",position:'河南省焦作市博爱县柏山镇',region:'中西大区'},
                {city:'河南',name: "登封一、二期",value: 2*32+2*60,crew:'2*32+2*60',company:"华润电力登封有限公司",position:'河南省登封市东刘碑村',region:'中西大区'},
                {city:'河南',name: "洛阳",value: 2*15,crew:'2*5',company:"洛阳华润环保能源有限公司",position:'河南省偃师市城关工业区',region:'中西大区'},
                {city:'河南',name: "古城",value: 2*30,crew:'2*30',company:"河南华润电力古城有限公司",position:'河南驻马店市驿城区古城乡',region:'中西大区'}]
        });
    })
    /*分享需要的json数据：https://share.weiyun.com/5x12K4r*/

    // 使用刚指定的配置项和数据显示图表。
    //myChart.setOption(chart);
</script>
</html>
<script src="${ctx}/resources/echarts/map/city/china-main-city-map.js"></script>