package com.uniso.video.sdk.domain.account;

import com.uniso.video.sdk.domain.exception.ResponseException;

public interface AccountClient {
    Account get() throws ResponseException;
}
