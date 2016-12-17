$(function() {

	// Setup leaflet map
	var map = new L.Map('map');

	var basemapLayer = new L.TileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1Ijoib3JsZXNzIiwiYSI6ImNpaXg4cGt2cTAwMGh2Mm01ZDlqYnk5N28ifQ.EDKxdytV0xTiHyI16K0zsg',
			{attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
			'<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
			'Imagery &copy; <a href="http://mapbox.com">Mapbox</a>',
	id: 'mapbox.streets'

});

	var hslToRgb = function (h, s, l){
		var r, g, b;

		if(s == 0){
			r = g = b = l; // achromatic
		}else{
		var hue2rgb = function hue2rgb(p, q, t){
			if(t < 0) t += 1;
			if(t > 1) t -= 1;
			if(t < 1/6) return p + (q - p) * 6 * t;
			if(t < 1/2) return q;
			if(t < 2/3) return p + (q - p) * (2/3 - t) * 6;
			return p;
			}
	
	        var q = l < 0.5 ? l * (1 + s) : l + s - l * s;
		var p = 2 * l - q;
		r = hue2rgb(p, q, h + 1/3);
		g = hue2rgb(p, q, h);
		b = hue2rgb(p, q, h - 1/3);
		}

		return [Math.round(r * 255), Math.round(g * 255), Math.round(b * 255)];
	};


	// Center map and default zoom level
	map.setView([48.399368824050626, 9.984123929980187], 13);

	// Adds the background layer to the map
	map.addLayer(basemapLayer);

	L.geoJSON(roads, {
		style: function (feature) {
				var value = Math.pow(6 * feature.properties.currentTime/86400, .8);
				var hue = value + (1/3);
				var rgb = hslToRgb(hue, 1, .4);
				var color = 'rgb(' + rgb[0] + ',' + rgb[1] + ',' + rgb[2] + ')';
//				console.log(feature.properties.currentTime + "->" + value + "->" + color);
			return {
				color: color,
				weight: (Math.log(feature.properties.numberOfThreads)/Math.log(2) + 0.5)
			};
		}
	}).addTo(map);

});
