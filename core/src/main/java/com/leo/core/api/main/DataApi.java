package com.leo.core.api.main;

import android.support.v7.widget.RecyclerView;

import com.leo.core.api.core.ThisApi;
import com.leo.core.iapi.api.ICheckApi;
import com.leo.core.iapi.inter.IDataHelper;
import com.leo.core.iapi.inter.IbooleanAction;
import com.leo.core.iapi.main.IDataApi;
import com.leo.core.util.LogUtil;
import com.leo.core.util.RunUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataApi<T extends DataApi, D> extends ThisApi<T> implements IDataApi<T, D> {

    private List<D> data;
    private List<D> filterData;
    private IDataHelper<D> helper;
    private boolean emptyListenEnable;
    private IbooleanAction emptyAction;
    private ICheckApi<ICheckApi, D> api;
    private RecyclerView.Adapter adapter;

    public DataApi() {
        data = new ArrayList<>();
    }

    public DataApi(List<D> data) {
        this();
        replaceAll(data);
    }

    public T setData(List<D> data) {
        this.data = data;
        return getThis();
    }

    public T setApi(ICheckApi<ICheckApi, D> api) {
        this.api = api;
        return getThis();
    }

    public T setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        return getThis();
    }

    public void setEmptyAction(IbooleanAction action) {
        this.emptyAction = action;
    }

    public void openEmptyListen(){
        this.emptyListenEnable = true;
    }

    public void closeEmptyListen(){
        this.emptyListenEnable = false;
    }

    public IDataHelper<D> getHelper() {
        return helper;
    }

    public T setHelper(IDataHelper<D> helper) {
        this.helper = helper;
        return getThis();
    }

    public T setFilterData(List<D> filterData) {
        this.filterData = filterData;
        return getThis();
    }

    private void initFilterAction() {
        if(adapter != null){
            if(getHelper() == null){
                filterData = null;
                adapter.notifyDataSetChanged();
            } else {
                getHelper().execute(adapter, getThis(), getData());
            }
        }
    }

    @Override
    public T notifyDataSetChanged() {
        initFilterAction();
        return getThis();
    }

    @Override
    public boolean check(D obj) {
        return obj == null || api != null && api.check(obj);
    }

    @Override
    public boolean contains(D obj) {
        return obj != null && getData().contains(obj);
    }

    @Override
    public Integer getType(D obj) {
        return null;
    }

    @Override
    public Integer getType(int position) {
        return null;
    }

    @Override
    public List<D> getData() {
        return data;
    }

    @Override
    public List<D> getFilterData() {
        return filterData == null ? getData() : filterData;
    }

    @Override
    public List<D> copy() {
        if (getData() == null) {
            return null;
        }
        return new ArrayList<>(getData());
    }

    @Override
    public Set<Integer> getTypeData() {
        Set<Integer> set = new HashSet<>();
        for (D obj : getData()) {
            Integer type = getType(obj);
            if (type != null) {
                set.add(type);
            }
        }
        if (set.size() == 0)
            set = null;
        return set;
    }

    @Override
    public Set<Class<D>> getClassData() {
        Set<Class<D>> set = new HashSet<>();
        for (D obj : getData()) {
            if (obj != null) {
                set.add((Class<D>) obj.getClass());
            }
        }
        if (set.size() == 0)
            set = null;
        return set;
    }

    @Override
    public int getCount() {
        int count = getFilterData() == null ? 0 : getFilterData().size();
        if (emptyListenEnable && emptyAction != null) {
            emptyAction.execute(count > 0);
        }
        return count;
    }

    @Override
    public int getTypeCount() {
        return getTypeData() == null ? 0 : getTypeData().size();
    }

    @Override
    public int getClassCount() {
        return getClassData() == null ? 0 : getClassData().size();
    }

    @Override
    public T add(D obj) {
        if (!check(obj)) {
            getData().add(obj);
        }
        return getThis();
    }

    @Override
    public T add(int position, D obj) {
        if (!check(obj) && position <= getCount() && position >= 0) {
            getData().add(position, obj);
        }
        return getThis();
    }

    @Override
    public T addHead(D obj) {
        add(0, obj);
        return getThis();
    }

    @Override
    public T addAll(List<D> data) {
        RunUtil.execute(data, this::add);
        return getThis();
    }

    @Override
    public T addAll(int position, List<D> data) {
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                add(position + i, data.get(i));
            }
        }
        return getThis();
    }

    @Override
    public T addAllHead(List<D> data) {
        if (data != null) {
            for (int i = data.size() - 1; i > -1; i--) {
                addHead(data.get(i));
            }
        }
        return getThis();
    }

    @Override
    public T remove(D obj) {
        if (obj != null)
            getData().remove(obj);
        return getThis();
    }

    @Override
    public T remove(int position) {
        if (position < getCount() && position >= 0) {
            getData().remove(position);
        }
        return getThis();
    }

    @Override
    public T removeLast(int position) {
        remove(getCount() - position - 1);
        return getThis();
    }

    @Override
    public T remove(int type, int position) {
        List<D> data = getData(type);
        if (data != null && position < data.size()) {
            remove(data.get(position));
        }
        return getThis();
    }

    @Override
    public T removeLast(int type, int position) {
        remove(type, getCount() - position - 1);
        return getThis();
    }

    @Override
    public T remove(Class<? extends D> clz, int position) {
        List<D> data = getData(clz);
        if (data != null && position < data.size()) {
            remove(data.get(position));
        }
        return getThis();
    }

    @Override
    public T removeLast(Class<? extends D> clz, int position) {
        remove(clz, getCount() - position - 1);
        return getThis();
    }

    @Override
    public T removeAll() {
        getData().clear();
        return getThis();
    }

    @Override
    public T removeAll(List<D> data) {
        if (data != null) {
            for (D obj : data) {
                remove(obj);
            }
        }
        return getThis();
    }

    @Override
    public Integer removeAll(int start, int end) {
        if (start >= 0 && start < getCount() && end >= 0 && end < getCount()) {
            int x, y;
            if (start < end) {
                x = start;
                y = end;
            } else {
                x = end;
                y = start;
            }
            for (int i = y; i >= x; i--) {
                remove(getData().get(i));
            }
            return x;
        }
        return null;
    }

    @Override
    public T removeAll(Class<? extends D> clz) {
        if (clz != null) {
            for (D obj : getData()) {
                if (obj != null && clz.equals(obj.getClass())) {
                    remove(obj);
                }
            }
        }
        return getThis();
    }

    @Override
    public T removeAll(int type) {
        for (D obj : getData()) {
            if (getType(obj) == type) {
                remove(obj);
            }
        }
        return getThis();
    }

    @Override
    public T replace(D old, D obj) {
        if (old != null && obj != null) {
            replace(getData().indexOf(old), obj);
        }
        return getThis();
    }

    @Override
    public T replace(int position, D obj) {
        if (obj != null && position >= 0) {
            if (position < getCount()) {
                remove(position);
                add(position, obj);
            } else if (position == getCount()) {
                add(position, obj);
            }
        }
        return getThis();
    }

    @Override
    public T replaceLast(int position, D obj) {
        if (obj != null) {
            replace(getCount() - position - 1, obj);
        }
        return getThis();
    }

    @Override
    public T replace(int position, List<D> data) {
        if (data != null && position < getCount() && position >= 0) {
            remove(position);
            addAll(position, data);
        }
        return getThis();
    }

    @Override
    public T replace(int start, int end, D obj) {
        if (obj != null) {
            List<D> data = copy();
            Integer x = removeAll(start, end);
            if (x != null) {
                add(x, obj);
            } else {
                this.data = data;
            }
        }
        return getThis();
    }

    @Override
    public T replace(int start, int end, List<D> data) {
        if (data != null) {
            List<D> d = copy();
            Integer x = removeAll(start, end);
            if (x != null) {
                addAll(x, data);
            } else {
                this.data = d;
            }
        }
        return getThis();
    }

    @Override
    public T replaceAll(List<D> data) {
        if (data != null) {
            getData().clear();
            addAll(data);
        }
        return getThis();
    }

    @Override
    public int getPosition(D obj) {
        return getData().indexOf(obj);
    }

    @Override
    public int getPosition(int type) {
        for (int i = 0; i < getCount(); i++) {
            if (getType(getItem(i)) == type) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getPosition(int type, int position) {
        int count = 0;
        for (int i = 0; i < getCount(); i++) {
            if (getType(getItem(i)) == type) {
                count++;
                if (count == position) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int getPositionLast(int type) {
        for (int i = getCount() - 1; i > -1; i--) {
            if (getType(getItem(i)) == type) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getPosition(Class<? extends D> clz) {
        if (clz == null)
            return -1;
        for (int i = 0; i < getCount(); i++) {
            D obj = getData().get(i);
            if (obj != null && clz.equals(obj.getClass())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getPosition(Class<? extends D> clz, int position) {
        if (clz == null)
            return -1;
        int count = 0;
        for (int i = 0; i < getCount(); i++) {
            D obj = getData().get(i);
            if (obj != null && clz.equals(obj.getClass())) {
                count++;
                if (count == position) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int getPositionLast(Class<? extends D> clz) {
        if (clz == null)
            return -1;
        for (int i = getCount() - 1; i > -1; i--) {
            D obj = getData().get(i);
            if (obj != null && clz.equals(obj.getClass())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public D getItem(int position) {
        if (position >= 0 && position < getCount())
            return getFilterData().get(position);
        return null;
    }

    @Override
    public D getLastItem(int position) {
        return getItem(getCount() - position - 1);
    }

    @Override
    public D getItem(int type, int position) {
        List<D> data = getData(type);
        if (data != null && position >= 0 && position < data.size())
            data.get(position);
        return null;
    }

    @Override
    public D getLastItem(int type, int position) {
        List<D> data = getData(type);
        int count = data.size();
        if (data != null && position >= 0 && position < count)
            data.get(count - position - 1);
        return null;
    }

    @Override
    public D getItem(Class<? extends D> clz, int position) {
        List<D> data = getData(clz);
        if (data != null && position >= 0 && position < data.size())
            data.get(position);
        return null;
    }

    @Override
    public D getLastItem(Class<? extends D> clz, int position) {
        List<D> data = getData(clz);
        int count = data.size();
        if (data != null && position >= 0 && position < count)
            data.get(count - position - 1);
        return null;
    }

    @Override
    public List<D> getData(int type) {
        List<D> data = new ArrayList<>();
        for (D obj : getData()) {
            if (type == getType(obj)) {
                data.add(obj);
            }
        }
        return data;
    }

    @Override
    public List<D> getData(Class<? extends D> clz) {
        if (clz == null) {
            return null;
        }
        List<D> data = new ArrayList<>();
        for (D obj : getData()) {
            if (obj != null && clz.equals(obj.getClass())) {
                data.add(obj);
            }
        }
        return data;
    }

    @Override
    public List<D> getData(int start, int end) {
        if (start >= 0 && start < getCount() && end >= 0 && end < getCount()) {
            List<D> data = new ArrayList<>();
            int x, y;
            if (start < end) {
                x = start;
                y = end;
            } else {
                x = end;
                y = start;
            }
            for (int i = y; i >= x; i--) {
                data.add(getData().get(i));
            }
            return data;
        }
        return null;
    }

}
