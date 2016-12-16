$(function() {

	// Setup leaflet map
	var map = new L.Map('map');

	var basemapLayer = new L.TileLayer('https://{s}.tiles.mapbox.com/v3/github.map-xgq2svrz/{z}/{x}/{y}.png',
			{attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
			'<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
			'Imagery &copy; <a href="http://mapbox.com">Mapbox</a>'});

	// Center map and default zoom level
	map.setView([48.399368824050626, 9.984123929980187], 13);

	// Adds the background layer to the map
	map.addLayer(basemapLayer);

	L.geoJSON(roads).addTo(map);

});
