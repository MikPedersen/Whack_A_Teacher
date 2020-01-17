package demo;

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class WhackGameMenu extends FXGLMenu {

    public WhackGameMenu() {
        super(MenuType.GAME_MENU);

        WhackGameMenu.WhackButton exitToMenu = new WhackGameMenu.WhackButton("Return to main menu", () -> this.fireExitToMainMenu());
        WhackGameMenu.WhackButton resumeGame = new WhackGameMenu.WhackButton("Resume game", ()-> this.fireResume());

        exitToMenu.setTranslateX(FXGL.getAppWidth()/2 - 200/2);
        exitToMenu.setTranslateY(FXGL.getAppHeight()/2 - 40);
        resumeGame.setTranslateX(FXGL.getAppWidth()/2 - 200/2);
        resumeGame.setTranslateY(FXGL.getAppHeight()/2 +20);

        getMenuContentRoot().getChildren().add(exitToMenu);
        getMenuContentRoot().getChildren().add(resumeGame);
    }


    @Override
    protected Button createActionButton( StringBinding stringBinding, Runnable runnable) {
        return new Button();
    }

    @Override
    protected Button createActionButton( String s, Runnable runnable) {
        return new Button();
    }

    @Override
    protected Node createBackground(double width, double height) {
        return new ImageView(FXGL.image("background.png"));
    }

    @Override
    protected Node createProfileView( String s) {
        return new Text();
    }

    @Override
    protected Node createTitleView( String s) {
        //Creates the title
        Text title = new Text();
        title.setText("Game is paused");
        title.setTranslateY(FXGL.getAppHeight()/2 -70);
        title.setTranslateX(FXGL.getAppWidth()/2 -420);
        title.setFill(Color.ORANGERED);
        title.setFont(Font.font("Verdana", FontWeight.BOLD,90));
        title.setEffect(new DropShadow(30, Color.BLACK));
        return title;}

    @Override
    protected Node createVersionView( String s) {
        return new Text();
    }

    private static class WhackButton extends StackPane {
        //Creates the button to start game
        public WhackButton (String name, Runnable action) {
            Rectangle bg = new Rectangle(200, 40);
            bg.setStroke(Color.WHITE);

            Text text = FXGL.getUIFactory().newText(name, Color.WHITE, 18);

            bg.fillProperty().bind(
                    Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.BLACK)
            );
            text.fillProperty().bind(
                    Bindings.when(hoverProperty()).then(Color.BLACK).otherwise(Color.WHITE)
            );
            setOnMouseClicked(e -> action.run());
            getChildren().addAll(bg, text);

        }
    }
}
