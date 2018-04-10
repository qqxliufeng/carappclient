package com.android.ql.lf.carappclient.action;


import com.android.ql.lf.carappclient.data.ImageBean;
import com.android.ql.lf.carappclient.utils.ImageUploadHelper;

import java.util.ArrayList;

/**
 * Created by lf on 18.2.13.
 *
 * @author lf on 18.2.13
 */

public class ViewCommunityAction implements IViewCommunityAction {

    @Override
    public boolean processImage(ArrayList<ImageBean> imageBeans, int maxSize, ImageUploadHelper.OnImageUploadListener uploadListener) {
        try {
            new ImageUploadHelper(uploadListener).upload(imageBeans, maxSize);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
