package com.example.moneylist;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MoneyListFragment extends Fragment {
    private RecyclerView mMoneyRecycleView;
    private MoneyAdapter mAdapter;
    private boolean mSubtitleVisible;

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private TextView mOutmoneyField;
    private TextView mInmoneyField;


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        //封装到list里
        View view=inflater.inflate(R.layout.fragment_moneylist,container,false);
        mMoneyRecycleView =(RecyclerView) view.findViewById(R.id.money_recycler_view);
        mMoneyRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mOutmoneyField =(TextView) view.findViewById(R.id.money_out);
        mInmoneyField = (TextView) view.findViewById(R.id.money_Sum);

        //返回账单时也能调取mAdapter
        MoneyLab moneyLab = MoneyLab.get(getActivity());
        List<Amoney> moneys=moneyLab.getMoneys();
        mAdapter = new MoneyAdapter(moneys);
        mMoneyRecycleView.setAdapter(mAdapter);
        //显示money的金额
        if(savedInstanceState!=null){
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        upateUI();
        return view;
    }

    //显示菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_money_list,menu);

        MenuItem subtitleitem = menu.findItem(R.id.show_subtitle);
        if(mSubtitleVisible){
            subtitleitem.setTitle(R.string.hide_subtitle);
        }else{
            subtitleitem.setTitle(R.string.show_subtitle);
        }
    }

    //菜单id的不同选择
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.show_subtitle:
                mSubtitleVisible=!mSubtitleVisible;
                //工具栏的重建
                getActivity().invalidateOptionsMenu();
                //运行方法
                updateSubtitle();
                return true;
                //新建
            case R.id.new_money:
                Amoney amoney = new Amoney();
                MoneyLab.get(getActivity()).addAmoney(amoney);
                Intent intent = MoneyPagerActivity.newIntent(getActivity(),amoney.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //调用菜单
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    public void onResume(){
        super.onResume();
        upateUI();
    }
    private void upateUI(){
        MoneyLab moneyLab = MoneyLab.get(getActivity());
        List<Amoney> moneys=moneyLab.getMoneys();
        if( mAdapter == null) {
            mAdapter = new MoneyAdapter(moneys);
            mMoneyRecycleView.setAdapter(mAdapter);
        }else{
            //调用适配器的set方法
            mAdapter.setmMoneys(moneys);
            mAdapter.notifyDataSetChanged();
        }
        //显示金额总数
        updateSubtitle();
    }


    //导航栏showsubtitle的实现
    private void updateSubtitle(){
        MoneyLab moneyLab = MoneyLab.get(getActivity());
        //隐藏
        if(!mSubtitleVisible){
            mOutmoneyField.setText("****");
            mInmoneyField.setText("****");
        }else{
            float outmoney = 0;
            float inmoney = 0;
            for(int i = 0 ;i<moneyLab.getMoneys().size();i++){
                if((moneyLab.getMoneys().get(i).getTitle()).equals("支出")) {
                    outmoney += Float.parseFloat(moneyLab.getMoneys().get(i).getMoney());
                }
                else if(moneyLab.getMoneys().get(i).getTitle().equals("收入")){
                    inmoney += Float.parseFloat(moneyLab.getMoneys().get(i).getMoney());
                }
            }
            mOutmoneyField.setText(outmoney+"");
            mInmoneyField.setText(inmoney+"");
        }

    }




    private class MoneyHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        private TextView mMethodTextView;
        private TextView mDateTextView;
        private ImageView mImageView;
        private TextView mMoneymoney;
        private Amoney mMoney;
        public MoneyHolder(LayoutInflater inflater ,ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_money,parent,false));

            //按的那一块部分
            itemView.setOnClickListener(this);

            mImageView =(ImageView) itemView.findViewById(R.id.money_methodimage);
            mMethodTextView= (TextView) itemView.findViewById(R.id.money_method);
            mDateTextView=(TextView) itemView.findViewById(R.id.money_date);
            mMoneymoney = (TextView) itemView.findViewById(R.id.money_money);


        }

        //绑定amoney的数据,显示到list上
        public void bind(Amoney amoney){
            mMoney = amoney;
            mMethodTextView.setText(mMoney.getMethod());
            mDateTextView.setText(DateFormat.format("E,MMM dd,yyyy",mMoney.getDate()));

            if(mMoney.getTitle().equals("支出")){
                mMoneymoney.setText(" - "+mMoney.getMoney());
            }else if(mMoney.getTitle().equals("收入")){
                mMoneymoney.setText(mMoney.getMoney());
            }

            if(mMethodTextView.getText().equals("运动")) {
                mImageView.setImageResource(R.drawable.sport);
            }else if(mMethodTextView.getText().equals("餐饮")){
                mImageView.setImageResource(R.drawable.fork);
            }else if(mMethodTextView.getText().equals("账单")){
                mImageView.setImageResource(R.drawable.check);
            }else if(mMethodTextView.getText().equals("娱乐")){
                mImageView.setImageResource(R.drawable.play);
            }else if(mMethodTextView.getText().equals("工资")){
                mImageView.setImageResource(R.drawable.money);
            }else{
                mImageView.setImageResource(R.drawable.play);
            }

        }

        //按后传值
        @Override
        public void onClick(View view) {

            //启动对应id的view,发送id
            Intent intent=MoneyPagerActivity.newIntent(getActivity(),mMoney.getId());
            startActivity(intent);
        }
    }
    private class MoneyAdapter extends RecyclerView.Adapter<MoneyHolder>{
        private List<Amoney> mMoneys;
        public MoneyAdapter(List<Amoney> moneys){
            mMoneys=moneys;
        }
        @Override
        public MoneyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new MoneyHolder(layoutInflater,viewGroup);
        }

        @Override
        public void onBindViewHolder( MoneyHolder moneyHolder, int i) {
            Amoney amoney = mMoneys.get(i);
            moneyHolder.bind(amoney);

        }

        @Override
        public int getItemCount() {
            return mMoneys.size();
        }
        //适配器传入crimes
        public void setmMoneys(List<Amoney> moneys){
            mMoneys = moneys;
        }
    }

}
