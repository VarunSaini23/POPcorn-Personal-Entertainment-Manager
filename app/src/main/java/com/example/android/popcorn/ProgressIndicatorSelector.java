package com.example.android.popcorn;

import java.util.ArrayList;
import java.util.Collections;

public class ProgressIndicatorSelector {

    public String ProgressIndicatorSelector(){
        ArrayList<String> progressSelector = new ArrayList<String>();
        progressSelector.add("BallPulseIndicator");
        progressSelector.add("BallGridPulseIndicator");
        progressSelector.add("BallClipRotatePulseIndicator");
        progressSelector.add("BallScaleIndicator");
        progressSelector.add("LineScaleIndicator");
        progressSelector.add("LineScalePartyIndicator");
        progressSelector.add("BallScaleMultipleIndicator");
        progressSelector.add("TriangleSkewSpinIndicator");
        progressSelector.add("PacmanIndicator");
        progressSelector.add("BallGridBeatIndicator");
        progressSelector.add("SemiCircleSpinIndicator");

        Collections.shuffle(progressSelector);

        return progressSelector.get(0);




    }




}
