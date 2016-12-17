package com.graphhopper.reader.gtfs;

import java.util.Collection;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;

public class Roads {

	private final Collection<Road> roads;

	public Roads(Collection<Road> roads) {
		this.roads = roads;
	}

	public Collection<Road> getRoads() {
		return roads;
	}

	public JsonObject toJsonObject() {
		final JsonObjectBuilder builder = JsonProvider.provider()
				.createObjectBuilder();

		builder.add("type", "FeatureCollection");

		JsonArrayBuilder featuresBuilder = JsonProvider.provider()
				.createArrayBuilder();

		getRoads().stream().map(Road::toJsonObject)
				.forEach(featuresBuilder::add);

		builder.add("features", featuresBuilder.build());

		return builder.build();
	}

}
