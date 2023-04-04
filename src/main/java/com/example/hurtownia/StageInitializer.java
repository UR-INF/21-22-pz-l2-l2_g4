package com.example.hurtownia;

import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;

public class StageInitializer implements ApplicationListener<Start.StageReadyEvent> {
    @Override
    public void onApplicationEvent(Start.StageReadyEvent event){
        Stage stage = event.getStage();
    }
}
