package com.betterda.betterdapay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.R;


/**自定义的标题布局
 * @author lyf
 *
 */
public class NormalTopBar extends RelativeLayout {
	private ImageView ivBack;
	private TextView tvTitle; //标题
	private TextView tvAction;
	private RelativeLayout relative_bus,relative_share;//
	private TextView tv_bus; //

	public NormalTopBar(Context context) {
		this(context, null);
	}

	public NormalTopBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		View.inflate(context, R.layout.bar_normal, this);
		ivBack = (ImageView) findViewById(R.id.bar_back);
		tvTitle = (TextView) findViewById(R.id.bar_title);
		tvAction = (TextView) findViewById(R.id.bar_action);
		relative_bus = (RelativeLayout) findViewById(R.id.bar_relative_bus);
		relative_share = (RelativeLayout) findViewById(R.id.bar_relative_share);
		tv_bus = (TextView) findViewById(R.id.tv_bar_bus);
	}

	public void setBackVisibility(boolean show) {
		ivBack.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
	}
	public void setShareVisibility(boolean show) {
		relative_share.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
	}
	//给后退键设置监听
	public void setOnBackListener(OnClickListener listener) {
		ivBack.setOnClickListener(listener);
	}

	//给后退键设置监听
	public void setOnShareListener(OnClickListener listener) {
		relative_share.setOnClickListener(listener);
	}

	public void setOnActionListener(OnClickListener listener) {
		tvAction.setOnClickListener(listener);
	}
	public void setOnBusListener(OnClickListener listener) {
		relative_bus.setOnClickListener(listener);
	}

	public void setTitle(String title) {
		tvTitle.setText(title);
	}

	public void setTitle(int titleId) {
		tvTitle.setText(titleId);
	}

	public void setActionText(String text) {
		tvAction.setText(text);
	}

	public void setActionText(int textId) {
		tvAction.setText(textId);
	}

	public void setActionTextVisibility(boolean visibility) {
		tvAction.setVisibility(visibility ? View.VISIBLE : View.GONE);
	}
	public void setBusVisibility(boolean visibility) {
		relative_bus.setVisibility(visibility ? View.VISIBLE : View.GONE);
	}
	public void setBusTextVisibility(boolean visibility) {
		tv_bus.setVisibility(visibility ? View.VISIBLE : View.GONE);
	}
	public void setBusText(String text) {
		tv_bus.setText(text);
	}

	public ImageView getBackView() {
		return ivBack;
	}

	public TextView getTitleView() {
		return tvTitle;
	}

	public View getActionView() {
		return tvAction;
	}
}
