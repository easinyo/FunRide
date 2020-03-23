package com.dubinostech.rideshareapp.presenter;

import com.dubinostech.rideshareapp.model.PostFragmentModel.PostModelInterface;
import com.dubinostech.rideshareapp.presenter.interfaces.PostPresenterInterface;
import com.dubinostech.rideshareapp.repository.Api.Responses.PostResponse;
import com.dubinostech.rideshareapp.repository.Data.PostData;
import com.dubinostech.rideshareapp.repository.ErrorHandler.ErrorCode;
import com.dubinostech.rideshareapp.ui.view.PostView;

import org.jetbrains.annotations.NotNull;

/**
 * A Presenter Class that will holds and manage Posting a Ride
 */
public class PostPresenter implements PostPresenterInterface {

    PostView postView;
    PostModelInterface postModelInterface;

    public PostPresenter(PostView postView, PostModelInterface postModelInterface) {
        this.postView = postView;
        this.postModelInterface = postModelInterface;
    }

    /**
     * callPostRide is a helper method registers a post.
     * @param postData
     *            Object user containing .....
     * Then it notifies the post view
     */
    @Override
    public void callPostRide(PostData postData) {
        postView.showLoading();
        postModelInterface.postRide(postData, new PostModelInterface.IOnPostFinishedListener() {
            @Override
            public void getPostData(@NotNull PostResponse post) {
                postView.hideLoading();
                if (post != null) {
                    postView.postSuccess(post);
                } else {
                    postView.postFailure(ErrorCode.POST_FAILED);
                }
            }

            @Override
            public void errorMsg(String errorMsg) {
                postView.hideLoading();
                postView.postFailure(errorMsg);
            }

        });
    }
}
