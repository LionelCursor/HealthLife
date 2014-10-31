package com.example.bdlocsdk;

import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.comapi.mapcontrol.BNMapController;
import com.baidu.navisdk.comapi.routeplan.BNRoutePlaner;
import com.baidu.navisdk.model.datastruct.LocData;
import com.baidu.navisdk.model.datastruct.SensorData;
import com.baidu.navisdk.ui.routeguide.BNavigator;
import com.baidu.navisdk.ui.routeguide.IBNavigatorListener;
import com.baidu.nplatform.comapi.map.MapGLSurfaceView;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

public class BNavigatorActivity extends Activity {

	private IBNavigatorListener mBNavigatorListener = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		mBNavigatorListener = new IBNavigatorListener() {  
			 
	        @Override  
	        public void onYawingRequestSuccess() {  
	            // TODO ƫ������ɹ�  
	 
	        }  
	 
	        @Override  
	        public void onYawingRequestStart() {  
	            // TODO ��ʼƫ������  
	 
	        }  
	 
	        @Override  
	        public void onPageJump(int jumpTiming, Object arg) {  
	            // TODO ҳ����ת�ص�  
	            if(IBNavigatorListener.PAGE_JUMP_WHEN_GUIDE_END == jumpTiming){  
	                finish();  
	            }else if(IBNavigatorListener.PAGE_JUMP_WHEN_ROUTE_PLAN_FAIL == jumpTiming){  
	                finish();  
	            }  
	        }  
	 
	        @Override  
	        public void notifyGPSStatusData(int arg0) {  
	            // TODO Auto-generated method stub  
	 
	        }  
	 
	        @Override  
	        public void notifyLoacteData(LocData arg0) {  
	            // TODO Auto-generated method stub  
	 
	        }  
	 
	        @Override  
	        public void notifyNmeaData(String arg0) {  
	            // TODO Auto-generated method stub  
	 
	        }  
	 
	        @Override  
	        public void notifySensorData(SensorData arg0) {  
	            // TODO Auto-generated method stub  
	 
	        }  
	 
	        @Override  
	        public void notifyStartNav() {  
	            // TODO Auto-generated method stub  
	            BaiduNaviManager.getInstance().dismissWaitProgressDialog();
	        }

			@Override
			public void notifyViewModeChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		};
	
	 //����NmapView  
    MapGLSurfaceView nMapView = BaiduNaviManager.getInstance().createNMapView(this);  

    //����������ͼ  
    View navigatorView = BNavigator.getInstance().init(BNavigatorActivity.this, getIntent().getExtras(), nMapView);  

    //�����ͼ  
    setContentView(navigatorView);
	}
	
	@Override  
    public void onResume() {  
        BNavigator.getInstance().resume();  
        super.onResume();  
        BNMapController.getInstance().onResume();  
    };  
 
    @Override  
    public void onPause() {  
        BNavigator.getInstance().pause();  
        super.onPause();  
        BNMapController.getInstance().onPause();  
    }  
 
    @Override  
    public void onConfigurationChanged(Configuration newConfig) {  
        BNavigator.getInstance().onConfigurationChanged(newConfig);  
        super.onConfigurationChanged(newConfig);  
    }  
 
    public void onBackPressed(){  
        BNavigator.getInstance().onBackPressed();  
    }  
 
    @Override  
    public void onDestroy(){  
        BNavigator.destory();  
        BNRoutePlaner.getInstance().setObserver(null);  
        super.onDestroy();  
    }
}
