package ua.com.fennec.customs;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ua.com.fennec.R;

public class FennecFragment extends Fragment {
    public FragmentManager fragmentManager;



    public void init() {
        fragmentManager = getParentFragmentManager();
    }
    public void showFragment(Fragment fragment, Boolean withAnimation) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (withAnimation == true) {
            ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        }
        ft.replace(R.id.fragmentContainer, fragment).commitAllowingStateLoss();
    }
    public void addFragment(Fragment fragment, Boolean withAnimation) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (withAnimation == true) {
            ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        }
        ft.add(R.id.fragmentContainer, fragment).commitAllowingStateLoss();
    }
    public void removeAllFragments() {
        FragmentManager manager = fragmentManager;
        for (int i = 0; i < manager.getFragments().size(); i++) {
            manager.beginTransaction().remove(manager.getFragments().get(i)).commitAllowingStateLoss();
        }
    }

}
