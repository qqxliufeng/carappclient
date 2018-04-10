package com.android.ql.lf.carappclient.present;


import com.android.ql.lf.carappclient.action.IViewCommunityAction;
import com.android.ql.lf.carappclient.action.ViewCommunityAction;
import com.android.ql.lf.carappclient.data.ImageBean;
import com.android.ql.lf.carappclient.utils.ImageUploadHelper;

import java.util.ArrayList;

/**
 * Created by lf on 18.2.13.
 *
 * @author lf on 18.2.13
 */

public class CommunityPresent {

    private IViewCommunityAction viewCommunityAction;

    public CommunityPresent() {
        viewCommunityAction = new ViewCommunityAction();
    }

    public void onUploadArticle(ArrayList<ImageBean> imageBeans, int maxSize, ImageUploadHelper.OnImageUploadListener uploadListener) {
        viewCommunityAction.processImage(imageBeans, maxSize, uploadListener);
    }
}
