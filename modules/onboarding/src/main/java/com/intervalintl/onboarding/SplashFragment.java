package com.intervalintl.onboarding;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.intervalintl.workflow.view.FlowFragment;


public class SplashFragment extends FlowFragment<SplashFlow> {

    private ViewGroup rootView;
    private SplashFlow splashFlow;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup)inflater.inflate(R.layout.fragment_splash, container, false);
        return rootView;
    }

    @Override
    protected void onFlowBound(SplashFlow flow) {
        Log.d("SplashFragment","SplashFragment - binding to SplashViewModel");
        splashFlow = flow;
        updateView();
    }

    private void updateView() {
        if (splashFlow.getStage() == SplashFlow.Stage.Idle) {
            splashFlow.startSplashTimeout();
        }
    }

}
