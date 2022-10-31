package ua.com.fennec.afterAuthFlow.profileModule.interfaces;

import androidx.annotation.NonNull;

import ua.com.fennec.models.Profile;

public interface ProfileInteractorOutput {
    void profileDidGot(@NonNull Profile profile);
    void profileDidUpdated(@NonNull Profile profile);
}
