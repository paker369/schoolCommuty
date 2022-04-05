package com.dev.user.base;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.common.base.BaseActivity;
import com.dev.common.entry.CityBean;
import com.dev.common.utils.CameraAndAlbumUtil;
import com.dev.common.utils.CityDatabaseManager;
import com.dev.common.utils.FileUtil;
import com.dev.common.view.CustomDialog;
import com.dev.user.R;
import com.dev.user.UserInfoViewBinder;
import com.dev.user.databinding.DialogChooseDoubleBinding;
import com.dev.user.databinding.DialogChoosePictureBinding;
import com.dev.user.databinding.DialogChooseSingleBinding;
import com.dev.user.databinding.DialogChooseThreeBinding;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

import static com.dev.common.utils.TimeUtils.getDaysByYearMonth;
import static com.dev.common.utils.data.Collections.mutableListOf;
import static com.dev.common.utils.data.Collections.take;
import static com.dev.common.view.CustomDialog.matchParent;

/**
 * @author long.guo
 * @since 1/25/21
 */
abstract public class UserSettingBaseActivity<T extends UserSettingViewModel> extends BaseActivity<T> {

    public static final String TEMP_FILE_NAME = "user_head_pic.png";
    @Nullable
    protected Uri imgUri = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        Uri uri = data.getData();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 相册
                case CameraAndAlbumUtil.OPEN_ALBUM:
                    if (uri != null) {
                        openSysCrop(uri);
                    }
                    break;
                //相机
                case CameraAndAlbumUtil.OPEN_CAMERA:
                    if (uri != null) openSysCrop(uri);
                    break;
                // 裁剪
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    Uri resultUri = result.getUri();
                    onHeadChange(new File(FileUtil.getRealPathFromURI(resultUri)).getAbsolutePath());
                    break;
            }
        }
    }

    private void openSysCrop(Uri uri) {
        CropImage.activity(uri)
                .setCropMenuCropButtonTitle(toResString(R.string.image_crop))
                .setAspectRatio(1, 1)
                .setRequestedSize(100, 100)
                .start(this);
    }

    /**
     * 展示选择用户头像弹框
     */
    protected void showChooseUserHeadDialog() {
        CustomDialog customDialog = new CustomDialog.Build(R.layout.dialog_choose_picture)
                .gravity(Gravity.BOTTOM)
                .width(matchParent)
                .style(R.style.dialog_show_navg)
                .updateUIListener((dialog, view) -> {
                    DialogChoosePictureBinding binding = DialogChoosePictureBinding.bind(view.findViewById(R.id.chooseRoot));
                    binding.tvTakePhoto.setOnClickListener(v -> {
                        imgUri = vm.openCamera(UserSettingBaseActivity.this, TEMP_FILE_NAME);
                        dialog.dismiss();
                    });
                    binding.tvOpenAlbum.setOnClickListener(v -> {
                        vm.openAlbum(UserSettingBaseActivity.this);
                        dialog.dismiss();
                    });
                    binding.tvCancle.setOnClickListener(v -> {
                        dialog.dismiss();
                    });
                }).create(this);
        customDialog.show();
    }

    /**
     * 性别选择
     */
    protected void showChooseSexDialog() {
        List<String> data = mutableListOf(toResString(R.string.male), toResString(R.string.female));
        MultiTypeAdapter adapter = new MultiTypeAdapter();
        Items item = new Items();
        item.addAll(data);
        CustomDialog customDialog = new CustomDialog.Build(R.layout.dialog_choose_single)
                .width(matchParent)
                .gravity(Gravity.BOTTOM)
                .style(R.style.dialog_show_navg)
                .updateUIListener((dialog, view) -> {
                    adapter.register(String.class, new UserInfoViewBinder());
                    adapter.setItems(item);
                    DialogChooseSingleBinding binding = DialogChooseSingleBinding.bind(view.findViewById(R.id.chooseRoot));
                    binding.rcy.setAdapter(adapter);
                    LinearLayoutManager lm = new LinearLayoutManager(UserSettingBaseActivity.this);
                    binding.rcy.setLayoutManager(lm);
                    LinearSnapHelper snap = new LinearSnapHelper();
                    snap.attachToRecyclerView(binding.rcy);
                    binding.tvOk.setOnClickListener(v -> {
                        View posView = snap.findSnapView(lm);
                        if (posView != null) {
                            int position = lm.getPosition(posView);
                            onGenderChange(position, data.get(position));
                            dialog.dismiss();
                        }
                    });
                }).create(this);
        customDialog.show();
        adapter.notifyDataSetChanged();
    }

    /**
     * 生日选择
     */
    protected void showChooseBirthDialog() {
        //年
        List<String> data1 = new ArrayList<>();
        int current = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = current; i > 0; i--) {
            data1.add(String.valueOf(i));
        }
        //月
        List<String> data2 = mutableListOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");

        //当前选中年份
        final int[] year = {Integer.parseInt(data1.get(0))};
        //当前选中月份
        final int[] month = {Integer.parseInt(data2.get(0)) - 1};

        //今年月数裁剪
        final List<String>[] monthList = new List[]{new ArrayList<>()};
        if (year[0] == Calendar.getInstance().get(Calendar.YEAR)) {
            monthList[0] = take(data2, Calendar.getInstance().get(Calendar.MONTH) + 1);
        }

        //日
        List<String> data3 = new ArrayList<>();
        int day = getDaysByYearMonth(year[0], month[0]);
        if (year[0] == Calendar.getInstance().get(Calendar.YEAR) && month[0] == Calendar.getInstance().get(Calendar.MONTH)) {
            day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }
        for (int i = 0; i < day; i++) {
            if (i < 9) {
                data3.add("0" + (i + 1));
            } else {
                data3.add((i + 1) + "");
            }
        }

        MultiTypeAdapter adapter1 = new MultiTypeAdapter();
        Items item1 = new Items();
        item1.addAll(data1);

        MultiTypeAdapter adapter2 = new MultiTypeAdapter();
        Items item2 = new Items();
        item2.addAll(monthList[0]);

        MultiTypeAdapter adapter3 = new MultiTypeAdapter();
        Items item3 = new Items();
        item3.addAll(data3);

        CustomDialog customDialog = new CustomDialog.Build(R.layout.dialog_choose_three)
                .style(R.style.dialog_show_navg)
                .width(matchParent)
                .gravity(Gravity.BOTTOM)
                .updateUIListener((dialog1, view) -> {
                    DialogChooseThreeBinding binding = DialogChooseThreeBinding.bind(view.findViewById(R.id.chooseRoot));
                    //日
                    adapter3.register(String.class, new UserInfoViewBinder());
                    adapter3.setItems(item3);
                    binding.rcyThree3.setAdapter(adapter3);
                    LinearLayoutManager lm3 = new LinearLayoutManager(UserSettingBaseActivity.this);

                    binding.rcyThree3.setLayoutManager(lm3);
                    LinearSnapHelper snap3 = new LinearSnapHelper();
                    snap3.attachToRecyclerView(binding.rcyThree3);

                    //年
                    adapter1.register(String.class, new UserInfoViewBinder());
                    adapter1.setItems(item1);
                    binding.rcyThree1.setAdapter(adapter1);
                    LinearLayoutManager lm1 = new LinearLayoutManager(UserSettingBaseActivity.this);
                    binding.rcyThree1.setLayoutManager(lm1);
                    LinearSnapHelper snap1 = new LinearSnapHelper();
                    snap1.attachToRecyclerView(binding.rcyThree1);
                    binding.rcyThree1.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                View posView1 = snap1.findSnapView(lm1);
                                //更新每月天数
                                if (posView1 != null) {
                                    year[0] = Integer.parseInt(data1.get(lm1.getPosition(posView1)));
                                    data3.clear();

                                    //计算本月天数总数
                                    int maxDay = getDaysByYearMonth(year[0], month[0]);
                                    if (year[0] == Calendar.getInstance().get(Calendar.YEAR) && month[0] == Calendar.getInstance().get(Calendar.MONTH)) {
                                        maxDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                                    }
                                    for (int it = 0; it < maxDay; it++) {
                                        if (it < 9) {
                                            data3.add("0" + (it + 1));
                                        } else {
                                            data3.add((it + 1) + "");
                                        }
                                    }

                                    item3.clear();
                                    item3.addAll(data3);
                                    adapter3.notifyDataSetChanged();

                                    //计算当前月份总数
                                    if (year[0] == Calendar.getInstance().get(Calendar.YEAR)) {
                                        monthList[0] = take(data2, Calendar.getInstance().get(Calendar.MONTH) + 1);
                                    } else {
                                        monthList[0] = data2;
                                    }
                                    item2.clear();
                                    item2.addAll(monthList[0]);
                                    adapter2.notifyDataSetChanged();
                                }
                            }
                        }
                    });

                    //月
                    adapter2.register(String.class, new UserInfoViewBinder());
                    adapter2.setItems(item2);
                    binding.rcyThree2.setAdapter(adapter2);
                    LinearLayoutManager lm2 = new LinearLayoutManager(UserSettingBaseActivity.this, RecyclerView.VERTICAL, false);
                    binding.rcyThree2.setLayoutManager(lm2);
                    LinearSnapHelper snap2 = new LinearSnapHelper();
                    snap2.attachToRecyclerView(binding.rcyThree2);
                    binding.rcyThree2.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                //更新每月天数
                                View posView2 = snap2.findSnapView(lm2);
                                if (posView2 != null) {
                                    month[0] = Integer.parseInt(data2.get(lm2.getPosition(posView2))) - 1;
                                    data3.clear();
                                    //计算天数总数
                                    int maxDay = getDaysByYearMonth(year[0], month[0]);
                                    if (year[0] == Calendar.getInstance().get(Calendar.YEAR) && month[0] == Calendar.getInstance().get(Calendar.MONTH)) {
                                        maxDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                                    }
                                    for (int it = 0; it < maxDay; it++) {
                                        if (it < 9) {
                                            data3.add("0" + (it + 1));
                                        } else {
                                            data3.add((it + 1) + "");
                                        }
                                    }
                                    item3.clear();
                                    item3.addAll(data3);
                                    adapter3.notifyDataSetChanged();
                                }
                            }
                        }
                    });

                    //完成
                    binding.tvThreeOk.setOnClickListener(v -> {
                        //当前日
                        int selectedDay = 1;
                        View posView3 = snap3.findSnapView(lm3);
                        if (posView3 != null) {
                            selectedDay = Integer.parseInt(data3.get(lm3.getPosition(posView3)));
                        }
                        String perfix = "";
                        if (month[0] < 9) {
                            perfix = "0";
                        }
                        //数据提交
                        onBirthdayChanged(year[0], month[0] + 1, selectedDay);
                        dialog1.dismiss();
                    });
                }).create(this);
        customDialog.show();
        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();
    }

    /**
     * 地址选择
     */
    protected void showChooseAddressDialog() {
        //省
        List<CityBean> data1 = getAddressData("-1");

        if (data1 == null || data1.isEmpty()) return;
        MultiTypeAdapter adapter1 = new MultiTypeAdapter();
        Items item1 = new Items();
        for (CityBean cityBean : data1) {
            item1.add(cityBean.getCode());
        }

        //获取首个省份的城市数据
        List<CityBean> data2 = new ArrayList<>(getAddressData(data1.get(0).getName()));
        MultiTypeAdapter adapter2 = new MultiTypeAdapter();
        Items item2 = new Items();
        for (CityBean cityBean : data2) {
            item2.add(cityBean.getCode());
        }
        //当前选中的省,默认取第一个
        final CityBean[] province = {data1.get(0)};

        CustomDialog customDialog = new CustomDialog.Build(R.layout.dialog_choose_double)
                .style(R.style.dialog_show_navg)
                .width(matchParent)
                .gravity(Gravity.BOTTOM)
                .updateUIListener(((dialog, view) -> {
                    DialogChooseDoubleBinding binding = DialogChooseDoubleBinding.bind(view.findViewById(R.id.chooseRoot));
                    //省
                    adapter1.register(String.class, new UserInfoViewBinder());
                    adapter1.setItems(item1);
                    binding.rcyTwo1.setAdapter(adapter1);
                    LinearLayoutManager lm1 = new LinearLayoutManager(UserSettingBaseActivity.this);
                    binding.rcyTwo1.setLayoutManager(lm1);
                    LinearSnapHelper snap1 = new LinearSnapHelper();
                    snap1.attachToRecyclerView(binding.rcyTwo1);
                    binding.rcyTwo1.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                View posView1 = snap1.findSnapView(lm1);
                                if (posView1 != null) {
                                    province[0] = data1.get(lm1.getPosition(posView1));

                                    if (province[0] != null) {
                                        //获取某个省份的所有市信息
                                        List<CityBean> cityData = getAddressData(province[0].getName());
                                        if (cityData != null) {
                                            data2.clear();
                                            item2.clear();

                                            data2.addAll(cityData);
                                            for (CityBean cityBean : data2) {
                                                item2.add(cityBean.getCode());
                                            }
                                            adapter2.notifyDataSetChanged();
                                        }
                                    }
                                }
                            }
                        }
                    });

                    //市
                    adapter2.register(String.class, new UserInfoViewBinder());
                    adapter2.setItems(item2);
                    binding.rcyTwo2.setAdapter(adapter2);
                    LinearLayoutManager lm2 = new LinearLayoutManager(UserSettingBaseActivity.this, RecyclerView.VERTICAL, false);
                    binding.rcyTwo2.setLayoutManager(lm2);
                    LinearSnapHelper snap2 = new LinearSnapHelper();
                    snap2.attachToRecyclerView(binding.rcyTwo2);

                    //完成
                    binding.tvTwoOk.setOnClickListener(v -> {
                        //当前市名称
                        String cityName = null;
                        View posView2 = snap2.findSnapView(lm2);
                        if (posView2 != null) {
                            cityName = data2.get(lm2.getPosition(posView2)).getCode();
                        }
                        //当前省名称
                        String provinceName = province[0].getCode();
                        //删除空格
                        String p = provinceName.substring(0, provinceName.length() - 1);
                        String c = cityName.substring(0, cityName.length() - 1);
                        //修改地址
                        onAddressChange(p, c);
                        dialog.dismiss();
                    });
                })).create(this);
        customDialog.show();
        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
    }

    protected void onHeadChange(String filePath) {

    }

    protected void onAddressChange(String province, String city) {
        Log.i("guolong", String.format("province:%s,city:%s", province, city));
    }

    /**
     * 获取地址信息
     */
    private List<CityBean> getAddressData(String code) {
        if (code.equals("-1")) {
            return CityDatabaseManager.queryProvince();
        } else {
            return CityDatabaseManager.queryCity(code);
        }
    }

    protected void onBirthdayChanged(int year, int month, int day) {
        Log.i("guolong", String.format("year:%s,month:%s,day:%s", year, month, day));
    }

    protected void onGenderChange(int position, String name) {

    }
}
