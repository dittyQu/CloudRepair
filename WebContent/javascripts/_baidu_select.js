function initMap(cust, comp_list) {
	//创建地图函数
	var map = new BMap.Map("map_canvas");//在百度地图容器中创建一个地图
    var point = new BMap.Point(cust.cust_point_x, cust.cust_point_y);//定义一个中心点坐标
    map.centerAndZoom(point,16);//设定地图的中心点和坐标并将地图显示在地图容器中
    window.map = map;//将map变量存储在全局
    
    //地图事件设置函数：
	map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
	map.enableScrollWheelZoom();//启用地图滚轮放大缩小
	map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
	map.enableKeyboard();//启用键盘上下左右键移动地图
	
	//地图控件添加函数：
		//向地图中添加缩放控件
	var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
	map.addControl(ctrl_nav);
	    //向地图中添加缩略图控件
	var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
	map.addControl(ctrl_ove);
	    //向地图中添加比例尺控件
	var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
	map.addControl(ctrl_sca);

	//顧客
	attachCustInfo(map, cust);

	//修理業者
	for (var i = 0; i < 3; i++) {
		attachCompInfo(map, comp_list[i]);
	}
}

function attachCustInfo(map, cust){
    var marker = new BMap.Marker(
    		new BMap.Point(cust.cust_point_x, cust.cust_point_y),
    		{icon: createIcon(21, 21, 0, 0, 6,5)});
    var map_content = tmpl("template-mapinfo-cust", cust);
	var iw = createInfoWindow(map_content);
	var label = new BMap.Label(cust.cust_nm + "　様", {"offset":new BMap.Size(5-6+10, -20)});
	marker.setLabel(label);
    map.addOverlay(marker);
    /*label.setStyle({
                borderColor:"#808080",
                color:"#333",
                cursor:"pointer"
    });*/

    $(function(){
    	var _iw = iw;
    	var _marker = marker;
		_marker.addEventListener("click",function(){
		    this.openInfoWindow(_iw);
	    });
	    _iw.addEventListener("open",function(){
		    _marker.getLabel().hide();
	    })
	    _iw.addEventListener("close",function(){
		    _marker.getLabel().show();
	    })
		label.addEventListener("click",function(){
		    _marker.openInfoWindow(_iw);
	    })
    });
}

function attachCompInfo(map, comp){
	var marker = new BMap.Marker(
    		new BMap.Point(comp.comp_point_x, comp.comp_point_y),
    		{icon: createIcon(21, 21, 46, 0, 6,5)});
    var map_content = tmpl("template-mapinfo-comp", comp);
	var iw = createInfoWindow(map_content);
	var label = new BMap.Label(comp.comp_nm, {"offset":new BMap.Size(5-6+10, -20)});
	marker.setLabel(label);
    map.addOverlay(marker);

    $(function(){
    	var _iw = iw;
    	var _marker = marker;
		_marker.addEventListener("click",function(){
		    this.openInfoWindow(_iw);
	    });
	    _iw.addEventListener("open",function(){
		    _marker.getLabel().hide();
	    })
	    _iw.addEventListener("close",function(){
		    _marker.getLabel().show();
	    })
		label.addEventListener("click",function(){
		    _marker.openInfoWindow(_iw);
	    })
    });
}

//创建一个Icon(w:21,h:21,l:0,t:0,x:6,lb:5)
function createIcon(w, h, l, t, x, lb){
  return new BMap.Icon("http://app.baidu.com/map/images/us_mk_icon.png", 
  		new BMap.Size(w, h),
  		{imageOffset: new BMap.Size(-l,-t),infoWindowOffset:new BMap.Size(lb+5,1),offset:new BMap.Size(x, h)}
  );
}

//创建InfoWindow
function createInfoWindow(content){
    return new BMap.InfoWindow(content);
}
