package application;
	
import connectButton.ConnectButton01;
import connectButton.ConnectButton02;
import connectButton.ConnectButtonController;

import eventBus.EventBus;
import eventBus.FXEventBus;
import eventBus.events.ConnectionEvent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import textFieldSmart.TextFieldSmart;
import textFieldSmart.validators.Ip4Validator;


//---- для отладки исходного кода компонента
public class MainMyControls extends Application {
	public  final EventBus eventBus = new FXEventBus();
	@Override
	public void start(Stage primaryStage) {
		try {
				
			// ----------connButton-------------           
            ConnectButtonController connBtnCtrl01 = new ConnectButton01();
            connBtnCtrl01.setConnAction(()->{
            	System.out.println("Action Connect");
            	eventBus.post(new ConnectionEvent(true, "MainMyControls"));
            });
            connBtnCtrl01.setDisconnAction(()->{
            	System.out.println("Action Disconnect");
            	eventBus.post(new ConnectionEvent(false, "MainMyControls"));
            });
            connBtnCtrl01.setEventBus(eventBus);
            connBtnCtrl01.setStatesName("Подключить","Отключить");
            connBtnCtrl01.setConnEventInverted(false);
            connBtnCtrl01.setConnEventEnabled(true);
            
            ConnectButtonController connBtnCtrl02 = new ConnectButton02();
            connBtnCtrl02.setConnAction(()->{
            	System.out.println("Action Connect");
            	eventBus.post(new ConnectionEvent(true, "MainMyControls"));
            });
            connBtnCtrl02.setDisconnAction(()->{
            	System.out.println("Action Disconnect");
            	eventBus.post(new ConnectionEvent(false, "MainMyControls"));
            });
            connBtnCtrl02.setEventBus(eventBus);
            connBtnCtrl02.setStatesName("Подключить","Отключить");
            connBtnCtrl02.setConnEventInverted(false);
            connBtnCtrl02.setConnEventEnabled(true);
            //--------------------------------------------------------
            
            // ----------textFieldSmart-------------
            TextFieldSmart tfs = new TextFieldSmart();
            tfs.setValidator(new Ip4Validator());
            tfs.setText("1.2.3.45");
            tfs.setEventBus(eventBus);
            tfs.setConnEventInverted(true);
            tfs.setConnEventEnabled(true);
            
            //--------------------------------------------------------
            VBox root = new VBox();
            root.getChildren().add(connBtnCtrl01);
            root.getChildren().add(connBtnCtrl02);
            root.getChildren().add(tfs);
            
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
