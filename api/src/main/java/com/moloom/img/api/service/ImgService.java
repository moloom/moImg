package com.moloom.img.api.service;

import com.moloom.img.api.entity.ImgEntity;
import com.moloom.img.api.vo.DownloadVO;
import com.moloom.img.api.to.R;
import com.moloom.img.api.vo.UploadVo;
import org.springframework.http.ResponseEntity;

/**
 * @author: moloom
 * @date: 2024-10-22 18:26
 * @description:
 */
public interface ImgService {


    /**
     * @param vo info about upload file
     * @return
     * @author moloom
     * @date 2024-10-22 20:19:06
     * @description handle upload file
     */
    public R imghandler(UploadVo vo);

    /**
     * @param img
     * @return
     * @author moloom
     * @date 2024-10-22 18:41:09
     * @description 保存图片
     */
    public R saveImg(ImgEntity img);

    /**
     * @param vo vo
     * @return
     * @author moloom
     * @date 2024-11-04 00:34:04
     * @description return a file from the server
     */
    public ResponseEntity<Object> download(DownloadVO vo);

    /**
     * @param url
     * @return the result of deletion
     * @author moloom
     * @date 2024-12-11 21:26:51
     * @description delete img by url
     */
    public R deleteImgByUrl(String url);

    /**
     * @param id of img
     * @return the result of deletion
     * @author moloom
     * @date 2024-12-11 21:24:59
     * @description delete img by id
     */
    public R deleteImgById(Long id);

    /**
     * @param url of img
     * @return <code>true</code> if the img exists in Redis or is found in the DB and cached into Redis;</br>
     * <code>false</code> if the img is not found in either Redis or the DB.
     * @author moloom
     * @date 2024-12-12 00:57:24
     * @description check if the img exists in Redis or the DB.</br>
     * if it exists in the DB but not in Redis, cache it in Redis.
     */
    public boolean checkAndCacheImg(String url);


}
