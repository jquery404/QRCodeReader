package com.jquery404.qrcodereader;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jquery404.qrcodereader.helper.AnimatorUtils;
import com.ogaclejapan.arclayout.ArcLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseCompatActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.buttonScan)
    Button buttonScan;

    @BindView(R.id.results)
    TextView tvResults;

    @BindView(R.id.menu_layout)
    View menuLayout;

    @BindView(R.id.arc_layout)
    ArcLayout arcLayout;

    @BindView(R.id.adView)
    AdView adView;

    private IntentIntegrator qrScan;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initViews();
    }

    public void initViews() {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        qrScan = new IntentIntegrator(this);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("flag", context.getClass().getSimpleName());
        context.startActivity(intent);
    }


    @OnClick(R.id.buttonScan)
    public void onClickScan() {
        hideMenu();
        qrScan.initiateScan();
    }

    @OnClick(R.id.ivlogo)
    public void onLogoClick() {
        AboutActivity.start(this);
    }


    @OnClick(R.id.btnshare)
    public void onClickShare() {
        hideMenu();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, tvResults.getText());
        startActivity(Intent.createChooser(intent, "Share using"));
    }

    @OnClick(R.id.btnemail)
    public void onClickEmail() {
        hideMenu();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{tvResults.getText().toString()});
        startActivity(intent);
    }

    @OnClick(R.id.btnweb)
    public void onClickWeb() {
        hideMenu();
        goToUrl(tvResults.getText().toString());
    }


    @OnClick(R.id.btnphone)
    public void onClickPhone() {
        hideMenu();
        onCallAskPermission();
    }


    @OnClick(R.id.btncontact)
    public void onClickContact() {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.NAME, "Contact Name");
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, tvResults.getText());
        startActivity(intent);
    }

    @AfterPermissionGranted(REQUEST_CODE_ASK_PERMISSIONS)
    void onCallAskPermission() {
        if (EasyPermissions.hasPermissions(this, android.Manifest.permission.CALL_PHONE)) {
            callPhone();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE},
                    REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + tvResults.getText()));
        startActivity(intent);
    }


    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse("https://google.com/search?q=" + url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @SuppressWarnings("NewApi")
    private void showMenu() {
        menuLayout.setVisibility(View.VISIBLE);

        List<Animator> animList = new ArrayList<>();

        for (int i = 0, len = arcLayout.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(arcLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }

    @SuppressWarnings("NewApi")
    private void hideMenu() {

        List<Animator> animList = new ArrayList<>();

        for (int i = arcLayout.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(arcLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                menuLayout.setVisibility(View.INVISIBLE);
            }
        });
        animSet.start();

    }

    private Animator createShowItemAnimator(View item) {

        float dx = buttonScan.getX() - item.getX();
        float dy = buttonScan.getY() - item.getY();

        item.setRotation(0f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(0f, 720f),
                AnimatorUtils.translationX(dx, 0f),
                AnimatorUtils.translationY(dy, 0f)
        );

        return anim;
    }

    private Animator createHideItemAnimator(final View item) {
        float dx = buttonScan.getX() - item.getX();
        float dy = buttonScan.getY() - item.getY();

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(720f, 0f),
                AnimatorUtils.translationX(0f, dx),
                AnimatorUtils.translationY(0f, dy)
        );

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });

        return anim;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                tvResults.setText(result.getContents());
                showMenu();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (EasyPermissions.hasPermissions(this, android.Manifest.permission.CALL_PHONE)) {
            callPhone();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
