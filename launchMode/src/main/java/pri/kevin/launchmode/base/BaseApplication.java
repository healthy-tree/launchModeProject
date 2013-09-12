package pri.kevin.launchmode.base;

import android.app.ActivityManager;
import android.app.Application;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Created by Kevin on 13-9-12.
 */
public class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getSimpleName();
    private ActivityManager activityManager;
    private HashMap<Integer, Stack<BaseActivity>> taskMap = null;

    @Override
    public void onCreate() {
        super.onCreate();
        activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        taskMap = new HashMap<Integer, Stack<BaseActivity>>();
    }

    public void pushToStack(BaseActivity activity){
        int currentTaskId = getCurrentTaskId();
        if (!taskMap.containsKey(currentTaskId)){
            taskMap.put(currentTaskId, new Stack<BaseActivity>());
        }
        Stack<BaseActivity> stack = taskMap.get(currentTaskId);
        stack.add(activity);
    }

    public int getCurrentTaskId() {
        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo runningTask = runningTasks.get(0);
        return runningTask.id;
    }

    public void removeFromStack(BaseActivity activity){
        Stack<BaseActivity> stack = taskMap.get(getCurrentTaskId());
        if(stack != null){
            stack.remove(activity);
        }
    }

    public Stack<BaseActivity> getCurrentTask(){
        return taskMap.get(getCurrentTaskId());
    }

}
