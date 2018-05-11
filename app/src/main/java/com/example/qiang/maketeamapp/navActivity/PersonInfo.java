package com.example.qiang.maketeamapp.navActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiang.maketeamapp.MainActivity;
import com.example.qiang.maketeamapp.R;
import com.example.qiang.maketeamapp.SearchActivity;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonInfo extends AppCompatActivity implements View.OnClickListener {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public CircleImageView headerCIW;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        headerCIW=(CircleImageView)findViewById(R.id.header_image_CIW);
        RelativeLayout nickNameLayout=(RelativeLayout)findViewById(R.id.nickName_layout);
        RelativeLayout sexLayout=(RelativeLayout)findViewById(R.id.sex_layout);
        RelativeLayout schoolLayout=(RelativeLayout)findViewById(R.id.school_layout);
        RelativeLayout contactMethod=(RelativeLayout)findViewById(R.id.contactMethod_layout);

        headerCIW.setOnClickListener(this);
        nickNameLayout.setOnClickListener(this);
        sexLayout.setOnClickListener(this);
        schoolLayout.setOnClickListener(this);
        contactMethod.setOnClickListener(this);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onClick(View v){   //点击个人信息子布局可进行编辑
        switch (v.getId()){
            case R.id.header_image_CIW:
                dialogList();
                break;
            case R.id.nickName_layout:
                final EditText et_nickname = new EditText(this);
                final TextView tv_nickName=(TextView)findViewById(R.id.nickName_tv);
                new AlertDialog.Builder(this).setTitle("编辑昵称")
                        .setIcon(R.drawable.ic_smile)
                        .setView(et_nickname)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et_nickname.getText().toString();
                                if (input.equals("")) {
                                    Toast.makeText(getApplicationContext(), "内容不能为空！" + input, Toast.LENGTH_LONG).show();
                                }
                                else{
                                    tv_nickName.setText(input);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;

            case R.id.sex_layout:
                dialogChoice();
                break;
            case R.id.school_layout:
                final EditText et_school = new EditText(this);
                final TextView tv_school= (TextView)findViewById(R.id.school_tv);
                new AlertDialog.Builder(this).setTitle("填写学校")
                        .setIcon(R.drawable.ic_smile)
                        .setView(et_school)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et_school.getText().toString();
                                if (input.equals("")) {
                                    Toast.makeText(getApplicationContext(), "学校不能为空！" + input, Toast.LENGTH_LONG).show();
                                }
                                else {
                                    tv_school.setText(input);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.contactMethod_layout:
                final EditText et_contactMethod = new EditText(this);
                final TextView tv_contactMethod = (TextView) findViewById(R.id.contactMethod_tv);
                new AlertDialog.Builder(this).setTitle("填写联系方式")
                        .setIcon(R.drawable.ic_smile)
                        .setView(et_contactMethod)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et_contactMethod.getText().toString();
                                if (input.equals("")) {
                                    Toast.makeText(getApplicationContext(), "联系方式不能为空！" + input, Toast.LENGTH_LONG).show();
                                }
                                else {
                                    tv_contactMethod.setText(input);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;

        }

    }

    private void dialogList() {
        final String items[] = {"Take a photo", "Select from album"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择图片");
        // builder.setMessage("是否确认退出?"); //设置内容
        builder.setIcon(R.drawable.ic_smile);
        // 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        takePhoto();
                        break;
                    case 1:
                        chooseFromAlbum();
                        break;
                }
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void dialogChoice() {
        final String items[] = {"男", "女"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择性别");
        builder.setIcon(R.drawable.ic_smile);
        builder.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    final TextView tv_sex=(TextView) findViewById(R.id.sex_tv);
                    @Override
                   public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:
                                tv_sex.setText(items[0]);
                                break;
                            case 1:
                                tv_sex.setText(items[1]);
                                break;
                        }
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void takePhoto(){
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");

        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(PersonInfo.this,
                    "com.example.cameraalbumtest.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }

        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    private void chooseFromAlbum(){
        if (ContextCompat.checkSelfPermission(PersonInfo.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PersonInfo.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }


    //打开相册
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "you denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bm = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        headerCIW.setImageBitmap(bm);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {  //4.4及以上的系统使用这个方法处理图片；
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);  //4.4及以下的系统使用这个方法处理图片
                    }
                }
            default:
                break;
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }


    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            headerCIW.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 4.4及以上的系统使用这个方法处理图片
     *
     * @param data
     */
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果document类型的Uri,则通过document来处理
            String docID = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docID.split(":")[1];     //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;

                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/piblic_downloads"), Long.valueOf(docID));

                imagePath = getImagePath(contentUri, null);

            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则使用普通方式使用
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，直接获取路径即可
            imagePath = uri.getPath();

        }

        displayImage(imagePath);
    }
}
