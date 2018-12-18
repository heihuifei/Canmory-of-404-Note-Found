package can.main_delete;

import can.aboutsqlite.DBManager;
import can.aboutsqlite.Memo;
import can.memorycan.R;
import can.memorycan.memo_add.list_View.Group;
import can.memorycan.memo_add.list_View.MyBaseExpandableListAdapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jay on 2015/9/25 0025.
 */
public class MyBaseExpandableListAdapter_new extends BaseExpandableListAdapter {

    private ArrayList<Group_new> gData;
    private ArrayList<ArrayList<Memo>> iData;
    private Context mContext;
    private static HashMap<Integer,Boolean> isSelected0;
    private static HashMap<Integer,Boolean> isSelected1;
    private static HashMap<Integer,Boolean> isSelected2;
    private List<Integer>list;

    public MyBaseExpandableListAdapter_new(ArrayList<Group_new> gData, ArrayList<ArrayList<Memo>> iData, Context mContext) {
        this.gData = gData;
        this.iData = iData;
        this.mContext = mContext;
        isSelected0 = new HashMap<Integer, Boolean>();
        isSelected1 = new HashMap<Integer, Boolean>();
        isSelected2 = new HashMap<Integer, Boolean>();
        list = new ArrayList<>();
        initDate();
    }

    private void initDate(){
        for(int i=0;i<iData.get(0).size();i++)
        {
            getIsSelected0().put(i,false);
        }
        for(int i=0;i<iData.get(1).size();i++)
        {
            getIsSelected1().put(i,false);
        }
        for(int i=0;i<iData.get(2).size();i++)
        {
            getIsSelected2().put(i,false);
        }
    }

    @Override
    public int getGroupCount() {
        return gData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return iData.get(groupPosition).size();
    }

    @Override
    public List<Integer> getGroup(int groupPosition) {
        return list;
    }

    @Override
    public Memo getChild(int groupPosition, int childPosition) {
        return iData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public static HashMap<Integer,Boolean> getIsSelected0() {
        return isSelected0;
    }
    public static HashMap<Integer,Boolean> getIsSelected1() {
        return isSelected1;
    }
    public static HashMap<Integer,Boolean> getIsSelected2() {
        return isSelected2;
    }

    public static void setIsSelected0(HashMap<Integer,Boolean> isSelected0) {
        MyBaseExpandableListAdapter_new.isSelected0 = isSelected0;
    }
    public static void setIsSelected1(HashMap<Integer,Boolean> isSelected1) {
        MyBaseExpandableListAdapter_new.isSelected1 = isSelected1;
    }
    public static void setIsSelected2(HashMap<Integer,Boolean> isSelected2) {
        MyBaseExpandableListAdapter_new.isSelected2 = isSelected2;
    }

//    public void change_gData(ArrayList<Group_new> group)
//    {
//        gData = group;
//        notifyDataSetChanged();
//    }

    public void change_iData(ArrayList<ArrayList<Memo>> item)
    {
        iData = item;
        notifyDataSetChanged();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ViewHolderGroup groupHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_exlist_group_new, parent, false);
            groupHolder = new ViewHolderGroup();
            groupHolder.tv_group_name = (TextView) convertView.findViewById(R.id.tv_group_name_new);
            convertView.setTag(groupHolder);
        }else{
            groupHolder = (ViewHolderGroup) convertView.getTag();
        }

        groupHolder.tv_group_name.setText(gData.get(groupPosition).get_Group_name());
        return convertView;
    }

    //取得显示给定分组给定子位置的数据用的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final int index1 = groupPosition;
        final int index2 = childPosition;
        ViewHolderItem itemHolder;
        convertView = null;
        if(convertView == null){
//            Log.e("groupposition",String.valueOf(convertView));
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_exlist_item_new, parent, false);
            itemHolder = new ViewHolderItem();
            itemHolder.ckb_name = (CheckBox) convertView.findViewById(R.id.ckb_name_new);
            itemHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name_new);
            if(groupPosition == 2) {
                Log.e("grouppositiongroupposit",String.valueOf(groupPosition));
                itemHolder.ckb_name.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
            convertView.setTag(itemHolder);
            itemHolder.ckb_name.setTag(childPosition);
//            notifyDataSetChanged();
        }else{
            itemHolder = (ViewHolderItem) convertView.getTag();
        }
//        itemHolder.ckb_name.setOnCheckedChangeListener(null);
//        itemHolder.ckb_name.setChecked(iData.get(groupPosition).get(childPosition).getCheckStatus());
//        if(groupPosition==0)
//        {
//            itemHolder.ckb_name.setChecked(getIsSelected0().get(childPosition));
//        }
//        else if(groupPosition==1)
//        {
//            itemHolder.ckb_name.setChecked(getIsSelected1().get(childPosition));
//        }
//        else if(groupPosition==2)
//        {
//            itemHolder.ckb_name.setChecked(getIsSelected2().get(childPosition));
//        }
        itemHolder.ckb_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Memo memo=iData.get(index1).get(index2);
                    System.out.println(memo.getMemo_id());
                    iData.get(2).add(memo);
                    System.out.println(memo.getMemo_title());
                    iData.get(index1).remove(index2);
                    System.out.println("将该子项从该列表移除");
                    list.add(memo.getMemo_id());
                    notifyDataSetChanged();
                }
            }
        });
        itemHolder.ckb_name.setText(iData.get(index1).get(index2).getMemo_title());
        Log.e("title_strings",iData.get(index1).get(index2).getMemo_title());
        String tmp=iData.get(index1).get(index2).getmemo_dtimestring();
        itemHolder.tv_name.setText(tmp);
        return convertView;
    }
    public void Chan_mgr(DBManager mgr)
    {

    }

//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
//        int index = (int)buttonView.getTag();
//        if(isChecked)
//            iData.get(index).get(index).setCheckStatus(true);
//        else
//            iData.get(index).get(index).setCheckStatus(false);
//    }

    //设置子列表是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class ViewHolderGroup{
        private TextView tv_group_name;
    }

    private static class ViewHolderItem{
        private TextView tv_name;
        private CheckBox ckb_name;
    }

}
