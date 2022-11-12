package com.example.applicationprojetsergiojerem.exo.ui;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.applicationprojetsergiojerem.R;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;

import viewmodel.guide.GuideListViewModel;
import viewmodel.guide.GuideViewModel;

public class GuideEdit extends BaseActivity {

    private Guide guide;
    private boolean isEditMode;
    private Toast toast;

    private GuideViewModel viewModel;

    private EditText etName, etLastName, etDescription, etAddress, etEmail, etPicPath, etBirthDate, etPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_guide);
        //getLayoutInflater().inflate(R.layout.activity_edit_guide, frameLayout);

        // Get every EditText object from the layout
        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        etDescription = findViewById(R.id.etDescription);
        etAddress = findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);
        //etPicPath = findViewById(R.id.etPicPath);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etBirthDate = findViewById(R.id.etBirthDate);

        Button btnSave = findViewById(R.id.btnSave);

        // When clicking on Save button, save changes
        btnSave.setOnClickListener(View -> {
            saveChanges(etName.getText().toString(), etLastName.getText().toString(), etDescription.getText().toString(),
                    etAddress.getText().toString(), etEmail.getText().toString(), /*etPicPath.getText().toString(),*/
                    etPhoneNumber.getText().toString().replace(" ", ""), etBirthDate.getText().toString());
            onBackPressed();
            toast.show();
        });

        // Get guide id from the intent. If there is none, -1 is automatically assigned
        int guideId = getIntent().getIntExtra("guideID", -1);

        // If there was no guide id in the intent, we're in create new guide mode, else we're in edit guide mode
        if (guideId == -1){
            setTitle(getString(R.string.title_activity_create_guide));
            toast = Toast.makeText(this, getString(R.string.guide_created), Toast.LENGTH_LONG);
            isEditMode = false;
        } else {
            setTitle(getString(R.string.title_edit_guide));
            btnSave.setText((R.string.action_update));
            toast = Toast.makeText(this, getString(R.string.guide_eddited), Toast.LENGTH_LONG);
            isEditMode = true;
        }

        GuideViewModel.Factory factory = new GuideViewModel.Factory(getApplication(), guideId);
        viewModel = new ViewModelProvider(new ViewModelStore(), factory).get(GuideViewModel.class);

        // If we got a guide id, we assign the guide values to the edit text objects
        if(isEditMode){
            viewModel.getGuide().observe(this, guideEntity -> {
                if (guideEntity != null){
                    guide = guideEntity;

                    etName.setText(guide.getName());
                    etLastName.setText(guide.getLastName());
                    etBirthDate.setText(guide.getBirthdate());
                    etPhoneNumber.setText(String.valueOf(guide.getPhoneNumber()));
                    etEmail.setText(guide.getEmail());
                    //etPicPath.setText(guide.getPicPath());
                    etAddress.setText(guide.getAddress());
                    etDescription.setText(guide.getDescription());
                }
            });
        }
    }


    private void saveChanges(String name, String lastName, String description, String address,
                             String email, /*String picPath,*/ String phoneNumber,String birthDate){
        if (isEditMode){
            // TODO check that params aren't empty
            guide.setName(name);
            guide.setLastName(lastName);
            guide.setDescription(description);
            guide.setAddress(address);
            guide.setEmail(email);
            //guide.setPicPath(picPath);
            guide.setPhoneNumber(Integer.parseInt(phoneNumber));
            guide.setBirthdate(birthDate);

            viewModel.updateGuide(guide, new OnAsyncEventListener(){
               @Override
               public void onSuccess(){
                   Log.d("EDIT GUIDE", "Update guide success");
               }

               @Override
                public void onFailure(Exception e){
                   Log.d("EDIT GUIDE", "Update guide failure", e);
               }
            });
        } else {
            String phoneS = etPhoneNumber.getText().toString();
            int phone = Integer.parseInt(phoneS);

            guide = new Guide(phone, etBirthDate.getText().toString(),
                    etName.getText().toString(), etLastName.getText().toString(), etDescription.getText().toString(),
                    etAddress.getText().toString(), etEmail.getText().toString(), "waitingToImplementIMGs.png");

            viewModel.createGuide(guide, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d("CREATE GUIDE", "Guide creation success");
                }

                @Override
                public void onFailure(Exception exception) {
                    Log.d("CREATE GUIDE", "Guide creation failure", exception);
                }
            });
        }
    }
}
