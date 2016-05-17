package com.ykf.bishe.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ykf.bishe.carservice.R;
import com.ykf.bishe.model.entity.element.Address;
import com.ykf.bishe.model.entity.element.Desc;
import com.ykf.bishe.model.entity.element.Order;
import com.ykf.bishe.model.entity.element.RepairDate;
import com.ykf.bishe.ui.base.BaseFragment;
import com.ykf.bishe.ui.util.App;
import com.ykf.bishe.ui.util.AppUtil;
import com.ykf.bishe.ui.util.Constant;
import com.ykf.bishe.ui.util.SharePreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by ykf on 16/3/23.
 */
public class ReserveFragment extends BaseFragment {

    Context context = null;

    @Bind(R.id.iv_choose_area)
    ImageView iv_choose_area;
    @Bind(R.id.ll_choose_area)
    LinearLayout ll_choose_area;
    @Bind(R.id.ll_map)
    LinearLayout ll_map;
    @Bind(R.id.tv_address)
    TextView tv_address;
    @Bind(R.id.tv_repair_address)
    TextView tv_repair_address;
    @Bind(R.id.tv_repair_time)
    TextView tv_repair_time;
    @Bind(R.id.iv_photo)
    ImageView iv_photo;
    @Bind(R.id.et_desc)
    TextView et_desc;
    @Bind(R.id.btn_commit)
    Button btn_commit;
    @Bind(R.id.et_name)
    EditText et_name;
    @Bind(R.id.et_phone)
    EditText et_phone;
    @Bind(R.id.rb_hava)
    RadioButton rb_hava;
    @Bind(R.id.rb_no)
    RadioButton rb_no;
    @Bind(R.id.et_price)
    EditText et_price;

    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    private File tempFile;
    /* 图片名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private boolean isSafe;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yuyue;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        context = getContext();
        Bmob.initialize(context, Constant.BMOB_KEY);
        Toast.makeText(context, SharePreferenceManager.getInstance(context).getName(), Toast.LENGTH_SHORT).show();
        if (SharePreferenceManager.getInstance(context).getName() != null) {
            et_name.setText(SharePreferenceManager.getInstance(context).getName());
        }
        if (SharePreferenceManager.getInstance(context).getPhone() != null) {
            et_phone.setText(SharePreferenceManager.getInstance(context).getPhone());
        }
    }


    @OnClick({R.id.iv_choose_area, R.id.ll_choose_area, R.id.ll_map})
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_choose_area:
                intent = new Intent(context, ProvinceListActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_map:
                intent = new Intent(context, MapActivity.class);
                startActivity(intent);
                break;

        }
    }

    @OnClick(R.id.tv_repair_time)
    public void chooseRepairTime() {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(), "datePicker");
    }

    @OnClick(R.id.tv_choose_photo)
    public void choosePhoto() {
        gallery(iv_photo);
    }

    @OnClick(R.id.et_desc)
    public void editDesc() {
        Intent intent = new Intent(context, EditDescActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rb_hava)
    public void haveRb() {
        isSafe = true;
    }

    @OnClick(R.id.rb_no)
    public void noRb() {
        isSafe = false;
    }

    @OnClick(R.id.btn_commit)
    public void commit() {
        if (SharePreferenceManager.getInstance(context).getUsername() != null &&
                !SharePreferenceManager.getInstance(context).getUsername().equals("")) {

            if (TextUtils.isEmpty(et_desc.getText().toString()) ||
                    TextUtils.isEmpty(tv_address.getText().toString()) ||
                    TextUtils.isEmpty(tv_repair_time.getText().toString()) ||
                    TextUtils.isEmpty(tv_repair_address.getText().toString()) ||
                    TextUtils.isEmpty(et_name.getText().toString()) ||
                    TextUtils.isEmpty(et_phone.getText().toString()) ||
                    TextUtils.isEmpty(et_price.getText().toString())) {
                Toast.makeText(context, "信息不全", Toast.LENGTH_SHORT).show();
                return;
            }
            Order order = new Order();
            order.setStatus("0");
            order.setUsername(SharePreferenceManager.getInstance(context).getUsername());
            order.setArea(tv_address.getText().toString());
            order.setAddress(tv_repair_address.getText().toString());
            order.setName(et_name.getText().toString());
            order.setPhone(et_phone.getText().toString());
            if (isSafe) {
                order.setIsSafe("1");
            } else {
                order.setIsSafe("0");
            }
            order.setTime(tv_repair_time.getText().toString());
            order.setPrice(et_price.getText().toString());
            order.setDesc(et_desc.getText().toString());
            order.save(context, new SaveListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(context, "提交成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(context, "请先登陆", Toast.LENGTH_SHORT).show();
        }
    }


    @Subscribe
    public void onEvent(Address address) {
        if (address != null) {
            tv_address.setText(address.getProvince() + "  " + address.getCity());
        }
    }


    @Subscribe
    public void onEvent(String repair) {
        if (repair != null && repair != "") {
            tv_repair_address.setText(repair);
        }
    }

    @Subscribe
    public void onEvent(RepairDate date) {
        tv_repair_time.setText(date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDay());
    }

    @Subscribe
    public void onEvent(Desc d) {
        et_desc.setText(d.getDesc());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    //从相机中获取照片
    public void gallery(View view) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }


    //从相机获取
    public void camera(View view) {
        // 激活相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (AppUtil.hasSdcard()) {
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    PHOTO_FILE_NAME);
            // 从文件中创建uri
            Uri uri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);

    }

    //剪切图片
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
            // 从相机返回的数据
            if (AppUtil.hasSdcard()) {
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(context, "未找到存储卡，无法存储照片！", 0).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                iv_photo.setImageBitmap(bitmap);
            }
            try {
                // 将临时文件删除
                tempFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //时间选择对话框
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            EventBus.getDefault().post(new RepairDate(i, i1, i2));
        }
    }
}
