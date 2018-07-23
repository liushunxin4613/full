package com.leo.core.iapi.main;

public interface INewApi {

    /**
     * 数据api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B viewApi();

    /**
     * new数据api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newViewApi();

    /**
     * 数据api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B dataApi();

    /**
     * new数据api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newDataApi();

    /**
     * 显示api
     *
     * @return api
     */
    <B> B showApi();

    /**
     * new显示api
     *
     * @return api
     */
    <B> B newShowApi();

    /**
     * http api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B httpApi();

    /**
     * new http api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newHttpApi();

    /**
     * 解析 api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B decodeApi();

    /**
     * new 解析 api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newDecodeApi();

    /**
     * md5 api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B md5Api();

    /**
     * new md5 api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newMd5Api();

    /**
     * object api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B objectApi();

    /**
     * new object api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newObjectApi();

    /**
     * action api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B actionApi();

    /**
     * new action api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newActionApi();

    /**
     * user api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B userApi();

    /**
     * new user api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newUserApi();

    /**
     * load image api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B loadImageApi();

    /**
     * new load image api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newLoadImageApi();

    /**
     * config api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B configApi();

    /**
     * new config api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newConfigApi();

    /**
     * data type api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B dataTypeApi();

    /**
     * new data type api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newDataTypeApi();

    /**
     * merge api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B mergeApi();

    /**
     * new merge api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newMergeApi();

    /**
     * subjoinApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B subjoinApi();

    /**
     * new subjoinApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newSubjoinApi();

    /**
     * galleryApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B galleryApi();

    /**
     * new galleryApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newGalleryApi();

    /**
     * fileApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B fileApi();

    /**
     * new fileApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newFileApi();

    /**
     * api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B api();

    /**
     * new fileApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newApi();

    /**
     * parseApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B parseApi();

    /**
     * new parseApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newParseApi();

    /**
     * 切换parseApi
     * @param <B> 泛型
     * @return B
     */
    <B> B switchNPApi();

    /**
     * helper api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B helper();

    /**
     * new helper api
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newHelper();

    /**
     * cameraApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B cameraApi();

    /**
     * new cameraApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newCameraApi();

    /**
     * activityLifecycleApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B activityLifecycleApi();

    /**
     * new activityLifecycleApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newActivityLifecycleApi();

    /**
     * vsApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B vsApi();

    /**
     * new vsApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newVsApi();

    /**
     * vosApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B vosApi();

    /**
     * new vosApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newVosApi();

    /**
     * dirApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B dirApi();

    /**
     * new dirApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newDirApi();

    /**
     * routeApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B routeApi();

    /**
     * new routeApi
     *
     * @param <B> 泛型
     * @return api
     */
    <B> B newRouteApi();

}