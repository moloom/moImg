package com.moloom.img.api.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: moloom
 * @date: 2024-10-22 17:41
 * @description:
 */
@Data
public class FileUploadVo {
    private String fileName;    //如果是在页面上传文件，页面会获取文件名，若是api则需自己获取
    private String fileExtension; //如果是在页面上传文件，页面会获取后缀，若是api则需自己获取
    private String bucketName;  //存储的bucket名
    private MultipartFile multipartFile;
    private String token;
    private ImgActionsVo actionsVo;
}
