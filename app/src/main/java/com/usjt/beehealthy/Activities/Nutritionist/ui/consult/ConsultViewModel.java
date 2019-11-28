package com.usjt.beehealthy.Activities.Nutritionist.ui.consult;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConsultViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ConsultViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}