package com.intervalintl.onboarding;

import android.util.Log;
import com.intervalintl.common.StateContext;
import com.intervalintl.workflow.Flow;
import com.intervalintl.workflow.common.Constants;
import com.intervalintl.workflow.common.FlowViewPortService;
import com.intervalintl.workflow.view.FlowViewPort;


public class SplashFlow extends Flow<StateContext, SplashFlow.SplashEvent> {

    public enum Stage {
        Idle,
        Splash,
        Done
    }

    private Stage stage = Stage.Idle;;
    private FlowViewPort flowViewPort;

    // TODO: Use PublishSubject from RxJava
    private Listener listener;


    public SplashFlow(String viewModelId) {
        super(viewModelId);
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void onStateContextUpdate(StateContext stateContext) {

        flowViewPort = stateContext
                .getState(FlowViewPortService.class, Constants.INSTANCE.getDEFAULT_FLOW_VIEWPORT_SERVICE_ID())
                .getFlowViewPort();
    }

    @Override
    public void start() {

        if (stage.equals(Stage.Idle)) {
            showSplashScreen();
        }
        else if (stage.equals(Stage.Done)) {
            Log.d("SplashViewModel","Calling start but SplashViewModel is done.");
        }
    }

    @Override
    public void stop() {

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public  void showSplashScreen() {
        SplashFragment splashFragment = new SplashFragment();
        splashFragment.setFlowId(getFlowId());

        flowViewPort.setView(splashFragment, Constants.INSTANCE.getSPLASH_FRAGMENT_TAG());
    }



    public void startSplashTimeout() {
        stage = Stage.Splash;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep( 3000);

                    Log.d("SplashViewModel","SplashViewModel: Dispatching splash timeout");
                    listener.onSplashFinished();
                    stage = Stage.Done;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();

    }


    public interface Listener {
        void onSplashFinished();
    }


    public class SplashEvent {}

}
