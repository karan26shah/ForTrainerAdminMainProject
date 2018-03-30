package in.fortrainer.admin.utilities;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.adapters.MasterAdapter;

import static in.fortrainer.admin.utilities.CommonRecyclerItem.ItemType.LOADING;


/**
 * Created by hbb20 on 20/7/16.
 */
public class CommonRecyclerScreen {

    private static final String TAG = "CRS";
    public static int PER_PAGE = 10;
    public int totalEntries;
    public int loadedPages;
    public List<CommonRecyclerItem> recyclerItems;
    public MasterAdapter recyclerAdapter;
    public boolean lockOnLoad;
    public boolean allEntriesFetched; //this might not used for older implementations
    RelativeLayout relative_recyclerHolder;
    @Nullable
    SwipeRefreshLayout swipeRefreshLayout;
    Context context;
    ScreenMode activeScreenMode = null;
    private android.support.v7.widget.RecyclerView recyclerView;
    private ImageView imageView_retryButton;
    private LinearLayout llProgressHolder;

    public CommonRecyclerScreen(Context context, View view, MasterAdapter adapter) {
        this.context = context;
        this.recyclerItems = new ArrayList<>();
        this.lockOnLoad = false;
        bindViews(view);
        resetPagination(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setColorSchemeResources(R.color.iw_blue_gray_bg, R.color.iw_background_alive, R.color.iw_very_dark_gray);
        }
    }

    public static CommonRecyclerScreen setupWithActivity(Activity activity) {
        return new CommonRecyclerScreen(activity, activity.findViewById(R.id.common_recycler_screen), null);
    }

    public static CommonRecyclerScreen setupWithFragment(Fragment fragment) {
        return new CommonRecyclerScreen(fragment.getContext(), fragment.getView(), null);
    }

    public void resetPagination(MasterAdapter adapter) {
        recyclerAdapter = adapter;
        recyclerItems = new ArrayList<>();
        //recyclerView.getRecycledViewPool().clear();
        if (recyclerAdapter != null) {
            recyclerView.setAdapter(recyclerAdapter);
            recyclerAdapter.setRecyclerItems(recyclerItems);
            recyclerAdapter.notifyDataSetChanged();
        }
        totalEntries = -1;
        loadedPages = 0;
        lockOnLoad = false;
        allEntriesFetched = false;
        setScreen(ScreenMode.LOADING);
    }

    private void bindViews(View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        imageView_retryButton = rootView.findViewById(R.id.imageview_retry);
        llProgressHolder = rootView.findViewById(R.id.ll_iw_progress_holder);
        relative_recyclerHolder = rootView.findViewById(R.id.common_recycler_screen);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
    }

    public void setScreen(ScreenMode screenMode) {
        try {
            if (activeScreenMode == null || activeScreenMode != screenMode) {
                hideAll();
                switch (screenMode) {
                    case LOADING:
                        Log.d(TAG, "setting loading");
                        if (swipeRefreshLayout == null || !swipeRefreshLayout.isRefreshing())
                            llProgressHolder.setVisibility(View.VISIBLE);
                          //  AdminHelper.startIWProgressRing(llProgressHolder);
                        break;
                    case RETRY:
                        Log.d(TAG, "setting retry");
                        releaseLoadLock();
                        imageView_retryButton.setVisibility(View.VISIBLE);
                        break;
                    case DONE:
                        Log.d(TAG, "setting DONE");
                        if (swipeRefreshLayout != null) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        recyclerView.setVisibility(View.VISIBLE);
                        llProgressHolder.setVisibility(View.GONE);

                        break;
                }
            } else {
                Log.d(TAG, "Ignoring mode: " + screenMode);
            }
            activeScreenMode = screenMode;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRetryClickListener(View.OnClickListener clickListener) {
        imageView_retryButton.setOnClickListener(clickListener);
    }

    public void setSwipeListener(SwipeRefreshLayout.OnRefreshListener swipeRefreshListener) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(swipeRefreshListener);
        }
    }

    public void hideAll() {
        recyclerView.setVisibility(View.GONE);
      //  AdminHelper.stopIWProcessRing(llProgressHolder);
        imageView_retryButton.setVisibility(View.GONE);
    }

    public void attachAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        recyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    public boolean isSwipeRefreshing() {
        if (swipeRefreshLayout != null) {
            return swipeRefreshLayout.isRefreshing();
        } else {
            return false;
        }
    }

    public void setSwipeRefreshing(boolean b) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(b);
        }
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void lockOnLoad() {
        Log.d(TAG, "Putting Lock On Load");
        lockOnLoad = true;
    }

    public void releaseLoadLock() {
        Log.d(TAG, "Releasing Lock From Load");
        lockOnLoad = false;
    }

    public boolean isLockOnLoad() {
        return lockOnLoad;
    }

    public void setLockOnLoad(boolean lockOnLoad) {
        this.lockOnLoad = lockOnLoad;
    }

    public boolean areAllEntriesFetched() {
        return allEntriesFetched;
    }

    public void setAllEntriesFetched(boolean allEntriesFetched) {
        this.allEntriesFetched = allEntriesFetched;
    }

    /**
     * adds loading more at the end of recyclerItemList.
     */
    public void addLoadingMoreAtEnd() {
        try {
            Log.d(TAG, "Adding LOADING at end");
            if (recyclerItems != null && (recyclerItems.size() == 0 || recyclerItems.get(recyclerItems.size() - 1).getItemType() != LOADING)) {
                recyclerItems.add(new CommonRecyclerItem(LOADING, null));

                if (recyclerAdapter != null) {
                    recyclerAdapter.notifyItemInserted(recyclerItems.size() - 1);
                }
            }
        } catch (ArrayIndexOutOfBoundsException arrayOutOfBound) {
            arrayOutOfBound.printStackTrace();
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * removes loading more from recyclerItemList.
     */
    public void removeLoadingFromEnd() {
        if (recyclerAdapter != null && recyclerAdapter.getRecyclerItems() != recyclerItems) {
            Log.d(TAG, "Adapter source is not proper");
            return;
        }
        if (recyclerItems != null && recyclerItems.size() > 0) {
            try {
                if (recyclerItems.get(recyclerItems.size() - 1).getItemType() == LOADING) {
                    recyclerItems.remove(recyclerItems.size() - 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (recyclerAdapter != null && recyclerAdapter.getRecyclerItems() == recyclerItems) {
                try {
                    recyclerAdapter.notifyItemRemoved(recyclerItems.size());
                } catch (Exception ex) {
                    //happened once when recycler view was computing and this removed item
                }
            }
        }
    }

    public void increaseLoadedPageValue() {
        loadedPages++;
    }

    public List<CommonRecyclerItem> getRecyclerItems() {
        return recyclerItems;
    }

    public int getTotalEntries() {
        return totalEntries;
    }

    public void setTotalEntries(int totalEntries) {
        this.totalEntries = totalEntries;
    }

    public int getLoadedPagesCount() {
        return loadedPages;
    }

    public enum ScreenMode {LOADING, RETRY, DONE}

}
