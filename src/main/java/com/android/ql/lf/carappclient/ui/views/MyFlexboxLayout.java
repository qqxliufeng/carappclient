package com.android.ql.lf.carappclient.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.ql.lf.carappclient.R;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

/**
 * Created by lf on 2017/12/4 0004.
 *
 * @author lf on 2017/12/4 0004
 */

public class MyFlexboxLayout extends LinearLayout {

    private TextView tvTitle;

    private FlexboxLayout flexboxLayout;

    private OnItemClickListener onItemClickListener;

    private String selectName = "";

    private int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

    public MyFlexboxLayout(Context context) {
        this(context, null);
    }

    public MyFlexboxLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyFlexboxLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void init() {
        setOrientation(VERTICAL);
        tvTitle = new TextView(getContext());
        tvTitle.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        tvTitle.setPadding(padding, padding, padding, padding);
        tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.text_deep_dark_color));
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.sp_16));
        tvTitle.setText("标题");
        addView(tvTitle);

        flexboxLayout = new FlexboxLayout(getContext());
        flexboxLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        flexboxLayout.setFlexWrap(FlexWrap.WRAP);
    }


    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void addItems(final ArrayList<String> items, final ArrayList<String> status) {
        if (items != null && !items.isEmpty() && status != null && items.size() == status.size()) {

            for (int i = 0; i < items.size(); i++) {
                final String item = items.get(i);
                final CheckedTextView tv = new CheckedTextView(flexboxLayout.getContext());
                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(margin, margin, margin, margin);
                tv.setText(item);
                // 1 为选中
                tv.setChecked("1".equals(status.get(i)));
                tv.setLayoutParams(params);
                tv.setBackgroundResource(R.drawable.selector_tv_bg1);
                tv.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.select_tv_color1));
                tv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (BottomGoodsParamDialog.INSTALL_SERVICE_FLAG.equals(tvTitle.getText().toString())) {
                            tv.setChecked(!tv.isChecked());
                            if (tv.isChecked()) {
                                if (onItemClickListener != null) {
                                    selectName = tv.getText().toString();
                                    onItemClickListener.onItemClick(items.indexOf(item));
                                }
                            } else {
                                selectName = "";
                            }
                        } else {
                            if (onItemClickListener != null) {
                                for (int i = 0; i < flexboxLayout.getChildCount(); i++) {
                                    CheckedTextView childAt = (CheckedTextView) flexboxLayout.getChildAt(i);
                                    childAt.setChecked(childAt == tv);
                                }
                                tv.setChecked(true);
                                selectName = tv.getText().toString();
                                onItemClickListener.onItemClick(items.indexOf(item));
                            }
                        }
                    }
                });
                flexboxLayout.addView(tv);
            }
            for (int i = 0; i < flexboxLayout.getChildCount(); i++) {
                CheckedTextView checkedTextView = (CheckedTextView) flexboxLayout.getChildAt(i);
                if (checkedTextView.isChecked()) {
                    selectName = checkedTextView.getText().toString();
                    break;
                }
            }
            addView(flexboxLayout);
        }
    }


    public String getTitle() {
        return tvTitle.getText().toString();
    }

    public String getSelectName() {
        return selectName;
    }

    public interface OnItemClickListener {
        public void onItemClick(int index);
    }

}
