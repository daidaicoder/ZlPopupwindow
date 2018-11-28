package cn.itcss.zlpopupwindow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.itcss.zlpop.MyPopupWindow;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button avatarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        avatarBtn = findViewById(R.id.avatarBtn);
        avatarBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.avatarBtn) {
            final MyPopupWindow myPopupWindow = new MyPopupWindow.Builder()
                    .setContext(this)
                    .setView(R.layout.avatar_popup)
                    .setFocus(true)
                    .setOutSideCancel(true)
                    .setAnimStyle(R.style.popup_anim_style)
                    .setAlpha(MainActivity.this, 0.7f)
                    .builder()
                    .showAtLocation(R.layout.activity_main, Gravity.BOTTOM, 0, 0);

            myPopupWindow.setOnClickListener(R.id.pop_cancel, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_SHORT).show();
                    myPopupWindow.dismiss();
                }
            });

            myPopupWindow.setOnClickListener(R.id.pop_pic, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "从相册选择", Toast.LENGTH_SHORT).show();
                }
            });

            myPopupWindow.setOnClickListener(R.id.pop_camera, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "拍照了", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
