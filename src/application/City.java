package application;

import java.util.LinkedList;

import javafx.scene.control.Button;

public class City implements Comparable<City> {
	float x;
	float y;
	String name;
	boolean isVisible;
	LinkedList<Edge> edges = new LinkedList<>();
	private double max = Double.MAX_VALUE;
	private City tempPrev;
	City prevousCity;
	Button test = new Button();

	public City(String name, float x, float y, boolean isVisible) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.isVisible = isVisible;
	}

	public City() {

	}

	public void addAdjacentCity(City city, double distance) {
		edges.add(new Edge(city, distance));
	}

	public void resetTemps() {
		max = Double.MAX_VALUE;
		tempPrev = null;
	}

	public Button getTest() {
		return test;
	}

	public void setTest(Button test) {
		this.test = test;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public LinkedList<Edge> getAdjacentsList() {
		return edges;
	}

	public void setTempCost(double tempCost) {
		this.max = tempCost;
	}

	public double getTempCost() {
		return max;
	}

	public void setTempPrev(City tempPrev) {
		this.tempPrev = tempPrev;
	}

	public String getFullName() {
		return name;
	}

	public City getTempPrev() {
		return tempPrev;
	}

	@Override
	public String toString() {
		return "City [x=" + x + ", y=" + y + ", name=" + name + ", visible=" + isVisible + "]";
	}

	@Override
	public int compareTo(City o) {
		if (this.max > o.max)
			return 1;
		else if (this.max < o.max)
			return -1;
		return 0;
	}

}
