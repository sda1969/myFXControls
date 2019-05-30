package application;
	
import java.util.prefs.Preferences;

import buttonOnEvent.ButtonOnEvent;
import buttonOnEvent.ToggleButtonOnEvent;
import connectButton.ConnectButton01;
import connectButton.ConnectButton02;
import connectButton.ConnectButtonController;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import textFieldSmart.TextFieldSmart;
import textFieldSmart.validators.Ip4Validator;


//---- для отладки исходного кода компонента
public class MainMyControls extends Application {
	public  final Preferences localPrefs = Preferences.userRoot().node("MainMyControls.class");
	public  BooleanProperty connEvent = new SimpleBooleanProperty(false);
	
	@Override
	public void start(Stage primaryStage) {
		try {
				
			// ----------ConnectButton-------------           
            ConnectButtonController connBtnCtrl01 = new ConnectButton01();
            connBtnCtrl01.setConnAction(()->{
            	System.out.println("Action Connect");
            	connEvent.setValue(true);
            });
            connBtnCtrl01.setDisconnAction(()->{
            	System.out.println("Action Disconnect");
            	connEvent.setValue(false);
            });
            connBtnCtrl01.setStatesName("Подключить","Отключить");
            connBtnCtrl01.setConnEventInverted(false);
            connBtnCtrl01.setConnEvent(connEvent);
            
            ConnectButtonController connBtnCtrl02 = new ConnectButton02();
            connBtnCtrl02.setConnAction(()->{
            	System.out.println("Action Connect");
            	connEvent.setValue(true);
            });
            connBtnCtrl02.setDisconnAction(()->{
            	System.out.println("Action Disconnect");
            	connEvent.setValue(false);
            });
            connBtnCtrl02.setStatesName("Подключить","Отключить");
            connBtnCtrl02.setConnEventInverted(false);
            connBtnCtrl02.setConnEvent(connEvent);
            //--------------------------------------------------------
            
            // ----------TextFieldSmart-------------
            TextFieldSmart tfs = new TextFieldSmart();
            tfs.setValidator(new Ip4Validator());
            tfs.setPreference("1.2.3.4", localPrefs);
            tfs.setConnEventInverted(true);
            tfs.setConnEvent(connEvent);
            tfs.setOnValidChange(str -> System.out.println(str));
            
            //--------------------------------------------------------
            
            //----------ButtonOnEvent-------------------------------
            ButtonOnEvent btnOnEvent = new ButtonOnEvent();
            btnOnEvent.setButtonAction(()->{
            	System.out.println("ButtonOnEvent is pressed");
            });
            btnOnEvent.setText("ButtonOnEvent");
            btnOnEvent.setConnEventInverted(false);
            btnOnEvent.setConnEvent(connEvent);
            
            //----------ToggleButtonOnEvent-------------------------------
            ToggleButtonOnEvent tglbtnOnEvent = new ToggleButtonOnEvent();
            tglbtnOnEvent.setButtonPressAction(()->{
            	System.out.println("ToggleButtonOnEvent is pressed");
            });
            tglbtnOnEvent.setButtonReleaseAction(()->{
            	System.out.println("ToggleButtonOnEvent is released");
            });
            tglbtnOnEvent.setText("ToggleButtonOnEvent");
            tglbtnOnEvent.setConnEventInverted(false);
            tglbtnOnEvent.setConnEvent(connEvent);
            //----------------------------------------------------
            VBox root = new VBox();
            root.getChildren().add(connBtnCtrl01);
            root.getChildren().add(connBtnCtrl02);
            root.getChildren().add(tfs);
            root.getChildren().add(btnOnEvent);
            root.getChildren().add(tglbtnOnEvent);
            
            primaryStage.setTitle("My Controls test");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
