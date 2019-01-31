package application;
	
import connectButton.ConnectButton01;
import connectButton.ConnectButton02;
import connectButton.ConnectButtonController;

import eventBus.EventBus;
import eventBus.FXEventBus;
import eventBus.events.ConnectionEvent;
import javafx.application.Application;
import javafx.scene.Scene;
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
            //ConnectButtonController connBtnCtrl = new ConnectButton01();
            ConnectButtonController connBtnCtrl = new ConnectButton02();
            
            connBtnCtrl.setConnAction(()->{
            	System.out.println("Action Connect");
            	eventBus.post(new ConnectionEvent(true, "MainMyControls"));
            });
            connBtnCtrl.setDisconnAction(()->{
            	System.out.println("Action Disconnect");
            	eventBus.post(new ConnectionEvent(false, "MainMyControls"));
            });
            connBtnCtrl.setEventBus(eventBus);
            connBtnCtrl.setStatesName("Подключить","Отключить");
            //--------------------------------------------------------
            
            // ----------textFieldSmart-------------
            TextFieldSmart tfs = new TextFieldSmart();
            tfs.setValidator(new Ip4Validator());
            tfs.setText("1.2.3.45");
            
            //--------------------------------------------------------
            primaryStage.setTitle("My Controls test");
            //primaryStage.setScene(new Scene(connBtnCtrl));
            primaryStage.setScene(new Scene(tfs));
            primaryStage.show();	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
