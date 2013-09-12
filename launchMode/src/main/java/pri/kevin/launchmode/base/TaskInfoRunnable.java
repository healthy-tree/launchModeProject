package pri.kevin.launchmode.base;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Stack;

import pri.kevin.launchmode.R;

/**
 * Created by Kevin on 13-9-11.
 */
public class TaskInfoRunnable implements Runnable {

    private final BaseApplication app;
    private final BaseActivity baseActivity;
    private LinearLayout tasks;
    private TextView taskIdField;
    private LinearLayout taskView;

    public TaskInfoRunnable(BaseActivity activity) {
        app = (BaseApplication) activity.getApplication();
        baseActivity = activity;
        tasks = (LinearLayout) activity.findViewById(R.id.task_container);
        tasks.removeAllViews();
    }

    private void buildTask() {
        LayoutInflater inflater = LayoutInflater.from(baseActivity);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.task_info_item, null);
//        linearLayout.removeAllViews();
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.BLUE);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(150, LinearLayout.LayoutParams.WRAP_CONTENT);
        taskIdField = (TextView)linearLayout.findViewById(R.id.task_id_field);
        taskView = (LinearLayout)linearLayout.findViewById(R.id.task_view);
        showCurrentTaskId();
        showCurrentTaskActivites();
//        return linearLayout;
        tasks.addView(linearLayout, param);
        tasks.invalidate();
    }

    private void showCurrentTaskActivites() {
        Stack<BaseActivity> task = app.getCurrentTask();
        for (int location = task.size() - 1; location >= 0; location--) {
            BaseActivity activity = task.get(location);
            showActivityDetails(activity);
        }
    }

    private void showActivityDetails(BaseActivity activity) {
//        String activityName = activity.getClass().getSimpleName();
//        Log.v(LOG_TAG, activityName);
        ImageView activityRepresentation = getActivityRepresentation(activity);
        taskView.addView(activityRepresentation);
        tasks.invalidate();
    }

    private ImageView getActivityRepresentation(BaseActivity activity) {
        ImageView image = new ImageView(activity);
        int color = activity.getBackgroundColour();
        image.setBackgroundResource(color);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10);
        params.setMargins(2, 2, 2, 2);
        image.setLayoutParams(params);
        return image;
    }

    private void showCurrentTaskId() {
        int taskId = app.getCurrentTaskId();
//        String taskMessage = "Activities in current task (id: " + taskId + ")";
        taskIdField.setText("Task id: " + taskId);
    }

    @Override
    public void run() {
        buildTask();
    }
}
