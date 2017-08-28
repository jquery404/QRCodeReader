package com.jquery404.qrcodereader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.jquery404.qrcodereader.database.IDatabaseRepository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jquery404.qrcodereader.R.id.nameWrapper;

/**
 * Created by Faisal on 8/26/17.
 */

public class RegistrationActivity extends BaseCompatActivity {

    @BindView(R.id.spinner0)
    Spinner sShift;
    @BindView(R.id.spinner1)
    Spinner sYear;
    @BindView(R.id.spinner2)
    Spinner sCareer;

    @BindView(R.id.root)
    NestedScrollView scrollView;

    @BindView(R.id.mainform)
    ViewGroup vgMainForm;

    @BindView(nameWrapper)
    TextInputLayout tilName;
    @BindView(R.id.familyNameWrapper)
    TextInputLayout tilFamilyName;
    @BindView(R.id.marriedNameWrapper)
    TextInputLayout tilMarriedName;
    @BindView(R.id.dniWrapper)
    TextInputLayout tilDNI;
    @BindView(R.id.emailWrapper)
    TextInputLayout tilEmail;
    @BindView(R.id.phoneWrapper)
    TextInputLayout tilPhone;
    @BindView(R.id.qrWrapper)
    TextInputLayout tilQR;


    private IDatabaseRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        initViews();
    }

    public void initViews() {

        QRCodeReaderApplication app = (QRCodeReaderApplication) getApplication();
        repository = app.getDatabase();

        String[] shiftArray = getResources().getStringArray(R.array.opt_shift);
        String[] yearArray = getResources().getStringArray(R.array.opt_year);
        String[] careerArray = getResources().getStringArray(R.array.opt_career);


        ArrayAdapter<String> shiftAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, shiftArray);
        sShift.setAdapter(shiftAdapter);

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, yearArray);
        sYear.setAdapter(yearAdapter);

        ArrayAdapter<String> careerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, careerArray);
        sCareer.setAdapter(careerAdapter);

    }


    @OnClick(R.id.button_list)
    public void onClickList() {
        ViewListActivity.start(this);
    }


    @OnClick(R.id.btn_submit)
    public void onSubmitForm() {

        String name = tilName.getEditText().getText().toString();
        String parentName = tilFamilyName.getEditText().getText().toString();
        String marryName = tilMarriedName.getEditText().getText().toString();
        String dni = tilDNI.getEditText().getText().toString();
        String email = tilEmail.getEditText().getText().toString();
        String phone = tilPhone.getEditText().getText().toString();
        String shift = sShift.getSelectedItem().toString();
        String year = sYear.getSelectedItem().toString();
        String career = sCareer.getSelectedItem().toString();
        String qrcode = tilQR.getEditText().getText().toString();

        if (isValidate(name, parentName, marryName, dni, email, phone, shift, year, career, qrcode)) {
            hideKeyboard();
            tilName.setErrorEnabled(false);
            tilFamilyName.setErrorEnabled(false);
            tilMarriedName.setErrorEnabled(false);
            tilDNI.setErrorEnabled(false);
            tilEmail.setErrorEnabled(false);
            tilPhone.setErrorEnabled(false);
            tilQR.setErrorEnabled(false);
            repository.addInfo(name, parentName, marryName, dni, email, phone, shift, year, career, qrcode);

            sShift.setSelection(0);
            sYear.setSelection(0);
            sCareer.setSelection(0);
            scrollView.scrollTo(0, 0);
            clearForm(vgMainForm);

            Toast.makeText(this, "Completo", Toast.LENGTH_SHORT).show();

        }

    }


    public boolean isValidate(String name, String parentName, String marryName, String dni, String email,
                              String phone, String shift, String year, String career, String qrcode) {
        if (name.isEmpty()) {
            tilName.setError(getString(R.string.not_allow_empty));
            return false;
        } else if (parentName.isEmpty()) {
            tilFamilyName.setError(getString(R.string.not_allow_empty));
            return false;
        } else if (marryName.isEmpty()) {
            tilMarriedName.setError(getString(R.string.not_allow_empty));
            return false;
        } else if (dni.isEmpty()) {
            tilDNI.setError(getString(R.string.not_allow_empty));
            return false;
        } else if (email.isEmpty()) {
            tilEmail.setError(getString(R.string.not_allow_empty));
            return false;
        } else if (phone.isEmpty()) {
            tilPhone.setError(getString(R.string.not_allow_empty));
            return false;
        } else if (shift.equals(getString(R.string.default_spinner_value))) {
            Toast.makeText(this, "Select some value", Toast.LENGTH_SHORT).show();
            return false;
        } else if (year.equals(getString(R.string.default_spinner_value))) {
            Toast.makeText(this, "Select some value", Toast.LENGTH_SHORT).show();
            return false;
        } else if (career.equals(getString(R.string.default_spinner_value))) {
            Toast.makeText(this, "Select some value", Toast.LENGTH_SHORT).show();
            return false;
        } else if (qrcode.isEmpty()) {
            tilQR.setError(getString(R.string.not_allow_empty));
            return false;
        }
        return true;
    }

    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof TextInputLayout) {
                ((TextInputLayout) view).getEditText().setText("");
                view.clearFocus();
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public static void start(Context context) {
        Intent launchIntent = new Intent(context, RegistrationActivity.class);
        launchIntent.putExtra("flag", context.getClass().getSimpleName());
        context.startActivity(launchIntent);
    }

}
