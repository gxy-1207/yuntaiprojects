package com.ytfu.yuntaifawu.ui.search.ack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.lxj.xpopup.util.XPopupUtils;
import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.search.bean.HistoryWord;
import com.ytfu.yuntaifawu.ui.search.bean.RecommendWordBean;
import com.ytfu.yuntaifawu.ui.search.p.SearchPresenter;
import com.ytfu.yuntaifawu.ui.search.v.SearchView;
import com.ytfu.yuntaifawu.ui.users.act.SearchListActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

@InjectLayout(value = R.layout.activity_search, toolbarLayoutId = R.layout.layout_toolbar_search)
@InjectPresenter(SearchPresenter.class)
public class SearchActivity extends BaseActivity<SearchView, SearchPresenter> implements SearchView {


    @BindView(R.id.et_search_input)
    EditText et_search_input;
    @BindView(R.id.fl_search_recommend)
    TagFlowLayout fl_search_recommend;

    public static void start(Context context) {
        Intent starter = new Intent(context, SearchActivity.class);
        context.startActivity(starter);
    }

    //===Desc:================================================================================


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        //设置文本颜色
        MenuItem item = mToolbar.getMenu().findItem(R.id.action_search);
        if (null != item) {
            CharSequence title = item.getTitle();
            SpannableStringBuilder ssb = new SpannableStringBuilder(title);
            //设置文字颜色。
            ssb.setSpan(new ForegroundColorSpan(Color.WHITE), 0, title.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            item.setTitle(ssb);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setViewListener() {
        super.setViewListener();
        et_search_input.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String input = et_search_input.getText().toString();
                if (!TextUtils.isEmpty(input)) {
                    et_search_input.setText("");
                    saveLocal(input);
                    enterResultActivity(input, "");
                }
                return true;
            }

            return false;
        });

        findViewById(R.id.tv_search_clean).setOnClickListener(v -> {
            LitePal.deleteAll(HistoryWord.class);
            getPresenter().getSearchHistory();
        });
    }

    @Override
    protected void initData() {
        super.initData();
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#43A1F7"));
        //获取服务器推荐的关键词
        getPresenter().getRecommendWords();
        //加载本地搜索历史记录
        getPresenter().getSearchHistory();
    }

    //===Desc:================================================================================
    @Override
    public void onRecommendWordsSuccess(List<RecommendWordBean.DataBean> data) {
        if (null == data || data.isEmpty()) {
            onRecommendWordsFail();
            return;
        }
        findViewById(R.id.ll_search_recommend).setVisibility(View.VISIBLE);
        fl_search_recommend.setAdapter(new TagAdapter<RecommendWordBean.DataBean>(data) {
            @Override
            public View getView(FlowLayout parent, int position, RecommendWordBean.DataBean s) {
                TextView tv = new TextView(mContext);
                tv.setTextColor(Color.parseColor("#43A1F7"));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                int paddingTop = XPopupUtils.dp2px(mContext, 8F);
                int paddingLeft = XPopupUtils.dp2px(mContext, 14F);
                tv.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
                tv.setBackgroundResource(R.drawable.selector_serarch_recommend);
                tv.setText(s.getDomain());
                return tv;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                if (view instanceof TextView) {
                    TextView tv = (TextView) view;
                    tv.setTextColor(Color.WHITE);
                }
                //进入搜索界面
                enterResultActivity("", data.get(position).getDomain());
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                if (view instanceof TextView) {
                    TextView tv = (TextView) view;
                    tv.setTextColor(Color.parseColor("#43A1F7"));
                }
            }
        });
    }

    @Override
    public void onRecommendWordsFail() {
        findViewById(R.id.ll_search_recommend).setVisibility(View.GONE);
    }

    @Override
    public void onSearchHistorySuccess(List<HistoryWord> data) {
        Logger.e(String.valueOf(data));
        if (null == data || data.isEmpty()) {
            findViewById(R.id.ll_search_history).setVisibility(View.GONE);
            return;
        }
        LinearLayout ll_search_history_root = findViewById(R.id.ll_search_history_root);
        ll_search_history_root.removeAllViews();
        //动态创建
        for (int i = 0; i < data.size(); i++) {
            if (i >= 5) {
                break;
            }
            String word = data.get(i).getWord();
            TextView textView = createHistoryView();
            textView.setText(word);
            textView.setOnClickListener(v -> {
                saveLocal(word);
                enterResultActivity(word, "");
            });
            ll_search_history_root.addView(textView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, XPopupUtils.dp2px(mContext, .5F));
            lineParams.leftMargin = XPopupUtils.dp2px(mContext, 10);
            lineParams.rightMargin = XPopupUtils.dp2px(mContext, 10);
            View lineView = new View(mContext);
            lineView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            ll_search_history_root.addView(lineView, lineParams);
        }
        findViewById(R.id.ll_search_history).setVisibility(View.VISIBLE);
    }

    //===Desc:================================================================================
    private TextView createHistoryView() {
        TextView tv = new TextView(mContext);
        tv.setMaxLines(1);
        int topPadding = XPopupUtils.dp2px(mContext, 10);
        int leftPadding = XPopupUtils.dp2px(mContext, 8);
        tv.setPadding(leftPadding, topPadding, leftPadding, topPadding);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        tv.setTextColor(Color.parseColor("#999999"));
        tv.setCompoundDrawablePadding(XPopupUtils.dp2px(mContext, 10));
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_search_history_item);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
        tv.setCompoundDrawables(drawable, null, null, null);//画在右边
        return tv;
    }

    private void saveLocal(String searchWord) {
        HistoryWord first = LitePal.where("word = ? ", searchWord).order("time").findFirst(HistoryWord.class);
        if (null == first) {
            first = new HistoryWord();
        }
        first.setWord(searchWord);
        first.setTime(System.currentTimeMillis());
        first.saveOrUpdate("word = ?", searchWord);
        et_search_input.setText("");
        //跟新历史记录显示
        getPresenter().getSearchHistory();
    }

    /**
     * @param lawyerName  搜索使用
     * @param expertPlace 点击擅长领域
     */
    private void enterResultActivity(String lawyerName, String expertPlace) {
        SearchListActivity.start(mContext, lawyerName, expertPlace);
    }
}
