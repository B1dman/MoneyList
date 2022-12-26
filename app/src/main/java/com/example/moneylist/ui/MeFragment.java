package com.example.moneylist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.moneylist.Amoney;
import com.example.moneylist.LoginActivity;
import com.example.moneylist.MoneyLab;
import com.example.moneylist.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment {

    private TextView mOutmoneyField;
    private TextView mInmoneyField;
    private TextView mOutmoneyField1;
    private TextView mInmoneyField1;
    private Button logout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_me, container, false);
        float a = 0;
        float b =0;
        mInmoneyField = (TextView) v.findViewById(R.id.money_in);
        mOutmoneyField = (TextView) v.findViewById(R.id.money_out);
        MoneyLab moneyLab = MoneyLab.get(getActivity());
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

       //按钮
       logout = v.findViewById(R.id.logout);
       logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(getActivity(), LoginActivity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(intent);
            }
       });

        return v;
    }

}