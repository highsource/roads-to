package com.graphhopper.reader.gtfs;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;

import com.graphhopper.util.PointList;

public class Road {

	private long currentTime;
	private int numberOfTransfers;
	private PointList pointList;
	private int numberOfThreads = 1;

	public Road(long startTime, int numberOfTransfers, PointList pointList) {
		this.currentTime = startTime;
		this.pointList = pointList;
		this.numberOfTransfers = numberOfTransfers;
	}

	public Road increadNumberOfThreads() {
		this.numberOfThreads++;
		return this;
	}

	public long getCurrentTime() {
		return currentTime;
	}

	public int getNumberOfTransfers() {
		return numberOfTransfers;
	}

	public int getNumberOfThreads() {
		return numberOfThreads;
	}

	public PointList getPointList() {
		return pointList;
	}

	@Override
	public String toString() {
		return "Road [currentTime=" + currentTime + ", numberOfTransfers="
				+ numberOfTransfers + ", pointList=" + pointList
				+ ", numberOfThreads=" + numberOfThreads + "]";
	}

	public JsonObject toJsonObject() {
		final JsonObjectBuilder builder = JsonProvider.provider()
				.createObjectBuilder();

		builder.add("type", "Feature");

		final JsonObjectBuilder properties = JsonProvider.provider()
				.createObjectBuilder();

		properties.add("currentTime", getCurrentTime());
		properties.add("numberOfTransfers", getNumberOfTransfers());
		properties.add("numberOfThreads", getNumberOfThreads());
		builder.add("properties", properties);

		JsonObjectBuilder geometryBuilder = JsonProvider.provider()
				.createObjectBuilder();
		geometryBuilder.add("type", "LineString");
		JsonArrayBuilder coordinatesBuilder = JsonProvider.provider()
				.createArrayBuilder();
		for (int index = 0; index < getPointList().size(); index++) {
			JsonArrayBuilder coordinateBuilder = JsonProvider.provider()
					.createArrayBuilder();
			coordinateBuilder.add(getPointList().getLon(index));
			coordinateBuilder.add(getPointList().getLat(index));
			coordinatesBuilder.add(coordinateBuilder.build());
		}
		geometryBuilder.add("coordinates", coordinatesBuilder.build());

		builder.add("geometry", geometryBuilder.build());
		return builder.build();
	}
}
