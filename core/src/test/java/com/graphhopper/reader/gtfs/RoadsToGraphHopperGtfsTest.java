package com.graphhopper.reader.gtfs;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RoadsToGraphHopperGtfsTest {

	private static final String GRAPH_LOC = "data/swu";
	private static RoadsToGraphHopperGtfs graphHopper;

	@BeforeClass
	public static void init() {
		graphHopper = RoadsToGraphHopperGtfs.createGraphHopperGtfs(GRAPH_LOC,
				"files/swu.zip", true);
	}

	@AfterClass
	public static void tearDown() {
		// if (graphHopper != null)
		// graphHopper.close();
	}

	@Test
	public void testRoads() {
		final double FROM_LAT = 48.399368824050626, FROM_LON = 9.984123929980187;
		Roads roads = graphHopper.roadsFrom(FROM_LAT, FROM_LON);
		assertEquals(483, roads.getRoads().size());
		System.out.println(roads.toJsonObject());
	}
}
