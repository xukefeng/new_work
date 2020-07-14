package com.mingzheng.lwzzr.view.widget

import android.annotation.SuppressLint
import android.content.Context
import android.database.DataSetObserver
import android.os.Handler
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.os.ParcelableCompat
import androidx.core.os.ParcelableCompatCreatorCallbacks
import androidx.viewpager.widget.PagerAdapter
import java.util.ArrayList

class TabPager :
    ViewGroup {
    private var mCurItem: Int = 0
    private val mItems = ArrayList<ItemInfo>()
    private var mCurTempItem: Int = 0
    private var mRestoredCurItem = -1
    private var mRestoredAdapterState: Parcelable? = null
    private var mRestoredClassLoader: ClassLoader? = null
    private var mObserver: PagerObserver? = null
    private var mOnTabChangeListener: OnTabChangeListener? = null

    constructor(context: Context?) : super(context) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    companion object {
        private val DEBUG = false
        var mAdapter: PagerAdapter? = null

        class ItemInfo {
            var `object`: Any? = null
            var position: Int = 0
        }
    }

    fun setAdapter(adapter: PagerAdapter) {
        if (mAdapter != null && mObserver != null) {
            Log.w("result", "---------------------------2")
            mAdapter!!.unregisterDataSetObserver(mObserver!!)
            mAdapter!!.startUpdate(this)
            for (i in mItems.indices) {
                val ii = mItems.get(i)
                ii.`object`?.let { mAdapter!!.destroyItem(this, ii.position, it) }
            }
            mAdapter!!.finishUpdate(this)
            mItems.clear()
            removeAllViews()
            mCurItem = 0
            mCurTempItem = 0
        }

        mAdapter = adapter

        if (mAdapter != null) {
            Log.w("result", "---------------------------3")

            if (mObserver == null) {
                Log.w("result", "---------------------------4")
                mObserver = PagerObserver()
            }
            mAdapter!!.registerDataSetObserver(mObserver!!)
            if (mRestoredCurItem >= 0) {
                Log.w("result", "---------------------------5")
                mAdapter!!.restoreState(mRestoredAdapterState, mRestoredClassLoader)
                mCurTempItem = mRestoredCurItem
                mRestoredCurItem = -1
                mRestoredAdapterState = null
                mRestoredClassLoader = null
            }
            requestLayout()
        }
    }

    fun getAdapter(): PagerAdapter {
        return mAdapter!!
    }

    fun setCurrentItem(item: Int) {
        var item = item

        Log.w("result", "---------------------------6---" + item + "---" + mAdapter!!.getCount())
        if (mAdapter == null) {
            return
        }
        Log.w("result", "---------------------------7---" + item + "---" + mAdapter!!.getCount())

        if (item >= mAdapter!!.getCount()) {

            item = mAdapter!!.getCount() - 1
        }
        if (item < 0) {
            item = 0
        }
        if (mCurItem == item) {
            return
        }
        mCurTempItem = item
        requestLayout()
    }

    fun getCurrentItem(): Int {
        return mCurItem
    }

    fun setOnTabChangeListener(listener: OnTabChangeListener) {
        mOnTabChangeListener = listener
    }

    internal fun dataSetChanged() {
        if (mAdapter == null)
            return
        // This method only gets called if our observer is attached, so mAdapter
        // is non-null.

        var newCurrItem = -1

        var isUpdating = false
        var i = 0
        while (i < mItems.size) {
            val ii = mItems[i]
            val newPos = ii.`object`?.let { mAdapter!!.getItemPosition(it) }

            if (newPos == PagerAdapter.POSITION_UNCHANGED) {
                i++
                continue
            }

            if (newPos == PagerAdapter.POSITION_NONE) {
                mItems.removeAt(i)
                i--

                if (!isUpdating) {
                    mAdapter!!.startUpdate(this)
                    isUpdating = true
                }

                mAdapter!!.destroyItem(this, ii.position, ii.`object`!!)

                if (mCurItem == ii.position) {
                    // Keep the current item in the valid range
                    newCurrItem = Math.max(0, Math.min(mCurItem, mAdapter!!.getCount() - 1))
                }
                i++
                continue
            }

            if (ii.position != newPos) {
                if (ii.position == mCurItem) {
                    // Our current item changed position. Follow it.
                    newCurrItem = newPos!!
                }

                ii.position = newPos!!
            }
            i++
        }

        if (isUpdating) {
            mAdapter!!.finishUpdate(this)
        }

        if (newCurrItem >= 0) {
            mCurTempItem = newCurrItem
            requestLayout()
        }
    }

    internal fun addNewItem(position: Int): ItemInfo {
        val ii = ItemInfo()
        ii.position = position
        ii.`object` = mAdapter!!.instantiateItem(this, position)
        mItems.add(ii)
        return ii
    }

    internal fun populate(): ItemInfo? {
        mAdapter!!.startUpdate(this)

        var curItem: ItemInfo? = null
        for (i in mItems.indices) {
            val ii = mItems[i]
            if (ii.position == mCurTempItem) {
                curItem = ii
                break
            }
        }

        if (curItem == null) {
            if (mCurTempItem < mAdapter!!.getCount())
                curItem = addNewItem(mCurTempItem)
            else
                mCurTempItem = 0
        }

        curItem?.`object`?.let { mAdapter!!.setPrimaryItem(this, mCurTempItem, it) }

        mAdapter!!.finishUpdate(this)

        return curItem
    }

    /**
     * This is the persistent state that is saved by ViewPager. Only needed if
     * you are creating a sublass of ViewPager that must save its own state, in
     * which case it should implement a subclass of this which contains that
     * state.
     */
    class SavedState : View.BaseSavedState {
        internal var position: Int = 0
        internal var adapterState: Parcelable? = null
        internal var loader: ClassLoader?=null

        constructor(superState: Parcelable) : super(superState) {

        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(position)
            out.writeParcelable(adapterState, flags)
        }

        override fun toString(): String {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + position + "}"
        }

        internal constructor(`in`: Parcel, loader: ClassLoader?) : super(`in`) {
            var loader = loader
            if (loader == null) {
                loader = javaClass.classLoader
            }
            position = `in`.readInt()
            adapterState = `in`.readParcelable(loader)
            if (loader != null) {
                this.loader = loader
            }
        }

        companion object {

            @SuppressLint("ParcelCreator")
            val CREATOR: Parcelable.Creator<SavedState> = ParcelableCompat.newCreator(object :
                ParcelableCompatCreatorCallbacks<SavedState> {
                override fun createFromParcel(`in`: Parcel, loader: ClassLoader): SavedState {
                    return SavedState(`in`, loader)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            })
        }
    }

    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState!!)
        ss.position = mCurItem
        if (mAdapter != null) {
            ss.adapterState = mAdapter!!.saveState()
        }
        return ss
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }

        super.onRestoreInstanceState(state.superState)

        if (mAdapter != null) {
            mAdapter!!.restoreState(state.adapterState, state.loader)
            mCurTempItem = state.position
            requestLayout()
        } else {
            mRestoredCurItem = state.position
            mRestoredAdapterState = state.adapterState
            mRestoredClassLoader = state.loader
        }
    }

    internal fun infoForChild(child: View, ii: ItemInfo): Boolean {
        return mAdapter!!.isViewFromObject(child, ii.`object`!!)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // For simple implementation, or internal size is always 0.
        // We depend on the container to specify the layout size of
        // our view. We can't really know what it is since we will be
        // adding and removing different arbitrary views and do not
        // want the layout to change as this happens.
        setMeasuredDimension(
            View.getDefaultSize(0, widthMeasureSpec),
            View.getDefaultSize(0, heightMeasureSpec)
        )

        // Children are just made to fill our space.
        val childWidthSize = measuredWidth - paddingLeft - paddingRight
        val childHeightSize = measuredHeight - paddingTop - paddingBottom
        val childWidthMeasureSpec =
            View.MeasureSpec.makeMeasureSpec(childWidthSize, View.MeasureSpec.EXACTLY)
        val childHeightMeasureSpec =
            View.MeasureSpec.makeMeasureSpec(childHeightSize, View.MeasureSpec.EXACTLY)

        var info: ItemInfo? = null
        if (mAdapter != null) {
            // Make sure we have created all fragments that we need to have
            // shown.
            info = populate()
        }

        // Page views next.
        val size = childCount
        for (i in 0 until size) {
            val child = getChildAt(i)
            if (info != null && infoForChild(child, info)) {
                child.visibility = View.VISIBLE
                //                if (DEBUG)
                //                    LogManager.logV(TabPager.class, "Measuring #" + i + " " + child + ": " + childWidthMeasureSpec);
                child.measure(childWidthMeasureSpec, childHeightMeasureSpec)
            } else {
                child.visibility = View.GONE
            }
        }
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val size = childCount
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        for (i in 0 until size) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                child.layout(
                    paddingLeft,
                    paddingTop,
                    paddingLeft + child.measuredWidth,
                    paddingTop + child.measuredHeight
                )
            }
        }
        if (mCurTempItem != mCurItem) {
            mCurItem = mCurTempItem
            if (mOnTabChangeListener != null) {
                // 在当前layout过程中onTabSelected回调方法如果导致了requestLayout()将无法执行，故post处理
                Handler().post { mOnTabChangeListener!!.onTabSelected(mCurItem) }
            }
        }
    }

    private inner class PagerObserver : DataSetObserver() {
        override fun onChanged() {
            dataSetChanged()
        }

        override fun onInvalidated() {
            dataSetChanged()
        }
    }


    interface OnTabChangeListener {

        fun onTabSelected(position: Int)

    }

}