function initMap(cust, comp_list) {
	var mapOptions = {
		center: new google.maps.LatLng(cust.cust_point_x, cust.cust_point_y),
		zoom:14,
		mapTypeId:google.maps.MapTypeId.TERRAIN// ROADMAP, SATELLITE, HYBRID, TERRAIN
	};
	var locmap = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);

	//顧客
	attachCustInfo(locmap, cust);

	//修理業者
	for (var i = 0; i < 3; i++) {
		attachCompInfo(locmap, comp_list[i]);
	}
}

function attachCustInfo(locmap, cust){
	var cust_icon = 'http://maps.google.com/mapfiles/marker_orange.png';
	var marker = new google.maps.Marker({
		position: new google.maps.LatLng(cust.cust_point_x, cust.cust_point_y),
		map: locmap,
		title: cust.cust_nm + "　様",
		icon: cust_icon,
		draggable:false,
		});
	var map_content = tmpl("template-mapinfo-cust", cust);
	var infowindow = new google.maps.InfoWindow({
		content: map_content,
		size: new google.maps.Size(50,50)
 	});
 	google.maps.event.addListener(marker, 'click', function() {
 		infowindow.open(locmap,marker);
	});
}

function attachCompInfo(locmap, comp){
	var comp_icon = 'http://maps.google.com/mapfiles/marker.png';
	var marker = new google.maps.Marker({
		position: new google.maps.LatLng(comp.comp_point_x,comp.comp_point_y),
		map:locmap,
		title: comp.comp_nm,
		icon: comp_icon,
		draggable:false,
		});

	var map_content = tmpl("template-mapinfo-comp", comp);
	var infowindow = new google.maps.InfoWindow({
		content: map_content,
		size: new google.maps.Size(50,50)
 	});
 	google.maps.event.addListener(marker, 'click', function() {
 		infowindow.open(locmap,marker);
	});
}