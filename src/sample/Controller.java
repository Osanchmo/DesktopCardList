package sample;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.io.IOException;

public class Controller {

    CardApi proc = new CardApi();

    @FXML
    ListView<Card> llistaCartes;
    @FXML
    ImageView cardImage;
    @FXML
    Label cardName;
    @FXML
    Label cardText;
    @FXML
    Label cardType;

    public void onClick(ActionEvent e) throws IOException {

        Button tempBton;
        MenuItem tempMenuItem;
        String option;
        Class clase = e.getSource().getClass();

        if (clase == Button.class) {
            tempBton = (Button) e.getSource();
            option = tempBton.getId();
        } else {
            tempMenuItem = (MenuItem) e.getSource();
            option = tempMenuItem.getId();
        }
        switch (option) {
            case "quit":
                Platform.exit();
                break;
            case "App":
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setContentText("Listado de cartas magic");
                info.setTitle("info");
                info.setHeaderText("JavaFX");
                info.show();
        }
    }

    public void initialize() {
        llistaCartes.setCellFactory((list) -> {
            return new ListCell<Card>() {
                @Override
                public void updateItem(Card item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Image img = new Image(item.getImage(),80,70,true,false,true);
                        ImageView imgview = new ImageView(img);
                        imgview.setCache(true);
                        setGraphic(imgview);
                        setText(item.getName());
                    }
                }
            };
        });
        //fem que es separi el text en linies
        cardText.setWrapText(true);
        //li posem limit de size
        cardText.setMaxWidth(300);
        llistaCartes.setCache(true);

        ObservableList<Card> cards = FXCollections.observableArrayList(proc.getCards());
        llistaCartes.setItems(cards);

        llistaCartes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    cardImage.setCache(true);
                    cardImage.setImage(new Image (newValue.getImage()));
                    cardName.setText(newValue.getName());
                    cardType.setText(newValue.getType() + " - " + newValue.getRarity());
                    cardText.setText(newValue.getText());
                }
        );
    }
}
