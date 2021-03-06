package com.nishitadutta.investomaster.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Clas that abstracts all intents and similar functions related to navigation
 */
public class Navigator {

    private Context activityContext;

    public Navigator() {

    }

    public void callIntent(Context mContext, String contact) {
        activityContext = mContext;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel: " + contact));
        startActivity(callIntent);
    }

    public void emailIntent(Context mContext, String email, String subject, String content) {
        activityContext = mContext;
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, content);
        try {
            startActivity(Intent.createChooser(i, "Send Email"));
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(mContext, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void openDialer(Context mContext, String telephone) {
        activityContext = mContext;
        Uri uri = Uri.parse("tel:".concat(telephone));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void openUrl(Context mContext, String url) {
        activityContext = mContext;
        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    private void startActivity(Intent intent) {
        activityContext.startActivity(intent);
    }

/*    public Intent openGallery(Context mContext)
    {
        activityContext = mContext;
        Intent intent = new Intent(mContext, AlbumSelectActivity.class);
        intent.putExtra("" + Constants.INTENT_EXTRA_LIMIT, AppConstants.GALLERY_NUM_IMGS_TO_SELECT);
        return intent;
    }*/

    public void openNewActivity(Context mContext, Activity activity) {
        activityContext = mContext;
        Intent i = new Intent(mContext, activity.getClass());
        startActivity(i);
    }

    public void openNewActivityForResult(Context mContext, Activity activity, int requestCode) {
        activityContext = mContext;
        Intent i = new Intent(mContext, activity.getClass());
        ((Activity) mContext).startActivityForResult(i, requestCode);
    }

    public void openNewActivityForResultWithExtras(Context mContext, Activity activity, int requestCode, Bundle bundle) {
        activityContext = mContext;
        Intent i = new Intent(mContext, activity.getClass());
        i.putExtras(bundle);
        ((Activity) mContext).startActivityForResult(i, requestCode);
    }

    public void openNewActivityWithExtras(Context mContext, Activity activity, Bundle bundle) {
        activityContext = mContext;
        Intent i = new Intent(mContext, activity.getClass());
        i.putExtras(bundle);
        startActivity(i);
    }

   /* public void openNewFragment(Context mContext, Fragment mFragment, int layout) {
        activityContext = mContext;
        String tag = mFragment.getClass().getCanonicalName();
        ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction().replace(layout, mFragment, tag).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }

    public void openNewFragmentAddToBackStack(Context mContext, Fragment mFragment, int layout) {
        activityContext = mContext;
        String tag = mFragment.getClass().getCanonicalName();
        ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction().replace(layout, mFragment, tag).addToBackStack(tag).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }*/

/*    public void openNewProductFragment(Context mContext)
    {
        ((Activity) mContext).getFragmentManager().beginTransaction().replace(R.id.content_frame, ProductListFragment.newInstance(), "ProductListFragment").addToBackStack("ProductListFragment").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }*/

/*    public void takePhoto(Context mContext)
    {
        activityContext = mContext;
        Intent i = new Intent(mContext, AddProductActivity.class);
        startActivity(i);
    }*/
}
