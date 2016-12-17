$(function() {

	// Setup leaflet map
	var map = new L.Map('map');

	var basemapLayer = new L.TileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1Ijoib3JsZXNzIiwiYSI6ImNpaXg4cGt2cTAwMGh2Mm01ZDlqYnk5N28ifQ.EDKxdytV0xTiHyI16K0zsg',
			{attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
			'<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
			'Imagery &copy; <a href="http://mapbox.com">Mapbox</a>',
	id: 'mapbox.streets'

});

	// Center map and default zoom level
	map.setView([52.5143168, 13.4191091], 13);

	// Adds the background layer to the map
	map.addLayer(basemapLayer);

	L.geoJSON(roads).addTo(map);

});
