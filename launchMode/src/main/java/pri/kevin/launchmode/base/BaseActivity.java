package pri.kevin.launchmode.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import pri.kevin.launchmode.R;
import pri.kevin.launchmode.aty.SingleInstanceActivity;
import pri.kevin.launchmode.aty.SingleTaskActivity;
import pri.kevin.launchmode.aty.SingleTopActivity;
import pri.kevin.launchmode.aty.StandardActivity;

/**
 * Created by Kevin on 13-9-12.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {
    private static final String TAG = BaseActivity.class.getSimpleName();
    private Button btn_standard, btn_singleTop, btn_singleTask, btn_singleInstance;
    private BaseApplication app;
    private Handler handler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (BaseApplication) getApplication();
        handler = new Handler();
        app.pushToStack(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        btn_standard = (Button) this.findViewById(R.id.btn_standard);
        btn_singleTop = (Button) this.findViewById(R.id.btn_singleTop);
        btn_singleTask = (Button) this.findViewById(R.id.btn_singleTask);
        btn_singleInstance = (Button) this.findViewById(R.id.btn_singleInstance);

        btn_standard.setOnClickListener(this);
        btn_singleTop.setOnClickListener(this);
        btn_singleTask.setOnClickListener(this);
        btn_singleInstance.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Runnable runnable = new TaskInfoRunnable(this);
        handler.postDelayed(runnable, 500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.removeFromStack(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_standard:
                intent = new Intent();
                intent.setClass(this, StandardActivity.class);
                break;
            case R.id.btn_singleTop:
                intent = new Intent();
                intent.setClass(this, SingleTopActivity.class);
                break;
            case R.id.btn_singleTask:
                intent = new Intent();
                intent.setClass(this, SingleTaskActivity.class);
                break;
            case R.id.btn_singleInstance:
                intent = new Intent();
                intent.setClass(this, SingleInstanceActivity.class);
                break;
            default:
                throw new IllegalArgumentException("按键错误");
        }
        startActivity(intent);
    }

    public abstract int getBackgroundColour();
}