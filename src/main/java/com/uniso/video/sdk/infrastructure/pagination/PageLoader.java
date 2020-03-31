package com.uniso.video.sdk.infrastructure.pagination;

import com.uniso.video.sdk.domain.exception.ResponseException;
import com.uniso.video.sdk.domain.pagination.Page;
import com.uniso.video.sdk.domain.pagination.PageQuery;


public interface PageLoader<T> {
    Page<T> load(PageQuery pageQuery) throws ResponseException;
}
