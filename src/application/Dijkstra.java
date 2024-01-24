package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Dijkstra {
	static ArrayList<City> Cities = new ArrayList<City>();
	static HashMap<String, City> allNodes = new HashMap<>();
	private City source;
	private City destination;
	private PriorityQueue<City> heap;

	public Dijkstra() {

	}

	public Dijkstra(ArrayList<City> Cities, City source, City destination) {
		heap = new PriorityQueue<>(Cities.size());
		this.destination = destination;
		this.Cities = Cities;
		for (City city : Cities) {
			city.resetTemps();
			if (city == source) {
				city.setTempCost(0);
			}
			heap.add(city);
		}
	}

	public void generateDijkstra() {
		while (!heap.isEmpty()) {
			City minUnknown = heap.poll();
			LinkedList<Edge> adjacentsList = minUnknown.getAdjacentsList();
			for (Edge edge : adjacentsList) {
				City adjacentCity = edge.getAdjacentCity();
				if (heap.contains(adjacentCity)) {
					if ((minUnknown.getTempCost() + edge.getDistance()) < adjacentCity.getTempCost()) {
						heap.remove(adjacentCity);
						adjacentCity.setTempCost(minUnknown.getTempCost() + edge.getDistance());
						adjacentCity.setTempPrev(minUnknown);
						heap.add(adjacentCity);
					}

				}
			}
		}
	}

	// getTempCost المسافة المخزنة في الفيرتكس

	private String pathString;
	String distanceString;

	public City[] pathTo(City destination) { //
		LinkedList<City> cities = new LinkedList<>();
		City iterator = destination;
		distanceString = String.format("%.2f", destination.getTempCost());
		while (iterator != source) { //
			cities.addFirst(iterator);
			pathString = iterator.getFullName() + " : " + String.format("%.2f", iterator.getTempCost()) + " KM" + "\n"
					+ "  ->  " + pathString;

			iterator = iterator.getTempPrev(); // تنتقل من الديستنيشن للبريفيس وهكذا لتوصل للستارت
		}

		return cities.toArray(new City[0]);
	}

	public String getPathString() {
		if (countOccurrences(pathString, '\n') <= 1) {
			pathString = "No Path";
			distanceString = "\t\t\t------------------";
			return pathString;
		}

		pathString = "\t" + pathString;

		int truncateIndex = pathString.lastIndexOf('\n');
		pathString = pathString.substring(0, truncateIndex);

		return pathString;
	}

	private static int countOccurrences(String haystack, char needle) {
		int count = 0;
		for (int i = 0; i < haystack.length(); i++) {
			if (haystack.charAt(i) == needle) {
				count++;
			}
		}
		return count;
	}

	public static ArrayList<City> readFile() throws FileNotFoundException {
		String line = null;
		int numberOfCities, numberOfEdges;
		File file = new File("data.txt");
		Scanner scan = new Scanner(file);
		line = scan.nextLine();
		String[] str = line.split(" ");
		numberOfCities = Integer.parseInt(str[0]);
		numberOfEdges = Integer.parseInt(str[1]);

		for (int i = 0; i < numberOfCities; i++) {
			float x, y;
			line = scan.nextLine();
			String[] strN = line.split(" ");
			y = (float) Double.parseDouble(strN[1]);
			x = (float) Double.parseDouble(strN[2]);
			boolean isVisible = strN[3].equals("1");
			City newCity = new City(strN[0], x, y, isVisible);
			Cities.add(newCity);
			allNodes.putIfAbsent(strN[0], newCity);// hash map

		}
		for (int i = 0; i < numberOfEdges; i++) {
			line = scan.nextLine();
			String[] strN = line.split(" ");
			String fromCityName = strN[0], toCityName = strN[1];
			City fromCity = allNodes.get(fromCityName), toCity = allNodes.get(toCityName);
			fromCity.addAdjacentCity(toCity, distance(fromCity.x, fromCity.y, toCity.x, toCity.y));
		}

		return Cities;

	}

	public static double distance(double x1, double y1, double x2, double y2) {
		// calculate the distance between tow Cities by there Latitude and longitude
		// Distance, d * arccos[(sin(Latitude1) * sin(Latitude2)) +
		// cos(Latitude1) * cos(Latitude2) * cos(longitude1 – longitude2)]
		return 6378.8
				* Math.acos((Math.sin(Math.toRadians(y1)) * Math.sin(Math.toRadians(y2))) + Math.cos(Math.toRadians(y1))
						* Math.cos(Math.toRadians(y2)) * Math.cos(Math.toRadians(x1) - Math.toRadians(x2)));
	}

}
