package com.graphhopper.reader.gtfs;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;

import com.graphhopper.util.PointList;

public class Road {

	private long startTime;
	private long endTime;
	private int numberOfTransfers;
	private PointList pointList;

	public Road(long startTime, long endTime, int numberOfTransfers,
			PointList pointList) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.pointList = pointList;
		this.numberOfTransfers = numberOfTransfers;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public int getNumberOfTransfers() {
		return numberOfTransfers;
	}

	public PointList getPointList() {
		return pointList;
	}

	@Override
	public String toString() {
		return "Road [startTime=" + startTime + ", endTime=" + endTime
				+ ", numberOfTransfers=" + numberOfTransfers + ", pointList="
				+ pointList + "]";
	}

	public JsonObject toJsonObject() {
		final JsonObjectBuilder builder = JsonProvider.provider()
				.createObjectBuilder();

		builder.add("type", "Feature");

		final JsonObjectBuilder properties = JsonProvider.provider()
				.createObjectBuilder();

		properties.add("startTime", getStartTime());
		properties.add("endTime", getEndTime());
		properties.add("numberOfTransfers", getNumberOfTransfers());
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
