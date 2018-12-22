package io.github.gravetii.controller;

import io.github.gravetii.game.GameService;
import io.github.gravetii.scene.FxScene;
import io.github.gravetii.scene.game.GameScene;
import io.github.gravetii.scene.help.AboutScene;
import io.github.gravetii.scene.help.WhatIsItScene;
import io.github.gravetii.scene.settings.GameTimeScene;
import io.github.gravetii.scene.theme.ChangeThemeScene;
import io.github.gravetii.store.StoreUtility;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class MenuBarController implements FxController {

  private Stage stage;

  public MenuBarController(Stage stage) {
    this.stage = stage;
  }

  private Stage newModalWindow() {
    Stage stage = new Stage();
    stage.setResizable(false);
    stage.initOwner(this.stage);
    stage.initModality(Modality.APPLICATION_MODAL);
    return stage;
  }

  private boolean showQuitGameAlert() {
    Alert alert =
        new Alert(
            Alert.AlertType.CONFIRMATION, "Quit current game?",
                ButtonType.NO, ButtonType.YES);
    alert.setHeaderText("");
    alert.setTitle("Quit?");
    alert.initOwner(this.stage);
    Optional<ButtonType> type = alert.showAndWait();
    return type.isPresent() && type.get() == ButtonType.YES;
  }

  private boolean currentGameCheck() {
    return !StoreUtility.isGameRunning() || this.showQuitGameAlert();
  }

  private boolean exitCheck() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
            "Are you sure?", ButtonType.NO, ButtonType.YES);
    alert.setHeaderText("");
    alert.setTitle("Really Exit?");
    alert.initOwner(this.stage);
    Optional<ButtonType> type = alert.showAndWait();
    return type.isPresent() && type.get() == ButtonType.YES;
  }

  @FXML
  public void newGame(ActionEvent event) throws Exception {
    if (this.currentGameCheck()) {
      FxScene scene = new GameScene(this.stage);
      scene.show();
      StoreUtility.setGameRunning(true);
    }
  }

  @FXML
  public void setGameTime(ActionEvent event) throws Exception {
    Stage stage = this.newModalWindow();
    FxScene scene = new GameTimeScene(stage);
    scene.show();
  }

  @FXML
  public void exit(ActionEvent event) throws Exception {
    if (this.exitCheck()) {
      GameService.close();
      this.stage.close();
      StoreUtility.close();
      Platform.exit();
    }
  }

  @FXML
  public void editTheme(ActionEvent event) throws Exception {
    Stage stage = this.newModalWindow();
    FxScene scene = new ChangeThemeScene(stage);
    scene.show();
  }

  @FXML
  public void showAbout(ActionEvent event) throws Exception {
    Stage stage = this.newModalWindow();
    FxScene scene = new AboutScene(stage);
    scene.show();
  }

  @FXML
  public void whatIsIt(ActionEvent event) throws Exception {
    Stage stage = this.newModalWindow();
    FxScene scene = new WhatIsItScene(stage);
    scene.show();
  }
}
