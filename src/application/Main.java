package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	private Alert error = new Alert(AlertType.ERROR);
	private Alert sucsses = new Alert(AlertType.INFORMATION);
	private LinkedList<Flight> flights = new LinkedList<>();

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setResizable(false);
		primaryStage.setTitle("Air Plan System");
		primaryStage.getIcons().add(new Image("airport.png"));
		sucsses.setHeaderText("Yahoooooo");
		mainInterface(primaryStage);
	}

	private void mainInterface(Stage primaryStage) {
		String css = "-fx-color:lightblue;-fx-background-radius: 50; -fx-min-width:350; -fx-min-height:45";
		Button read = new Button("Read data");
		read.setStyle(css);
		read.setOnAction(e -> read(primaryStage));

		Button displayFlightInfo = new Button("Display flight�s information");
		displayFlightInfo.setStyle(css);
		displayFlightInfo.setOnAction(e -> displayFlights(primaryStage));

		Button displayPassengerInfo = new Button("Display Passengers information for a specific flight");
		displayPassengerInfo.setStyle(css);
		displayPassengerInfo.setOnAction(e -> displayPassengers(primaryStage));

		Button addEditFlight = new Button("Add/Edit flight");
		addEditFlight.setStyle(css);
		addEditFlight.setOnAction(e -> addEditFlight(primaryStage));

		Button reserve = new Button("Reserve a ticket for a specific flight");
		reserve.setStyle(css);
		reserve.setOnAction(e -> reserve(primaryStage));
		Button cancel = new Button("Cancel a reservation");
		cancel.setStyle(css);
		cancel.setOnAction(e -> cancel(primaryStage));

		Button cheak = new Button("Check whether a ticket is reserved for a particular person.");
		cheak.setStyle(css);
		cheak.setOnAction(e -> check(primaryStage));

		Button searchPassenger = new Button("Search for a specific passenger and print all his information");
		searchPassenger.setStyle(css);
		searchPassenger.setOnAction(e -> searchPassenger(primaryStage));

		Button exit = new Button("Exit");
		exit.setStyle(css);
		exit.setOnAction(e -> primaryStage.close());

		GridPane p = new GridPane();

		p.addColumn(0, read, displayFlightInfo, displayPassengerInfo, addEditFlight);
		p.addColumn(1, reserve, cancel, cheak, searchPassenger);
		p.setVgap(40);
		p.setHgap(30);
		p.setAlignment(Pos.CENTER);

		VBox all = new VBox(40, p, exit);
		all.setAlignment(Pos.CENTER);
		all.setBackground(
				new Background(new BackgroundImage(new Image("airport_324754607.png"), null, null, null, new BackgroundSize(1600, 900, false, false, false, false))));

		Scene s = new Scene(all, 1000, 600);
		primaryStage.setScene(s);
		primaryStage.show();

	}

	private void read(Stage primaryStage) {
		GridPane pane = new GridPane();
		pane.setVgap(20);
		pane.setHgap(10);
		pane.setAlignment(Pos.CENTER);
		FileChooser passFileChooser = new FileChooser();
		passFileChooser.setTitle("select Passengers File");
		passFileChooser.setInitialDirectory(new File("."));
		passFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));

		FileChooser flightFileChooser = new FileChooser();
		flightFileChooser.setTitle("select Flights File");
		flightFileChooser.setInitialDirectory(new File("."));
		flightFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));

		ImageView passBrowseIcone = new ImageView("external-browse-file-folder-blue-vinzence-studio.png");
		passBrowseIcone.setFitWidth(25);
		passBrowseIcone.setFitHeight(25);
		Label passLabel = new Label("Passenger File: ");
		Button passButton = new Button("Browse", passBrowseIcone);
		passButton.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 30; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView flightBrowseIcone = new ImageView("external-browse-file-folder-blue-vinzence-studio.png");
		flightBrowseIcone.setFitWidth(25);
		flightBrowseIcone.setFitHeight(25);
		Label flightLabel = new Label("Flight File: ");
		Button flightButton = new Button("Browse", flightBrowseIcone);
		flightButton.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 30; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView backIcone = new ImageView("return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");
		back.setOnAction(e -> mainInterface(primaryStage));

		pane.addRow(0, flightLabel, flightButton);
		pane.addRow(1, passLabel, passButton);

		VBox all = new VBox(20, pane, back);
		all.setBackground(
				new Background(new BackgroundImage(new Image("airport_324754607.png"), null, null, null, new BackgroundSize(1600, 900, false, false, false, false))));
		all.setAlignment(Pos.CENTER);

		passButton.setOnAction(e -> readPass(passFileChooser.showOpenDialog(primaryStage)));
		flightButton.setOnAction(e -> readFlight(flightFileChooser.showOpenDialog(primaryStage)));

		Scene s = new Scene(all, 1000, 600);
		primaryStage.setScene(s);
		
	}

	private void readFlight(File flight) {
		try {
			boolean bobo = true;
			Scanner flightsScanner = new Scanner(flight);

			while (flightsScanner.hasNext()) {
				String line = flightsScanner.nextLine();

				String[] tokens = line.split(",");
				if (!flights.insert(new Flight(Integer.parseInt(tokens[0].trim()), tokens[1].trim(), tokens[2].trim(),
						tokens[3].trim(), Integer.parseInt(tokens[4].trim()))))
					bobo = false;
			}
			if (bobo) {
				sucsses.setContentText("Done, all Flights read without any error.");
				sucsses.show();
			} else {
				error.setContentText("You have duplicate data in the file.");
				error.show();
			}
			flightsScanner.close();
		} catch (NumberFormatException e) {
			error.setContentText("You have an Error in data in the file.");
			error.show();
		} catch (FileNotFoundException e) {
			error.setContentText("Can't read the file");
			error.show();
		} catch (IndexOutOfBoundsException e) {
			error.setContentText("You have an Error in data in the file.");
			error.show();
		} catch (Exception e2) {
			error.setContentText(e2.getMessage());
			error.show();
		}

	}

	private void readPass(File pass) {
		try {
			boolean bobo = true;
			Scanner passengerScanner = new Scanner(pass);

			while (passengerScanner.hasNext()) {
				String line = passengerScanner.nextLine();

				String[] tokens = line.split(",");

				Node<Flight> index = flights
						.search(new Flight(Integer.parseInt(tokens[0].trim()), null, null, null, 0));

				if (index == null) {
					error.setContentText(
							"flight number: " + Integer.parseInt(tokens[0].trim()) + " is NOT exist in your System !");
					error.show();
				} else {
					String[] date = tokens[5].split("/");
					if (!index.getData().addPassenger(Integer.parseInt(tokens[0].trim()),
							Integer.parseInt(tokens[1].trim()), tokens[2].trim(), tokens[3].trim(), tokens[4].trim(),
							new GregorianCalendar(Integer.parseInt(date[2].trim()), Integer.parseInt(date[1].trim()),
									Integer.parseInt(date[0].trim()))))
						bobo = false;

				}
			}
			if (bobo) {
				sucsses.setContentText("Done, all Passengers read without any error.");
				sucsses.show();
			} else {
				error.setContentText("You have duplicate data in the file.");
				error.show();
			}
			passengerScanner.close();
		} catch (NumberFormatException e) {
			error.setContentText("You have an Error in data in the file.");
			error.show();
		} catch (FileNotFoundException e) {
			error.setContentText("Can't read the file");
			error.show();
		} catch (IndexOutOfBoundsException e) {
			error.setContentText("You have an Error in data in the file.");
			error.show();
		} catch (Exception e2) {
			error.setContentText(e2.getMessage());
			error.show();
		}
	}

	private void reserve(Stage primaryStage) {
		GridPane Pane = new GridPane();
		Pane.setAlignment(Pos.CENTER);
		Pane.setHgap(50);
		Pane.setVgap(20);

		Label numEditLable = new Label("Flight Number:");
		TextField numEditText = new TextField();
		Pane.add(numEditLable, 0, 0);
		Pane.add(numEditText, 1, 0);

		Label fullNameLable = new Label("Full Name:");
		TextField fullNameText = new TextField();
		Pane.add(fullNameLable, 0, 1);
		Pane.add(fullNameText, 1, 1);

		Label passportNumLable = new Label("passport Number:");
		TextField passportNumText = new TextField();
		Pane.add(passportNumLable, 0, 2);
		Pane.add(passportNumText, 1, 2);

		Label nationalityLable = new Label("Nationality:");
		nationalityLable.setTextFill(Color.WHITE);
		TextField nationalityText = new TextField();
		Pane.add(nationalityLable, 0, 3);
		Pane.add(nationalityText, 1, 3);

		Label birthdateLable = new Label("Birthdate:");
		ComboBox<Integer> day = new ComboBox<>();
		ComboBox<Integer> month = new ComboBox<>();
		ComboBox<Integer> year = new ComboBox<>();
		day.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
				26, 27, 28, 29, 30, 31);
		month.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		year.getItems().addAll(2004, 2003, 2002, 2001, 2000, 1999, 1998, 1997, 1996, 1995, 1994, 1993, 1992, 1991, 1990,
				1989, 1988, 1987, 1986, 1985, 1984, 1983, 1982, 1981, 1980, 1979, 1978, 1977, 1976, 1975, 1974, 1973,
				1972, 1971, 1970, 1969, 1968, 1967, 1966, 1965, 1964, 1963, 1962, 1961, 1960, 1959, 1958, 1957, 1956,
				1955, 1954, 1953, 1952, 1951, 1950, 1949, 1948, 1947, 1946, 1945, 1944, 1943, 1942, 1941, 1940, 1939,
				1938, 1937, 1936, 1935, 1934, 1933, 1932, 1931, 1930, 1929, 1928, 1927, 1926, 1925, 1924, 1923, 1922,
				1921, 1920, 1919, 1918, 1917, 1916, 1915, 1914, 1913, 1912, 1911, 1910, 1909, 1908, 1907, 1906, 1905,
				1904, 1903, 1902, 1901, 1900);

		HBox dateBox = new HBox(2, day, month, year);

		Pane.add(birthdateLable, 0, 4);
		Pane.add(dateBox, 1, 4);

		ImageView reserveIcone = new ImageView("reservation-2--v2.png");
		reserveIcone.setFitWidth(35);
		reserveIcone.setFitHeight(35);
		Button reserve = new Button("reserve", reserveIcone);
		reserve.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		reserve.setOnAction(e -> {
			try {
				if (!numEditText.getText().equals("") && !fullNameText.getText().equals("")
						&& !passportNumText.getText().equals("") && !nationalityText.getText().equals("")) {
					Node<Flight> index = flights
							.search(new Flight(Integer.parseInt(numEditText.getText().trim()), null, null, null, 0));
					if (index == null) {
						error.setContentText("This Flight number is NOT exist!!");
						error.show();
					} else {
						int i = getMax(index);
						if (!index.getData().addPassenger(Integer.parseInt(numEditText.getText().trim()), i + 1,
								fullNameText.getText().trim(), passportNumText.getText().trim(),
								nationalityText.getText().trim(),
								new GregorianCalendar(year.getValue(), month.getValue(), day.getValue()))) {
							error.setContentText(fullNameText.getText().trim() + " is already exist in flight");
							error.show();
						} else {
							sucsses.setContentText("Done and Your ticket is " + i);
							sucsses.show();
						}
					}
				} else {
					error.setContentText("You should fill all fields !!");
					error.show();
				}
			} catch (IndexOutOfBoundsException e2) {
				error.setContentText(e2.getMessage());
				error.show();
			} catch (NumberFormatException e2) {
				error.setContentText("Flight number and ticket number should be integer !!");
				error.show();
			} catch (NullPointerException e2) {
				error.setContentText("You should enter full date.");
				error.show();
			} catch (Exception e2) {
				error.setContentText(e2.getMessage());
				error.show();
			}

		});

		ImageView backIcone = new ImageView("return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");
		back.setOnAction(e -> mainInterface(primaryStage));

		HBox control = new HBox(15, reserve, back);
		control.setAlignment(Pos.CENTER);

		VBox all = new VBox(30, Pane, control);
		all.setAlignment(Pos.CENTER);
		all.setBackground(
				new Background(new BackgroundImage(new Image("airport_324754607.png"), null, null, null, new BackgroundSize(1600, 900, false, false, false, false))));

		Scene s = new Scene(all, 1000, 600);
		primaryStage.setScene(s);
	}

	private int getMax(Node<Flight> index) {
		if (index.getData().getPassengers().getHead() == null)
			return 1;
		else {
			Node<Passenger> curr = index.getData().getPassengers().getHead();
			int max = curr.getData().getTicketNumber();
			while (curr != null) {
				if (curr.getData().getTicketNumber() > max)
					max = curr.getData().getTicketNumber();
				curr = curr.getNext();
			}
			return max;
		}
	}

	private void searchPassenger(Stage primaryStage) {
		GridPane Pane = new GridPane();
		Pane.setAlignment(Pos.CENTER);
		Pane.setHgap(50);
		Pane.setVgap(20);

		Label fullNameLable = new Label("Full Name:");
		TextField fullNameText = new TextField();
		Pane.add(fullNameLable, 0, 0);
		Pane.add(fullNameText, 1, 0);

		Label numEditLable = new Label("Flight Number:");
		TextField numEditText = new TextField();
		Pane.add(numEditLable, 0, 1);
		Pane.add(numEditText, 1, 1);
		numEditText.setEditable(false);

		Label ticketNumLable = new Label("Ticket Number:");
		TextField ticketNumText = new TextField();
		Pane.add(ticketNumLable, 0, 2);
		Pane.add(ticketNumText, 1, 2);
		ticketNumText.setEditable(false);

		Label passportNumLable = new Label("passport Number:");
		TextField passportNumText = new TextField();
		Pane.add(passportNumLable, 0, 3);
		Pane.add(passportNumText, 1, 3);
		passportNumText.setEditable(false);

		Label nationalityLable = new Label("Nationality:");
		nationalityLable.setTextFill(Color.WHITE);
		TextField nationalityText = new TextField();
		Pane.add(nationalityLable, 0, 4);
		Pane.add(nationalityText, 1, 4);
		nationalityText.setEditable(false);

		Label birthdateLable = new Label("Birthdate:");
		birthdateLable.setTextFill(Color.WHITE);
		TextField birthdateText = new TextField();
		Pane.add(birthdateLable, 0, 5);
		Pane.add(birthdateText, 1, 5);
		birthdateText.setEditable(false);

		ImageView searchIcone = new ImageView("search.png");
		searchIcone.setFitWidth(35);
		searchIcone.setFitHeight(35);
		Button search = new Button("Find", searchIcone);
		search.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		search.setOnAction(e -> {
			try {
				if (!fullNameText.getText().equals("")) {
					Node<Flight> curr = flights.getHead();
					boolean exist = false;
					while (curr != null) {
						Node<Passenger> pass = curr.getData().getPassengers()
								.search(new Passenger(0, fullNameText.getText().trim(), null, null, null));
						if (pass != null) {
							exist = true;
							numEditText.setText(curr.getData().getFlightNumber() + "");
							ticketNumText.setText(pass.getData().getTicketNumber() + "");
							fullNameText.setText(pass.getData().getFullName() + "");
							passportNumText.setText(pass.getData().getPassportNumber() + "");
							birthdateText.setText(pass.getData().getBirthDate().get(Calendar.DAY_OF_MONTH) + "/"
									+ pass.getData().getBirthDate().get(Calendar.MONTH) + "/"
									+ pass.getData().getBirthDate().get(Calendar.YEAR));
							nationalityText.setText(pass.getData().getNationality());
							break;
						}
						curr = curr.getNext();
					}
					if (!exist) {
						numEditText.setText("");
						ticketNumText.setText("");
						passportNumText.setText("");
						birthdateText.setText("");
						nationalityText.setText("");

						error.setContentText("Passenger does NOT exist");
						error.show();
					}
				} else {
					error.setContentText("You should enter name of passenger");
					error.show();
				}
			} catch (Exception e3) {
				error.setContentText(e3.getMessage());
				error.show();
			}

		});

		ImageView backIcone = new ImageView("return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");
		back.setOnAction(e -> mainInterface(primaryStage));

		HBox control = new HBox(15, search, back);
		control.setAlignment(Pos.CENTER);

		VBox all = new VBox(30, Pane, control);
		all.setAlignment(Pos.CENTER);
		all.setBackground(
				new Background(new BackgroundImage(new Image("airport_324754607.png"), null, null, null, new BackgroundSize(1600, 900, false, false, false, false))));

		Scene s = new Scene(all, 1000, 600);
		primaryStage.setScene(s);
	}

	private void check(Stage primaryStage) {
		GridPane Pane = new GridPane();
		Pane.setAlignment(Pos.CENTER);
		Pane.setHgap(50);
		Pane.setVgap(20);

		Label numEditLable = new Label("Flight Number:");
		TextField numEditText = new TextField();
		Pane.add(numEditLable, 0, 0);
		Pane.add(numEditText, 1, 0);

		Label fullNameLable = new Label("Full Name:");
		TextField fullNameText = new TextField();
		Pane.add(fullNameLable, 0, 2);
		Pane.add(fullNameText, 1, 2);

		Label resultLable = new Label("Is Exist:");
		TextField resultText = new TextField();
		resultText.setMinWidth(500);
		Pane.add(resultLable, 0, 3);
		Pane.add(resultText, 1, 3);
		resultText.setEditable(false);

		ImageView searchIcone = new ImageView("search.png");
		searchIcone.setFitWidth(35);
		searchIcone.setFitHeight(35);
		Button search = new Button("Find", searchIcone);
		search.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		search.setOnAction(e -> {
			try {
				Node<Flight> index = flights
						.search(new Flight(Integer.parseInt(numEditText.getText().trim()), null, null, null, 0));
				if (index == null) {
					error.setContentText("This Flight number is NOT exist!!");
					error.show();
				} else {
					if (!fullNameText.getText().equals("")) {
						Node<Passenger> pass = index.getData().getPassengers()
								.search(new Passenger(0, fullNameText.getText().trim(), null, null, null));
						if (pass == null)
							resultText.setText("This Pass has NOT ticket in this flight");
						else
							resultText.setText("This Pass has ticket in this flight and it's: "
									+ pass.getData().getTicketNumber());
					} else {
						error.setContentText("You should enter name of passenger");
						error.show();
					}
				}
			} catch (NumberFormatException e2) {
				error.setContentText("Flight number should be integer !!");
				error.show();
			} catch (Exception e3) {
				error.setContentText(e3.getMessage());
				error.show();
			}

		});

		ImageView backIcone = new ImageView("return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");
		back.setOnAction(e ->

		mainInterface(primaryStage));

		HBox control = new HBox(15, search, back);
		control.setAlignment(Pos.CENTER);

		VBox all = new VBox(30, Pane, control);
		all.setAlignment(Pos.CENTER);
		all.setBackground(
				new Background(new BackgroundImage(new Image("airport_324754607.png"), null, null, null, new BackgroundSize(1600, 900, false, false, false, false))));

		Scene s = new Scene(all, 1000, 600);
		primaryStage.setScene(s);
	}

	private void cancel(Stage primaryStage) {
		GridPane Pane = new GridPane();
		Pane.setAlignment(Pos.CENTER);
		Pane.setHgap(50);
		Pane.setVgap(20);

		Label numEditLable = new Label("Flight Number:");
		TextField numEditText = new TextField();
		Pane.add(numEditLable, 0, 0);
		Pane.add(numEditText, 1, 0);

		Label fullNameLable = new Label("Full Name:");
		TextField fullNameText = new TextField();
		Pane.add(fullNameLable, 0, 1);
		Pane.add(fullNameText, 1, 1);

		Label ticketNumLable = new Label("Ticket Number:");
		TextField ticketNumText = new TextField();
		Pane.add(ticketNumLable, 0, 2);
		Pane.add(ticketNumText, 1, 2);
		ticketNumText.setEditable(false);

		Label passportNumLable = new Label("passport Number:");
		TextField passportNumText = new TextField();
		Pane.add(passportNumLable, 0, 3);
		Pane.add(passportNumText, 1, 3);
		passportNumText.setEditable(false);

		Label nationalityLable = new Label("Nationality:");
		nationalityLable.setTextFill(Color.WHITE);
		TextField nationalityText = new TextField();
		Pane.add(nationalityLable, 0, 4);
		Pane.add(nationalityText, 1, 4);
		nationalityText.setEditable(false);

		Label birthdateLable = new Label("Birthdate:");
		birthdateLable.setTextFill(Color.WHITE);
		TextField birthdateText = new TextField();
		Pane.add(birthdateLable, 0, 5);
		Pane.add(birthdateText, 1, 5);
		birthdateText.setEditable(false);

		ImageView searchIcone = new ImageView("search.png");
		searchIcone.setFitWidth(35);
		searchIcone.setFitHeight(35);
		Button search = new Button("Find", searchIcone);
		search.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView cancelIcone = new ImageView("cancel--v1.png");
		cancelIcone.setFitWidth(35);
		cancelIcone.setFitHeight(35);
		Button cancelButton = new Button("cancel", cancelIcone);
		cancelButton.setDisable(true);
		cancelButton.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		search.setOnAction(e -> {
			try {
				Node<Flight> index = flights
						.search(new Flight(Integer.parseInt(numEditText.getText().trim()), null, null, null, 0));
				if (index == null) {
					error.setContentText("This Flight number is NOT exist!!");
					error.show();
				} else {
					Node<Passenger> pass = index.getData().getPassengers()
							.search(new Passenger(0, fullNameText.getText(), null, null, null));
					if (pass == null) {
						error.setContentText("Passenger Not Exist in this Flight");
						error.show();
					} else {
						numEditText.setText(index.getData().getFlightNumber() + "");
						ticketNumText.setText(pass.getData().getTicketNumber() + "");
						fullNameText.setText(pass.getData().getFullName() + "");
						passportNumText.setText(pass.getData().getPassportNumber() + "");
						birthdateText.setText(pass.getData().getBirthDate().get(Calendar.DAY_OF_MONTH) + "/"
								+ pass.getData().getBirthDate().get(Calendar.MONTH) + "/"
								+ pass.getData().getBirthDate().get(Calendar.YEAR));
						nationalityText.setText(pass.getData().getNationality() + "");

						cancelButton.setDisable(false);
						cancelButton.setOnAction(e1 -> {
							try {
								index.getData().getPassengers().remove(pass.getData());

								sucsses.setContentText("Done, passenger reserve canceld");
								sucsses.show();

								numEditText.setText("");
								ticketNumText.setText("");
								fullNameText.setText("");
								passportNumText.setText("");
								birthdateText.setText("");
								nationalityText.setText("");

								cancelButton.setDisable(true);

							} catch (Exception e2) {
								error.setContentText(e2.getMessage());
								error.show();
							}
						});
					}
				}
			} catch (NumberFormatException e2) {
				error.setContentText("Flight number and ticket number should be integer !!");
				error.show();
			} catch (Exception e2) {
				error.setContentText(e2.getMessage());
				error.show();
			}

		});

		ImageView backIcone = new ImageView("return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");
		back.setOnAction(e -> mainInterface(primaryStage));

		HBox control = new HBox(15, search, cancelButton, back);
		control.setAlignment(Pos.CENTER);

		VBox all = new VBox(30, Pane, control);
		all.setAlignment(Pos.CENTER);
		all.setBackground(
				new Background(new BackgroundImage(new Image("airport_324754607.png"), null, null, null, new BackgroundSize(1600, 900, false, false, false, false))));

		Scene s = new Scene(all, 1000, 600);
		primaryStage.setScene(s);

	}

	private void addEditFlight(Stage primaryStage) {
		GridPane addPane = new GridPane();
		addPane.setAlignment(Pos.CENTER);
		addPane.setHgap(50);
		addPane.setVgap(20);

		Label numLable = new Label("Flight Number:");
		TextField numText = new TextField();
		addPane.add(numLable, 0, 0);
		addPane.add(numText, 1, 0);

		Label airLineLable = new Label("Airline Name:");
		TextField airLineText = new TextField();
		airLineText.setDisable(true);
		addPane.add(airLineLable, 0, 1);
		addPane.add(airLineText, 1, 1);
		airLineText.setDisable(true);

		Label fromLable = new Label("From:");
		TextField fromText = new TextField();
		fromText.setDisable(true);
		addPane.add(fromLable, 0, 2);
		addPane.add(fromText, 1, 2);
		fromText.setDisable(true);

		Label toLable = new Label("To:");
		TextField toText = new TextField();
		toText.setDisable(true);
		addPane.add(toLable, 0, 3);
		addPane.add(toText, 1, 3);
		toText.setDisable(true);

		Label capacityLable = new Label("Capacity:");
		capacityLable.setTextFill(Color.WHITE);
		TextField capacityText = new TextField();
		capacityText.setDisable(true);
		addPane.add(capacityLable, 0, 4);
		addPane.add(capacityText, 1, 4);
		capacityText.setDisable(true);

		ImageView searchIcone = new ImageView("search.png");
		searchIcone.setFitWidth(35);
		searchIcone.setFitHeight(35);
		Button searchButton = new Button("search", searchIcone);
		searchButton.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView editIcone = new ImageView("loop.png");
		editIcone.setFitWidth(35);
		editIcone.setFitHeight(35);
		Button editButton = new Button("update", editIcone);
		editButton.setDisable(true);
		editButton.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView addIcone = new ImageView("add--v1.png");
		addIcone.setFitWidth(35);
		addIcone.setFitHeight(35);
		Button addButton = new Button("add", addIcone);
		addButton.setDisable(true);
		addButton.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		VBox add = new VBox(30, addPane, searchButton);
		add.setAlignment(Pos.CENTER);

		searchButton.setOnAction(e -> {
			try {
				Node<Flight> index = flights
						.search(new Flight(Integer.parseInt(numText.getText().trim()), null, null, null, 0));
				if (index == null) {
					sucsses.setContentText("This Flight number is NOT exist, add it by write in fields and click add.");
					sucsses.show();
					numText.setEditable(false);
					airLineText.setDisable(false);
					fromText.setDisable(false);
					toText.setDisable(false);
					capacityText.setDisable(false);
					addButton.setDisable(false);

					addButton.setOnAction(e5 -> {
						if (!airLineText.getText().equals("") && !fromText.getText().equals("")
								&& !toText.getText().equals("") && !capacityText.getText().equals(""))
							try {
								flights.insert(new Flight(Integer.parseInt(numText.getText().trim()),
										airLineText.getText().trim(), fromText.getText().trim(),
										toText.getText().trim(), Integer.parseInt(capacityText.getText().trim())));
								sucsses.setContentText("Added!");
								sucsses.show();

								airLineText.setDisable(true);
								fromText.setDisable(true);
								toText.setDisable(true);
								capacityText.setDisable(true);
								addButton.setDisable(true);

								numText.setEditable(true);

								numText.setText("");
								airLineText.setText("");
								fromText.setText("");
								toText.setText("");
								capacityText.setText("");

							} catch (NumberFormatException e3) {
								error.setContentText("You should just enter numbers in Flight Number and capacity.");
								error.show();
							} catch (Exception e3) {
								error.setContentText(e3.getMessage());
								error.show();
							}
						else {
							error.setContentText("You should fill all fields");
							error.show();
						}
					});
				} else {
					sucsses.setContentText(
							"This Flight number is exist, in the fields you have all info, edit it as you want and click edit.");
					sucsses.show();

					numText.setEditable(false);
					airLineText.setText(index.getData().getAirline() + "");
					fromText.setText(index.getData().getSource() + "");
					toText.setText(index.getData().getDestination() + "");
					capacityText.setText(index.getData().getCapacity() + "");

					airLineText.setDisable(false);
					fromText.setDisable(false);
					toText.setDisable(false);
					capacityText.setDisable(false);

					editButton.setDisable(false);
					editButton.setOnAction(e1 -> {
						if (!airLineText.getText().equals("") && !fromText.getText().equals("")
								&& !toText.getText().equals("") && !capacityText.getText().equals(""))
							try {
								index.getData().setAirline(airLineText.getText().trim());
								index.getData().setSource(fromText.getText().trim());
								index.getData().setDestination(toText.getText().trim());
								index.getData().setCapacity(Integer.parseInt(capacityText.getText().trim()));

								sucsses.setContentText("Done, Flight updated");
								sucsses.show();

								airLineText.setDisable(true);
								fromText.setDisable(true);
								toText.setDisable(true);
								capacityText.setDisable(true);
								editButton.setDisable(true);

								numText.setEditable(true);

								numText.setText("");
								airLineText.setText("");
								fromText.setText("");
								toText.setText("");
								capacityText.setText("");
							} catch (NumberFormatException e3) {
								error.setContentText("You should just enter numbers in Flight Number and capacity.");
								error.show();
							} catch (Exception e3) {
								error.setContentText(e3.getMessage());
								error.show();
							}
						else {
							error.setContentText("All fields should fe fill");
							error.show();
						}
					});
				}
			} catch (NumberFormatException e3) {
				error.setContentText("You should just enter numbers in Flight Number and capacity.");
				error.show();
			} catch (Exception e3) {
				error.setContentText(e3.getMessage());
				error.show();
			}

		});

		ImageView backIcone = new ImageView("return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		HBox control = new HBox(15, searchButton, addButton, editButton, back);
		control.setAlignment(Pos.CENTER);

		VBox all = new VBox(20, add, control);
		all.setAlignment(Pos.CENTER);

		back.setOnAction(e -> mainInterface(primaryStage));

		all.setBackground(
				new Background(new BackgroundImage(new Image("airport_324754607.png"), null, null, null, new BackgroundSize(1600, 900, false, false, false, false))));

		Scene s = new Scene(all, 1000, 600);
		primaryStage.setScene(s);
	}

	private void displayPassengers(Stage primaryStage) {
		Label flightNum = new Label("Flight Number: ");
		TextField flightNumField = new TextField();

		ImageView backIcone = new ImageView("return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");
		back.setOnAction(e -> mainInterface(primaryStage));

		HBox read = new HBox(30, flightNum, flightNumField);
		read.setAlignment(Pos.CENTER);

		TextArea result = new TextArea();
		result.setMaxWidth(800);
		result.setMinHeight(400);
		result.setEditable(false);

		flightNumField.setOnKeyTyped(e -> {
			try {
				Node<Flight> index = flights
						.search(new Flight(Integer.parseInt(flightNumField.getText().trim()), null, null, null, 0));
				if (index == null)
					result.setText("This Flight number is NOT exist !");
				else
					result.setText(index.getData().toString());
			} catch (NumberFormatException e2) {
				result.setText("Flight number should be just numbers !!!!");
			}

		});

		VBox all = new VBox(30, read, result, back);
		all.setAlignment(Pos.CENTER);
		all.setBackground(
				new Background(new BackgroundImage(new Image("airport_324754607.png"), null, null, null, new BackgroundSize(1600, 900, false, false, false, false))));

		Scene s = new Scene(all, 1000, 600);
		primaryStage.setScene(s);
	}

	private void displayFlights(Stage primaryStage) {
		TextArea display = new TextArea(flights.toString());
		display.setMaxWidth(800);
		display.setMinHeight(400);
		display.setEditable(false);

		ImageView backIcone = new ImageView("return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");
		back.setOnAction(e -> mainInterface(primaryStage));

		VBox all = new VBox(50, display, back);
		all.setAlignment(Pos.CENTER);
		all.setBackground(
				new Background(new BackgroundImage(new Image("airport_324754607.png"), null, null, null, new BackgroundSize(1600, 900, false, false, false, false))));

		Scene s = new Scene(all, 1000, 600);
		primaryStage.setScene(s);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
