package com.graphhopper.reader.gtfs;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.graphhopper.storage.GraphHopperStorage;
import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.PointList;

public class RoadBuilder {

	private final GraphHopperStorage storage;

	public RoadBuilder(GraphHopperStorage storage) {
		this.storage = storage;
	}

	private Map<PointList, Label> labels = new HashMap<>();

	private Map<PointList, Road> roads = new HashMap<>();

	public void addLabel(Label label) {
		double lastLat = storage.getNodeAccess().getLat(label.adjNode);
		double lastLon = storage.getNodeAccess().getLon(label.adjNode);
		final PointList lastPoint = new PointList(1, false);
		lastPoint.add(lastLat, lastLon);

		labels.compute(lastPoint, (key, oldLabel) -> {
			if (oldLabel == null) {
				return label;
			} else if (label.currentTime < oldLabel.currentTime) {

				return label;
			} else {
				return oldLabel;
			}
		});
	}

	public Roads buildRoads() {
		this.labels.values().forEach(this::buildRoads);
		return new Roads(this.roads.values());
	}

	private void buildRoads(Label label) {
		if (label == null || label.edge == -1) {
			return;
		}
		long currentTime;
		final PointList pointList = new PointList();
		int numberOfTransfers = 0;

		double lastLat = Double.NaN;
		double lastLon = Double.NaN;
		Label currentLabel = label;
		currentTime = currentLabel.currentTime;
		do {
			numberOfTransfers += currentLabel.nTransfers;
			final EdgeIteratorState state = storage.getEdgeIteratorState(
					currentLabel.edge, currentLabel.adjNode);
			final PointList geometry = state.fetchWayGeometry(3);
			for (int index = geometry.getSize() - 1; index >= 0; index--) {
				double currentLat = geometry.getLat(index);
				double currentLon = geometry.getLon(index);
				if (Double.doubleToLongBits(currentLat) != Double
						.doubleToLongBits(lastLat)
						&& Double.doubleToLongBits(currentLon) != Double
								.doubleToLongBits(lastLon)) {
					pointList.add(currentLat, currentLon);
				}
				lastLat = currentLat;
				lastLon = currentLon;

			}
			currentLabel = currentLabel.parent;
		} while (currentLabel != null && currentLabel.edge != -1
				&& pointList.size() < 2);
		if (pointList.size() < 2) {
			System.out.println(MessageFormat.format("Point list size is {0}.",
					pointList.size()));
		}

		pointList.reverse();
		final Road road = new Road(currentTime, numberOfTransfers, pointList);

		roads.compute(road.getPointList(), (key, oldRoad) -> {
			if (oldRoad == null) {
				return road;
			} else {
				return oldRoad.increadNumberOfThreads();
			}
		});
		buildRoads(currentLabel);
	}
}