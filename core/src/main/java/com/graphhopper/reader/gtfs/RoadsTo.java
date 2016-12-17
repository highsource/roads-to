package com.graphhopper.reader.gtfs;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collections;

import javax.json.JsonWriter;
import javax.json.spi.JsonProvider;
import javax.json.stream.JsonGenerator;

import com.graphhopper.util.CmdArgs;
import com.graphhopper.util.Helper;

public class RoadsTo {

	public static void main(String[] strs) {
		CmdArgs args = CmdArgs.read(strs);
		new RoadsTo().process(args);
	}

	public RoadsTo() {

	}

	public void process(CmdArgs args) {
		final RoadsToGraphHopperGtfs roadsToGraphHopperGtfs = init(args);

		String latString = args.get("tolat", "tolat not specified");
		String lonString = args.get("tolon", "tolon not specified");

		double lat = Double.parseDouble(latString);
		double lon = Double.parseDouble(lonString);

		final Roads roads = roadsToGraphHopperGtfs.roadsFrom(lat, lon);

		String geojsonFile = args.get("geojson.file", "");
		String geojsonVar = args.get("geojson.var", "");

		// OutputStream os = null;
		Writer writer = null;
		try {

			if (Helper.isEmpty(geojsonFile)) {
				writer = new OutputStreamWriter(System.out, "UTF-8");
			} else {
				writer = new OutputStreamWriter(new FileOutputStream(
						geojsonFile), "UTF-8");
			}
			if (!Helper.isEmpty(geojsonVar)) {
				writer.write("var " + geojsonVar + "=");
			}

			final JsonWriter jsonWriter = JsonProvider
					.provider()
					.createWriterFactory(
							Collections
									.singletonMap(
											JsonGenerator.PRETTY_PRINTING,
											Boolean.TRUE)).createWriter(writer);
			jsonWriter.writeObject(roads.toJsonObject());
			if (!Helper.isEmpty(geojsonVar)) {
				writer.write(";");
			}
			jsonWriter.close();
		} catch (IOException ioex) {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException ignored) {
					ioex.printStackTrace();
				}
			}
		}
	}

	public RoadsToGraphHopperGtfs init(CmdArgs args) {
		String gtfsFile = args.get("gtfs.file", "");
		String graphLocation = args.get("graph.location", "");
		return RoadsToGraphHopperGtfs.createGraphHopperGtfs(graphLocation,
				"files/swu.zip", true);
	}

}
