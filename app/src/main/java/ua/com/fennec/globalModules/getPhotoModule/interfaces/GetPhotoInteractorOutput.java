package ua.com.fennec.globalModules.getPhotoModule.interfaces;

import java.util.ArrayList;

public interface GetPhotoInteractorOutput {
    void privateGalleryGot(ArrayList<String> photos);
    void publicGalleryGot(ArrayList<String> photos);
}
