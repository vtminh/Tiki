package vn.tiki.android.androidhometest.mvp.presenter;

import android.support.annotation.NonNull;

import java.util.List;
import vn.tiki.android.androidhometest.mvp.model.remote.api.response.Deal;
import vn.tiki.android.androidhometest.mvp.view.BaseView;


public class DealContract {

    public interface View extends BaseView<Presenter> {
        void showDealList(List<Deal> deals);
        boolean isActive();
        void showDealDetailUi(Deal deal);


    }

    public interface Presenter extends BasePresenter {
        void loadDealList(boolean forceUpdate);
        void openDealDetail(@NonNull Deal deal);
    }


}
