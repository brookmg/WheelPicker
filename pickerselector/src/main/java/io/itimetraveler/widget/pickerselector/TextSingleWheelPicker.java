package io.itimetraveler.widget.pickerselector;

import android.content.Context;
import android.content.res.ColorStateList;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.itimetraveler.widget.adapter.PickerAdapter;
import io.itimetraveler.widget.picker.WheelPicker;

/**
 * Created by iTimeTraveler on 2017/12/19.
 */
public class TextSingleWheelPicker extends WheelPicker {

	private TextAdapter mAdapter;
	private List<String> mStrList;

	public TextSingleWheelPicker(Context context) {
		this(context, null);
	}

	public TextSingleWheelPicker(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TextSingleWheelPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mAdapter = new TextAdapter(context);
		setAdapter(mAdapter);
	}

	public void setTextList(List<String> list){
		mStrList = list;
		mAdapter.setTextList(list);
	}

	public void setTheme(Theme theme){
		switch (theme){
			case black:
				setBackgroundColor(0xFF0D0D0D);
				setDefaultColor(0xFF626262);
				setSelectColor(0xFFCFCFCF);
//				setDividerColor(0xFF3C3C3C);
				break;
			case white:
			default:
				setBackgroundColor(0xFFFFFFFF);
				setDefaultColor(0xFFA4A4A4);
				setSelectColor(0xFF333333);
//				setDividerColor(0xFFCDCDCD);
				break;
		}
	}

	/**
	 * 字体大小
	 * @param textSize
	 */
	public void setTextSize(int textSize){
		mAdapter.setTextSize(textSize);
	}

	/**
	 * 未选中项字体颜色
	 * @param color
	 */
	public void setDefaultColor(int color){
		mAdapter.setDefaultColor(color);
	}

	/**
	 * 选中项字体颜色
	 * @param selectColor
	 */
	public void setSelectColor(int selectColor) {
		mAdapter.setSelectColor(selectColor);
	}

	//主题色
	public enum Theme {
		white, black
	}

	private static class TextAdapter extends PickerAdapter {
		private Context mContext;
		private List<String> mStrList;

		//默认配置
		private int mTextSize = 20;
		private int mDefaultColor = 0xFFAAAAAA;
		private int mSelectColor = 0xFF333333;

		public TextAdapter(Context context){
			mContext = context;
		}

		public void setTextList(List<String> list){
			mStrList = list;
		}

		public void setTextSize(int mTextSize) {
			this.mTextSize = mTextSize;
		}

		public void setDefaultColor(int mDefaultColor) {
			this.mDefaultColor = mDefaultColor;
		}

		public void setSelectColor(int mSelectColor) {
			this.mSelectColor = mSelectColor;
		}

		@Override
		public int numberOfComponentsInWheelPicker(WheelPicker wheelPicker) {
			return 1;
		}

		@Override
		public int numberOfRowsInComponent(int component) {
			return mStrList != null ? mStrList.size() : 0;
		}

		@Override
		public View onCreateView(ViewGroup parent, int row, int component) {
			ViewHolder viewHolder = new ViewHolder();
			TextView tv = new TextView(mContext);
			tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			tv.setPadding(20, 3, 20, 3);
			tv.setTextSize(mTextSize);

			//选中颜色
			int[] colors = new int[] {mSelectColor, mDefaultColor};
			int[][] states = {{android.R.attr.state_selected}, {}};
			tv.setTextColor(new ColorStateList(states, colors));

			viewHolder.textView = tv;
			View convertView = tv;
			convertView.setTag(viewHolder);
			viewHolder.textView.setText(mStrList.get(row));
			return convertView;
		}

		@Override
		public void onBindView(ViewGroup parent, View convertView, int row, int component) {
			Object object = convertView.getTag();
			if(object instanceof ViewHolder){
				((ViewHolder) object).textView.setText(mStrList.get(row));
			}
		}
	}

	private static class ViewHolder{
		TextView textView;
	}
}
