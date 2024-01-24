package application;

public class Edge {
	City adjacent;
	double distance;

	public Edge() {
		super();
	}

	public Edge(City city, double distance) {
		this.adjacent = city;
		this.distance = distance;
	}

	public City getAdjacentCity() {
		return adjacent;
	}

	public double getDistance() {
		return distance;
	}

	@Override
	public String toString() {
		return "Edge [adjacent=" + adjacent + ", distance=" + distance + "]";
	}

}
