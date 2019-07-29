//package com.primecloud.library.baselibrary.widget.bottomnavigationviewex;
//
//import android.content.Context;
//import android.content.res.ColorStateList;
//import android.graphics.Paint;
//import android.graphics.Typeface;
//import android.support.design.internal.BottomNavigationItemView;
//import android.support.design.internal.BottomNavigationMenuView;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v4.view.ViewPager;
//import android.util.AttributeSet;
//import android.util.SparseIntArray;
//import android.util.TypedValue;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.lang.ref.WeakReference;
//import java.lang.reflect.Field;
//
///**
// * Created by yu on 2016/11/10.
// */
//public class BottomNavigationViewEx extends BottomNavigationView {
//    // used for animation
//    private float mShiftAmount;
//    private float mScaleUpFactor;
//    private float mScaleDownFactor;
//    private boolean animationRecord;
//    private float mLargeLabelSize;
//    private float mSmallLabelSize;
//    private boolean visibilityTextSizeRecord;
//    private boolean visibilityHeightRecord;
//    private int mItemHeight;
//    private boolean textVisibility = true;
//    // used for animation end
//
//    // used for setupWithViewPager
//    private ViewPager mViewPager;
//    private MyOnNavigationItemSelectedListener mMyOnNavigationItemSelectedListener;
//    private BottomNavigationViewExOnPageChangeListener mPageChangeListener;
//    private BottomNavigationMenuView mMenuView;
//    private BottomNavigationItemView[] mButtons;
//    // used for setupWithViewPager end
//
//    // detect navigation tab changes when the user clicking on navigation item
//    private static boolean isNavigationItemClicking = false;
//
//    public BottomNavigationViewEx(Context context) {
//        super(context);
//    }
//
//    public BottomNavigationViewEx(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public BottomNavigationViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    /**
//     * change the visibility of icon
//     *
//     * @param visibility
//     */
//    public BottomNavigationViewEx setIconVisibility(boolean visibility) {
//        /*
//        1. get field in this class
//        private final BottomNavigationMenuView mMenuView;
//
//        2. get field in mButtons
//        private BottomNavigationItemView[] mButtons;
//
//        3. get mIcon in mButtons
//        private ImageView mIcon
//
//        4. set mIcon visibility gone
//
//        5. change mItemHeight to only text size in mMenuView
//         */
//        // 1. get mMenuView
//        final BottomNavigationMenuView mMenuView = getBottomNavigationMenuView();
//        // 2. get mButtons
//        BottomNavigationItemView[] mButtons = getBottomNavigationItemViews();
//        // 3. get mIcon in mButtons
//        for (BottomNavigationItemView button : mButtons) {
//            ImageView mIcon = getField(button.getClass(), button, "icon");
//            // 4. set mIcon visibility gone
//            mIcon.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
//        }
//
//        // 5. change mItemHeight to only text size in mMenuView
//        if (!visibility) {
//            // if not record mItemHeight
//            if (!visibilityHeightRecord) {
//                visibilityHeightRecord = true;
//                mItemHeight = getItemHeight();
//            }
//
//            // change mItemHeight
//            BottomNavigationItemView button = mButtons[0];
//            if (null != button) {
//                final ImageView mIcon = getField(button.getClass(), button, "icon");
////                System.out.println("mIcon.getMeasuredHeight():" + mIcon.getMeasuredHeight());
//                if (null != mIcon) {
//                    mIcon.post(new Runnable() {
//                        @Override
//                        public void run() {
////                            System.out.println("mIcon.getMeasuredHeight():" + mIcon.getMeasuredHeight());
//                            setItemHeight(mItemHeight - mIcon.getMeasuredHeight());
//                        }
//                    });
//                }
//            }
//        } else {
//            // if not record the mItemHeight, we need do nothing.
//            if (!visibilityHeightRecord)
//                return_back this;
//
//            // restore it
//            setItemHeight(mItemHeight);
//        }
//
//        mMenuView.updateMenuView();
//        return_back this;
//    }
//
//    /**
//     * change the visibility of text
//     *
//     * @param visibility
//     */
//    public BottomNavigationViewEx setTextVisibility(boolean visibility) {
//        this.textVisibility = visibility;
//        /*
//        1. get field in this class
//        private final BottomNavigationMenuView mMenuView;
//
//        2. get field in mButtons
//        private BottomNavigationItemView[] mButtons;
//
//        3. set text size in mButtons
//        private final TextView mLargeLabel
//        private final TextView mSmallLabel
//
//        4. change mItemHeight to only icon size in mMenuView
//         */
//        // 1. get mMenuView
//        BottomNavigationMenuView mMenuView = getBottomNavigationMenuView();
//        // 2. get mButtons
//        BottomNavigationItemView[] mButtons = getBottomNavigationItemViews();
//
//        // 3. change field mShiftingMode value in mButtons
//        for (BottomNavigationItemView button : mButtons) {
//            TextView mLargeLabel = getField(button.getClass(), button, "largeLabel");
//            TextView mSmallLabel = getField(button.getClass(), button, "smallLabel");
//
//            if (!visibility) {
//                // if not record the font size, record it
//                if (!visibilityTextSizeRecord && !animationRecord) {
//                    visibilityTextSizeRecord = true;
//                    mLargeLabelSize = mLargeLabel.getTextSize();
//                    mSmallLabelSize = mSmallLabel.getTextSize();
//                }
//
//                // if not visitable, set font size to 0
//                mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0);
//                mSmallLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0);
//
//            } else {
//                // if not record the font size, we need do nothing.
//                if (!visibilityTextSizeRecord)
//                    break;
//
//                // restore it
//                mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLargeLabelSize);
//                mSmallLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallLabelSize);
//            }
//        }
//
//        // 4 change mItemHeight to only icon size in mMenuView
//        if (!visibility) {
//            // if not record mItemHeight
//            if (!visibilityHeightRecord) {
//                visibilityHeightRecord = true;
//                mItemHeight = getItemHeight();
//            }
//
//            // change mItemHeight to only icon size in mMenuView
//            // private final int mItemHeight;
//
//            // change mItemHeight
////            System.out.println("mLargeLabel.getMeasuredHeight():" + getFontHeight(mSmallLabelSize));
//            setItemHeight(mItemHeight - getFontHeight(mSmallLabelSize));
//
//        } else {
//            // if not record the mItemHeight, we need do nothing.
//            if (!visibilityHeightRecord)
//                return_back this;
//            // restore mItemHeight
//            setItemHeight(mItemHeight);
//        }
//
//        mMenuView.updateMenuView();
//        return_back this;
//    }
//
//    /**
//     * get text height by font size
//     *
//     * @param fontSize
//     * @return_back
//     */
//    private static int getFontHeight(float fontSize) {
//        Paint paint = new Paint();
//        paint.setTextSize(fontSize);
//        Paint.FontMetrics fm = paint.getFontMetrics();
//        return_back (int) Math.ceil(fm.descent - fm.top) + 2;
//    }
//
//    /**
//     * enable or disable click item animation(text scale and icon move animation in no item shifting mode)
//     *
//     * @param enable It means the text won't scale and icon won't move when active it in no item shifting mode if false.
//     */
//    public BottomNavigationViewEx enableAnimation(boolean enable) {
//        /*
//        1. get field in this class
//        private final BottomNavigationMenuView mMenuView;
//
//        2. get field in mButtons
//        private BottomNavigationItemView[] mButtons;
//
//        3. chang mShiftAmount to 0 in mButtons
//        private final int mShiftAmount
//
//        change mScaleUpFactor and mScaleDownFactor to 1f in mButtons
//        private final float mScaleUpFactor
//        private final float mScaleDownFactor
//
//        4. change label font size in mButtons
//        private final TextView mLargeLabel
//        private final TextView mSmallLabel
//         */
//
//        // 1. get mMenuView
//        BottomNavigationMenuView mMenuView = getBottomNavigationMenuView();
//        // 2. get mButtons
//        BottomNavigationItemView[] mButtons = getBottomNavigationItemViews();
//        // 3. change field mShiftingMode value in mButtons
//        for (BottomNavigationItemView button : mButtons) {
//            TextView mLargeLabel = getField(button.getClass(), button, "largeLabel");
//            TextView mSmallLabel = getField(button.getClass(), button, "smallLabel");
//
//            // if disable animation, need animationRecord the source value
//            if (!enable) {
//                if (!animationRecord) {
//                    animationRecord = true;
//                    mShiftAmount = getField(button.getClass(), button, "shiftAmount");
//                    mScaleUpFactor = getField(button.getClass(), button, "scaleUpFactor");
//                    mScaleDownFactor = getField(button.getClass(), button, "scaleDownFactor");
//
//                    mLargeLabelSize = mLargeLabel.getTextSize();
//                    mSmallLabelSize = mSmallLabel.getTextSize();
//
////                    System.out.println("mShiftAmount:" + mShiftAmount + " mScaleUpFactor:"
////                            + mScaleUpFactor + " mScaleDownFactor:" + mScaleDownFactor
////                            + " mLargeLabel:" + mLargeLabelSize + " mSmallLabel:" + mSmallLabelSize);
//                }
//                // disable
//                setField(button.getClass(), button, "shiftAmount", 0);
//                setField(button.getClass(), button, "scaleUpFactor", 1);
//                setField(button.getClass(), button, "scaleDownFactor", 1);
//
//                // let the mLargeLabel font size equal to mSmallLabel
//                mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallLabelSize);
//
//                // debug start
////                mLargeLabelSize = mLargeLabel.getTextSize();
////                System.out.println("mLargeLabel:" + mLargeLabelSize);
//                // debug end
//
//            } else {
//                // haven't change the value. It means it was the first call this method. So nothing need to do.
//                if (!animationRecord)
//                    return_back this;
//                // enable animation
//                setField(button.getClass(), button, "shiftAmount", mShiftAmount);
//                setField(button.getClass(), button, "scaleUpFactor", mScaleUpFactor);
//                setField(button.getClass(), button, "scaleDownFactor", mScaleDownFactor);
//                // restore
//                mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLargeLabelSize);
//            }
//        }
//        mMenuView.updateMenuView();
//        return_back this;
//    }
//
//    /**
//     * @Deprecated use {@link #setLabelVisibilityMode }
//     * enable the shifting mode for navigation
//     *
//     * @param enable It will has a shift animation if true. Otherwise all items are the same width.
//     */
//    @Deprecated
//    public BottomNavigationViewEx enableShiftingMode(boolean enable) {
//        /*
//        1. get field in this class
//        private final BottomNavigationMenuView mMenuView;
//
//        2. change field mShiftingMode value in mMenuView
//        private boolean mShiftingMode = true;
//         */
//        // 1. get mMenuView
////        BottomNavigationMenuView mMenuView = getBottomNavigationMenuView();
//        // 2. change field mShiftingMode value in mMenuView
////        setField(mMenuView.getClass(), mMenuView, "isShifting", enable);
////        mMenuView.updateMenuView();
//        setLabelVisibilityMode(enable ? 0 : 1);
//        return_back this;
//    }
//
//    /**
//     * @Deprecated use {@link #setItemHorizontalTranslationEnabled(boolean)}
//     * enable the shifting mode for each item
//     *
//     * @param enable It will has a shift animation for item if true. Otherwise the item text always be shown.
//     */
//    @Deprecated
//    public BottomNavigationViewEx enableItemShiftingMode(boolean enable) {
//        /*
//        1. get field in this class
//        private final BottomNavigationMenuView mMenuView;
//
//        2. get field in this mMenuView
//        private BottomNavigationItemView[] mButtons;
//
//        3. change field mShiftingMode value in mButtons
//        private boolean mShiftingMode = true;
//         */
//        // 1. get mMenuView
////        BottomNavigationMenuView mMenuView = getBottomNavigationMenuView();
//        // 2. get buttons
////        BottomNavigationItemView[] mButtons = getBottomNavigationItemViews();
//        // 3. change field mShiftingMode value in mButtons
////        for (BottomNavigationItemView button : mButtons) {
////            button.setShifting(enable);
////        }
////        mMenuView.updateMenuView();
//
//        setItemHorizontalTranslationEnabled(enable);
//        return_back this;
//    }
//
//    /**
//     * get the current checked item position
//     *
//     * @return_back index of item, start from 0.
//     */
//    public int getCurrentItem() {
//        /*
//        1. get field in this class
//        private final BottomNavigationMenuView mMenuView;
//
//        2. get field in mMenuView
//        private BottomNavigationItemView[] mButtons;
//
//        3. get menu and traverse it to get the checked one
//         */
//
//        // 2. get mButtons
//        BottomNavigationItemView[] mButtons = getBottomNavigationItemViews();
//        // 3. get menu and traverse it to get the checked one
//        Menu menu = getMenu();
//        for (int i = 0; i < mButtons.length; i++) {
//            if (menu.getItem(i).isChecked()) {
//                return_back i;
//            }
//        }
//        return_back 0;
//    }
//
//    /**
//     * get menu item position in menu
//     *
//     * @param item
//     * @return_back position if success, -1 otherwise
//     */
//    public int getMenuItemPosition(MenuItem item) {
//        // get item id
//        int itemId = item.getItemId();
//        // get meunu
//        Menu menu = getMenu();
//        int size = menu.size();
//        for (int i = 0; i < size; i++) {
//            if (menu.getItem(i).getItemId() == itemId) {
//                return_back i;
//            }
//        }
//        return_back -1;
//    }
//
//    /**
//     * set the current checked item
//     *
//     * @param item start from 0.
//     */
//    public BottomNavigationViewEx setCurrentItem(int index) {
//        setSelectedItemId(getMenu().getItem(index).getItemId());
//        return_back this;
//    }
//
//    /**
//     * get OnNavigationItemSelectedListener
//     *
//     * @return_back
//     */
//    public OnNavigationItemSelectedListener getOnNavigationItemSelectedListener() {
//        // private OnNavigationItemSelectedListener mListener;
//        OnNavigationItemSelectedListener mListener = getField(BottomNavigationView.class, this, "selectedListener");
//        return_back mListener;
//    }
//
//    @Override
//    public void setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener listener) {
//        // if not set up with view pager, the same with father
//        if (null == mMyOnNavigationItemSelectedListener) {
//            super.setOnNavigationItemSelectedListener(listener);
//            return_back;
//        }
//
//        mMyOnNavigationItemSelectedListener.setOnNavigationItemSelectedListener(listener);
//    }
//
//    /**
//     * get private mMenuView
//     *
//     * @return_back
//     */
//    private BottomNavigationMenuView getBottomNavigationMenuView() {
//        if (null == mMenuView)
//            mMenuView = getField(BottomNavigationView.class, this, "menuView");
//        return_back mMenuView;
//    }
//
//    /**
//     * get private mButtons in mMenuView
//     *
//     * @return_back
//     */
//    public BottomNavigationItemView[] getBottomNavigationItemViews() {
//        if (null != mButtons)
//            return_back mButtons;
//        /*
//         * 1 private final BottomNavigationMenuView mMenuView;
//         * 2 private BottomNavigationItemView[] mButtons;
//         */
//        BottomNavigationMenuView mMenuView = getBottomNavigationMenuView();
//        mButtons = getField(mMenuView.getClass(), mMenuView, "buttons");
//        return_back mButtons;
//    }
//
//    /**
//     * get private mButton in mMenuView at position
//     *
//     * @param position
//     * @return_back
//     */
//    public BottomNavigationItemView getBottomNavigationItemView(int position) {
//        return_back getBottomNavigationItemViews()[position];
//    }
//
//    /**
//     * get icon at position
//     *
//     * @param position
//     * @return_back
//     */
//    public ImageView getIconAt(int position) {
//        /*
//         * 1 private final BottomNavigationMenuView mMenuView;
//         * 2 private BottomNavigationItemView[] mButtons;
//         * 3 private ImageView mIcon;
//         */
//        BottomNavigationItemView mButtons = getBottomNavigationItemView(position);
//        ImageView mIcon = getField(BottomNavigationItemView.class, mButtons, "icon");
//        return_back mIcon;
//    }
//
//    /**
//     * get small label at position
//     * Each item has tow label, one is large, another is small.
//     *
//     * @param position
//     * @return_back
//     */
//    public TextView getSmallLabelAt(int position) {
//        /*
//         * 1 private final BottomNavigationMenuView mMenuView;
//         * 2 private BottomNavigationItemView[] mButtons;
//         * 3 private final TextView mSmallLabel;
//         */
//        BottomNavigationItemView mButtons = getBottomNavigationItemView(position);
//        TextView mSmallLabel = getField(BottomNavigationItemView.class, mButtons, "smallLabel");
//        return_back mSmallLabel;
//    }
//
//    /**
//     * get large label at position
//     * Each item has tow label, one is large, another is small.
//     *
//     * @param position
//     * @return_back
//     */
//    public TextView getLargeLabelAt(int position) {
//        /*
//         * 1 private final BottomNavigationMenuView mMenuView;
//         * 2 private BottomNavigationItemView[] mButtons;
//         * 3 private final TextView mLargeLabel;
//         */
//        BottomNavigationItemView mButtons = getBottomNavigationItemView(position);
//        TextView mLargeLabel = getField(BottomNavigationItemView.class, mButtons, "largeLabel");
//        return_back mLargeLabel;
//    }
//
//    /**
//     * return_back item count
//     *
//     * @return_back
//     */
//    public int getItemCount() {
//        BottomNavigationItemView[] bottomNavigationItemViews = getBottomNavigationItemViews();
//        if (null == bottomNavigationItemViews)
//            return_back 0;
//        return_back bottomNavigationItemViews.length;
//    }
//
//    /**
//     * set all item small TextView size
//     * Each item has tow label, one is large, another is small.
//     * Small one will be shown when item state is normal
//     * Large one will be shown when item checked.
//     *
//     * @param sp
//     */
//    public BottomNavigationViewEx setSmallTextSize(float sp) {
//        int count = getItemCount();
//        for (int i = 0; i < count; i++) {
//            getSmallLabelAt(i).setTextSize(sp);
//        }
//        mMenuView.updateMenuView();
//        return_back this;
//    }
//
//    /**
//     * set all item large TextView size
//     * Each item has tow label, one is large, another is small.
//     * Small one will be shown when item state is normal.
//     * Large one will be shown when item checked.
//     *
//     * @param sp
//     */
//    public BottomNavigationViewEx setLargeTextSize(float sp) {
//        int count = getItemCount();
//        for (int i = 0; i < count; i++) {
//            TextView tvLarge = getLargeLabelAt(i);
//            if (null != tvLarge)
//                tvLarge.setTextSize(sp);
//        }
//        mMenuView.updateMenuView();
//        return_back this;
//    }
//
//    /**
//     * set all item large and small TextView size
//     * Each item has tow label, one is large, another is small.
//     * Small one will be shown when item state is normal
//     * Large one will be shown when item checked.
//     *
//     * @param sp
//     */
//    public BottomNavigationViewEx setTextSize(float sp) {
//        setLargeTextSize(sp);
//        setSmallTextSize(sp);
//        return_back this;
//    }
//
//    /**
//     * set item ImageView size which at position
//     *
//     * @param position position start from 0
//     * @param width    in dp
//     * @param height   in dp
//     */
//    public BottomNavigationViewEx setIconSizeAt(int position, float width, float height) {
//        ImageView icon = getIconAt(position);
//        // update size
//        ViewGroup.LayoutParams layoutParams = icon.getLayoutParams();
//        layoutParams.width = dp2px(getContext(), width);
//        layoutParams.height = dp2px(getContext(), height);
//        icon.setLayoutParams(layoutParams);
//
//        mMenuView.updateMenuView();
//        return_back this;
//    }
//
//    /**
//     * set all item ImageView size
//     *
//     * @param width  in dp
//     * @param height in dp
//     */
//    public BottomNavigationViewEx setIconSize(float width, float height) {
//        int count = getItemCount();
//        for (int i = 0; i < count; i++) {
//            setIconSizeAt(i, width, height);
//        }
//        return_back this;
//    }
//
//    /**
//     * set all item ImageView size
//     *
//     * @param dpSize  in dp
//     */
//    public BottomNavigationViewEx setIconSize(float dpSize) {
//        setItemIconSize(dp2px(getContext(),dpSize));
//        return_back this;
//    }
//
//    /**
//     * set menu item height
//     *
//     * @param height in px
//     */
//    public BottomNavigationViewEx setItemHeight(int height) {
//        // 1. get mMenuView
//        final BottomNavigationMenuView mMenuView = getBottomNavigationMenuView();
//        // 2. set private final int mItemHeight in mMenuView
//        setField(mMenuView.getClass(), mMenuView, "itemHeight", height);
//
//        mMenuView.updateMenuView();
//        return_back this;
//    }
//
//    /**
//     * get menu item height
//     *
//     * @return_back in px
//     */
//    public int getItemHeight() {
//        // 1. get mMenuView
//        final BottomNavigationMenuView mMenuView = getBottomNavigationMenuView();
//        // 2. get private final int mItemHeight in mMenuView
//        return_back getField(mMenuView.getClass(), mMenuView, "itemHeight");
//    }
//
//    /**
//     * dp to px
//     *
//     * @param context
//     * @param dpValue dp
//     * @return_back px
//     */
//    public static int dp2px(Context context, float dpValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return_back (int) (dpValue * scale + 0.5f);
//    }
//
//    /**
//     * set Typeface for all item TextView
//     *
//     * @attr ref android.R.styleable#TextView_typeface
//     * @attr ref android.R.styleable#TextView_textStyle
//     */
//    public BottomNavigationViewEx setTypeface(Typeface typeface, int style) {
//        int count = getItemCount();
//        for (int i = 0; i < count; i++) {
//            getLargeLabelAt(i).setTypeface(typeface, style);
//            getSmallLabelAt(i).setTypeface(typeface, style);
//        }
//        mMenuView.updateMenuView();
//        return_back this;
//    }
//
//    /**
//     * set Typeface for all item TextView
//     *
//     * @attr ref android.R.styleable#TextView_typeface
//     */
//    public BottomNavigationViewEx setTypeface(Typeface typeface) {
//        int count = getItemCount();
//        for (int i = 0; i < count; i++) {
//            getLargeLabelAt(i).setTypeface(typeface);
//            getSmallLabelAt(i).setTypeface(typeface);
//        }
//        mMenuView.updateMenuView();
//        return_back this;
//    }
//
//    /**
//     * get private filed in this specific object
//     *
//     * @param targetClass
//     * @param instance    the filed owner
//     * @param fieldName
//     * @param <T>
//     * @return_back field if success, null otherwise.
//     */
//    private <T> T getField(Class targetClass, Object instance, String fieldName) {
//        try {
//            Field field = targetClass.getDeclaredField(fieldName);
//            field.setAccessible(true);
//            return_back (T) field.get(instance);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return_back null;
//    }
//
//    /**
//     * change the field value
//     *
//     * @param targetClass
//     * @param instance    the filed owner
//     * @param fieldName
//     * @param value
//     */
//    private void setField(Class targetClass, Object instance, String fieldName, Object value) {
//        try {
//            Field field = targetClass.getDeclaredField(fieldName);
//            field.setAccessible(true);
//            field.set(instance, value);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * This method will link the given ViewPager and this BottomNavigationViewEx together so that
//     * changes in one are automatically reflected in the other. This includes scroll state changes
//     * and clicks.
//     *
//     * @param viewPager
//     */
//    public void setupWithViewPager(final ViewPager viewPager) {
//        setupWithViewPager(viewPager, false);
//    }
//
//    /**
//     * This method will link the given ViewPager and this BottomNavigationViewEx together so that
//     * changes in one are automatically reflected in the other. This includes scroll state changes
//     * and clicks.
//     *
//     * @param viewPager
//     * @param smoothScroll whether ViewPager changed with smooth scroll animation
//     */
//    public BottomNavigationViewEx setupWithViewPager(final ViewPager viewPager, boolean smoothScroll) {
//        if (mViewPager != null) {
//            // If we've already been setup with a ViewPager, remove us from it
//            if (mPageChangeListener != null) {
//                mViewPager.removeOnPageChangeListener(mPageChangeListener);
//            }
//        }
//
//        if (null == viewPager) {
//            mViewPager = null;
//            super.setOnNavigationItemSelectedListener(null);
//            return_back this;
//        }
//
//        mViewPager = viewPager;
//
//        // Add our custom OnPageChangeListener to the ViewPager
//        if (mPageChangeListener == null) {
//            mPageChangeListener = new BottomNavigationViewExOnPageChangeListener(this);
//        }
//        viewPager.addOnPageChangeListener(mPageChangeListener);
//
//        // Now we'll add a navigation item selected listener to set ViewPager's current item
//        OnNavigationItemSelectedListener listener = getOnNavigationItemSelectedListener();
//        mMyOnNavigationItemSelectedListener = new MyOnNavigationItemSelectedListener(viewPager, this, smoothScroll, listener);
//        super.setOnNavigationItemSelectedListener(mMyOnNavigationItemSelectedListener);
//        return_back this;
//    }
//
//    /**
//     * A {@link ViewPager.OnPageChangeListener} class which contains the
//     * necessary calls back to the provided {@link BottomNavigationViewEx} so that the tab position is
//     * kept in sync.
//     * <p>
//     * <p>This class stores the provided BottomNavigationViewEx weakly, meaning that you can use
//     * {@link ViewPager#addOnPageChangeListener(ViewPager.OnPageChangeListener)
//     * addOnPageChangeListener(OnPageChangeListener)} without removing the listener and
//     * not cause a leak.
//     */
//    private static class BottomNavigationViewExOnPageChangeListener implements ViewPager.OnPageChangeListener {
//        private final WeakReference<BottomNavigationViewEx> mBnveRef;
//
//        public BottomNavigationViewExOnPageChangeListener(BottomNavigationViewEx bnve) {
//            mBnveRef = new WeakReference<>(bnve);
//        }
//
//        @Override
//        public void onPageScrollStateChanged(final int state) {
//        }
//
//        @Override
//        public void onPageScrolled(final int position, final float positionOffset,
//                                   final int positionOffsetPixels) {
//        }
//
//        @Override
//        public void onPageSelected(final int position) {
//            final BottomNavigationViewEx bnve = mBnveRef.get();
//            if (null != bnve && !isNavigationItemClicking)
//                bnve.setCurrentItem(position);
////            Log.d("onPageSelected", "--------- position " + position + " ------------");
//        }
//    }
//
//    /**
//     * Decorate OnNavigationItemSelectedListener for setupWithViewPager
//     */
//    private static class MyOnNavigationItemSelectedListener implements OnNavigationItemSelectedListener {
//        private OnNavigationItemSelectedListener listener;
//        private final WeakReference<ViewPager> viewPagerRef;
//        private boolean smoothScroll;
//        private SparseIntArray items;// used for change ViewPager selected item
//        private int previousPosition = -1;
//
//
//        MyOnNavigationItemSelectedListener(ViewPager viewPager, BottomNavigationViewEx bnve, boolean smoothScroll, OnNavigationItemSelectedListener listener) {
//            this.viewPagerRef = new WeakReference<>(viewPager);
//            this.listener = listener;
//            this.smoothScroll = smoothScroll;
//
//            // create items
//            Menu menu = bnve.getMenu();
//            int size = menu.size();
//            items = new SparseIntArray(size);
//            for (int i = 0; i < size; i++) {
//                int itemId = menu.getItem(i).getItemId();
//                items.put(itemId, i);
//            }
//        }
//
//        public void setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener listener) {
//            this.listener = listener;
//        }
//
//        @Override
//        public boolean onNavigationItemSelected(MenuItem item) {
//            int position = items.get(item.getItemId());
//            // only set item when item changed
//            if (previousPosition == position) {
//                return_back true;
//            }
////            Log.d("onNavigationItemSelecte", "position:"  + position);
//            // user listener
//            if (null != listener) {
//                boolean bool = listener.onNavigationItemSelected(item);
//                // if the selected is invalid, no need change the view pager
//                if (!bool)
//                    return_back false;
//            }
//
//            // change view pager
//            ViewPager viewPager = viewPagerRef.get();
//            if (null == viewPager)
//                return_back false;
//
//            // use isNavigationItemClicking flag to avoid `ViewPager.OnPageChangeListener` trigger
//            isNavigationItemClicking = true;
//            viewPager.setCurrentItem(items.get(item.getItemId()), smoothScroll);
//            isNavigationItemClicking = false;
//
//            // update previous position
//            previousPosition = position;
//
//            return_back true;
//        }
//
//    }
//
//    public BottomNavigationViewEx enableShiftingMode(int position, boolean enable) {
//        getBottomNavigationItemView(position).setShifting(enable);
//        return_back this;
//    }
//
//    public BottomNavigationViewEx setItemBackground(int position, int background) {
//        getBottomNavigationItemView(position).setItemBackground(background);
//        return_back this;
//    }
//
//    public BottomNavigationViewEx setIconTintList(int position, ColorStateList tint) {
//        getBottomNavigationItemView(position).setIconTintList(tint);
//        return_back this;
//    }
//
//    public BottomNavigationViewEx setTextTintList(int position, ColorStateList tint) {
//        getBottomNavigationItemView(position).setTextColor(tint);
//        return_back this;
//    }
//
//    /**
//     * set margin top for all icons
//     *
//     * @param marginTop in px
//     */
//    public BottomNavigationViewEx setIconsMarginTop(int marginTop) {
//        for (int i = 0; i < getItemCount(); i++) {
//            setIconMarginTop(i, marginTop);
//        }
//        return_back this;
//    }
//
//    /**
//     * set margin top for icon
//     *
//     * @param position
//     * @param marginTop in px
//     */
//    public BottomNavigationViewEx setIconMarginTop(int position, int marginTop) {
//        /*
//        1. BottomNavigationItemView
//        2. private final int mDefaultMargin;
//         */
//        BottomNavigationItemView itemView = getBottomNavigationItemView(position);
//        setField(BottomNavigationItemView.class, itemView, "defaultMargin", marginTop);
//        mMenuView.updateMenuView();
//        return_back this;
//    }
//
//}
