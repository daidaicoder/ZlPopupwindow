package cn.itcss.zlpop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 自定义PopupWindow实现
 * by daizelai on 2018/11/28 11:21
 *
 * 使用方式：
 *
 * 1.弹窗
 * MyPopupWindow myPopupWindow = new MyPopupWindow.Builder()
 *                     .setContext(this)
 *                     .setView(R.layout.avatar_popup)
 *                     .setFocus(true)
 *                     .setOutSideCancel(true)
 *                     .setAnimStyle(R.style.popup_anim_style)
 *                     .setAlpha(MainActivity.this, 0.7f)
 *                     .builder()
 *                     .showAtLocation(R.layout.activity_main, Gravity.BOTTOM, 0, 0);
 * 2.事件
 * myPopupWindow.setOnClickListener(R.id.pop_camera, new View.OnClickListener() {
 *                 @Override
 *                 public void onClick(View view) {
 *                     Toast.makeText(MainActivity.this, "拍照了", Toast.LENGTH_SHORT).show();
 *                 }
 *             });
 */
public class MyPopupWindow implements PopupWindow.OnDismissListener {
    private Context mContext;
    private Activity mActivity;
    private PopupWindow mPopupWindow;
    private View rootView;

    public MyPopupWindow(Builder builder) {
        this.mContext = builder.mContext;
        rootView = LayoutInflater.from(mContext).inflate(builder.viewId, null);

        if (builder.width == 0 || builder.height == 0) {
            builder.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            builder.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }

        mPopupWindow = new PopupWindow(rootView, builder.width, builder.height, builder.focus);

        // 配置mPopupWindow的UI效果
        mPopupWindow.setOutsideTouchable(builder.outSideCancel);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setAnimationStyle(builder.animStyle);

        // 设置背景色
        if (builder.alpha > 0 && builder.alpha < 1) {
            mActivity = builder.mActivity;
            WindowManager.LayoutParams params = builder.mActivity.getWindow().getAttributes();
            params.alpha = builder.alpha;
            builder.mActivity.getWindow().setAttributes(params);
        }

        mPopupWindow.setOnDismissListener(this);
    }

    /**
     * 隐藏PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            // 恢复背景色
            if (mActivity != null) {
                WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
                params.alpha = 1.0f;
                mActivity.getWindow().setAttributes(params);
            }
        }
    }

    /**
     * 根据id去获取view
     * @param viewId
     * @return
     */
    public View getItemView(int viewId) {
        if (mPopupWindow != null) {
            return this.rootView.findViewById(viewId);
        }
        return null;
    }

    /**
     * 根据父布局，显示位置
     * @param viewId
     * @param gravity
     * @param x
     * @param y
     * @return
     */
    public MyPopupWindow showAtLocation(int viewId, int gravity, int x, int y){
        if (mPopupWindow != null) {
            View mView = LayoutInflater.from(mContext).inflate(viewId, null);
            mPopupWindow.showAtLocation(mView, gravity, x, y);
        }
        return this;
    }

    /**
     * 根据id获取view，并显示在该view位置
     * @param viewId
     * @param gravity
     * @param offx
     * @param offy
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public MyPopupWindow showAsDropDown(int viewId, int gravity, int offx, int offy) {
        if (mPopupWindow != null) {
            View mView = LayoutInflater.from(mContext).inflate(viewId, null);
            mPopupWindow.showAsDropDown(mView, offx, offy, gravity);
        }
        return this;
    }

    /**
     * 显示在view的不同位置
     * @param view
     * @param gravity
     * @param offx
     * @param offy
     * @return
     */
    public MyPopupWindow showAsDropDown(View view, int gravity, int offx, int offy) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(view, offx, offy, gravity);
        }
        return this;
    }

    /**
     * 根据ID去设置监听事件
     * @param viewId
     * @param listener
     */
    public void setOnFocusListener(int viewId, View.OnFocusChangeListener listener) {
        getItemView(viewId).setOnFocusChangeListener(listener);
    }

    /**
     * 根据ID设置点击事件监听
     * @param viewId
     * @param listener
     */
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        getItemView(viewId).setOnClickListener(listener);
    }

    /**
     * 恢复初始背景
     */
    @Override
    public void onDismiss() {
        if (mActivity != null) {
            WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
            params.alpha = 1.0f;
            mActivity.getWindow().setAttributes(params);
        }
    }

    /**
     * 在实体类中提供一个静态类，添加公共方法以被使用者调用
     */
    public static class Builder {
        private Context mContext;
        private Activity mActivity;
        // 弹窗布局文件
        private int viewId;
        private int width;
        private int height;
        // 设置是否可以获取焦点
        private boolean focus;
        // 是否外部点击取消
        private boolean outSideCancel;
        // 动画显示
        private int animStyle;
        // 调协背景色
        private float alpha;

        public MyPopupWindow builder() {
            return new MyPopupWindow(this);
        }

        public Builder setContext(Context context) {
            this.mContext = context;
            return this;
        }

        /**
         * 设置布局文件
         * @param viewId
         * @return
         */
        public Builder setView(int viewId) {
            this.viewId = viewId;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setFocus(boolean focus) {
            this.focus = focus;
            return this;
        }

        public Builder setOutSideCancel(boolean outSideCancel) {
            this.outSideCancel = outSideCancel;
            return this;
        }

        public Builder setAnimStyle(int animStyle) {
            this.animStyle = animStyle;
            return this;
        }

        public Builder setAlpha(Activity activity, float alpha) {
            this.mActivity = activity;
            this.alpha = alpha;
            return this;
        }
    }
}
