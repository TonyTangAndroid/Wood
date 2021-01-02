package com.tonytangandroid.wood;

import androidx.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
import static com.tonytangandroid.wood.WoodColorUtil.SEARCHED_HIGHLIGHT_BACKGROUND_COLOR;

public class LeafDetailFragment extends Fragment implements View.OnClickListener, TextUtil.AsyncTextProvider, TextWatcher {

    private static final String ARG_ID = "arg_id";
    private final BackgroundColorSpan colorSpan = new BackgroundColorSpan(SEARCHED_HIGHLIGHT_BACKGROUND_COLOR);
    private long id;
    private String searchKey;
    private WoodColorUtil colorUtil;
    private int currentSearchIndex;
    private Leaf leaf;
    private List<Integer> searchIndexList = new ArrayList<>(0);
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private View search_bar;
    private EditText et_key_word;
    private TextView tv_search_count;
    private AppCompatTextView tv_body;
    private final Debouncer<String> searchDebouncer = new Debouncer<>(400, this::onSearchKeyEmitted);
    private NestedScrollView nested_scroll_view;
    private FloatingActionButton floating_action_button;


    public static LeafDetailFragment newInstance(long id) {
        LeafDetailFragment fragment = new LeafDetailFragment();
        Bundle b = new Bundle();
        b.putLong(ARG_ID, id);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        assert getArguments() != null;
        id = getArguments().getLong(ARG_ID);
        colorUtil = WoodColorUtil.getInstance(getContext());
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wood_fragment_leaf_detail, container, false);
        bindView(rootView);
        return rootView;
    }

    private void bindView(View rootView) {
        tv_body = rootView.findViewById(R.id.wood_details_body);
        nested_scroll_view = rootView.findViewById(R.id.wood_details_scroll_parent);
        floating_action_button = rootView.findViewById(R.id.wood_details_search_fab);
        search_bar = rootView.findViewById(R.id.wood_details_search_bar);
        View searchBarPrev = rootView.findViewById(R.id.wood_details_search_prev);
        View searchBarNext = rootView.findViewById(R.id.wood_details_search_next);
        View searchBarClose = rootView.findViewById(R.id.wood_details_search_close);
        et_key_word = rootView.findViewById(R.id.wood_details_search);
        tv_search_count = rootView.findViewById(R.id.wood_details_search_count);
        floating_action_button.setOnClickListener(this);
        searchBarPrev.setOnClickListener(this);
        searchBarNext.setOnClickListener(this);
        searchBarClose.setOnClickListener(this);
        et_key_word.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        searchDebouncer.consume(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observe();
    }

    private void observe() {
        LeafDetailViewModel viewModel = ViewModelProviders.of(requireActivity())
                .get(LeafDetailViewModel.class);
        viewModel.getTransactionWithId(id).observe(getViewLifecycleOwner(), this::transactionUpdated);
    }

    private void transactionUpdated(Leaf transaction) {
        this.leaf = transaction;
        populateUI();
    }

    private void populateUI() {
        int color = colorUtil.getTransactionColor(leaf);
        floating_action_button.setBackgroundTintList(colorStateList(color));
        search_bar.setBackgroundColor(color);
        et_key_word.setHint(R.string.wood_search_hint);
        populateBody();
    }

    @NonNull
    private ColorStateList colorStateList(int color) {
        return new ColorStateList(new int[][]{new int[]{0}}, new int[]{color});
    }

    private void onSearchKeyEmitted(String searchKey) {
        this.searchKey = searchKey;
        updateUI();
    }

    private void updateUI() {
        searchIndexList = FormatUtils.highlightSearchKeyword(tv_body, searchKey);
        updateSearch(1, searchKey);
    }

    private void populateBody() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(leaf.getTag());
        actionBar.setSubtitle(timeDesc(leaf));
        TextUtil.asyncSetText(executor, this);
    }

    @NonNull
    private String timeDesc(Leaf leaf) {
        return FormatUtils.timeDesc(leaf.getCreateAt());
    }

    @Override
    public CharSequence getText() {
        CharSequence body = leaf.body();
        if (TextUtil.isNullOrWhiteSpace(body) || TextUtil.isNullOrWhiteSpace(searchKey)) {
            return body;
        } else {
            List<Integer> indexList = FormatUtils.indexOf(body, searchKey);
            SpannableString spannableBody = new SpannableString(body);
            FormatUtils.applyHighlightSpan(spannableBody, indexList, searchKey.length());
            searchIndexList = indexList;
            return spannableBody;
        }
    }

    @Override
    public AppCompatTextView getTextView() {
        return tv_body;
    }


    private void updateSearch(int targetIndex, String searchKey) {
        List<Integer> list = searchIndexList;
        int size = list.size();
        targetIndex = adjustTargetIndex(targetIndex, size);
        tv_search_count.setText(String.valueOf(targetIndex).concat("/").concat(String.valueOf(size)));
        ((Spannable) tv_body.getText()).removeSpan(colorSpan);
        if (targetIndex > 0) {
            updateSpan(targetIndex, searchKey, list);
        }
        currentSearchIndex = targetIndex;
    }

    private void updateSpan(int targetIndex, String searchKey, List<Integer> list) {
        int begin = list.get(targetIndex - 1);
        int end = begin + searchKey.length();
        int lineNumber = tv_body.getLayout().getLineForOffset(begin);
        ((Spannable) tv_body.getText()).setSpan(colorSpan, begin, end, SPAN_EXCLUSIVE_EXCLUSIVE);
        int scrollToY = tv_body.getLayout().getLineTop(lineNumber);
        nested_scroll_view.scrollTo(0, scrollToY);
    }

    private int adjustTargetIndex(int targetIndex, int size) {
        if (size == 0) {
            targetIndex = 0;
        } else {
            if (targetIndex > size) {
                targetIndex = 1;
            } else if (targetIndex <= 0) {
                targetIndex = size;
            }
        }
        return targetIndex;
    }

    private void showKeyboard() {
        et_key_word.requestFocus();
        InputMethodManager imm = inputMethodManager();
        if (imm != null) {
            imm.showSoftInput(et_key_word, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private InputMethodManager inputMethodManager() {
        return (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private void hideKeyboard() {
        InputMethodManager imm = inputMethodManager();
        if (imm != null) {
            imm.hideSoftInputFromWindow(et_key_word.getWindowToken(), 0);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (!isVisibleToUser) {
            hideKeyboard();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.wood_details_search_fab) {
            showSearch();
        } else if (id == R.id.wood_details_search_close) {
            clearSearch();
        } else if (id == R.id.wood_details_search_prev) {
            updateSearch(currentSearchIndex - 1, searchKey);
        } else if (id == R.id.wood_details_search_next) {
            updateSearch(currentSearchIndex + 1, searchKey);
        }
    }

    private void clearSearch() {
        if (TextUtil.isNullOrWhiteSpace(searchKey)) {
            floating_action_button.show();
            search_bar.setVisibility(View.GONE);
            nested_scroll_view.setPadding(0, 0, 0, nested_scroll_view.getBottom());
            hideKeyboard();
        } else {
            et_key_word.setText("");
        }
    }

    private void showSearch() {
        floating_action_button.hide();
        search_bar.setVisibility(View.VISIBLE);
        nested_scroll_view.setPadding(0, getResources().getDimensionPixelSize(R.dimen.wood_search_bar_height), 0, nested_scroll_view.getBottom());
        showKeyboard();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.wood_details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.share_text) {
            share(FormatUtils.getShareText(leaf));
            return true;
        } else if (itemId == R.id.copy) {
            copy(FormatUtils.getShareText(leaf));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    private void copy(CharSequence text) {
        ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("log", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(requireContext(), "Copied", Toast.LENGTH_SHORT).show();
    }

    private void share(CharSequence content) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, null));
    }


}