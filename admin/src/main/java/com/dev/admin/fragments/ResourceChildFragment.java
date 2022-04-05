package com.dev.admin.fragments;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.dev.admin.R;
import com.dev.admin.adapter.SourceManagerAdapter;
import com.dev.admin.databinding.FragmentResourceChildBinding;
import com.dev.admin.viewmodel.ResourceManagerViewModel;
import com.dev.common.adapter.OnItemClickListener;
import com.dev.common.base.BaseFragment;
import com.dev.common.database.dynamic.Dynamic;

/**
 * @author long.guo
 * @since 2/25/21
 */
public class ResourceChildFragment extends BaseFragment<ResourceManagerViewModel> {
    private FragmentResourceChildBinding binding;
    private int position = 0;
    private final SourceManagerAdapter adapter = new SourceManagerAdapter();

    public static ResourceChildFragment newInstance(int pos) {
        ResourceChildFragment f = new ResourceChildFragment();
        f.position = pos;
        return f;
    }

    @Override
    protected View initBinding() {
        binding = FragmentResourceChildBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void setupRecyclerView() {
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        vm.loadDynamic();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setupViews() {
        if (position == 1) {
            binding.text.setText("点击推送资源");
        }

        adapter.onItemClickListener = (view, position) -> {
            Dynamic dynamic = vm.dynamicList.getValue().get(position);
            if (this.position == 0) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, dynamic.title);
                // 指定发送内容的类型
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, dynamic.content));
            } else {
                notify1(dynamic);
            }
        };
    }

    @Override
    protected void initObserver() {
        vm.dynamicList.observe(this, adapter::setData);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void notify1(Dynamic dynamic) {
        NotificationManager manager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "app1";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "app1", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(getActivity(), channelId)
                .setContentTitle(dynamic.title)
                .setContentText(dynamic.content)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .build();
        manager.notify(1, notification);
    }
}
