package com.example.rxpermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    private  String TAG=MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissionRequest(this);
    }

    /**
     * ①.申请单个权限
     * @param activity
     *总结
     * 1、返回true：申请成功 ；返回false：申请失败
     * 2、同意后，之后再申请此权限则不再弹出提示框
     * 3、不要使用compose方法，如RxLifeCycle
     */
    public void checkPermissionRequest(FragmentActivity activity) {
        RxPermissions permissions = new RxPermissions(activity);
        permissions.setLogging(true);
        permissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.e(TAG, "checkPermission22--:" + aBoolean);
                    }
                });
    }

    /**
     * ②.申请多个权限
     * @param activity
     *总结
     * 1、只要有一个禁止，则返回false；全部同意，则返回true。
     * 2、某个权限同意后，之后再申请此权限则不再弹出提示框，其他的会继续弹出
     * 3、此申请三个权限，但只会有两个弹窗，READ_EXTERNAL_STORAGE和WRITE_EXTERNAL_STORAGE 是属于一组，
     * 链接：https://www.jianshu.com/p/c1219d1d2401
     *
     */
    public void checkPermissionRequest2(FragmentActivity activity) {
        RxPermissions permissions = new RxPermissions(activity);
        permissions.setLogging(true);
        permissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CALENDAR)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.e(TAG, "checkPermission22--:" + aBoolean);
                    }
                });
    }

    /**
     *③.申请单个权限，获得详细信息
     * @param activity
     * 总结
     * 1、只返回一个Permission对象
     */
    public void checkPermissionRequestEach3(FragmentActivity activity) {
        RxPermissions permissions = new RxPermissions(activity);
        permissions.setLogging(true);
        permissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        Log.e(TAG, "checkPermissionRequestEach--:" + "-permission-:" + permission.name + "---------------");
                        if (permission.name.equalsIgnoreCase(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            if (permission.granted) {//同意后调用
                                Log.e(TAG, "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-:" + true);
                            } else if (permission.shouldShowRequestPermissionRationale){//禁止，但没有选择“以后不再询问”，以后申请权限，会继续弹出提示
                                Log.e(TAG, "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-shouldShowRequestPermissionRationale:" + false);
                            }else {//禁止，但选择“以后不再询问”，以后申请权限，不会继续弹出提示
                                Log.e(TAG, "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-:" + false);
                            }
                        }
                    }
                });
    }


    /**
     * ④.申请多个权限，获得详细信息
     * @param activity
     * 总结
     * 1、返回三个permission对象
     */
    public void checkPermissionRequestEach4(FragmentActivity activity) {
        RxPermissions permissions = new RxPermissions(activity);
        permissions.setLogging(true);
        permissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CALENDAR)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        Log.e(TAG, "checkPermissionRequestEach--:" + "-permission-:" + permission.name + "---------------");
                        if (permission.name.equalsIgnoreCase(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            if (permission.granted) {//同意后调用
                                Log.e(TAG, "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-:" + true);
                            } else if (permission.shouldShowRequestPermissionRationale){//禁止，但没有选择“以后不再询问”，以后申请权限，会继续弹出提示
                                Log.e(TAG, "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-shouldShowRequestPermissionRationale:" + false);
                            }else {//禁止，但选择“以后不再询问”，以后申请权限，不会继续弹出提示
                                Log.e(TAG, "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-:" + false);
                            }
                        }
                        if (permission.name.equalsIgnoreCase(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            if (permission.granted) {
                                Log.e(TAG, "checkPermissionRequestEach--:" + "-WRITE_EXTERNAL_STORAGE-:" + true);
                            }else if (permission.shouldShowRequestPermissionRationale){
                                Log.e(TAG, "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-shouldShowRequestPermissionRationale:" + false);
                            } else {
                                Log.e(TAG, "checkPermissionRequestEach--:" + "-WRITE_EXTERNAL_STORAGE-:" + false);
                            }
                        }
                        if (permission.name.equalsIgnoreCase(Manifest.permission.READ_CALENDAR)) {
                            if (permission.granted) {
                                Log.e(TAG, "checkPermissionRequestEach--:" + "-READ_CALENDAR-:" + true);
                            }else if (permission.shouldShowRequestPermissionRationale){
                                Log.e(TAG, "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-shouldShowRequestPermissionRationale:" + false);
                            } else {
                                Log.e(TAG, "checkPermissionRequestEach--:" + "-READ_CALENDAR-:" + false);
                            }
                        }
                    }
                });
    }

    /**
     * ⑤.申请多个权限，获取合并后的详细信息
     * @param activity
     */
    public void checkPermissionRequestEachCombined(FragmentActivity activity) {
        RxPermissions permissions = new RxPermissions(activity);
        permissions.setLogging(true);
        permissions.requestEachCombined(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CALENDAR)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        Log.e(TAG, "checkPermissionRequestEachCombined--:" + "-permission-:" + permission.name + "---------------");
                        if (permission.granted) {//全部同意后调用
                            Log.e(TAG, "checkPermissionRequestEachCombined--:" + "-READ_EXTERNAL_STORAGE-:" + true);
                        } else if (permission.shouldShowRequestPermissionRationale) {//只要有一个选择：禁止，但没有选择“以后不再询问”，以后申请权限，会继续弹出提示
                            Log.e(TAG, "checkPermissionRequestEachCombined--:" + "-READ_EXTERNAL_STORAGE-shouldShowRequestPermissionRationale:" + false);
                        } else {//只要有一个选择：禁止，但选择“以后不再询问”，以后申请权限，不会继续弹出提示
                            Log.e(TAG, "checkPermissionRequestEachCombined--:" + "-READ_EXTERNAL_STORAGE-:" + false);
                        }
                    }
                });
    }

    /**
     * 4、使用用例结合RxJava
     * @param activity
     * 总结
     * 1、ensure、ensureEach、ensureEachCombined用法跟之前类似
     */
    public void checkPermissionsEnsure(FragmentActivity activity) {
        final RxPermissions permissions = new RxPermissions(activity);
        permissions.setLogging(true);
        Observable.timer(100, TimeUnit.MILLISECONDS)
                .compose(permissions.ensure(Manifest.permission.READ_EXTERNAL_STORAGE))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.e(TAG, "checkPermissionsEnsure--:" + "-READ_EXTERNAL_STORAGE-:" + aBoolean);
                    }
                });
    }

    /**
     * 5.检查某个权限是否被申请
     *
     * @param activity
     */
    public void checkPermissionsIsGranted(FragmentActivity activity) {
        RxPermissions permissions = new RxPermissions(activity);
        permissions.setLogging(true);
        Log.e(TAG, "checkPermissionsIsGranted--:" + "-WRITE_EXTERNAL_STORAGE-:" + permissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }








}
