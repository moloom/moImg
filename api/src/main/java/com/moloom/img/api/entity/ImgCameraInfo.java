package com.moloom.img.api.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.tika.metadata.Metadata;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.time.DateUtils.parseDate;

/**
 * @author: moloom
 * @date: 2024-11-09 19:24
 * @description: 摄像头信息
 */

@Data
@Accessors(chain = true)
@Builder
public class ImgCameraInfo {
    private Long id; // 主键
    private String imageDescription;    //图片描述
    private String make;        //摄像头品牌
    private String model;       //摄像头型号
    private String software;    //固件 Firmware 版本或者编辑软件
    private String orientation;     //旋转角度
    private Double xResolution;     //水平分辨率
    private Double yResolution;     //垂直分辨率
    private String resolutionUnit;  //分辨率单位
    private String yCbCrPositioning;        //色相位置
    private String componentsConfiguration;     //颜色配置
    private String compressedBitsPerPixel;      //平均压缩率
    private String colorSpace;      //颜色空间
    private String compression;     //压缩方式

    private String exifVersion;     //EXIF版本
    private String shutterSpeed;    //快门速度
    private Integer isoSpeedRatings;    //ISO曝光度
    private String exposureTime;    //曝光时间，快门速度
    private String exposureMode;     //曝光模式
    private String exposureBiasValue;       //曝光偏差
    private Float fNumber;     //光圈系数
    private String maxApertureValue;        //最大光圈
    private String meteringMode;        //测光模式
    private String lightSource;     //光源，白平衡设置
    private Float Brightness;       //亮度
    private String whiteBalance;    //白平衡
    private String focalLength;     //镜头物理焦距
    private String focalLength35;   //35mm 等效焦距
    private String flash;       //闪光灯描述
    private Boolean flashValue;      //是否开启闪光灯
    private String flashPixVersion;     //FlashPix版本
    private String sceneCaptureType;    //拍摄类型
    private String LensMake;        //镜头生产厂商
    private String LensModel;       //镜头型号
    private Timestamp dateTimeOriginal;     //拍摄时间
    private Integer SubsecTimeOriginal;     //拍摄时间 微秒数
    private Timestamp dateTimeDigitized;    //数字化时间
    private Integer SubsecTimeDigitized;    //数字化时间 微秒数
    //不入数据库
    private Integer width;     //图像宽度
    private Integer length;    //图像高度

    // 静态方法：将 Metadata 信息映射到 ImgCameraInfo 对象
    public ImgCameraInfo fromMetadata(Metadata metadata) {

        this.setImageDescription(metadata.get("Exif IFD0:Image Description"));
        this.setMake(metadata.get("tiff:Make"));
        this.setModel(metadata.get("Exif IFD0:Model"));
        this.setSoftware(metadata.get("Firmware Version"));
        this.setOrientation(metadata.get("tiff:Orientation"));
        if (metadata.get("tiff:XResolution") != null)
            this.setXResolution(parseDouble(metadata.get("tiff:XResolution")));
        if (metadata.get("tiff:YResolution") != null)
            this.setYResolution(parseDouble(metadata.get("tiff:YResolution")));
        this.setResolutionUnit(metadata.get("tiff:ResolutionUnit"));
        this.setYCbCrPositioning(metadata.get("Exif IFD0:YCbCr Positioning"));
        this.setComponentsConfiguration(metadata.get("Exif SubIFD:Components Configuration"));
        this.setCompressedBitsPerPixel(metadata.get("Exif SubIFD:Compressed Bits Per Pixel"));
        this.setColorSpace(metadata.get("Exif SubIFD:Color Space"));
        this.setCompression(metadata.get("Compression Type"));

        this.setExifVersion(metadata.get("Exif SubIFD:Exif Version"));
        this.setShutterSpeed(metadata.get("Exif SubIFD:Shutter Speed Value"));
        if (metadata.get("exif:IsoSpeedRatings") != null)
            this.setIsoSpeedRatings(parseInt(metadata.get("exif:IsoSpeedRatings")));
        this.setExposureTime(metadata.get("Exif SubIFD:Exposure Time"));
        this.setExposureMode(metadata.get("Exif SubIFD:Exposure Mode"));
        this.setExposureBiasValue(metadata.get("Exif SubIFD:Exposure Bias Value:"));
        if (metadata.get("exif:FNumber") != null)
            this.setFNumber(parseFloat(metadata.get("exif:FNumber")));
        this.setMaxApertureValue(metadata.get("Exif SubIFD:Aperture Value"));
        this.setMeteringMode(metadata.get("Exif SubIFD:Metering Mode"));
//        this.setLightSource(metadata.get("Light Source"));
        this.setWhiteBalance(metadata.get("Exif SubIFD:White Balance Mode"));
        if (metadata.get("Exif SubIFD:Brightness Value") != null)
            this.setBrightness(parseFloat(metadata.get("Exif SubIFD:Brightness Value")));
        this.setFocalLength(metadata.get("Exif SubIFD:Focal Length"));
        this.setFocalLength35(metadata.get("Exif SubIFD:Focal Length 35"));
        this.setFlash(metadata.get("Exif SubIFD:Flash"));
        if (metadata.get("exif:Flash") != null)
            this.setFlashValue(Boolean.valueOf(metadata.get("exif:Flash")));
        this.setFlashPixVersion(metadata.get("Exif SubIFD:FlashPix Version"));
        this.setSceneCaptureType(metadata.get("Exif SubIFD:Scene Capture Type"));
        this.setLensMake(metadata.get("Exif SubIFD:Lens Make"));
        this.setLensModel(metadata.get("Exif SubIFD:Lens Model"));
        this.setDateTimeOriginal(convertStringToTimestamp(metadata.get("Exif SubIFD:Date/Time Original")));
        this.setDateTimeDigitized(convertStringToTimestamp(metadata.get("Exif SubIFD:Date/Time Digitized")));

//        this.setWidth(parseInt(metadata.get("tiff:ImageWidth")));
//        this.setLength(parseInt(metadata.get("tiff:ImageLength")));
        return this;
    }

    // 如果字符串格式不同，先解析为 Date，再转为 Timestamp
    public static Timestamp convertStringToTimestamp(String str, String pattern) {
        try {
            if (str == null || str.isEmpty()) return null;
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            Date parsedDate = dateFormat.parse(str);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // 如果字符串已经是 "yyyy:MM:dd HH:mm:ss" 格式
    public static Timestamp convertStringToTimestamp(String str) {
        return convertStringToTimestamp(str, "yyyy:MM:dd HH:mm:ss");
    }
}
