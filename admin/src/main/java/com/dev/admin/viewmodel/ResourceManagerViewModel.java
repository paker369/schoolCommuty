package com.dev.admin.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.dev.common.base.BaseViewModel;
import com.dev.common.database.dynamic.Dynamic;

import java.util.List;

import static com.dev.common.database.DaoProvider.dynamicDao;

/**
 * @author long.guo
 * @since 2/25/21
 */
public class ResourceManagerViewModel extends BaseViewModel {
    public MutableLiveData<List<Dynamic>> dynamicList = new MutableLiveData<>();

    public void loadDynamic() {
        List<Dynamic> list = dynamicDao().findAll();
        dynamicList.setValue(list);
    }
}
