package me.williandrade;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class FXMLExampleController {
	String path = "/Users/william/Documents/projects/networkClass/dir";
	String NAME = "/doIt.toCalc";

	@FXML
	private TextField firstText;

	@FXML
	private TextField secondText;

	@FXML
	private Text resultText;

	@FXML
	private ChoiceBox<String> operator;

	@FXML
	private Pane resultPane;

	public void initialize() {
		this.initTexts();
		this.initSelect();
	}

	private void initTexts() {
		this.firstText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					firstText.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		this.secondText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					firstText.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
	}

	private void initSelect() {
		this.operator.getItems().clear();
		this.operator.getItems().add("x");
		this.operator.getItems().add("/");
		this.operator.getItems().add("+");
		this.operator.getItems().add("-");
	}

	@FXML
	public void calc(ActionEvent event) {
		String output = "";

		output += this.firstText.getText();
		output += ":::";
		output += this.operator.getValue();
		output += ":::";
		output += this.secondText.getText();

		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path + NAME, false)));
			out.write(output);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void result(ActionEvent event) {
		try {
			String content = getContent(path, NAME + ".finish");
			resultText.setText(content);
			resultPane.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getContent(String path, String fileName) throws Exception {
		byte[] encoded = Files.readAllBytes(Paths.get(path + "/" + fileName));
		String content = new String(encoded, "UTF-8");
		return content;
	}
}
