package com.moloom.img.api.vo;

import com.moloom.img.api.entity.TokensEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: moloom
 * @date: 2024-10-22 17:41
 * @description:
 */
@Data
@Builder
@Accessors(chain = true)
public class UploadVo implements Cloneable {
    private String fileName;    //文件名，不带后缀的,如果是在页面上传文件，页面会获取文件名，若是api则需自己获取
    private String fileExtension; //文件的拓展名，如果是在页面上传文件，页面会获取后缀，若是api则需自己获取
    private String contentType;     //文件类型；例：application/x-gtar、video/mp4
    private String fileStoragePath;  //存储路径
    private String bucketName;  //存储的bucket名
    private MultipartFile multipartFile;
    private TokensEntity token;
    private ImgActionsVo actionsVo;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
