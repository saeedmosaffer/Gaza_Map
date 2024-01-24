package application;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;

public class Main extends Application {

	static ArrayList<City> Cities;
	static City sourceCity = null;
	static City destinationCity = null;
	Pane root = new Pane();
	ComboBox<Label> source = new ComboBox<Label>();
	ComboBox<Label> Target = new ComboBox<Label>();

	@Override
	public void start(Stage stage) throws FileNotFoundException {
		Stage primaryStage = new Stage();

		Scene scene = new Scene(root, 1200, 695);
		primaryStage.setTitle("Gaza Map");
		root.setStyle("-fx-background-color:#ced6e0;\r\n");
		initialize();
		Label names[] = new Label[Cities.size()];
		Label s = new Label("Source:");
		s.setFont(new Font(30));
		s.setTextFill(Color.BLACK);
		Label d = new Label("Target:");
		d.setFont(new Font(30));
		d.setTextFill(Color.BLACK);
		source.setStyle("-fx-background-color: #dfe4ea;\r\n");
		Target.setStyle("-fx-background-color: #dfe4ea;\r\n");
		for (int i = 0, j = 0; i < names.length; i++, j++) {
			names[i] = new Label();
			names[i].setFont(new Font(20));
			names[i].setTextFill(Color.BLACK);
			names[i].setText(Cities.get(i).name);
			source.getItems().add(names[i]);
			names[j] = new Label();
			names[j].setFont(new Font(20));
			names[j].setTextFill(Color.BLACK);
			names[j].setText(Cities.get(j).name);
			Target.getItems().add(names[j]);
		}
		source.setTranslateX(800);
		source.setTranslateY(50);
		source.setPrefSize(180, 50);
		Target.setTranslateX(800);
		Target.setTranslateY(150);
		Target.setPrefSize(180, 50);
		s.setTranslateX(685);
		s.setTranslateY(50);
		d.setTranslateX(685);
		d.setTranslateY(150);

		source.setOnAction(e -> {
			sourceCity = Dijkstra.allNodes.get(source.getValue().getText());
			if (sourceCity != null) {
				sourceCity.getTest()
						.setStyle("-fx-background-color: #FF0000;\r\n" + "        -fx-background-radius:100;\r\n");
			}
		});
		Target.setOnAction(i -> {
			destinationCity = Dijkstra.allNodes.get(Target.getValue().getText());
			if (destinationCity != null) {
				destinationCity.getTest()
						.setStyle("-fx-background-color: #FF0000;\r\n" + "        -fx-background-radius:100;\r\n");
			}
		});

		Image image10 = new Image("https://img.icons8.com/?size=512&id=s1zBglnEKEfn&format=png");
		ImageView imageView10 = new ImageView();
		imageView10.setImage(image10);
		Button run = new Button("Run", imageView10);
		run.setStyle("-fx-background-color:transparent;");
		imageView10.setFitHeight(40);
		imageView10.setFitWidth(40);
		run.setFont(new Font(30));
		run.setTranslateX(800);
		run.setTranslateY(220);
		run.setMinWidth(170);
		run.setMinHeight(80);
		run.setAlignment(Pos.CENTER);
		run.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, new CornerRadii(25), Insets.EMPTY)));

		DropShadow shadow = new DropShadow();

		run.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent eh) -> {
			run.setEffect(shadow);
		});

		run.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent ej) -> {
			run.setEffect(null);
		});

		TextArea path = new TextArea();
		path.setTranslateX(800);
		path.setTranslateY(320);
		path.setMinSize(270, 220);
		path.setMaxSize(270, 220);
		path.setEditable(false);
		path.setStyle("-fx-background-color: #dfe4ea;");

		Label p = new Label("Path:");
		p.setFont(new Font(30));
		p.setTranslateX(685);
		p.setTranslateY(320);
		p.setTextFill(Color.BLACK);

		TextField t1 = new TextField();
		t1.setTranslateX(800);
		t1.setTranslateY(570);
		t1.setPrefSize(190, 50);
		t1.setEditable(false);
		t1.setFont(new Font(20));
		t1.setStyle("-fx-background-color: #dfe4ea;");

		Label t = new Label("Distance:");
		t.setFont(new Font(30));
		t.setTranslateX(670);
		t.setTranslateY(570);
		t.setTextFill(Color.BLACK);

		run.setOnAction(e -> {
			int v = 0, w = 0;
			for (int i = 0; i < Cities.size(); i++) {
				if (sourceCity.getFullName().equals(Cities.get(i).getFullName()))
					v = i;
				if (destinationCity.getFullName().equals(Cities.get(i).getFullName()))
					w = i;
			}
			if (sourceCity != null && destinationCity != null) {
				Dijkstra graph = new Dijkstra(Cities, Cities.get(v), Cities.get(w));
				graph.generateDijkstra();
				drawPathOnMap(graph.pathTo(Cities.get(w)));
				root.getChildren().add(group);
				path.setText(graph.getPathString());
				t1.setText(graph.distanceString + " KM");
			}
		});

		Image image11 = new Image("https://img.icons8.com/?size=512&id=115647&format=png");
		ImageView imageView11 = new ImageView();
		imageView11.setImage(image11);
		Button reset = new Button("", imageView11);
		reset.setStyle("-fx-background-color:transparent;");
		imageView11.setFitHeight(40);
		imageView11.setFitWidth(40);
		reset.setPrefSize(150, 60);
		reset.setAlignment(Pos.CENTER);
		reset.setTranslateX(800);
		reset.setTranslateY(640);
		reset.setFont(new Font(30));

		DropShadow shadow1 = new DropShadow();

		reset.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent eh) -> {
			reset.setEffect(shadow1);
		});

		reset.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent ej) -> {
			reset.setEffect(null);
		});

		reset.setOnAction(action -> {
			sourceCity.getTest()
					.setStyle("-fx-background-color: #000000;\r\n" + "        -fx-background-radius:100;\r\n");
			destinationCity.getTest()
					.setStyle("-fx-background-color: #000000;\r\n" + "        -fx-background-radius:100;\r\n");
			sourceCity = new City();
			destinationCity = new City();
			group.getChildren().clear();
			root.getChildren().remove(group);
			source.setValue(new Label(""));
			Target.setValue(new Label(""));
			path.setText(null);
			t1.setText(null);
		});

		root.getChildren().addAll(source, Target, run, path, t1, s, d, reset, p, t);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void initialize() {
		Image image1 = new Image("C:\\Users\\User\\eclipse-workspace\\Gaza_Map\\Gaza.png");
		ImageView imageView1 = new ImageView(image1);
		imageView1.setFitHeight(mapHieght);
		imageView1.setFitWidth(mapWidth);
		imageView1.setVisible(true);
		root.getChildren().add(imageView1);
		for (int i = 0; i < Cities.size(); i++) {
			City city = Cities.get(i);
			if (city.isVisible()) {

				Polygon triangle = new Polygon();
				double size = 50; // Size of the triangle
				triangle.getPoints().addAll(new Double[] { 0.0, 0.0, // Top left
						size, 0.0, // Top right
						size / 2, size * Math.sqrt(3) / 2 // Bottom center
				});
				triangle.setFill(Color.GREEN); // Set triangle color

				Image buttonImage = new Image("C:\\Users\\User\\eclipse-workspace\\Gaza_Map\\src\\application");
				ImageView buttonImageView = new ImageView(buttonImage);
				buttonImageView.setFitWidth(10);
				buttonImageView.setFitHeight(10);
				buttonImageView.setPreserveRatio(true);

				Button b = new Button();
				b.setGraphic(buttonImageView);
				Cities.get(i).setTest(b);
				b.setUserData(Cities.get(i));
				b.setTranslateX(getX(Cities.get(i).x));
				b.setTranslateY(getY(Cities.get(i).y));

				b.setShape(triangle); // Set the triangle as the button shape
				b.setMinSize(size, size * Math.sqrt(3) / 2);
				b.setMaxSize(size, size * Math.sqrt(3) / 2);

				b.setMinWidth(10);
				b.setMinHeight(10);
				b.setMaxWidth(10);
				b.setMaxHeight(10);
				b.setStyle("-fx-background-color: #000000;\r\n" + "        -fx-background-radius:100;\r\n");
				// The triangle color before clicking on it
				b.setOnAction(event -> {
					b.setStyle("-fx-background-color: #0dff00;\r\n" + "        -fx-background-radius:100;\r\n");
					// The triangle color after clicking on it
					if (sourceCity == null) {
						sourceCity = (City) b.getUserData();
						Label l = new Label();
						l.setFont(new Font(20));
						l.setTextFill(Color.BLACK);
						l.setText(sourceCity.name);
						source.setValue(l);
					} else if (destinationCity == null && sourceCity != null) {
						destinationCity = (City) b.getUserData();
						Label l = new Label();
						l.setFont(new Font(20));
						l.setTextFill(Color.BLACK);
						l.setText(destinationCity.name);
						Target.setValue(l);
					}
				});

				Label lb = new Label(Cities.get(i).name);
				lb.setFont(new Font(10));
				lb.setTextFill(Color.BLACK);
				// The color of the names of colleges and buildings
				lb.setTranslateX(getX(Cities.get(i).x));
				lb.setTranslateY(getY(Cities.get(i).y) - 10);

				root.getChildren().add(b);
				root.getChildren().add(lb);
			}

		}

	}

	Group group = new Group();

	private void drawPathOnMap(City[] faculties) {
	    for (int i = 0; i < faculties.length - 1; i++) {
	        Line line = new Line(getX(faculties[i].x), getY(faculties[i].y), getX(faculties[i + 1].x),
	                getY(faculties[i + 1].y));
	        line.setStroke(Color.BLACK);
	        line.setStrokeWidth(2);

	        // Position the arrow at the end of the line
	        double angle = Math.atan2(line.getEndY() - line.getStartY(), line.getEndX() - line.getStartX()) * 180 / Math.PI;
	        
	        group.getChildren().addAll(line);
	    }
	}


	static float mapHieght = 695;
	static float mapWidth = 589;
	static double MaxX = 589;
	static double MinX = 0;
	static double MaxY = 695;
	static double MinY = 0;
	static double MxMin = 34.1707489947630;
	static double MxMax = 34.575060834817954;
	static double MyMin = 31.614521165206845;
	static double MyMax = 31.208163033163977;

	public double getX(double xCity) {
		double x = ((((MaxX - MinX) * (xCity - MxMin)) / (MxMax - MxMin))) + MinX;
		return x;
	}

	public double getY(double yCity) {
		double x = ((((MaxY - MinY) * (yCity - MyMin)) / (MyMax - MyMin))) + MinY;
		return x;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Cities = Dijkstra.readFile();

		launch(args);
	}
}
