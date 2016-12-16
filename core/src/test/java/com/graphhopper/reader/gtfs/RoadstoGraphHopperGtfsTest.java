package com.graphhopper.reader.gtfs;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RoadstoGraphHopperGtfsTest {

	private static final String GRAPH_LOC = "data/swu";
	private static RoadstoGraphHopperGtfs graphHopper;

	@BeforeClass
	public static void init() {
		graphHopper = RoadstoGraphHopperGtfs.createGraphHopperGtfs(GRAPH_LOC,
				"files/swu.zip", true);
	}

	@AfterClass
	public static void tearDown() {
		// if (graphHopper != null)
		// graphHopper.close();
	}

	@Test
	public void testSpt() {
		final double FROM_LAT = 48.399368824050626, FROM_LON = 9.984123929980187;
		Collection<Link> links = graphHopper.spt(FROM_LAT, FROM_LON);
		assertEquals(483, links.size());
		links.stream().map(Link::toJsonObject).map(Object::toString)
				.forEach(System.out::println);
	}
}
