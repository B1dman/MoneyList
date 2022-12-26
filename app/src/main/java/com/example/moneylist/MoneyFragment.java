package com.example.moneylist;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.service.quicksettings.Tile;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MoneyFragment extends Fragment {
    private Amoney aMoney;
    private EditText mTitleField;
    private EditText mMethodField;
    private EditText mMoneyField;
    private EditText mNoteField;
    private Button mDateButton;

    private Button mReportButton;
    private Button mContanterButton;

    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private File mPhotoFile;

    private static final String ARG_MONEY_ID = "money_id";
    private static final String DIALOG_DATE="DialogDate";
    private static  final int REQUEST_DATE = 0;
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_PHOTO = 2;



    public static Fragment newInstance(UUID moneyId) {
        Bundle args = new Bundle();
        //打包数据 进行获取数值
        args.putSerializable(ARG_MONEY_ID, moneyId);
        MoneyFragment fragment = new MoneyFragment();
        //传入fragment
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //接收数据
        UUID moneyId = (UUID) getArguments().getSerializable(ARG_MONEY_ID);

        aMoney = MoneyLab.get(getActivity()).getAmoney(moneyId);

        mPhotoFile = MoneyLab.get(getActivity()).getPhotoFile(aMoney);
        //允许调用菜单
        setHasOptionsMenu(true);
    }

    //更新aMoney
    @Override
    public void onPause() {
        super.onPause();
        MoneyLab.get(getActivity()).updateMoney(aMoney);
    }

    //view的初始化
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_money, container, false);
        //mTItle
        mTitleField = (EditText) v.findViewById(R.id.money_title);
        mTitleField.setText(aMoney.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //假如改变数值，在数据库中也同样做出改变
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                aMoney.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //用处的实例化
        mMethodField = (EditText) v.findViewById(R.id.money_method);
        mMethodField.setText(aMoney.getMethod());
        mMethodField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                aMoney.setMethod(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //金额的实例化
        mMoneyField = (EditText) v.findViewById(R.id.money_money);
        mMoneyField.setText(aMoney.getMoney());
        mMoneyField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                aMoney.setMoney( charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //备注的实例化
        mNoteField = (EditText) v.findViewById(R.id.money_notes);
        mNoteField.setText(aMoney.getNotes());
        mNoteField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                aMoney.setNotes( charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //时间的实例化
        //button 的实例化
        mDateButton =(Button) v.findViewById(R.id.money_date);
        mDateButton.setText(aMoney.getDate().toString());
//        mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager=getFragmentManager();
//                DatePickerFragment dialog=new DatePickerFragment();
                //初始化DatePickerFragment
                DatePickerFragment dialog=DatePickerFragment.newInstance(aMoney.getDate());
                //建立连接datepickerfragment目标指向MoneyFragment
                dialog.setTargetFragment(MoneyFragment.this,REQUEST_DATE);
                dialog.show(manager,DIALOG_DATE);
            }
        });

        //report按钮
        mReportButton = (Button) v.findViewById(R.id.money_report);
        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送短信
                Intent i  = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,getMoneyReport());
                i.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.money_subject));
                i = Intent.createChooser(i,getString(R.string.send_report));
                startActivity(i);
            }
        });
        //选择联系人
        final Intent pickContact = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
        mContanterButton = (Button) v.findViewById(R.id.money_containner);
        mContanterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(pickContact,REQUEST_CONTACT);
            }
        });
        if(aMoney.getmContanter()!=null){
            mContanterButton.setText(aMoney.getmContanter());
        }

        //相机按钮
        mPhotoButton = (ImageButton) v.findViewById(R.id.money_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),"com.example.moneylist.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                List<ResolveInfo> cameraActivities = getActivity().getPackageManager()
                        .queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);
                for(ResolveInfo activity : cameraActivities){
                    getActivity().grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView) v.findViewById(R.id.money_photo);
        updatePhotoView();
        return v;
    }

    //封装消息
    private String getMoneyReport(){
        //类型
        String TitleString = null;
        TitleString  = aMoney.getTitle();
        //金额
        String money = aMoney.getMoney();

        String method = aMoney.getMethod();

        //时间
        String dataFormat = "EEE,MMM dd";
        String dateString = DateFormat.format(dataFormat, aMoney.getDate()).toString();
        //联系人
        String contanter = aMoney.getmContanter();

        String report = getString (R.string.report_format,dateString,TitleString,money,
        method,contanter);
        return report;
    }



    //初始化按钮和money的信息（联系人，时间）
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            aMoney.setDate(date);
            mDateButton.setText(aMoney.getDate().toString());
        } else if (requestCode == REQUEST_CONTACT && data != null) {
            Uri contactUri = data.getData();
            String[] queryFields = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME
            };
            //指针搜索联系人
            Cursor c = getActivity().getContentResolver()
                    .query(contactUri, queryFields, null, null, null);
                try {
                    if (c.getCount() == 0) {
                        return;
                    }
                    c.moveToFirst();
                    String contanter = c.getString(0);
                    aMoney.setmContanter(contanter);
                    mContanterButton.setText(contanter);
                } finally {
                    c.close();
                }
            }else if(requestCode==REQUEST_PHOTO){
            Uri uri = FileProvider.getUriForFile(getActivity()
                    ,"com.example.moneylist.fileprovider", mPhotoFile);
            getActivity().revokeUriPermission(uri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
            }
        }


    //删除导航栏操作
    //菜单创建
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_money,menu);
    }
    //菜单id的不同选择
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.del_money:
                Amoney amoney = aMoney;
                MoneyLab.get(getActivity()).removeAmoney(amoney);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updatePhotoView() {
        if (mPhotoFile==null || !mPhotoFile.exists()){
            mPhotoView.setImageDrawable(null);
        }else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(),getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }

}

